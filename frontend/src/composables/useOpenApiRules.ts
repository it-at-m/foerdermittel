import type { ValidationAttributes } from "@/util/validation";
import type { MaybeRefOrGetter } from "vue";
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import type { ValidationRule } from "vuetify";

import { computed, toValue } from "vue";
import { useRules } from "vuetify";

import { mapOpenAPIToVuetifyValidationRules } from "@/util/validation";

/**
 * Creates Vuetify {@link ValidationRule}s from OpenAPI-generated validation
 * attributes.
 *
 * See supported rules in {@link VuetifyRuleAliases}.
 *
 * @param options Validation options.
 * @param options.validationAttributesMap OpenAPI-generated validation attributes map.
 * @param options.property Optional model property. If omitted, returns a factory
 * function for the whole attributes map.
 * @param options.trimStringValues Whether string values should be trimmed before validation.
 */
export default function useOpenApiRules<
  T extends Record<string, ValidationAttributes>,
  K extends keyof T,
>({
  validationAttributesMap,
  property,
  trimStringValues,
}: {
  validationAttributesMap: MaybeRefOrGetter<T | undefined>;
  property?: MaybeRefOrGetter<K | undefined>;
  trimStringValues?: MaybeRefOrGetter<boolean>;
}) {
  const rules = useRules();

  const valMap = toValue(validationAttributesMap);

  const calculateRules = <P extends keyof T>(
    property: MaybeRefOrGetter<P | undefined>
  ) =>
    computed(() => {
      const map = toValue(validationAttributesMap);
      const key = toValue(property);

      if (map === undefined || key === undefined) {
        return [];
      }

      return mapOpenAPIToVuetifyValidationRules(
        rules,
        map,
        key,
        toValue(trimStringValues) ?? false
      );
    });

  if (valMap === undefined) {
    return computed(() => []);
  }

  if (property === undefined) {
    return <P extends keyof T>(property: MaybeRefOrGetter<P | undefined>) =>
      calculateRules(property);
  }

  return calculateRules(property);
}
