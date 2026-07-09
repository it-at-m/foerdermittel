import "vuetify/styles";

import type { VueI18nAdapterParams } from "vuetify/locale/adapters/vue-i18n";

import { useI18n } from "vue-i18n";
import { createVuetify } from "vuetify";
import { aliases, mdi } from "vuetify/iconsets/mdi-svg";
import { createVueI18nAdapter } from "vuetify/locale/adapters/vue-i18n";

import { ITEMS_PER_PAGE_OPTIONS } from "@/composables/usePagination";
import i18n from "@/plugins/i18n";

export default createVuetify({
  icons: {
    defaultSet: "mdi",
    aliases,
    sets: {
      mdi,
    },
  },
  defaults: {
    VBtn: {
      color: "secondary",
      variant: "text",
    },
    VTextarea: {
      variant: "outlined",
      persistentPlaceholder: true,
      noResize: true,
    },
    VTextField: {
      variant: "outlined",
      persistentPlaceholder: true,
    },
    VNumberInput: {
      variant: "outlined",
      persistentPlaceholder: true,
      controlVariant: "stacked",
    },
    VCard: {
      class: "elevation-0",
    },
    VCardActions: {
      class: "mb-4 mr-4 pt-0",
    },
    VDialog: {
      maxWidth: "800px",
      width: "90%",
      persistent: true,
    },
    VNavigationDrawer: {
      permanent: true,
      width: 400,
      color: "secondary",
    },
    VTooltip: {
      location: "bottom",
    },
    VSnackbar: {
      timeout: 5000,
      color: "info",
    },
    VDataTableServer: {
      itemsPerPageOptions: ITEMS_PER_PAGE_OPTIONS.map((option) => ({
        value: option,
        title: String(option),
      })),
    },
  },
  theme: {
    defaultTheme: "light",
    themes: {
      light: {
        colors: {
          primary: "#FFCC00",
          secondary: "#333333",
          accent: "#7BA4D9",
          success: "#69BE28",
          error: "#FF0000",
        },
      },
    },
  },
  locale: {
    // @ts-expect-error false positive for type mismatch (no tsc compilation error)
    adapter: createVueI18nAdapter({ i18n, useI18n } as VueI18nAdapterParams),
  },
});
