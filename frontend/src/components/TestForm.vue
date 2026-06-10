<template>
  <v-form
    :readonly="displayMode === InputDisplayMode.READ"
    @update:model-value="onValidityChanged"
  >
    <fm-text-field
      v-model="modelValue.stringField"
      label="String-Feld*"
      :rules="[rules.required()]"
      class="mb-3"
      :display-mode="displayMode"
      disable-edit
    />
    <!--    <v-number-input-->
    <!--      v-model="modelValue.numberField"-->
    <!--      :label="-->
    <!--        isEdit-->
    <!--          ? t('common.generics.readOnly', ['Nummern-Feld'])-->
    <!--          : 'Nummern-Feld'-->
    <!--      "-->
    <!--      :readonly="readonly || isEdit"-->
    <!--      :hide-details="readonly"-->
    <!--      :variant="readonly ? 'plain' : undefined"-->
    <!--      :control-variant="readonly ? 'hidden' : undefined"-->
    <!--      :class="{ 'pointer-events-none': readonly || isEdit }"-->
    <!--      class="mb-3"-->
    <!--    />-->
    <!--    <v-checkbox-->
    <!--      v-model="modelValue.booleanField"-->
    <!--      label="Boolsches Feld"-->
    <!--      :readonly="readonly"-->
    <!--      :hide-details="readonly"-->
    <!--      :class="{ 'pointer-events-none': readonly }"-->
    <!--      class="ml-n3 mt-n3"-->
    <!--    />-->
  </v-form>
</template>

<script setup lang="ts">
import type { Test } from "@/types/Test";

import { useRules } from "vuetify/labs/rules";

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
