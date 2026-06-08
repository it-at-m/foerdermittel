<template>
  <v-form
    v-model="isValid"
    :disabled="disabled"
    class="w-100"
    @update:model-value="onValidityChanged"
  >
    <slot :model="modelValue" />
  </v-form>
</template>

<script setup lang="ts" generic="T">
import { ref } from "vue";

const { disabled = false } = defineProps<{
  disabled?: boolean;
}>();

const modelValue = defineModel<T>({ required: true });

const emit = defineEmits<{
  isValid: [boolean | null];
}>();

const isValid = ref<boolean | null>(false);

const onValidityChanged = (newIsValid: boolean | null) => {
  isValid.value = newIsValid;
  emit("isValid", newIsValid);
};
</script>
