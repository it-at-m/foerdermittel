<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="baseViewLoading"
        :table-headers="headers"
        :api="publikationApi"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :should-load-form-context="isAdmin"
        :handle-create="handleCreate"
        :handle-update="handleUpdate"
        :handle-delete="handleDelete"
        :form-ref="publikationFormRef"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <publikation-form
            v-if="publikationApi.context.data"
            ref="publikationForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :publikation-form-context="publikationApi.context.data.value!"
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { PublikationResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import PublikationForm from "@/components/forms/PublikationForm.vue";
import { usePublikationApi } from "@/composables/api/usePublikationApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import { Role } from "@/types/Role";

const domainKey = "model.publikation.modelName";

const isAdmin = useHasAnyRole(Role.ADMIN);

definePage({
  meta: {
    hasAnyRole: [
      Role.SACHBEARBEITUNG,
      Role.SACHBEARBEITUNG_HAUSHALT,
      Role.ADMIN,
    ],
  },
});

const { t } = useI18n();

const headers: DataTableHeader<Partial<PublikationResponseDTO>>[] = [
  {
    title: t("model.publikation.kurzform"),
    value: "kurzform",
    align: "center",
    width: 100,
  },
  { title: t("model.publikation.bezeichnung"), value: "bezeichnung" },
];

const EMPTY_ITEM_TEMPLATE: Partial<PublikationResponseDTO> = {
  kurzform: undefined,
  bezeichnung: "",
};

const publikationApi = usePublikationApi();

type PublikationFormType = InstanceType<typeof PublikationForm>;
const publikationFormRef =
  useTemplateRef<PublikationFormType>("publikationForm");

const handleCreate = async (
  publikationCreateDTO: Partial<PublikationResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = publikationCreateDTO as PublikationResponseDTO;
  await publikationApi.create.call({
    publikationCreateDTO: model,
  });
  return !publikationApi.create.error.value;
};

const handleUpdate = async (
  publikationUpdateDTO: Partial<PublikationResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = publikationUpdateDTO as PublikationResponseDTO;
  await publikationApi.update.call({
    id: model.id,
    publikationUpdateDTO: model,
  });
  return !publikationApi.update.error.value;
};

const handleDelete = async (id: string) => {
  await publikationApi.delete.call({
    id,
  });
  return !publikationApi.delete.error.value;
};
</script>
