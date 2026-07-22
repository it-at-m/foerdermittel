<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <!-- @vue-generic {Partial<ListennameResponseDTO>} -->
      <crud-card
        ref="crudRef"
        v-model="dataTableOptions"
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="loading || baseViewLoading"
        :table-headers="headers"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :items="listennamen?.content ?? []"
        :total-items="listennamen?.page?.totalElements ?? 0"
        @delete="handleDelete"
        @create="handleCreate"
        @update="handleUpdate"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <listenname-form
            v-if="listennameFormContext"
            ref="listennameForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :listenname-form-context="listennameFormContext"
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { ListennameResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import ListennameForm from "@/components/forms/ListennameForm.vue";
import {
  useCreateListenname,
  useDeleteListenname,
  useGetListennamen,
  useGetListennameFormContext,
  useUpdateListenname,
} from "@/composables/api/useListennameApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import usePagination from "@/composables/usePagination";
import { Role } from "@/types/Role";

const domainKey = "model.listenname.modelName";

const isAdmin = useHasAnyRole(Role.ADMIN);

const { t } = useI18n();

const headers: DataTableHeader<Partial<ListennameResponseDTO>>[] = [
  {
    title: t("model.listenname.kurzbez"),
    value: "kurzbez",
    align: "center",
    width: 100,
  },
  { title: t("model.listenname.bezeichnung"), value: "bezeichnung" },
];

const EMPTY_ITEM_TEMPLATE: Partial<ListennameResponseDTO> = {
  kurzbez: undefined,
  bezeichnung: "",
};

const {
  data: listennamen,
  call: getlistennamen,
  loading: getlistennamenLoading,
} = useGetListennamen();

const {
  data: listennameFormContext,
  call: getlistennameFormContext,
  loading: getlistennameFormContextLoading,
} = useGetListennameFormContext();

type listennameFormType = InstanceType<typeof ListennameForm>;
const listennameFormRef =
  useTemplateRef<listennameFormType>("listennameForm");

const { dataTableOptions, onSuccess, onFailure } = usePagination(
  computed(() => listennamen.value?.page?.totalPages),
  getlistennamen,
  isAdmin,
  getlistennameFormContext,
  () => listennameFormRef.value?.validate()
);

const {
  call: createlistenname,
  loading: createlistennameLoading,
  error: createlistennamenError,
} = useCreateListenname();

const handleCreate = async (
  listennameCreateDTO: Partial<ListennameResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = listennameCreateDTO as ListennameResponseDTO;
  await createlistenname({
    listennameCreateDTO: model,
  });
  if (!createlistennamenError.value) {
    await onSuccess(t("common.message.created", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.createdError", [t(domainKey)]));
  }
};

const {
  call: updatelistenname,
  loading: updatelistennameLoading,
  error: updatelistennamenError,
} = useUpdateListenname();

const handleUpdate = async (
  listennameUpdateDTO: Partial<ListennameResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = listennameUpdateDTO as ListennameResponseDTO;
  await updatelistenname({
    id: model.id,
    listennameUpdateDTO: model,
  });
  if (!updatelistennamenError.value) {
    await onSuccess(t("common.message.updated", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.updatedError", [t(domainKey)]));
  }
};

const {
  call: deletelistenname,
  loading: deletelistennameLoading,
  error: deletelistennamenError,
} = useDeleteListenname();

const handleDelete = async (id: string) => {
  await deletelistenname({
    id,
  });
  if (!deletelistennamenError.value) {
    await onSuccess(t("common.message.deleted", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.deletedError", [t(domainKey)]));
  }
};

const loading = computed(
  () =>
    getlistennamenLoading.value ||
    getlistennameFormContextLoading.value ||
    createlistennameLoading.value ||
    updatelistennameLoading.value ||
    deletelistennameLoading.value
);
</script>
