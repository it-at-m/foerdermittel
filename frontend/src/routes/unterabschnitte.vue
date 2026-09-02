<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="baseViewLoading"
        :table-headers="headers"
        :api="unterabschnittApi"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :should-load-form-context="isAdmin"
        :handle-create="handleCreate"
        :handle-update="handleUpdate"
        :handle-delete="handleDelete"
        :form-ref="unterabschnittFormRef"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <unterabschnitt-form
            v-if="unterabschnittApi.context.data"
            ref="unterabschnittForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :unterabschnitt-form-context="unterabschnittApi.context.data.value!"
            @is-valid="updateValidity"
          />
        </template>
        <template #[`item.hasHa`]="{ item }">
          <v-tooltip
            :text="item.haBezeichnung"
            location="right"
          >
            <template #activator="{ props }">
              <v-chip
                v-bind="props"
                :append-icon="mdiArrowRight"
                to="hauptabschnitte"
                >{{ item.hasHa }}
              </v-chip>
            </template>
          </v-tooltip>
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { UnterabschnittResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { mdiArrowRight } from "@mdi/js";
import { useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import UnterabschnittForm from "@/components/forms/UnterabschnittForm.vue";
import { useUnterabschnittApi } from "@/composables/api/useUnterabschnittApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import { Role } from "@/types/Role";

const domainKey = "model.unterabschnitt.modelName";

const isAdmin = useHasAnyRole(Role.ADMIN);

const { t } = useI18n();

const headers: DataTableHeader<Partial<UnterabschnittResponseDTO>>[] = [
  {
    title: t("model.unterabschnitt.ua"),
    value: "ua",
    align: "center",
    width: 120,
  },
  {
    title: t("model.hauptabschnitt.ha"),
    value: "hasHa",
    align: "center",
    width: 120,
  },
  {
    title: t("model.unterabschnitt.bezeichnung"),
    value: "bezeichnung",
  },
];

const EMPTY_ITEM_TEMPLATE: Partial<UnterabschnittResponseDTO> = {
  ua: undefined,
  bezeichnung: "",
  hasHa: "",
  haBezeichnung: "",
};

const unterabschnittApi = useUnterabschnittApi();

type UnterabschnittFormType = InstanceType<typeof UnterabschnittForm>;

const unterabschnittFormRef =
  useTemplateRef<UnterabschnittFormType>("unterabschnittForm");

const handleCreate = async (
  unterabschnittCreateDTO: Partial<UnterabschnittResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = unterabschnittCreateDTO as UnterabschnittResponseDTO;

  await unterabschnittApi.create.call({
    unterabschnittCreateDTO: model,
  });
  return !unterabschnittApi.create.error.value;
};

const handleUpdate = async (
  unterabschnittUpdateDTO: Partial<UnterabschnittResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = unterabschnittUpdateDTO as UnterabschnittResponseDTO;

  await unterabschnittApi.update.call({
    id: model.id,
    unterabschnittUpdateDTO: model,
  });

  return !unterabschnittApi.update.error.value;
};

const handleDelete = async (id: string) => {
  await unterabschnittApi.delete.call({
    id,
  });
  return !unterabschnittApi.delete.error.value;
};
</script>
