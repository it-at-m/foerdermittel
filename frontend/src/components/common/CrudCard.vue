<template>
  <v-dialog :model-value="showDialog" :max-width="dialogWidth">
    <confirm-card
      v-if="dialogMode === 'read' || dialogMode === 'write'"
      :title="dialogTitle"
      :loading="loading"
      :show-confirm="dialogMode === 'write'"
      :disable-confirm="!isFormSlotValid"
      @cancel="closeDialog"
      @confirm="saveItem"
    >
      <slot
        name="form"
        :item="activeItem"
        :read-only="dialogMode === 'read'"
        :update-item="updateActiveItem"
        :update-validity="updateFormValidity"
        :is-editing="isEditing"
      />
    </confirm-card>

    <confirm-card
      v-else-if="dialogMode === 'delete'"
      :title="t('common.generics.delete', [domainSingular])"
      :text="t('common.message.confirmDelete', [domainSingular])"
      :loading="loading"
      :confirm-icon="mdiTrashCan"
      :confirm-text="t('common.action.delete')"
      show-confirm
      @cancel="closeDialog"
      @confirm="deleteItem"
    />
  </v-dialog>

  <v-card class="w-100">
    <template #title>
      <v-row align-content="center">
        <v-col class="d-flex align-center justify-end">
          <v-btn
            v-if="enableActions"
            variant="flat"
            color="primary"
            :append-icon="mdiPlus"
            :text="t('common.action.create')"
            :disabled="loading"
            class="mb-4"
            @click="openCreate"
          />
        </v-col>
      </v-row>
      <v-row class="mt-2">
        <v-col>
          <v-divider />
        </v-col>
      </v-row>
    </template>
    <template #text>
      <v-data-table-server
        :headers="tableHeadersWithActions"
        :items="items"
        :items-length="totalItems"
        :items-per-page="itemsPerPage"
        :loading="loading"
        :loading-text="t('common.message.loading', [domainPlural])"
        :no-data-text="t('common.message.noData', [domainPlural])"
        :show-expand="expandable"
        expand-strategy="single"
        @update:options="(data) => emit('updatedOptions', data)"
      >
        <!-- Static actions for edit and delete -->
        <template #[`item.actions`]="{ item }">
          <v-row align-content="center">
            <v-col
              class="pa-0"
              cols="12"
              sm="6"
            >
              <v-btn
                v-if="enableActions"
                size="small"
                :icon="mdiPencil"
                class="mr-1"
                @click="openEdit(item)"
              />
            </v-col>
            <v-col
              class="pa-0"
              cols="12"
              sm="6"
            >
              <v-btn
                v-if="enableActions"
                size="small"
                :icon="mdiDelete"
                @click="openDelete(item)"
              />
            </v-col>
          </v-row>
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
              :read-only="true"
              :update-item="undefined"
              :update-validity="undefined"
              :is-editing="false"
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
            v-if="slotName !== 'form' && slotName !== 'item.actions'"
            :name="slotName"
            v-bind="slotProps || {}"
          />
        </template>
      </v-data-table-server>
    </template>
  </v-card>
</template>

<script setup lang="ts" generic="T extends { id?: string }">
import type { TableColumnHeader } from "@/types/TableColumnHeader";

import { mdiDelete, mdiPencil, mdiPlus, mdiTrashCan } from "@mdi/js";
import { computed, ref, toRaw } from "vue";
import { useI18n } from "vue-i18n";

import ConfirmCard from "@/components/common/ConfirmCard.vue";
import { DialogWidth } from "@/types/DialogWidth";

const { t } = useI18n();

type DialogMode = "write" | "delete" | "read" | null;
const dialogMode = ref<DialogMode>(null);
const showDialog = computed(() => dialogMode.value !== null);

const {
  domainKey,
  tableHeaders,
  emptyItemTemplate,
  loading = false,
  itemsPerPage = 20,
  items = [],
  enableActions = true,
  expandable = false,
  dialogWidth = DialogWidth.MEDIUM
} = defineProps<{
  emptyItemTemplate: T;
  domainKey: string;
  loading?: boolean;
  tableHeaders: TableColumnHeader<T>[];
  items?: T[];
  itemsPerPage?: number;
  totalItems: number;
  enableActions?: boolean;
  expandable?: boolean;
  dialogWidth?: DialogWidth;
}>();

const domainSingular = computed(() => t(domainKey));
const domainPlural = computed(() => t(domainKey, 2));

const dialogTitle = computed(() => {
  if (dialogMode.value === "read") return domainSingular.value;
  return isEditing.value
    ? t("common.generics.update", [domainPlural])
    : t("common.generics.create", [domainPlural]);
});

const tableHeadersWithActions = computed(() => [
  ...tableHeaders,
  {
    title: t("common.word.action", { count: 2 }),
    value: "actions",
    width: 150,
  },
]);

const activeItem = ref<T>({ ...emptyItemTemplate } as T);
const isEditing = computed<boolean>(() => !!activeItem.value.id);

const isFormSlotValid = ref(false);

const emit = defineEmits<{
  create: [item: T];
  update: [item: T];
  delete: [id: string];
  // Sadly there is no type for the emit of updatedOptions ...
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  updatedOptions: [options: any];
}>();

// --- Functions ---
const updateFormValidity = (valid: boolean | null) => {
  isFormSlotValid.value = !!valid;
};

const updateActiveItem = (newValue: T) => {
  activeItem.value = newValue;
};

const openCreate = () => {
  activeItem.value = { ...emptyItemTemplate } as T;
  dialogMode.value = "write";
};

const openEdit = (item: T) => {
  activeItem.value = structuredClone(toRaw(item)) as T;
  dialogMode.value = "write";
};

const openDelete = (item: T) => {
  activeItem.value = item;
  dialogMode.value = "delete";
};

const saveItem = () => {
  if (isEditing.value) {
    emit("update", activeItem.value);
  } else {
    emit("create", activeItem.value);
  }
};

const deleteItem = () => {
  if (isEditing.value) {
    emit("delete", activeItem.value.id);
  }
};

const closeDialog = () => {
  dialogMode.value = null;
  activeItem.value = emptyItemTemplate;
};

defineExpose({
  closeDialog,
});
</script>
