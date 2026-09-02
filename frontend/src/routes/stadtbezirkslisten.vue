<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <crud-card
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="baseViewLoading"
        :table-headers="headers"
        :api="stadtbezirkslisteApi"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :expandable="true"
        :should-load-form-context="isAdmin"
        :handle-create="handleCreate"
        :handle-update="handleUpdate"
        :handle-delete="handleDelete"
        :form-ref="stadtbezirkslisteFormRef"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <stadtbezirksliste-form
            v-if="stadtbezirkslisteApi.context.data"
            ref="stadtbezirkslisteForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :listenname-form-context="stadtbezirkslisteApi.context.data.value!"
            @is-valid="updateValidity"
          />
        </template>

        <template #expanded="{ item }">
          <div>
            <!-- Header -->
            <v-row class="font-weight-bold py-2 pl-6">
              <v-col cols="2">{{
                t("model.stadtbezirksliste.bezStadtbezirk")
              }}</v-col>
              <v-col cols="4">
                {{ t("model.stadtbezirk.bezeichnung") }}
              </v-col>
              <v-col cols="6">{{
                t("model.stadtbezirksliste.listeBezeichnung")
              }}</v-col>
            </v-row>

            <v-divider />

            <v-row
              v-if="!item.assignedStadtbezirke?.length"
              class="py-4 pl-6"
            >
              <v-col
                cols="12"
                class="text-medium-emphasis"
              >
                {{ t("model.stadtbezirksliste.noDistricts") }}
              </v-col>
            </v-row>

            <template
              v-for="stadtbezirk in item.assignedStadtbezirke"
              :key="stadtbezirk.stadtbezirkId"
            >
              <v-row class="py-2 pl-6 d-flex align-center">
                <v-col cols="2">
                  <v-chip
                    :append-icon="mdiArrowRight"
                    to="stadtbezirke"
                  >
                    {{ stadtbezirk.stadtbezirkId }}
                  </v-chip>
                </v-col>
                <v-col cols="4">{{ stadtbezirk.stadtbezirkBezeichnung }}</v-col>
                <v-col cols="6">{{ stadtbezirk.bezeichnung }}</v-col>
              </v-row>

              <v-divider />
            </template>
          </div>
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { StadtbezirkslisteResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { mdiArrowRight } from "@mdi/js";
import { useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import StadtbezirkslisteForm from "@/components/forms/StadtbezirkslisteForm.vue";
import { useStadtbezirkslisteApi } from "@/composables/api/useStadtbezirkslisteApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import { Role } from "@/types/Role";

const domainKey = "model.stadtbezirksliste.modelName";

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

const headers: DataTableHeader<Partial<StadtbezirkslisteResponseDTO>>[] = [
  {
    title: t("model.stadtbezirksliste.lnaKurzbez"),
    value: "kurzbez",
    width: 120,
  },
  {
    title: t("model.stadtbezirksliste.bezeichnung"),
    value: "bezeichnung",
  },
];

const EMPTY_ITEM_TEMPLATE: Partial<StadtbezirkslisteResponseDTO> = {
  kurzbez: "",
  bezeichnung: "",
  assignedStadtbezirke: [],
};

const stadtbezirkslisteApi = useStadtbezirkslisteApi();

type StadtbezirkslisteFormType = InstanceType<typeof StadtbezirkslisteForm>;

const stadtbezirkslisteFormRef = useTemplateRef<StadtbezirkslisteFormType>(
  "stadtbezirkslisteForm"
);

const handleCreate = async (
  stadtbezirkslisteCreateDTO: Partial<StadtbezirkslisteResponseDTO>
) => {
  const model = stadtbezirkslisteCreateDTO as StadtbezirkslisteResponseDTO;

  await stadtbezirkslisteApi.create.call({
    listennameCreateDTO: model,
  });
};

const handleUpdate = async (
  stadtbezirkslisteUpdateDTO: Partial<StadtbezirkslisteResponseDTO>
) => {
  const model = stadtbezirkslisteUpdateDTO as StadtbezirkslisteResponseDTO;

  await stadtbezirkslisteApi.update.call({
    id: model.id,
    listennameUpdateDTO: model,
  });
};

const handleDelete = async (id: string) => {
  await stadtbezirkslisteApi.delete.call({
    id,
  });
};
</script>
