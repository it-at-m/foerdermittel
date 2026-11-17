<template>
  <base-view :title="title">
    <template #default="{ baseViewLoading }">
      <report-card
        :empty-form-template="EMPTY_FORM_TEMPLATE"
        :loading="baseViewLoading"
        :api="reportAuswertungAbrufeApi"
        :form-ref="reportAuswertungAbrufeFormRef"
      >
        <template #form="{ item, updateValidity }">
          <reportauswertungabrufeform
            ref="reportAuswertungAbrufeForm"
            :model-value="item"
            :reportauswertungabrufeformcontext="
              reportAuswertungAbrufeFormContext
            "
            @is-valid="updateValidity"
          />
        </template>
      </report-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { GetReportAuswertungAbrufeRequest } from "@/api/generated/foerdermittel-backend";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import ReportCard from "@/components/common/ReportCard.vue";
import reportauswertungabrufeform from "@/components/forms/report/ReportAuswertungAbrufeForm.vue";
import { useReportAuswertungAbrufeApi } from "@/composables/api/report/useReportAuswertungAbrufeApi";
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
  t("domain.report.auswertungAbrufe.modelName", 1),
]);

const EMPTY_FORM_TEMPLATE: Partial<GetReportAuswertungAbrufeRequest> = {
  parameters: {
    jahrAbrufVon: "",
    jahrAbrufBis: "",
    jahrErhaltVon: "",
    jahrErhaltBis: "",
    sbl: "",
    bez: "",
    fb: "",
    ua: "",
    kurz: "",
    pname: "",
    pstrasse: "",
    projnr: "",
    sgt: "",
    bpg: "",
    fag: "0",
    offen: "0",
    ohnebewill: "0",
    erh: "1",
    nichterh: "1",
    sort: "FOERDERBEREICH_PROJEKTNUMMER_ADATUM",
    type: "PDF",
  },
};

const reportAuswertungAbrufeApi = useReportAuswertungAbrufeApi();

const reportAuswertungAbrufeFormContext = computed(
  () => reportAuswertungAbrufeApi.context.data.value
);

type ReportAuswertungAbrufeFormRef = InstanceType<
  typeof reportauswertungabrufeform
>;

const reportAuswertungAbrufeFormRef =
  useTemplateRef<ReportAuswertungAbrufeFormRef>("reportAuswertungAbrufeForm");
</script>
