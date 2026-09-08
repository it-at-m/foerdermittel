import { describe, expect, it } from "vitest";

import { deepEqualTrimmed } from "../../../src/util/validation";

describe("deepEqualTrimmed", () => {
  describe("strings", () => {
    it("returns true for equal strings", () => {
      expect(deepEqualTrimmed("hello", "hello")).toBe(true);
    });

    it("returns true for strings that are equal after trimming", () => {
      expect(deepEqualTrimmed("  hello  ", "hello")).toBe(true);
      expect(deepEqualTrimmed("hello ", " hello")).toBe(true);
    });

    it("returns false for different strings after trimming", () => {
      expect(deepEqualTrimmed("hello", "world")).toBe(false);
    });

    it("returns true for whitespace-only strings", () => {
      expect(deepEqualTrimmed("   ", "")).toBe(true);
    });
  });

  describe("null and undefined", () => {
    it("returns true for null and null", () => {
      expect(deepEqualTrimmed(null, null)).toBe(true);
    });

    it("returns true for undefined and undefined", () => {
      expect(deepEqualTrimmed(undefined, undefined)).toBe(true);
    });

    it("returns true for null and undefined", () => {
      expect(deepEqualTrimmed(null, undefined)).toBe(true);
    });

    it("returns true for undefined and null", () => {
      expect(deepEqualTrimmed(undefined, null)).toBe(true);
    });

    it("returns false when only one value is nullish", () => {
      expect(deepEqualTrimmed(null, "")).toBe(false);
      expect(deepEqualTrimmed(undefined, "")).toBe(false);
      expect(deepEqualTrimmed(null, 0)).toBe(false);
      expect(deepEqualTrimmed(undefined, false)).toBe(false);
    });
  });

  describe("primitive values", () => {
    it("returns true for equal numbers", () => {
      expect(deepEqualTrimmed(42, 42)).toBe(true);
    });

    it("returns false for different numbers", () => {
      expect(deepEqualTrimmed(42, 43)).toBe(false);
    });

    it("returns true for equal booleans", () => {
      expect(deepEqualTrimmed(true, true)).toBe(true);
      expect(deepEqualTrimmed(false, false)).toBe(true);
    });

    it("returns false for different booleans", () => {
      expect(deepEqualTrimmed(true, false)).toBe(false);
    });

    it("returns false for different primitive types", () => {
      expect(deepEqualTrimmed("1", 1)).toBe(false);
      expect(deepEqualTrimmed("true", true)).toBe(false);
      expect(deepEqualTrimmed(0, false)).toBe(false);
    });

    it("returns true for the same symbol reference", () => {
      const symbol = Symbol("test");

      expect(deepEqualTrimmed(symbol, symbol)).toBe(true);
    });

    it("returns false for different symbols", () => {
      expect(deepEqualTrimmed(Symbol("test"), Symbol("test"))).toBe(false);
    });
  });

  describe("dates", () => {
    it("returns true for dates representing the same time", () => {
      expect(
        deepEqualTrimmed(
          new Date("2026-08-31T12:00:00Z"),
          new Date("2026-08-31T12:00:00Z")
        )
      ).toBe(true);
    });

    it("returns false for dates representing different times", () => {
      expect(
        deepEqualTrimmed(
          new Date("2026-08-31T12:00:00Z"),
          new Date("2026-08-31T13:00:00Z")
        )
      ).toBe(false);
    });

    it("returns false when comparing a date with another type", () => {
      expect(
        deepEqualTrimmed(
          new Date("2026-08-31T12:00:00Z"),
          "2026-08-31T12:00:00Z"
        )
      ).toBe(false);
    });
  });

  describe("arrays", () => {
    it("returns true for equal arrays", () => {
      expect(deepEqualTrimmed([1, 2, 3], [1, 2, 3])).toBe(true);
    });

    it("returns true for the same array reference", () => {
      const array = [1, 2, 3];

      expect(deepEqualTrimmed(array, array)).toBe(true);
    });

    it("returns false for arrays with different lengths", () => {
      expect(deepEqualTrimmed([1, 2], [1, 2, 3])).toBe(false);
    });

    it("returns false for arrays with different values", () => {
      expect(deepEqualTrimmed([1, 2, 3], [1, 2, 4])).toBe(false);
    });

    it("compares array strings using trimmed values", () => {
      expect(deepEqualTrimmed([" hello ", " world"], ["hello", "world "])).toBe(
        true
      );
    });

    it("treats null and undefined array items as equal", () => {
      expect(deepEqualTrimmed([1, null, 3], [1, undefined, 3])).toBe(true);
    });

    it("supports nested arrays", () => {
      expect(
        deepEqualTrimmed([[" foo "], [null]], [["foo"], [undefined]])
      ).toBe(true);
    });

    it("returns false when comparing an array with a plain object", () => {
      expect(deepEqualTrimmed([1, 2], { 0: 1, 1: 2 })).toBe(false);
    });
  });

  describe("objects", () => {
    it("returns true for empty objects", () => {
      expect(deepEqualTrimmed({}, {})).toBe(true);
    });

    it("returns true for the same object reference", () => {
      const object = { foo: "bar" };

      expect(deepEqualTrimmed(object, object)).toBe(true);
    });

    it("returns true for objects with equal properties", () => {
      expect(
        deepEqualTrimmed({ foo: "bar", count: 1 }, { foo: "bar", count: 1 })
      ).toBe(true);
    });

    it("does not depend on property order", () => {
      expect(
        deepEqualTrimmed({ foo: "bar", count: 1 }, { count: 1, foo: "bar" })
      ).toBe(true);
    });

    it("compares object strings using trimmed values", () => {
      expect(
        deepEqualTrimmed(
          { firstName: " Alice ", lastName: " Smith " },
          { firstName: "Alice", lastName: "Smith" }
        )
      ).toBe(true);
    });

    it("treats null and undefined property values as equal", () => {
      expect(deepEqualTrimmed({ foo: null }, { foo: undefined })).toBe(true);
    });

    it("treats null and undefined as equal in nested objects", () => {
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

    it("returns false for different property values", () => {
      expect(deepEqualTrimmed({ foo: "bar" }, { foo: "baz" })).toBe(false);
    });

    it("returns false when objects have different numbers of properties", () => {
      expect(deepEqualTrimmed({ foo: "bar" }, { foo: "bar", baz: 1 })).toBe(
        false
      );
    });

    it("returns false when objects have different property names", () => {
      expect(deepEqualTrimmed({ foo: "value" }, { bar: "value" })).toBe(false);
    });

    it("considers an explicitly undefined property different from a missing property", () => {
      expect(deepEqualTrimmed({ foo: undefined }, {})).toBe(false);
    });

    it("considers an explicitly null property different from a missing property", () => {
      expect(deepEqualTrimmed({ foo: null }, {})).toBe(false);
    });

    it("supports arrays nested inside objects", () => {
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

    it("supports dates nested inside objects", () => {
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
    it("returns false for an object and a primitive", () => {
      expect(deepEqualTrimmed({}, 1)).toBe(false);
      expect(deepEqualTrimmed({}, "test")).toBe(false);
    });

    it("returns false for an array and a primitive", () => {
      expect(deepEqualTrimmed([], 1)).toBe(false);
      expect(deepEqualTrimmed([], "test")).toBe(false);
    });
  });

  describe("complex nested structures", () => {
    it("deeply compares trimmed strings and nullish values", () => {
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

    it("returns false when a deeply nested value differs", () => {
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
