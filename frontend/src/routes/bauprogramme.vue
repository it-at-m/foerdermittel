<template>
  <base-view :domain-key="domainKey">
    <!-- @vue-generic {Partial<BauprogrammResponseDTO>} -->
    <crud-card
      ref="crudRef"
      v-model="dataTableOptions"
      :empty-item-template="EMPTY_ITEM_TEMPLATE"
      :loading="loading"
      :table-headers="headers"
      :domain-key="domainKey"
      :enable-actions="isAdmin"
      :items="bauprogramme?.content ?? []"
      :total-items="bauprogramme?.page?.totalElements ?? 0"
      :dialog-width="DialogWidth.MEDIUM"
      @delete="handleDelete"
      @create="handleCreate"
      @update="handleUpdate"
    >
      <template #form="{ item, updateValidity, inputDisplayMode }">
        <bauprogramm-form
          v-if="bauprogrammFormContext"
          ref="bauprogrammForm"
          :model-value="item"
          :display-mode="inputDisplayMode"
          :bauprogramm-form-context="bauprogrammFormContext"
          @is-valid="updateValidity"
        />
      </template>
    </crud-card>
  </base-view>
</template>

<script setup lang="ts">
import type { BauprogrammResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import BauprogrammForm from "@/components/forms/BauprogrammForm.vue";
import {
  useCreateBauprogramm,
  useDeleteBauprogramm,
  useGetBauprogramme,
  useGetBauprogrammFormContext,
  useUpdateBauprogramm,
} from "@/composables/api/useBauprogrammApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import usePagination from "@/composables/usePagination";
import { DialogWidth } from "@/types/DialogWidth";
import { Role } from "@/types/Role";

const domainKey = "model.bauprogramm.modelName";

const isAdmin = useHasAnyRole(Role.ADMIN);

const { t } = useI18n();

const headers: DataTableHeader<Partial<BauprogrammResponseDTO>>[] = [
  {
    title: t("model.bauprogramm.bauprogramm"),
    value: "bauprogramm",
    align: "center",
    width: 100,
  },
  { title: t("model.bauprogramm.bezeichnung"), value: "bezeichnung" },
];

const EMPTY_ITEM_TEMPLATE: Partial<BauprogrammResponseDTO> = {
  bauprogramm: undefined,
  bezeichnung: "",
};

const {
  data: bauprogramme,
  call: getBauprogramme,
  loading: getBauprogrammeLoading,
} = useGetBauprogramme();

const {
  data: bauprogrammFormContext,
  call: getBauprogrammFormContext,
  loading: getBauprogrammFormContextLoading,
} = useGetBauprogrammFormContext();

type BauprogrammFormType = InstanceType<typeof BauprogrammForm>;
const bauprogrammFormRef =
  useTemplateRef<BauprogrammFormType>("bauprogrammForm");

const { dataTableOptions, onSuccess, onFailure } = usePagination(
  getBauprogramme,
  getBauprogrammFormContext,
  () => bauprogrammFormRef.value?.validate()
);

const {
  call: createBauprogramm,
  loading: createBauprogrammLoading,
  error: createBauprogrammeError,
} = useCreateBauprogramm();

const handleCreate = async (
  bauprogrammCreateDTO: Partial<BauprogrammResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = bauprogrammCreateDTO as BauprogrammResponseDTO;
  await createBauprogramm({
    bauprogrammCreateDTO: model,
  });
  if (!createBauprogrammeError.value) {
    await onSuccess(t("common.message.created", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.createdError", [t(domainKey)]));
  }
};

const {
  call: updateBauprogramm,
  loading: updateBauprogrammLoading,
  error: updateBauprogrammError,
} = useUpdateBauprogramm();

const handleUpdate = async (
  bauprogrammUpdateDTO: Partial<BauprogrammResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = bauprogrammUpdateDTO as BauprogrammResponseDTO;
  await updateBauprogramm({
    id: model.id,
    bauprogrammUpdateDTO: model,
  });
  if (!updateBauprogrammError.value) {
    await onSuccess(t("common.message.updated", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.updatedError", [t(domainKey)]));
  }
};

const {
  call: deleteBauprogramm,
  loading: deleteBauprogrammLoading,
  error: deleteBauprogrammError,
} = useDeleteBauprogramm();

const handleDelete = async (id: string) => {
  await deleteBauprogramm({
    id,
  });
  if (!deleteBauprogrammError.value) {
    await onSuccess(t("common.message.deleted", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.deletedError", [t(domainKey)]));
  }
};

const loading = computed(
  () =>
    getBauprogrammeLoading.value ||
    getBauprogrammFormContextLoading.value ||
    createBauprogrammLoading.value ||
    updateBauprogrammLoading.value ||
    deleteBauprogrammLoading.value
);
</script>
