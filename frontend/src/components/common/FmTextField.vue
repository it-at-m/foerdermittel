<template>
  <v-text-field
    v-if="displayMode !== InputDisplayMode.READ"
    ref="textField"
    :model-value="model"
    :readonly="canNotEdit"
    :class="{
      'pointer-events-none': canNotEdit,
    }"
    v-bind="$attrs"
    @update:model-value="updateModel"
  >
    <template #label>
      {{ label }}
      <span
        v-if="required && !canNotEdit"
        class="text-red"
        >{{ t("common.word.required") }}</span
      >
      <span v-if="canNotEdit">{{ t("common.word.readOnly") }}</span>
    </template>
  </v-text-field>
  <v-textarea
    v-else
    :label="label"
    auto-grow
    readonly
    hide-details
    variant="plain"
    class="pointer-events-none"
    v-bind="$attrs"
  />
</template>

<script setup lang="ts">
import type { VTextField } from "vuetify/components";

import { computed, nextTick, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import { InputDisplayMode } from "@/types/InputDisplayMode";

const {
  displayMode = InputDisplayMode.CREATE,
  disableEdit = false,
  required = false,
  uppercase = false,
} = defineProps<{
  label: string;
  displayMode?: InputDisplayMode;
  disableEdit?: boolean;
  required?: boolean;
  uppercase?: boolean;
}>();

const canNotEdit = computed(
  () =>
    displayMode === InputDisplayMode.READ ||
    (displayMode === InputDisplayMode.EDIT && disableEdit)
);

const model = defineModel<string>();
const textFieldRef = useTemplateRef<VTextField>("textField");

async function updateModel(newModelValue: string) {
  const input = textFieldRef.value?.$el.querySelector(
    "input"
  ) as HTMLInputElement;

  const start = input?.selectionStart;
  const end = input?.selectionEnd;

  model.value = uppercase ? newModelValue.toUpperCase() : newModelValue;

  await nextTick();

  input?.setSelectionRange(start, end);
}

const { t } = useI18n();
</script>
