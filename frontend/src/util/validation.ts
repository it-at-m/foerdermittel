// eslint-disable-next-line @typescript-eslint/no-unused-vars
import type { maxRule, minRule } from "@/plugins/rules";
import type { ValidationRule } from "vuetify";

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import useOpenApiRules from "@/composables/useOpenApiRules";
import { toTrimmedString } from "@/util/formatter";

/**
 * Type that holds a sub-set of attributes in generated *ValidationAttributesMap types via the OpenAPI generator.
 * Only the attributes that are mappable to a corresponding Vuetify {@link VuetifyRuleAliases} are included.
 * Type currently not exposed publicly by OpenAPIGenerator, thus custom type required, see https://github.com/OpenAPITools/openapi-generator/pull/24623
 */
export interface ValidationAttributes {
  dataType?: string;
  required?: boolean;
  maxLength?: number;
  minLength?: number;
  pattern?: string;
  maximum?: number;
  exclusiveMaximum?: boolean;
  minimum?: number;
  exclusiveMinimum?: boolean;
}

/**
 * Type that mimics supported OpenAPI-relevant Vuetify provided rules of type RuleAliases (as the type is not publicly exported).
 * Additionally, the type holds OpenAPI-relevant custom rules defined in `plugins/rules.ts`
 */
export interface VuetifyRuleAliases {
  required: () => ValidationRule;
  strictLength: (length: number) => ValidationRule;
  minLength: (length: number) => ValidationRule;
  maxLength: (length: number) => ValidationRule;
  pattern: (pattern: RegExp) => ValidationRule;
  number: () => ValidationRule;
  // OpenAPI supported custom rules
  min?: (value: number, exclusive?: boolean) => ValidationRule;
  max?: (value: number, exclusive?: boolean) => ValidationRule;
}

/**
 * Wraps a validation rule to trim string values before validating them when requested.
 *
 * @param rule validation rule to wrap
 * @param enabled whether string values should be trimmed before validation
 */
function applyStringTrimming(
  rule: ValidationRule,
  enabled: boolean
): ValidationRule {
  if (!enabled || typeof rule !== "function") {
    return rule;
  }

  return ((value: unknown) =>
    rule(
      typeof value === "string" ? toTrimmedString(value) : value
    )) as ValidationRule;
}

/**
 * Maps OpenAPIGenerator typescript-fetch created constraints defined in *ValidationAttributesMap to Vuetify {@link ValidationRule}s.
 *
 * The mapped Vuetify rules can be used with the `rules` property on Vuetify input components.
 *
 * **Note:** Prefer using the {@link useOpenApiRules} Vue composable instead of calling this function directly.
 *
 * Supported rules currently are:
 * - {@link VuetifyRuleAliases.required}
 * - {@link VuetifyRuleAliases.strictLength}
 * - {@link VuetifyRuleAliases.minLength}
 * - {@link VuetifyRuleAliases.maxLength}
 * - {@link VuetifyRuleAliases.pattern}
 * - {@link VuetifyRuleAliases.number}
 * - Custom {@link minRule} implementation
 * - Custom {@link maxRule} implementation
 *
 * @param rules all available {@link VuetifyRuleAliases} typically retrieved via `useRules` composable from Vuetify
 * @param validationAttributes a generated *ValidationAttributesMap object
 * @param property property of the model object to calculate the {@link ValidationRule}s for.
 * @param trimStringValues trims strings before validation. Use this for inputs whose model is trimmed before it is sent to the API.
 */
export function mapOpenAPIToVuetifyValidationRules<
  T extends Record<string, ValidationAttributes>,
  K extends keyof T,
>(
  rules: VuetifyRuleAliases,
  validationAttributes?: T,
  property?: K,
  trimStringValues = false
): ValidationRule[] {
  if (!validationAttributes || !property) {
    return [];
  }

  const attributes = validationAttributes[property];
  const result: ValidationRule[] = [];

  if (!attributes) {
    return [];
  }

  // Required
  if (attributes.required !== undefined && attributes.required) {
    result.push(applyStringTrimming(rules.required(), trimStringValues));
  }

  // Strings
  const { minLength = 0, maxLength = 0 } = attributes;
  if (minLength > 0 && minLength === maxLength) {
    result.push(
      applyStringTrimming(rules.strictLength(minLength), trimStringValues)
    );
  } else {
    if (minLength > 0)
      result.push(
        applyStringTrimming(rules.minLength(minLength), trimStringValues)
      );
    if (maxLength > 0)
      result.push(
        applyStringTrimming(rules.maxLength(maxLength), trimStringValues)
      );
  }

  if (attributes.pattern !== undefined) {
    // typescript-fetch generator creates validation attribute with regex delimiters.
    // Thus remove leading and trailing slash before constructing the RegExp object
    const regex = new RegExp(attributes.pattern.replace(/^\/|\/$/g, ""));
    result.push(applyStringTrimming(rules.pattern(regex), trimStringValues));
  }

  // Numbers
  if (attributes.dataType === "number") {
    result.push(rules.number());
  }

  if (attributes.minimum !== undefined && rules.min) {
    result.push(rules.min(attributes.minimum, attributes.exclusiveMinimum));
  }

  if (attributes.maximum !== undefined && rules.max) {
    result.push(rules.max(attributes.maximum, attributes.exclusiveMaximum));
  }

  return result;
}

/**
 * Retrieves the value of an OpenAPI {@link ValidationAttributes} map for use in Vuetify input components (e.g. `counter` property)
 *
 * @param validationAttributes a generated *ValidationAttributesMap object
 * @param property property of the model object to calculate the {@link ValidationRule}s for.
 * @param constraint name of a constraint in {@link ValidationAttributes}
 */
export function getOpenAPIValidationConstraint<
  T extends Record<string, ValidationAttributes>,
  K extends keyof T,
  C extends keyof ValidationAttributes,
>(validationAttributes: T, property: K, constraint: C) {
  return validationAttributes[property]?.[constraint];
}

/**
 * Deep equal compares two objects using trimmed string values
 * <br>
 * <b>Note:</b> Does not work for non-plain objects e.g. Map, Set, RegExp, ...
 * @param a object a
 * @param b object b
 */
export function deepEqualTrimmed(a: unknown, b: unknown): boolean {
  if (typeof a === "string" && typeof b === "string") {
    return toTrimmedString(a) === toTrimmedString(b);
  }

  // Treat null and undefined as equal
  if (a == null && b == null) {
    return true;
  }

  if (a === b) {
    return true;
  }

  if (a instanceof Date || b instanceof Date) {
    return (
      a instanceof Date && b instanceof Date && a.getTime() === b.getTime()
    );
  }

  // If only one side is an array, they are not equal
  if (Array.isArray(a) || Array.isArray(b)) {
    return (
      Array.isArray(a) &&
      Array.isArray(b) &&
      a.length === b.length &&
      a.every((item, i) => deepEqualTrimmed(item, b[i]))
    );
  }

  if (a && b && typeof a === "object" && typeof b === "object") {
    const aKeys = Object.keys(a);
    const bKeys = Object.keys(b);

    return (
      aKeys.length === bKeys.length &&
      aKeys.every(
        (key) =>
          bKeys.includes(key) &&
          deepEqualTrimmed(
            (a as Record<string, unknown>)[key],
            (b as Record<string, unknown>)[key]
          )
      )
    );
  }

  return false;
}
