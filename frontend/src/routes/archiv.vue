<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <!-- @vue-generic {Partial<ArchivResponseDTO>} -->
      <crud-card
        ref="crudRef"
        v-model="dataTableOptions"
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="loading || baseViewLoading"
        :table-headers="headers"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :items="archive?.content ?? []"
        :expandable="true"
        :total-items="archive?.page?.totalElements ?? 0"
        @delete="handleDelete"
        @create="handleCreate"
        @update="handleUpdate"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <archiv-form
            v-if="archivFormContext"
            ref="archivForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :projekte="projekte"
            @is-valid="updateValidity"
          />
        </template>

        <template #[`item.speicherAkt`]="{ item }">
          <v-icon
            v-if="item.speicherAkt"
            :icon="mdiCheck"
          />
        </template>

        <template #[`item.speicherRechnungen`]="{ item }">
          <v-icon
            v-if="item.speicherRechnungen"
            :icon="mdiCheck"
          />
        </template>

        <template #[`item.speicherDatum`]="{ item }">
          {{ formatDate(item.speicherDatum) }}
        </template>

        <template #[`item.mikroDatPlan`]="{ item }">
          {{ formatDate(item.mikroDatPlan) }}
        </template>

        <template #[`item.mikroDat`]="{ item }">
          {{ formatDate(item.mikroDat) }}
        </template>

        <template #expanded="{ item }">
          <div class="pa-4">
            <div class="text-subtitle-2 mb-2">
              {{ t("model.archiv.notizen") }}
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
import type { ArchivResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { mdiCheck } from "@mdi/js";
import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import ArchivForm from "@/components/forms/ArchivForm.vue";
import {
  useCreateArchiv,
  useDeleteArchiv,
  useGetArchiv,
  useGetArchivFormContext,
  useUpdateArchiv,
} from "@/composables/api/useArchivApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import usePagination from "@/composables/usePagination";
import { Role } from "@/types/Role";

const domainKey = "model.archiv.modelName";

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

const headers: DataTableHeader<Partial<ArchivResponseDTO>>[] = [
  {
    title: t("model.archiv.projnr"),
    value: "projnr",
    align: "center",
    width: 100,
  },
  {
    title: t("model.archiv.speicherDatum"),
    value: "speicherDatum",
    align: "center",
    width: 110,
  },
  {
    title: t("model.archiv.speicherAkt"),
    value: "speicherAkt",
    align: "center",
    width: 110,
  },
  {
    title: t("model.archiv.speicherRechnungen"),
    value: "speicherRechnungen",
    align: "center",
    width: 110,
  },
  {
    title: t("model.archiv.mikroDatPlan"),
    value: "mikroDatPlan",
    align: "center",
    width: 110,
  },
  {
    title: t("model.archiv.mikroDat"),
    value: "mikroDat",
    align: "center",
    width: 110,
  },
  {
    title: t("model.archiv.fobFb"),
    value: "fobFb",
    align: "center",
    width: 50,
  },
  {
    title: t("model.archiv.pstrasse"),
    value: "pstrasse",
    align: "start",
    width: 180,
  },
  {
    title: t("model.archiv.pname"),
    value: "pname",
    align: "start",
    width: 150,
  },
];

const EMPTY_ITEM_TEMPLATE: Partial<ArchivResponseDTO> = {
  projnr: undefined,
  speicherDatum: undefined,
  mikroDatPlan: undefined,
  mikroDat: undefined,
  speicherAkt: false,
  speicherRechnungen: false,
  notizen: undefined,
};

const {
  data: archivFormContext,
  call: getArchivFormContext,
  loading: getArchivFormContextLoading,
} = useGetArchivFormContext();

const {
  data: archive,
  call: getArchiveintraege,
  loading: getArchivLoading,
} = useGetArchiv();

const projekte = computed(() => archivFormContext.value?.projekte ?? []);

type ArchivFormType = InstanceType<typeof ArchivForm>;
const archivFormRef = useTemplateRef<ArchivFormType>("archivForm");

const { dataTableOptions, onSuccess, onFailure } = usePagination(
  computed(() => archive.value?.page?.totalPages),
  getArchiveintraege,
  isAdmin,
  getArchivFormContext,
  async () => {
    await archivFormRef.value?.validate();
  }
);

const {
  call: createArchiv,
  loading: createArchivLoading,
  error: createArchivError,
} = useCreateArchiv();

const handleCreate = async (archivCreateDTO: Partial<ArchivResponseDTO>) => {
  const model = archivCreateDTO as ArchivResponseDTO;

  await createArchiv({
    archivCreateDTO: model,
  });

  if (!createArchivError.value) {
    await onSuccess(t("common.message.created", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.createdError", [t(domainKey)]));
  }
};

const {
  call: updateArchiv,
  loading: updateArchivLoading,
  error: updateArchivError,
} = useUpdateArchiv();

const handleUpdate = async (archivUpdateDTO: Partial<ArchivResponseDTO>) => {
  const model = archivUpdateDTO as ArchivResponseDTO;
  await updateArchiv({
    id: model.id,
    archivUpdateDTO: model,
  });

  if (!updateArchivError.value) {
    await onSuccess(t("common.message.updated", [t(domainKey)]));
  } else {
    onFailure(t("common.message.updatedError", [t(domainKey)]));
  }
};

const {
  call: deleteArchiv,
  loading: deleteArchivLoading,
  error: deleteArchivError,
} = useDeleteArchiv();

const handleDelete = async (id: string) => {
  await deleteArchiv({
    id: Number(id),
  });
  if (!deleteArchivError.value) {
    await onSuccess(t("common.message.deleted", [t(domainKey)]));
  } else {
    onFailure(t("common.message.deletedError", [t(domainKey)]));
  }
};

const formatDate = (value?: string | null) =>
  value ? value.split("-").reverse().join(".") : "";

const loading = computed(
  () =>
    getArchivLoading.value ||
    getArchivFormContextLoading.value ||
    createArchivLoading.value ||
    updateArchivLoading.value ||
    deleteArchivLoading.value
);
</script>
