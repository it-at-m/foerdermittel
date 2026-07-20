<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <!-- @vue-generic {Partial<FoerderbereichResponseDTO>} -->
      <crud-card
        ref="crudRef"
        v-model="dataTableOptions"
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="loading || baseViewLoading"
        :table-headers="headers"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :items="foerderbereiche?.content ?? []"
        :total-items="foerderbereiche?.page?.totalElements ?? 0"
        @delete="handleDelete"
        @create="handleCreate"
        @update="handleUpdate"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <foerderbereich-form
            v-if="foerderbereichFormContext"
            ref="foerderbereichForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :foerderbereich-form-context="foerderbereichFormContext"
            @is-valid="updateValidity"
          />
        </template>
        <template #[`item.finanzausgleich`]="{ item }">
          <v-icon
            v-if="item.finanzausgleich"
            :icon="mdiCheck"
          />
        </template>
        <template #[`item.jahresstatistik`]="{ item }">
          <v-icon
            v-if="item.jahresstatistik"
            :icon="mdiCheck"
          />
        </template>
        <template #[`item.kindergarten`]="{ item }">
          <v-icon
            v-if="item.kindergarten"
            :icon="mdiCheck"
          />
        </template>
        <template #[`item.nichtRelevant`]="{ item }">
          <v-icon
            v-if="item.nichtRelevant"
            :icon="mdiCheck"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { FoerderbereichResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { mdiCheck } from "@mdi/js";
import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import FoerderbereichForm from "@/components/forms/FoerderbereichForm.vue";
import {
  useCreateFoerderbereich,
  useDeleteFoerderbereich,
  useGetFoerderbereiche,
  useGetFoerderbereichFormContext,
  useUpdateFoerderbereich,
} from "@/composables/api/useFoerderbereichApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import usePagination from "@/composables/usePagination";
import { Role } from "@/types/Role";

const domainKey = "model.foerderbereich.modelName";

const isAdmin = useHasAnyRole(Role.ADMIN);

const { t } = useI18n();

const headers: DataTableHeader<Partial<FoerderbereichResponseDTO>>[] = [
  {
    title: t("model.foerderbereich.fb"),
    value: "fb",
    align: "center",
    width: 120,
  },
  {
    title: t("model.foerderbereich.bezeichnung"),
    value: "bezeichnung",
    width: 300,
    minWidth: 100,
  },
  {
    title: t("model.foerderbereich.finanzausgleich"),
    value: "finanzausgleich",
    align: "center",
    width: 100,
    minWidth: 80,
  },
  {
    title: t("model.foerderbereich.jahresstatistik"),
    value: "jahresstatistik",
    align: "center",
    width: 100,
    minWidth: 80,
  },
  {
    title: t("model.foerderbereich.kindergarten"),
    value: "kindergarten",
    align: "center",
    width: 100,
    minWidth: 80,
  },
  {
    title: t("model.foerderbereich.nichtRelevant"),
    value: "nichtRelevant",
    align: "center",
    width: 100,
    minWidth: 80,
  },
];

const EMPTY_ITEM_TEMPLATE: Partial<FoerderbereichResponseDTO> = {
  fb: undefined,
  bezeichnung: "",
  finanzausgleich: false,
  jahresstatistik: false,
  kindergarten: false,
  nichtRelevant: false,
};

const {
  data: foerderbereiche,
  call: getFoerderbereiche,
  loading: getFoerderbereicheLoading,
} = useGetFoerderbereiche();

const {
  data: foerderbereichFormContext,
  call: getFoerderbereichFormContext,
  loading: getFoerderbereichFormContextLoading,
} = useGetFoerderbereichFormContext();

type FoerderbereichFormType = InstanceType<typeof FoerderbereichForm>;
const foerderbereichFormRef =
  useTemplateRef<FoerderbereichFormType>("foerderbereichForm");

const { dataTableOptions, onSuccess, onFailure } = usePagination(
  computed(() => foerderbereiche.value?.page?.totalPages),
  getFoerderbereiche,
  isAdmin,
  getFoerderbereichFormContext,
  () => foerderbereichFormRef.value?.validate()
);

const {
  call: createFoerderbereich,
  loading: createFoerderbereichLoading,
  error: createFoerderbereichError,
} = useCreateFoerderbereich();

const handleCreate = async (
  foerderbereichCreateDTO: Partial<FoerderbereichResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = foerderbereichCreateDTO as FoerderbereichResponseDTO;
  await createFoerderbereich({
    foerderbereichCreateDTO: model,
  });
  if (!createFoerderbereichError.value) {
    await onSuccess(t("common.message.created", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.createdError", [t(domainKey)]));
  }
};

const {
  call: updateFoerderbereich,
  loading: updateFoerderbereichLoading,
  error: updateFoerderbereichError,
} = useUpdateFoerderbereich();

const handleUpdate = async (
  foerderbereichUpdateDTO: Partial<FoerderbereichResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = foerderbereichUpdateDTO as FoerderbereichResponseDTO;
  await updateFoerderbereich({
    id: model.id,
    foerderbereichUpdateDTO: model,
  });
  if (!updateFoerderbereichError.value) {
    await onSuccess(t("common.message.updated", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.updatedError", [t(domainKey)]));
  }
};

const {
  call: deleteFoerderbereich,
  loading: deleteFoerderbereichLoading,
  error: deleteFoerderbereichError,
} = useDeleteFoerderbereich();

const handleDelete = async (id: string) => {
  await deleteFoerderbereich({
    id,
  });
  if (!deleteFoerderbereichError.value) {
    await onSuccess(t("common.message.deleted", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.deletedError", [t(domainKey)]));
  }
};

const loading = computed(
  () =>
    getFoerderbereicheLoading.value ||
    getFoerderbereichFormContextLoading.value ||
    createFoerderbereichLoading.value ||
    updateFoerderbereichLoading.value ||
    deleteFoerderbereichLoading.value
);
</script>
