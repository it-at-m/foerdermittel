<template>
  <base-view :domain-key="domainKey">
    <!-- @vue-generic {Partial<siedlungsgebietResponseDTO>} -->
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
import type { siedlungsgebietResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DataTableHeader } from "vuetify/framework";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import CrudCard from "@/components/common/CrudCard.vue";
import siedlungsgebietForm from "@/components/forms/siedlungsgebietForm.vue";
import {
  useCreatesiedlungsgebiet,
  useDeletesiedlungsgebiet,
  useGetsiedlungsgebiete,
  useGetsiedlungsgebietFormContext,
  useUpdatesiedlungsgebiet,
} from "@/composables/api/usesiedlungsgebietApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import usePagination from "@/composables/usePagination";
import { DialogWidth } from "@/types/DialogWidth";
import { Role } from "@/types/Role";

const domainKey = "model.siedlungsgebiet.modelName";

const isAdmin = useHasAnyRole(Role.ADMIN);

const { t } = useI18n();

const headers: DataTableHeader<Partial<siedlungsgebietResponseDTO>>[] = [
  {
    title: t("model.siedlungsgebiet.siedlungsgebiet"),
    value: "siedlungsgebiet",
    align: "center",
    width: 100,
  },
  { title: t("model.siedlungsgebiet.bezeichnung"), value: "bezeichnung" },
];

const EMPTY_ITEM_TEMPLATE: Partial<siedlungsgebietResponseDTO> = {
  siedlungsgebiet: undefined,
  bezeichnung: "",
};

const {
  data: siedlungsgebiete,
  call: getsiedlungsgebiete,
  loading: getsiedlungsgebieteLoading,
} = useGetsiedlungsgebiete();

const {
  data: siedlungsgebietFormContext,
  call: getsiedlungsgebietFormContext,
  loading: getsiedlungsgebietFormContextLoading,
} = useGetsiedlungsgebietFormContext();

type siedlungsgebietFormType = InstanceType<typeof siedlungsgebietForm>;
const siedlungsgebietFormRef = useTemplateRef<siedlungsgebietFormType>(
  "siedlungsgebietForm"
);

const { dataTableOptions, onSuccess, onFailure } = usePagination(
  computed(() => siedlungsgebiete.value?.page?.totalPages),
  getsiedlungsgebiete,
  getsiedlungsgebietFormContext,
  () => siedlungsgebietFormRef.value?.validate()
);

const {
  call: createsiedlungsgebiet,
  loading: createsiedlungsgebietLoading,
  error: createsiedlungsgebieteError,
} = useCreatesiedlungsgebiet();

const handleCreate = async (
  siedlungsgebietCreateDTO: Partial<siedlungsgebietResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = siedlungsgebietCreateDTO as siedlungsgebietResponseDTO;
  await createsiedlungsgebiet({
    siedlungsgebietCreateDTO: model,
  });
  if (!createsiedlungsgebieteError.value) {
    await onSuccess(t("common.message.created", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.createdError", [t(domainKey)]));
  }
};

const {
  call: updatesiedlungsgebiet,
  loading: updatesiedlungsgebietLoading,
  error: updatesiedlungsgebietError,
} = useUpdatesiedlungsgebiet();

const handleUpdate = async (
  siedlungsgebietUpdateDTO: Partial<siedlungsgebietResponseDTO>
) => {
  // TODO: some type checking improvements
  const model = siedlungsgebietUpdateDTO as siedlungsgebietResponseDTO;
  await updatesiedlungsgebiet({
    id: model.id,
    siedlungsgebietUpdateDTO: model,
  });
  if (!updatesiedlungsgebietError.value) {
    await onSuccess(t("common.message.updated", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.updatedError", [t(domainKey)]));
  }
};

const {
  call: deletesiedlungsgebiet,
  loading: deletesiedlungsgebietLoading,
  error: deletesiedlungsgebietError,
} = useDeletesiedlungsgebiet();

const handleDelete = async (id: string) => {
  await deletesiedlungsgebiet({
    id,
  });
  if (!deletesiedlungsgebietError.value) {
    await onSuccess(t("common.message.deleted", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.deletedError", [t(domainKey)]));
  }
};

const loading = computed(
  () =>
    getsiedlungsgebieteLoading.value ||
    getsiedlungsgebietFormContextLoading.value ||
    createsiedlungsgebietLoading.value ||
    updatesiedlungsgebietLoading.value ||
    deletesiedlungsgebietLoading.value
);
</script>
