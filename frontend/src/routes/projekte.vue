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
        :handle-create="handleCreate"
        :handle-update="handleUpdate"
        :handle-delete="handleDelete"
        :form-ref="projektFormRef"
        should-load-form-context
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <projekt-form
            v-if="projektFormContext"
            ref="projektForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :projekt-form-context="projektFormContext"
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { ProjektResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { computed, useTemplateRef } from "vue";
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
    title: t("model.projekt.pname"),
    value: "pname",
    sortable: true,
  },
  {
    title: t("model.projekt.pstrasse"),
    value: "pstrasse",
    sortable: true,
  },
];

const EMPTY_ITEM_TEMPLATE: Partial<ProjektResponseDTO> = {
  projnr: "",
  fobFB: "",
  kurKurzbez: undefined,
  uasUa: undefined,
  pname: "",
  pstrasse: "",
};

const projektApi = useProjektApi();

const projektFormContext = computed(() => projektApi.context.data.value);

type ProjektFormType = InstanceType<typeof ProjektForm>;
const projektFormRef = useTemplateRef<ProjektFormType>("projektForm");

const handleCreate = async (projektCreateDTO: Partial<ProjektResponseDTO>) => {
  // TODO: some type checking improvements
  const model = projektCreateDTO as ProjektResponseDTO;
  await projektApi.create.call({
    projektCreateDTO: model,
  });
};

const handleUpdate = async (projektUpdateDTO: Partial<ProjektResponseDTO>) => {
  // TODO: some type checking improvements
  const model = projektUpdateDTO as ProjektResponseDTO;
  await projektApi.update.call({
    id: model.projnr,
    projektUpdateDTO: model,
  });
};
const handleDelete = async (id: string) => {
  await projektApi.delete.call({
    id,
  });
};
</script>
