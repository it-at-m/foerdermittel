import { createRulesPlugin } from "vuetify/labs/rules";

import vuetify from "@/plugins/vuetify";

// eslint-disable-next-line @typescript-eslint/no-explicit-any
type CustomRule = (...args: any[]) => (value: any) => string | boolean;

const minRule: CustomRule = (minNumber, err) => {
  return (v) =>
    v >= minNumber || err || `Der Wert muss mindestens ${minNumber} betragen.`;
};

const maxRule: CustomRule = (maxNumber, err) => {
  return (v) =>
    v <= maxNumber || err || `Der Wert darf höchstens ${maxNumber} betragen.`;
};

export default createRulesPlugin(
  {
    aliases: {
      min: minRule,
      max: maxRule,
    },
  },
  vuetify.locale
);

export { minRule, maxRule };
