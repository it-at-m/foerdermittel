<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="baseViewLoading"
        :table-headers="headers"
        :filter-keys="['projnr', 'fobFb']"
        :api="projektApi"
        :domain-key="domainKey"
        :handle-create="handleCreate"
        :handle-update="handleUpdate"
        :handle-delete="handleDelete"
        :form-ref="projektFormRef"
        should-load-form-context
        :expandable="true"
        :filter-default="{ projnr: undefined, fobFb: undefined }"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <projekt-form
            v-if="projektFormContext"
            ref="projektForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :projekt-form-context="projektFormContext"
            @is-valid="updateValidity"
          />
        </template>

        <template #filter-form="{ filter, updateValidity }">
          <v-form @update:model-value="updateValidity">
            <v-row>
              <v-col
                cols="12"
                md="4"
              >
                <v-text-field
                  v-model="filter.projnr"
                  :label="t('model.projekt.projnr')"
                  density="compact"
                  clearable
                />
              </v-col>

              <v-col
                cols="12"
                md="4"
              >
                <v-text-field
                  v-model="filter.fobFb"
                  :label="t('model.projekt.fobFb')"
                  density="compact"
                  clearable
                />
              </v-col>
              <v-col
                cols="12"
                md="4"
              >
                <v-text-field
                  v-model="filter.kurKurzbez"
                  :label="t('model.projekt.kurKurzbez')"
                  density="compact"
                  clearable
                />
              </v-col>
            </v-row>
            <v-row>
              <v-col
                cols="12"
                md="4"
              >
                <v-text-field
                  v-model="filter.uasUa"
                  :label="t('model.projekt.uasUa')"
                  density="compact"
                  clearable
                />
              </v-col>
              <v-col
                cols="12"
                md="4"
              >
                <v-text-field
                  v-model="filter.pname"
                  :label="t('model.projekt.pname')"
                  density="compact"
                  clearable
                />
              </v-col>

              <v-col
                cols="12"
                md="4"
              >
                <v-text-field
                  v-model="filter.pstrasse"
                  :label="t('model.projekt.pstrasse')"
                  density="compact"
                  clearable
                />
              </v-col>
            </v-row>
          </v-form>
        </template>

        <template #expanded="{ item }">
          <div class="pa-4">
            <v-row>
              <v-col
                v-for="field in expandedFields"
                :key="field"
                cols="12"
                md="2"
              >
                <div class="text-subtitle-2 mb-2">
                  {{ t(`model.projekt.${field}`) }}
                </div>
                <div>{{ item[field] ?? "—" }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-divider />
              <v-col cols="12">
                <div class="text-subtitle-2 mb-2">
                  {{ t("model.projekt.notizen") }}
                </div>
                <div style="white-space: pre-wrap">
                  {{ item.notizen || "—" }}
                </div>
              </v-col>
            </v-row>
          </div>
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { ProjektResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import ProjektForm from "@/components/forms/ProjektForm.vue";
import { useProjektApi } from "@/composables/api/useProjektApi";
import { Role } from "@/types/Role";

definePage({
  meta: {
    hasAnyRole: [
      Role.SACHBEARBEITUNG,
      Role.SACHBEARBEITUNG_HAUSHALT,
      Role.ADMIN,
    ],
  },
});

const domainKey = "model.projekt.modelName";

const { t } = useI18n();

const headers: DataTableHeader<Partial<ProjektResponseDTO>>[] = [
  {
    title: t("model.projekt.projnr"),
    value: "projnr",
    sortable: true,
  },
  {
    title: t("model.projekt.fobFb"),
    value: "fobFb",
    sortable: true,
  },
  {
    title: t("model.projekt.kurKurzbez"),
    value: "kurKurzbez",
    sortable: true,
  },
  {
    title: t("model.projekt.uasUa"),
    value: "uasUa",
    sortable: true,
  },
  {
    title: t("model.projekt.pname"),
    value: "pname",
    sortable: true,
  },
  {
    title: t("model.projekt.pstrasse"),
    value: "pstrasse",
    sortable: true,
  },
];

const expandedFields = [
  "projart",
  "bezStadtbezirk",
  "krnKrhname",
  "sgtSiedlungsgebiet",
  "bpgBauprogramm",
  "krisofp",
  "krhzweck",
  "refinanzierbar",
  "kauf",
  "kripplatz",
  "kigaplatz",
  "hortplatz",
] as const satisfies readonly (keyof ProjektResponseDTO)[];

const EMPTY_ITEM_TEMPLATE: Partial<ProjektResponseDTO> = {
  id: "",
  projnr: "",
  fobFb: undefined,
  bezStadtbezirk: undefined,
  krnKrhname: undefined,
  kurKurzbez: undefined,
  uasUa: undefined,
  sgtSiedlungsgebiet: undefined,
  jahr: undefined,
  lfdnr1: undefined,
  lfdnr2: undefined,
  pname: "",
  pstrasse: "",
  projart: "",
  refinanzierbar: undefined,
  kauf: undefined,
  kripplatz: undefined,
  kigaplatz: undefined,
  hortplatz: undefined,
  notizen: "",
  bpgBauprogramm: undefined,
  krisofp: undefined,
  krhzweck: undefined,
};

const projektApi = useProjektApi();

const projektFormContext = computed(() => projektApi.context?.data.value);

type ProjektFormType = InstanceType<typeof ProjektForm>;
const projektFormRef = useTemplateRef<ProjektFormType>("projektForm");

const handleCreate = async (projektCreateDTO: Partial<ProjektResponseDTO>) => {
  const model = projektCreateDTO as ProjektResponseDTO;

  await projektApi.create?.call({
    projektCreateDTO: model,
  });
};

const handleUpdate = async (projektUpdateDTO: Partial<ProjektResponseDTO>) => {
  const model = projektUpdateDTO as ProjektResponseDTO;

  await projektApi.update?.call({
    id: model.projnr,
    projektUpdateDTO: model,
  });
};

const handleDelete = async (id: string) => {
  await projektApi.delete?.call({
    id,
  });
};
</script>
