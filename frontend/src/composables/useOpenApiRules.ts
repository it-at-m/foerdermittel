import type { ValidationAttributes } from "@/util/validation";
import type { ComputedRef, MaybeRefOrGetter } from "vue";
import type { ValidationRule } from "vuetify";

import { computed, toValue } from "vue";
import { useRules } from "vuetify";

import { mapOpenAPIToVuetifyValidationRules } from "@/util/validation";

export default function useOpenApiRules<
  T extends Record<string, ValidationAttributes>,
  K extends keyof T,
>(
  validationAttributesMap: MaybeRefOrGetter<T | undefined>,
  property: MaybeRefOrGetter<K | undefined>,
  trimStringValues?: MaybeRefOrGetter<boolean>
): ComputedRef<ValidationRule[]>;

export default function useOpenApiRules<
  T extends Record<string, ValidationAttributes>,
>(
  validationAttributesMap: MaybeRefOrGetter<T | undefined>,
  trimStringValues?: MaybeRefOrGetter<boolean>
): <K extends keyof T>(
  property: MaybeRefOrGetter<K | undefined>
) => ComputedRef<ValidationRule[]>;

/**
 * Creates Vuetify {@link ValidationRule}s from OpenAPI-generated validation
 * attributes.
 *
 * See supported rules in {@link VuetifyRuleAliases}.
 */
export default function useOpenApiRules<
  T extends Record<string, ValidationAttributes>,
  K extends keyof T,
>(
  validationAttributesMap: MaybeRefOrGetter<T | undefined>,
  propertyOrTrim?: MaybeRefOrGetter<K> | MaybeRefOrGetter<boolean>,
  trimStringValues?: MaybeRefOrGetter<boolean>
) {
  const rules = useRules();

  const calculateRules = (
    property: MaybeRefOrGetter<K | undefined>,
    trimStringValues?: MaybeRefOrGetter<boolean>
  ) =>
    computed(() =>
      mapOpenAPIToVuetifyValidationRules(
        rules,
        toValue(validationAttributesMap),
        toValue(property),
        toValue(trimStringValues) ?? false
      )
    );

  // If the second argument is omitted, return a property factory.
  if (propertyOrTrim === undefined) {
    return (property: MaybeRefOrGetter<K>) =>
      calculateRules(property, trimStringValues);
  }

  return calculateRules(
    propertyOrTrim as MaybeRefOrGetter<K>,
    trimStringValues
  );
}
