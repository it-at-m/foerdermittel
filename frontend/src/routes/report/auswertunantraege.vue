<template>
  <base-view :title="title">
    <template #default="{ baseViewLoading }">
      <report-card
        :empty-form-template="EMPTY_FORM_TEMPLATE"
        :loading="baseViewLoading"
        :api="reportAuswertungAntraegeApi"
        :form-ref="reportAuswertungAntraegerFormRef"
      >
        <template #form="{ item, updateValidity }">
          <report-auswertung-Antraege-form
            ref="reportAuswertungAntraegeForm"
            :model-value="item"
            :report-auswertung-Antrag-form-context="
              reportAuswertungAntraegeFormContext
            "
            @is-valid="updateValidity"
          />
        </template>
      </report-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { GetReportAuswertungAntragRequest } from "@/api/generated/foerdermittel-backend";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import ReportCard from "@/components/common/ReportCard.vue";
import ReportAuswertungAntraegeForm from "@/components/forms/report/ReportAuswertungAntraegeForm.vue";
import { useReportAuswertungAntraegeApi } from "@/composables/api/report/useReportAuswertungAntraegeApi";
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
  t("domain.report.auswertungAntrag.modelName", 1),
]);

const EMPTY_FORM_TEMPLATE: Partial<GetReportAuswertungAntragRequest> = {
  parameters: {
    jahrvon: "",

    sbl: "",
    bez: "",
    fb: "",
    ua: "",
    kurz: "",
    pname: "",
    pstrasse: "",
    sgt: "",
    bpg: "",
    kauf: "0",
    offen: "0",
    relevant: "0",
    sort: "PROJEKTNUMMER",
    type: "PDF",
  },
};

const reportAuswertungAntraegeApi = useReportAuswertungAntraegeApi();

const reportAuswertungAntraegeFormContext = computed(
  () => reportAuswertungAntraegeApi.context.data.value
);

type ReportAuswertungAntraegeFormRef = InstanceType<
  typeof ReportAuswertungAntraegeForm
>;

const reportAuswertungAntraegeFormRef =
  useTemplateRef<ReportAuswertungAntraegeFormRef>(
    "reportAuswertungAntraegeForm"
  );
</script>
