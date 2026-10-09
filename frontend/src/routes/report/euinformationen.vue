<template>
  <base-view :title="title">
    <template #default="{ baseViewLoading }">
      <report-card
        :empty-form-template="EMPTY_FORM_TEMPLATE"
        :loading="baseViewLoading"
        :api="reportEuinformationenApi"
        :form-ref="reportEuinformationenFormRef"
      >
        <template #form="{ item, updateValidity }">
          <report-euinformationen-form
            ref="reportEuinformationenForm"
            :model-value="item"
            :report-euinformationen-form-context="
              reportEuinformationenFormContext
            "
            @is-valid="updateValidity"
          />
        </template>
      </report-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { GetReportEuinformationenRequest } from "@/api/generated/foerdermittel-backend";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import ReportCard from "@/components/common/ReportCard.vue";
import ReportEuinformationenForm from "@/components/forms/report/ReportEuinformationenForm.vue";
import { useReportEuinformationApi } from "@/composables/api/report/useReportEuinformationApi";
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

const domainKey = "model.ablageindex.modelName";

const { t } = useI18n();
const title = t("common.generics.reportTitle", [t(domainKey, 2)]);

const EMPTY_FORM_TEMPLATE: Partial<GetReportEuinformationenRequest> = {
  parameters: {
    publikationen: "",
  },
};

const reportEuinformationenApi = useReportEuinformationApi();

const reportEuinformationenFormContext = computed(
  () => reportEuinformationenApi.context.data.value
);

type ReportEuinformationenFormRef = InstanceType<
  typeof ReportEuinformationenForm
>;
const reportEuinformationenFormRef =
  useTemplateRef<ReportEuinformationenFormRef>("reportEuinformationenForm");
</script>
