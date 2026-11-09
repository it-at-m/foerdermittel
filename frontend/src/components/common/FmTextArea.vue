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
        v-if="isRequired && !canNotEdit"
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
import type { ValidationAttributes } from "@/util/validation";
import type { ValidationRule } from "vuetify/framework";

import { computed } from "vue";
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
  required = undefined,
} = defineProps<{
  label: string;
  displayMode?: InputDisplayMode;
  disableEdit?: boolean;
  validationAttributeMap?: M;
  validationAttributeKey?: K;
  additionalRules?: ValidationRule[];
  /** Overrides the required marker from the OpenAPI spec, e.g. for conditionally required fields */
  required?: boolean;
}>();

const {
  required: specRequired,
  allRules,
  counter,
  canNotEdit,
} = useInputValidation(
  displayMode,
  disableEdit,
  additionalRules,
  validationAttributeMap,
  validationAttributeKey,
  true
);

const isRequired = computed(() => required ?? specRequired.value);

const model = defineModel<string>();

function trimModel() {
  model.value = toTrimmedString(model.value);
}

const { t } = useI18n();
</script>
