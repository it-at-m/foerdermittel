<template>
  <base-view :title="title">
    <template #default="{ baseViewLoading }">
      <report-card
        :empty-form-template="EMPTY_FORM_TEMPLATE"
        :loading="baseViewLoading"
        :api="reportAuswertungArchivApi"
        :form-ref="reportAuswertungArchivFormRef"
      >
        <template #form="{ item, updateValidity }">
          <reportauswertungarchivform
            ref="reportAuswertungArchivForm"
            :model-value="item"
            :reportauswertungarchivformcontext="
              reportAuswertungArchivFormContext
            "
            @is-valid="updateValidity"
          />
        </template>
      </report-card>
    </template>
  </base-view>
</template>

<script setup lang="ts">
import type { GetReportAuswertungArchivRequest } from "@/api/generated/foerdermittel-backend";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import ReportCard from "@/components/common/ReportCard.vue";
import reportauswertungarchivform from "@/components/forms/report/ReportAuswertungArchivForm.vue";
import { useReportAuswertungArchivApi } from "@/composables/api/report/useReportAuswertungArchivApi";
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
  t("domain.report.auswertungArchiv.modelName", 1),
]);

const EMPTY_FORM_TEMPLATE: Partial<GetReportAuswertungArchivRequest> = {
  parameters: {
    film: false,
    filmohne: false,
    mp: false,
    offen: false,
    op: false,
    sort: "STRASSE_PROJEKTNUMMER",
  },
};

const reportAuswertungArchivApi = useReportAuswertungArchivApi();

const reportAuswertungArchivFormContext = computed(
  () => reportAuswertungArchivApi.context.data.value
);

type ReportAuswertungArchivFormRef = InstanceType<
  typeof reportauswertungarchivform
>;

const reportAuswertungArchivFormRef =
  useTemplateRef<ReportAuswertungArchivFormRef>("reportAuswertungArchivForm");
</script>
