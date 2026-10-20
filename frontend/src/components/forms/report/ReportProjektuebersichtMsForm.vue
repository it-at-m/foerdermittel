<template>
  <v-form
    ref="form"
    :disabled="!reportProjektuebersichtMsFormContext"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col
        cols="12"
        md="2"
      >
        <fm-text-field
          v-model="modelValue.parameters!.jahr"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportProjektuebersichtMsDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="jahr"
          :label="t('domain.report.common.jahr')"
        />
      </v-col>
      <v-col
        cols="12"
        md="5"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.sbl"
          :items="reportProjektuebersichtMsFormContext?.stadtbezirkslisten"
          :item-title="getStadtbezirkslisteTitle"
          item-value="kurzbez"
          :validation-attribute-map="
            ReportProjektuebersichtMsDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="sbl"
          :label="t('domain.report.projektUebersichtMs.sbl')"
        />
      </v-col>
      <v-col
        cols="12"
        md="5"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.bez"
          :items="reportProjektuebersichtMsFormContext?.stadtbezirke"
          :item-title="getStadtbezirkTitle"
          item-value="stadtbezirk"
          :validation-attribute-map="
            ReportProjektuebersichtMsDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="bez"
          :label="t('domain.report.projektUebersichtMs.bez')"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.fb"
          :items="reportProjektuebersichtMsFormContext?.foerderbereiche"
          :item-title="getFoerderbereichTitle"
          item-value="fb"
          :validation-attribute-map="
            ReportProjektuebersichtMsDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="fb"
          :label="t('domain.report.projektUebersichtMs.fb')"
        />
      </v-col>
      <v-col
        cols="12"
        md="6"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.ua"
          :items="reportProjektuebersichtMsFormContext?.unterabschnitte"
          :item-title="getUnterabschnittTitle"
          item-value="ua"
          :validation-attribute-map="
            ReportProjektuebersichtMsDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="ua"
          :label="t('domain.report.projektUebersichtMs.ua')"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.kurz"
          :items="reportProjektuebersichtMsFormContext?.kurzbezeichnungen"
          :item-title="getKurzbezeichnungTitle"
          item-value="kurzbez"
          :validation-attribute-map="
            ReportProjektuebersichtMsDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="kurz"
          :label="t('domain.report.projektUebersichtMs.kurz')"
        />
      </v-col>
      <v-col
        cols="12"
        md="3"
      >
        <fm-checkbox
          v-model="modelValue.parameters!.offen"
          :label="t('domain.report.projektUebersichtMs.offen')"
          true-value="1"
          false-value="0"
        />
      </v-col>
      <v-col md="3">
        <fm-checkbox
          v-model="modelValue.parameters!.vn"
          :label="t('domain.report.projektUebersichtMs.vn')"
          true-value="1"
          false-value="0"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.krisofp"
          :items="reportProjektuebersichtMsFormContext?.krisofps"
          :item-title="getKrisofpTitle"
          :item-value="getKrisofpValue"
          :validation-attribute-map="
            ReportProjektuebersichtMsDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="krisofp"
          :label="t('domain.report.projektUebersichtMs.krisofp')"
        />
      </v-col>
      <v-col
        cols="12"
        md="6"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.sgt"
          :items="reportProjektuebersichtMsFormContext?.siedlungsgebiete"
          :item-title="getSiedlungsgebietTitle"
          item-value="siedlungsgebiet"
          :validation-attribute-map="
            ReportProjektuebersichtMsDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="sgt"
          :label="t('domain.report.projektUebersichtMs.sgt')"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.bpg"
          :items="reportProjektuebersichtMsFormContext?.bauprogramme"
          :item-title="getBauprogrammTitle"
          item-value="bauprogramm"
          :validation-attribute-map="
            ReportProjektuebersichtMsDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="bpg"
          :label="t('domain.report.projektUebersichtMs.bpg')"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <v-select
          v-model="modelValue.parameters!.sort"
          :items="sortOptions"
          item-value="value"
          :label="t('domain.report.common.reportSort')"
          variant="outlined"
        />
      </v-col>
      <v-col md="6">
        <v-select
          v-model="modelValue.parameters!.type"
          :items="formatOptions"
          item-value="value"
          :label="t('domain.report.common.reportType.modelName')"
          variant="outlined"
        />
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type {
  BauprogrammFormContextDTO,
  FoerderbereichFormContextDTO,
  GetReportProjektuebersichtMsRequest,
  KurzbezeichnungFormContextDTO,
  ListennameStadtbezirkslisteFormContextDTO,
  ReportProjektuebersichtMsFormContext,
  ReportProjektuebersichtMsFormContextKrisofpsEnum,
  SiedlungsgebietFormContextDTO,
  StadtbezirkFormContextDTO,
  UnterabschnittFormContextDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import {
  ReportEuinformationDTOPropertyValidationAttributesMap,
  ReportProjektuebersichtMsDTOPropertyValidationAttributesMap,
} from "@/api/generated/foerdermittel-backend";
import FmAutocomplete from "@/components/common/FmAutocomplete.vue";
import FmCheckbox from "@/components/common/FmCheckbox.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";
import { getKrisofpTitle } from "@/util/krisofpUtils";

const { t } = useI18n();

const modelValue = defineModel<Partial<GetReportProjektuebersichtMsRequest>>({
  required: true,
});

const { reportProjektuebersichtMsFormContext } = defineProps<{
  reportProjektuebersichtMsFormContext?: DeepReadonly<ReportProjektuebersichtMsFormContext>;
}>();

const emit = defineEmits<{
  isValid: [boolean | null];
}>();

function onValidityChanged(newIsValid: boolean | null) {
  emit("isValid", newIsValid);
}

function getStadtbezirkslisteTitle(
  item: ListennameStadtbezirkslisteFormContextDTO
) {
  return item ? `${item.kurzbez} (${item.bezeichnung})` : "";
}

function getStadtbezirkTitle(item: StadtbezirkFormContextDTO) {
  return item ? `${item.stadtbezirk} (${item.bezeichnung})` : "";
}

function getFoerderbereichTitle(item: FoerderbereichFormContextDTO) {
  return item ? `${item.fb} (${item.bezeichnung})` : "";
}

function getUnterabschnittTitle(item: UnterabschnittFormContextDTO) {
  return item ? `${item.ua} (${item.bezeichnung})` : "";
}

function getKurzbezeichnungTitle(item: KurzbezeichnungFormContextDTO) {
  return item ? `${item.kurzbez} (${item.bezeichnung})` : "";
}

function getKrisofpValue(
  item: ReportProjektuebersichtMsFormContextKrisofpsEnum
): string {
  return item;
}

function getSiedlungsgebietTitle(item: SiedlungsgebietFormContextDTO) {
  return item ? `${item.siedlungsgebiet} (${item.bezeichnung})` : "";
}

function getBauprogrammTitle(item: BauprogrammFormContextDTO) {
  return item ? `${item.bauprogramm} (${item.bezeichnung})` : "";
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

const sortOptions = [
  {
    title: t("domain.report.projektUebersichtMs.sortEnum.projektnummer"),
    value: "PROJEKTNUMMER",
  },
  {
    title: t("domain.report.projektUebersichtMs.sortEnum.strasseProjektnummer"),
    value: "STRASSEPROJNR",
  },
  {
    title: t("domain.report.projektUebersichtMs.sortEnum.fbProjektnummer"),
    value: "FBPROJNR",
  },
];

const formatOptions = [
  {
    title: t("domain.report.common.reportType.pdf"),
    value: "PDF",
  },
  {
    title: t("domain.report.common.reportType.excel"),
    value: "EXCEL",
  },
];
</script>
