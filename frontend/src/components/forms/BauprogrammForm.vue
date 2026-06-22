<template>
  <v-form
    :readonly="displayMode === InputDisplayMode.READ"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col cols="3">
        <fm-number-input
          v-model="modelValue.bauprogramm"
          :display-mode="displayMode"
          :rules="[rules.required(), rules.number(), rules['min']!(1), rules['max']!(10)]"
          disable-edit
          label="Bauprogramm"
        />
      </v-col>
      <v-col cols="9">
        <fm-text-field
          v-model="modelValue.bezeichnung"
          :display-mode="displayMode"
          :rules="[rules.required(), rules.maxLength(200)]"
          :counter="200"
          label="Bezeichnung*"
        />
      </v-col>
    </v-row>
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
