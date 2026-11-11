<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="baseViewLoading"
        :table-headers="headers"
        :api="hhjahrApi"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :should-load-form-context="isAdmin"
        :handle-create="handleCreate"
        :handle-update="handleUpdate"
        :handle-delete="handleDelete"
        :form-ref="hhjahrFormRef"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <haushaltsjahr-form
            v-if="hhjahrFormContext"
            ref="hhjahrForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :hhjahr-form-context="hhjahrFormContext"
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { HhjahrResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import HaushaltsjahrForm from "@/components/forms/HaushaltsjahrForm.vue";
import { useHaushaltsjahrApi } from "@/composables/api/useHaushaltsjahrApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import { Role } from "@/types/Role";

const domainKey = "model.haushaltsjahr.modelName";
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

const headers: DataTableHeader<Partial<HhjahrResponseDTO>>[] = [
  {
    title: t("model.haushaltsjahr.hhjahr"),
    value: "hhjahr",
    align: "center",
    width: 120,
  },
  {
    title: t("model.haushaltsjahr.hhbezeichnung"),
    value: "hhbezeichnung",
  },
  {
    title: t("model.haushaltsjahr.notizen"),
    value: "notizen",
  },
];

const EMPTY_ITEM_TEMPLATE: Partial<HhjahrResponseDTO> = {
  hhjahr: undefined,
  hhbezeichnung: "",
  notizen: "",
};

const hhjahrApi = useHaushaltsjahrApi();

const hhjahrFormContext = computed(() => hhjahrApi.context?.data.value);

type HaushaltsjahrFormType = InstanceType<typeof HaushaltsjahrForm>;

const hhjahrFormRef = useTemplateRef<HaushaltsjahrFormType>("hhjahrForm");

const handleCreate = async (hhjahrCreateDTO: Partial<HhjahrResponseDTO>) => {
  const model = hhjahrCreateDTO as HhjahrResponseDTO;
  await hhjahrApi.create?.call({
    hhjahrCreateDTO: model,
  });
};

const handleUpdate = async (hhjahrUpdateDTO: Partial<HhjahrResponseDTO>) => {
  const model = hhjahrUpdateDTO as HhjahrResponseDTO;
  await hhjahrApi.update?.call({
    id: model.id,
    hhjahrUpdateDTO: model,
  });
};

const handleDelete = async (id: string) => {
  await hhjahrApi.delete?.call({
    id,
  });
};
</script>
