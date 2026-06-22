import { assert, describe, test } from "vitest";

import { maxRule, minRule } from "../../src/plugins/rules";

describe("min rule tests", () => {
  test("Throws error when too low", async () => {
    // given
    const num = 5;
    const validationRule = minRule(10);

    // when
    const result = validationRule(num);

    // then
    assert.isString(result);
  });

  test("Throws no error when high enough", async () => {
    // given
    const num = 10;
    const validationRule = minRule(10);

    // when
    const result = validationRule(num);

    // then
    assert.isTrue(result);
  });
});

describe("max rule tests", () => {
  test("Throws error when too high", async () => {
    // given
    const num = 15;
    const validationRule = maxRule(10);

    // when
    const result = validationRule(num);

    // then
    assert.isString(result);
  });

  test("Throws no error when low enough", async () => {
    // given
    const num = 10;
    const validationRule = maxRule(10);

    // when
    const result = validationRule(num);

    // then
    assert.isTrue(result);
  });
});
