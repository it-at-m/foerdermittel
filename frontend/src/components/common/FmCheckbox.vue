<template>
  <v-checkbox
    :readonly="canNotEdit"
    :hide-details="displayMode === InputDisplayMode.READ"
    :class="{
      'pointer-events-none': canNotEdit,
    }"
    v-bind="$attrs"
  >
    <template #label>
      {{ label }}
      <span v-if="displayMode == InputDisplayMode.EDIT && canNotEdit">{{
        t("common.word.readOnly")
      }}</span>
    </template>
  </v-checkbox>
</template>

<script setup lang="ts">
import { useI18n } from "vue-i18n";

import { useInputValidation } from "@/composables/useInputValidation";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { displayMode = InputDisplayMode.CREATE, disableEdit = false } =
  defineProps<{
    label: string;
    displayMode?: InputDisplayMode;
    disableEdit?: boolean;
  }>();

const { canNotEdit } = useInputValidation(
  displayMode,
  disableEdit,
  undefined,
  undefined,
  undefined
);

const { t } = useI18n();
</script>
