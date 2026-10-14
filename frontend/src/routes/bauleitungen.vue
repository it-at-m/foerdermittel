<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="baseViewLoading"
        :table-headers="headers"
        :api="bauleitungApi"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :should-load-form-context="isAdmin"
        :handle-create="handleCreate"
        :handle-update="handleUpdate"
        :handle-delete="handleDelete"
        :form-ref="bauleitungFormRef"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <bauleitung-form
            v-if="bauleitungFormContext"
            ref="bauleitungForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :bauleitung-form-context="bauleitungFormContext"
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { BauleitungResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import BauleitungForm from "@/components/forms/BauleitungForm.vue";
import { useBauleitungApi } from "@/composables/api/useBauleitungApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import { Role } from "@/types/Role";

const domainKey = "model.bauleitung.modelName";

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

const headers: DataTableHeader<Partial<BauleitungResponseDTO>>[] = [
  {
    title: t("model.bauleitung.bauleitung"),
    value: "bauleitung",
    align: "center",
    width: 100,
  },
  { title: t("model.bauleitung.bezeichnung"), value: "bezeichnung" },
];

const EMPTY_ITEM_TEMPLATE: Partial<BauleitungResponseDTO> = {
  bauleitung: undefined,
  bezeichnung: "",
};

const bauleitungApi = useBauleitungApi();

const bauleitungFormContext = computed(() => bauleitungApi.context.data.value);

type BauleitungFormType = InstanceType<typeof BauleitungForm>;
const bauleitungFormRef = useTemplateRef<BauleitungFormType>("bauleitungForm");

const handleCreate = async (
  bauleitungCreateDTO: Partial<BauleitungResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = bauleitungCreateDTO as BauleitungResponseDTO;
  await bauleitungApi.create.call({
    bauleitungCreateDTO: model,
  });
};

const handleUpdate = async (
  bauleitungUpdateDTO: Partial<BauleitungResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = bauleitungUpdateDTO as BauleitungResponseDTO;
  await bauleitungApi.update.call({
    id: model.id,
    bauleitungUpdateDTO: model,
  });
};

const handleDelete = async (id: string) => {
  await bauleitungApi.delete.call({
    id,
  });
};
</script>
