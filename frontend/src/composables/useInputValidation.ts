import type { ValidationAttributes } from "@/util/validation";
import type { ComputedRef, MaybeRefOrGetter } from "vue";
import type { ValidationRule } from "vuetify";

import { computed, toValue } from "vue";

import useOpenApiRules from "@/composables/useOpenApiRules";
import { InputDisplayMode } from "@/types/InputDisplayMode";
import { getOpenAPIValidationConstraint } from "@/util/validation";

export function useInputValidation(
  displayMode: MaybeRefOrGetter<InputDisplayMode>,
  disableEdit: MaybeRefOrGetter<boolean>,
  additionalRules: MaybeRefOrGetter<ValidationRule[]> = [],
  validationAttributesMap?: MaybeRefOrGetter<
    Record<string, ValidationAttributes> | undefined
  >,
  property?: MaybeRefOrGetter<string | undefined>,
  trimStringValues = false,
  // overrides the required marker from the OpenAPI spec, e.g. for conditionally required fields
  requiredOverride: MaybeRefOrGetter<boolean | undefined> = undefined
) {
  const openApiRules = useOpenApiRules({
    validationAttributesMap,
    property,
    trimStringValues,
  }) as ComputedRef<ValidationRule[]>;

  const required = computed(() => {
    const override = toValue(requiredOverride);
    if (override !== undefined) {
      return override;
    }

    const map = toValue(validationAttributesMap);
    const key = toValue(property);

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
    const map = toValue(validationAttributesMap);
    const key = toValue(property);

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
