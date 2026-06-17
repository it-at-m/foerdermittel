<template>
  <v-form
    :readonly="displayMode === InputDisplayMode.READ"
    @update:model-value="onValidityChanged"
  >
    <fm-number-input
      v-model="modelValue.bauprogramm"
      :display-mode="displayMode"
      :rules="[rules.required()]"
      disable-edit
      label="Bauprogramm"
      class="mb-3"
    />
    <fm-text-field
      v-model="modelValue.bezeichnung"
      :display-mode="displayMode"
      :rules="[rules.required()]"
      label="Bezeichnung*"
      class="mb-3"
    />
  </v-form>
</template>

<script setup lang="ts">
import type { BauprogrammResponseDTO } from "@/api/generated/foerdermittel-backend";

import { useRules } from "vuetify/labs/rules";

import FmNumberInput from "@/components/common/FmNumberInput.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const modelValue = defineModel<Partial<BauprogrammResponseDTO>>({
  required: true,
});

const { displayMode = InputDisplayMode.CREATE } = defineProps<{
  displayMode?: InputDisplayMode;
}>();

const emit = defineEmits<{
  isValid: [boolean | null];
}>();

const onValidityChanged = (newIsValid: boolean | null) => {
  emit("isValid", newIsValid);
};

const rules = useRules();
</script>
