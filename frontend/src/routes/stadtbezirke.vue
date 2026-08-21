<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="baseViewLoading"
        :table-headers="headers"
        :api="stadtbezirkApi"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :should-load-form-context="isAdmin"
        :handle-create="handleCreate"
        :handle-update="handleUpdate"
        :handle-delete="handleDelete"
        :form-ref="stadtbezirkFormRef"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <stadtbezirk-form
            v-if="stadtbezirkApi.context.data"
            ref="stadtbezirkForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :stadtbezirk-form-context="stadtbezirkApi.context.data.value!"
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { StadtbezirkResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import StadtbezirkForm from "@/components/forms/StadtbezirkForm.vue";
import { useStadtbezirkApi } from "@/composables/api/useStadtbezirkApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import { Role } from "@/types/Role";

const domainKey = "model.stadtbezirk.modelName";

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

const headers: DataTableHeader<Partial<StadtbezirkResponseDTO>>[] = [
  {
    title: t("model.stadtbezirk.stadtbezirk"),
    value: "stadtbezirk",
    align: "center",
    width: 100,
  },
  { title: t("model.stadtbezirk.bezeichnung"), value: "bezeichnung" },
];

const EMPTY_ITEM_TEMPLATE: Partial<StadtbezirkResponseDTO> = {
  stadtbezirk: undefined,
  bezeichnung: "",
};

const stadtbezirkApi = useStadtbezirkApi();

type StadtbezirkFormType = InstanceType<typeof StadtbezirkForm>;
const stadtbezirkFormRef =
  useTemplateRef<StadtbezirkFormType>("stadtbezirkForm");

const handleCreate = async (
  stadtbezirkCreateDTO: Partial<StadtbezirkResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = stadtbezirkCreateDTO as StadtbezirkResponseDTO;
  await stadtbezirkApi.create.call({
    stadtbezirkCreateDTO: model,
  });
  return !stadtbezirkApi.create.error.value;
};

const handleUpdate = async (
  stadtbezirkUpdateDTO: Partial<StadtbezirkResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = stadtbezirkUpdateDTO as StadtbezirkResponseDTO;
  await stadtbezirkApi.update.call({
    id: model.id,
    stadtbezirkUpdateDTO: model,
  });
  return !stadtbezirkApi.update.error.value;
};

const handleDelete = async (id: string) => {
  await stadtbezirkApi.delete.call({
    id,
  });
  return !stadtbezirkApi.delete.error.value;
};
</script>
