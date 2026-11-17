<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <v-card variant="flat">
        <v-card-title
          class="d-flex align-center justify-space-between text-h6 px-0"
        >
          <div class="d-flex align-center">
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
          </div>

          <action-button
            :actions="actions"
            :loading="baseViewLoading"
          />
        </v-card-title>

        <crud-card
          v-if="hhjahr !== undefined"
          :empty-item-template="EMPTY_ITEM_TEMPLATE"
          :loading="baseViewLoading || hhplanApi.getAll?.loading.value"
          :domain-key="domainKey"
          :table-headers="headers"
          :api="hhplanApi"
          :enable-actions="false"
          :should-load-form-context="false"
          :expandable="true"
        >
          <template #form="{ item, inputDisplayMode }">
            <v-card
              v-if="inputDisplayMode === InputDisplayMode.READ"
              class="ma-2"
            >
              <v-card-text>
                <v-row align="start">
                  <v-col
                    cols="12"
                    md="2"
                  >
                    <div class="text-subtitle-2">
                      {{ t("model.haushaltsplanung.projektdetails") }}
                    </div>
                  </v-col>

                  <v-col
                    cols="12"
                    md="10"
                  >
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
          </template>
        </crud-card>
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

import ActionButton from "@/components/common/ActionButton.vue";
import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import { useHaushaltsplanungApi } from "@/composables/api/useHaushaltsplanungApi";
import { InputDisplayMode } from "@/types/InputDisplayMode";
import { Role } from "@/types/Role";

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

const { t } = useI18n();
const route = useRoute();

const domainKey = "model.haushaltsplanung.modelName";

const hhjahr = computed(() => {
  const value = route.query.hhjahr;

  if (typeof value !== "string" || value.trim() === "") {
    return undefined;
  }

  const year = Number(value);

  return Number.isInteger(year) ? year : undefined;
});

const hhplanApi = useHaushaltsplanungApi();

watch(
  hhjahr,
  async (year) => {
    if (year === undefined) {
      return;
    }

    await hhplanApi.getAll?.call({ hhjahr: year });
  },
  { immediate: true }
);

const EMPTY_ITEM_TEMPLATE = {} as HhplanResponseDTO;

const actions = computed(() => [
  {
    label: t("model.haushaltsplanung.planungErstellen"),
    onClick: () => {},
  },
  {
    label: t("model.haushaltsplanung.werteAktualisieren"),
    onClick: () => {},
  },
]);

const formatDate = (value?: Date | string | null): string =>
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

const headers = computed<DataTableHeader<HhplanResponseDTO>[]>(() => [
  {
    title: t("model.haushaltsplanung.proProjnr"),
    key: "proProjnr",
    align: "center",
    width: 100,
  },
  {
    title: t("model.haushaltsplanung.fipo"),
    key: "fipo",
    width: 150,
  },
  {
    title: t("model.haushaltsplanung.fobFb"),
    key: "fobFb",
    width: 50,
  },
  {
    title: t("model.haushaltsplanung.stadtbezirk"),
    key: "stadtbezirk",
    width: 50,
  },
  {
    title: t("model.haushaltsplanung.listenname"),
    key: "listenname",
    width: 60,
  },
  {
    title: t("model.haushaltsplanung.mitteleinplanung"),
    key: "mitteleinplanung",
    width: 100,
  },
  {
    title: t("model.haushaltsplanung.bewilligungVoj"),
    key: "bewilligungVoj",
    width: 80,
  },
  {
    title: t("model.haushaltsplanung.bewilligungHhj"),
    key: "bewilligungHhj",
    width: 80,
  },
  {
    title: t("model.haushaltsplanung.erhaltenVoj"),
    key: "erhaltenVoj",
    width: 80,
  },
  {
    title: t("model.haushaltsplanung.erhaltenHhj"),
    key: "erhaltenHhj",
    width: 80,
  },
  {
    title: t("model.haushaltsplanung.baustandaktuell"),
    key: "baustandaktuell",
    width: 80,
  },
  {
    title: t("model.haushaltsplanung.an"),
    key: "an",
    width: 60,
  },
  {
    title: t("model.haushaltsplanung.n1"),
    key: "n1",
    width: 60,
  },
  {
    title: t("model.haushaltsplanung.n2"),
    key: "n2",
    width: 60,
  },
]);
</script>
