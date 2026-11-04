<template>
  <v-form
    ref="form"
    :disabled="!reportauswertungantraegeformcontext"
    @update:model-value="onValidityChanged"
  >
    <v-row align="center">
      <v-col
        cols="12"
        md="3"
      >
        <fm-text-field
          v-model="modelValue.parameters!.jahrvon"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportAuswertungAntraegeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="jahrVon"
          :label="t('domain.report.staedtebaufoerderung.jahrVon')"
          :error-messages="jahrVonError"
          density="comfortable"
        />
      </v-col>
      <v-col
        cols="12"
        md="3"
      >
        <fm-text-field
          v-model="modelValue.parameters!.jahrbis"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportAuswertungAntragFormContextDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="jahrBis"
          :label="t('domain.report.staedtebaufoerderung.jahrBis')"
          :error-messages="jahrBisError"
          density="comfortable"
        />
      </v-col>

      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.sbl"
          :items="reportauswertungantraegeformcontext?.sbls ?? []"
          :item-title="getStadtbezirkslisteTitle"
          item-value="kurzbez"
          :validation-attribute-map="
            ReportAuswertungAntraegeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="kurzbez"
          :label="t('domain.report.auswertungAntraege.sbl')"
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
          :items="reportauswertungantraegeformcontext?.bezs ?? []"
          :item-title="getStadtbezirkTitle"
          item-value="stadtbezirk"
          :validation-attribute-map="
            ReportAuswertungAntraegeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="stadtbezirk"
          :label="t('domain.report.auswertungAntraege.bez')"
          density="comfortable"
          @update:model-value="delete modelValue.parameters!.sbl"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.fb"
          :items="reportauswertungantraegeformcontext?.fbs ?? []"
          :item-title="getFoerderbereichTitle"
          item-value="fb"
          :validation-attribute-map="
            ReportAuswertungAntraegeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="fb"
          :label="t('domain.report.projektUebersichtMs.fb')"
          density="comfortable"
        />
      </v-col>
      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.ua"
          :items="reportauswertungantraegeformcontext?.uas ?? []"
          :item-title="getUnterabschnittTitle"
          item-value="ua"
          :validation-attribute-map="
            ReportAuswertungAntraegeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="ua"
          :label="t('domain.report.auswertungAntraege.ua')"
          density="comfortable"
        />
      </v-col>
      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.kurz"
          :items="reportauswertungantraegeformcontext?.kurzs ?? []"
          :item-title="getKurzbezeichnungTitle"
          item-value="kurzbez"
          :validation-attribute-map="
            ReportAuswertungAntraegeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="kurz"
          :label="t('domain.report.projektUebersichtMs.kurz')"
          density="comfortable"
        />
      </v-col>
      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.projnr"
          :items="reportauswertungantraegeformcontext?.projs"
          :item-title="getProjektTitle"
          item-value="projnr"
          :validation-attribute-map="
            ReportAuswertungAntraegeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="projnr"
          :label="t('model.projekt.projnr')"
          density="comfortable"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <fm-text-field
          v-model="modelValue.parameters!.pstrasse"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportAuswertungAntraegeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="pstrasse"
          :label="t('domain.report.auswertungAntraege.pstrasse')"
          density="comfortable"
        />
      </v-col>

      <v-col
        cols="12"
        md="6"
      >
        <fm-text-field
          v-model="modelValue.parameters!.pname"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportAuswertungAntraegeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="pname"
          :label="t('domain.report.auswertungAntraege.pname')"
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
          v-model="modelValue.parameters!.offen"
          :label="t('domain.report.auswertungAntraege.offen')"
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
          v-model="modelValue.parameters!.fag"
          :label="t('domain.report.auswertungAntraege.fag')"
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
          v-model="modelValue.parameters!.relevant"
          :label="t('domain.report.auswertungAntraege.relevant')"
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
          v-model="modelValue.parameters!.krisofp"
          :items="reportauswertungantraegeformcontext?.krisofps"
          :item-title="getKrisofpTitle"
          :item-value="getKrisofpValue"
          :validation-attribute-map="
            ReportAuswertungAntraegeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="krisofp"
          :label="t('model.projekt.krisofp')"
          density="comfortable"
          clearable
        />
      </v-col>

      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.atyp"
          :items="reportauswertungantraegeformcontext?.antraege"
          :item-title="getAntraegeTitle"
          :item-value="getAntraegeValue"
          :validation-attribute-map="
            ReportAuswertungAntraegeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="antraege"
          :label="t('domain.report.auswertungAntraege.atyp')"
          density="comfortable"
          clearable
        />
      </v-col>

      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.sgt"
          :items="reportauswertungantraegeformcontext?.sgts ?? []"
          :item-title="getSiedlungsgebietTitle"
          item-value="siedlungsgebiet"
          :validation-attribute-map="
            ReportAuswertungAntraegeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="siedlungsgebiet"
          :label="t('domain.report.auswertungAntraege.sgt')"
          density="comfortable"
        />
      </v-col>

      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.bpg"
          :items="reportauswertungantraegeformcontext?.bpgs ?? []"
          :item-title="getBauprogrammTitle"
          item-value="bauprogramm"
          :validation-attribute-map="
            ReportAuswertungAntraegeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="bauprogramm"
          :label="t('domain.report.auswertungAntraege.bpg')"
          density="comfortable"
        />
      </v-col>
    </v-row>

    <v-row>
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
        <v-select
          v-model="modelValue.parameters!.sort"
          :items="filteredSortOptions"
          item-title="title"
          item-value="value"
          :label="t('domain.report.common.reportSort')"
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
  GetReportAuswertungAntraegeRequest,
  KurzbezeichnungFormContextDTO,
  ListennameStadtbezirkslisteFormContextDTO,
  ReportAuswertungAntraegeFormContext,
  ReportAuswertungAntraegeFormContextAntraegeEnum,
  ReportAuswertungAntraegeFormContextKrisofpsEnum,
  ReportAuswertungAntragFormContextDTO,
  SiedlungsgebietFormContextDTO,
  StadtbezirkFormContextDTO,
  UnterabschnittFormContextDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { computed, useTemplateRef, watch } from "vue";
