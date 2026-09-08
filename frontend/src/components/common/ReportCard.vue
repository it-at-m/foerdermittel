<template>
  <v-card class="d-flex flex-column fill-height w-100">
    <v-card-title class="pa-0">
      <v-row
        align-content="center"
        justify="center"
        class="mb-4"
      >
        <v-col class="d-flex align-center justify-end">
          <v-btn
            variant="flat"
            color="accent"
            class="text-body-large"
            :append-icon="mdiDownload"
            :text="t('common.action.download')"
            :disabled="loading || !isFormSlotValid"
            @click="handleDownload"
          />
        </v-col>
      </v-row>
    </v-card-title>
    <v-card-text class="pa-0">
      <div class="d-flex flex-column h-100 pb-5">
        <slot
          name="form"
          :item="currentValue"
          :update-validity="updateFormValidity"
        />
      </div>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts" generic="TGetOptsRequest, TContextResponse extends {}">
import type { ReportApiComposables } from "@/util/composable-helper";

import { mdiDownload } from "@mdi/js";
import { computed, onMounted, ref, toRaw } from "vue";
import { useI18n } from "vue-i18n";

import { STATUS_INDICATORS } from "@/constants";
import { useSnackbarStore } from "@/stores/snackbar";
import { openURL, toURL } from "@/util/url-helper";

const { t } = useI18n();

const {
  emptyFormTemplate,
  loading: loadingProp = false,
  api,
  formRef,
} = defineProps<{
  emptyFormTemplate: Partial<TGetOptsRequest>;
  loading?: boolean;
  api: ReportApiComposables<TGetOptsRequest, TContextResponse>;
  formRef?: { validate: () => unknown } | null | undefined;
}>();

const loading = computed(
  () => loadingProp || api.getOpts.loading.value || api.context.loading.value
);

const currentValue = ref(structuredClone(toRaw(emptyFormTemplate)));

const isFormSlotValid = ref(false);
const updateFormValidity = (valid: boolean | null) => {
  isFormSlotValid.value = !!valid;
};

onMounted(async () => {
  await loadFormContext();
});

const loadFormContext = async () => {
  await api.context.call();
};

async function handleDownload() {
  const model = currentValue.value as TGetOptsRequest;
  const urlOpts = await api.getOpts.call(model);
  if (!api.getOpts.error.value && urlOpts) {
    const url = toURL(urlOpts);
    openURL(url);
    await onSuccess();
  } else {
    await onFailure(
      t("common.message.createdError", [t("common.word.report")])
    );
  }
}

const snackbarStore = useSnackbarStore();
const onSuccess = async () => {
  await loadFormContext();
};
const onFailure = async (msg: string) => {
  snackbarStore.push({ text: msg, color: STATUS_INDICATORS.ERROR });
  await loadFormContext();
  await formRef?.validate();
};
</script>
