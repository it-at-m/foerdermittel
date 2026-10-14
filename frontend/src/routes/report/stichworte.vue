<template>
  <base-view :title="title">
    <template #default="{ baseViewLoading }">
      <report-card
        :empty-form-template="EMPTY_FORM_TEMPLATE"
        :loading="baseViewLoading"
        :api="reportStichworteApi"
        :form-ref="reportStichworteFormRef"
      >
        <template #form="{ item, updateValidity }">
          <report-stichworte-form
            ref="reportStichworteForm"
            :model-value="item"
            :report-stichworte-form-context="reportStichworteFormContext"
            @is-valid="updateValidity"
          />
        </template>
      </report-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { GetReportStichworteRequest } from "@/api/generated/foerdermittel-backend";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import ReportCard from "@/components/common/ReportCard.vue";
import ReportStichworteForm from "@/components/forms/report/ReportStichworteForm.vue";
import { useReportStichworteApi } from "@/composables/api/useReportApi";
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

const EMPTY_FORM_TEMPLATE: Partial<GetReportStichworteRequest> = {
  parameters: {
    bereich: "",
  },
};

const reportStichworteApi = useReportStichworteApi();

const reportStichworteFormContext = computed(
  () => reportStichworteApi.context.data.value
);

type ReportStichworteFormRef = InstanceType<typeof ReportStichworteForm>;
const reportStichworteFormRef = useTemplateRef<ReportStichworteFormRef>(
  "reportStichworteForm"
);
</script>
