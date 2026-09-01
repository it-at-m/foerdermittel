<template>
  <base-view :title="title">
    <template #default="{ baseViewLoading }">
      <!-- @vue-generic {Partial<GetReportStichworteRequest>} -->
      <report-card
        :empty-item-template="EMPTY_ITEM_TEMPLATE"
        :loading="baseViewLoading || loading"
        @download="handleDownload"
      >
        <template #form="{ item, updateValidity }">
          <report-stichworte-form
            v-if="reportStichworteFormContext"
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

import { computed, onMounted, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import BaseView from "@/components/common/BaseView.vue";
import ReportCard from "@/components/common/ReportCard.vue";
import ReportStichworteForm from "@/components/forms/report/ReportStichworteForm.vue";
import {
  useGetReportStichworteFormContext,
  useGetReportStichworteOpts,
} from "@/composables/api/useReportApi";
import { STATUS_INDICATORS } from "@/constants";
import { useSnackbarStore } from "@/stores/snackbar";
import { Role } from "@/types/Role";
import { openURL, toURL } from "@/util/url-helper";

definePage({
  meta: {
    hasAnyRole: [
      Role.SACHBEARBEITUNG,
      Role.SACHBEARBEITUNG_HAUSHALT,
      Role.ADMIN,
    ],
  },
});

onMounted(async () => {
  await getReportStichworteFormContext();
});

const domainKey = "model.ablageindex.modelName";

const { t } = useI18n();
const title = t("common.generics.reportTitle", [t(domainKey, 2)]);

const EMPTY_ITEM_TEMPLATE: Partial<GetReportStichworteRequest> = {
  bereich: "",
};

const {
  call: getReportStichworteOpts,
  loading: getReportStichworteOptsLoading,
  error: getReportStichworteOptsError,
} = useGetReportStichworteOpts();

const {
  data: reportStichworteFormContext,
  call: getReportStichworteFormContext,
  loading: getReportStichworteFormContextLoading,
} = useGetReportStichworteFormContext();

const loading = computed(
  () =>
    getReportStichworteOptsLoading.value ||
    getReportStichworteFormContextLoading.value
);

async function handleDownload(params: Partial<GetReportStichworteRequest>) {
  // TODO: some type checking improvements
  const model = params as GetReportStichworteRequest;
  const urlOpts = await getReportStichworteOpts(model);
  if (!getReportStichworteOptsError.value) {
    await onSuccess(t("common.message.created", [t("common.word.report")]));
    const url = toURL(urlOpts);
    openURL(url);
  } else {
    await onFailure(
      t("common.message.createdError", [t("common.word.report")])
    );
  }
}

type ReportStichworteFormRef = InstanceType<typeof ReportStichworteForm>;
const reportStichworteFormRef = useTemplateRef<ReportStichworteFormRef>(
  "reportStichworteForm"
);

const snackbarStore = useSnackbarStore();
const onSuccess = async (msg: string) => {
  snackbarStore.push({ text: msg, color: STATUS_INDICATORS.SUCCESS });
  await getReportStichworteFormContext();
};
const onFailure = async (msg: string) => {
  snackbarStore.push({ text: msg, color: STATUS_INDICATORS.ERROR });
  await getReportStichworteFormContext();
  if (validate) {
    await validate();
  }
};

async function validate() {
  await reportStichworteFormRef?.value?.validate();
}
</script>
