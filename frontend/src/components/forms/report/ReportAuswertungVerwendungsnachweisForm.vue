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
        <fm-autocomplete
          v-model="modelValue.parameters!.jahr"
          :items="jahrOptions"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportAuswertungVerwendungsnachweisDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="jahr"
          :label="t('domain.report.auswertungVerwendungsnachweis.jahr')"
          density="comfortable"
        />
      </v-col>
      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.monat"
          :items="monatOptions"
          :validation-attribute-map="
            ReportAuswertungVerwendungsnachweisDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="monat"
          :label="t('domain.report.auswertungVerwendungsnachweis.monat')"
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
            ReportAuswertungVerwendungsnachweisDTOPropertyValidationAttributesMap
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
            ReportAuswertungVerwendungsnachweisDTOPropertyValidationAttributesMap
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
          :label="t('domain.report.auswertungVerwendungsnachweis.fag')"
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
          :label="t('domain.report.auswertungVerwendungsnachweis.notizen')"
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
          :label="t('domain.report.auswertungVerwendungsnachweis.win')"
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
          :label="t('domain.report.auswertungVerwendungsnachweis.ohneschluss')"
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
          :items="
            props.reportAuswertungVerwendungsnachweisFormContext?.sbls ?? []
          "
          :item-title="getStadtbezirkslisteTitle"
          item-value="kurzbez"
          :validation-attribute-map="
            ReportAuswertungVerwendungsnachweisDTOPropertyValidationAttributesMap
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
          :items="
            props.reportAuswertungVerwendungsnachweisFormContext?.bezs ?? []
          "
          :item-title="getStadtbezirkTitle"
          item-value="stadtbezirk"
          :validation-attribute-map="
            ReportAuswertungVerwendungsnachweisDTOPropertyValidationAttributesMap
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
          :items="
            props.reportAuswertungVerwendungsnachweisFormContext?.fbs ?? []
          "
          :item-title="getFoerderbereichTitle"
          item-value="fb"
          :validation-attribute-map="
            ReportAuswertungVerwendungsnachweisDTOPropertyValidationAttributesMap
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
          :items="
            props.reportAuswertungVerwendungsnachweisFormContext?.uas ?? []
          "
          :item-title="getUnterabschnittTitle"
          item-value="ua"
          :validation-attribute-map="
            ReportAuswertungVerwendungsnachweisDTOPropertyValidationAttributesMap
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
          :items="
            props.reportAuswertungVerwendungsnachweisFormContext?.kurzs ?? []
          "
          :item-title="getKurzbezeichnungTitle"
          item-value="kurzbez"
          :validation-attribute-map="
            ReportAuswertungVerwendungsnachweisDTOPropertyValidationAttributesMap
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
          :items="
            props.reportAuswertungVerwendungsnachweisFormContext?.sgts ?? []
          "
          :item-title="getSiedlungsgebietTitle"
          item-value="siedlungsgebiet"
          :validation-attribute-map="
            ReportAuswertungVerwendungsnachweisDTOPropertyValidationAttributesMap
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
          :items="
            props.reportAuswertungVerwendungsnachweisFormContext?.bpgs ?? []
          "
          :item-title="getBauprogrammTitle"
          item-value="bauprogramm"
          :validation-attribute-map="
            ReportAuswertungVerwendungsnachweisDTOPropertyValidationAttributesMap
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
          :items="
            props.reportAuswertungVerwendungsnachweisFormContext?.krhs ?? []
          "
          :item-title="getKrankenhausTitle"
          item-value="krhname"
          :validation-attribute-map="
            ReportAuswertungVerwendungsnachweisDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="krh"
          :label="t('domain.report.auswertungVerwendungsnachweis.krh')"
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
          :items="
            props.reportAuswertungVerwendungsnachweisFormContext?.krisofps ?? []
          "
          :item-title="getKrisofpTitle"
          :item-value="getKrisofpValue"
          :validation-attribute-map="
            ReportAuswertungVerwendungsnachweisDTOPropertyValidationAttributesMap
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
        <v-select
          v-model="modelValue.parameters!.type"
          :items="formatOptions"
          item-title="title"
          item-value="value"
          :label="t('domain.report.common.reportType.modelName')"
          variant="outlined"
          density="comfortable"
        />
      </v-col>
      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.sort"
          :items="filteredSortOptions"
          item-value="value"
          item-title="title"
          :label="t('domain.report.common.reportSort')"
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
  GetReportAuswertungVerwendungsnachweisRequest,
  KrankenhausFormContextDTO,
  KurzbezeichnungFormContextDTO,
  ListennameStadtbezirkslisteFormContextDTO,
  ReportAuswertungVerwendungsnachweisFormContext,
  SiedlungsgebietFormContextDTO,
  StadtbezirkFormContextDTO,
  UnterabschnittFormContextDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { computed, useTemplateRef, watch } from "vue";
