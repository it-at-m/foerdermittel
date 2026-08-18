<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="baseViewLoading"
        :table-headers="headers"
        :api="bauprogrammApi"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :should-load-form-context="isAdmin"
        :handle-create="handleCreate"
        :handle-update="handleUpdate"
        :handle-delete="handleDelete"
        :form-ref="bauprogrammFormRef"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <bauprogramm-form
            v-if="bauprogrammApi.context.data"
            ref="bauprogrammForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :bauprogramm-form-context="bauprogrammApi.context.data.value!"
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { BauprogrammResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import BauprogrammForm from "@/components/forms/BauprogrammForm.vue";
import { useBauprogrammApi } from "@/composables/api/useBauprogrammApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import { Role } from "@/types/Role";

const domainKey = "model.bauprogramm.modelName";

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

const headers: DataTableHeader<Partial<BauprogrammResponseDTO>>[] = [
  {
    title: t("model.bauprogramm.bauprogramm"),
    value: "bauprogramm",
    align: "center",
    width: 100,
  },
  { title: t("model.bauprogramm.bezeichnung"), value: "bezeichnung" },
];

const EMPTY_ITEM_TEMPLATE: Partial<BauprogrammResponseDTO> = {
  bauprogramm: undefined,
  bezeichnung: "",
};

type BauprogrammFormType = InstanceType<typeof BauprogrammForm>;
const bauprogrammFormRef =
  useTemplateRef<BauprogrammFormType>("bauprogrammForm");

const bauprogrammApi = useBauprogrammApi();

const handleCreate = async (
  bauprogrammCreateDTO: Partial<BauprogrammResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = bauprogrammCreateDTO as BauprogrammResponseDTO;
  await bauprogrammApi.create.call({
    bauprogrammCreateDTO: model,
  });
  return !bauprogrammApi.create.error.value;
};

const handleUpdate = async (
  bauprogrammUpdateDTO: Partial<BauprogrammResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = bauprogrammUpdateDTO as BauprogrammResponseDTO;
  await bauprogrammApi.update.call({
    id: model.id,
    bauprogrammUpdateDTO: model,
  });
  return !bauprogrammApi.update.error.value;
};

const handleDelete = async (id: string) => {
  await bauprogrammApi.delete.call({
    id,
  });
  return !bauprogrammApi.delete.error.value;
};
</script>
