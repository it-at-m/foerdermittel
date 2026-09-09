<template>
  <v-navigation-drawer
    v-model:rail="isRail"
    color="grey-darken-4"
    :expand-on-hover="expandOnHover && !autocompleteMenuOpen"
    class="d-flex flex-column"
  >
    <div class="d-flex align-center px-2 pt-2">
      <span class="text-h6 ml-2 font-weight-bold">
        {{ isRail ? t("common.appAbbrev") : t("common.appName") }}
      </span>

      <v-spacer />

      <div
        class="d-flex align-center"
        :style="{ visibility: isRail ? 'hidden' : 'visible' }"
      >
        <theme-toggle-btn />
        <v-icon-btn
          v-tooltip:start="
            expandOnHover
              ? t('component.theNavigationDrawer.pin')
              : t('component.theNavigationDrawer.unpin')
          "
          variant="text"
          color="accent"
          :icon="expandOnHover ? mdiPinOutline : mdiPin"
          @click="expandOnHover = !expandOnHover"
        />
      </div>
    </div>

    <v-list class="pt-0">
      <v-list-item
        :prepend-avatar="avatarUrl"
        :subtitle="rolesText"
        :title="userInfoStore.userInfo?.name"
      />
    </v-list>

    <v-divider />

    <v-autocomplete
      v-if="!isRail"
      v-model="selectedTo"
      theme="dark"
      class="px-2 pt-2"
      :items="searchableNavigationItems"
      :label="t('component.theNavigationDrawer.search')"
      variant="outlined"
      density="compact"
      hide-details
      hide-no-data
      @update:menu="autocompleteMenuOpen = $event"
      @update:model-value="navigateToNavigationItem"
    />

    <v-list
      v-if="hasRole && !autocompleteMenuOpen"
      :items="navigationItems"
      open-strategy="single"
      nav
      density="compact"
      :color="isRail ? 'transparent' : 'accent'"
    />
  </v-navigation-drawer>
</template>

<script setup lang="ts">
import type { NavigationItem } from "@/types/NavigationItem";

import {
  mdiChartBar,
  mdiDatabase,
  mdiFileChart,
  mdiMagnify,
  mdiNote,
  mdiPin,
  mdiPinOutline,
  mdiSitemap,
} from "@mdi/js";
import { computed, ref } from "vue";
import { useI18n } from "vue-i18n";
import { useRouter } from "vue-router";
import { VAutocomplete } from "vuetify/components";

import { getAvatarHref } from "@/api/ad2imageavatar-client";
import ThemeToggleBtn from "@/components/common/ThemeToggleBtn.vue";
import useHasAnyRole from "@/composables/useHasAnyRole";
import { useUserInfoStore } from "@/stores/userinfo";
import { Role } from "@/types/Role";

const userInfoStore = useUserInfoStore();
const { t } = useI18n();

const hasRole = useHasAnyRole([
  Role.SACHBEARBEITUNG,
  Role.SACHBEARBEITUNG_HAUSHALT,
  Role.ADMIN,
]);

const rolesText = computed(() =>
  userInfoStore.currentRoles.length
    ? userInfoStore.currentRoles
        .map((role) => t(`common.roles.${role}`))
        .join(", ")
    : t("common.roles.noRole")
);

const isRail = ref(true);
const expandOnHover = ref(true);

const avatarUrl = computed(() => {
  return getAvatarHref(userInfoStore.userInfo?.preferred_username ?? "");
});

