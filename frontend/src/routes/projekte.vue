<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :filter-default="FILTER_DEFAULT"
        :loading="baseViewLoading"
        :table-headers="headers"
        :api="projektApi"
        :domain-key="domainKey"
        :handle-create="handleCreate"
        :handle-update="handleUpdate"
        :handle-delete="handleDelete"
        :form-ref="projektFormRef"
        should-load-form-context
        :expandable="true"
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
                <div>
                  {{
                    ["refinanzierbar", "kauf"].includes(field)
                      ? item[field]
                        ? "Ja"
                        : "Nein"
                      : (item[field] ?? "—")
                  }}
                </div>
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
        <template #filter-form="{ filter, updateValidity }">
          <projekt-filter-form
            :model-value="filter"
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type {
  ProjektFilter,
  ProjektResponseDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import ProjektForm from "@/components/forms/ProjektForm.vue";
import { useProjektApi } from "@/composables/api/useProjektApi";
import ProjektFilterForm from "@/components/forms/filter/ProjektFilterForm.vue";
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

const FILTER_DEFAULT: Partial<ProjektFilter> = {
  projnr: undefined,
  fobFb: undefined,
  kurz: undefined,
  ua: undefined,
  pname: undefined,
  pstrasse: undefined,
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
