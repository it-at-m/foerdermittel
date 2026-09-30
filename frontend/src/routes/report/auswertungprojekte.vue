<template>
  <base-view :title= "title">
    <template #default="{ baseViewLoading }">
      <report-card
          :empty-form-template="EMPTY_FORM_TEMPLATE"
          :loading="baseViewLoading"
          :api="reportAuswertungProjekteApi"
          :form-ref="reportAuswertungProjekteFormRef"
      >
        <template #form="{ item, updateValidity }">
          <report-auswertung-projekte-form
              ref="reportAuswertungProjektForm"
              :model-value="item"
              :report-auswertung-projekt-form-context="
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

const title = t("common.generics.reportTitle", [
  t("domain.report.auswertungProjekt.modelName", 1),
]);

const EMPTY_FORM_TEMPLATE: Partial<GetReportAuswertungProjektRequest> = {
  parameters: {
    jahr: "",
    sbl: "",
    bez: "",
    fb: "",
    ua: "",
    kurz: "",
    pname: "",
    pstrasse: "",
    krisofp: "",
    sgt: "",
    bpg: "",
    kauf: "0",
    offen: "0",
    relevant: "0",
    sort: "PROJEKTNUMMER",
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
