import { createRulesPlugin } from "vuetify/labs/rules";

import i18n from "@/plugins/i18n";
import vuetify from "@/plugins/vuetify";

// eslint-disable-next-line @typescript-eslint/no-explicit-any
type CustomRule = (...args: any[]) => (value: any) => string | boolean;

const minRule: CustomRule = (minNumber: number, exclusive = false, err) => {
  return (v) =>
    exclusive
      ? v > minNumber || err || `Der Wert muss größer als ${minNumber} sein.`
      : v >= minNumber ||
        err ||
        `Der Wert muss mindestens ${minNumber} betragen.`;
};

const maxRule: CustomRule = (maxNumber: number, exclusive = false, err) => {
  return (v) =>
    exclusive
      ? v < maxNumber || err || `Der Wert muss kleiner als ${maxNumber} sein.`
      : v <= maxNumber ||
        err ||
        `Der Wert darf höchstens ${maxNumber} betragen.`;
};

const uniqueRule: CustomRule = (values, currentValue, err) => {
  return (v) =>
    v === currentValue ||
    !values.includes(v) ||
    err ||
    `Der Wert ${v} ist bereits vorhanden.`;
};

const requiredTrimmedRule: CustomRule = () => {
  return (v) => {
    const value = typeof v === "string" ? v.trim() : v;

    // $vuetify is defined in Vuetify and does not have to be defined in this project
    // eslint-disable-next-line @intlify/vue-i18n/no-missing-keys
    return value === 0 || !!value || i18n.global.t("$vuetify.rules.required");
  };
};

export default createRulesPlugin(
  {
    aliases: {
      min: minRule,
      max: maxRule,
      unique: uniqueRule,
      requiredTrimmed: requiredTrimmedRule,
    },
  },
  vuetify.locale
);

export { minRule, maxRule, uniqueRule, requiredTrimmedRule };
