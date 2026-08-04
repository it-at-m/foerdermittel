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
            :archiv-form-context="archivFormContext"
            @is-valid="updateValidity"
          />
        </template>

        <template #item.speicherAkt="{ item }">
          <v-icon
            v-if="item.speicherAkt"
            :icon="mdiCheck"
          />
        </template>

        <template #item.speicherRechnungen="{ item }">
          <v-icon
            v-if="item.speicherRechnungen"
            :icon="mdiCheck"
          />
        </template>

        <template #item.speicherDatum="{ item }">
          {{ formatDate(item.speicherDatum) }}
        </template>

        <template #item.mikroDatPlan="{ item }">
          {{ formatDate(item.mikroDatPlan) }}
        </template>

        <template #item.mikroDat="{ item }">
          {{ formatDate(item.mikroDat) }}
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { ArchivResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { mdiCheck } from "@mdi/js";
import { computed, onMounted, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import ArchivForm from "@/components/forms/ArchivForm.vue";
import {
  useCreateArchiv,
  useDeleteArchiv,
  useGetArchive,
  useGetArchivFormContext,
  useUpdateArchiv,
} from "@/composables/api/useArchivApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import usePagination from "@/composables/usePagination";
import { Role } from "@/types/Role";

const domainKey = "model.archiv.modelName";

const isAdmin = useHasAnyRole(Role.ADMIN);

const { t } = useI18n();

function formatDate(value?: string) {
  if (!value) return "";

  const date = new Date(value);

  if (Number.isNaN(date.getTime())) {
    return value;
  }

  return date.toLocaleDateString("de-DE", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
  });
}

const headers: DataTableHeader<Partial<ArchivResponseDTO>>[] = [
  {
    title: t("model.archiv.projnr"),
    value: "projnr",
    align: "center",
    width: 120,
  },
  {
    title: t("model.archiv.speicherDatum"),
    value: "speicherDatum",
  },
  {
    title: t("model.archiv.speicherAkt"),
    value: "speicherAkt",
    align: "center",
  },
  {
    title: t("model.archiv.speicherRechnungen"),
    value: "speicherRechnungen",
    align: "center",
  },
  {
    title: t("model.archiv.mikroDatPlan"),
    value: "mikroDatPlan",
  },
  {
    title: t("model.archiv.mikroDat"),
    value: "mikroDat",
  },
  {
    title: t("model.archiv.fob_fb"),
    value: "fobFb",
    align: "center",
  },
  {
    title: t("model.archiv.pstrasse"),
    value: "pstrasse",
  },
  {
    title: t("model.archiv.pname"),
    value: "pname",
  },
  {
    title: t("model.archiv.notizen"),
    value: "notizen",
  },
];

const EMPTY_ITEM_TEMPLATE: Partial<ArchivResponseDTO> = {
  id: undefined,
  speicherDatum: undefined,
  speicherAkt: false,
  speicherRechnungen: false,
  mikroDatPlan: undefined,
  mikroDat: undefined,
  notizen: "",
  projnr: "",
  pname: "",
  pstrasse: "",
  fobFb: undefined,
};

const {
  data: archive,
  call: getArchive,
  loading: getArchiveLoading,
} = useGetArchive();

const {
  data: archivFormContext,
  call: getArchivFormContext,
  loading: getArchivFormContextLoading,
} = useGetArchivFormContext();

type ArchivFormType = InstanceType<typeof ArchivForm>;

const archivFormRef = useTemplateRef<ArchivFormType>("archivForm");

onMounted(async () => {
  await getArchive();
  await getArchivFormContext();
});

const { dataTableOptions, onSuccess, onFailure } = usePagination(
  computed(() => archive.value?.page?.totalPages),
  getArchive,
  isAdmin,
  getArchivFormContext,
  () => archivFormRef.value?.validate()
);

const {
  call: createArchiv,
  loading: createArchivLoading,
  error: createArchivError,
} = useCreateArchiv();

const handleCreate = async (archivCreateDTO: Partial<ArchivResponseDTO>) => {
  await createArchiv({
    archivCreateDTO: archivCreateDTO as ArchivResponseDTO,
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
    await onFailure(t("common.message.updatedError", [t(domainKey)]));
  }
};

const {
  call: deleteArchiv,
  loading: deleteArchivLoading,
  error: deleteArchivError,
} = useDeleteArchiv();

const handleDelete = async (id: string) => {
  await deleteArchiv({
    id,
  });

  if (!deleteArchivError.value) {
    await onSuccess(t("common.message.deleted", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.deletedError", [t(domainKey)]));
  }
};

const loading = computed(
  () =>
    getArchiveLoading.value ||
    getArchivFormContextLoading.value ||
    createArchivLoading.value ||
    updateArchivLoading.value ||
    deleteArchivLoading.value
);
</script>
