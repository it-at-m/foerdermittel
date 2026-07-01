<template>
  <v-text-field
    v-if="displayMode !== InputDisplayMode.READ"
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
import { computed } from "vue";
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

function updateModel(newModelValue: string) {
  model.value = uppercase ? newModelValue.toUpperCase() : newModelValue;
}

const { t } = useI18n();
</script>
