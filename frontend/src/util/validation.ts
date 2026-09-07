import type { ValidationAttributes } from "@/types/OpenAPIValidationAttributes";
import type { ValidationRule } from "vuetify/framework";
import type { RuleAliases } from "vuetify/labs/rules";

import { toTrimmedString } from "@/util/formatter";

function withTrimmedStringValue(
  rule: ValidationRule,
  trimStringValues: boolean
): ValidationRule {
  if (!trimStringValues || typeof rule !== "function") {
    return rule;
  }

  return ((value: unknown) =>
    rule(
      typeof value === "string" ? toTrimmedString(value) : value
    )) as ValidationRule;
}

/**
 * Calculates Vuetify {@link ValidationRule}s for a single input component using *ValidationAttributesMap object generated using OpenAPIGenerator typescript-fetch generator
 * Supported rules currently are:
 * - {@link RuleAliases.required}
 * - {@link RuleAliases.strictLength}
 * - {@link RuleAliases.minLength}
 * - {@link RuleAliases.maxLength}
 * - {@link RuleAliases.pattern}
 * - {@link RuleAliases.number}
 * - Custom {@link minRule} implementation
 * - Custom {@link maxRule} implementation
 *
 * @param rules all available {@link RuleAliases} typically retrieved via `useRules` composable from Vuetify
 * @param validationAttributes a generated *ValidationAttributesMap object
 * @param property property of the model object to calculate the {@link ValidationRule}s for.
 * @param trimStringValues trims strings before validation. Use this for inputs whose model is trimmed before it is sent to the API.
 */
export function mapOpenAPIToVuetifyValidationRules<
  T extends Record<string, ValidationAttributes>,
  K extends keyof T,
>(
  rules: RuleAliases,
  validationAttributes: T,
  property: K,
  trimStringValues = false
): ValidationRule[] {
  const attributes = validationAttributes[property];
  const result: ValidationRule[] = [];

  if (!attributes) {
    console.debug(
      `Validation property "${String(property)}" not found in ${JSON.stringify(validationAttributes)}"`
    );
    return [];
  }

  // Required
  if (attributes.required !== undefined && attributes.required) {
    result.push(withTrimmedStringValue(rules.required(), trimStringValues));
  }

  // Strings
  if (
    attributes.minLength !== undefined &&
    attributes.maxLength !== undefined &&
    attributes.minLength === attributes.maxLength
  ) {
    result.push(
      withTrimmedStringValue(
        rules.strictLength(attributes.minLength),
        trimStringValues
      )
    );
  } else {
    if (attributes.minLength !== undefined && attributes.minLength > 0) {
      result.push(
        withTrimmedStringValue(
          rules.minLength(attributes.minLength),
          trimStringValues
        )
      );
    }

    if (attributes.maxLength !== undefined && attributes.maxLength > 0) {
      result.push(
        withTrimmedStringValue(
          rules.maxLength(attributes.maxLength),
          trimStringValues
        )
      );
    }
  }

  if (attributes.pattern !== undefined) {
    const regex = new RegExp(attributes.pattern.replace(/^\/|\/$/g, ""));
    result.push(withTrimmedStringValue(rules.pattern(regex), trimStringValues));
  }

  // Numbers
  if (attributes.dataType === "number") {
    result.push(rules.number());
  }

  if (attributes.minimum !== undefined && rules["min"]) {
    result.push(rules["min"](attributes.minimum, attributes.exclusiveMinimum));
  }

  if (attributes.maximum !== undefined && rules["max"]) {
    result.push(rules["max"](attributes.maximum, attributes.exclusiveMaximum));
  }

  return result;
}

/**
 * Retrieves the value of an OpenAPI {@link ValidationAttributes} map for use in Vuetify input components (e.g. `counter`)
 *
 * @param validationAttributes a generated *ValidationAttributesMap object
 * @param property property of the model object to calculate the {@link ValidationRule}s for.
 * @param constraint name of an constraint in {@link ValidationAttributes}
 */
export function getOpenAPIValidationConstraint<
  T extends Record<string, ValidationAttributes>,
  K extends keyof T,
  C extends keyof ValidationAttributes,
>(validationAttributes: T, property: K, constraint: C) {
  const attributes = validationAttributes[property];

  if (!attributes) {
    console.debug(
      `Validation property "${String(property)}" not found in ${JSON.stringify(validationAttributes)}"`
    );
    return undefined;
  }

  return attributes[constraint];
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
