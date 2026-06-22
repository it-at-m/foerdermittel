import { createRulesPlugin } from "vuetify/labs/rules";

import vuetify from "@/plugins/vuetify";

export default createRulesPlugin(
  {
    aliases: {
      min: (minNumber, err) => {
        return v => v >= minNumber || err || `Der Wert muss mindestens ${minNumber} betragen.`
      },
      max: (maxNumber, err) => {
        return v => v <= maxNumber || err || `Der Wert darf höchstens ${maxNumber} betragen.`
      },
    },
  },
  vuetify.locale
);
