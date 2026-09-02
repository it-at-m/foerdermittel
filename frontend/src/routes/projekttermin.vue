<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <!-- @vue-generic {Partial<ProjektterminResponseDTO>} -->
      <crud-card
          ref="crudRef"
          v-model="dataTableOptions"
          :empty-item-template="EMPTY_ITEM_TEMPLATE"
          :loading="loading || baseViewLoading"
          :table-headers="headers"
          :domain-key="domainKey"
          :enable-actions="isAdmin"
          :items="projekttermine?.content ?? []"
          :expandable="true"
          :total-items="projekttermine?.page?.totalElements ?? 0"
          @delete="handleDelete"
          @create="handleCreate"
          @update="handleUpdate"
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

        <template #[`item.speicherRechnungen`]="{ item }">
          <v-icon
              v-if="item.speicherRechnungen"
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
import {
  useCreateProjekttermin,
  useDeleteProjekttermin,
  useGetProjekttermin,
  useGetProjektterminFormContext,
  useUpdateProjekttermin,
} from "@/composables/api/useProjektterminApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import usePagination from "@/composables/usePagination";
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
    width: 110,
  },
  {
    title: t("model.termin.telefon"),
    value: "telefon",
    align: "center",
    width: 110,
  },
  {
    title: t("model.termin.zustaendig"),
    value: "zustaendig",
    align: "center",
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

const {
  data: projektterminFormContext,
  call: getProjektterminFormContext,
  loading: getProjektterminFormContextLoading,
} = useGetProjektterminFormContext();

const {
  data: projekttermine,
  call: getProjekttermineintraege,
  loading: getProjektterminLoading,
} = useGetProjekttermin();

const projekte = computed(() => projektterminFormContext.value?.projekte ?? []);

type ProjektterminFormType = InstanceType<typeof ProjektterminForm>;
const projektterminFormRef = useTemplateRef<ProjektterminFormType>("projektterminForm");

const { dataTableOptions, onSuccess, onFailure } = usePagination(
    computed(() => projekttermine.value?.page?.totalPages),
    getProjekttermineintraege,
    isAdmin,
    getProjektterminFormContext,
    async () => {
      await projektterminFormRef.value?.validate();
    }
);

const {
  call: createProjekttermin,
  loading: createProjektterminLoading,
  error: createProjektterminError,
} = useCreateProjekttermin();

const handleCreate = async (projektterminCreateDTO: Partial<ProjektterminResponseDTO>) => {
  const model = projektterminCreateDTO as ProjektterminResponseDTO;

  await createProjekttermin({
    projektterminCreateDTO: model,
  });

  if (!createProjektterminError.value) {
    await onSuccess(t("common.message.created", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.createdError", [t(domainKey)]));
  }
};

const {
  call: updateProjekttermin,
  loading: updateProjektterminLoading,
  error: updateProjektterminError,
} = useUpdateProjekttermin();

const handleUpdate = async (projektterminUpdateDTO: Partial<ProjektterminResponseDTO>) => {
  const model = projektterminUpdateDTO as ProjektterminResponseDTO;
  await updateProjekttermin({
    id: model.id,
    projektterminUpdateDTO: model,
  });

  if (!updateProjektterminError.value) {
    await onSuccess(t("common.message.updated", [t(domainKey)]));
  } else {
    onFailure(t("common.message.updatedError", [t(domainKey)]));
  }
};

const {
  call: deleteProjekttermin,
  loading: deleteProjektterminLoading,
  error: deleteProjektterminError,
} = useDeleteProjekttermin();

const handleDelete = async (id: string) => {
  await deleteProjekttermin({
    id: Number(id),
  });
  if (!deleteProjektterminError.value) {
    await onSuccess(t("common.message.deleted", [t(domainKey)]));
  } else {
    onFailure(t("common.message.deletedError", [t(domainKey)]));
  }
};

const formatDate = (value?: Date | null) =>
    value ? value.toLocaleDateString("de-DE") : "";

const loading = computed(
    () =>
        getProjektterminLoading.value ||
        getProjektterminFormContextLoading.value ||
        createProjektterminLoading.value ||
        updateProjektterminLoading.value ||
        deleteProjektterminLoading.value
);
</script>