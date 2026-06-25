<template>
  <v-number-input
    :readonly="displayMode === InputDisplayMode.READ || canNotEdit"
    :hide-details="displayMode === InputDisplayMode.READ"
    :variant="displayMode === InputDisplayMode.READ ? 'plain' : undefined"
    :control-variant="
      displayMode === InputDisplayMode.READ ? 'hidden' : undefined
    "
    :class="{
      'pointer-events-none':
        displayMode === InputDisplayMode.READ || canNotEdit,
    }"
    v-bind="$attrs"
  >
    <template #label>
      {{ label }}
      <span
        v-if="required && !canNotEdit && displayMode !== InputDisplayMode.READ"
        class="text-red"
        >{{ t("common.word.required") }}</span
      >
      <span v-if="canNotEdit">{{ t("common.word.readOnly") }}</span>
    </template>
  </v-number-input>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useI18n } from "vue-i18n";

import { InputDisplayMode } from "@/types/InputDisplayMode";

const {
  displayMode = InputDisplayMode.CREATE,
  disableEdit = false,
  required = false,
} = defineProps<{
  label: string;
  displayMode?: InputDisplayMode;
  disableEdit?: boolean;
  required?: boolean;
}>();

const canNotEdit = computed(
  () => displayMode === InputDisplayMode.EDIT && disableEdit
);

const { t } = useI18n();
</script>
