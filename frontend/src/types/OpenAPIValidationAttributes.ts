// eslint-disable-next-line @typescript-eslint/no-unused-vars
import type { RuleAliases } from "vuetify/labs/rules";

/**
 * This interface holds most available in generated *ValidationAttributesMap types via the OpenAPI generator.
 * Only the attributes that are mappable to a corresponding Vuetify {@link RuleAliases} are included.
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
