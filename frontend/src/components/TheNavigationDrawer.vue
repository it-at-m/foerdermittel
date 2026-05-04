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
      "components.theNavigationDrawer.navigationItems.projects.groupText"
    ),
    props: {
      prependIcon: mdiSitemap,
    },
    children: [
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.projects.projects"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.projects.requests"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.projects.approvals"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.projects.retrievals"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.projects.deadlines"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.projects.costs"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.projects.archive"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.projects.budget"
        ),
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
        title: t(
          "components.theNavigationDrawer.navigationItems.management.urbanPlanning"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.management.euInformation"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.management.plannedMeasures"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.management.keywords"
        ),
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
        title: t(
          "components.theNavigationDrawer.navigationItems.search.projects"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.search.requests"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.search.approvals"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.search.retrievals"
        ),
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
        title: t(
          "components.theNavigationDrawer.navigationItems.search.budget"
        ),
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
        title: t(
          "components.theNavigationDrawer.navigationItems.statistics.projects"
        ),
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
        title: t(
          "components.theNavigationDrawer.navigationItems.reports.projects"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.reports.requests"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.reports.approvals"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.reports.retrievals"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.reports.useStatements"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.reports.deadlines"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.reports.costs"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.reports.archive"
        ),
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
        title: t(
          "components.theNavigationDrawer.navigationItems.reports.budget"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.reports.urbanPlanning"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.reports.euInformation"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.reports.plannedMeasures"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.reports.keywords"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.reports.instructions"
        ),
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
        title: t(
          "components.theNavigationDrawer.navigationItems.masterData.constructionManagement"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.masterData.constructionPrograms"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.masterData.fundingAreas"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.masterData.hospitals"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.masterData.abbreviations"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.masterData.publications"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.masterData.departments"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.masterData.settlementArea"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.masterData.districts"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.masterData.districtLists"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.masterData.keywordAreas"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.masterData.sponsors"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.masterData.sections"
        ),
      },
      {
        title: t(
          "components.theNavigationDrawer.navigationItems.masterData.subSections"
        ),
      },
    ],
  },
];
</script>
