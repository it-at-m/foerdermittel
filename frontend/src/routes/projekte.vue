<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="baseViewLoading"
        :table-headers="headers"
        :api="projektApi"
        :domain-key="domainKey"
        :filter-default="{ projnr: undefined, fobFb: undefined }"
      >
        <template #filter-form="{ filter, updateValidity }">
          <v-form @update:model-value="updateValidity">
            <v-row>
              <v-col cols="6">
                <v-text-field
                  v-model="filter.projnr"
                  :label="t('model.projekt.projnr')"
                  clearable
                />
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="6">
                <v-text-field
                  v-model="filter.fobFb"
                  :label="t('model.projekt.fobFb')"
                  clearable
                />
              </v-col>
            </v-row>
          </v-form>
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { ProjektResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import { useProjektApi } from "@/composables/api/useProjektApi";
import { Role } from "@/types/Role";

definePage({
  meta: {
    hasAnyRole: [
      Role.SACHBEARBEITUNG,
      Role.SACHBEARBEITUNG_HAUSHALT,
      Role.ADMIN,
    ],
  },
});

const domainKey = "model.projekt.modelName";

const { t } = useI18n();

const headers: DataTableHeader<Partial<ProjektResponseDTO>>[] = [
  {
    title: t("model.projekt.projnr"),
    value: "projnr",
    sortable: true,
  },
  {
    title: t("model.projekt.fobFb"),
    value: "fobFb",
    sortable: true,
  },
  {
    title: t("model.projekt.kurKurzbez"),
    value: "kurKurzbez",
    sortable: true,
  },
  {
    title: t("model.projekt.uasUa"),
    value: "uasUa",
    sortable: true,
  },
  {
    title: t("model.projekt.name"),
    value: "pname",
    sortable: true,
  },
  {
    title: t("model.projekt.strasse"),
    value: "pstrasse",
    sortable: true,
  },
];

const EMPTY_ITEM_TEMPLATE: Partial<ProjektResponseDTO> = {
  id: "",
  projnr: "",
  fobFb: undefined,
  kurKurzbez: undefined,
  uasUa: undefined,
  pname: "",
  pstrasse: "",
};

const projektApi = useProjektApi();
</script>
