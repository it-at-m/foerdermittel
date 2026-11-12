<template>
  <v-card variant="flat">
    <v-card-title class="text-h6 px-0">
      {{ t("model.haushaltsplanung.modelName") }}
    </v-card-title>

    <v-data-table
      :headers="headers"
      :items="props.planungen"
      item-value="proProjnr"
      density="compact"
      show-expand
      expand-on-click
      :items-per-page="-1"
      hide-default-footer
    >
      <template #expanded-row="{ item, columns }">
        <td :colspan="columns.length">
          <v-card
            variant="tonal"
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
                      cols="12"
                      md="3"
                    >
                      <v-label>
                        {{ t("model.haushaltsplanung.proProjnr") }}
                      </v-label>
                      <div>{{ item.proProjnr }}</div>
                    </v-col>

                    <v-col
                      cols="12"
                      md="3"
                    >
                      <v-label>
                        {{ t("model.haushaltsplanung.pname") }}
                      </v-label>
                      <div>{{ item.pname }}</div>
                    </v-col>

                    <v-col
                      cols="12"
                      md="3"
                    >
                      <v-label>
                        {{ t("model.haushaltsplanung.pstrasse") }}
                      </v-label>
                      <div>{{ item.pstrasse }}</div>
                    </v-col>

                    <v-col
                      cols="12"
                      md="3"
                    >
                      <v-label>
                        {{ t("model.haushaltsplanung.notizen") }}
                      </v-label>
                      <div>{{ item.notizen || "—" }}</div>
                    </v-col>

                    <v-col
                      cols="12"
                      md="3"
                    >
                      <v-label>
                        {{ t("model.haushaltsplanung.anlagedatum") }}
                      </v-label>
                      <div>{{ formatDate(item.anlagedatum) }}</div>
                    </v-col>

                    <v-col
                      cols="12"
                      md="3"
                    >
                      <v-label>
                        {{ t("model.haushaltsplanung.anlagevon") }}
                      </v-label>
                      <div>{{ item.anlagevon || "—" }}</div>
                    </v-col>

                    <v-col
                      cols="12"
                      md="3"
                    >
                      <v-label>
                        {{ t("model.haushaltsplanung.aenderungdatum") }}
                      </v-label>
                      <div>{{ formatDate(item.aenderungsdatum) }}</div>
                    </v-col>

                    <v-col
                      cols="12"
                      md="3"
                    >
                      <v-label>
                        {{ t("model.haushaltsplanung.aenderungvon") }}
                      </v-label>
                      <div>{{ item.aenderungvon || "—" }}</div>
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

<script setup lang="ts">
import type { HhjahrResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { useI18n } from "vue-i18n";

type Hhplan = NonNullable<HhjahrResponseDTO["assignedProProjnr"]>[number];

const props = defineProps<{
  planungen: Hhplan[];
}>();

const { t } = useI18n();

const headers: DataTableHeader<Hhplan>[] = [
  {
    title: t("model.haushaltsplanung.proProjnr"),
    key: "proProjnr",
    width: 90,
  },
  {
    title: t("model.haushaltsplanung.fipo"),
    key: "fipo",
    width: 145,
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
    width: 90,
  },
  {
    title: t("model.haushaltsplanung.bewilligungVoj"),
    key: "bewilligungVoj",
    width: 90,
  },
  {
    title: t("model.haushaltsplanung.bewilligungHhj"),
    key: "bewilligungHhj",
    width: 80,
  },
  {
    title: t("model.haushaltsplanung.erhaltenVoj"),
    key: "erhaltenVoj",
    width: 90,
  },
  {
    title: t("model.haushaltsplanung.erhaltenHhj"),
    key: "erhaltenHhj",
    width: 80,
  },
  {
    title: t("model.haushaltsplanung.baustandaktuell"),
    key: "baustandaktuell",
    width: 70,
  },
  {
    title: t("model.haushaltsplanung.an"),
    key: "an",
    width: 70,
  },
  {
    title: t("model.haushaltsplanung.n1"),
    key: "n1",
    width: 50,
  },
  {
    title: t("model.haushaltsplanung.n2"),
    key: "n2",
    width: 50,
  },
];

const formatDate = (value?: Date | string | null) =>
  value
    ? new Date(value).toLocaleDateString("de-DE", {
        day: "2-digit",
        month: "2-digit",
        year: "numeric",
      })
    : "";
</script>
