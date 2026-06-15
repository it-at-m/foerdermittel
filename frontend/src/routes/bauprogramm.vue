<template>
  <base-view :domain-key="domainKey">
    <crud-card
      ref="crudRef"
      :empty-item-template="EMPTY_ITEM_TEMPLATE"
      :loading="loading"
      :table-headers="headers"
      :domain-key="domainKey"
      :enable-actions="isAdmin"
      :items="bauprogramme"
      :total-items="testsData?.length ?? 0"
      :items-per-page="dataTableOptions.itemsPerPage"
      expandable
      @delete="handleDelete"
      @create="handleCreate"
      @update="handleUpdate"
      @updated-options="handleUpdatedOptions"
    >
    </crud-card>
  </base-view>
</template>

<script setup lang="ts">
import type {
  BauprogrammCreateDTO,
  BauprogrammResponseDTO,
  BauprogrammUpdateDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DataTableOptions } from "@/types/DataTableOptions";
import type { TableColumnHeader } from "@/types/TableColumnHeader";

import { computed, onMounted, ref, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import {
  useCreateBauprogramm,
  useDeleteBauprogramm,
  useGetBauprogramme,
  useUpdateBauprogramm,
} from "@/composables/api/useBauprogrammApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import { STATUS_INDICATORS } from "@/constants";
import { useSnackbarStore } from "@/stores/snackbar";
import { Role } from "@/types/Role";

const domainKey = "model.bauprogramm.modelName";

const isAdmin = useHasAnyRole(Role.ADMIN);

const { t } = useI18n();

const headers: TableColumnHeader<BauprogrammResponseDTO>[] = [
  { title: t("model.bauprogramm.bauprogramm"), value: "bauprogramm" },
  { title: t("model.bauprogramm.bezeichnung"), value: "bezeichnung" },
];

const EMPTY_ITEM_TEMPLATE: Partial<BauprogrammCreateDTO> = {
  bauprogramm: undefined,
  bezeichnung: "",
};

const dataTableOptions = ref<DataTableOptions>({
  page: 1,
  itemsPerPage: 12,
  sortBy: [],
});

const handleUpdatedOptions = async (newOptions: DataTableOptions) => {
  dataTableOptions.value = newOptions;
  await getBauprogramme({
    pageNumber: dataTableOptions.value.page,
    pageSize: dataTableOptions.value.itemsPerPage,
  });
};

const {
  data: bauprogramme,
  call: getBauprogramme,
  loading: getBauprogrammeLoading,
} = useGetBauprogramme();

onMounted(() => getBauprogramme({}));

const {
  call: createBauprogramm,
  loading: createBauprogrammLoading,
  error: createBauprogrammeError,
} = useCreateBauprogramm();

const handleCreate = async (bauprogrammCreateDTO: BauprogrammCreateDTO) => {
  await createBauprogramm({
    bauprogrammCreateDTO,
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

const handleUpdate = async (bauprogrammUpdateDTO: BauprogrammUpdateDTO) => {
  await updateBauprogramm({
    id: bauprogrammUpdateDTO.id,
    bauprogrammUpdateDTO: bauprogrammUpdateDTO,
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
  await getBauprogramme({
    pageNumber: dataTableOptions.value.page,
    pageSize: dataTableOptions.value.itemsPerPage,
  });
};
const onFailure = (msg: string) => {
  snackbarStore.push({ text: msg, color: STATUS_INDICATORS.ERROR });
};
</script>