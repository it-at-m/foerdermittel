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
        <template #form="{ item, updateItem, updateValidity, inputDisplayMode }">
          <archiv-form
            ref="archivForm"
            :model-value="item"
            @update:model-value="updateItem"
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
import { formatDate } from "@/util/date";

const domainKey = "model.archiv.modelName";

const { t } = useI18n();

const isAdmin = useHasAnyRole(Role.ADMIN);

const {
  data: archivFormContext,
  call: getArchivFormContext,
  loading: archivFormContextLoading,
} = useGetArchivFormContext();

const projekte = computed(() => archivFormContext.value?.projekte ?? []);

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
    width: 120,
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
    width: 120,
  },
  {
    title: t("model.archiv.mikroDat"),
    value: "mikroDat",
    align: "center",
    width: 120,
  },
  {
    title: t("model.archiv.fob_fb"),
    value: "fobFb",
    align: "center",
    width: 50,
  },
  {
    title: t("model.archiv.pstrasse"),
    value: "pstrasse",
    align: "center",
    width: 180,
  },
  {
    title: t("model.archiv.pname"),
    value: "pname",
    align: "center",
    width: 150,
  },
  {
    title: t("model.archiv.notizen"),
    value: "notizen",
    width: 250,
  },
];

const EMPTY_ITEM_TEMPLATE: Partial<ArchivResponseDTO> = {
    projnr: undefined,
    speicherDatum: null,
    mikroDatPlan: null,
    mikroDat: null,
    speicherAkt: false,
    speicherRechnungen: false,
    notizen: "",
};

const {
  data: archive,
  call: getArchive,
  loading: archiveLoading,
} = useGetArchive();

type ArchivFormType = InstanceType<typeof ArchivForm>;

const archivFormRef = useTemplateRef<ArchivFormType>("archivForm");

onMounted(async () => {
  await Promise.all([getArchive(), getArchivFormContext()]);
});

const { dataTableOptions, onSuccess, onFailure } = usePagination(
  computed(() => archive.value?.page?.totalPages),
  getArchive,
  isAdmin,
  undefined,
  () => archivFormRef.value?.validate()
);

const {
  call: createArchiv,
  loading: createLoading,
  error: createError,
} = useCreateArchiv();

async function handleCreate(dto: Partial<ArchivResponseDTO>) {
  await createArchiv({
    archivCreateDTO: dto as ArchivResponseDTO,
  });

  if (createError.value) {
    await onFailure(t("common.message.createdError", [t(domainKey)]));
  } else {
    await onSuccess(t("common.message.created", [t(domainKey)]));
  }
}

const {
  call: updateArchiv,
  loading: updateLoading,
  error: updateError,
} = useUpdateArchiv();

async function handleUpdate(dto: Partial<ArchivResponseDTO>) {
  await updateArchiv({
    id: dto.id!,
    archivUpdateDTO: dto as ArchivResponseDTO,
  });

  if (updateError.value) {
    await onFailure(t("common.message.updatedError", [t(domainKey)]));
  } else {
    await onSuccess(t("common.message.updated", [t(domainKey)]));
  }
}

const {
  call: deleteArchiv,
  loading: deleteLoading,
  error: deleteError,
} = useDeleteArchiv();

async function handleDelete(id: string) {
  await deleteArchiv({ id });

  if (deleteError.value) {
    await onFailure(t("common.message.deletedError", [t(domainKey)]));
  } else {
    await onSuccess(t("common.message.deleted", [t(domainKey)]));
  }
}

const loading = computed(
  () =>
    archiveLoading.value ||
    archivFormContextLoading.value ||
    createLoading.value ||
    updateLoading.value ||
    deleteLoading.value
);
</script>
