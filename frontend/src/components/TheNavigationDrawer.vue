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
          <p>{{ t("component.theNavigationDrawer.loggedIn") }}</p>
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
      },
      {
        title: t("model.bauprogramm.modelName", 2),
      },
      {
        title: t("model.foerderbereich.modelName", 2),
      },
      {
        title: t("model.krankenhaus.modelName", 2),
      },
      {
        title: t("model.kurzbezeichnung.modelName", 2),
      },
      {
        title: t("model.publikation.modelName", 2),
      },
      {
        title: t("model.referat.modelName", 2),
      },
      {
        title: t("model.siedlungsgebiet.modelName", 2),
      },
      {
        title: t("model.stadtbezirk.modelName", 2),
      },
      {
        title: t("model.listenname.modelName", 2),
      },
      {
        title: t("model.stadtbezirksliste.modelName", 2),
      },
      {
        title: t("model.stichwortbereich.modelName", 2),
      },
      {
        title: t("model.traeger.modelName"),
      },
      {
        title: t("model.hauptabschnitt.modelName", 2),
      },
      {
        title: t("model.unterabschnitt.modelName", 2),
      },
    ],
  },
];
</script>
