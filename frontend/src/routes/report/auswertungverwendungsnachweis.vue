<template>
  <base-view :title="title">
    <template #default="{ baseViewLoading }">
      <report-card
        :empty-form-template="EMPTY_FORM_TEMPLATE"
        :loading="baseViewLoading"
        :api="reportAuswertungVerwendungsnachweisApi"
        :formref="reportAuswertungVerwendungsnachweisFormContext"
      >
        <template #form="{ item, updateValidity }">
          <report-auswertung-verwendungsnachweis-form
            ref="reportVerwendungsnachweisForm"
            :model-value="item"
            :report-auswertung-verwendungsnachweis-form-context="
              reportAuswertungVerwendungsnachweisFormContext
            "
            @is-valid="updateValidity"
          />
        </template>
      </report-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { GetReportAuswertungVerwendungsnachweisRequest } from "@/api/generated/foerdermittel-backend";

import { computed } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import ReportCard from "@/components/common/ReportCard.vue";
import ReportAuswertungVerwendungsnachweisForm from "@/components/forms/report/ReportAuswertungVerwendungsnachweisForm.vue";
import { useReportAuswertungVerwendungsnachweisApi } from "@/composables/api/report/useReportAuswertungVerwendungsnachweisApi.ts";
import { Role } from "@/types/Role";

definePage({
  meta: {
    hasAnyRole: [
      Role.SACHBEARBEITUNG,
      Role.SACHBEARBEITUNG_HAUSHALT,
      Role.ADMIN,
    ],
  },
});

const { t } = useI18n();

const title = t("common.generics.reportTitle", [
  t("domain.report.auswertungVerwendungsnachweis.modelName", 1),
]);

const EMPTY_FORM_TEMPLATE: Partial<GetReportAuswertungVerwendungsnachweisRequest> =
  {
    parameters: {
      jahr: "",
      monat: "",
      sbl: "",
      bez: "",
      fb: "",
      ua: "",
      kurz: "",
      pname: "",
      pstrasse: "",
      sgt: "",
      bpg: "",
      krh: "",
      fag: "0",
      notizen: "0",
      win: "0",
      ohneschluss: "0",
      sort: "FOERDERBEREICH_PROJEKTNUMMER",
      type: "PDF",
    },
  };

const reportAuswertungVerwendungsnachweisApi =
  useReportAuswertungVerwendungsnachweisApi();

const reportAuswertungVerwendungsnachweisFormContext = computed(
  () => reportAuswertungVerwendungsnachweisApi.context.data.value
);
</script>
