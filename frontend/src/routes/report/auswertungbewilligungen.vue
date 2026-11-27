<template>
  <base-view :title="title">
    <template #default="{ baseViewLoading }">
      <report-card
        :empty-form-template="EMPTY_FORM_TEMPLATE"
        :loading="baseViewLoading"
        :api="reportAuswertungBewilligungenApi"
        :form-ref="reportAuswertungBewilligungenFormRef"
      >
        <template #form="{ item, updateValidity }">
          <reportauswertungbewilligungenform
            ref="reportAuswertungBewilligungenForm"
            :model-value="item"
            :reportauswertungbewilligungenformcontext="
              reportAuswertungBewilligungenFormContext
            "
            @is-valid="updateValidity"
          />
        </template>
      </report-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { GetReportAuswertungBewilligungenRequest } from "@/api/generated/foerdermittel-backend";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import ReportCard from "@/components/common/ReportCard.vue";
import reportauswertungbewilligungenform from "@/components/forms/report/ReportAuswertungBewilligungenForm.vue";
import { useReportAuswertungBewilligungenApi } from "@/composables/api/report/useReportAuswertungBewilligungenApi";
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
  t("domain.report.auswertungBewilligungen.modelName", 1),
]);

const EMPTY_FORM_TEMPLATE: Partial<GetReportAuswertungBewilligungenRequest> = {
  parameters: {
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
    erfolgt: "0",
    notizen: "0",
    sort: "FOERDERBEREICH_PROJEKTNUMMER_BEWILLIGTAM",
    type: "PDF",
  },
};

const reportAuswertungBewilligungenApi = useReportAuswertungBewilligungenApi();

const reportAuswertungBewilligungenFormContext = computed(
  () => reportAuswertungBewilligungenApi.context.data.value
);

type ReportAuswertungBewilligungenFormRef = InstanceType<
  typeof reportauswertungbewilligungenform
>;

const reportAuswertungBewilligungenFormRef =
  useTemplateRef<ReportAuswertungBewilligungenFormRef>(
    "reportAuswertungBewilligungenForm"
  );
</script>
