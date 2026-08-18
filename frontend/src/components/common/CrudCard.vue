<template>
  <v-dialog
    :model-value="showDialog"
    :max-width="dialogWidth"
  >
    <confirm-card
      v-if="dialogMode === 'write'"
      :title="dialogTitle"
      :loading="loading"
      :disable-confirm="!isFormSlotValid || !isDirty"
      @cancel="requestCloseDialog"
      @confirm="saveItem"
    >
      <slot
        name="form"
        :item="activeItem"
        :update-validity="updateFormValidity"
        :input-display-mode="
          isEditing ? InputDisplayMode.EDIT : InputDisplayMode.CREATE
        "
      />
    </confirm-card>

    <confirm-card
      v-else-if="dialogMode === 'delete'"
      :title="t('common.generics.delete', [domainSingular])"
      :text="t('common.message.confirmDelete', [domainSingular])"
      :loading="loading"
      :confirm-icon="mdiTrashCan"
      :confirm-text="t('common.action.delete')"
      @cancel="closeDialog"
      @confirm="deleteItem"
    />
  </v-dialog>

  <unsaved-changes-dialog
    :model-value="showUnsavedChangesDialog"
    :loading="loading"
    @cancel="discardDialogChanges"
    @confirm="continueEditing"
  />

  <v-card class="d-flex flex-column fill-height w-100">
    <v-card-title class="pa-0">
      <v-row
        align-content="center"
        justify="center"
        class="mb-4"
      >
        <v-col class="d-flex align-center justify-end">
          <v-btn
            v-if="enableActions"
            variant="flat"
            color="accent"
            class="text-body-large"
            :append-icon="mdiPlus"
            :text="t('common.action.create')"
            :disabled="loading"
            @click="openCreate"
          />
        </v-col>
      </v-row>
      <v-row class="mt-2">
        <v-col>
          <v-divider />
        </v-col>
      </v-row>
    </v-card-title>
    <v-card-text class="pa-0">
      <div class="d-flex flex-column h-100 overflow-x-hidden">
        <v-data-table-server
          v-model:items-per-page="itemsPerPage"
          v-model:page="page"
          v-model:sort-by="sortBy"
          v-model:search="search"
          fixed-header
          :headers="tableHeadersWithActions"
          :items="api.getAll.data.value?.content ?? []"
          :items-length="api.getAll.data.value?.page?.totalElements ?? 0"
          :loading="loading"
          :show-expand="expandable"
          expand-strategy="single"
          height="10"
          class="flex-grow-1 w-100"
        >
          <template #loading>
            <p>{{ t("common.message.loading", [domainPlural]) }}</p>
          </template>
          <template #no-data>
            <p>{{ t("common.message.noData", [domainPlural]) }}</p>
          </template>
          <!-- Static actions for edit and delete -->
          <template #[`item.actions`]="{ item }">
            <v-icon-btn
              :icon="mdiPencil"
              class="mr-1"
              @click="openEdit(item)"
            />
            <v-icon-btn
              :icon="mdiDelete"
              @click="openDelete(item)"
            />
          </template>
          <!-- Slot for rendering the expansion panel -->
          <template
            v-if="expandable"
            #expanded="{ item }"
          >
            <div class="pa-10 bg-grey-lighten-5">
              <slot
                name="form"
                :item="item"
                :input-display-mode="InputDisplayMode.READ"
                :update-validity="undefined"
              />
            </div>
          </template>
          <!-- Allow custom slots for other table columns -->
          <template
            v-for="(_, slotName) in $slots"
            :key="slotName"
            #[slotName]="slotProps"
          >
            <slot
              :name="slotName"
              v-bind="slotProps || {}"
            />
          </template>
        </v-data-table-server>
      </div>
    </v-card-text>
  </v-card>
</template>

<script
  setup
  lang="ts"
  generic="
    TGetRequest extends {},
    TGetResponse extends { id?: string },
    TContextRequest extends {},
    TContextResponse extends {},
    TCreateRequest extends {},
    TCreateResponse extends {},
    TUpdateRequest extends {},
    TUpdateResponse extends {},
    TDeleteRequest extends {},
    TDeleteResponse extends {}
  "
>
import type { ApiComposables } from "@/util/composable-helper";
import type { Awaitable } from "@vueuse/core";
import type { MaybeRefOrGetter } from "vue";
import type { DataTableHeader } from "vuetify/framework";

import { mdiDelete, mdiPencil, mdiPlus, mdiTrashCan } from "@mdi/js";
import { computed, onMounted, ref, toValue } from "vue";
import { useI18n } from "vue-i18n";

import ConfirmCard from "@/components/common/ConfirmCard.vue";
import UnsavedChangesDialog from "@/components/common/UnsavedChangesDialog.vue";
import { useDirtyFlag } from "@/composables/useDirtyFlag";
import usePagination from "@/composables/usePagination";
import { STATUS_INDICATORS } from "@/constants";
import { useSnackbarStore } from "@/stores/snackbar";
import { DialogWidth } from "@/types/DialogWidth";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();

type DialogMode = "write" | "delete" | null;
const dialogMode = ref<DialogMode>(null);
const showDialog = computed(() => dialogMode.value !== null);

