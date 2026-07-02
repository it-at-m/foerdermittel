<template>
  <base-view :domain-key="domainKey">
    <!-- @vue-generic {Partial<SiedlungsgebietResponseDTO>} -->
    <crud-card
      ref="crudRef"
      v-model="dataTableOptions"
      :empty-item-template="EMPTY_ITEM_TEMPLATE"
      :loading="loading"
      :table-headers="headers"
      :domain-key="domainKey"
      :enable-actions="isAdmin"
      :items="siedlungsgebiete?.content ?? []"
      :total-items="siedlungsgebiete?.page?.totalElements ?? 0"
      :dialog-width="DialogWidth.MEDIUM"
      @delete="handleDelete"
      @create="handleCreate"
      @update="handleUpdate"
    >
      <template #form="{ item, updateValidity, inputDisplayMode }">
        <siedlungsgebiet-form
          v-if="siedlungsgebietFormContext"
          ref="siedlungsgebietForm"
          :model-value="item"
          :display-mode="inputDisplayMode"
          :siedlungsgebiet-form-context="siedlungsgebietFormContext"
          @is-valid="updateValidity"
        />
      </template>
    </crud-card>
  </base-view>
</template>

<script setup lang="ts">
import type { SiedlungsgebietResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import SiedlungsgebietForm from "@/components/forms/SiedlungsgebietForm.vue";
import {
  useCreateSiedlungsgebiet,
  useDeleteSiedlungsgebiet,
  useGetSiedlungsgebiete,
  useGetSiedlungsgebietFormContext,
  useUpdateSiedlungsgebiet,
} from "@/composables/api/useSiedlungsgebietApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import usePagination from "@/composables/usePagination";
import { DialogWidth } from "@/types/DialogWidth";
import { Role } from "@/types/Role";

const domainKey = "model.siedlungsgebiet.modelName";

const isAdmin = useHasAnyRole(Role.ADMIN);

const { t } = useI18n();

const headers: DataTableHeader<Partial<SiedlungsgebietResponseDTO>>[] = [
  {
    title: t("model.siedlungsgebiet.siedlungsgebiet"),
    value: "siedlungsgebiet",
    align: "center",
    width: 120,
  },
  { title: t("model.siedlungsgebiet.bezeichnung"), value: "bezeichnung" },
];

const EMPTY_ITEM_TEMPLATE: Partial<SiedlungsgebietResponseDTO> = {
  siedlungsgebiet: undefined,
  bezeichnung: "",
};

const {
  data: siedlungsgebiete,
  call: getSiedlungsgebiete,
  loading: getSiedlungsgebieteLoading,
} = useGetSiedlungsgebiete();

const {
  data: siedlungsgebietFormContext,
  call: getSiedlungsgebietFormContext,
  loading: getSiedlungsgebietFormContextLoading,
} = useGetSiedlungsgebietFormContext();

type SiedlungsgebietFormType = InstanceType<typeof SiedlungsgebietForm>;
const siedlungsgebietFormRef =
  useTemplateRef<SiedlungsgebietFormType>("siedlungsgebietForm");

const { dataTableOptions, onSuccess, onFailure } = usePagination(
  computed(() => siedlungsgebiete.value?.page?.totalPages),
  getSiedlungsgebiete,
  getSiedlungsgebietFormContext,
  () => siedlungsgebietFormRef.value?.validate()
);

const {
  call: createSiedlungsgebiet,
  loading: createSiedlungsgebietLoading,
  error: createSiedlungsgebieteError,
} = useCreateSiedlungsgebiet();

const handleCreate = async (
  siedlungsgebietCreateDTO: Partial<SiedlungsgebietResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = siedlungsgebietCreateDTO as SiedlungsgebietResponseDTO;
  await createSiedlungsgebiet({
    siedlungsgebietCreateDTO: model,
  });
  if (!createSiedlungsgebieteError.value) {
    await onSuccess(t("common.message.created", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.createdError", [t(domainKey)]));
  }
};

const {
  call: updateSiedlungsgebiet,
  loading: updateSiedlungsgebietLoading,
  error: updateSiedlungsgebietError,
} = useUpdateSiedlungsgebiet();

const handleUpdate = async (
  siedlungsgebietUpdateDTO: Partial<SiedlungsgebietResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = siedlungsgebietUpdateDTO as SiedlungsgebietResponseDTO;
  await updateSiedlungsgebiet({
    id: model.id,
    siedlungsgebietUpdateDTO: model,
  });
  if (!updateSiedlungsgebietError.value) {
    await onSuccess(t("common.message.updated", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.updatedError", [t(domainKey)]));
  }
};

const {
  call: deleteSiedlungsgebiet,
  loading: deleteSiedlungsgebietLoading,
  error: deleteSiedlungsgebietError,
} = useDeleteSiedlungsgebiet();

const handleDelete = async (id: string) => {
  await deleteSiedlungsgebiet({
    id,
  });
  if (!deleteSiedlungsgebietError.value) {
    await onSuccess(t("common.message.deleted", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.deletedError", [t(domainKey)]));
  }
};

const loading = computed(
  () =>
    getSiedlungsgebieteLoading.value ||
    getSiedlungsgebietFormContextLoading.value ||
    createSiedlungsgebietLoading.value ||
    updateSiedlungsgebietLoading.value ||
    deleteSiedlungsgebietLoading.value
);
</script>
