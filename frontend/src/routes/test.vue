<template>
  <base-view :domain-key="domainKey">
    <crud-card
      ref="crudRef"
      :empty-item-template="EMPTY_ITEM_TEMPLATE"
      :loading="loading"
      :table-headers="headers"
      :domain-key="domainKey"
      :enable-actions="isAdmin"
      :items="testsData"
      :total-items="testsData?.length ?? 0"
      :items-per-page="dataTableOptions.itemsPerPage"
      expandable
      @delete="handleDelete"
      @create="handleCreate"
      @update="handleUpdate"
      @updated-options="handleUpdatedOptions"
    >
      <template #form="{ item, updateValidity, inputDisplayMode }">
        <test-form
          :model-value="item"
          :display-mode="inputDisplayMode"
          @is-valid="updateValidity"
        />
      </template>
      <template #[`item.booleanField`]="{ value }">
        <v-icon
          v-if="value"
          :icon="mdiCheck"
          color="success"
        />
        <v-icon
          v-else
          :icon="mdiClose"
          color="error"
        />
      </template>
    </crud-card>
  </base-view>
</template>

<script setup lang="ts">
import type { DataTableOptions } from "@/types/DataTableOptions";
import type { TableColumnHeader } from "@/types/TableColumnHeader";
import type { Test } from "@/types/Test";

import { mdiCheck, mdiClose } from "@mdi/js";
import { computed, onMounted, ref, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import TestForm from "@/components/TestForm.vue";
import {
  useCreateTest,
  useDeleteTest,
  useGetTests,
  useUpdateTest,
} from "@/composables/api/useTestApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import { STATUS_INDICATORS } from "@/constants";
import { useSnackbarStore } from "@/stores/snackbar";
import { Role } from "@/types/Role";

const { t } = useI18n();

const domainKey = "model.projekt.modelName";

const isAdmin = useHasAnyRole(Role.ADMIN);

const headers: TableColumnHeader<Test>[] = [
  { title: "String-Feld", value: "stringField", sortable: true },
  { title: "Nummern-Feld", value: "numberField" },
  { title: "Boolsches Feld", value: "booleanField", align: "center" },
];

const EMPTY_ITEM_TEMPLATE: Test = {
  stringField: undefined,
  numberField: undefined,
  booleanField: true,
};

const {
  data: testsData,
  call: getTests,
  loading: getTestsLoading,
} = useGetTests();

onMounted(() => getTests());

const {
  call: createTest,
  loading: createTestLoading,
  error: createTestError,
} = useCreateTest();

const handleCreate = async (test: Test) => {
  await createTest(test);
  if (!createTestError.value) {
    await onSuccess(t("common.message.created", [t(domainKey)]));
  } else {
    onFailure(t("common.message.createdError", [t(domainKey)]));
  }
};

const {
  call: updateTest,
  loading: updateTestLoading,
  error: updateTestError,
} = useUpdateTest();

const handleUpdate = async (test: Test) => {
  await updateTest(test);
  if (!updateTestError.value) {
    await onSuccess(t("common.message.updated", [t(domainKey)]));
  } else {
    onFailure(t("common.message.updatedError", [t(domainKey)]));
  }
};

const {
  call: deleteTest,
  loading: deleteTestLoading,
  error: deleteTestError,
} = useDeleteTest();

const handleDelete = async (id: string) => {
  await deleteTest(id);
  if (!deleteTestError.value) {
    await onSuccess(t("common.message.deleted", [t(domainKey)]));
  } else {
    onFailure(t("common.message.deletedError", [t(domainKey)]));
  }
};

const dataTableOptions = ref<DataTableOptions>({
  page: 1,
  itemsPerPage: 12,
  sortBy: [],
});

const handleUpdatedOptions = async (newOptions: DataTableOptions) => {
  dataTableOptions.value = newOptions;
  await getTests();
};

const loading = computed(
  () =>
    getTestsLoading.value ||
    createTestLoading.value ||
    updateTestLoading.value ||
    deleteTestLoading.value
);

const snackbarStore = useSnackbarStore();
const crudRef = useTemplateRef("crudRef");

const onSuccess = async (msg: string) => {
  snackbarStore.push({ text: msg, color: STATUS_INDICATORS.SUCCESS });
  if (crudRef.value) {
    crudRef.value.closeDialog();
  }
  await getTests();
};
const onFailure = (msg: string) => {
  snackbarStore.push({ text: msg, color: STATUS_INDICATORS.ERROR });
};
</script>
