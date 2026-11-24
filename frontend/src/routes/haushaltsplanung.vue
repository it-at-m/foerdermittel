<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="baseViewLoading"
        :table-headers="headers"
        :api="hhjahrApi"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :should-load-form-context="isAdmin"
        :expandable="true"
      >
        <template #form="{}" />

        <template #[`item.hhjahr`]="{ item }">
          <v-chip
            v-tooltip:end="item.hhbezeichnung"
            label
            :append-icon="mdiArrowTopRight"
            :to="{
              path: '/haushaltsplanung/haushaltszahlen',
              query: {
                hhjahr: String(item.hhjahr),
              },
            }"
            @click.stop
          >
            {{ item.hhjahr }}
          </v-chip>
        </template>
        <template #expanded="{ item }">
          <div class="pa-4">
            <div class="text-subtitle-2 mb-2">
              {{ t("model.haushaltsjahr.notizen") }}
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
import type { HhjahrResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { mdiArrowTopRight } from "@mdi/js";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import { useHaushaltsjahrApi } from "@/composables/api/useHaushaltsjahrApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import { Role } from "@/types/Role";

const domainKey = "model.haushaltsplanung.modelName";

const { t } = useI18n();

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

const headers: DataTableHeader<Partial<HhjahrResponseDTO>>[] = [
  {
    title: t("model.haushaltsjahr.hhjahr"),
    value: "hhjahr",
    width: 150,
  },
  {
    title: t("model.haushaltsjahr.hhbezeichnung"),
    value: "hhbezeichnung",
  },
];

const EMPTY_ITEM_TEMPLATE: Partial<HhjahrResponseDTO> = {
  hhjahr: undefined,
  hhbezeichnung: "",
  notizen: "",
  assignedProProjnr: [],
};

const hhjahrApi = useHaushaltsjahrApi();
</script>
