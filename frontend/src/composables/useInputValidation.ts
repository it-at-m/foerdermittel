import type { ValidationAttributes } from "@/util/validation";
import type { MaybeRefOrGetter } from "vue";
import type { ValidationRule } from "vuetify/framework";

import { computed, toValue } from "vue";
import { useRules } from "vuetify";

import { InputDisplayMode } from "@/types/InputDisplayMode";
import {
  getOpenAPIValidationConstraint,
  mapOpenAPIToVuetifyValidationRules,
} from "@/util/validation";

export function useInputValidation(
  displayMode: MaybeRefOrGetter<InputDisplayMode>,
  disableEdit: MaybeRefOrGetter<boolean>,
  additionalRules: MaybeRefOrGetter<ValidationRule[]> = [],
  validationAttributeMap?: MaybeRefOrGetter<
    Record<string, ValidationAttributes> | undefined
  >,
  validationAttributeKey?: MaybeRefOrGetter<string | undefined>
) {
  const required = computed(() => {
    const resolvedValidationAttributeMap = toValue(validationAttributeMap);
    const resolvedValidationAttributeKey = toValue(validationAttributeKey);

    return (
      (resolvedValidationAttributeMap &&
        resolvedValidationAttributeKey &&
        getOpenAPIValidationConstraint(
          resolvedValidationAttributeMap,
          resolvedValidationAttributeKey,
          "required"
        )) ??
      false
    );
  });

  const rules = useRules();
  const allRules = computed(() => {
    const resolvedValidationAttributeMap = toValue(validationAttributeMap);
    const resolvedValidationAttributeKey = toValue(validationAttributeKey);

    if (!resolvedValidationAttributeMap || !resolvedValidationAttributeKey) {
      return toValue(additionalRules);
    }

    return [
      ...mapOpenAPIToVuetifyValidationRules(
        rules,
        resolvedValidationAttributeMap,
        resolvedValidationAttributeKey
      ),
      ...toValue(additionalRules),
    ];
  });

  const counter = computed(() => {
    const resolvedValidationAttributeMap = toValue(validationAttributeMap);
    const resolvedValidationAttributeKey = toValue(validationAttributeKey);

    if (!resolvedValidationAttributeMap || !resolvedValidationAttributeKey) {
      return undefined;
    }

    return getOpenAPIValidationConstraint(
      resolvedValidationAttributeMap,
      resolvedValidationAttributeKey,
      "maxLength"
    ) as number | undefined;
  });

  const canNotEdit = computed(
    () =>
      toValue(displayMode) === InputDisplayMode.READ ||
      (toValue(displayMode) === InputDisplayMode.EDIT && toValue(disableEdit))
  );

  return {
    required,
    allRules,
    counter,
    canNotEdit,
  };
}
