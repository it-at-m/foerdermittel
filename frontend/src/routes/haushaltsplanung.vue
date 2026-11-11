<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="baseViewLoading"
        :table-headers="headers"
        :api="haushaltsjahrApi"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :expandable="true"
      >
        <template #expanded="{ item }">
          <v-table
            density="compact"
            class="overflow-x-auto"
          >
            <thead>
              <tr>
                <th>{{ t("model.haushaltsplanung.proProjnr") }}</th>
                <th>{{ t("model.haushaltsplanung.fipo") }}</th>
                <th>{{ t("model.projekt.fobFb") }}</th>
                <th>{{ t("model.haushaltsplanung.mitteleinplanung") }}</th>
                <th>{{ t("model.haushaltsplanung.bewilligungVoj") }}</th>
                <th>{{ t("model.haushaltsplanung.bewilligungHhj") }}</th>
                <th>{{ t("model.haushaltsplanung.erhaltenVoj") }}</th>
                <th>{{ t("model.haushaltsplanung.erhaltenHhj") }}</th>
                <th>{{ t("model.haushaltsplanung.baustandaktuell") }}</th>
                <th>{{ t("model.haushaltsplanung.pname") }}</th>
                <th>{{ t("model.haushaltsplanung.pstrasse") }}</th>
                <th>{{ t("model.haushaltsplanung.notizen") }}</th>
                <th>{{ t("model.haushaltsplanung.an") }}</th>
                <th>{{ t("model.haushaltsplanung.n1") }}</th>
                <th>{{ t("model.haushaltsplanung.n2") }}</th>
                <th>{{ t("model.stadtbezirksliste.modelName", 2) }}</th>
              </tr>
            </thead>

            <tbody>
              <tr v-if="!item.assignedProProjnr?.length">
                <td
                  colspan="16"
                  class="text-medium-emphasis"
                >
                  {{ t("model.haushaltsplanung.keineZuordnungen") }}
                </td>
              </tr>

              <tr
                v-for="planung in item.assignedProProjnr"
                :key="planung.id"
              >
                <td>{{ planung.proProjnr }}</td>
                <td>{{ planung.fipo }}</td>
                <td>{{ planung.fobFb }}</td>
                <td>{{ planung.mitteleinplanung }}</td>
                <td>{{ planung.bewilligungVoj }}</td>
                <td>{{ planung.bewilligungHhj }}</td>
                <td>{{ planung.erhaltenVoj }}</td>
                <td>{{ planung.erhaltenHhj }}</td>
                <td>{{ planung.baustandaktuell }}</td>
                <td>{{ planung.pname }}</td>
                <td>{{ planung.pstrasse }}</td>
                <td>{{ planung.notizen }}</td>
                <td>{{ planung.an }}</td>
                <td>{{ planung.n1 }}</td>
                <td>{{ planung.n2 }}</td>
                <td>
                  <div
                    v-for="liste in planung.stadtbezirkslisten"
                    :key="liste.id"
                    class="mb-2"
                  >
                    <div>
                      <strong>{{ liste.kurzbez }}</strong>
                      {{ t("model.haushaltsplanung.trennzeichen") }}
                      {{ liste.bezeichnung }}
                    </div>

                    <div
                      v-for="stadtbezirk in liste.assignedStadtbezirke"
                      :key="stadtbezirk.stadtbezirkId"
                      class="text-medium-emphasis"
                    >
                      {{ stadtbezirk.stadtbezirkId }}
                      {{ t("model.haushaltsplanung.trennzeichen") }}
                      {{ stadtbezirk.stadtbezirkBezeichnung }}

                      <span v-if="stadtbezirk.bezeichnung">
                        {{
                          t("model.haushaltsplanung.bezeichnungInKlammern", {
                            bezeichnung: stadtbezirk.bezeichnung,
                          })
                        }}
                      </span>
                    </div>
                  </div>
                </td>
              </tr>
            </tbody>
          </v-table>
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
    width: 120,
  },
  {
    title: t("model.haushaltsjahr.hhbezeichnung"),
    value: "hhbezeichnung",
    width: 120,
  },
  {
    title: t("model.haushaltsjahr.notizen"),
    value: "notizen",
    width: 120,
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