import { useI18n } from "vue-i18n";

import {
  ReportAuswertungVerwendungsnachweisDTOKrisofpEnum,
  ReportAuswertungVerwendungsnachweisDTOPropertyValidationAttributesMap,
} from "@/api/generated/foerdermittel-backend";
import FmAutocomplete from "@/components/common/FmAutocomplete.vue";
import FmCheckbox from "@/components/common/FmCheckbox.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode.ts";
import { getKrisofpTitle } from "@/util/krisofpUtils.ts";

const { t } = useI18n();

const modelValue = defineModel<
  Partial<GetReportAuswertungVerwendungsnachweisRequest>
>({
  required: true,
});

const props = defineProps<{
  reportAuswertungVerwendungsnachweisFormContext?: DeepReadonly<ReportAuswertungVerwendungsnachweisFormContext>;
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
  item: ReportAuswertungVerwendungsnachweisDTOKrisofpEnum
): string {
  return item;
}

const filteredSortOptions = computed(() => {
  if (modelValue.value.parameters?.type === "PDF") {
    return sortOptions.slice(0, 2);
  }

  return sortOptions;
});

watch(
  () => modelValue.value.parameters?.type,
  (type) => {
    const parameters = modelValue.value.parameters;

    if (type === "PDF" && parameters) {
      parameters.sort = "FOERDERBEREICH_PROJEKTNUMMER";
    }
  }
);

const sortOptions = [
  {
    title: t(
      "domain.report.auswertungVerwendungsnachweis.sortEnum.fbProjektnummer"
    ),
    value: "FOERDERBEREICH_PROJEKTNUMMER",
  },
  {
    title: t(
      "domain.report.auswertungVerwendungsnachweis.sortEnum.fbProjektstrasse"
    ),
    value: "FOERDERBEREICH_STRASSE",
  },
  {
    title: t(
      "domain.report.auswertungVerwendungsnachweis.sortEnum.strasseProjektnummer"
    ),
    value: "PROJEKTSTRASSE_PROJEKTNUMMER",
  },
  {
    title: t(
      "domain.report.auswertungVerwendungsnachweis.sortEnum.projektnummer"
    ),
    value: "PROJEKTNUMMER",
  },
];

const formatOptions = [
  {
    title: t("domain.report.auswertungVerwendungsnachweis.reportType.pdf"),
    value: "PDF",
  },
  {
    title: t("domain.report.auswertungVerwendungsnachweis.reportType.pdfFlat"),
    value: "PDF_FLAT",
  },
];

const jahrOptions = computed(() =>
  [
    ...new Set(
      (props.reportAuswertungVerwendungsnachweisFormContext?.projekte ?? [])
        .map((projekt) => projekt.vndat?.getFullYear())
        .filter((jahr): jahr is number => jahr !== undefined)
    ),
  ].sort((a, b) => b - a)
);

const monatOptions = computed(() =>
  [
    ...new Set(
      (props.reportAuswertungVerwendungsnachweisFormContext?.projekte ?? [])
        .map((projekt) => projekt.vndat?.getMonth())
        .filter((monat): monat is number => monat !== undefined)
        .map((monat) => monat + 1)
    ),
  ].sort((a, b) => a - b)
);
</script>
