// eslint-disable-next-line @typescript-eslint/no-unused-vars
import type { maxRule, minRule } from "@/plugins/rules";
import type { ValidationRule } from "vuetify";

import { toTrimmedString } from "@/util/formatter";

/**
 * Type that holds a sub-set of attributes in generated *ValidationAttributesMap types via the OpenAPI generator.
 * Only the attributes that are mappable to a corresponding Vuetify {@link VuetifyRuleAliases} are included.
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
 * */
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
 * Calculates Vuetify {@link ValidationRule}s for a single input component using *ValidationAttributesMap object generated using OpenAPIGenerator typescript-fetch generator
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
 */
export function mapOpenAPIToVuetifyValidationRules<
  T extends Record<string, ValidationAttributes>,
  K extends keyof T,
>(
  rules: VuetifyRuleAliases,
  validationAttributes: T,
  property: K
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
    result.push(rules.required());
  }

  // Strings
  if (
    attributes.minLength !== undefined &&
    attributes.maxLength !== undefined &&
    attributes.minLength === attributes.maxLength
  ) {
    result.push(rules.strictLength(attributes.minLength));
  } else {
    if (attributes.minLength !== undefined && attributes.minLength > 0) {
      result.push(rules.minLength(attributes.minLength));
    }

    if (attributes.maxLength !== undefined && attributes.maxLength > 0) {
      result.push(rules.maxLength(attributes.maxLength));
    }
  }

  if (attributes.pattern !== undefined) {
    const regex = new RegExp(attributes.pattern.replace(/^\/|\/$/g, ""));
    result.push(rules.pattern(regex));
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
 * <b>Note:</b> Does not work for non-plain objects e.g. Date, Map, Set, RegExp, ...
 * @param a object a
 * @param b object b
 */
export function deepEqualTrimmed(a: unknown, b: unknown): boolean {
  if (typeof a === "string" && typeof b === "string") {
    return toTrimmedString(a) === toTrimmedString(b);
  }

  if (a === b) {
    return true;
  }

  if (a instanceof Date && b instanceof Date) {
    return a.getTime() === b.getTime();
  }

  if (Array.isArray(a) && Array.isArray(b)) {
    return (
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
