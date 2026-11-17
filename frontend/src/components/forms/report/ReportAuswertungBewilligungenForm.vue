<template>
  <v-form
    ref="form"
    :disabled="!reportauswertungbewilligungenformcontext"
    @update:model-value="onValidityChanged"
  >
    <v-row align="center">
      <v-col
        cols="12"
        md="2"
      >
        <fm-text-field
          v-model="modelValue.parameters!.jahrVon"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportAuswertungBewilligungenDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="jahrVon"
          :label="t('domain.report.common.jahrVon')"
          :error-messages="jahrVonError"
          density="comfortable"
        />
      </v-col>
      <v-col
        cols="12"
        md="2"
      >
        <fm-text-field
          v-model="modelValue.parameters!.jahrBis"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportAuswertungBewilligungenDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="jahrBis"
          :label="t('domain.report.common.jahrBis')"
          :error-messages="jahrBisError"
          density="comfortable"
        />
      </v-col>
      <v-col
          cols="12"
          md="1"
      >
        <fm-text-field
            v-model="modelValue.parameters!.monat"
            :display-mode="InputDisplayMode.EDIT"
            :label="t('domain.report.common.monat')"
            :validation-attribute-map="
            ReportAuswertungBewilligungenDTOPropertyValidationAttributesMap
            "
            validation-attribute-key="monat"
            density="comfortable"
        />
      </v-col>
      <v-col
          cols="12"
          md="1"
      >
        <fm-date-input :label="t('domain.report.auswertungBewilligungen.datum')">
          v-model="modelValue.parameters!.datum"
          :display-mode="displayMode"
          clearable
        </fm-date-input>
      </v-col>
      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.sbl"
          :items="reportauswertungbewilligungenformcontext?.sbls ?? []"
          :item-title="getStadtbezirkslisteTitle"
          item-value="kurzbez"
          :validation-attribute-map="
            ReportAuswertungBewilligungenDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="kurzbez"
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
          :items="reportauswertungbewilligungenformcontext?.bezs ?? []"
          :item-title="getStadtbezirkTitle"
          item-value="stadtbezirk"
          :validation-attribute-map="
            ReportAuswertungBewilligungenDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="stadtbezirk"
          :label="t('domain.report.common.bez')"
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
          :items="reportauswertungbewilligungenformcontext?.fbs ?? []"
          :item-title="getFoerderbereichTitle"
          item-value="fb"
          :validation-attribute-map="
            ReportAuswertungBewilligungenDTOPropertyValidationAttributesMap
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
          :items="reportauswertungbewilligungenformcontext?.uas ?? []"
          :item-title="getUnterabschnittTitle"
          item-value="ua"
          :validation-attribute-map="
            ReportAuswertungBewilligungenDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="ua"
          :label="t('domain.report.common.ua')"
          density="comfortable"
        />
      </v-col>
      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.kurz"
          :items="reportauswertungbewilligungenformcontext?.kurzs ?? []"
          :item-title="getKurzbezeichnungTitle"
          item-value="kurzbez"
          :validation-attribute-map="
            ReportAuswertungBewilligungenDTOPropertyValidationAttributesMap
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
          v-model="modelValue.parameters!.projnr"
          :items="reportauswertungbewilligungenformcontext?.projs"
          :item-title="getProjektTitle"
          item-value="projnr"
          :validation-attribute-map="
            ReportAuswertungBewilligungenDTOPropertyValidationAttributesMap
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
            ReportAuswertungBewilligungenDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="pstrasse"
          :label="t('domain.report.common.pstrasse')"
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
            ReportAuswertungBewilligungenDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="pname"
          :label="t('domain.report.common.pname')"
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
          v-model="modelValue.parameters!.erfolgt"
          :label="t('domain.report.common.erfolgt')"
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
          :label="t('domain.report.common.fag')"
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
          :label="t('domain.report.common.notizen')"
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
          :items="reportauswertungbewilligungenformcontext?.krisofps"
          :item-title="getKrisofpTitle"
          :item-value="getKrisofpValue"
          :validation-attribute-map="
            ReportAuswertungBewilligungenDTOPropertyValidationAttributesMap
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
          v-model="modelValue.parameters!.zuwendungsart"
          :items="reportauswertungbewilligungenformcontext?.zuwendungen"
          :item-title="getBewilligungenTitle"
          :item-value="getBewilligungenValue"
          :validation-attribute-map="
            ReportAuswertungBewilligungenDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="bewilligungen"
          :label="t('domain.report.common.zuwendungsart')"
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
          :items="reportauswertungbewilligungenformcontext?.sgts ?? []"
          :item-title="getSiedlungsgebietTitle"
          item-value="siedlungsgebiet"
          :validation-attribute-map="
            ReportAuswertungBewilligungenDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="siedlungsgebiet"
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
          :items="reportauswertungbewilligungenformcontext?.bpgs ?? []"
          :item-title="getBauprogrammTitle"
          item-value="bauprogramm"
          :validation-attribute-map="
            ReportAuswertungBewilligungenDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="bauprogramm"
          :label="t('domain.report.common.bpg')"
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
  GetReportAuswertungBewilligungenRequest,
  KurzbezeichnungFormContextDTO,
  ListennameStadtbezirkslisteFormContextDTO,
  ReportAuswertungBewilligungenFormContext,
  ReportAuswertungBewilligungenFormContextKrisofpsEnum,
  ReportAuswertungBewilligungenFormContextZuwendungenEnum,
  ReportAuswertungBewilligungFormContextDTO,
  SiedlungsgebietFormContextDTO,
  StadtbezirkFormContextDTO,
  UnterabschnittFormContextDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { computed, useTemplateRef, watch } from "vue";
