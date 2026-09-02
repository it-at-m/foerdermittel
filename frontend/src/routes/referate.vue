<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="baseViewLoading"
        :table-headers="headers"
        :api="referatApi"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :should-load-form-context="isAdmin"
        :handle-create="handleCreate"
        :handle-update="handleUpdate"
        :handle-delete="handleDelete"
        :form-ref="referatFormRef"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <referat-form
            v-if="referatFormContext"
            ref="referatForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :referat-form-context="referatFormContext"
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { ReferatResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import ReferatForm from "@/components/forms/ReferatForm.vue";
import { useReferatApi } from "@/composables/api/useReferatApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import { Role } from "@/types/Role";

const domainKey = "model.referat.modelName";

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

const headers: DataTableHeader<Partial<ReferatResponseDTO>>[] = [
  {
    title: t("model.referat.refnr"),
    value: "refnr",
    align: "center",
    width: 120,
  },
  { title: t("model.referat.bezeichnung"), value: "bezeichnung" },
];

const EMPTY_ITEM_TEMPLATE: Partial<ReferatResponseDTO> = {
  refnr: undefined,
  bezeichnung: "",
};

const referatApi = useReferatApi();

const referatFormContext = computed(() => referatApi.context.data.value);

type ReferatFormType = InstanceType<typeof ReferatForm>;
const referatFormRef = useTemplateRef<ReferatFormType>("referatForm");

const handleCreate = async (referatCreateDTO: Partial<ReferatResponseDTO>) => {
  // TODO: some type checking improvements
  const model = referatCreateDTO as ReferatResponseDTO;
  await referatApi.create.call({
    referatCreateDTO: model,
  });
};

const handleUpdate = async (referatUpdateDTO: Partial<ReferatResponseDTO>) => {
  // TODO: some type checking improvements
  const model = referatUpdateDTO as ReferatResponseDTO;
  await referatApi.update.call({
    id: model.id,
    referatUpdateDTO: model,
  });
};

const handleDelete = async (id: string) => {
  await referatApi.delete.call({
    id,
  });
};
</script>
