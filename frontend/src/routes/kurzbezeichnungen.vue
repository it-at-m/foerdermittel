<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <!-- @vue-generic {Partial<KurzbezeichnungResponseDTO>} -->
      <crud-card
        ref="crudRef"
        v-model="dataTableOptions"
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="loading || baseViewLoading"
        :table-headers="headers"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :items="kurzbezeichnungen?.content ?? []"
        :total-items="kurzbezeichnungen?.page?.totalElements ?? 0"
        @delete="handleDelete"
        @create="handleCreate"
        @update="handleUpdate"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <kurzbezeichnung-form
            v-if="kurzbezeichnungFormContext"
            ref="kurzbezeichnungForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :kurzbezeichnung-form-context="kurzbezeichnungFormContext"
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { KurzbezeichnungResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import KurzbezeichnungForm from "@/components/forms/KurzbezeichnungForm.vue";
import {
  useCreateKurzbezeichnung,
  useDeleteKurzbezeichnung,
  useGetKurzbezeichnungen,
  useGetKurzbezeichnungFormContext,
  useUpdateKurzbezeichnung,
} from "@/composables/api/useKurzbezeichnungApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import usePagination from "@/composables/usePagination";
import { Role } from "@/types/Role";

const domainKey = "model.kurzbezeichnung.modelName";

const isAdmin = useHasAnyRole(Role.ADMIN);

const { t } = useI18n();

const headers: DataTableHeader<Partial<KurzbezeichnungResponseDTO>>[] = [
  {
    title: t("model.kurzbezeichnung.kurzbez"),
    value: "kurzbez",
    align: "center",
    width: 150,
  },
  { title: t("model.kurzbezeichnung.bezeichnung"), value: "bezeichnung" },
];

const EMPTY_ITEM_TEMPLATE: Partial<KurzbezeichnungResponseDTO> = {
  kurzbez: undefined,
  bezeichnung: "",
};

const {
  data: kurzbezeichnungen,
  call: getKurzbezeichnungen,
  loading: getKurzbezeichnungenLoading,
} = useGetKurzbezeichnungen();

const {
  data: kurzbezeichnungFormContext,
  call: getKurzbezeichnungFormContext,
  loading: getKurzbezeichnungFormContextLoading,
} = useGetKurzbezeichnungFormContext();

type KurzbezeichnungFormType = InstanceType<typeof KurzbezeichnungForm>;
const kurzbezeichnungFormRef = useTemplateRef<KurzbezeichnungFormType>(
  "kurzbezeichnungForm");

const { dataTableOptions, onSuccess, onFailure } = usePagination(
  computed(() => kurzbezeichnungen.value?.page?.totalPages),
  getKurzbezeichnungen,
  isAdmin,
  getKurzbezeichnungFormContext,
  () => kurzbezeichnungFormRef.value?.validate()
);

const {
  call: createKurzbezeichnung,
  loading: createKurzbezeichnungLoading,
  error: createKurzbezeichnungenError,
} = useCreateKurzbezeichnung();

const handleCreate = async (
  kurzbezeichnungCreateDTO: Partial<KurzbezeichnungResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = kurzbezeichnungCreateDTO as KurzbezeichnungResponseDTO;
  await createKurzbezeichnung({
    kurzbezeichnungCreateDTO: model,
  });
  if (!createKurzbezeichnungenError.value) {
    await onSuccess(t("common.message.created", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.createdError", [t(domainKey)]));
  }
};

const {
  call: updateKurzbezeichnung,
  loading: updateKurzbezeichnungLoading,
  error: updateKurzbezeichnungenError,
} = useUpdateKurzbezeichnung();

const handleUpdate = async (
  kurzbezeichnungUpdateDTO: Partial<KurzbezeichnungResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = kurzbezeichnungUpdateDTO as KurzbezeichnungResponseDTO;
  await updateKurzbezeichnung({
    id: model.id,
    kurzbezeichnungUpdateDTO: model,
  });
  if (!updateKurzbezeichnungenError.value) {
    await onSuccess(t("common.message.updated", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.updatedError", [t(domainKey)]));
  }
};

const {
  call: deleteKurzbezeichnung,
  loading: deleteKurzbezeichnungLoading,
  error: deleteKurzbezeichnungenError,
} = useDeleteKurzbezeichnung();

const handleDelete = async (id: string) => {
  await deleteKurzbezeichnung({
    id,
  });
  if (!deleteKurzbezeichnungenError.value) {
    await onSuccess(t("common.message.deleted", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.deletedError", [t(domainKey)]));
  }
};

const loading = computed(
  () =>
    getKurzbezeichnungenLoading.value ||
    getKurzbezeichnungFormContextLoading.value ||
    createKurzbezeichnungLoading.value ||
    updateKurzbezeichnungLoading.value ||
    deleteKurzbezeichnungLoading.value
);
</script>
