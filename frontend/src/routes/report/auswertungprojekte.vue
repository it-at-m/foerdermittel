<template>
  <base-view :title="t('domain.report.projektUebersicht')">
    <template #default="{ baseViewLoading }">
      <report-card
          :empty-form-template="EMPTY_FORM_TEMPLATE"
          :loading="baseViewLoading"
          :api="reportAuswertungProjekteApi"
          :form-ref="reportAuswertungProjekteFormRef"
      >
        <template #form="{ item, updateValidity }">
          <report-auswertung-projekte-form
              ref="reportAuswertungProjekteForm"
              :model-value="item"
              :report-auswertung-projekte-form-context="
              reportAuswertungProjekteFormContext
            "
              @is-valid="updateValidity"
          />
        </template>
      </report-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { GetReportAuswertungProjektRequest } from "@/api/generated/foerdermittel-backend";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import ReportCard from "@/components/common/ReportCard.vue";
import ReportAuswertungProjekteForm from "@/components/forms/report/ReportAuswertungProjekteForm.vue";
import { useReportAuswertungProjekteApi } from "@/composables/api/useReportApi";
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

const EMPTY_FORM_TEMPLATE: Partial<GetReportAuswertungProjektRequest> = {
  parameters: {
    jahr: "",
    sgt: "",
    bez: "",
    fb: "",
    ua: "",
    kurz: "",
    pname: "",
    pstrasse: "",
    foerderprogramm: "",
    krisofp: "",
    sbg: "",
    bpg: "",
  },
};

const reportAuswertungProjekteApi = useReportAuswertungProjekteApi();

const reportAuswertungProjekteFormContext = computed(
    () => reportAuswertungProjekteApi.context.data.value,
);

type ReportAuswertungProjekteFormRef = InstanceType<
    typeof ReportAuswertungProjekteForm
>;

const reportAuswertungProjekteFormRef =
    useTemplateRef<ReportAuswertungProjekteFormRef>(
        "reportAuswertungProjekteForm",
    );
</script>
