<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="baseViewLoading"
        :table-headers="headers"
        :api="siedlungsgebietApi"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :should-load-form-context="isAdmin"
        :handle-create="handleCreate"
        :handle-update="handleUpdate"
        :handle-delete="handleDelete"
        :form-ref="siedlungsgebietFormRef"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <siedlungsgebiet-form
            v-if="siedlungsgebietApi.context.data"
            ref="siedlungsgebietForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :siedlungsgebiet-form-context="
              siedlungsgebietApi.context.data.value!
            "
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { SiedlungsgebietResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import SiedlungsgebietForm from "@/components/forms/SiedlungsgebietForm.vue";
import { useSiedlungsgebietApi } from "@/composables/api/useSiedlungsgebietApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import { Role } from "@/types/Role";

const domainKey = "model.siedlungsgebiet.modelName";

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

const headers: DataTableHeader<Partial<SiedlungsgebietResponseDTO>>[] = [
  {
    title: t("model.siedlungsgebiet.siedlungsgebiet"),
    value: "siedlungsgebiet",
    align: "center",
    width: 120,
  },
  { title: t("model.siedlungsgebiet.bezeichnung"), value: "bezeichnung" },
];

const EMPTY_ITEM_TEMPLATE: Partial<SiedlungsgebietResponseDTO> = {
  siedlungsgebiet: undefined,
  bezeichnung: "",
};

const siedlungsgebietApi = useSiedlungsgebietApi();

type SiedlungsgebietFormType = InstanceType<typeof SiedlungsgebietForm>;
const siedlungsgebietFormRef = useTemplateRef<SiedlungsgebietFormType>(
  "siedlungsgebietForm"
);

const handleCreate = async (
  siedlungsgebietCreateDTO: Partial<SiedlungsgebietResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = siedlungsgebietCreateDTO as SiedlungsgebietResponseDTO;
  await siedlungsgebietApi.create.call({
    siedlungsgebietCreateDTO: model,
  });
  return !siedlungsgebietApi.create.error.value;
};

const handleUpdate = async (
  siedlungsgebietUpdateDTO: Partial<SiedlungsgebietResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = siedlungsgebietUpdateDTO as SiedlungsgebietResponseDTO;
  await siedlungsgebietApi.update.call({
    id: model.id,
    siedlungsgebietUpdateDTO: model,
  });
  return !siedlungsgebietApi.update.error.value;
};

const handleDelete = async (id: string) => {
  await siedlungsgebietApi.delete.call({
    id,
  });
  return !siedlungsgebietApi.delete.error.value;
};
</script>
