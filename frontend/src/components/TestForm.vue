<template>
  <v-form
    :readonly="readonly"
    @update:model-value="onValidityChanged"
  >
    <v-text-field
      v-model="modelValue.stringField"
      label="String-Feld*"
      :rules="[rules.required()]"
      :readonly="readonly"
      :hide-details="readonly"
      :variant="readonly ? 'plain' : undefined"
      :class="{ 'pointer-events-none': readonly }"
      class="mb-3"
    />
    <v-number-input
      v-model="modelValue.numberField"
      :label="
        isEdit
          ? t('common.generics.readOnly', ['Nummern-Feld'])
          : 'Nummern-Feld'
      "
      :readonly="readonly || isEdit"
      :hide-details="readonly"
      :variant="readonly ? 'plain' : undefined"
      :control-variant="readonly ? 'hidden' : undefined"
      :class="{ 'pointer-events-none': readonly || isEdit }"
      class="mb-3"
    />
    <v-checkbox
      v-model="modelValue.booleanField"
      label="Boolsches Feld"
      :readonly="readonly"
      :hide-details="readonly"
      :class="{ 'pointer-events-none': readonly }"
      class="ml-n3 mt-n3"
    />
  </v-form>
</template>

<script setup lang="ts">
import type { Test } from "@/types/Test";

import { useI18n } from "vue-i18n";
import { useRules } from "vuetify/labs/rules";

const modelValue = defineModel<Test>({ required: true });

const { t } = useI18n();

const { readonly = false, isEdit = false } = defineProps<{
  readonly?: boolean;
  isEdit?: boolean;
}>();

const emit = defineEmits<{
  isValid: [boolean | null];
}>();

const onValidityChanged = (newIsValid: boolean | null) => {
  emit("isValid", newIsValid);
};

const rules = useRules();
</script>
