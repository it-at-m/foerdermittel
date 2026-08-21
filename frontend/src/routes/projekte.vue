<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <!-- @vue-generic {Partial<ProjektResponseDTO>} -->
      <crud-card
        ref="crudRef"
        v-model="dataTableOptions"
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="loading || baseViewLoading"
        :table-headers="headers"
        :domain-key="domainKey"
        :enable-actions="true"
        :items="projekte?.content ?? []"
        :total-items="projekte?.page?.totalElements ?? 0"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <projekt-form
            :model-value="item"
            :display-mode="inputDisplayMode"
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { ProjektResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { computed } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import ProjektForm from "@/components/forms/ProjektForm.vue";
import { useGetProjekte } from "@/composables/api/useProjektApi";
import usePagination from "@/composables/usePagination";

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

const EMPTY_ITEM_TEMPLATE: Partial<ProjektResponseDTO> = {
  projnr: "",
  fobFb: undefined,
  kurKurzbez: "",
  uasUa: "",
  pname: "",
  pstrasse: "",
};

const {
  data: projekte,
  call: getProjekte,
  loading: getProjekteLoading,
} = useGetProjekte();

const { dataTableOptions } = usePagination(
  computed(() => projekte.value?.page?.totalPages),
  getProjekte
);

const loading = computed(() => getProjekteLoading.value);
</script>