<template>
  <v-textarea
    v-model="model"
    :readonly="canNotEdit"
    :counter="counter"
    :rules="allRules"
    v-bind="$attrs"
    :hide-details="displayMode === InputDisplayMode.READ"
    @blur="trimModel"
  >
    <template #label>
      {{ label }}
      <span
        v-if="required && !canNotEdit"
        class="text-red"
      >
        {{ t("common.word.required") }}
      </span>
      <span v-if="displayMode === InputDisplayMode.EDIT && canNotEdit">{{
        t("common.word.readOnly")
      }}</span>
    </template>
  </v-textarea>
</template>

<script
  setup
  lang="ts"
  generic="
    M extends Record<string, ValidationAttributes>,
    K extends keyof M & string
  "
>
import type { ValidationAttributes } from "@/types/OpenAPIValidationAttributes";
import type { ValidationRule } from "vuetify/framework";

import { useI18n } from "vue-i18n";

import { useInputValidation } from "@/composables/useInputValidation";
import { InputDisplayMode } from "@/types/InputDisplayMode";
import { toTrimmedString } from "@/util/formatter";

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
  validationAttributeKey
);

const model = defineModel<string>();

function trimModel() {
  model.value = toTrimmedString(model.value);
}

const { t } = useI18n();
</script>
