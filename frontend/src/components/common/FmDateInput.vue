<template>
  <v-date-input
    v-if="displayMode !== InputDisplayMode.READ"
    v-model="model"
    :rules="allRules"
    :readonly="canNotEdit"
    :menu-props="canNotEdit ? { modelValue: false } : undefined"
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
  </v-date-input>
  <v-textarea
    v-else
    :model-value="formattedModel"
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
  generic="
    M extends Record<string, ValidationAttributes>,
    K extends keyof M & string
  "
>
import type { ValidationAttributes } from "@/types/OpenAPIValidationAttributes";
import type { ValidationRule } from "vuetify/framework";

import { computed } from "vue";
import { useI18n } from "vue-i18n";
import { useDate } from "vuetify/framework";

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

const model = defineModel<Date | null>();

const date = useDate();
const formattedModel = computed(() => {
  return model.value ? date.format(model.value, "fullDate") : "";
});

const { t } = useI18n();
</script>
