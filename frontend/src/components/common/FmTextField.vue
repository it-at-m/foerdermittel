<template>
  <v-text-field
    v-if="displayMode !== InputDisplayMode.READ"
    :label="canNotEdit ? t('common.generics.readOnly', [label]) : label"
    :readonly="canNotEdit"
    :class="{
      'pointer-events-none': canNotEdit,
    }"
    v-bind="$attrs"
  />
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
