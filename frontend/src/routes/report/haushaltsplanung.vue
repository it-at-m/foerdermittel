<template>
  <base-view :title="title">
    <template #default="{ baseViewLoading }">
      <report-card
        :empty-form-template="EMPTY_FORM_TEMPLATE"
        :loading="baseViewLoading"
        :api="reportHaushaltsplanungApi"
        :form-ref="reportHaushaltsplanungFormRef"
      >
        <template #form="{ item, updateValidity }">
          <report-haushaltsplanung-form
            ref="reportHaushaltsplanungForm"
            :model-value="item"
            :report-haushaltsplanung-form-context="reportHaushaltsplanungFormContext"
            @is-valid="updateValidity"
          />
        </template>
      </report-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { GetReportHaushaltsplanungRequest } from "@/api/generated/foerdermittel-backend";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import ReportCard from "@/components/common/ReportCard.vue";
import ReportHaushaltsplanungForm from "@/components/forms/report/ReportHaushaltsplanungForm.vue";
import { useReportHaushaltsplanungApi } from "@/composables/api/useReportApi";
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
  t("domain.report.haushaltsplanung.modelName", 1),
]);

const EMPTY_FORM_TEMPLATE: Partial<GetReportHaushaltsplanungRequest> = {
  parameters: {
    haushaltsjahr: "",
    fb: "",
    fipo: "",
    sbl: "",
    bez: "",
    hh: "0",
    sort: "PROJEKTNUMMER",
    type: "PDF",
  },
};

const reportHaushaltsplanungApi = useReportHaushaltsplanungApi();

const reportHaushaltsplanungFormContext = computed(
  () => reportHaushaltsplanungApi.context.data.value
);

type ReportHaushaltsplanungFormRef = InstanceType<typeof ReportHaushaltsplanungForm>;
const reportHaushaltsplanungFormRef = useTemplateRef<ReportHaushaltsplanungFormRef>(
  "reportHaushaltsplanungForm"
);
</script>
