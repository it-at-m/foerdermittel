<template>
  <base-view :title="title">
    <template #default="{ baseViewLoading }">
      <report-card
        :empty-form-template="EMPTY_FORM_TEMPLATE"
        :loading="baseViewLoading"
        :api="reportFortsetzungsantragApi"
        :form-ref="reportFortsetzungsantragFormRef"
      >
        <template #form="{ item, updateValidity }">
          <report-fortsetzungsantrag-form
            ref="reportFortsetzungsantragForm"
            :model-value="item"
            :report-fortsetzungsantrag-form-context="reportFortsetzungsantragFormContext"
            @is-valid="updateValidity"
          />
        </template>
      </report-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { GetReportFortsetzungsantragRequest } from "@/api/generated/foerdermittel-backend";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import ReportCard from "@/components/common/ReportCard.vue";
import ReportFortsetzungsantragForm from "@/components/forms/report/ReportFortsetzungsantragForm.vue";
import { useReportFortsetzungsantragApi } from "@/composables/api/useReportApi";
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
  t("domain.report.fortsetzungsAntrag.modelName", 1),
]);

const EMPTY_FORM_TEMPLATE: Partial<GetReportFortsetzungsantragRequest> = {
  parameters: {
    sbl: "",
    bez: "",
    fag: "1",
    ofPro: "1",
    type: "PDF",
  },
};

const reportFortsetzungsantragApi = useReportFortsetzungsantragApi();

const reportFortsetzungsantragFormContext = computed(
  () => reportFortsetzungsantragApi.context.data.value
);

type ReportFortsetzungsantragFormRef = InstanceType<typeof ReportFortsetzungsantragForm>;
const reportFortsetzungsantragFormRef = useTemplateRef<ReportFortsetzungsantragFormRef>(
  "reportFortsetzungsantragForm"
);
</script>
