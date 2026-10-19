<template>
  <v-form
    ref="form"
    :disabled="!reportProjektuebersichtMsFormContext"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.sbl"
          :items="reportProjektuebersichtMsFormContext?.stadtbezirkslisten"
          :item-title="getStadtbezirkslisteTitle"
          item-value="sbl"
          :validation-attribute-map="
            ReportProjektuebersichtMsDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="sbl"
          :label="t('domain.report.projektuebersichtMs.sbl')"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.bez"
          :items="reportProjektuebersichtMsFormContext?.stadtbezirke"
          :item-title="getStadtbezirkTitle"
          item-value="bez"
          :validation-attribute-map="
            ReportProjektuebersichtMsDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="bez"
          :label="t('domain.report.projektuebersichtMs.bez')"
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
          :label="t('domain.report.projektuebersichtMs.fb')"
        />
      </v-col>
    </v-row>
    <v-row>
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
          :label="t('domain.report.projektuebersichtMs.ua')"
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
          item-value="kurz"
          :validation-attribute-map="
            ReportProjektuebersichtMsDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="kurz"
          :label="t('domain.report.projektuebersichtMs.kurz')"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <fm-checkbox
          v-model="modelValue.parameters!.offen"
          :label="t('domain.report.projektuebersichtMs.offen')"
        />
      </v-col>
      <v-col md="6">
        <fm-checkbox
          v-model="modelValue.parameters!.vn"
          :label="t('domain.report.projektuebersichtMs.vn')"
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
          item-value="krisofp"
          :validation-attribute-map="
            ReportProjektuebersichtMsDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="krisofp"
          :label="t('domain.report.projektuebersichtMs.krisofp')"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <v-select
          v-model="modelValue.parameters!.sort"
          :items="sortOptions"
          item-value="value"
          label="t('domain.report.projektuebersichtMs.sortierung')"
          variant="outlined"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <v-select
          v-model="modelValue.parameters!.type"
          :items="formatOptions"
          item-value="value"
          label="t('domain.report.projektuebersichtMs.format')"
          variant="outlined"
        />
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type {
  FoerderbereichFormContextDTO,
  GetReportProjektuebersichtMsRequest,
  KurzbezeichnungFormContextDTO,
  ListennameStadtbezirkslisteFormContextDTO,
  ReportProjektuebersichtMsFormContext,
  StadtbezirkFormContextDTO,
  UnterabschnittFormContextDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import {
  ReportProjektuebersichtMsDTOPropertyValidationAttributesMap,
} from "@/api/generated/foerdermittel-backend";
import FmAutocomplete from "@/components/common/FmAutocomplete.vue";
import FmCheckbox from "@/components/common/FmCheckbox.vue";
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
    title: t("domain.report.projektuebersichtMs.sortEnum.projektnummer"),
    value: "PROJEKTNUMMER",
  },
  {
    title: t("domain.report.projektuebersichtMs.sortEnum.strasseProjektnummer"),
    value: "STRASSEPROJNR",
  },
  {
    title: t("domain.report.projektuebersichtMs.sortEnum.fbProjektnummer"),
    value: "FBPROJNR",
  },
];

const formatOptions = [
  {
    title: t("domain.report.projektuebersichtMs.type.pdf"),
    value: "PDF",
  },
  {
    title: t("domain.report.projektuebersichtMs.type.excel"),
    value: "EXCEL",
  },
];
</script>
