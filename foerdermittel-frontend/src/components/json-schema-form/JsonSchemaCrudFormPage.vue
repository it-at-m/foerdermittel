<script setup lang="ts">
import type { CrudResourceDefinition } from "@/types/json-schema-crud/CrudResourceDefinition";
import type { JsonSchema } from "@jsonforms/core";
import type { ErrorObject } from "ajv";

import { computed, onMounted, ref, watch } from "vue";
import { useI18n } from "vue-i18n";
import { useRoute, useRouter } from "vue-router";

import { ApiError } from "@/api/ApiError";
import {
  create,
  getById,
  getCreateSchema,
  getUpdateSchema,
  update,
} from "@/api/json-schema-crud/json-schema-crud-client";
import YesNoDialog from "@/components/common/YesNoDialog.vue";
import JsonSchemaForm from "@/components/json-schema-form/JsonSchemaForm.vue";
import { useSaveLeave } from "@/composables/saveLeave";
import { STATUS_INDICATORS } from "@/constants";
import { useSnackbarStore } from "@/stores/snackbar";

defineOptions({
  name: "JsonSchemaCrudFormPage",
});

const props = defineProps<{
  resource: CrudResourceDefinition;
  mode: "create" | "edit";
}>();

const route = useRoute();
const router = useRouter();
const { t } = useI18n();
const snackbarStore = useSnackbarStore();

const schema = ref<JsonSchema>();
const formData = ref<Record<string, unknown>>({});
const initialData = ref("{}");
const validationErrors = ref<ErrorObject[]>([]);
const isLoading = ref(true);
const isSubmitting = ref(false);

const {
  saveLeaveDialog,
  saveLeaveDialogText,
  saveLeaveDialogTitle,
  isSave,
  cancel,
  leave,
} = useSaveLeave(() => isDirty.value);

const isEditMode = computed(() => props.mode === "edit");
const entityId = computed(() => {
  const routeParams = route.params as Record<
    string,
    string | string[] | undefined
  >;
  const rawValue = routeParams[props.resource.routeParam];
  return typeof rawValue === "string" ? rawValue : "";
});
const pageTitle = computed(() =>
  isEditMode.value
    ? props.resource.texts.editTitle
    : props.resource.texts.createTitle
);
const isDirty = computed(
  () => JSON.stringify(formData.value ?? {}) !== initialData.value
);
const isSaveDisabled = computed(
  () =>
    isLoading.value ||
    isSubmitting.value ||
    !schema.value ||
    validationErrors.value.length > 0
);

watch(entityId, async () => {
  if (isEditMode.value) {
    await loadPage();
  }
});

onMounted(async () => {
  await loadPage();
});

function toIndexRoute(): string {
  return props.resource.routeBase;
}

function setInitialData(data: Record<string, unknown>): void {
  formData.value = data;
  initialData.value = JSON.stringify(data ?? {});
}

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

async function loadPage(): Promise<void> {
  isLoading.value = true;
  validationErrors.value = [];

  try {
    if (isEditMode.value) {
      if (!entityId.value) {
        throw new ApiError({
          message: "Die Datensatz-ID fehlt.",
        });
      }

      const [loadedSchema, loadedData] = await Promise.all([
        getUpdateSchema<JsonSchema>(props.resource.apiBase),
        getById<Record<string, unknown>>(
          props.resource.apiBase,
          entityId.value
        ),
      ]);

      schema.value = loadedSchema;
      setInitialData(loadedData);
      return;
    }

    schema.value = await getCreateSchema<JsonSchema>(props.resource.apiBase);
    setInitialData({});
  } catch (error) {
    handleError(error);
    await router.push(toIndexRoute());
  } finally {
    isLoading.value = false;
  }
}

function onValidityChange(errors: ErrorObject[]): void {
  validationErrors.value = errors;
}

async function submit(): Promise<void> {
  if (isSaveDisabled.value) {
    return;
  }

  isSubmitting.value = true;

  try {
    if (isEditMode.value) {
      await update(
        props.resource.apiBase,
        entityId.value,
        props.resource.mapUpdatePayload?.(formData.value) ?? formData.value
      );
    } else {
      await create(
        props.resource.apiBase,
        props.resource.mapCreatePayload?.(formData.value) ?? formData.value
      );
    }

    snackbarStore.push({
      text: props.resource.texts.saveSuccess,
      color: STATUS_INDICATORS.SUCCESS,
    });
    isSave.value = true;
    await router.push(toIndexRoute());
  } catch (error) {
    handleError(error);
  } finally {
    isSubmitting.value = false;
  }
}
</script>

<template>
  <v-card>
    <v-card-title class="text-h4">
      {{ pageTitle }}
    </v-card-title>
    <v-card-text>
      <json-schema-form
        v-model="formData"
        :schema="schema"
        :ui-schema="
          isEditMode ? resource.updateUiSchema : resource.createUiSchema
        "
        :loading="isLoading"
        :readonly="isSubmitting"
        @validity-change="onValidityChange"
      />
    </v-card-text>
    <v-card-actions class="px-4 pb-4">
      <v-btn
        data-testid="cancel-button"
        variant="text"
        :disabled="isSubmitting"
        @click="router.push(toIndexRoute())"
      >
        {{ t("common.actions.cancel") }}
      </v-btn>
      <v-spacer />
      <v-btn
        data-testid="save-button"
        color="primary"
        :loading="isSubmitting"
        :disabled="isSaveDisabled"
        @click="submit"
      >
        {{ t("common.actions.save") }}
      </v-btn>
    </v-card-actions>
  </v-card>

  <yes-no-dialog
    v-model="saveLeaveDialog"
    :dialogtitle="saveLeaveDialogTitle"
    :dialogtext="saveLeaveDialogText"
    @no="cancel"
    @yes="leave"
  />
</template>
