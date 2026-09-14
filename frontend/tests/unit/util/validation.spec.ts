import { beforeEach, describe, expect, test, vi } from "vitest";

import type {
  ValidationAttributes,
  VuetifyRuleAliases,
} from "../../../src/util/validation";

import {
  deepEqualTrimmed,
  getOpenAPIValidationConstraint,
  mapOpenAPIToVuetifyValidationRules,
} from "../../../src/util/validation";

describe("deepEqualTrimmed", () => {
  describe("strings", () => {
    test("returns true for equal strings", () => {
      expect(deepEqualTrimmed("hello", "hello")).toBe(true);
    });

    test("returns true for strings that are equal after trimming", () => {
      expect(deepEqualTrimmed("  hello  ", "hello")).toBe(true);
      expect(deepEqualTrimmed("hello ", " hello")).toBe(true);
    });

    test("returns false for different strings after trimming", () => {
      expect(deepEqualTrimmed("hello", "world")).toBe(false);
    });

    test("returns true for whitespace-only strings", () => {
      expect(deepEqualTrimmed("   ", "")).toBe(true);
    });
  });

  describe("null and undefined", () => {
    test("returns true for null and null", () => {
      expect(deepEqualTrimmed(null, null)).toBe(true);
    });

    test("returns true for undefined and undefined", () => {
      expect(deepEqualTrimmed(undefined, undefined)).toBe(true);
    });

    test("returns true for null and undefined", () => {
      expect(deepEqualTrimmed(null, undefined)).toBe(true);
    });

    test("returns true for undefined and null", () => {
      expect(deepEqualTrimmed(undefined, null)).toBe(true);
    });

    test("returns false when only one value is nullish", () => {
      expect(deepEqualTrimmed(null, "")).toBe(false);
      expect(deepEqualTrimmed(undefined, "")).toBe(false);
      expect(deepEqualTrimmed(null, 0)).toBe(false);
      expect(deepEqualTrimmed(undefined, false)).toBe(false);
    });
  });

  describe("primitive values", () => {
    test("returns true for equal numbers", () => {
      expect(deepEqualTrimmed(42, 42)).toBe(true);
    });

    test("returns false for different numbers", () => {
      expect(deepEqualTrimmed(42, 43)).toBe(false);
    });

    test("returns true for equal booleans", () => {
      expect(deepEqualTrimmed(true, true)).toBe(true);
      expect(deepEqualTrimmed(false, false)).toBe(true);
    });

    test("returns false for different booleans", () => {
      expect(deepEqualTrimmed(true, false)).toBe(false);
    });

    test("returns false for different primitive types", () => {
      expect(deepEqualTrimmed("1", 1)).toBe(false);
      expect(deepEqualTrimmed("true", true)).toBe(false);
      expect(deepEqualTrimmed(0, false)).toBe(false);
    });

    test("returns true for the same symbol reference", () => {
      const symbol = Symbol("test");

      expect(deepEqualTrimmed(symbol, symbol)).toBe(true);
    });

    test("returns false for different symbols", () => {
      expect(deepEqualTrimmed(Symbol("test"), Symbol("test"))).toBe(false);
    });
  });

  describe("dates", () => {
    test("returns true for dates representing the same time", () => {
      expect(
        deepEqualTrimmed(
          new Date("2026-08-31T12:00:00Z"),
          new Date("2026-08-31T12:00:00Z")
        )
      ).toBe(true);
    });

    test("returns false for dates representing different times", () => {
      expect(
        deepEqualTrimmed(
          new Date("2026-08-31T12:00:00Z"),
          new Date("2026-08-31T13:00:00Z")
        )
      ).toBe(false);
    });

    test("returns false when comparing a date with another type", () => {
      expect(
        deepEqualTrimmed(
          new Date("2026-08-31T12:00:00Z"),
          "2026-08-31T12:00:00Z"
        )
      ).toBe(false);
    });
  });

  describe("arrays", () => {
    test("returns true for equal arrays", () => {
      expect(deepEqualTrimmed([1, 2, 3], [1, 2, 3])).toBe(true);
    });

    test("returns true for the same array reference", () => {
      const array = [1, 2, 3];

      expect(deepEqualTrimmed(array, array)).toBe(true);
    });

    test("returns false for arrays with different lengths", () => {
      expect(deepEqualTrimmed([1, 2], [1, 2, 3])).toBe(false);
    });

    test("returns false for arrays with different values", () => {
      expect(deepEqualTrimmed([1, 2, 3], [1, 2, 4])).toBe(false);
    });

    test("compares array strings using trimmed values", () => {
      expect(deepEqualTrimmed([" hello ", " world"], ["hello", "world "])).toBe(
        true
      );
    });

    test("treats null and undefined array items as equal", () => {
      expect(deepEqualTrimmed([1, null, 3], [1, undefined, 3])).toBe(true);
    });

    test("supports nested arrays", () => {
      expect(
        deepEqualTrimmed([[" foo "], [null]], [["foo"], [undefined]])
      ).toBe(true);
    });

    test("returns false when comparing an array with a plain object", () => {
      expect(deepEqualTrimmed([1, 2], { 0: 1, 1: 2 })).toBe(false);
    });
  });

  describe("objects", () => {
    test("returns true for empty objects", () => {
      expect(deepEqualTrimmed({}, {})).toBe(true);
    });

    test("returns true for the same object reference", () => {
      const object = { foo: "bar" };

      expect(deepEqualTrimmed(object, object)).toBe(true);
    });

    test("returns true for objects with equal properties", () => {
      expect(
        deepEqualTrimmed({ foo: "bar", count: 1 }, { foo: "bar", count: 1 })
      ).toBe(true);
    });

    test("does not depend on property order", () => {
      expect(
        deepEqualTrimmed({ foo: "bar", count: 1 }, { count: 1, foo: "bar" })
      ).toBe(true);
    });

    test("compares object strings using trimmed values", () => {
      expect(
        deepEqualTrimmed(
          { firstName: " Alice ", lastName: " Smith " },
          { firstName: "Alice", lastName: "Smith" }
        )
      ).toBe(true);
    });

    test("treats null and undefined property values as equal", () => {
      expect(deepEqualTrimmed({ foo: null }, { foo: undefined })).toBe(true);
    });

    test("treats null and undefined as equal in nested objects", () => {
      expect(
        deepEqualTrimmed(
          {
            user: {
              name: " Alice ",
              address: {
                street: null,
              },
            },
          },
          {
            user: {
              name: "Alice",
              address: {
                street: undefined,
              },
            },
          }
        )
      ).toBe(true);
    });

    test("returns false for different property values", () => {
      expect(deepEqualTrimmed({ foo: "bar" }, { foo: "baz" })).toBe(false);
    });

    test("returns false when objects have different numbers of properties", () => {
      expect(deepEqualTrimmed({ foo: "bar" }, { foo: "bar", baz: 1 })).toBe(
        false
      );
    });

    test("returns false when objects have different property names", () => {
      expect(deepEqualTrimmed({ foo: "value" }, { bar: "value" })).toBe(false);
    });

    test("considers an explicitly undefined property different from a missing property", () => {
      expect(deepEqualTrimmed({ foo: undefined }, {})).toBe(false);
    });

    test("considers an explicitly null property different from a missing property", () => {
      expect(deepEqualTrimmed({ foo: null }, {})).toBe(false);
    });

    test("supports arrays nested inside objects", () => {
      expect(
        deepEqualTrimmed(
          {
            values: [" foo ", null, { value: " bar " }],
          },
          {
            values: ["foo", undefined, { value: "bar" }],
          }
        )
      ).toBe(true);
    });

    test("supports dates nested inside objects", () => {
      expect(
        deepEqualTrimmed(
          {
            createdAt: new Date("2026-08-31T12:00:00Z"),
          },
          {
            createdAt: new Date("2026-08-31T12:00:00Z"),
          }
        )
      ).toBe(true);
    });
  });

  describe("mixed values", () => {
    test("returns false for an object and a primitive", () => {
      expect(deepEqualTrimmed({}, 1)).toBe(false);
      expect(deepEqualTrimmed({}, "test")).toBe(false);
    });

    test("returns false for an array and a primitive", () => {
      expect(deepEqualTrimmed([], 1)).toBe(false);
      expect(deepEqualTrimmed([], "test")).toBe(false);
    });
  });

  describe("complex nested structures", () => {
    test("deeply compares trimmed strings and nullish values", () => {
      const a = {
        name: " Alice ",
        age: 42,
        active: true,
        optional: null,
        createdAt: new Date("2026-08-31T12:00:00Z"),
        tags: [" admin ", "user"],
        address: {
          street: " Main Street ",
          apartment: undefined,
          coordinates: [1, 2],
        },
      };

      const b = {
        name: "Alice",
        age: 42,
        active: true,
        optional: undefined,
        createdAt: new Date("2026-08-31T12:00:00Z"),
        tags: ["admin", " user "],
        address: {
          street: "Main Street",
          apartment: null,
          coordinates: [1, 2],
        },
      };

      expect(deepEqualTrimmed(a, b)).toBe(true);
    });

    test("returns false when a deeply nested value differs", () => {
      const a = {
        user: {
          address: {
            city: "Berlin",
          },
        },
      };

      const b = {
        user: {
          address: {
            city: "Munich",
          },
        },
      };

      expect(deepEqualTrimmed(a, b)).toBe(false);
    });
  });
});

