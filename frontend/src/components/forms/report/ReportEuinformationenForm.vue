<template>
  <v-form
    ref="form"
    :disabled="!reportEuinformationenFormContext"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <fm-text-field
          v-model="modelValue.parameters!.jahrVon"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportEuinformationDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="jahrVon"
          :label="t('domain.report.euinformation.jahrVon')"
          :error-messages="jahrVonError"
        />
      </v-col>
      <v-col md="6">
        <fm-text-field
          v-model="modelValue.parameters!.jahrBis"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportEuinformationDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="jahrBis"
          :label="t('domain.report.euinformation.jahrBis')"
          :error-messages="jahrBisError"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.publikation"
          :items="reportEuinformationenFormContext?.publikationen"
          :item-title="getPublikationTitle"
          item-value="kurzform"
          :validation-attribute-map="
            PublikationFormContextDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="publikation"
          :label="t('domain.report.euinformation.publikation')"
        />
      </v-col>

      <v-col md="6">
        <v-select
          v-model="modelValue.parameters!.referatId"
          :items="referatOptions"
          item-value="value"
          :label="t('domain.report.euinformation.referat')"
          variant="outlined"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <fm-text-field
          v-model="modelValue.parameters!.inhalt"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportEuinformationDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="inhalt"
          :label="t('domain.report.euinformation.inhalt')"
        />
      </v-col>

      <v-col md="6">
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
  PublikationFormContextDTO,
  ReportEuinformationFormContext,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import {
  PublikationFormContextDTOPropertyValidationAttributesMap,
  ReportEuinformationDTOPropertyValidationAttributesMap,
} from "@/api/generated/foerdermittel-backend";
//import { ReportEuinformationenDTOPropertyValidationAttributesMap } from "@/api/generated/foerdermittel-backend";

import FmAutocomplete from "@/components/common/FmAutocomplete.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";

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

function getPublikationTitle(item: PublikationFormContextDTO) {
  return item ? `${item.kurzform} (${item.bezeichnung})` : "";
}
const referatOptions = [
  {
    title: t("domain.report.euinformation.referatEnum.alleReferate"),
    value: "20",
  },
  {
    title: t("domain.report.euinformation.referatEnum.raw"),
    value: "1",
  },
  {
    title: t("domain.report.euinformation.referatEnum.rbs"),
    value: "2",
  },
  {
    title: t("domain.report.euinformation.referatEnum.sozrefR5"),
    value: "3",
  },
  {
    title: t("domain.report.euinformation.referatEnum.rgu11"),
    value: "4",
  },
  {
    title: t("domain.report.euinformation.referatEnum.rguCs"),
    value: "5",
  },
  {
    title: t("domain.report.euinformation.referatEnum.krh"),
    value: "6",
  },
  {
    title: t("domain.report.euinformation.referatEnum.awb"),
    value: "7",
  },
  {
    title: t("domain.report.euinformation.referatEnum.swm"),
    value: "8",
  },
  {
    title: t("domain.report.euinformation.referatEnum.kulturref"),
    value: "9",
  },
  {
    title: t("domain.report.euinformation.referatEnum.bauref"),
    value: "10",
  },
  {
    title: t("domain.report.euinformation.referatEnum.planref"),
    value: "11",
  },
  {
    title: t("domain.report.euinformation.referatEnum.direktorium"),
    value: "12",
  },
  {
    title: t("domain.report.euinformation.referatEnum.por"),
    value: "13",
  },
  {
    title: t("domain.report.euinformation.referatEnum.kvr"),
    value: "14",
  },
  {
    title: t("domain.report.euinformation.referatEnum.komref"),
    value: "15",
  },
  {
    title: t("domain.report.euinformation.referatEnum.mse"),
    value: "16",
  },
  {
    title: t("domain.report.euinformation.referatEnum.stk"),
    value: "17",
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

const jahrVonError = computed(() => {
  const von = modelValue.value.parameters?.jahrVon;
  const bis = modelValue.value.parameters?.jahrBis;

  if (von == null || von === "" || bis == null || bis === "") {
    return [];
  }

  return Number(von) > Number(bis)
    ? ["Jahr von darf nicht größer als Jahr bis sein."]
    : [];
});

const jahrBisError = computed(() => {
  const von = modelValue.value.parameters?.jahrVon;
  const bis = modelValue.value.parameters?.jahrBis;

  if (von == null || von === "" || bis == null || bis === "") {
    return [];
  }

  return Number(bis) < Number(von)
    ? ["Jahr bis darf nicht kleiner als Jahr von sein."]
    : [];
});
</script>
