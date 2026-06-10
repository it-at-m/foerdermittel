<template>
  <base-view domain-key="model.projekt.modelName">
    <template #default="{ domainKey }">
      <crud-card
        ref="crudRef"
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="
          getTestsLoading ||
          createTestLoading ||
          updateTestLoading ||
          deleteTestLoading
        "
        :table-headers="headers"
        :domain-key="domainKey ?? ''"
        :enable-actions="isAdmin"
        :items="testsData"
        :total-items="testsData?.length ?? 0"
        :items-per-page="4"
        expandable
        @delete="handleDelete"
        @create="handleCreate"
        @update="handleUpdate"
      >
        <template
          #form="{ item, updateItem, updateValidity, isEditing, readOnly }"
        >
          <test-form
            :model-value="item"
            :readonly="readOnly || createTestLoading || updateTestLoading"
            :is-edit="isEditing"
            @update:model-value="updateItem"
            @is-valid="updateValidity"
          />
        </template>
        <template #[`item.booleanField`]="{ value }">
          <v-checkbox-btn
            readonly
            hide-details
            :model-value="value"
            class="pointer-events-none ml-n3"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { TableColumnHeader } from "@/types/TableColumnHeader";
import type { Test } from "@/types/Test";

import { onMounted, useTemplateRef } from "vue";
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

const isAdmin = useHasAnyRole(Role.ADMIN);

const headers: TableColumnHeader<Test>[] = [
  { title: "String-Feld", value: "stringField", sortable: true },
  { title: "Nummern-Feld", value: "numberField" },
  { title: "Boolsches Feld", value: "booleanField" },
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

const {
  call: createTest,
  loading: createTestLoading,
  error: createTestError,
} = useCreateTest();

const {
  call: updateTest,
  loading: updateTestLoading,
  error: updateTestError,
} = useUpdateTest();

const {
  call: deleteTest,
  loading: deleteTestLoading,
  error: deleteTestError,
} = useDeleteTest();

onMounted(async () => await getTests());

const handleCreate = async (test: Test) => {
  await createTest(test);
  if (!createTestError.value) {
    await onSuccess(
      t("common.message.created", [t("model.projekt.modelName")])
    );
  }
};

const handleUpdate = async (test: Test) => {
  await updateTest(test);
  if (!updateTestError.value) {
    await onSuccess(
      t("common.message.updated", [t("model.projekt.modelName")])
    );
  }
};

const handleDelete = async (id: string) => {
  await deleteTest(id);
  if (!deleteTestError.value) {
    await onSuccess(
      t("common.message.deleted", [t("model.projekt.modelName")])
    );
  }
};

const snackbarStore = useSnackbarStore();
const crudRef = useTemplateRef("crudRef");
const onSuccess = async (msg: string) => {
  snackbarStore.push({ text: msg, color: STATUS_INDICATORS.SUCCESS });
  if (crudRef.value) {
    crudRef.value.closeDialog();
  }
  await getTests();
};
</script>
