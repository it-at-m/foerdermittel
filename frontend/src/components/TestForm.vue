<template>
  <v-form
    :readonly="displayMode === InputDisplayMode.READ"
    @update:model-value="onValidityChanged"
  >
    <fm-text-field
      v-model="modelValue.stringField"
      :display-mode="displayMode"
      :rules="[rules.required()]"
      disable-edit
      label="String-Feld*"
      class="mb-3"
    />
    <fm-number-input
      v-model="modelValue.numberField"
      :display-mode="displayMode"
      label="Nummern-Feld"
      class="mb-3"
    />
    <fm-checkbox
      v-model="modelValue.booleanField"
      :display-mode="displayMode"
      label="Boolsches Feld"
      class="ml-n3 mt-n3"
    />
  </v-form>
</template>

<script setup lang="ts">
import type { Test } from "@/types/Test";

import { useRules } from "vuetify/labs/rules";

import FmCheckbox from "@/components/common/FmCheckbox.vue";
import FmNumberInput from "@/components/common/FmNumberInput.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const modelValue = defineModel<Test>({ required: true });

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
