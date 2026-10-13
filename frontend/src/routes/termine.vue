<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="baseViewLoading"
        :table-headers="headers"
        :api="terminApi"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :should-load-form-context="isAdmin"
        :handle-create="handleCreate"
        :handle-update="handleUpdate"
        :handle-delete="handleDelete"
        :form-ref="terminFormRef"
        :expandable="true"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <termin-form
            v-if="terminFormContext"
            ref="terminForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :projekte="projekte"
            @is-valid="updateValidity"
          />
        </template>

        <template #[`item.ueberwachung`]="{ item }">
          <v-icon
            v-if="item.ueberwachung"
            :icon="mdiCheck"
          />
        </template>

        <template #[`item.termin`]="{ item }">
          {{ formatDate(item.termin) }}
        </template>

        <template #expanded="{ item }">
          <div class="pa-4">
            <div class="text-subtitle-2 mb-2">
              {{ t("model.termin.notizen") }}
            </div>

            <div
              class="text-body-2"
              style="white-space: pre-wrap"
            >
              {{ item.notizen || "—" }}
            </div>
          </div>
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { TerminResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { mdiCheck } from "@mdi/js";
import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import TerminForm from "@/components/forms/TerminForm.vue";
import { useTerminApi } from "@/composables/api/useTerminApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import { Role } from "@/types/Role";

const domainKey = "model.termin.modelName";

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

const headers: DataTableHeader<Partial<TerminResponseDTO>>[] = [
  {
    title: t("model.termin.projnr"),
    value: "projnr",
    align: "center",
    width: 100,
  },
  {
    title: t("model.termin.termin"),
    value: "termin",
    align: "center",
    width: 100,
  },
  {
    title: t("model.termin.zustaendig"),
    value: "zustaendig",
    align: "start",
    width: 150,
  },
  {
    title: t("model.termin.ueberwachung"),
    value: "ueberwachung",
    align: "center",
    width: 50,
  },
  {
    title: t("model.termin.telefon"),
    value: "telefon",
    align: "start",
    width: 110,
  },

  {
    title: t("model.termin.fobFb"),
    value: "fobFb",
    align: "center",
    width: 50,
  },
  {
    title: t("model.termin.pstrasse"),
    value: "pstrasse",
    align: "start",
    width: 180,
  },
  {
    title: t("model.termin.pname"),
    value: "pname",
    align: "start",
    width: 150,
  },
  {
    title: t("model.termin.bezStadtbezirk"),
    value: "bezStadtbezirk",
    align: "center",
    width: 50,
  },
];

const EMPTY_ITEM_TEMPLATE: Partial<TerminResponseDTO> = {
  projnr: undefined,
  termin: undefined,
  zustaendig: undefined,
  ueberwachung: false,
  telefon: undefined,
  notizen: undefined,
};

const terminApi = useTerminApi();

const terminFormContext = computed(() => terminApi.context.data.value);

const projekte = computed(() => terminFormContext.value?.projekte ?? []);

type TerminFormType = InstanceType<typeof TerminForm>;
const terminFormRef = useTemplateRef<TerminFormType>("terminForm");

const handleCreate = async (terminCreateDTO: Partial<TerminResponseDTO>) => {
  const model = terminCreateDTO as TerminResponseDTO;

  await terminApi.create.call({
    terminCreateDTO: model,
  });
};

const handleUpdate = async (terminUpdateDTO: Partial<TerminResponseDTO>) => {
  const model = terminUpdateDTO as TerminResponseDTO;
  await terminApi.update.call({
    id: model.id,
    terminUpdateDTO: model,
  });
};

const handleDelete = async (id: string) => {
  await terminApi.delete.call({
    id,
  });
};

const formatDate = (value?: Date | null) =>
  value ? value.toLocaleDateString("de-DE") : "";
</script>
