<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <!-- @vue-generic {Partial<HauptabschnittResponseDTO>} -->
      <crud-card
        ref="crudRef"
        v-model="dataTableOptions"
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="loading || baseViewLoading"
        :table-headers="headers"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :items="hauptabschnitte?.content ?? []"
        :total-items="hauptabschnitte?.page?.totalElements ?? 0"
        @delete="handleDelete"
        @create="handleCreate"
        @update="handleUpdate"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <hauptabschnitt-form
            v-if="hauptabschnittFormContext"
            ref="hauptabschnittForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :hauptabschnitt-form-context="hauptabschnittFormContext"
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { HauptabschnittResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import HauptabschnittForm from "@/components/forms/HauptabschnittForm.vue";
import {
  useCreateHauptabschnitt,
  useDeleteHauptabschnitt,
  useGetHauptabschnitte,
  useGetHauptabschnittFormContext,
  useUpdateHauptabschnitt,
} from "@/composables/api/useHauptabschnittApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import usePagination from "@/composables/usePagination";
import { Role } from "@/types/Role";

const domainKey = "model.hauptabschnitt.modelName";

const isAdmin = useHasAnyRole(Role.ADMIN);

const { t } = useI18n();

const headers: DataTableHeader<Partial<HauptabschnittResponseDTO>>[] = [
  {
    title: t("model.hauptabschnitt.ha"),
    value: "ha",
    align: "center",
    width: 100,
  },
  { title: t("model.hauptabschnitt.bezeichnung"), value: "bezeichnung" },
];

const EMPTY_ITEM_TEMPLATE: Partial<HauptabschnittResponseDTO> = {
  ha: undefined,
  bezeichnung: "",
};

const {
  data: hauptabschnitte,
  call: getHauptabschnitte,
  loading: getHauptabschnitteLoading,
} = useGetHauptabschnitte();

const {
  data: hauptabschnittFormContext,
  call: getHauptabschnittFormContext,
  loading: getHauptabschnittFormContextLoading,
} = useGetHauptabschnittFormContext();

type HauptabschnittFormType = InstanceType<typeof HauptabschnittForm>;
const hauptabschnittFormRef =
  useTemplateRef<HauptabschnittFormType>("hauptabschnittForm");

const { dataTableOptions, onSuccess, onFailure } = usePagination(
  computed(() => hauptabschnitte.value?.page?.totalPages),
  getHauptabschnitte,
  isAdmin,
  getHauptabschnittFormContext,
  () => hauptabschnittFormRef.value?.validate()
);

const {
  call: createHauptabschnitt,
  loading: createHauptabschnittLoading,
  error: createHauptabschnitteError,
} = useCreateHauptabschnitt();

const handleCreate = async (
  hauptabschnittCreateDTO: Partial<HauptabschnittResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = hauptabschnittCreateDTO as HauptabschnittResponseDTO;
  await createHauptabschnitt({
    hauptabschnittCreateDTO: model,
  });
  if (!createHauptabschnitteError.value) {
    await onSuccess(t("common.message.created", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.createdError", [t(domainKey)]));
  }
};

const {
  call: updateHauptabschnitt,
  loading: updateHauptabschnittLoading,
  error: updateHauptabschnitteError,
} = useUpdateHauptabschnitt();

const handleUpdate = async (
  hauptabschnittUpdateDTO: Partial<HauptabschnittResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = hauptabschnittUpdateDTO as HauptabschnittResponseDTO;
  await updateHauptabschnitt({
    id: model.id,
    hauptabschnittUpdateDTO: model,
  });
  if (!updateHauptabschnitteError.value) {
    await onSuccess(t("common.message.updated", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.updatedError", [t(domainKey)]));
  }
};

const {
  call: deleteHauptabschnitt,
  loading: deleteHauptabschnittLoading,
  error: deleteHauptabschnitteError,
} = useDeleteHauptabschnitt();

const handleDelete = async (id: string) => {
  await deleteHauptabschnitt({
    id,
  });
  if (!deleteHauptabschnitteError.value) {
    await onSuccess(t("common.message.deleted", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.deletedError", [t(domainKey)]));
  }
};

const loading = computed(
  () =>
    getHauptabschnitteLoading.value ||
    getHauptabschnittFormContextLoading.value ||
    createHauptabschnittLoading.value ||
    updateHauptabschnittLoading.value ||
    deleteHauptabschnittLoading.value
);
</script>
