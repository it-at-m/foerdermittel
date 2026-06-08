<template>
  <v-form
    v-model="isValid"
    :disabled="disabled"
    @update:model-value="onValidityChanged"
  >
    <slot
      :model="model"
      :valid="isValid"
    />
  </v-form>
</template>

<script setup lang="ts" generic="T">
import { ref } from "vue";

const { disabled = false } = defineProps<{
  disabled?: boolean;
}>();

const model = defineModel<T>({ required: true });

const emit = defineEmits<{
  isValid: [boolean | null];
}>();

const isValid = ref<boolean | null>(false);

const onValidityChanged = (newIsValid: boolean | null) => {
  isValid.value = newIsValid;
  emit("isValid", newIsValid);
};
</script>
