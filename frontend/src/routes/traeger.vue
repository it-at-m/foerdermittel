<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <!-- @vue-generic {Partial<TraegerResponseDTO>} -->
      <crud-card
        ref="crudRef"
        v-model="dataTableOptions"
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="loading || baseViewLoading"
        :table-headers="headers"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :items="traeger?.content ?? []"
        :total-items="traeger?.page?.totalElements ?? 0"
        @delete="handleDelete"
        @create="handleCreate"
        @update="handleUpdate"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <traeger-form
            v-if="traegerFormContext"
            ref="traegerForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :traeger-form-context="traegerFormContext"
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { TraegerResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import TraegerForm from "@/components/forms/TraegerForm.vue";
import {
  useCreateTraeger,
  useDeleteTraeger,
  useGetTraeger1,
  useGetTraegerFormContext,
  useUpdateTraeger,
} from "@/composables/api/useTraegerApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import usePagination from "@/composables/usePagination";
import { Role } from "@/types/Role";

const domainKey = "model.traeger.modelName";

const isAdmin = useHasAnyRole(Role.ADMIN);

const { t } = useI18n();

const headers: DataTableHeader<Partial<TraegerResponseDTO>>[] = [
  {
    title: t("model.traeger.kurzform"),
    value: "kurzform",
    align: "center",
    width: 100,
  },
  { title: t("model.traeger.bezeichnung"), value: "bezeichnung" },
];

const EMPTY_ITEM_TEMPLATE: Partial<TraegerResponseDTO> = {
  kurzform: undefined,
  bezeichnung: "",
};

const {
  data: traeger,
  call: getTraeger,
  loading: getTraegerLoading,
} = useGetTraeger1();

const {
  data: traegerFormContext,
  call: getTraegerFormContext,
  loading: getTraegerFormContextLoading,
} = useGetTraegerFormContext();

type TraegerFormType = InstanceType<typeof TraegerForm>;
const traegerFormRef = useTemplateRef<TraegerFormType>("traegerForm");

const { dataTableOptions, onSuccess, onFailure } = usePagination(
  computed(() => traeger.value?.page?.totalPages),
  getTraeger,
  isAdmin,
  getTraegerFormContext,
  () => traegerFormRef.value?.validate()
);

const {
  call: createTraeger,
  loading: createTraegerLoading,
  error: createTraegerError,
} = useCreateTraeger();

const handleCreate = async (traegerCreateDTO: Partial<TraegerResponseDTO>) => {
  // TODO: some type checking improvements
  const model = traegerCreateDTO as TraegerResponseDTO;
  await createTraeger({
    traegerCreateDTO: model,
  });
  if (!createTraegerError.value) {
    await onSuccess(t("common.message.created", [t(domainKey)]));
  } else {
    onFailure(t("common.message.createdError", [t(domainKey)]));
  }
};

const {
  call: updateTraeger,
  loading: updateTraegerLoading,
  error: updateTraegerError,
} = useUpdateTraeger();

const handleUpdate = async (traegerUpdateDTO: Partial<TraegerResponseDTO>) => {
  // TODO: some type checking improvements
  const model = traegerUpdateDTO as TraegerResponseDTO;
  await updateTraeger({
    id: model.id,
    traegerUpdateDTO: model,
  });
  if (!updateTraegerError.value) {
    await onSuccess(t("common.message.updated", [t(domainKey)]));
  } else {
    onFailure(t("common.message.updatedError", [t(domainKey)]));
  }
};

const {
  call: deleteTraeger,
  loading: deleteTraegerLoading,
  error: deleteTraegerError,
} = useDeleteTraeger();

const handleDelete = async (id: string) => {
  await deleteTraeger({
    id,
  });
  if (!deleteTraegerError.value) {
    await onSuccess(t("common.message.deleted", [t(domainKey)]));
  } else {
    onFailure(t("common.message.deletedError", [t(domainKey)]));
  }
};

const loading = computed(
  () =>
    getTraegerLoading.value ||
    getTraegerFormContextLoading.value ||
    createTraegerLoading.value ||
    updateTraegerLoading.value ||
    deleteTraegerLoading.value
);
</script>
