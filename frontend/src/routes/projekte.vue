<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="baseViewLoading"
        :table-headers="headers"
        :filter-keys="['projnr']"
        :api="projektApi"
        :domain-key="domainKey"
        :form-ref="projektFormRef"
        should-load-form-context
      >
        <template #form="{}"> </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { ProjektResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import ProjektForm from "@/components/forms/ProjektForm.vue";
import { useProjektApi } from "@/composables/api/useProjektApi";

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
  projnr: "",
  fobFb: undefined,
  kurKurzbez: undefined,
  uasUa: undefined,
  pname: "",
  pstrasse: "",
};

const projektApi = useProjektApi();

type ProjektFormType = InstanceType<typeof ProjektForm>;
const projektFormRef = useTemplateRef<ProjektFormType>("projektForm");
</script>