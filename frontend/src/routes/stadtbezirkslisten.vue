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
        :expandable="true"
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
            :listenname-form-context="stadtbezirkslisteFormContext"
            @is-valid="updateValidity"
          />
        </template>

        <template #expanded="{ item }">
          <div>
            <!-- Header -->
            <v-row class="font-weight-bold py-2 pl-6">
              <v-col cols="2">{{
                t("model.stadtbezirksliste.bezStadtbezirk")
              }}</v-col>
              <v-col cols="4"></v-col>
              <v-col cols="6">{{
                t("model.stadtbezirksliste.listeBezeichnung")
              }}</v-col>
            </v-row>

            <v-divider />

            <v-row
              v-if="!item.assignedStadtbezirke?.length"
              class="py-4 pl-6"
            >
              <v-col
                cols="12"
                class="text-medium-emphasis"
              >
                {{ t("model.stadtbezirksliste.noAssigneStadtbezirke") }}
              </v-col>
            </v-row>

            <template
              v-for="stadtbezirk in item.assignedStadtbezirke"
              :key="stadtbezirk.stadtbezirkId"
            >
              <v-row class="py-2 pl-6">
                <v-col cols="2">{{ stadtbezirk.stadtbezirkId }}</v-col>
                <v-col cols="4">{{ stadtbezirk.stadtbezirkBezeichnung }}</v-col>
                <v-col cols="6">{{ stadtbezirk.bezeichnung }}</v-col>
              </v-row>

              <v-divider />
            </template>
          </div>
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
  useCreateListenname,
  useDeleteStadtbezirksliste,
  useGetStadtbezirkslisteFormContext,
  useGetStadtbezirkslisten,
  useUpdateListenname,
} from "@/composables/api/useStadtbezirkslisteApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import usePagination from "@/composables/usePagination";
import { Role } from "@/types/Role";

const domainKey = "model.stadtbezirksliste.modelName";

const { t } = useI18n();

const isAdmin = useHasAnyRole(Role.ADMIN);

const headers: DataTableHeader<Partial<StadtbezirkslisteResponseDTO>>[] = [
  {
    title: t("model.stadtbezirksliste.lnaKurzbez"),
    value: "kurzbez",
    width: 120,
  },
  {
    title: t("model.stadtbezirksliste.bezeichnung"),
    value: "bezeichnung",
  },
];

const EMPTY_ITEM_TEMPLATE: Partial<StadtbezirkslisteResponseDTO> = {
  kurzbez: "",
  bezeichnung: "",
  assignedStadtbezirke: [],
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
  call: createListenname,
  loading: createListennameLoading,
  error: createListennameError,
} = useCreateListenname();

const handleCreate = async (
  stadtbezirkslisteCreateDTO: Partial<StadtbezirkslisteResponseDTO>
) => {
  const model = stadtbezirkslisteCreateDTO as StadtbezirkslisteResponseDTO;

  await createListenname({
    listennameCreateDTO: model,
  });

  if (!createListennameError.value) {
    await onSuccess(t("common.message.created", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.createdError", [t(domainKey)]));
  }
};

const {
  call: updateListenname,
  loading: updateListennameLoading,
  error: updateListennameError,
} = useUpdateListenname();

const handleUpdate = async (
  stadtbezirkslisteUpdateDTO: Partial<StadtbezirkslisteResponseDTO>
) => {
  const model = stadtbezirkslisteUpdateDTO as StadtbezirkslisteResponseDTO;

  await updateListenname({
    id: model.id,
    listennameUpdateDTO: model,
  });

  if (!updateListennameError.value) {
    await onSuccess(t("common.message.updated", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.updatedError", [t(domainKey)]));
  }
};

const {
  call: deleteStadtbezirksliste,
  loading: deleteStadtbezirkslisteLoading,
  error: deleteStadtbezirkslisteError,
} = useDeleteStadtbezirksliste();

const handleDelete = async (kurzbez: string) => {
  await deleteStadtbezirksliste({
    kurzbez,
  });

  if (!deleteStadtbezirkslisteError.value) {
    await onSuccess(t("common.message.deleted", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.deletedError", [t(domainKey)]));
  }
};

const loading = computed(
  () =>
    getStadtbezirkslistenLoading.value ||
    getStadtbezirkslisteFormContextLoading.value ||
    createListennameLoading.value ||
    updateListennameLoading.value ||
    deleteStadtbezirkslisteLoading.value
);
</script>