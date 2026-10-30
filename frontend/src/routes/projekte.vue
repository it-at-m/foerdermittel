<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="baseViewLoading"
        :table-headers="headers"
        :filter-keys="['projnr']"
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
              <v-col cols="12" md="2">
                <div class="text-subtitle-2 mb-2">
                  {{ t("model.projekt.stadtbezirk") }}
                </div>
                <div>{{ item.stadtbezirk ?? "—" }}</div>
              </v-col>

              <v-col cols="12" md="2">
                <div class="text-subtitle-2 mb-2">
                  {{ t("model.projekt.krhname") }}
                </div>
                <div>{{ item.krhname ?? "—" }}</div>
              </v-col>

              <v-col cols="12" md="2">
                <div class="text-subtitle-2 mb-2">
                  {{ t("model.projekt.siedlungsgebiet") }}
                </div>
                <div>{{ item.siedlungsgebiet ?? "—" }}</div>
              </v-col>

              <v-col cols="12" md="2">
                <div class="text-subtitle-2 mb-2">
                  {{ t("model.projekt.bauprogramm") }}
                </div>
                <div>{{ item.bauprogramm ?? "—" }}</div>
              </v-col>

              <v-col cols="12" md="2">
                <div class="text-subtitle-2 mb-2">
                  {{ t("model.projekt.krisofp") }}
                </div>
                <div>{{ item.krisofp ?? "—" }}</div>
              </v-col>

              <v-col cols="12" md="2">
                <div class="text-subtitle-2 mb-2">
                  {{ t("model.projekt.krhzweck") }}
                </div>
                <div>{{ item.krhzweck ?? "—" }}</div>
              </v-col>

              <v-col cols="12" md="2">
                <div class="text-subtitle-2 mb-2">
                  {{ t("model.projekt.refinanzierbar") }}
                </div>
                <div>{{ item.refinanzierbar ?? "—" }}</div>
              </v-col>

              <v-col cols="12" md="2">
                <div class="text-subtitle-2 mb-2">
                  {{ t("model.projekt.kauf") }}
                </div>
                <div>{{ item.kauf ?? "—" }}</div>
              </v-col>

              <v-col cols="12" md="2">
                <div class="text-subtitle-2 mb-2">
                  {{ t("model.projekt.kripplatz") }}
                </div>
                <div>{{ item.kripplatz ?? "—" }}</div>
              </v-col>

              <v-col cols="12" md="2">
                <div class="text-subtitle-2 mb-2">
                  {{ t("model.projekt.kigaplatz") }}
                </div>
                <div>{{ item.kigaplatz ?? "—" }}</div>
              </v-col>

              <v-col cols="12" md="2">
                <div class="text-subtitle-2 mb-2">
                  {{ t("model.projekt.hortplatz") }}
                </div>
                <div>{{ item.hortplatz ?? "—" }}</div>
              </v-col>

              <v-col cols="12">
                <div class="text-subtitle-2 mb-2">
                  {{ t("model.projekt.notizen") }}
                </div>
                <div style="white-space: pre-wrap">{{ item.notizen || "—" }}</div>
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
    title: t("model.projekt.kurzbez"),
    value: "kurzbez",
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
  {
    title: t("model.projekt.projart"),
    value: "projart",
    sortable: true,
  },
];

const EMPTY_ITEM_TEMPLATE: Partial<ProjektResponseDTO> = {
  projnr: "",
  fobFb: undefined,
  stadtbezirk: undefined,
  krhname: undefined,
  kurzbez: undefined,
  uasUa: undefined,
  siedlungsgebiet: undefined,
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
  bauprogramm: undefined,
  krisofp: undefined,
  krhzweck: undefined,
};

const projektApi = useProjektApi();

const projektFormContext = computed(() => projektApi.context.data.value);

type ProjektFormType = InstanceType<typeof ProjektForm>;
const projektFormRef = useTemplateRef<ProjektFormType>("projektForm");

const handleCreate = async (projektCreateDTO: Partial<ProjektResponseDTO>) => {
  const model = projektCreateDTO as ProjektResponseDTO;
  await projektApi.create.call({
    projektCreateDTO: model,
  });
};

const handleUpdate = async (projektUpdateDTO: Partial<ProjektResponseDTO>) => {
  const model = projektUpdateDTO as ProjektResponseDTO;
  await projektApi.update.call({
    id: model.projnr,
    projektUpdateDTO: model,
  });
};

const handleDelete = async (id: string) => {
  await projektApi.delete.call({
    id,
  });
};
</script>
