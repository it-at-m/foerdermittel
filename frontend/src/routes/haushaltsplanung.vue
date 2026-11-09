<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
          :empty-item-template="EMPTY_ITEM_TEMPLATE"
          :loading="baseViewLoading"
          :table-headers="headers"
          :api="haushaltsplanungApi"
          :domain-key="domainKey"
          :enable-actions="isAdmin"
          :expandable="true"
      >
        <template #expanded="{ item }">
          <div>
            <!-- Header -->
            <v-row class="font-weight-bold py-2 pl-6">
              <v-col cols="3">
                {{ t("model.haushaltsplanung.proProjnr") }}
              </v-col>
              <v-col cols="3">
                {{ t("model.haushaltsplanung.fipo") }}
              </v-col>
              <v-col cols="3">
                {{ t("model.projekte.fobFb") }}
              </v-col>
              <v-col cols="3">
                {{ t("model.projekte.bezStadtbezirke") }}
              </v-col>
            </v-row>

            <v-divider />

            <v-row class="font-weight-bold py-2 pl-6">
              <v-col cols="3">
                {{ t("model.haushaltsplanung.lnaKurzbez") }}
              </v-col>
              <v-col cols="3">
                {{ t("model.haushaltsplanung.mitteleinplanung") }}
              </v-col>
              <v-col cols="3">
                {{ t("model.haushaltsplanung.bewilligungVoj") }}
              </v-col>
              <v-col cols="3">
                {{ t("model.haushaltsplanung.bewilligungHhj") }}
              </v-col>
            </v-row>

            <v-divider />

            <v-row class="font-weight-bold py-2 pl-6">
              <v-col cols="3">
                {{ t("model.haushaltsplanung.erhaltenVoj") }}
              </v-col>
              <v-col cols="3">
                {{ t("model.haushaltsplanung.erhaltenHhj") }}
              </v-col>
              <v-col cols="3">
                {{ t("model.haushaltsplanung.baustandaktuell") }}
              </v-col>
              <v-col cols="3">
                {{ t("model.haushaltsplanung.pname") }}
              </v-col>
            </v-row>

            <v-divider />

            <v-row class="font-weight-bold py-2 pl-6">
              <v-col cols="6">
                {{ t("model.haushaltsplanung.pstrasse") }}
              </v-col>
              <v-col cols="6">
                {{ t("model.haushaltsplanung.notizen") }}
              </v-col>
            </v-row>

            <v-divider />

            <v-row class="font-weight-bold py-2 pl-6">
              <v-col cols="4">
                {{ t("model.haushaltsplanung.an") }}
              </v-col>
              <v-col cols="4">
                {{ t("model.haushaltsplanung.n1") }}
              </v-col>
              <v-col cols="4">
                {{ t("model.haushaltsplanung.n2") }}
              </v-col>
            </v-row>

            <v-divider />

            <!-- Einträge -->
            <v-row
                v-if="!item.assignedProProjnr?.length"
                class="py-4 pl-6"
            >
              <v-col cols="12" class="text-medium-emphasis">
                Keine zugeordneten Haushaltsplanungen vorhanden.
              </v-col>
            </v-row>

            <template
                v-for="planung in item.assignedProProjnr"
                :key="planung.proProjnr"
            >
              <v-row class="py-2 pl-6">
                <v-col cols="3">{{ planung.proProjnr }}</v-col>
                <v-col cols="3">{{ planung.fipo }}</v-col>
                <v-col cols="3">{{ planung.fobFb }}</v-col>
                <v-col cols="3">{{ planung.bezStadtbezirke }}</v-col>
              </v-row>

              <v-row class="py-2 pl-6">
                <v-col cols="3">{{ planung.lnaKurzbez }}</v-col>
                <v-col cols="3">{{ planung.mitteleinplanung }}</v-col>
                <v-col cols="3">{{ planung.bewilligungVoj }}</v-col>
                <v-col cols="3">{{ planung.bewilligungHhj }}</v-col>
              </v-row>

              <v-row class="py-2 pl-6">
                <v-col cols="3">{{ planung.erhaltenVoj }}</v-col>
                <v-col cols="3">{{ planung.erhaltenHhj }}</v-col>
                <v-col cols="3">{{ planung.baustandaktuell }}</v-col>
                <v-col cols="3">{{ planung.pname }}</v-col>
              </v-row>

              <v-row class="py-2 pl-6">
                <v-col cols="6">{{ planung.pstrasse }}</v-col>
                <v-col cols="6">{{ planung.notizen }}</v-col>
              </v-row>

              <v-row class="py-2 pl-6">
                <v-col cols="4">{{ planung.an }}</v-col>
                <v-col cols="4">{{ planung.n1 }}</v-col>
                <v-col cols="4">{{ planung.n2 }}</v-col>
              </v-row>

              <v-divider />
            </template>
          </div>
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type {HhjahrResponseDTO, HhplanResponseDTO} from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import { useHaushaltsplanungApi } from "@/composables/api/useHaushaltsplanungApi.ts";
import useHasAnyRole from "@/composables/useHasAnyRole";
import { Role } from "@/types/Role";

const domainKey = "model.haushaltsplanung.modelName";

const { t } = useI18n();

const isAdmin = useHasAnyRole(Role.ADMIN);

definePage({
  meta: {
    hasAnyRole: [
      Role.SACHBEARBEITUNG,
      Role.SACHBEARBEITUNG_HAUSHALT,
      Role.ADMIN,
    ],
  },
});

const headers: DataTableHeader<Partial<HhjahrResponseDTO>>[] = [
  {
    title: t("model.haushaltsjahr.hhjahr"),
    value: "hhjahr",
    width: 120,
  },
  {
    title: t("model.haushaltsjahr.hhbezeichnung"),
    value: "hhbezeichnung",
    width: 120,
  },
  {
    title: t("model.haushaltsjahr.notizen"),
    value: "notizen",
    width: 120,
  }
];

const EMPTY_ITEM_TEMPLATE: Partial<HhjahrResponseDTO> = {
  hhjahr: undefined,
  hhbezeichnung: "",
  notizen: undefined,
  assignedProProjnr: [],
};

const haushaltsplanungApi = useHaushaltsplanungApi();

</script>
