<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <!-- @vue-generic {Partial<PublikationResponseDTO>} -->
      <crud-card
        ref="crudRef"
        v-model="dataTableOptions"
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="loading || baseViewLoading"
        :table-headers="headers"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :items="publikationen?.content ?? []"
        :total-items="publikationen?.page?.totalElements ?? 0"
        @delete="handleDelete"
        @create="handleCreate"
        @update="handleUpdate"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <publikation-form
            v-if="publikationFormContext"
            ref="publikationForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :publikation-form-context="publikationFormContext"
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { PublikationResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import PublikationForm from "@/components/forms/PublikationForm.vue";
import {
  useCreatePublikation,
  useDeletePublikation,
  useGetPublikationen,
  useGetPublikationFormContext,
  useUpdatePublikation,
} from "@/composables/api/usePublikationApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import usePagination from "@/composables/usePagination";
import { Role } from "@/types/Role";

const domainKey = "model.publikation.modelName";

const isAdmin = useHasAnyRole(Role.ADMIN);

const { t } = useI18n();

const headers: DataTableHeader<Partial<PublikationResponseDTO>>[] = [
  {
    title: t("model.publikation.kurzform"),
    value: "kurzform",
    align: "center",
    width: 100,
  },
  { title: t("model.publikation.bezeichnung"), value: "bezeichnung" },
];

const EMPTY_ITEM_TEMPLATE: Partial<PublikationResponseDTO> = {
  kurzform: undefined,
  bezeichnung: "",
};

const {
  data: publikationen,
  call: getPublikationen,
  loading: getPublikationenLoading,
} = useGetPublikationen();

const {
  data: publikationFormContext,
  call: getPublikationFormContext,
  loading: getPublikationFormContextLoading,
} = useGetPublikationFormContext();

type PublikationFormType = InstanceType<typeof PublikationForm>;
const publikationFormRef =
  useTemplateRef<PublikationFormType>("publikationForm");

const { dataTableOptions, onSuccess, onFailure } = usePagination(
  computed(() => publikationen.value?.page?.totalPages),
  getPublikationen,
  getPublikationFormContext,
  () => publikationFormRef.value?.validate()
);

const {
  call: createPublikation,
  loading: createPublikationLoading,
  error: createPublikationenError,
} = useCreatePublikation();

const handleCreate = async (
  publikationCreateDTO: Partial<PublikationResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = publikationCreateDTO as PublikationResponseDTO;
  await createPublikation({
    publikationCreateDTO: model,
  });
  if (!createPublikationenError.value) {
    await onSuccess(t("common.message.created", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.createdError", [t(domainKey)]));
  }
};

const {
  call: updatePublikation,
  loading: updatePublikationLoading,
  error: updatePublikationenError,
} = useUpdatePublikation();

const handleUpdate = async (
  publikationUpdateDTO: Partial<PublikationResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = publikationUpdateDTO as PublikationResponseDTO;
  await updatePublikation({
    id: model.id,
    publikationUpdateDTO: model,
  });
  if (!updatePublikationenError.value) {
    await onSuccess(t("common.message.updated", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.updatedError", [t(domainKey)]));
  }
};

const {
  call: deletePublikation,
  loading: deletePublikationLoading,
  error: deletePublikationenError,
} = useDeletePublikation();

const handleDelete = async (id: string) => {
  await deletePublikation({
    id,
  });
  if (!deletePublikationenError.value) {
    await onSuccess(t("common.message.deleted", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.deletedError", [t(domainKey)]));
  }
};

const loading = computed(
  () =>
    getPublikationenLoading.value ||
    getPublikationFormContextLoading.value ||
    createPublikationLoading.value ||
    updatePublikationLoading.value ||
    deletePublikationLoading.value
);
</script>