import { useI18n } from "vue-i18n";

import { ReportAuswertungBewilligungenDTOPropertyValidationAttributesMap } from "@/api/generated/foerdermittel-backend";
import FmAutocomplete from "@/components/common/FmAutocomplete.vue";
import FmCheckbox from "@/components/common/FmCheckbox.vue";
import FmDateInput from "@/components/common/FmDateInput.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";
import { getBewilligungenTitle } from "@/util/bewilligungenUtils.ts";
import { getKrisofpTitle } from "@/util/krisofpUtils";
import { getJahrBisError, getJahrVonError } from "@/util/yearHelper.ts";

const { t } = useI18n();

const modelValue = defineModel<
  Partial<GetReportAuswertungBewilligungenRequest>
>({
  required: true,
});

const { reportauswertungbewilligungenformcontext } = defineProps<{
  reportauswertungbewilligungenformcontext?: DeepReadonly<ReportAuswertungBewilligungenFormContext>;
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
  item: ReportAuswertungBewilligungenFormContextKrisofpsEnum
): string {
  return item;
}

function getBewilligungenValue(
  item: ReportAuswertungBewilligungenFormContextZuwendungenEnum
): string {
  return item;
}

function getSiedlungsgebietTitle(item: SiedlungsgebietFormContextDTO) {
  return item ? `${item.siedlungsgebiet} (${item.bezeichnung})` : "";
}

function getProjektTitle(item: ReportAuswertungBewilligungFormContextDTO) {
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
      parameters.sort = "FOERDERBEREICH_PROJEKTNUMMER_BEWILLIGTAM";
    }
  }
);

const sortOptions = [
  {
    title: t(
      "domain.report.auswertungBewilligungen.sortEnum.fbProjektnummerBewilligtAm"
    ),
    value: "FOERDERBEREICH_PROJEKTNUMMER_BEWILLIGTAM",
  },
  {
    title: t(
      "domain.report.auswertungBewilligungen.sortEnum.fbProjektstrasseProjektNummer"
    ),
    value: "FOERDERBEREICH_STRASSE_PROJEKTNUMMER",
  },
  {
    title: t(
      "domain.report.auswertungBewilligungen.sortEnum.projektnummerBewilligtAm"
    ),
    value: "PROJEKTNUMMER_BEWILLIGTAM",
  },
  {
    title: t(
      "domain.report.auswertungBewilligungen.sortEnum.strasseProjektnummerBewilligtAm"
    ),
    value: "PROJEKTSTRASSE_PROJEKTNUMMER_BEWILLIGTAM",
  },
  {
    title: t("domain.report.auswertungBewilligungen.sortEnum.bewilligtAm"),
    value: "BEWILLIGTAM",
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
    modelValue.value.parameters?.jahrVon,
    modelValue.value.parameters?.jahrBis
  )
);

const jahrBisError = computed(() =>
  getJahrBisError(
    modelValue.value.parameters?.jahrVon,
    modelValue.value.parameters?.jahrBis
  )
);
</script>
