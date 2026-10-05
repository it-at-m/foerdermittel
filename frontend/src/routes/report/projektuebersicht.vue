<template>
  <base-view :title="t('domain.report.projektUebersicht')">
    <template #default="{ baseViewLoading }">
      <report-card
        :empty-form-template="EMPTY_FORM_TEMPLATE"
        :loading="baseViewLoading"
        :api="reportProjektuebersichtApi"
        :form-ref="reportProjektuebersichtFormRef"
      >
        <template #form="{ item, updateValidity }">
          <report-projektuebersicht-form
            ref="reportProjektuebersichtForm"
            :model-value="item"
            :report-projektuebersicht-form-context="
              reportProjektuebersichtFormContext
            "
            @is-valid="updateValidity"
          />
        </template>
      </report-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { GetReportProjektuebersichtRequest } from "@/api/generated/foerdermittel-backend";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import ReportCard from "@/components/common/ReportCard.vue";
import ReportProjektuebersichtForm from "@/components/forms/report/ReportProjektuebersichtForm.vue";
import { useReportProjektuebersichtApi } from "@/composables/api/useReportApi";
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

const EMPTY_FORM_TEMPLATE: Partial<GetReportProjektuebersichtRequest> = {
  parameters: {
    projnr: "",
    notiz: false,
  },
};

const reportProjektuebersichtApi = useReportProjektuebersichtApi();
const reportProjektuebersichtFormContext = computed(
  () => reportProjektuebersichtApi.context.data.value
);

type ReportProjektuebersichtFormRef = InstanceType<
  typeof ReportProjektuebersichtForm
>;
const reportProjektuebersichtFormRef =
  useTemplateRef<ReportProjektuebersichtFormRef>("reportProjektuebersichtForm");
</script>
