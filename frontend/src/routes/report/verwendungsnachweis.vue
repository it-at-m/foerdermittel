<template>
  <base-view :title="title">
    <template #default="{ baseViewLoading }">
      <report-card
        :empty-form-template="EMPTY_FORM_TEMPLATE"
        :loading="baseViewLoading"
        :api="reportVerwendungsnachweisApi"
        :formref="reportVerwendungsnachweisFormContext"
      >
        <template #form="{ item, updateValidity }">
          <report-verwendungsnachweis-form
            ref="reportVerwendungsnachweisForm"
            :model-value="item"
            :report-verwendungsnachweis-form-context="
              reportVerwendungsnachweisFormContext
            "
            @is-valid="updateValidity"
          />
        </template>
      </report-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { GetReportVerwendungsnachweisRequest } from "@/api/generated/foerdermittel-backend";

import { computed } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import ReportCard from "@/components/common/ReportCard.vue";
import ReportVerwendungsnachweisForm from "@/components/forms/report/ReportVerwendungsnachweisForm.vue";
import { useReportVerwendungsnachweisApi } from "@/composables/api/report/useReportVerwendungsnachweisApi";
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
  t("domain.report.verwendungsnachweis.modelName", 1),
]);

const EMPTY_FORM_TEMPLATE: Partial<GetReportVerwendungsnachweisRequest> = {
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
    sort: "PROJEKTNUMMER",
    type: "PDF",
  },
};

const reportVerwendungsnachweisApi = useReportVerwendungsnachweisApi();

const reportVerwendungsnachweisFormContext = computed(
  () => reportVerwendungsnachweisApi.context.data.value
);
</script>
