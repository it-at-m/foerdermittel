<script setup lang="ts">
import type { JsonSchema, UISchemaElement } from "@jsonforms/core";
import type { JsonFormsChangeEvent } from "@jsonforms/vue";
import type { ErrorObject } from "ajv";

import { JsonForms } from "@jsonforms/vue";
import { vuetifyRenderers } from "@jsonforms/vue-vuetify";
import Ajv2020 from "ajv/dist/2020";

import "@jsonforms/vue-vuetify/lib/jsonforms-vue-vuetify.css";

defineOptions({
  name: "JsonSchemaForm",
});

const props = defineProps<{
  modelValue: Record<string, unknown>;
  schema?: JsonSchema;
  uiSchema?: UISchemaElement;
  readonly?: boolean;
  loading?: boolean;
}>();

const emit = defineEmits<{
  "update:modelValue": [value: Record<string, unknown>];
  "validity-change": [errors: ErrorObject[]];
}>();

function onChange({ data, errors }: JsonFormsChangeEvent): void {
  emit("update:modelValue", (data ?? {}) as Record<string, unknown>);
  emit("validity-change", errors ?? []);
}

const ajv = new Ajv2020({
  allErrors: true,
  verbose: true,
  strict: false,
});
</script>

<template>
  <v-skeleton-loader
    v-if="loading"
    type="article"
  />
  <json-forms
    v-else-if="schema"
    :data="props.modelValue"
    :schema="schema"
    :uischema="uiSchema"
    :renderers="vuetifyRenderers"
    :readonly="readonly"
    validation-mode="ValidateAndShow"
    @change="onChange"
    :ajv="ajv"
  />
</template>
