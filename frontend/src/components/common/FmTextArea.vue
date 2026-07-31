<template>
  <v-textarea
    v-model="model"
    :readonly="canNotEdit"
    :class="{
      'pointer-events-none': canNotEdit,
    }"
    :counter="counter"
    :rules="allRules"
    v-bind="$attrs"
    :hide-details="displayMode === InputDisplayMode.READ"
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
  generic="M extends Record<string, ValidationAttributes>, K extends keyof M"
>
import type { ValidationAttributes } from "@/types/OpenAPIValidationAttributes";
import type { ValidationRule } from "vuetify/framework";

import { computed } from "vue";
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
    : getOpenAPIValidationConstraint(
        validationAttributeMap,
        validationAttributeKey,
        "maxLength"
      )
);

const canNotEdit = computed(
  () =>
    displayMode === InputDisplayMode.READ ||
    (displayMode === InputDisplayMode.EDIT && disableEdit)
);

const model = defineModel<string>();

const { t } = useI18n();
</script>
