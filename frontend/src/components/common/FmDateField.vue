<template>
  <v-date-input
    v-bind="$attrs"
    v-model="value"
    prepend-icon=""
    locale="de-DE"
  />
</template>

<script setup lang="ts">
import { computed } from "vue";

import { normalizeDate } from "@/util/date";

defineOptions({
  inheritAttrs: false,
});

interface Props {
  modelValue?: string | Date | null;
}

const props = defineProps<Props>();

const emit = defineEmits<{
  (e: "update:modelValue", value?: Date): void;
}>();

const value = computed({
  get: () => normalizeDate(props.modelValue),

  set: (date) => {
    emit(
      "update:modelValue",
      normalizeDate(date)
    );
  },
});
</script>