<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <v-card variant="flat">
        <v-card-title>
          <div>
            <v-btn
              :icon="mdiArrowLeft"
              density="comfortable"
              :to="{ path: '/haushaltsplanung' }"
            />
            {{ t("model.haushaltsplanung.modelName") }}
            {{ hhjahr ?? "" }}
          </div>
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
              variant="flat"
            >
              <div class="pa-2">
                <v-card-title class="pa-0">
                  {{ t("model.haushaltsplanung.details") }}
                </v-card-title>

                <v-row
                  dense
                  class="mt-2"
                >
                  <v-col
                    cols="12"
                    md="6"
                  >
                    <v-row
                      v-for="detail in getProjektDetails(item)"
                      :key="detail.label"
                      dense
                    >
                      <v-col cols="5">
                        {{ `${detail.label}:` }}
                      </v-col>
                      <v-col cols="7">
                        {{ detail.value }}
                      </v-col>
                    </v-row>
                  </v-col>

                  <v-col
                    cols="12"
                    md="6"
                  >
                    <div>
                      {{ t("model.haushaltsplanung.notizen") }}
                    </div>

                    <div style="white-space: pre-wrap">
                      {{ item.notizen || "" }}
                    </div>
                  </v-col>

                  <v-divider />

                  <v-col cols="12">
                    <div v-if="item.anlagedatum || item.anlagevon">
                      {{ t("model.haushaltsplanung.anlagedatum") }}
                      {{ item.anlagedatum ? formatDate(item.anlagedatum) : "" }}
                      {{
                        item.anlagevon
                          ? t("model.haushaltsplanung.anlagevon") +
                            " " +
                            item.anlagevon
                          : ""
                      }}
                    </div>

                    <div v-if="item.aenderungsdatum || item.aenderungvon">
                      {{ t("model.haushaltsplanung.aenderungdatum") }}
                      {{
                        item.aenderungsdatum
                          ? formatDate(item.aenderungsdatum)
                          : ""
                      }}
                      {{
                        item.aenderungvon
                          ? t("model.haushaltsplanung.aenderungvon") +
                            " " +
                            item.aenderungvon
                          : ""
                      }}
                    </div>
                  </v-col>
                </v-row>
              </div>
            </v-card>
          </template>

          <template
            v-for="header in headers"
            #[`header.${header.key}`]="{ column }"
            :key="header.key"
          >
            <v-tooltip
              v-if="getHeaderTooltip(header.key)"
              :text="getHeaderTooltip(header.key)"
              location="top"
            >
              <template #activator="{ props }">
                <span v-bind="props">
                  {{ column.title }}
                </span>
              </template>
            </v-tooltip>

            <span v-else>
              {{ column.title }}
            </span>
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

const headerTooltips: Record<string, string> = {
  mitteleinplanung: "Mitteleinplanung",
  bewilligungVoj: "Bewilligung Vorjahre",
  bewilligungHhj: "Bewilligung Haushaltsjahr",
  erhaltenVoj: "Erhalten Vorjahre",
  erhaltenHhj: "Erhalten Haushaltsjahr",
  baustandaktuell: "Aktueller Baustand",
  an: "Ansatz",
  n1: "1. Nachtrag",
  n2: "2. Nachtrag",
};

const getHeaderTooltip = (key: string | undefined): string | undefined =>
  key ? headerTooltips[key] : undefined;

const formatDate = (value?: Date | string | null): string =>
  value
    ? new Date(value).toLocaleDateString("de-DE", {
        day: "2-digit",
        month: "2-digit",
        year: "numeric",
      })
    : "";

const getProjektDetails = (item: HhplanResponseDTO) => [
  {
    label: t("model.haushaltsplanung.proProjnr"),
    value: item.proProjnr || "",
  },
  {
    label: t("model.haushaltsplanung.pname"),
    value: item.pname || "",
  },
  {
    label: t("model.haushaltsplanung.pstrasse"),
    value: item.pstrasse || "",
  },
];

const headers = computed<DataTableHeader<HhplanResponseDTO>[]>(() => [
  {
    title: t("model.haushaltsplanung.proProjnr"),
    key: "proProjnr",
    width: 150,
  },
  {
    title: t("model.haushaltsplanung.fipo"),
    key: "fipo",
    width: 150,
  },
  {
    title: t("model.haushaltsplanung.fobFb"),
    key: "fobFb",
    width: 80,
  },
  {
    title: t("model.haushaltsplanung.stadtbezirk"),
    key: "stadtbezirk",
    width: 80,
  },
  {
    title: t("model.haushaltsplanung.listenname"),
    key: "listenname",
    width: 80,
  },
  {
    title: t("model.haushaltsplanung.mitteleinplanung"),
    key: "mitteleinplanung",
    width: 120,
  },
  {
    title: t("model.haushaltsplanung.bewilligungVoj"),
    key: "bewilligungVoj",
    width: 120,
  },
  {
    title: t("model.haushaltsplanung.bewilligungHhj"),
    key: "bewilligungHhj",
    width: 120,
  },
  {
    title: t("model.haushaltsplanung.erhaltenVoj"),
    key: "erhaltenVoj",
    width: 120,
  },
  {
    title: t("model.haushaltsplanung.erhaltenHhj"),
    key: "erhaltenHhj",
    width: 120,
  },
  {
    title: t("model.haushaltsplanung.baustandaktuell"),
    key: "baustandaktuell",
    width: 120,
  },
  {
    title: t("model.haushaltsplanung.an"),
    key: "an",
    width: 80,
  },
  {
    title: t("model.haushaltsplanung.n1"),
    key: "n1",
    width: 80,
  },
  {
    title: t("model.haushaltsplanung.n2"),
    key: "n2",
    width: 80,
  },
]);
</script>
