import type { ValidationAttributes } from "@/types/OpenAPIValidationAttributes";
import type { ValidationRule } from "vuetify/framework";

import { computed } from "vue";
import { useRules } from "vuetify/labs/rules";

import { InputDisplayMode } from "@/types/InputDisplayMode";
import {
  getOpenAPIValidationConstraint,
  mapOpenAPIToVuetifyValidationRules,
} from "@/util/validation";

export function useInputValidation(
  displayMode: InputDisplayMode,
  disableEdit: boolean,
  additionalRules: ValidationRule[] = [],
  validationAttributeMap?: Record<string, ValidationAttributes>,
  validationAttributeKey?: string
) {
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

  return {
    required,
    allRules,
    counter,
    canNotEdit,
  };
}
