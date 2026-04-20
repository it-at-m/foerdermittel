<script setup lang="ts">
import type {
  CrudFormData,
  CrudResourceDefinition,
} from "@/types/json-schema-crud/CrudResourceDefinition";

import { computed, onMounted, ref } from "vue";
import { useI18n } from "vue-i18n";
import { useRouter } from "vue-router";

import { ApiError } from "@/api/ApiError";
import {
  getIndex,
  remove,
} from "@/api/json-schema-crud/json-schema-crud-client";
import YesNoDialog from "@/components/common/YesNoDialog.vue";
import { STATUS_INDICATORS } from "@/constants";
import { useSnackbarStore } from "@/stores/snackbar";

defineOptions({
  name: "JsonSchemaCrudIndexPage",
});

const props = defineProps<{
  resource: CrudResourceDefinition;
}>();

const router = useRouter();
const { t } = useI18n();
const snackbarStore = useSnackbarStore();

const isLoading = ref(true);
const items = ref<CrudFormData[]>([]);
const deleteDialog = ref(false);
const selectedItem = ref<CrudFormData>();
const isDeleting = ref(false);

const deleteDialogText = computed(() => {
  if (!selectedItem.value) {
    return "";
  }

  return props.resource.texts.getDeleteText(
    props.resource.getEntityLabel(selectedItem.value)
  );
});

onMounted(async () => {
  await loadItems();
});

function handleError(error: unknown): void {
  const apiError =
    error instanceof ApiError
      ? error
      : new ApiError({
          message: "Es ist ein unbekannter Fehler aufgetreten.",
        });

  snackbarStore.push({
    text: apiError.message,
    color: apiError.level as STATUS_INDICATORS,
  });
}

async function loadItems(): Promise<void> {
  isLoading.value = true;

  try {
    items.value = await getIndex(props.resource.apiBase);
  } catch (error) {
    handleError(error);
  } finally {
    isLoading.value = false;
  }
}

function toCreateRoute(): string {
  return `${props.resource.routeBase}/create`;
}

function toEditRoute(item: CrudFormData): string {
  return `${props.resource.routeBase}/${encodeURIComponent(
    props.resource.getEntityId(item)
  )}/edit`;
}

function openDeleteDialog(item: CrudFormData): void {
  selectedItem.value = item;
  deleteDialog.value = true;
}

function closeDeleteDialog(): void {
  deleteDialog.value = false;
  selectedItem.value = undefined;
}

async function confirmDelete(): Promise<void> {
  if (!selectedItem.value) {
    return;
  }

  isDeleting.value = true;

  try {
    await remove(
      props.resource.apiBase,
      props.resource.getEntityId(selectedItem.value)
    );
    snackbarStore.push({
      text: props.resource.texts.deleteSuccess,
      color: STATUS_INDICATORS.SUCCESS,
    });
    closeDeleteDialog();
    await loadItems();
  } catch (error) {
    handleError(error);
  } finally {
    isDeleting.value = false;
  }
}
</script>

<template>
  <v-card>
    <v-card-title class="d-flex align-center justify-space-between">
      <span class="text-h4">{{ resource.texts.indexTitle }}</span>
      <v-btn
        color="primary"
        @click="router.push(toCreateRoute())"
      >
        {{ resource.texts.createAction }}
      </v-btn>
    </v-card-title>
    <v-card-text>
      <v-progress-linear
        v-if="isLoading"
        indeterminate
        color="primary"
      />
      <v-table v-else>
        <thead>
          <tr>
            <th
              v-for="column in resource.columns"
              :key="column.key"
            >
              {{ column.title }}
            </th>
            <th class="text-right">
              {{ t("common.actions.actions") }}
            </th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="item in items"
            :key="resource.getEntityId(item)"
          >
            <td
              v-for="column in resource.columns"
              :key="column.key"
            >
              {{ item[column.key] }}
            </td>
            <td class="text-right">
              <v-btn
                variant="text"
                color="primary"
                @click="router.push(toEditRoute(item))"
              >
                {{ t("common.actions.edit") }}
              </v-btn>
              <v-btn
                variant="text"
                color="error"
                @click="openDeleteDialog(item)"
              >
                {{ t("common.actions.delete") }}
              </v-btn>
            </td>
          </tr>
        </tbody>
      </v-table>
    </v-card-text>
  </v-card>

  <yes-no-dialog
    v-model="deleteDialog"
    :dialogtitle="resource.texts.deleteTitle"
    :dialogtext="deleteDialogText"
    @no="closeDeleteDialog"
    @yes="confirmDelete"
  />
</template>
