<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <!-- @vue-generic {Partial<StadtbezirkslisteResponseDTO>} -->
      <crud-card
        ref="crudRef"
        v-model="dataTableOptions"
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="loading || baseViewLoading"
        :table-headers="headers"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :items="stadtbezirkslisten?.content ?? []"
        :total-items="stadtbezirkslisten?.page?.totalElements ?? 0"
        @delete="handleDelete"
        @create="handleCreate"
        @update="handleUpdate"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <stadtbezirksliste-form
            v-if="stadtbezirkslisteFormContext"
            ref="stadtbezirkslisteForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :stadtbezirksliste-form-context="stadtbezirkslisteFormContext"
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { StadtbezirkslisteResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import StadtbezirkslisteForm from "@/components/forms/StadtbezirkslisteForm.vue";
import {
  useCreateStadtbezirksliste,
  useDeleteStadtbezirksliste,
  useGetStadtbezirkslisteFormContext,
  useGetStadtbezirkslisten,
  useUpdateStadtbezirksliste,
} from "@/composables/api/useStadtbezirkslisteApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import usePagination from "@/composables/usePagination";
import { Role } from "@/types/Role";

const domainKey = "model.stadtbezirksliste.modelName";

const isAdmin = useHasAnyRole(Role.ADMIN);

const { t } = useI18n();

const headers: DataTableHeader<Partial<StadtbezirkslisteResponseDTO>>[] = [
  {
    title: t("model.stadtbezirksliste.lnaKurzbez"),
    value: "lnaKurzbez",
    align: "center",
    width: 100,
  },
  { title: t("model.stadtbezirksliste.bezStadtbezirk"), value: "bezStadtbezirk" },
  { title: t("model.stadtbezirksliste.bezeichnung"), value: "bezeichnung" },
];

const EMPTY_ITEM_TEMPLATE: Partial<StadtbezirkslisteResponseDTO> = {
  lnaKurzbez: undefined,
  bezeichnung: "",
};

const {
  data: stadtbezirkslisten,
  call: getStadtbezirkslisten,
  loading: getStadtbezirkslistenLoading,
} = useGetStadtbezirkslisten();

const {
  data: stadtbezirkslisteFormContext,
  call: getStadtbezirkslisteFormContext,
  loading: getStadtbezirkslisteFormContextLoading,
} = useGetStadtbezirkslisteFormContext();

type StadtbezirkslisteFormType = InstanceType<typeof StadtbezirkslisteForm>;
const stadtbezirkslisteFormRef = useTemplateRef<StadtbezirkslisteFormType>(
  "stadtbezirkslisteForm"
);

const { dataTableOptions, onSuccess, onFailure } = usePagination(
  computed(() => stadtbezirkslisten.value?.page?.totalPages),
  getStadtbezirkslisten,
  isAdmin,
  getStadtbezirkslisteFormContext,
  () => stadtbezirkslisteFormRef.value?.validate()
);

const {
  call: createStadtbezirksliste,
  loading: createStadtbezirkslisteLoading,
  error: createStadtbezirkslistenError,
} = useCreateStadtbezirksliste();

const handleCreate = async (
  stadtbezirkslisteCreateDTO: Partial<StadtbezirkslisteResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = stadtbezirkslisteCreateDTO as StadtbezirkslisteResponseDTO;
  await createStadtbezirksliste({
    stadtbezirkslisteCreateDTO: model,
  });
  if (!createStadtbezirkslistenError.value) {
    await onSuccess(t("common.message.created", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.createdError", [t(domainKey)]));
  }
};

const {
  call: updateStadtbezirksliste,
  loading: updateStadtbezirkslisteLoading,
  error: updateStadtbezirkslistenError,
} = useUpdateStadtbezirksliste();

const handleUpdate = async (
  stadtbezirkslisteUpdateDTO: Partial<StadtbezirkslisteResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = stadtbezirkslisteUpdateDTO as StadtbezirkslisteResponseDTO;
  await updateStadtbezirksliste({
    id: model.id,
    stadtbezirkslisteUpdateDTO: model,
  });
  if (!updateStadtbezirkslistenError.value) {
    await onSuccess(t("common.message.updated", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.updatedError", [t(domainKey)]));
  }
};

const {
  call: deleteStadtbezirksliste,
  loading: deleteStadtbezirkslisteLoading,
  error: deleteStadtbezirkslistenError,
} = useDeleteStadtbezirksliste();

const handleDelete = async (id: string) => {
  await deleteStadtbezirksliste({
    id,
  });
  if (!deleteStadtbezirkslistenError.value) {
    await onSuccess(t("common.message.deleted", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.deletedError", [t(domainKey)]));
  }
};

const loading = computed(
  () =>
    getStadtbezirkslistenLoading.value ||
    getStadtbezirkslisteFormContextLoading.value ||
    createStadtbezirkslisteLoading.value ||
    updateStadtbezirkslisteLoading.value ||
    deleteStadtbezirkslisteLoading.value
);
</script>
