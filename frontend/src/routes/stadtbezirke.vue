<template>
  <base-view :domain-key="domainKey">
    <!-- @vue-generic {Partial<StadtbezirkResponseDTO>} -->
    <crud-card
      ref="crudRef"
      v-model="dataTableOptions"
      :empty-item-template="EMPTY_ITEM_TEMPLATE"
      :loading="loading"
      :table-headers="headers"
      :domain-key="domainKey"
      :enable-actions="isAdmin"
      :items="stadtbezirke?.content ?? []"
      :total-items="stadtbezirke?.page?.totalElements ?? 0"
      @delete="handleDelete"
      @create="handleCreate"
      @update="handleUpdate"
    >
      <template #form="{ item, updateValidity, inputDisplayMode }">
        <stadtbezirk-form
          v-if="stadtbezirkFormContext"
          ref="stadtbezirkForm"
          :model-value="item"
          :display-mode="inputDisplayMode"
          :stadtbezirk-form-context="stadtbezirkFormContext"
          @is-valid="updateValidity"
        />
      </template>
    </crud-card>
  </base-view>
</template>

<script setup lang="ts">
import type { StadtbezirkResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import StadtbezirkForm from "@/components/forms/StadtbezirkForm.vue";
import {
  useCreateStadtbezirk,
  useDeleteStadtbezirk,
  useGetStadtbezirke,
  useGetStadtbezirkFormContext,
  useUpdateStadtbezirk,
} from "@/composables/api/useStadtbezirkApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import usePagination from "@/composables/usePagination";
import { Role } from "@/types/Role";

const domainKey = "model.stadtbezirk.modelName";

const isAdmin = useHasAnyRole(Role.ADMIN);

const { t } = useI18n();

const headers: DataTableHeader<Partial<StadtbezirkResponseDTO>>[] = [
  {
    title: t("model.stadtbezirk.stadtbezirk"),
    value: "stadtbezirk",
    align: "center",
    width: 100,
  },
  { title: t("model.stadtbezirk.bezeichnung"), value: "bezeichnung" },
];

const EMPTY_ITEM_TEMPLATE: Partial<StadtbezirkResponseDTO> = {
  stadtbezirk: undefined,
  bezeichnung: "",
};

const {
  data: stadtbezirke,
  call: getStadtbezirke,
  loading: getStadtbezirkeLoading,
} = useGetStadtbezirke();

const {
  data: stadtbezirkFormContext,
  call: getStadtbezirkFormContext,
  loading: getStadtbezirkFormContextLoading,
} = useGetStadtbezirkFormContext();

type StadtbezirkFormType = InstanceType<typeof StadtbezirkForm>;
const stadtbezirkFormRef =
  useTemplateRef<StadtbezirkFormType>("stadtbezirkForm");

const { dataTableOptions, onSuccess, onFailure } = usePagination(
  computed(() => stadtbezirke.value?.page?.totalPages),
  getStadtbezirke,
  getStadtbezirkFormContext,
  () => stadtbezirkFormRef.value?.validate()
);

const {
  call: createStadtbezirk,
  loading: createStadtbezirkLoading,
  error: createStadtbezirkeError,
} = useCreateStadtbezirk();

const handleCreate = async (
  stadtbezirkCreateDTO: Partial<StadtbezirkResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = stadtbezirkCreateDTO as StadtbezirkResponseDTO;
  await createStadtbezirk({
    stadtbezirkCreateDTO: model,
  });
  if (!createStadtbezirkeError.value) {
    await onSuccess(t("common.message.created", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.createdError", [t(domainKey)]));
  }
};

const {
  call: updateStadtbezirk,
  loading: updateStadtbezirkLoading,
  error: updateStadtbezirkError,
} = useUpdateStadtbezirk();

const handleUpdate = async (
  stadtbezirkUpdateDTO: Partial<StadtbezirkResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = stadtbezirkUpdateDTO as StadtbezirkResponseDTO;
  await updateStadtbezirk({
    id: model.id,
    stadtbezirkUpdateDTO: model,
  });
  if (!updateStadtbezirkError.value) {
    await onSuccess(t("common.message.updated", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.updatedError", [t(domainKey)]));
  }
};

const {
  call: deleteStadtbezirk,
  loading: deleteStadtbezirkLoading,
  error: deleteStadtbezirkError,
} = useDeleteStadtbezirk();

const handleDelete = async (id: string) => {
  await deleteStadtbezirk({
    id,
  });
  if (!deleteStadtbezirkError.value) {
    await onSuccess(t("common.message.deleted", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.deletedError", [t(domainKey)]));
  }
};

const loading = computed(
  () =>
    getStadtbezirkeLoading.value ||
    getStadtbezirkFormContextLoading.value ||
    createStadtbezirkLoading.value ||
    updateStadtbezirkLoading.value ||
    deleteStadtbezirkLoading.value
);
</script>
