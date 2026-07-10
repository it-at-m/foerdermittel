<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <!-- @vue-generic {Partial<ReferatResponseDTO>} -->
      <crud-card
        ref="crudRef"
        v-model="dataTableOptions"
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="loading || baseViewLoading"
        :table-headers="headers"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :items="referate?.content ?? []"
        :total-items="referate?.page?.totalElements ?? 0"
        @delete="handleDelete"
        @create="handleCreate"
        @update="handleUpdate"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <referat-form
            v-if="referatFormContext"
            ref="referatForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :referat-form-context="referatFormContext"
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { ReferatResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import ReferatForm from "@/components/forms/ReferatForm.vue";
import {
  useCreateReferat,
  useDeleteReferat,
  useGetReferate,
  useGetReferatFormContext,
  useUpdateReferat,
} from "@/composables/api/useReferatApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import usePagination from "@/composables/usePagination";
import { Role } from "@/types/Role";

const domainKey = "model.referat.modelName";

const isAdmin = useHasAnyRole(Role.ADMIN);

const { t } = useI18n();

const headers: DataTableHeader<Partial<ReferatResponseDTO>>[] = [
  {
    title: t("model.referat.refnr"),
    value: "refnr",
    align: "center",
    width: 120,
  },
  { title: t("model.referat.bezeichnung"), value: "bezeichnung" },
];

const EMPTY_ITEM_TEMPLATE: Partial<ReferatResponseDTO> = {
  refnr: undefined,
  bezeichnung: "",
};

const {
  data: referate,
  call: getReferate,
  loading: getReferateLoading,
} = useGetReferate();

const {
  data: referatFormContext,
  call: getReferatFormContext,
  loading: getReferatFormContextLoading,
} = useGetReferatFormContext();

type ReferatFormType = InstanceType<typeof ReferatForm>;
const referatFormRef = useTemplateRef<ReferatFormType>("referatForm");

const { dataTableOptions, onSuccess, onFailure } = usePagination(
  computed(() => referate.value?.page?.totalPages),
  getReferate,
  isAdmin,
  getReferatFormContext,
  () => referatFormRef.value?.validate()
);

const {
  call: createReferat,
  loading: createReferatLoading,
  error: createReferateError,
} = useCreateReferat();

const handleCreate = async (referatCreateDTO: Partial<ReferatResponseDTO>) => {
  // TODO: some type checking improvements
  const model = referatCreateDTO as ReferatResponseDTO;
  await createReferat({
    referatCreateDTO: model,
  });
  if (!createReferateError.value) {
    await onSuccess(t("common.message.created", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.createdError", [t(domainKey)]));
  }
};

const {
  call: updateReferat,
  loading: updateReferatLoading,
  error: updateReferatError,
} = useUpdateReferat();

const handleUpdate = async (referatUpdateDTO: Partial<ReferatResponseDTO>) => {
  // TODO: some type checking improvements
  const model = referatUpdateDTO as ReferatResponseDTO;
  await updateReferat({
    id: model.id,
    referatUpdateDTO: model,
  });
  if (!updateReferatError.value) {
    await onSuccess(t("common.message.updated", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.updatedError", [t(domainKey)]));
  }
};

const {
  call: deleteReferat,
  loading: deleteReferatLoading,
  error: deleteReferatError,
} = useDeleteReferat();

const handleDelete = async (id: string) => {
  await deleteReferat({
    id,
  });
  if (!deleteReferatError.value) {
    await onSuccess(t("common.message.deleted", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.deletedError", [t(domainKey)]));
  }
};

const loading = computed(
  () =>
    getReferateLoading.value ||
    getReferatFormContextLoading.value ||
    createReferatLoading.value ||
    updateReferatLoading.value ||
    deleteReferatLoading.value
);
</script>