const {
  emptyItemTemplate,
  domainKey,
  loading: loadingProp = false,
  tableHeaders,
  api,
  enableActions = true,
  expandable = false,
  dialogWidth = DialogWidth.MEDIUM,
  shouldLoadFormContext,
  handleCreate,
  handleUpdate,
  handleDelete,
  formRef,
} = defineProps<{
  emptyItemTemplate: TGetResponse;
  domainKey: string;
  loading?: boolean;
  tableHeaders: Readonly<DataTableHeader<TGetResponse>>[];
  api: ApiComposables<
    TGetResponse,
    TContextResponse,
    TCreateRequest,
    TCreateResponse,
    TUpdateRequest,
    TUpdateResponse,
    TDeleteRequest
  >;
  enableActions?: boolean;
  expandable?: boolean;
  dialogWidth?: DialogWidth;
  shouldLoadFormContext: MaybeRefOrGetter<boolean>;
  handleCreate: (item: TGetResponse) => Awaitable<boolean>;
  handleUpdate: (item: TGetResponse) => Awaitable<boolean>;
  handleDelete: (id: string) => Awaitable<boolean>;
  formRef?: { validate: () => unknown } | null | undefined;
}>();

const loading = computed(
  () =>
    loadingProp ||
    api.getAll.loading.value ||
    api.context.loading.value ||
    api.create.loading.value ||
    api.update.loading.value ||
    api.delete.loading.value
);

const { dataTableOptions, refetchEntities } = usePagination(
  computed(() => api.getAll.data.value?.page?.totalPages),
  api.getAll.call
);

const snackbarStore = useSnackbarStore();

const page = computed({
  get: () => dataTableOptions.value.page,
  set: (value) => {
    dataTableOptions.value = {
      ...dataTableOptions.value,
      page: value,
    };
  },
});

const itemsPerPage = computed({
  get: () => dataTableOptions.value.itemsPerPage,
  set: (value) => {
    dataTableOptions.value = {
      ...dataTableOptions.value,
      itemsPerPage: value,
    };
  },
});

const sortBy = computed({
  get: () => dataTableOptions.value.sortBy,
  set: (value) => {
    dataTableOptions.value = {
      ...dataTableOptions.value,
      sortBy: value,
    };
  },
});

const search = computed({
  get: () => dataTableOptions.value.search,
  set: (value) => {
    dataTableOptions.value = {
      ...dataTableOptions.value,
      search: value,
    };
  },
});

const domainSingular = computed(() => t(domainKey));
const domainPlural = computed(() => t(domainKey, 2));

const dialogTitle = computed(() => {
  return isEditing.value
    ? t("common.generics.update", [domainSingular.value])
    : t("common.generics.create", [domainSingular.value]);
});

const tableHeadersWithActions = computed(() => [
  ...tableHeaders,
  enableActions
    ? ({
        title: t("common.word.action", { count: 2 }),
        value: "actions",
        width: "100",
        align: "center",
        cellProps: { class: "text-no-wrap" },
      } satisfies DataTableHeader<TGetResponse>)
    : {},
]);

const {
  currentValue: activeItem,
  isDirty,
  showUnsavedChangesDialog,
  reset,
  track,
  requestClose,
  continueEditing,
  continuePendingNavigation,
  discardChanges,
} = useDirtyFlag<TGetResponse>(
  emptyItemTemplate,
  computed(() => dialogMode.value === "write")
);
const isEditing = computed<boolean>(() => !!activeItem.value.id);

const isFormSlotValid = ref(false);

// --- Lifecycle Handlers ---

onMounted(async () => {
  await loadFormContext();
});

// --- Functions ---

const loadFormContext = async () => {
  if (toValue(shouldLoadFormContext)) {
    await api.context.call();
  }
};

const updateFormValidity = (valid: boolean | null) => {
  isFormSlotValid.value = !!valid;
};

const openCreate = () => {
  track(emptyItemTemplate);
  isFormSlotValid.value = false;
  dialogMode.value = "write";
};

const openEdit = (item: TGetResponse) => {
  track(item);
  isFormSlotValid.value = false;
  dialogMode.value = "write";
};

const openDelete = (item: TGetResponse) => {
  reset(item);
  isFormSlotValid.value = false;
  dialogMode.value = "delete";
};

const onSuccess = async (msg: string) => {
  snackbarStore.push({
    text: msg,
    color: STATUS_INDICATORS.SUCCESS,
  });
  await refetchEntities();
  await loadFormContext();
  closeDialog();
};

const onFailure = async (msg: string) => {
  await loadFormContext();
  await formRef?.validate();
  snackbarStore.push({
    text: msg,
    color: STATUS_INDICATORS.ERROR,
  });
};

const saveItem = async () => {
  if (isEditing.value && activeItem.value.id) {
    if (await handleUpdate(activeItem.value)) {
      await onSuccess(t("common.message.updated", [t(domainKey)]));
    } else {
      await onFailure(t("common.message.updatedError", [t(domainKey)]));
    }
  } else {
    if (await handleCreate(activeItem.value)) {
      await onSuccess(t("common.message.created", [t(domainKey)]));
    } else {
      await onFailure(t("common.message.createdError", [t(domainKey)]));
    }
  }
};

const deleteItem = async () => {
  if (!activeItem.value.id) return;
  if (await handleDelete(activeItem.value.id)) {
    await onSuccess(t("common.message.deleted", [t(domainKey)]));
  } else {
    await onFailure(t("common.message.deletedError", [t(domainKey)]));
  }
};

const closeDialog = () => {
  dialogMode.value = null;
  reset();
  continuePendingNavigation();
};

const requestCloseDialog = () => {
  requestClose(closeDialog);
};

const discardDialogChanges = () => {
  dialogMode.value = null;
  discardChanges();
};
</script>

<style scoped>
:deep(table) {
  table-layout: fixed;
}
</style>
