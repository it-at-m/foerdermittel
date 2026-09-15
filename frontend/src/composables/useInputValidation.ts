import type { ValidationAttributes } from "@/util/validation";
import type { MaybeRefOrGetter } from "vue";
import type { ValidationRule } from "vuetify";

import { computed, toValue } from "vue";

import useOpenApiRules from "@/composables/useOpenApiRules";
import { InputDisplayMode } from "@/types/InputDisplayMode";
import { getOpenAPIValidationConstraint } from "@/util/validation";

export function useInputValidation(
  displayMode: MaybeRefOrGetter<InputDisplayMode>,
  disableEdit: MaybeRefOrGetter<boolean>,
  additionalRules: MaybeRefOrGetter<ValidationRule[]> = [],
  validationAttributeMap?: MaybeRefOrGetter<
    Record<string, ValidationAttributes> | undefined
  >,
  validationAttributeKey?: MaybeRefOrGetter<string | undefined>,
  trimStringValues = false
) {
  const openApiRules = useOpenApiRules(
    validationAttributeMap,
    validationAttributeKey,
    trimStringValues
  );

  const required = computed(() => {
    const map = toValue(validationAttributeMap);
    const key = toValue(validationAttributeKey);

    return (
      (map && key && getOpenAPIValidationConstraint(map, key, "required")) ??
      false
    );
  });

  const allRules = computed(() => [
    ...openApiRules.value,
    ...toValue(additionalRules),
  ]);

  const counter = computed(() => {
    const map = toValue(validationAttributeMap);
    const key = toValue(validationAttributeKey);

    if (!map || !key) {
      return undefined;
    }

    return getOpenAPIValidationConstraint(map, key, "maxLength") as
      number | undefined;
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
