<template>
  <base-view :title="t('domain.report.annahmeAnordnungSAP')">
    <template #default="{ baseViewLoading }">
      <report-card
        :empty-form-template="EMPTY_FORM_TEMPLATE"
        :loading="baseViewLoading"
        :api="reportAnnahmeAnordnungSapApi"
        :form-ref="reportAnnahmeAnordnungSapFormRef"
      >
        <template #form="{ item, updateValidity }">
          <report-annahme-anordnung-sap-form
            ref="reportAnnahmeAnordnungSapForm"
            :model-value="item"
            :report-annahme-anordnung-sap-form-context="
              reportAnnahmeAnordnungSapFormContext
            "
            @is-valid="updateValidity"
          />
        </template>
      </report-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { GetReportAnnahmeAnordnungSapRequest } from "@/api/generated/foerdermittel-backend";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import ReportCard from "@/components/common/ReportCard.vue";
import ReportAnnahmeAnordnungSapForm from "@/components/forms/report/ReportAnnahmeAnordnungSapForm.vue";
import { useReportAnnahmeAnordnungSapApi } from "@/composables/api/report/useReportAnnahmeAnordnungSapApi";
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

const EMPTY_FORM_TEMPLATE: Partial<GetReportAnnahmeAnordnungSapRequest> =
  {
    parameters: {
      projnr: "",
    },
  };

const reportAnnahmeAnordnungSapApi =
  useReportAnnahmeAnordnungSapApi();
const reportAnnahmeAnordnungSapFormContext = computed(
  () => reportAnnahmeAnordnungSapApi.context.data.value
);

type ReportAnnahmeAnordnungSapFormRef = InstanceType<
  typeof ReportAnnahmeAnordnungSapForm
>;
const reportAnnahmeAnordnungSapFormRef =
  useTemplateRef<ReportAnnahmeAnordnungSapFormRef>(
    "reportAnnahmeAnordnungSapForm"
  );
</script>
