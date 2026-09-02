<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="baseViewLoading"
        :table-headers="headers"
        :api="foerderbereichApi"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :should-load-form-context="isAdmin"
        :handle-create="handleCreate"
        :handle-update="handleUpdate"
        :handle-delete="handleDelete"
        :form-ref="foerderbereichFormRef"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <foerderbereich-form
            v-if="foerderbereichApi.context.data"
            ref="foerderbereichForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :foerderbereich-form-context="foerderbereichApi.context.data.value!"
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
import { useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import FoerderbereichForm from "@/components/forms/FoerderbereichForm.vue";
import { useFoerderbereichApi } from "@/composables/api/useFoerderbereichApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import { Role } from "@/types/Role";

const domainKey = "model.foerderbereich.modelName";

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
  },
  {
    title: t("model.foerderbereich.finanzausgleich"),
    value: "finanzausgleich",
    align: "center",
    width: 130,
  },
  {
    title: t("model.foerderbereich.jahresstatistik"),
    value: "jahresstatistik",
    align: "center",
    width: 130,
  },
  {
    title: t("model.foerderbereich.kindergarten"),
    value: "kindergarten",
    align: "center",
    width: 130,
  },
  {
    title: t("model.foerderbereich.nichtRelevant"),
    value: "nichtRelevant",
    align: "center",
    width: 130,
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

const foerderbereichApi = useFoerderbereichApi();

type FoerderbereichFormType = InstanceType<typeof FoerderbereichForm>;
const foerderbereichFormRef =
  useTemplateRef<FoerderbereichFormType>("foerderbereichForm");

const handleCreate = async (
  foerderbereichCreateDTO: Partial<FoerderbereichResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = foerderbereichCreateDTO as FoerderbereichResponseDTO;
  await foerderbereichApi.create.call({
    foerderbereichCreateDTO: model,
  });
};

const handleUpdate = async (
  foerderbereichUpdateDTO: Partial<FoerderbereichResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = foerderbereichUpdateDTO as FoerderbereichResponseDTO;
  await foerderbereichApi.update.call({
    id: model.id,
    foerderbereichUpdateDTO: model,
  });
};

const handleDelete = async (id: string) => {
  await foerderbereichApi.delete.call({
    id,
  });
};
</script>
