
<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <v-card variant="flat">
        <v-card-title class="d-flex align-center text-h6 px-0">
          <v-btn
              :icon="mdiArrowLeft"
              variant="text"
              density="comfortable"
              :to="{ path: '/haushaltsplanung' }"
              aria-label="Zurück zur Haushaltsplanung"
          />
          <span>
            {{ t("model.haushaltsplanung.modelName") }}
            {{ hhjahr ?? "" }}
          </span>
        </v-card-title>

        <v-data-table
            :headers="headers"
            :items="planungen"
            item-value="proProjnr"
            density="compact"
            show-expand
            expand-on-click
            :items-per-page="-1"
            hide-default-footer
            :loading="baseViewLoading || hhplanApi?.loading.value"
        >
          <template #expanded-row="{ item, columns }">
            <td :colspan="columns.length">
              <v-card variant="tonal" class="ma-2">
                <v-card-text>
                  <v-row align="start">
                    <v-col cols="12" md="2">
                      <div class="text-subtitle-2">
                        {{ t("model.haushaltsplanung.projektdetails") }}
                      </div>
                    </v-col>

                    <v-col cols="12" md="10">
                      <v-row>
                        <v-col
                            v-for="detail in getProjektDetails(item)"
                            :key="detail.label"
                            cols="12"
                            md="3"
                        >
                          <v-label>{{ detail.label }}</v-label>
                          <div>{{ detail.value }}</div>
                        </v-col>
                      </v-row>
                    </v-col>
                  </v-row>
                </v-card-text>
              </v-card>
            </td>
          </template>

          <template #no-data>
            {{ t("model.haushaltsplanung.keineZuordnungen") }}
          </template>
        </v-data-table>
      </v-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { HhplanResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { mdiArrowLeft } from "@mdi/js";
import { computed, watch } from "vue";
import { useI18n } from "vue-i18n";
import { useRoute } from "vue-router";

import BaseView from "@/components/common/BaseView.vue";
import { useGetHaushaltsplanungenByHhjahr } from "@/composables/api/useHaushaltsplanungApi";
import { Role } from "@/types/Role";

const { t } = useI18n();
const route = useRoute();

definePage({
  path: "/haushaltsplanung/haushaltszahlen",
  meta: {
    hasAnyRole: [
      Role.SACHBEARBEITUNG,
      Role.SACHBEARBEITUNG_HAUSHALT,
      Role.ADMIN,
    ],
  },
});

const hhjahr = computed(() => {
  const value = route.query.hhjahr;

  if (typeof value !== "string" || value.trim() === "") {
    return undefined;
  }

  const year = Number(value);
  return Number.isInteger(year) ? year : undefined;
});

const domainKey = "model.haushaltsplanung.modelName";

const hhplanApi = useGetHaushaltsplanungenByHhjahr?.();

watch(
    hhjahr,
    async (year) => {
      if (year === undefined) {
        return;
      }

      await hhplanApi?.call({ hhjahr: year });
    },
    { immediate: true }
);

const planungen = computed<HhplanResponseDTO[]>(() =>
    hhjahr.value === undefined
        ? []
        : (hhplanApi?.data.value?.content ?? [])
);

const formatDate = (value?: Date | string | null) =>
    value
        ? new Date(value).toLocaleDateString("de-DE", {
          day: "2-digit",
          month: "2-digit",
          year: "numeric",
        })
        : "";

const getProjektDetails = (item: HhplanResponseDTO) =>
    [
      ["proProjnr", item.proProjnr],
      ["pname", item.pname],
      ["pstrasse", item.pstrasse],
      ["notizen", item.notizen || "—"],
      ["anlagedatum", formatDate(item.anlagedatum)],
      ["anlagevon", item.anlagevon || "—"],
      ["aenderungdatum", formatDate(item.aenderungsdatum)],
      ["aenderungvon", item.aenderungvon || "—"],
    ].map(([key, value]) => ({
      label: t(`model.haushaltsplanung.${key}`),
      value,
    }));

const headers: DataTableHeader<HhplanResponseDTO>[] = [
  {
    title: t("model.haushaltsplanung.proProjnr"),
    value: "proProjnr",
    width: 80,
  },
  {
    title: t("model.haushaltsplanung.fipo"),
    value: "fipo",
    width: 140,
  },
  {
    title: t("model.haushaltsplanung.fobFb"),
    value: "fobFb",
    width: 30,
  },
  {
    title: t("model.haushaltsplanung.stadtbezirk"),
    value: "stadtbezirk",
    width: 30,
  },
  {
    title: t("model.haushaltsplanung.listenname"),
    value: "listenname",
    width: 30,
  },
  {
    title: t("model.haushaltsplanung.mitteleinplanung"),
    value: "mitteleinplanung",
    width: 50,
  },
  {
    title: t("model.haushaltsplanung.bewilligungVoj"),
    value: "bewilligungVoj",
    width: 50,
  },
  {
    title: t("model.haushaltsplanung.bewilligungHhj"),
    value: "bewilligungHhj",
    width: 50,
  },
  {
    title: t("model.haushaltsplanung.erhaltenVoj"),
    value: "erhaltenVoj",
    width: 50,
  },
  {
    title: t("model.haushaltsplanung.erhaltenHhj"),
    value: "erhaltenHhj",
    width: 50,
  },
  {
    title: t("model.haushaltsplanung.baustandaktuell"),
    value: "baustandaktuell",
    width: 50,
  },
  {
    title: t("model.haushaltsplanung.an"),
    value: "an",
    width: 30,
  },
  {
    title: t("model.haushaltsplanung.n1"),
    value: "n1",
    width: 30,
  },
  {
    title: t("model.haushaltsplanung.n2"),
    value: "n2",
    width: 30,
  },
];
</script>
