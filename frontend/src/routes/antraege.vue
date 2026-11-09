<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="baseViewLoading"
        :table-headers="headers"
        :api="antragApi"
        :domain-key="domainKey"
        :enable-actions="true"
        :should-load-form-context="true"
        :handle-create="handleCreate"
        :handle-update="handleUpdate"
        :handle-delete="handleDelete"
        :form-ref="antragFormRef"
        :expandable="true"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <antrag-form
            v-if="antragFormContext"
            ref="antragForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :projekte="projekte"
            @is-valid="updateValidity"
          />
        </template>

        <template
          v-for="field in SHORT_HEADER_FIELDS"
          :key="field"
          #[`header.${field}`]="{ column }"
        >
          <span v-tooltip:top="t(`model.antrag.${field}`)">
            {{ column.title }}
          </span>
        </template>

        <template #[`item.vorzbeg`]="{ item }">
          <v-icon
            v-if="item.vorzbeg"
            :icon="mdiCheck"
          />
        </template>

        <template #expanded="{ item }">
          <div class="pa-4">
            <div class="text-subtitle-2 mb-2">
              {{ t("model.antrag.notizen") }}
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
import type { AntragResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { mdiCheck } from "@mdi/js";
import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import AntragForm from "@/components/forms/AntragForm.vue";
import { useAntragApi } from "@/composables/api/useAntragApi";
import { Role } from "@/types/Role";

const domainKey = "model.antrag.modelName";

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

const formatBetrag = (value?: number) =>
  value === undefined ? "" : value.toLocaleString("de-DE");

const SHORT_HEADER_FIELDS = ["asuZ", "asuD", "asuK", "vorzbeg"] as const;

// key is the backend sort path, value the displayed DTO field
const headers: DataTableHeader<Partial<AntragResponseDTO>>[] = [
  {
    title: t("model.antrag.projnr"),
    key: "projekt.projnr",
    value: "projnr",
    align: "center",
    width: 90,
  },
  {
    title: t("model.antrag.fobFb"),
    key: "projekt.fobFb.fb",
    value: "fobFb",
    align: "center",
    width: 50,
  },
  {
    title: t("model.antrag.pname"),
    key: "projekt.pname",
    value: "pname",
    align: "start",
    width: 180,
  },
  {
    title: t("model.antrag.antragstyp"),
    value: "antragstyp",
    align: "center",
    width: 50,
  },
  {
    title: t("model.antrag.antragsdatum"),
    key: "antragsdatum",
    value: (item) =>
      item.antragsdatum?.toLocaleDateString("de-DE", {
        day: "2-digit",
        month: "2-digit",
        year: "numeric",
      }) ?? "",
    align: "center",
    width: 110,
  },
  {
    title: t("model.antrag.spalte.asuZ"),
    key: "asuZ",
    value: (item) => formatBetrag(item.asuZ),
    align: "end",
    width: 110,
  },
  {
    title: t("model.antrag.spalte.asuD"),
    key: "asuD",
    value: (item) => formatBetrag(item.asuD),
    align: "end",
    width: 110,
  },
  {
    title: t("model.antrag.spalte.asuK"),
    key: "asuK",
    value: (item) => formatBetrag(item.asuK),
    align: "end",
    width: 110,
  },
  {
    title: t("model.antrag.spalte.vorzbeg"),
    value: "vorzbeg",
    align: "center",
    width: 50,
  },
];

const EMPTY_ITEM_TEMPLATE: Partial<AntragResponseDTO> = {
  projnr: undefined,
  antragstyp: undefined,
  antragsdatum: undefined,
  geskosten: undefined,
  zwfkosten: undefined,
  vorzbeg: false,
  vbdatum: undefined,
  unbeddat: undefined,
  unbedja: undefined,
  unbedbis: undefined,
  asuZ: undefined,
  asuD: undefined,
  asuK: undefined,
  bvorSuZ: undefined,
  bvorSuD: undefined,
  bvorSuK: undefined,
  notizen: undefined,
};

const antragApi = useAntragApi();

const antragFormContext = computed(() => antragApi.context?.data.value);

const projekte = computed(() => antragFormContext.value?.projekte ?? []);

type AntragFormType = InstanceType<typeof AntragForm>;
const antragFormRef = useTemplateRef<AntragFormType>("antragForm");

const handleCreate = async (antragCreateDTO: Partial<AntragResponseDTO>) => {
  const model = antragCreateDTO as AntragResponseDTO;

  await antragApi.create?.call({
    antragCreateDTO: model,
  });
};

const handleUpdate = async (antragUpdateDTO: Partial<AntragResponseDTO>) => {
  const model = antragUpdateDTO as AntragResponseDTO;
  await antragApi.update?.call({
    id: model.id,
    antragUpdateDTO: model,
  });
};

const handleDelete = async (id: string) => {
  await antragApi.delete?.call({
    id,
  });
};
</script>
