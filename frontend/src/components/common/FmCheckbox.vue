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
import { computed } from "vue";
import { useI18n } from "vue-i18n";

import { InputDisplayMode } from "@/types/InputDisplayMode";

const { displayMode = InputDisplayMode.CREATE, disableEdit = false } =
  defineProps<{
    label: string;
    displayMode?: InputDisplayMode;
    disableEdit?: boolean;
  }>();

const canNotEdit = computed(
  () =>
    displayMode === InputDisplayMode.READ ||
    (displayMode === InputDisplayMode.EDIT && disableEdit)
);

const { t } = useI18n();
</script>
