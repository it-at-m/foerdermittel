<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="baseViewLoading"
        :table-headers="headers"
        :api="kurzbezeichnungApi"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :should-load-form-context="isAdmin"
        :handle-create="handleCreate"
        :handle-update="handleUpdate"
        :handle-delete="handleDelete"
        :form-ref="kurzbezeichnungFormRef"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <kurzbezeichnung-form
            v-if="kurzbezeichnungApi.context.data"
            ref="kurzbezeichnungForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :kurzbezeichnung-form-context="
              kurzbezeichnungApi.context.data.value!
            "
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { KurzbezeichnungResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import KurzbezeichnungForm from "@/components/forms/KurzbezeichnungForm.vue";
import { useKurzbezeichnungApi } from "@/composables/api/useKurzbezeichnungApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import { Role } from "@/types/Role";

const domainKey = "model.kurzbezeichnung.modelName";

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

const headers: DataTableHeader<Partial<KurzbezeichnungResponseDTO>>[] = [
  {
    title: t("model.kurzbezeichnung.kurzbez"),
    value: "kurzbez",
    align: "center",
    width: 150,
  },
  { title: t("model.kurzbezeichnung.bezeichnung"), value: "bezeichnung" },
];

const EMPTY_ITEM_TEMPLATE: Partial<KurzbezeichnungResponseDTO> = {
  kurzbez: undefined,
  bezeichnung: "",
};

const kurzbezeichnungApi = useKurzbezeichnungApi();

type KurzbezeichnungFormType = InstanceType<typeof KurzbezeichnungForm>;
const kurzbezeichnungFormRef = useTemplateRef<KurzbezeichnungFormType>(
  "kurzbezeichnungForm"
);

const handleCreate = async (
  kurzbezeichnungCreateDTO: Partial<KurzbezeichnungResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = kurzbezeichnungCreateDTO as KurzbezeichnungResponseDTO;
  await kurzbezeichnungApi.create.call({
    kurzbezeichnungCreateDTO: model,
  });
};

const handleUpdate = async (
  kurzbezeichnungUpdateDTO: Partial<KurzbezeichnungResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = kurzbezeichnungUpdateDTO as KurzbezeichnungResponseDTO;
  await kurzbezeichnungApi.update.call({
    id: model.id,
    kurzbezeichnungUpdateDTO: model,
  });
};

const handleDelete = async (id: string) => {
  await kurzbezeichnungApi.delete.call({
    id,
  });
};
</script>
