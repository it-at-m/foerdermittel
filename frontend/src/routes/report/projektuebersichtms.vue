<template>
  <base-view :title="title">
    <template #default="{ baseViewLoading }">
      <report-card
        :empty-form-template="EMPTY_FORM_TEMPLATE"
        :loading="baseViewLoading"
        :api="reportProjektuebersichtMsApi"
        :form-ref="reportProjektuebersichtMsFormRef"
      >
        <template #form="{ item, updateValidity }">
          <report-projektuebersicht-ms-form
            ref="reportProjektuebersichtMsForm"
            :model-value="item"
            :report-projektuebersicht-ms-form-context="
              reportProjektuebersichtMsFormContext
            "
            @is-valid="updateValidity"
          />
        </template>
      </report-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { GetReportProjektuebersichtMsRequest } from "@/api/generated/foerdermittel-backend";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import ReportCard from "@/components/common/ReportCard.vue";
import ReportProjektuebersichtMsForm from "@/components/forms/report/ReportProjektuebersichtMsForm.vue";
import { useReportProjektuebersichtMsApi } from "@/composables/api/report/useReportProjektuebersichtMsApi";
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
  t("domain.report.projektUebersichtMs.modelName", 1),
]);

const EMPTY_FORM_TEMPLATE: Partial<GetReportProjektuebersichtMsRequest> = {
  parameters: {
    jahr: "",
    sbl: "",
    bez: "",
    fb: "",
    ua: "",
    kurz: "",
    offen: "",
    vn: "",
    sgt: "",
    bpg: "",
    sort: "FBPROJNR",
    type: "PDF",
  },
};

const reportProjektuebersichtMsApi = useReportProjektuebersichtMsApi();
const reportProjektuebersichtMsFormContext = computed(
  () => reportProjektuebersichtMsApi.context.data.value
);

type ReportProjektuebersichtMsFormRef = InstanceType<
  typeof ReportProjektuebersichtMsForm
>;
const reportProjektuebersichtMsFormRef =
  useTemplateRef<ReportProjektuebersichtMsFormRef>(
    "reportProjektuebersichtMsForm"
  );
</script>
