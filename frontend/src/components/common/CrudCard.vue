<template>
  <v-dialog
    :model-value="showDialog"
    width="90%"
    max-width="800px"
    persistent
  >
    <confirm-card
      v-if="dialogMode === 'read' || dialogMode === 'edit'"
      :title="
        dialogMode === 'read'
          ? domain
          : t(
              activeItem.id
                ? 'common.generics.update'
                : 'common.generics.create',
              {
                domain: domain,
              }
            )
      "
      :loading="loading"
      :show-confirm="dialogMode === 'edit'"
      :disable-confirm="!isFormSlotValid"
      @cancel="closeDialog"
      @confirm="saveItem"
    >
      <template #content>
        <slot
          name="form"
          :item="activeItem"
          :update-item="updateActiveItem"
          :update-validity="updateFormValidity"
        />
      </template>
    </confirm-card>

    <confirm-card
      v-else-if="dialogMode === 'delete'"
      :title="t('common.generics.delete', { domain })"
      :text="t('common.message.confirmDelete', { domain })"
      :loading="loading"
      :confirm-icon="mdiTrashCan"
      :confirm-text="t('common.action.delete')"
      show-confirm
      @cancel="closeDialog"
      @confirm="deleteItem"
    />
  </v-dialog>

  <v-card
    :loading="loading"
    class="elevation-0"
  >
    <v-card-title>
      <v-row align-content="center">
        <v-col class="d-flex align-center justify-end">
          <v-btn
            v-if="enableActions"
            :append-icon="mdiPlus"
            :text="t('common.action.create')"
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
    <template #text>
      <v-data-table-server
        :headers="tableHeadersWithActions"
        :items="items"
        :items-length="totalItems"
        hide-default-footer
        :items-per-page="itemsPerPage"
        :loading="loading"
        show-expand
        expand-on-click
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
                :icon="mdiDelete"
                @click="openDelete(item)"
              />
            </v-col>
          </v-row>
        </template>
        <!-- Slot for rendering the expansion panel -->
        <template #expanded="{ item }">
          <slot
            name="form"
            :item="item"
            :read-only="true"
          />
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

const { t } = useI18n();

type DialogMode = "edit" | "delete" | "read" | null;
const dialogMode = ref<DialogMode>(null);
const showDialog = computed(() => dialogMode.value !== null);

const {
  tableHeaders,
  emptyItemTemplate,
  loading = false,
  itemsPerPage = 20,
  items = [],
  enableActions = true,
} = defineProps<{
  primaryKeyName: keyof T;
  emptyItemTemplate: T;
  domain: string;
  loading?: boolean;
  tableHeaders: TableColumnHeader<T>[];
  items?: T[];
  itemsPerPage?: number;
  totalItems: number;
  enableActions?: boolean;
}>();

const tableHeadersWithActions = computed(() => [
  ...tableHeaders,
  {
    title: t("common.action", { count: 2 }),
    value: "actions",
  },
]);

const activeItem = ref<T>({ ...emptyItemTemplate } as T);

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
  dialogMode.value = "edit";
};

const openEdit = (item: T) => {
  activeItem.value = structuredClone(toRaw(item)) as T;
  dialogMode.value = "edit";
};

const openDelete = (item: T) => {
  activeItem.value = item;
  dialogMode.value = "delete";
};

const saveItem = () => {
  if (activeItem.value[primary]) {
    emit("update", activeItem.value);
  } else {
    emit("create", activeItem.value);
  }
};

const deleteItem = () => {
  if (activeItem.value.id) {
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
