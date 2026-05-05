<template>
  <v-navigation-drawer
    permanent
    width="400"
    color="primary"
  >
    <v-container>
      <div class="text-center mb-5">
        <p class="text-h5 font-weight-bold">{{ t("common.appName") }}</p>
      </div>
      <div
        v-if="userInfoStore.getUserInfo?.preferred_username"
        class="text-center"
      >
        <v-btn
          class="mx-2"
          variant="text"
          icon
        >
          <ad2-image-avatar
            :username="userInfoStore.getUserInfo.preferred_username"
          />
        </v-btn>
        <div>
          <p>{{ t("components.theNavigationDrawer.loggedIn") }}</p>
          <p>{{ userInfoStore.getUserInfo?.name }}</p>
        </div>
      </div>
    </v-container>

    <v-divider />

    <v-container>
      <v-list
        :items="navigationItems"
        open-strategy="single"
        nav
        density="compact"
      />
    </v-container>
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
  mdiSitemap,
} from "@mdi/js";
import { useI18n } from "vue-i18n";

import Ad2ImageAvatar from "@/components/common/Ad2ImageAvatar.vue";
import { useUserInfoStore } from "@/stores/userinfo";

const userInfoStore = useUserInfoStore();
const { t } = useI18n();

const navigationItems: NavigationItem[] = [
  {
    title: t(
      "components.theNavigationDrawer.navigationItems.projects.groupText",
      2
    ),
    props: {
      prependIcon: mdiSitemap,
    },
    children: [
      {
        title: t("domain.projekt.domainName", 2),
      },
      {
        title: t("domain.antrag.domainName", 2),
      },
      {
        title: t("domain.bewilligung.domainName", 2),
      },
      {
        title: t("domain.abruf.domainName", 2),
      },
      {
        title: t("domain.termin.domainName", 2),
      },
      {
        title: t("domain.istkosten.domainName"),
      },
      {
        title: t("domain.archiv.domainName"),
      },
      {
        title: t("domain.haushaltsplanung.domainName", 2),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.projects.checklists"
        ),
      },
    ],
  },
  {
    title: t(
      "components.theNavigationDrawer.navigationItems.management.groupText"
    ),
    props: {
      prependIcon: mdiNote,
    },
    children: [
      {
        title: t("domain.staedtebaufoerderung.domainName", 2),
      },
      {
        title: t("domain.euinformation.domainName", 2),
      },
      {
        title: t("domain.geplantemassnahme.domainName", 2),
      },
      {
        title: t("domain.ablageindex.domainName", 2),
      },
    ],
  },
  {
    title: t("components.theNavigationDrawer.navigationItems.search.groupText"),
    props: {
      prependIcon: mdiMagnify,
    },
    children: [
      {
        title: t("domain.projekt.domainName", 2),
      },
      {
        title: t("domain.antrag.domainName", 2),
      },
      {
        title: t("domain.bewilligung.domainName", 2),
      },
      {
        title: t("domain.abruf.domainName", 2),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.search.useStatements"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.search.childcareFacilities"
        ),
      },
      {
        title: t("domain.haushaltsplanung.domainName", 2),
      },
    ],
  },
  {
    title: t(
      "components.theNavigationDrawer.navigationItems.statistics.groupText"
    ),
    props: {
      prependIcon: mdiChartBar,
    },
    children: [
      {
        title: t("domain.projekt.domainName", 2),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.statistics.fagYearly"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.statistics.fbYearly"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.statistics.generateYearly"
        ),
      },
    ],
  },
  {
    title: t(
      "components.theNavigationDrawer.navigationItems.reports.groupText"
    ),
    props: {
      prependIcon: mdiFileChart,
    },
    children: [
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.reports.projectOverview"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.reports.projectOverviewManagement"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.reports.acceptanceOrderSAP"
        ),
      },
      {
        title: t("domain.projekt.domainName", 2),
      },
      {
        title: t("domain.antrag.domainName", 2),
      },
      {
        title: t("domain.bewilligung.domainName", 2),
      },
      {
        title: t("domain.abruf.domainName", 2),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.reports.useStatements"
        ),
      },
      {
        title: t("domain.termin.domainName", 2),
      },
      {
        title: t("domain.istkosten.domainName"),
      },
      {
        title: t("domain.archiv.domainName"),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.reports.clearanceRequests"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.reports.openApprovalsPerProject"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.reports.openApprovalsPerFundingArea"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.reports.openApprovalsEarlyStart"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.reports.approvalAndPayoutProject"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.reports.approvalAndPayoutCompare"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.reports.continuationRequests"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.reports.childcareFacilities"
        ),
      },
      {
        title: t("domain.haushaltsplanung.domainName", 2),
      },
      {
        title: t("domain.staedtebaufoerderung.domainName", 2),
      },
      {
        title: t("domain.euinformation.domainName", 2),
      },
      {
        title: t("domain.geplantemassnahme.domainName", 2),
      },
      {
        title: t("domain.ablageindex.domainName", 2),
      },
      {
        title: t("domain.benutzerhinweis.domainName", 2),
      },
    ],
  },
  {
    title: t(
      "components.theNavigationDrawer.navigationItems.masterData.groupText"
    ),
    props: {
      prependIcon: mdiDatabase,
    },
    children: [
      {
        title: t("domain.bauleitung.domainName", 2),
      },
      {
        title: t("domain.bauprogramm.domainName", 2),
      },
      {
        title: t("domain.foerderbereich.domainName", 2),
      },
      {
        title: t("domain.krankenhaus.domainName", 2),
      },
      {
        title: t("domain.kurzbezeichnung.domainName", 2),
      },
      {
        title: t("domain.publikation.domainName", 2),
      },
      {
        title: t("domain.referat.domainName", 2),
      },
      {
        title: t("domain.siedlungsgebiet.domainName", 2),
      },
      {
        title: t("domain.stadtbezirk.domainName", 2),
      },
      {
        title: t("domain.stadtbezirksliste.domainName", 2),
      },
      {
        title: t("domain.stichwortbereich.domainName", 2),
      },
      {
        title: t("domain.traeger.domainName"),
      },
      {
        title: t("domain.unterabschnitt.domainName", 2),
      },
    ],
  },
];
</script>
