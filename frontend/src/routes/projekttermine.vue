<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="baseViewLoading"
        :table-headers="headers"
        :api="projektterminApi"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :should-load-form-context="isAdmin"
        :handle-create="handleCreate"
        :handle-update="handleUpdate"
        :handle-delete="handleDelete"
        :form-ref="projektterminFormRef"
        :expandable="true"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <projekttermin-form
            v-if="projektterminFormContext"
            ref="projektterminForm"
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
import type { ProjektterminResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { mdiCheck } from "@mdi/js";
import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import ProjektterminForm from "@/components/forms/ProjektterminForm.vue";
import { useProjektterminApi } from "@/composables/api/useProjektterminApi";
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

const headers: DataTableHeader<Partial<ProjektterminResponseDTO>>[] = [
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

const EMPTY_ITEM_TEMPLATE: Partial<ProjektterminResponseDTO> = {
  projnr: undefined,
  termin: undefined,
  zustaendig: undefined,
  ueberwachung: false,
  telefon: undefined,
  notizen: undefined,
};

const projektterminApi = useProjektterminApi();

const projektterminFormContext = computed(
  () => projektterminApi.context.data.value
);

const projekte = computed(() => projektterminFormContext.value?.projekte ?? []);

type ProjektterminFormType = InstanceType<typeof ProjektterminForm>;
const projektterminFormRef =
  useTemplateRef<ProjektterminFormType>("projektterminForm");

const handleCreate = async (
  projektterminCreateDTO: Partial<ProjektterminResponseDTO>
) => {
  const model = projektterminCreateDTO as ProjektterminResponseDTO;

  await projektterminApi.create.call({
    projektterminCreateDTO: model,
  });
};

const handleUpdate = async (
  projektterminUpdateDTO: Partial<ProjektterminResponseDTO>
) => {
  const model = projektterminUpdateDTO as ProjektterminResponseDTO;
  await projektterminApi.update.call({
    id: model.id,
    projektterminUpdateDTO: model,
  });
};

const handleDelete = async (id: string) => {
  await projektterminApi.delete.call({
    id,
  });
};

const formatDate = (value?: Date | null) =>
  value ? value.toLocaleDateString("de-DE") : "";
</script>
