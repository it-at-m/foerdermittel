<template>
  <v-text-field
    v-if="displayMode !== InputDisplayMode.READ"
    ref="textField"
    :model-value="model"
    :readonly="canNotEdit"
    :counter="counter"
    :rules="allRules"
    v-bind="$attrs"
    @update:model-value="updateModel"
  >
    <template #label>
      {{ label }}
      <span
        v-if="required && !canNotEdit"
        class="text-red"
        >{{ t("common.word.required") }}</span
      >
      <span v-if="displayMode === InputDisplayMode.EDIT && canNotEdit">{{
        t("common.word.readOnly")
      }}</span>
    </template>
  </v-text-field>
  <v-textarea
    v-else
    :model-value="model"
    :label="label"
    auto-grow
    readonly
    hide-details
    variant="plain"
    v-bind="$attrs"
  />
</template>

<script
  setup
  lang="ts"
  generic="M extends Record<string, ValidationAttributes>, K extends keyof M"
>
import type { ValidationAttributes } from "@/types/OpenAPIValidationAttributes";
import type { VTextField } from "vuetify/components";
import type { ValidationRule } from "vuetify/framework";

import { nextTick, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import { useInputValidation } from "@/composables/useInputValidation";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const {
  displayMode = InputDisplayMode.CREATE,
  disableEdit = false,
  uppercase = false,
  validationAttributeMap,
  validationAttributeKey,
  additionalRules = [],
} = defineProps<{
  label: string;
  displayMode?: InputDisplayMode;
  disableEdit?: boolean;
  uppercase?: boolean;
  validationAttributeMap?: M;
  validationAttributeKey?: K;
  additionalRules?: ValidationRule[];
}>();

const { required, allRules, counter, canNotEdit } = useInputValidation(
  displayMode,
  disableEdit,
  additionalRules,
  validationAttributeMap,
  validationAttributeKey as string
);

const model = defineModel<string>();
const textFieldRef = useTemplateRef<VTextField>("textField");

async function updateModel(newModelValue: string) {
  const input = textFieldRef.value?.$el.querySelector(
    "input"
  ) as HTMLInputElement;

  const start = input?.selectionStart;
  const end = input?.selectionEnd;

  model.value = uppercase ? newModelValue.toUpperCase() : newModelValue;

  await nextTick();

  input?.setSelectionRange(start, end);
}

const { t } = useI18n();
</script>
