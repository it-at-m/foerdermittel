<template>
  <v-textarea
    v-model="model"
    :readonly="canNotEdit"
    :class="{
      'pointer-events-none': canNotEdit,
    }"
    v-bind="$attrs"
  >
    <template #label>
      {{ label }}
      <span
        v-if="required && !canNotEdit"
        class="text-red"
        >{{ t("common.word.required") }}</span
      >
      <span v-if="displayMode === InputDisplayMode.EDIT && canNotEdit">{{
        t("common.word.readOnly")
      }}</span>
    </template>
  </v-textarea>
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
  () =>
    displayMode === InputDisplayMode.READ ||
    (displayMode === InputDisplayMode.EDIT && disableEdit)
);

const model = defineModel<string>();

const { t } = useI18n();
</script>
