<template>
  <v-checkbox
    :readonly="displayMode === InputDisplayMode.READ || canNotEdit"
    :hide-details="displayMode === InputDisplayMode.READ"
    :class="{
      'pointer-events-none':
        displayMode === InputDisplayMode.READ || canNotEdit,
    }"
    v-bind="$attrs"
  >
    <template #label>
      {{ label }}
      <span v-if="canNotEdit">{{ t("common.word.readOnly") }}</span>
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
  () => displayMode === InputDisplayMode.EDIT && disableEdit
);

const { t } = useI18n();
</script>
