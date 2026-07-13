<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <!-- @vue-generic {Partial<StichwortbereichResponseDTO>} -->
      <crud-card
        ref="crudRef"
        v-model="dataTableOptions"
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="loading || baseViewLoading"
        :table-headers="headers"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :items="stichwortbereiche?.content ?? []"
        :total-items="stichwortbereiche?.page?.totalElements ?? 0"
        @delete="handleDelete"
        @create="handleCreate"
        @update="handleUpdate"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <stichwortbereich-form
            v-if="stichwortbereichFormContext"
            ref="stichwortbereichForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :stichwortbereich-form-context="stichwortbereichFormContext"
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { StichwortbereichResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import StichwortbereichForm from "@/components/forms/StichwortbereichForm.vue";
import {
  useCreateStichwortbereich,
  useDeleteStichwortbereich,
  useGetStichwortbereiche,
  useGetStichwortbereichFormContext,
  useUpdateStichwortbereich,
} from "@/composables/api/useStichwortbereichApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import usePagination from "@/composables/usePagination";
import { Role } from "@/types/Role";

const domainKey = "model.stichwortbereich.modelName";

const isAdmin = useHasAnyRole(Role.ADMIN);

const { t } = useI18n();

const headers: DataTableHeader<Partial<StichwortbereichResponseDTO>>[] = [
  {
    title: t("model.stichwortbereich.bereich"),
    value: "bereich",
    align: "center",
    width: 100,
  },
  { title: t("model.stichwortbereich.bezeichnung"), value: "bezeichnung" },
];

const EMPTY_ITEM_TEMPLATE: Partial<StichwortbereichResponseDTO> = {
  bereich: undefined,
  bezeichnung: "",
};

const {
  data: stichwortbereiche,
  call: getStichwortbereiche,
  loading: getStichwortbereicheLoading,
} = useGetStichwortbereiche();

const {
  data: stichwortbereichFormContext,
  call: getStichwortbereichFormContext,
  loading: getStichwortbereichFormContextLoading,
} = useGetStichwortbereichFormContext();

type StichwortbereichFormType = InstanceType<typeof StichwortbereichForm>;
const stichwortbereichFormRef =
  useTemplateRef<StichwortbereichFormType>("stichwortbereichForm");

const { dataTableOptions, onSuccess, onFailure } = usePagination(
  computed(() => stichwortbereiche.value?.page?.totalPages),
  getStichwortbereiche,
  isAdmin,
  getStichwortbereichFormContext,
  () => StichwortbereichFormRef.value?.validate()
);

const {
  call: createStichwortbereich,
  loading: createStichwortbereichLoading,
  error: createStichwortbereicheError,
} = useCreateStichwortbereich();

const handleCreate = async (
  stichwortbereichCreateDTO: Partial<StichwortbereichResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = stichwortbereichCreateDTO as StichwortbereichResponseDTO;
  await createStichwortbereich({
    stichwortbereichCreateDTO: model,
  });
  if (!createStichwortbereicheError.value) {
    await onSuccess(t("common.message.created", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.createdError", [t(domainKey)]));
  }
};

const {
  call: updateStichwortbereich,
  loading: updateStichwortbereichLoading,
  error: updateStichwortbereicheError,
} = useUpdateStichwortbereich();

const handleUpdate = async (
  stichwortbereichUpdateDTO: Partial<StichwortbereichResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = stichwortbereichUpdateDTO as StichwortbereichResponseDTO;
  await updateStichwortbereich({
    id: model.id,
    stichwortbereichUpdateDTO: model,
  });
  if (!updateStichwortbereicheError.value) {
    await onSuccess(t("common.message.updated", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.updatedError", [t(domainKey)]));
  }
};

const {
  call: deleteStichwortbereich,
  loading: deleteStichwortbereichLoading,
  error: deleteStichwortbereicheError,
} = useDeleteStichwortbereich();

const handleDelete = async (id: string) => {
  await deleteStichwortbereich({
    id,
  });
  if (!deleteStichwortbereicheError.value) {
    await onSuccess(t("common.message.deleted", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.deletedError", [t(domainKey)]));
  }
};

const loading = computed(
  () =>
    getStichwortbereicheLoading.value ||
    getStichwortbereichFormContextLoading.value ||
    createStichwortbereichLoading.value ||
    updateStichwortbereichLoading.value ||
    deleteStichwortbereichLoading.value
);
</script>