import { useI18n } from "vue-i18n";

import {
  ReportAuswertungAntraegeDTOPropertyValidationAttributesMap,
  ReportAuswertungAntragFormContextDTOPropertyValidationAttributesMap,
} from "@/api/generated/foerdermittel-backend";
import FmAutocomplete from "@/components/common/FmAutocomplete.vue";
import FmCheckbox from "@/components/common/FmCheckbox.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";
import { getAntraegeTitle } from "@/util/antraegeUtils.ts";
import { getKrisofpTitle } from "@/util/krisofpUtils";
import { getJahrBisError, getJahrVonError } from "@/util/yearHelper.ts";

const { t } = useI18n();

const modelValue = defineModel<Partial<GetReportAuswertungAntraegeRequest>>({
  required: true,
});

const { reportauswertungantraegeformcontext } = defineProps<{
  reportauswertungantraegeformcontext?: DeepReadonly<ReportAuswertungAntraegeFormContext>;
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
  item: ReportAuswertungAntraegeFormContextKrisofpsEnum
): string {
  return item;
}

function getAntraegeValue(
  item: ReportAuswertungAntraegeFormContextAntraegeEnum
): string {
  return item;
}

function getSiedlungsgebietTitle(item: SiedlungsgebietFormContextDTO) {
  return item ? `${item.siedlungsgebiet} (${item.bezeichnung})` : "";
}

function getProjektTitle(item: ReportAuswertungAntragFormContextDTO) {
  return item ? `${item.projnr} (${item.pname})` : "";
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
    title: t("domain.report.auswertungAntraege.sortEnum.fbProjektnummer"),
    value: "FOERDERBEREICH_PROJEKTNUMMER",
  },
  {
    title: t("domain.report.auswertungAntraege.sortEnum.fbProjektstrasse"),
    value: "FOERDERBEREICH_STRASSE",
  },
  {
    title: t("domain.report.auswertungAntraege.sortEnum.strasseProjektnummer"),
    value: "PROJEKTSTRASSE_PROJEKTNUMMER",
  },
  {
    title: t("domain.report.auswertungAntraege.sortEnum.projektnummer"),
    value: "PROJEKTNUMMER",
  },
];

const formatOptions = [
  {
    title: t("domain.report.auswertungAntraege.reportType.pdf"),
    value: "PDF",
  },
  {
    title: t("domain.report.common.reportType.pdfFlat"),
    value: "PDF_FLAT",
  },
  {
    title: t("domain.report.common.reportType.excel"),
    value: "EXCEL",
  },
];

const jahrVonError = computed(() =>
  getJahrVonError(
    modelValue.value.parameters?.jahrvon,
    modelValue.value.parameters?.jahrbis
  )
);

const jahrBisError = computed(() =>
  getJahrBisError(
    modelValue.value.parameters?.jahrvon,
    modelValue.value.parameters?.jahrbis
  )
);
</script>
