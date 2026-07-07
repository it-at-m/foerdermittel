<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <!-- @vue-generic {Partial<KrankenhausResponseDTO>} -->
      <crud-card
        ref="crudRef"
        v-model="dataTableOptions"
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="loading || baseViewLoading"
        :table-headers="headers"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :items="krankenhaeuser?.content ?? []"
        :total-items="krankenhaeuser?.page?.totalElements ?? 0"
        @delete="handleDelete"
        @create="handleCreate"
        @update="handleUpdate"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <krankenhaus-form
            v-if="krankenhausFormContext"
            ref="krankenhausForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :krankenhaus-form-context="krankenhausFormContext"
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { KrankenhausResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import KrankenhausForm from "@/components/forms/KrankenhausForm.vue";
import {
  useCreateKrankenhaus,
  useDeleteKrankenhaus,
  useGetKrankenhaeuser,
  useGetKrankenhausFormContext,
  useUpdateKrankenhaus,
} from "@/composables/api/useKrankenhausApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import usePagination from "@/composables/usePagination";
import { Role } from "@/types/Role";

const domainKey = "model.krankenhaus.modelName";

const isAdmin = useHasAnyRole(Role.ADMIN);

const { t } = useI18n();

const headers: DataTableHeader<Partial<KrankenhausResponseDTO>>[] = [
  {
    title: t("model.krankenhaus.krhname"),
    value: "krhname",
    align: "center",
    width: 100,
  },
  { title: t("model.krankenhaus.bezeichnung"), value: "bezeichnung" },
];

const EMPTY_ITEM_TEMPLATE: Partial<KrankenhausResponseDTO> = {
  krhname: undefined,
  bezeichnung: "",
};

const {
  data: krankenhaeuser,
  call: getKrankenhaeuser,
  loading: getKrankenhaeuserLoading,
} = useGetKrankenhaeuser();

const {
  data: krankenhausFormContext,
  call: getKrankenhausFormContext,
  loading: getKrankenhausFormContextLoading,
} = useGetKrankenhausFormContext();

type KrankenhausFormType = InstanceType<typeof KrankenhausForm>;
const krankenhausFormRef =
  useTemplateRef<KrankenhausFormType>("krankenhausForm");

const { dataTableOptions, onSuccess, onFailure } = usePagination(
  computed(() => krankenhaeuser.value?.page?.totalPages),
  getKrankenhaeuser,
  getKrankenhausFormContext,
  () => krankenhausFormRef.value?.validate()
);

const {
  call: createKrankenhaus,
  loading: createKrankenhausLoading,
  error: createKrankenhaeuserError,
} = useCreateKrankenhaus();

const handleCreate = async (
  krankenhausCreateDTO: Partial<KrankenhausResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = krankenhausCreateDTO as KrankenhausResponseDTO;
  await createKrankenhaus({
    krankenhausCreateDTO: model,
  });
  if (!createKrankenhaeuserError.value) {
    await onSuccess(t("common.message.created", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.createdError", [t(domainKey)]));
  }
};

const {
  call: updateKrankenhaus,
  loading: updateKrankenhausLoading,
  error: updateKrankenhaeuserError,
} = useUpdateKrankenhaus();

const handleUpdate = async (
  krankenhausUpdateDTO: Partial<KrankenhausResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = krankenhausUpdateDTO as KrankenhausResponseDTO;
  await updateKrankenhaus({
    id: model.id,
    krankenhausUpdateDTO: model,
  });
  if (!updateKrankenhaeuserError.value) {
    await onSuccess(t("common.message.updated", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.updatedError", [t(domainKey)]));
  }
};

const {
  call: deleteKrankenhaus,
  loading: deleteKrankenhausLoading,
  error: deleteKrankenhaeuserError,
} = useDeleteKrankenhaus();

const handleDelete = async (id: string) => {
  await deleteKrankenhaus({
    id,
  });
  if (!deleteKrankenhaeuserError.value) {
    await onSuccess(t("common.message.deleted", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.deletedError", [t(domainKey)]));
  }
};

const loading = computed(
  () =>
    getKrankenhaeuserLoading.value ||
    getKrankenhausFormContextLoading.value ||
    createKrankenhausLoading.value ||
    updateKrankenhausLoading.value ||
    deleteKrankenhausLoading.value
);
</script>
