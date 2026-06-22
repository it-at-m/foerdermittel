<template>
  <base-view :domain-key="domainKey">
    <crud-card
      ref="crudRef"
      :empty-item-template="EMPTY_ITEM_TEMPLATE"
      :loading="loading"
      :table-headers="headers"
      :domain-key="domainKey"
      :enable-actions="isAdmin"
      :items="bauprogramme?.content ?? []"
      :total-items="bauprogramme?.page?.totalElements ?? 0"
      :items-per-page="dataTableOptions.itemsPerPage"
      :dialog-width="DialogWidth.MEDIUM"
      expandable
      @delete="handleDelete"
      @create="handleCreate"
      @update="handleUpdate"
      @updated-options="handleUpdatedOptions"
    >
      <template #form="{ item, updateValidity, inputDisplayMode }">
        <bauprogramm-form
          :model-value="item"
          :display-mode="inputDisplayMode"
          @is-valid="updateValidity"
        />
      </template>
    </crud-card>
  </base-view>
</template>

<script setup lang="ts">
import type {
  BauprogrammResponseDTO
} from "@/api/generated/foerdermittel-backend";
import type { DataTableOptions } from "@/types/DataTableOptions";
import type { TableColumnHeader } from "@/types/TableColumnHeader";

import { computed, onMounted, ref, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import BauprogrammForm from "@/components/forms/BauprogrammForm.vue";
import {
  useCreateBauprogramm,
  useDeleteBauprogramm,
  useGetBauprogramme,
  useUpdateBauprogramm,
} from "@/composables/api/useBauprogrammApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import { STATUS_INDICATORS } from "@/constants";
import { useSnackbarStore } from "@/stores/snackbar";
import { DialogWidth } from "@/types/DialogWidth";
import { Role } from "@/types/Role";

const domainKey = "model.bauprogramm.modelName";

const isAdmin = useHasAnyRole(Role.ADMIN);

const { t } = useI18n();

const headers: TableColumnHeader<BauprogrammResponseDTO>[] = [
  { title: t("model.bauprogramm.bauprogramm"), value: "bauprogramm", align: "center", width: 50 },
  { title: t("model.bauprogramm.bezeichnung"), value: "bezeichnung" },
];

const EMPTY_ITEM_TEMPLATE: Partial<BauprogrammResponseDTO> = {
  bauprogramm: undefined,
  bezeichnung: "",
};

const dataTableOptions = ref<DataTableOptions>({
  page: 1,
  itemsPerPage: 10,
  sortBy: [],
});

const pageable = computed(() => {
  return {
    page: dataTableOptions.value.page - 1,
    size: dataTableOptions.value.itemsPerPage,
    sort: dataTableOptions.value.sortBy.map(sortOption => Object.values(sortOption).join(","))
  };
})

const handleUpdatedOptions = async (newOptions: DataTableOptions) => {
  dataTableOptions.value = newOptions;
  await getBauprogramme(pageable.value);
};

const {
  data: bauprogramme,
  call: getBauprogramme,
  loading: getBauprogrammeLoading,
} = useGetBauprogramme();

onMounted(() =>
  getBauprogramme(pageable.value)
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
    onFailure(t("common.message.createdError", [t(domainKey)]));
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
    onFailure(t("common.message.updatedError", [t(domainKey)]));
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
    onFailure(t("common.message.deletedError", [t(domainKey)]));
  }
};

const loading = computed(
  () =>
    getBauprogrammeLoading.value ||
    createBauprogrammLoading.value ||
    updateBauprogrammLoading.value ||
    deleteBauprogrammLoading.value
);

const snackbarStore = useSnackbarStore();

const crudRef = useTemplateRef("crudRef");
const onSuccess = async (msg: string) => {
  snackbarStore.push({ text: msg, color: STATUS_INDICATORS.SUCCESS });
  if (crudRef.value) {
    crudRef.value.closeDialog();
  }
  await getBauprogramme(pageable.value);
};
const onFailure = (msg: string) => {
  snackbarStore.push({ text: msg, color: STATUS_INDICATORS.ERROR });
};
</script>