describe("mapOpenAPIToVuetifyValidationRules", () => {
  let rules: VuetifyRuleAliases;

  beforeEach(() => {
    rules = {
      required: vi.fn(() => "required-rule"),
      strictLength: vi.fn((length) => `strict-length-${length}`),
      minLength: vi.fn((length) => `min-length-${length}`),
      maxLength: vi.fn((length) => `max-length-${length}`),
      pattern: vi.fn((pattern) => pattern),
      number: vi.fn(() => "number-rule"),
      min: vi.fn((value, exclusive) => `min-${value}-${exclusive}`),
      max: vi.fn((value, exclusive) => `max-${value}-${exclusive}`),
    };
  });

  test.concurrent.for([
    {
      description: "returns no rules when the property does not exist",
      attributes: {
        name: {
          required: true,
        },
      },
      property: "unknown",
      expected: [],
    },
    {
      description: "adds the required rule",
      attributes: {
        name: {
          required: true,
        },
      },
      property: "name",
      expected: ["required-rule"],
    },
    {
      description: "does not add required when required is false",
      attributes: {
        name: {
          required: false,
        },
      },
      property: "name",
      expected: [],
    },
    {
      description: "maps equal minLength and maxLength to strictLength",
      attributes: {
        value: {
          minLength: 10,
          maxLength: 10,
        },
      },
      property: "value",
      expected: ["strict-length-10"],
    },
    {
      description: "maps minLength and maxLength independently",
      attributes: {
        value: {
          minLength: 2,
          maxLength: 20,
        },
      },
      property: "value",
      expected: ["min-length-2", "max-length-20"],
    },
    {
      description: "does not add string length rules for zero",
      attributes: {
        value: {
          minLength: 0,
          maxLength: 0,
        },
      },
      property: "value",
      expected: [],
    },
    {
      description: "adds the number rule for number data types",
      attributes: {
        value: {
          dataType: "number",
        },
      },
      property: "value",
      expected: ["number-rule"],
    },
    {
      description: "does not add the number rule for other data types",
      attributes: {
        value: {
          dataType: "string",
        },
      },
      property: "value",
      expected: [],
    },
    {
      description: "maps minimum to the custom min rule",
      attributes: {
        value: {
          minimum: 10,
        },
      },
      property: "value",
      expected: ["min-10-undefined"],
    },
    {
      description: "passes exclusiveMinimum to the min rule",
      attributes: {
        value: {
          minimum: 10,
          exclusiveMinimum: true,
        },
      },
      property: "value",
      expected: ["min-10-true"],
    },
    {
      description: "maps maximum to the custom max rule",
      attributes: {
        value: {
          maximum: 100,
        },
      },
      property: "value",
      expected: ["max-100-undefined"],
    },
    {
      description: "passes exclusiveMaximum to the max rule",
      attributes: {
        value: {
          maximum: 100,
          exclusiveMaximum: true,
        },
      },
      property: "value",
      expected: ["max-100-true"],
    },
  ])("$description", ({ attributes, property, expected }) => {
    const result = mapOpenAPIToVuetifyValidationRules(
      rules,
      attributes,
      property as never
    );

    expect(result).toEqual(expected);
  });

  test.concurrent.for([
    {
      rule: "min",
      attributes: {
        value: {
          minimum: 10,
        },
      },
    },
    {
      rule: "max",
      attributes: {
        value: {
          maximum: 100,
        },
      },
    },
  ])(
    "does not add custom rule '$rule' when unavailable",
    ({ rule, attributes }) => {
      const rulesWithoutRule = {
        ...rules,
        [rule]: undefined,
      };

      const result = mapOpenAPIToVuetifyValidationRules(
        rulesWithoutRule,
        attributes,
        "value"
      );

      expect(result).toEqual([]);
    }
  );

  test("maps pattern strings to regular expressions", () => {
    const attributes = {
      value: {
        pattern: "/^[A-Z]+$/",
      },
    };

    mapOpenAPIToVuetifyValidationRules(rules, attributes, "value");

    expect(rules.pattern).toHaveBeenCalledOnce();

    const regex = vi.mocked(rules.pattern).mock.calls[0][0];

    expect(regex).toBeInstanceOf(RegExp);
    expect(regex.source).toBe("^[A-Z]+$");
  });

  test("maps all applicable rules in the expected order", () => {
    const attributes = {
      value: {
        required: true,
        minLength: 2,
        maxLength: 20,
        pattern: "/^[a-z]+$/",
        dataType: "number",
        minimum: 1,
        exclusiveMinimum: true,
        maximum: 100,
        exclusiveMaximum: false,
      },
    };

    const result = mapOpenAPIToVuetifyValidationRules(
      rules,
      attributes,
      "value"
    );

    expect(result).toEqual([
      "required-rule",
      "min-length-2",
      "max-length-20",
      /^[a-z]+$/,
      "number-rule",
      "min-1-true",
      "max-100-false",
    ]);
  });
});

describe("getOpenAPIValidationConstraint", () => {
  interface TestCase {
    attributes: Record<string, ValidationAttributes>;
    property: string;
    constraint: keyof ValidationAttributes;
    expected: unknown;
  }

  test.concurrent.for<TestCase>([
    {
      attributes: {
        name: {
          required: true,
          maxLength: 100,
        },
      },
      property: "name",
      constraint: "maxLength",
      expected: 100,
    },
    {
      attributes: {
        name: {
          required: true,
          maxLength: 100,
        },
      },
      property: "name",
      constraint: "required",
      expected: true,
    },
    {
      attributes: {
        name: {},
      },
      property: "name",
      constraint: "maxLength",
      expected: undefined,
    },
    {
      attributes: {
        name: {
          required: true,
        },
      },
      property: "unknown",
      constraint: "required",
      expected: undefined,
    },
  ])(
    "returns $expected for $property.$constraint",
    ({ attributes, property, constraint, expected }) => {
      expect(
        getOpenAPIValidationConstraint(attributes, property, constraint)
      ).toBe(expected);
    }
  );
});
