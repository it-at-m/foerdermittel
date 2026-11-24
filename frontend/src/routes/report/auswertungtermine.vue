<template>
  <base-view :title="title">
    <template #default="{ baseViewLoading }">
      <report-card
        :empty-form-template="EMPTY_FORM_TEMPLATE"
        :loading="baseViewLoading"
        :api="reportAuswertungTermineApi"
        :form-ref="reportAuswertungTermineFormRef"
      >
        <template #form="{ item, updateValidity }">
          <reportauswertungtermineform
            ref="reportAuswertungTermineForm"
            :model-value="item"
            :reportauswertungtermineformcontext="
              reportAuswertungTermineFormContext
            "
            @is-valid="updateValidity"
          />
        </template>
      </report-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { GetReportAuswertungTermineRequest } from "@/api/generated/foerdermittel-backend";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import ReportCard from "@/components/common/ReportCard.vue";
import reportauswertungtermineform from "@/components/forms/report/ReportAuswertungTermineForm.vue";
import { useReportAuswertungTermineApi } from "@/composables/api/report/useReportAuswertungTermineApi";
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
  t("domain.report.auswertungTermine.modelName", 1),
]);

const EMPTY_FORM_TEMPLATE: Partial<GetReportAuswertungTermineRequest> = {
  parameters: {
    zukunft: false,
    ueb: false,
    sort: "PROJEKTNUMMER",
  },
};

const reportAuswertungTermineApi = useReportAuswertungTermineApi();

const reportAuswertungTermineFormContext = computed(
  () => reportAuswertungTermineApi.context.data.value
);

type ReportAuswertungTermineFormRef = InstanceType<
  typeof reportauswertungtermineform
>;

const reportAuswertungTermineFormRef =
  useTemplateRef<ReportAuswertungTermineFormRef>("reportAuswertungTermineForm");
</script>
