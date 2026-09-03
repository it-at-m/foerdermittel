<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="baseViewLoading"
        :table-headers="headers"
        :api="traegerApi"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :should-load-form-context="isAdmin"
        :handle-create="handleCreate"
        :handle-update="handleUpdate"
        :handle-delete="handleDelete"
        :form-ref="traegerFormRef"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <traeger-form
            v-if="traegerFormContext"
            ref="traegerForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :traeger-form-context="traegerFormContext"
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { TraegerResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import TraegerForm from "@/components/forms/TraegerForm.vue";
import { useTraegerApi } from "@/composables/api/useTraegerApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import { Role } from "@/types/Role";

const domainKey = "model.traeger.modelName";

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

const headers: DataTableHeader<Partial<TraegerResponseDTO>>[] = [
  {
    title: t("model.traeger.kurzform"),
    value: "kurzform",
    align: "center",
    width: 100,
  },
  { title: t("model.traeger.bezeichnung"), value: "bezeichnung" },
];

const EMPTY_ITEM_TEMPLATE: Partial<TraegerResponseDTO> = {
  kurzform: undefined,
  bezeichnung: "",
};

const traegerApi = useTraegerApi();

const traegerFormContext = computed(() => traegerApi.context.data.value);

type TraegerFormType = InstanceType<typeof TraegerForm>;
const traegerFormRef = useTemplateRef<TraegerFormType>("traegerForm");

const handleCreate = async (traegerCreateDTO: Partial<TraegerResponseDTO>) => {
  // TODO: some type checking improvements
  const model = traegerCreateDTO as TraegerResponseDTO;
  await traegerApi.create.call({
    traegerCreateDTO: model,
  });
};

const handleUpdate = async (traegerUpdateDTO: Partial<TraegerResponseDTO>) => {
  // TODO: some type checking improvements
  const model = traegerUpdateDTO as TraegerResponseDTO;
  await traegerApi.update.call({
    id: model.id,
    traegerUpdateDTO: model,
  });
};

const handleDelete = async (id: string) => {
  await traegerApi.delete.call({
    id,
  });
};
</script>
