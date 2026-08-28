<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <!-- @vue-generic {Partial<ProjektResponseDTO>} -->
      <crud-card
        ref="crudRef"
        v-model="dataTableOptions"
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="loading || baseViewLoading"
        :table-headers="headers"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :items="projekte?.content ?? []"
        :total-items="projekte?.page?.totalElements ?? 0"

        @create="handleCreate"

      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <projekt-form
            :model-value="item"
            :display-mode="inputDisplayMode"
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type {
  ProjektResponseDTO
} from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import ProjektForm from "@/components/forms/ProjektForm.vue";
import {
  useCreateProjekt,
  useGetProjekte,
  useGetProjektFormContext,
} from "@/composables/api/useProjektApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import usePagination from "@/composables/usePagination";
import { Role } from "@/types/Role";

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
  fobFb: undefined,
  kurKurzbez: undefined,
  uasUa: undefined,
  pname: "",
  pstrasse: "",
};

const isAdmin = useHasAnyRole(Role.ADMIN);

const {
  data: projekte,
  call: getProjekte,
  loading: getProjekteLoading,
} = useGetProjekte();

const {
  data: projektFormContext,
  call: getProjektFormContext,
  loading: getProjektFormContextLoading,
} = useGetProjektFormContext();

type ProjektFormType = InstanceType<typeof ProjektForm>;
const projektFormRef = useTemplateRef<ProjektFormType>("projektForm");

const { dataTableOptions, onSuccess, onFailure } = usePagination(
  computed(() => projekte.value?.page?.totalPages),
  getProjekte,
  isAdmin,
  getProjektFormContext,
  () => projektFormRef.value?.validate()
);

const {
  call: createProjekt,
  loading: createProjektLoading,
  error: createProjekteError,
} = useCreateProjekt();

const handleCreate = async (projektCreateDTO: Partial<ProjektResponseDTO>) => {
  // TODO: some type checking improvements
  const model = projektCreateDTO as ProjektResponseDTO;
  await createProjekt({
    projektCreateDTO: model,
  });
  if (!createProjekteError.value) {
    await onSuccess(t("common.message.created", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.createdError", [t(domainKey)]));
  }
};

const loading = computed(() => getProjekteLoading.value);
</script>