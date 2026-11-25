<template>
  <v-form
    ref="form"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col
        cols="12"
        md="3"
      >
        <fm-text-field
          v-model="modelValue.parameters!.jahr"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportVerwendungsnachweisDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="jahr"
          :label="t('domain.report.verwendungsnachweis.jahr')"
          density="comfortable"
        />
      </v-col>
      <v-col
        cols="12"
        md="3"
      >
        <fm-text-field
          v-model="modelValue.parameters!.monat"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportVerwendungsnachweisDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="monat"
          :label="t('domain.report.verwendungsnachweis.monat')"
          density="comfortable"
      /></v-col>
    </v-row>
    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <fm-text-field
          v-model="modelValue.parameters!.pname"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportVerwendungsnachweisDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="pname"
          :label="t('domain.report.common.pname')"
          density="comfortable"
        />
      </v-col>

      <v-col
        cols="12"
        md="6"
      >
        <fm-text-field
          v-model="modelValue.parameters!.pstrasse"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportVerwendungsnachweisDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="pstrasse"
          :label="t('domain.report.common.pstrasse')"
          density="comfortable"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="12"
        md="3"
      >
        <fm-checkbox
          v-model="modelValue.parameters!.fag"
          :label="t('domain.report.verwendungsnachweis.fag')"
          true-value="1"
          false-value="0"
          density="compact"
        />
      </v-col>

      <v-col
        cols="12"
        md="3"
      >
        <fm-checkbox
          v-model="modelValue.parameters!.notizen"
          :label="t('domain.report.verwendungsnachweis.notizen')"
          true-value="1"
          false-value="0"
          density="compact"
        />
      </v-col>

      <v-col
        cols="12"
        md="3"
      >
        <fm-checkbox
          v-model="modelValue.parameters!.win"
          :label="t('domain.report.verwendungsnachweis.win')"
          true-value="1"
          false-value="0"
          density="compact"
        />
      </v-col>
      <v-col
        cols="12"
        md="3"
      >
        <fm-checkbox
          v-model="modelValue.parameters!.ohneschluss"
          :label="t('domain.report.verwendungsnachweis.ohneschluss')"
          true-value="1"
          false-value="0"
          density="compact"
        />
      </v-col>
    </v-row>

    <v-row>
      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.sbl"
          :items="props.reportVerwendungsnachweisFormContext?.sbls ?? []"
          :item-title="getStadtbezirkslisteTitle"
          item-value="kurzbez"
          :validation-attribute-map="
            ReportVerwendungsnachweisDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="sbl"
          :label="t('domain.report.common.sbl')"
          density="comfortable"
          @update:model-value="delete modelValue.parameters!.bez"
        />
      </v-col>

      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.bez"
          :items="props.reportVerwendungsnachweisFormContext?.bezs ?? []"
          :item-title="getStadtbezirkTitle"
          item-value="stadtbezirk"
          :validation-attribute-map="
            ReportVerwendungsnachweisDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="bez"
          :label="t('domain.report.common.bez')"
          density="comfortable"
          @update:model-value="delete modelValue.parameters!.sbl"
        />
      </v-col>

      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.fb"
          :items="props.reportVerwendungsnachweisFormContext?.fbs ?? []"
          :item-title="getFoerderbereichTitle"
          item-value="fb"
          :validation-attribute-map="
            ReportVerwendungsnachweisDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="fb"
          :label="t('domain.report.common.fb')"
          density="comfortable"
        />
      </v-col>

      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.ua"
          :items="props.reportVerwendungsnachweisFormContext?.uas ?? []"
          :item-title="getUnterabschnittTitle"
          item-value="ua"
          :validation-attribute-map="
            ReportVerwendungsnachweisDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="ua"
          :label="t('domain.report.common.ua')"
          density="comfortable"
        />
      </v-col>
    </v-row>

    <v-row>
      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.kurz"
          :items="props.reportVerwendungsnachweisFormContext?.kurzs ?? []"
          :item-title="getKurzbezeichnungTitle"
          item-value="kurzbez"
          :validation-attribute-map="
            ReportVerwendungsnachweisDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="kurz"
          :label="t('domain.report.common.kurz')"
          density="comfortable"
        />
      </v-col>

      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.sgt"
          :items="props.reportVerwendungsnachweisFormContext?.sgts ?? []"
          :item-title="getSiedlungsgebietTitle"
          item-value="siedlungsgebiet"
          :validation-attribute-map="
            ReportVerwendungsnachweisDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="sgt"
          :label="t('domain.report.common.sgt')"
          density="comfortable"
        />
      </v-col>

      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.bpg"
          :items="props.reportVerwendungsnachweisFormContext?.bpgs ?? []"
          :item-title="getBauprogrammTitle"
          item-value="bauprogramm"
          :validation-attribute-map="
            ReportVerwendungsnachweisDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="bpg"
          :label="t('domain.report.common.bpg')"
          density="comfortable"
        />
      </v-col>

      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.krh"
          :items="props.reportVerwendungsnachweisFormContext?.krhs ?? []"
          :item-title="getKrankenhausTitle"
          item-value="krankenhaus"
          :validation-attribute-map="
            ReportVerwendungsnachweisDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="krh"
          :label="t('domain.report.verwendungsnachweis.krh')"
          density="comfortable"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.krisofp"
          :items="props.reportVerwendungsnachweisFormContext?.krisofps ?? []"
          :item-title="getKrisofpTitle"
          :item-value="getKrisofpValue"
          :validation-attribute-map="
            ReportVerwendungsnachweisDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="krisofp"
          :label="t('domain.report.common.krisofp')"
          density="comfortable"
          clearable
        />
      </v-col>
      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.sort"
          :items="sortOptions"
          item-value="value"
          item-title="title"
          :label="t('domain.report.common.reportSort')"
          density="comfortable"
        />
      </v-col>

      <v-col
        cols="12"
        md="3"
      >
        <v-select
          v-model="modelValue.parameters!.type"
          :items="formatOptions"
          item-value="value"
          label="Format"
          variant="outlined"
          density="comfortable"
        />
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type {
  BauprogrammFormContextDTO,
  FoerderbereichFormContextDTO,
  GetReportVerwendungsnachweisRequest,
  KrankenhausFormContextDTO,
  KurzbezeichnungFormContextDTO,
  ListennameStadtbezirkslisteFormContextDTO,
  ReportVerwendungsnachweisFormContext,
  SiedlungsgebietFormContextDTO,
  StadtbezirkFormContextDTO,
  UnterabschnittFormContextDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import {
  ReportVerwendungsnachweisDTOKrisofpEnum,
  ReportVerwendungsnachweisDTOPropertyValidationAttributesMap,
} from "@/api/generated/foerdermittel-backend";
import FmAutocomplete from "@/components/common/FmAutocomplete.vue";
import FmCheckbox from "@/components/common/FmCheckbox.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode.ts";
import { getKrisofpTitle } from "@/util/krisofpUtils.ts";

const { t } = useI18n();

const modelValue = defineModel<Partial<GetReportVerwendungsnachweisRequest>>({
  required: true,
});

const props = defineProps<{
  reportVerwendungsnachweisFormContext?: DeepReadonly<ReportVerwendungsnachweisFormContext>;
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

function getFoerderbereichTitle(item: FoerderbereichFormContextDTO) {
  return item ? `${item.fb} (${item.bezeichnung})` : "";
}

function getStadtbezirkslisteTitle(
  item: ListennameStadtbezirkslisteFormContextDTO
) {
  return item ? `${item.kurzbez} (${item.bezeichnung})` : "";
}
function getStadtbezirkTitle(item: StadtbezirkFormContextDTO) {
  return item ? `${item.stadtbezirk} (${item.bezeichnung})` : "";
}

function getUnterabschnittTitle(item: UnterabschnittFormContextDTO) {
  return item ? `${item.ua} (${item.bezeichnung})` : "";
}

function getKurzbezeichnungTitle(item: KurzbezeichnungFormContextDTO) {
  return item ? `${item.kurzbez ?? ""} (${item.bezeichnung})` : "";
}

function getSiedlungsgebietTitle(item: SiedlungsgebietFormContextDTO) {
  return item ? `${item.siedlungsgebiet ?? ""} (${item.bezeichnung})` : "";
}

function getBauprogrammTitle(item: BauprogrammFormContextDTO) {
  return item ? `${item.bauprogramm} (${item.bezeichnung})` : "";
}

function getKrankenhausTitle(item: KrankenhausFormContextDTO) {
  return item ? `${item.krhname} (${item.bezeichnung})` : "";
}

function getKrisofpValue(
  item: ReportVerwendungsnachweisDTOKrisofpEnum
): string {
  return item;
}

const sortOptions = [
  {
    title: t("domain.report.verwendungsnachweis.sortEnum.projektnummer"),
    value: "PROJEKTNUMMER",
  },
  {
    title: t("domain.report.verwendungsnachweis.sortEnum.strasseProjektnummer"),
    value: "STRASSENAME",
  },
  {
    title: t("domain.report.verwendungsnachweis.sortEnum.fbProjektnummer"),
    value: "FBPROJNR",
  },
  {
    title: t("domain.report.verwendungsnachweis.sortEnum.fbProjektstrasse"),
    value: "FBSTRASSE",
  },
];

const formatOptions = [
  {
    title: t("domain.report.verwendungsnachweis.type.pdf"),
    value: "PDF",
  },
  {
    title: t("domain.report.verwendungsnachweis.type.pdfFlat"),
    value: "PDF_FLAT",
  },
];
</script>
