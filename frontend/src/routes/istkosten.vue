<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="baseViewLoading"
        :table-headers="headers"
        :api="istkostenApi"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :should-load-form-context="isAdmin"
        :handle-create="handleCreate"
        :handle-update="handleUpdate"
        :handle-delete="handleDelete"
        :form-ref="istkostenFormRef"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <istkosten-form
            v-if="istkostenFormContext"
            ref="istkostenForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :projekte="projekte"
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { IstkostenResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import IstkostenForm from "@/components/forms/IstkostenForm.vue";
import { useIstkostenApi } from "@/composables/api/useIstkostenApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import { Role } from "@/types/Role";

const domainKey = "model.istkosten.modelName";

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

const headers: DataTableHeader<Partial<IstkostenResponseDTO>>[] = [
  {
    title: t("model.istkosten.projnr"),
    value: "projnr",
    align: "center",
    width: 100,
  },
  {
    title: t("model.istkosten.jahr"),
    value: "jahr",
    align: "center",
    width: 110,
  },
  {
    title: t("model.istkosten.monat"),
    value: "monat",
    align: "center",
    width: 110,
  },
  {
    title: t("model.istkosten.istkosten"),
    value: "istkosten",
    align: "center",
    width: 110,
  },
  {
    title: t("model.istkosten.fobFb"),
    value: "fobFb",
    align: "center",
    width: 50,
  },
  {
    title: t("model.istkosten.pstrasse"),
    value: "pstrasse",
    align: "start",
    width: 180,
  },
  {
    title: t("model.istkosten.pname"),
    value: "pname",
    align: "start",
    width: 150,
  },
];

const today = new Date();

const currentYear = today.getFullYear();

const currentMonth = today.getMonth() + 1;

const EMPTY_ITEM_TEMPLATE: Partial<IstkostenResponseDTO> = {
  id: undefined,
  projnr: undefined,
  jahr: currentYear,
  monat: currentMonth,
  istkosten: undefined,
};

const istkostenApi = useIstkostenApi();

const istkostenFormContext = computed(() => istkostenApi.context.data.value);

const projekte = computed(() => istkostenFormContext.value?.projekte ?? []);

type IstkostenFormType = InstanceType<typeof IstkostenForm>;
const istkostenFormRef = useTemplateRef<IstkostenFormType>("istkostenForm");

const handleCreate = async (
  istkostenCreateDTO: Partial<IstkostenResponseDTO>
) => {
  const model = istkostenCreateDTO as IstkostenResponseDTO;

  await istkostenApi.create.call({
    istkostenCreateDTO: model,
  });
};

const handleUpdate = async (
  istkostenUpdateDTO: Partial<IstkostenResponseDTO>
) => {
  const model = istkostenUpdateDTO as IstkostenResponseDTO;

  await istkostenApi.update.call({
    id: model.id,
    istkostenUpdateDTO: model,
  });
};

const handleDelete = async (id: string) => {
  await istkostenApi.delete.call({
    id,
  });
};
</script>
