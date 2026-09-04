<template>
  <v-autocomplete
    v-if="displayMode !== InputDisplayMode.READ"
    v-model="model"
    :readonly="canNotEdit"
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
      <span v-if="displayMode === InputDisplayMode.EDIT && canNotEdit">{{
        t("common.word.readOnly")
      }}</span>
    </template>
  </v-autocomplete>
  <v-textarea
    v-else
    :model-value="model"
    :label="label"
    readonly
    hide-details
    variant="plain"
    v-bind="$attrs"
  />
</template>

<script
  setup
  lang="ts"
  generic="
    T,
    M extends Record<string, ValidationAttributes>,
    K extends keyof M & string
  "
>
import type { ValidationAttributes } from "@/util/validation";
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

const { required, allRules, canNotEdit } = useInputValidation(
  displayMode,
  disableEdit,
  additionalRules,
  validationAttributeMap,
  validationAttributeKey
);

const model = defineModel<T>();

const { t } = useI18n();
</script>
