<template>
  <base-view :title="title">
    <template #default="{ baseViewLoading }">
      <report-card
          :empty-form-template="EMPTY_FORM_TEMPLATE"
          :loading="baseViewLoading"
          :api="reportStaedtebaufoerderungApi"
          :form-ref="reportStaedtebaufoerderungFormRef"
      >
        <template #form="{ item, updateValidity }">
          <report-staedtebaufoerderung-form
              ref="reportStaedtebaufoerderungForm"
              :model-value="item"
              :report-staedtebaufoerderung-form-context="
              reportStaedtebaufoerderungFormContext
            "
              @is-valid="updateValidity"
          />
        </template>
      </report-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { GetReportStaedtebaufoerderungRequest } from "@/api/generated/foerdermittel-backend";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import ReportCard from "@/components/common/ReportCard.vue";
import ReportStaedtebaufoerderungForm from "@/components/forms/report/ReportStaedtebaufoerderungForm.vue";
import { useReportStaedtebaufoerderungApi } from "@/composables/api/useReportApi";
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
  t("domain.staedtebaufoerderung.modelName", 1),
]);

const EMPTY_FORM_TEMPLATE: Partial<GetReportStaedtebaufoerderungRequest> = {
  parameters: {
    type: "PDF",
  },
};

const reportStaedtebaufoerderungApi = useReportStaedtebaufoerderungApi();

const reportStaedtebaufoerderungFormContext = computed(
    () => reportStaedtebaufoerderungApi.context.data.value
);

type ReportStaedtebaufoerderungFormRef = InstanceType<
    typeof ReportStaedtebaufoerderungForm
>;
const reportStaedtebaufoerderungFormRef =
    useTemplateRef<ReportStaedtebaufoerderungFormRef>("reportStaedtebaufoerderungForm");
</script>
