<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="baseViewLoading"
        :table-headers="headers"
        :api="stichwortbereichApi"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :should-load-form-context="isAdmin"
        :handle-create="handleCreate"
        :handle-update="handleUpdate"
        :handle-delete="handleDelete"
        :form-ref="stichwortbereichFormRef"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <stichwortbereich-form
            v-if="stichwortbereichFormContext"
            ref="stichwortbereichForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :stichwortbereich-form-context="stichwortbereichFormContext"
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { StichwortbereichResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import StichwortbereichForm from "@/components/forms/StichwortbereichForm.vue";
import { useStichwortbereichApi } from "@/composables/api/useStichwortbereichApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import { Role } from "@/types/Role";

const domainKey = "model.stichwortbereich.modelName";

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

const headers: DataTableHeader<Partial<StichwortbereichResponseDTO>>[] = [
  {
    title: t("model.stichwortbereich.bereich"),
    value: "bereich",
    width: 350,
  },
  { title: t("model.stichwortbereich.bezeichnung"), value: "bezeichnung" },
];

const EMPTY_ITEM_TEMPLATE: Partial<StichwortbereichResponseDTO> = {
  bereich: undefined,
  bezeichnung: "",
};

const stichwortbereichApi = useStichwortbereichApi();

const stichwortbereichFormContext = computed(
  () => stichwortbereichApi.context.data.value
);

type StichwortbereichFormType = InstanceType<typeof StichwortbereichForm>;
const stichwortbereichFormRef = useTemplateRef<StichwortbereichFormType>(
  "stichwortbereichForm"
);

const handleCreate = async (
  stichwortbereichCreateDTO: Partial<StichwortbereichResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = stichwortbereichCreateDTO as StichwortbereichResponseDTO;
  await stichwortbereichApi.create.call({
    stichwortbereichCreateDTO: model,
  });
};

const handleUpdate = async (
  stichwortbereichUpdateDTO: Partial<StichwortbereichResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = stichwortbereichUpdateDTO as StichwortbereichResponseDTO;
  await stichwortbereichApi.update.call({
    id: model.id,
    stichwortbereichUpdateDTO: model,
  });
};

const handleDelete = async (id: string) => {
  await stichwortbereichApi.delete.call({
    id,
  });
};
</script>
