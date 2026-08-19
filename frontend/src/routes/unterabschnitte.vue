<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <!-- @vue-generic {Partial<UnterabschnittResponseDTO>} -->
      <crud-card
        ref="crudRef"
        v-model="dataTableOptions"
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="loading || baseViewLoading"
        :table-headers="headers"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :items="unterabschnitte?.content ?? []"
        :total-items="unterabschnitte?.page?.totalElements ?? 0"
        @delete="handleDelete"
        @create="handleCreate"
        @update="handleUpdate"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <unterabschnitt-form
            v-if="unterabschnittFormContext"
            ref="unterabschnittForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :unterabschnitt-form-context="unterabschnittFormContext"
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { UnterabschnittResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import UnterabschnittForm from "@/components/forms/UnterabschnittForm.vue";
import {
  useCreateUnterabschnitt,
  useDeleteUnterabschnitt,
  useGetUnterabschnitte,
  useGetUnterabschnittFormContext,
  useUpdateUnterabschnitt,
} from "@/composables/api/useUnterabschnittApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import usePagination from "@/composables/usePagination";
import { Role } from "@/types/Role";

const domainKey = "model.unterabschnitt.modelName";

const isAdmin = useHasAnyRole(Role.ADMIN);

const { t } = useI18n();

const headers: DataTableHeader<Partial<UnterabschnittResponseDTO>>[] = [
  {
    title: t("model.unterabschnitt.hasHa"),
    value: "hasHa",
    align: "center",
    width: 80,
  },
  {
    title: t("model.unterabschnitt.haBezeichnung"),
    value: "haBezeichnung",
    align: "start",
    width: 150,
  },
  {
    title: t("model.unterabschnitt.ua"),
    value: "ua",
    align: "center",
    width: 80,
  },
  {
    title: t("model.unterabschnitt.bezeichnung"),
    value: "bezeichnung",
    align: "start",
    width: 350,
  },
];

const EMPTY_ITEM_TEMPLATE: Partial<UnterabschnittResponseDTO> = {
  ua: undefined,
  bezeichnung: "",
  hasHa: "",
  haBezeichnung: "",
};

const {
  data: unterabschnitte,
  call: getUnterabschnitte,
  loading: getUnterabschnitteLoading,
} = useGetUnterabschnitte();

const {
  data: unterabschnittFormContext,
  call: getUnterabschnittFormContext,
  loading: getUnterabschnittFormContextLoading,
} = useGetUnterabschnittFormContext();

type UnterabschnittFormType = InstanceType<typeof UnterabschnittForm>;

const unterabschnittFormRef =
  useTemplateRef<UnterabschnittFormType>("unterabschnittForm");

const { dataTableOptions, onSuccess, onFailure } = usePagination(
  computed(() => unterabschnitte.value?.page?.totalPages),
  getUnterabschnitte,
  isAdmin,
  getUnterabschnittFormContext,
  () => unterabschnittFormRef.value?.validate()
);

const {
  call: createUnterabschnitt,
  loading: createUnterabschnittLoading,
  error: createUnterabschnitteError,
} = useCreateUnterabschnitt();

const handleCreate = async (
  unterabschnittCreateDTO: Partial<UnterabschnittResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = unterabschnittCreateDTO as UnterabschnittResponseDTO;

  await createUnterabschnitt({
    unterabschnittCreateDTO: model,
  });

  if (!createUnterabschnitteError.value) {
    await onSuccess(t("common.message.created", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.createdError", [t(domainKey)]));
  }
};

const {
  call: updateUnterabschnitt,
  loading: updateUnterabschnittLoading,
  error: updateUnterabschnitteError,
} = useUpdateUnterabschnitt();

const handleUpdate = async (
  unterabschnittUpdateDTO: Partial<UnterabschnittResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = unterabschnittUpdateDTO as UnterabschnittResponseDTO;

  await updateUnterabschnitt({
    id: model.id,
    unterabschnittUpdateDTO: model,
  });

  if (!updateUnterabschnitteError.value) {
    await onSuccess(t("common.message.updated", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.updatedError", [t(domainKey)]));
  }
};

const {
  call: deleteUnterabschnitt,
  loading: deleteUnterabschnittLoading,
  error: deleteUnterabschnitteError,
} = useDeleteUnterabschnitt();

const handleDelete = async (id: string) => {
  await deleteUnterabschnitt({
    id,
  });

  if (!deleteUnterabschnitteError.value) {
    await onSuccess(t("common.message.deleted", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.deletedError", [t(domainKey)]));
  }
};

const loading = computed(
  () =>
    getUnterabschnitteLoading.value ||
    getUnterabschnittFormContextLoading.value ||
    createUnterabschnittLoading.value ||
    updateUnterabschnittLoading.value ||
    deleteUnterabschnittLoading.value
);
</script>
