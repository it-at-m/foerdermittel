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

import { computed, nextTick, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";
import { useRules } from "vuetify/labs/rules";

import { InputDisplayMode } from "@/types/InputDisplayMode";
import {
  getOpenAPIValidationConstraint,
  mapOpenAPIToVuetifyValidationRules,
} from "@/util/validation";

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

const required = computed(
  () =>
    (validationAttributeMap &&
      validationAttributeKey &&
      getOpenAPIValidationConstraint(
        validationAttributeMap,
        validationAttributeKey,
        "required"
      )) ??
    false
);

const rules = useRules();
const allRules = computed(() => {
  if (!validationAttributeMap || !validationAttributeKey) {
    return additionalRules;
  }

  return [
    ...mapOpenAPIToVuetifyValidationRules(
      rules,
      validationAttributeMap,
      validationAttributeKey
    ),
    ...additionalRules,
  ];
});

const counter = computed(() =>
  !validationAttributeMap || !validationAttributeKey
    ? undefined
    : (getOpenAPIValidationConstraint(
        validationAttributeMap,
        validationAttributeKey,
        "maxLength"
      ) as number)
);

const canNotEdit = computed(
  () =>
    displayMode === InputDisplayMode.READ ||
    (displayMode === InputDisplayMode.EDIT && disableEdit)
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
