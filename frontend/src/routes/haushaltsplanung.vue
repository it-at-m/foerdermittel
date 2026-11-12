
<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
          :empty-item-template="EMPTY_ITEM_TEMPLATE"
          :loading="baseViewLoading"
          :table-headers="headers"
          :api="haushaltsjahrApi"
          :domain-key="domainKey"
          :enable-actions="false"
          :expandable="true"
      >
        <template #expanded="{ item }">
          <haushaltszahlen
              :planungen="item.assignedProProjnr ?? []"
              :haushaltsjahr="item.hhjahr"
              :haushaltsbezeichnung="item.hhbezeichnung"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { HhjahrResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import { useHaushaltsjahrApi } from "@/composables/api/useHaushaltsjahrApi";
import Haushaltszahlen from "@/routes/haushaltszahlen.vue";
import { Role } from "@/types/Role";

const domainKey = "model.haushaltsplanung.modelName";

const { t } = useI18n();

definePage({
  meta: {
    hasAnyRole: [
      Role.SACHBEARBEITUNG,
      Role.SACHBEARBEITUNG_HAUSHALT,
      Role.ADMIN,
    ],
  },
});

const headers: DataTableHeader<Partial<HhjahrResponseDTO>>[] = [
  {
    title: t("model.haushaltsjahr.hhjahr"),
    value: "hhjahr",
    width: 120,
  },
  {
    title: t("model.haushaltsjahr.hhbezeichnung"),
    value: "hhbezeichnung",
    width: 200,
  },
  {
    title: t("model.haushaltsjahr.notizen"),
    value: "notizen",
    width: 200,
  },
];

const EMPTY_ITEM_TEMPLATE: Partial<HhjahrResponseDTO> = {
  hhjahr: undefined,
  hhbezeichnung: "",
  notizen: undefined,
  assignedProProjnr: [],
};

const haushaltsjahrApi = useHaushaltsjahrApi();
</script>
