<template>
  <base-view :title="title">
    <template #default="{ baseViewLoading }">
      <report-card
        :empty-form-template="EMPTY_FORM_TEMPLATE"
        :loading="baseViewLoading"
        :api="reportHaushalt1Api"
        :form-ref="reportHaushalt1FormRef"
      >
        <template #form="{ item, updateValidity }">
          <report-haushalt1-form
            ref="reportHaushalt1Form"
            :model-value="item"
            :report-haushalt1-form-context="reportHaushalt1FormContext"
            @is-valid="updateValidity"
          />
        </template>
      </report-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { GetReportHaushalt1Request } from "@/api/generated/foerdermittel-backend";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import ReportCard from "@/components/common/ReportCard.vue";
import ReportHaushalt1Form from "@/components/forms/report/ReportHaushalt1Form.vue";
import { useReportHaushalt1Api } from "@/composables/api/useReportApi";
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
  t("domain.report.haushalt1.modelName", 1),
]);

const EMPTY_FORM_TEMPLATE: Partial<GetReportHaushalt1Request> = {
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

const reportHaushalt1Api = useReportHaushalt1Api();

const reportHaushalt1FormContext = computed(
  () => reportHaushalt1Api.context.data.value
);

type ReportHaushalt1FormRef = InstanceType<typeof ReportHaushalt1Form>;
const reportHaushalt1FormRef = useTemplateRef<ReportHaushalt1FormRef>(
  "reportHaushalt1Form"
);
</script>
