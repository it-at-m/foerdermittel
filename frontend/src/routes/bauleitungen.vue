<template>
  <base-view :domain-key="domainKey">
    <template #default="{ baseViewLoading }">
      <!-- @vue-generic {Partial<BauleitungResponseDTO>} -->
      <crud-card
        ref="crudRef"
        v-model="dataTableOptions"
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="loading || baseViewLoading"
        :table-headers="headers"
        :domain-key="domainKey"
        :enable-actions="isAdmin"
        :items="bauleitungen?.content ?? []"
        :total-items="bauleitungen?.page?.totalElements ?? 0"
        @delete="handleDelete"
        @create="handleCreate"
        @update="handleUpdate"
      >
        <template #form="{ item, updateValidity, inputDisplayMode }">
          <bauleitung-form
            v-if="bauleitungFormContext"
            ref="bauleitungForm"
            :model-value="item"
            :display-mode="inputDisplayMode"
            :bauleitung-form-context="bauleitungFormContext"
            @is-valid="updateValidity"
          />
        </template>
      </crud-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { BauleitungResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import BauleitungForm from "@/components/forms/BauleitungForm.vue";
import {
  useCreateBauleitung,
  useDeleteBauleitung,
  useGetBauleitungen,
  useGetBauleitungFormContext,
  useUpdateBauleitung,
} from "@/composables/api/useBauleitungApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import usePagination from "@/composables/usePagination";
import { Role } from "@/types/Role";

const domainKey = "model.bauleitung.modelName";

const isAdmin = useHasAnyRole(Role.ADMIN);

const { t } = useI18n();

const headers: DataTableHeader<Partial<BauleitungResponseDTO>>[] = [
  {
    title: t("model.bauleitung.bauleitung"),
    value: "bauleitung",
    align: "center",
    width: 100,
  },
  { title: t("model.bauleitung.bezeichnung"), value: "bezeichnung" },
];

const EMPTY_ITEM_TEMPLATE: Partial<BauleitungResponseDTO> = {
  bauleitung: undefined,
  bezeichnung: "",
};

const {
  data: bauleitungen,
  call: getBauleitungen,
  loading: getBauleitungenLoading,
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
} = useGetBauleitungen!();

const {
  data: bauleitungFormContext,
  call: getBauleitungFormContext,
  loading: getBauleitungFormContextLoading,
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
} = useGetBauleitungFormContext!();

type BauleitungFormType = InstanceType<typeof BauleitungForm>;
const bauleitungFormRef = useTemplateRef<BauleitungFormType>("bauleitungForm");

const { dataTableOptions, onSuccess, onFailure } = usePagination(
  computed(() => bauleitungen.value?.page?.totalPages),
  getBauleitungen,
  getBauleitungFormContext,
  () => bauleitungFormRef.value?.validate()
);

const {
  call: createBauleitung,
  loading: createBauleitungLoading,
  error: createBauleitungenError,
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
} = useCreateBauleitung!();

const handleCreate = async (
  bauleitungCreateDTO: Partial<BauleitungResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = bauleitungCreateDTO as BauleitungResponseDTO;
  await createBauleitung({
    bauleitungCreateDTO: model,
  });
  if (!createBauleitungenError.value) {
    await onSuccess(t("common.message.created", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.createdError", [t(domainKey)]));
  }
};

const {
  call: updateBauleitung,
  loading: updateBauleitungLoading,
  error: updateBauleitungerror,
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
} = useUpdateBauleitung!();

const handleUpdate = async (
  bauleitungUpdateDTO: Partial<BauleitungResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = bauleitungUpdateDTO as BauleitungResponseDTO;
  await updateBauleitung({
    id: model.id,
    bauleitungUpdateDTO: model,
  });
  if (!updateBauleitungerror.value) {
    await onSuccess(t("common.message.updated", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.updatedError", [t(domainKey)]));
  }
};

const {
  call: deleteBauleitung,
  loading: deleteBauleitungLoading,
  error: deleteBauleitungenrror,
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
} = useDeleteBauleitung!();

const handleDelete = async (id: string) => {
  await deleteBauleitung({
    id,
  });
  if (!deleteBauleitungenrror.value) {
    await onSuccess(t("common.message.deleted", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.deletedError", [t(domainKey)]));
  }
};

const loading = computed(
  () =>
    getBauleitungenLoading.value ||
    getBauleitungFormContextLoading.value ||
    createBauleitungLoading.value ||
    updateBauleitungLoading.value ||
    deleteBauleitungLoading.value
);
</script>
