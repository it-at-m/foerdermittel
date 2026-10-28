<template>
  <base-view :title="t('domain.report.bewilligungAuszahlungProjekt.modelName')">
    <template #default="{ baseViewLoading }">
      <report-card
        :empty-form-template="EMPTY_FORM_TEMPLATE"
        :loading="baseViewLoading"
        :api="reportBewilligungAuszahlungProjektApi"
        :form-ref="reportBewilligungAuszahlungProjektFormRef"
      >
        <template #form="{ item, updateValidity }">
          <report-bewilligung-auszahlung-projekt-form
            ref="reportBewilligungAuszahlungProjektForm"
            :model-value="item"
            :report-bewilligung-auszahlung-projekt-form-context="
              reportBewilligungAuszahlungProjektFormContext
            "
            @is-valid="updateValidity"
          />
        </template>
      </report-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { GetReportBewilligungAuszahlungProjektRequest } from "@/api/generated/foerdermittel-backend";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import ReportCard from "@/components/common/ReportCard.vue";
import ReportBewilligungAuszahlungProjektForm from "@/components/forms/report/ReportBewilligungAuszahlungProjektForm.vue";
import { useReportBewilligungAuszahlungProjektApi } from "@/composables/api/report/useReportBewilligungAuszahlungProjektApi";
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

const EMPTY_FORM_TEMPLATE: Partial<GetReportBewilligungAuszahlungProjektRequest> =
  {
    parameters: {
      projnr: "",
      erfolgt: false,
    },
  };

const reportBewilligungAuszahlungProjektApi =
  useReportBewilligungAuszahlungProjektApi();
const reportBewilligungAuszahlungProjektFormContext = computed(
  () => reportBewilligungAuszahlungProjektApi.context.data.value
);

type ReportBewilligungAuszahlungProjektFormRef = InstanceType<
  typeof ReportBewilligungAuszahlungProjektForm
>;
const reportBewilligungAuszahlungProjektFormRef =
  useTemplateRef<ReportBewilligungAuszahlungProjektFormRef>(
    "reportBewilligungAuszahlungProjektForm"
  );
</script>