const navigationItems: NavigationItem[] = [
  {
    title: t("model.projekt.modelName", 2),
    props: {
      prependIcon: mdiSitemap,
    },
    children: [
      {
        title: t("model.projekt.modelName", 2),
      },
      {
        title: t("model.antrag.modelName", 2),
      },
      {
        title: t("model.bewilligung.modelName", 2),
      },
      {
        title: t("model.abruf.modelName", 2),
      },
      {
        title: t("model.termin.modelName", 2),
      },
      {
        title: t("model.istkosten.modelName"),
      },
      {
        title: t("model.archiv.modelName"),
      },
      {
        title: t("model.haushaltsjahr.modelName", 2),
      },
      {
        title: t("model.haushaltsplanung.modelName", 2),
      },
    ],
  },
  {
    title: t("common.word.management"),
    props: {
      prependIcon: mdiNote,
    },
    children: [
      {
        title: t("model.staedtebaufoerderung.modelName", 2),
      },
      {
        title: t("model.euinformation.modelName", 2),
      },
      {
        title: t("model.geplantemassnahme.modelName", 2),
      },
      {
        title: t("model.ablageindex.modelName", 2),
      },
    ],
  },
  {
    title: t("common.action.search"),
    props: {
      prependIcon: mdiMagnify,
    },
    children: [
      {
        title: t("model.projekt.modelName", 2),
      },
      {
        title: t("model.antrag.modelName", 2),
      },
      {
        title: t("model.bewilligung.modelName", 2),
      },
      {
        title: t("model.abruf.modelName", 2),
      },
      {
        title: t("domain.verwendungsNachweis", 2),
      },
      {
        title: t("domain.kinderbetreuungsEinrichtung", 2),
      },
      {
        title: t("model.haushaltsplanung.modelName", 2),
      },
    ],
  },
  {
    title: t("common.word.statistic", 2),
    props: {
      prependIcon: mdiChartBar,
    },
    children: [
      {
        title: t("model.projekt.modelName", 2),
      },
      {
        title: t("domain.statistic.fagJahresStatistik"),
      },
      {
        title: t("domain.statistic.fbJahresStatistik"),
      },
      {
        title: t("domain.statistic.generateJahresStatistik"),
      },
    ],
  },
  {
    title: t("common.word.report", 2),
    props: {
      prependIcon: mdiFileChart,
    },
    children: [
      {
        title: t("domain.report.projektUebersicht"),
      },
      {
        title: t("domain.report.projektUebersichtManagement"),
      },
      {
        title: t("domain.report.annahmeAnordnungSAP"),
      },
      {
        title: t("model.projekt.modelName", 2),
      },
      {
        title: t("domain.report.checklisten"),
      },
      {
        title: t("model.antrag.modelName", 2),
      },
      {
        title: t("model.bewilligung.modelName", 2),
      },
      {
        title: t("model.abruf.modelName", 2),
      },
      {
        title: t("domain.verwendungsNachweis", 2),
      },
      {
        title: t("model.termin.modelName", 2),
      },
      {
        title: t("model.istkosten.modelName"),
      },
      {
        title: t("model.archiv.modelName"),
      },
      {
        title: t("domain.report.unbedenklichkeitsAntrag", 2),
      },
      {
        title: t("domain.report.ausstBewilligungProjekt"),
      },
      {
        title: t("domain.report.ausstBewilligungFoerderbereich"),
      },
      {
        title: t("domain.report.ausstGenehmigungVorzBeginn"),
      },
      {
        title: t("domain.report.bewilligungAuszahlungProjekt"),
      },
      {
        title: t("domain.report.bewilligungAuzahlungVergleich"),
      },
      {
        title: t("domain.report.fortsetzungsAntrag", 2),
      },
      {
        title: t("domain.kinderbetreuungsEinrichtung", 2),
      },
      {
        title: t("model.haushaltsplanung.modelName", 2),
      },
      {
        title: t("model.staedtebaufoerderung.modelName", 2),
      },
      {
        title: t("model.euinformation.modelName", 2),
      },
      {
        title: t("model.geplantemassnahme.modelName", 2),
      },
      {
        title: t("model.ablageindex.modelName", 2),
      },
      {
        title: t("model.benutzerhinweis.modelName", 2),
      },
    ],
  },
  {
    title: t("common.word.masterdata"),
    props: {
      prependIcon: mdiDatabase,
    },
    children: [
      {
        title: t("model.bauleitung.modelName", 2),
        props: {
          to: "/bauleitungen",
        },
      },
      {
        title: t("model.bauprogramm.modelName", 2),
        props: {
          to: "/bauprogramme",
        },
      },
      {
        title: t("model.foerderbereich.modelName", 2),
        props: {
          to: "/foerderbereiche",
        },
      },
      {
        title: t("model.krankenhaus.modelName", 2),
        props: {
          to: "/krankenhaeuser",
        },
      },
      {
        title: t("model.kurzbezeichnung.modelName", 2),
        props: {
          to: "/kurzbezeichnungen",
        },
      },
      {
        title: t("model.publikation.modelName", 2),
        props: {
          to: "/publikationen",
        },
      },
      {
        title: t("model.referat.modelName", 2),
        props: {
          to: "/referate",
        },
      },
      {
        title: t("model.siedlungsgebiet.modelName", 2),
        props: {
          to: "/siedlungsgebiete",
        },
      },
      {
        title: t("model.stadtbezirk.modelName", 2),
        props: {
          to: "/stadtbezirke",
        },
      },
      {
        title: t("model.stadtbezirksliste.modelName", 2),
        props: {
          to: "/stadtbezirkslisten",
        },
      },
      {
        title: t("model.stichwortbereich.modelName", 2),
        props: {
          to: "/stichwortbereiche",
        },
      },
      {
        title: t("model.traeger.modelName"),
        props: {
          to: "/traeger",
        },
      },
      {
        title: t("model.hauptabschnitt.modelName", 2),
        props: {
          to: "/hauptabschnitte",
        },
      },
      {
        title: t("model.unterabschnitt.modelName", 2),
        props: {
          to: "/unterabschnitte",
        },
      },
    ],
  },
];

const selectedTo = ref<string | null>(null);

const searchableNavigationItems = computed(() =>
  navigationItems.flatMap((parent) => {
    const children = (parent.children ?? [])
      .filter(
        (child) =>
          child.props?.to && child.props.to !== router.currentRoute.value.path
      )
      .map((child) => ({
        title: child.title,
        value: child.props?.to,
      }));

    if (!children.length) {
      return [];
    }

    return [
      {
        type: "subheader" as const,
        title: parent.title,
      },
      ...children,
    ];
  })
);

const autocompleteMenuOpen = ref(false);

const router = useRouter();
function navigateToNavigationItem(route: string | null) {
  selectedTo.value = null;
  if (route) {
    router.push(route);
  }
}
</script>
