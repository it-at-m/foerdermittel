<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="baseViewLoading"
        :table-headers="headers"
        :api="hauptabschnittApi"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :should-load-form-context="isAdmin"
        :handle-create="handleCreate"
        :handle-update="handleUpdate"
        :handle-delete="handleDelete"
        :form-ref="hauptabschnittFormRef"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <hauptabschnitt-form
            v-if="hauptabschnittApi.context.data"
            ref="hauptabschnittForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :hauptabschnitt-form-context="hauptabschnittApi.context.data.value!"
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { HauptabschnittResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import HauptabschnittForm from "@/components/forms/HauptabschnittForm.vue";
import { useHauptabschnittApi } from "@/composables/api/useHauptabschnittApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import { Role } from "@/types/Role";

const domainKey = "model.hauptabschnitt.modelName";

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

const headers: DataTableHeader<Partial<HauptabschnittResponseDTO>>[] = [
  {
    title: t("model.hauptabschnitt.ha"),
    value: "ha",
    align: "center",
    width: 100,
  },
  { title: t("model.hauptabschnitt.bezeichnung"), value: "bezeichnung" },
];

const EMPTY_ITEM_TEMPLATE: Partial<HauptabschnittResponseDTO> = {
  ha: undefined,
  bezeichnung: "",
};

const hauptabschnittApi = useHauptabschnittApi();

type HauptabschnittFormType = InstanceType<typeof HauptabschnittForm>;
const hauptabschnittFormRef =
  useTemplateRef<HauptabschnittFormType>("hauptabschnittForm");

const handleCreate = async (
  hauptabschnittCreateDTO: Partial<HauptabschnittResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = hauptabschnittCreateDTO as HauptabschnittResponseDTO;
  await hauptabschnittApi.create.call({
    hauptabschnittCreateDTO: model,
  });
};

const handleUpdate = async (
  hauptabschnittUpdateDTO: Partial<HauptabschnittResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = hauptabschnittUpdateDTO as HauptabschnittResponseDTO;
  await hauptabschnittApi.update.call({
    id: model.id,
    hauptabschnittUpdateDTO: model,
  });
};

const handleDelete = async (id: string) => {
  await hauptabschnittApi.delete.call({
    id,
  });
};
</script>
