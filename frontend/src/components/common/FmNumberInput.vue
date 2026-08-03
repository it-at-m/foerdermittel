<template>
  <v-number-input
    v-if="displayMode !== InputDisplayMode.READ"
    v-model="model"
    :readonly="canNotEdit"
    :counter="counter"
    :rules="allRules"
    v-bind="$attrs"
  >
    <template #label>
      {{ label }}
      <span
        v-if="required && !canNotEdit"
        class="text-red"
        >{{ t("common.word.required") }}</span
      >
      <span v-if="displayMode == InputDisplayMode.EDIT && canNotEdit">{{
        t("common.word.readOnly")
      }}</span>
    </template>
  </v-number-input>
  <v-textarea
    v-else
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
import type { ValidationRule } from "vuetify/framework";

import { useI18n } from "vue-i18n";

import { useInputValidation } from "@/composables/useInputValidation";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const {
  displayMode = InputDisplayMode.CREATE,
  disableEdit = false,
  validationAttributeMap,
  validationAttributeKey,
  additionalRules = [],
} = defineProps<{
  label: string;
  displayMode?: InputDisplayMode;
  disableEdit?: boolean;
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

const model = defineModel<number>();

const { t } = useI18n();
</script>
