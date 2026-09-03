<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="baseViewLoading"
        :table-headers="headers"
        :api="krankenhausApi"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :should-load-form-context="isAdmin"
        :handle-create="handleCreate"
        :handle-update="handleUpdate"
        :handle-delete="handleDelete"
        :form-ref="krankenhausFormRef"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <krankenhaus-form
            v-if="krankenhausFormContext"
            ref="krankenhausForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :krankenhaus-form-context="krankenhausFormContext"
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { KrankenhausResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import KrankenhausForm from "@/components/forms/KrankenhausForm.vue";
import { useKrankenhausApi } from "@/composables/api/useKrankenhausApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import { Role } from "@/types/Role";

const domainKey = "model.krankenhaus.modelName";

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

const headers: DataTableHeader<Partial<KrankenhausResponseDTO>>[] = [
  {
    title: t("model.krankenhaus.krhname"),
    value: "krhname",
    align: "center",
    width: 100,
  },
  { title: t("model.krankenhaus.bezeichnung"), value: "bezeichnung" },
];

const EMPTY_ITEM_TEMPLATE: Partial<KrankenhausResponseDTO> = {
  krhname: undefined,
  bezeichnung: "",
};

type KrankenhausFormType = InstanceType<typeof KrankenhausForm>;
const krankenhausFormRef =
  useTemplateRef<KrankenhausFormType>("krankenhausForm");

const krankenhausApi = useKrankenhausApi();

const krankenhausFormContext = computed(
  () => krankenhausApi.context.data.value
);

const handleCreate = async (
  krankenhausCreateDTO: Partial<KrankenhausResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = krankenhausCreateDTO as KrankenhausResponseDTO;
  await krankenhausApi.create.call({
    krankenhausCreateDTO: model,
  });
};

const handleUpdate = async (
  krankenhausUpdateDTO: Partial<KrankenhausResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = krankenhausUpdateDTO as KrankenhausResponseDTO;
  await krankenhausApi.update.call({
    id: model.id,
    krankenhausUpdateDTO: model,
  });
};

const handleDelete = async (id: string) => {
  await krankenhausApi.delete.call({
    id,
  });
};
</script>
