<template>
  <v-form
    ref="form"
    :disabled="!reportEuinformationenFormContext"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col cols="12">
        <fm-autocomplete
          v-model="modelValue.parameters!.publikation"
          :items="reportEuinformationenFormContext?.publikationen"
          :item-title="getPublikationTitle"
          item-value="publikation"
          :validation-attribute-map="
            ReportEuinformationenDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="publikation"
          :label="t('domain.report.euinformation.publikation')"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <v-select
          v-model="modelValue.parameters!.referatBez"
          :items="referatOptions"
          item-value="referat"
          :label="t('domain.report.euinformation.referat')"
          variant="outlined"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <v-select
          v-model="modelValue.parameters!.sort"
          :items="sortOptions"
          item-value="value"
          label="Sortierung"
          variant="outlined"
        />
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type {
  GetReportEuinformationenRequest,
  ReportEuinformationFormContext,
  ReportEuinformationFormContextDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

//import { ReportEuinformationenDTOPropertyValidationAttributesMap } from "@/api/generated/foerdermittel-backend";

import FmAutocomplete from "@/components/common/FmAutocomplete.vue";

const { t } = useI18n();

const modelValue = defineModel<Partial<GetReportEuinformationenRequest>>({
  required: true,
});

const { reportEuinformationenFormContext } = defineProps<{
  reportEuinformationenFormContext?: DeepReadonly<ReportEuinformationFormContext>;
}>();

const emit = defineEmits<{
  isValid: [boolean | null];
}>();

function onValidityChanged(newIsValid: boolean | null) {
  emit("isValid", newIsValid);
}

const formRef = useTemplateRef<VForm>("form");
async function validate() {
  if (formRef.value) {
    await formRef.value.validate();
  }
}
defineExpose({
  validate,
});

function getPublikationTitle(item: ReportEuinformationFormContextDTO) {
  return item ? `${item.kurzform} (${item.bezeichnung})` : "";
}
const referatOptions = [
  {
    title: t("domain.report.euinformation.referatEnum.alleReferate"),
    value: "Alle Referate",
  },
  {
    title: t("domain.report.euinformation.referatEnum.raw"),
    value: "RAW",
  },
  {
    title: t("domain.report.euinformation.referatEnum.rbs"),
    value: "RBS",
  },
  {
    title: t("domain.report.euinformation.referatEnum.sozrefR5"),
    value: "Sozref R 5",
  },
  {
    title: t("domain.report.euinformation.referatEnum.rgu11"),
    value: "RGU 11",
  },
  {
    title: t("domain.report.euinformation.referatEnum.rguCs"),
    value: "RGU Cs",
  },
  {
    title: t("domain.report.euinformation.referatEnum.krh"),
    value: "Krh",
  },
  {
    title: t("domain.report.euinformation.referatEnum.awb"),
    value: "AWB",
  },
  {
    title: t("domain.report.euinformation.referatEnum.swm"),
    value: "SWM",
  },
  {
    title: t("domain.report.euinformation.referatEnum.kulturref"),
    value: "Kulturref",
  },
  {
    title: t("domain.report.euinformation.referatEnum.bauref"),
    value: "Bauref",
  },
  {
    title: t("domain.report.euinformation.referatEnum.planref"),
    value: "Planref",
  },
  {
    title: t("domain.report.euinformation.referatEnum.direktorium"),
    value: "Direktorium",
  },
  {
    title: t("domain.report.euinformation.referatEnum.por"),
    value: "POR",
  },
  {
    title: t("domain.report.euinformation.referatEnum.kvr"),
    value: "KVR",
  },
  {
    title: t("domain.report.euinformation.referatEnum.komref"),
    value: "Kommref",
  },
  {
    title: t("domain.report.euinformation.referatEnum.mse"),
    value: "MSE",
  },
  {
    title: t("domain.report.euinformation.referatEnum.stk"),
    value: "Stk",
  },
];

const sortOptions = [
  {
    title: t("domain.report.euinformation.sortEnum.publikation"),
    value: "PUBLIKATION",
  },
  {
    title: t("domain.report.euinformation.sortEnum.jahr"),
    value: "JAHR",
  },
  {
    title: t("domain.report.euinformation.sortEnum.referat"),
    value: "REFERAT",
  },
  {
    title: t("domain.report.euinformation.sortEnum.referatJahr"),
    value: "REFERAT_JAHR",
  },
];
</script>
