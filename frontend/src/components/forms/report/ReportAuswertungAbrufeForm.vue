<template>
  <v-form
    ref="form"
    :disabled="!reportauswertungabrufeformcontext"
    @update:model-value="onValidityChanged"
  >
    <v-row align="center">
      <v-col
        cols="12"
        md="3"
      >
        <fm-text-field
          v-model="modelValue.parameters!.jahrAbrufVon"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportAuswertungAbrufeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="jahrAbrufVon"
          :label="t('domain.report.common.jahrAbrufVon')"
          :error-messages="jahrAbrufVonError"
          density="comfortable"
        />
      </v-col>
      <v-col
        cols="12"
        md="3"
      >
        <fm-text-field
          v-model="modelValue.parameters!.jahrAbrufBis"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportAuswertungAbrufeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="jahrAbrufBis"
          :label="t('domain.report.common.jahrAbrufBis')"
          :error-messages="jahrAbrufBisError"
          density="comfortable"
        />
      </v-col>
      <v-col
        cols="12"
        md="3"
      >
        <fm-text-field
          v-model="modelValue.parameters!.jahrErhaltVon"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportAuswertungAbrufeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="jahrErhaltVon"
          :label="t('domain.report.common.jahrErhaltVon')"
          :error-messages="jahrErhaltVonError"
          density="comfortable"
        />
      </v-col>
      <v-col
        cols="12"
        md="3"
      >
        <fm-text-field
          v-model="modelValue.parameters!.jahrErhaltBis"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportAuswertungAbrufeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="jahrErhaltBis"
          :label="t('domain.report.common.jahrErhaltBis')"
          :error-messages="jahrErhaltBisError"
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
          v-model="modelValue.parameters!.sbl"
          :items="reportauswertungabrufeformcontext?.sbls ?? []"
          :item-title="getStadtbezirkslisteTitle"
          item-value="kurzbez"
          :validation-attribute-map="
            ReportAuswertungAbrufeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="kurzbez"
          :label="t('domain.report.common.sbl')"
          density="comfortable"
          clearable
          @update:model-value="delete modelValue.parameters!.bez"
        />
      </v-col>

      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.bez"
          :items="reportauswertungabrufeformcontext?.bezs ?? []"
          :item-title="getStadtbezirkTitle"
          item-value="stadtbezirk"
          :validation-attribute-map="
            ReportAuswertungAbrufeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="stadtbezirk"
          :label="t('domain.report.common.bez')"
          density="comfortable"
          clearable
          @update:model-value="delete modelValue.parameters!.sbl"
        />
      </v-col>
      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.fb"
          :items="reportauswertungabrufeformcontext?.fbs ?? []"
          :item-title="getFoerderbereichTitle"
          item-value="fb"
          :validation-attribute-map="
            ReportAuswertungAbrufeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="fb"
          :label="t('domain.report.common.fb')"
          density="comfortable"
          clearable
        />
      </v-col>
      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.ua"
          :items="reportauswertungabrufeformcontext?.uas ?? []"
          :item-title="getUnterabschnittTitle"
          item-value="ua"
          :validation-attribute-map="
            ReportAuswertungAbrufeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="ua"
          :label="t('domain.report.common.ua')"
          density="comfortable"
          clearable
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
          :items="reportauswertungabrufeformcontext?.kurzs ?? []"
          :item-title="getKurzbezeichnungTitle"
          item-value="kurzbez"
          :validation-attribute-map="
            ReportAuswertungAbrufeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="kurz"
          :label="t('domain.report.common.kurz')"
          density="comfortable"
          clearable
        />
      </v-col>
      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.projnr"
          :items="reportauswertungabrufeformcontext?.projs"
          :item-title="getProjektTitle"
          item-value="projnr"
          :validation-attribute-map="
            ReportAuswertungAbrufeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="projnr"
          :label="t('model.projekt.projnr')"
          density="comfortable"
          clearable
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
            ReportAuswertungAbrufeDTOPropertyValidationAttributesMap
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
        md="6"
      >
        <fm-text-field
          v-model="modelValue.parameters!.pname"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportAuswertungAbrufeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="pname"
          :label="t('domain.report.common.pname')"
          density="comfortable"
        />
      </v-col>
      <v-col
        cols="12"
        md="1"
      >
        <fm-checkbox
          v-model="modelValue.parameters!.fag"
          :label="t('domain.report.common.fag')"
          true-value="1"
          false-value="0"
          density="compact"
          clearable
        />
      </v-col>

      <v-col
        cols="12"
        md="2"
      >
        <fm-checkbox
          v-model="modelValue.parameters!.offen"
          :label="t('domain.report.common.offen')"
          true-value="1"
          false-value="0"
          density="compact"
          clearable
        />
      </v-col>

      <v-col
        cols="12"
        md="3"
      >
        <fm-checkbox
          v-model="modelValue.parameters!.ohnebewill"
          :label="t('domain.report.common.ohnebewill')"
          true-value="1"
          false-value="0"
          density="compact"
          clearable
        />
      </v-col>

      <v-col
        cols="12"
        md="3"
      >
        <fm-checkbox
          v-model="modelValue.parameters!.erh"
          :label="t('domain.report.common.erh')"
          true-value="1"
          false-value="0"
          density="compact"
          clearable
        />
      </v-col>
      <v-col
        cols="12"
        md="3"
      >
        <fm-checkbox
          v-model="modelValue.parameters!.nichterh"
          :label="t('domain.report.common.nichtErh')"
          true-value="1"
          false-value="0"
          density="compact"
          clearable
        />
      </v-col>
      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.ref"
          :items="reportauswertungabrufeformcontext?.refs"
          :item-title="getReferatTitle"
          item-value="refnr"
          :validation-attribute-map="
            ReportAuswertungAbrufeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="ref"
          :label="t('domain.report.common.ref')"
          density="comfortable"
          clearable
        />
      </v-col>
      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.krh"
          :items="reportauswertungabrufeformcontext?.krhs"
          :item-title="getKrankenhausTitle"
          item-value="krhname"
          :validation-attribute-map="
            ReportAuswertungAbrufeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="krh"
          :label="t('domain.report.common.krh')"
          density="comfortable"
          clearable
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
          :items="reportauswertungabrufeformcontext?.krisofps"
          :item-title="getKrisofpTitle"
          :item-value="getKrisofpValue"
          :validation-attribute-map="
            ReportAuswertungAbrufeDTOPropertyValidationAttributesMap
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
          v-model="modelValue.parameters!.sgt"
          :items="reportauswertungabrufeformcontext?.sgts ?? []"
          :item-title="getSiedlungsgebietTitle"
          item-value="siedlungsgebiet"
          :validation-attribute-map="
            ReportAuswertungAbrufeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="siedlungsgebiet"
          :label="t('domain.report.common.sgt')"
          density="comfortable"
          clearable
        />
      </v-col>

      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.bpg"
          :items="reportauswertungabrufeformcontext?.bpgs ?? []"
          :item-title="getBauprogrammTitle"
          item-value="bauprogramm"
          :validation-attribute-map="
            ReportAuswertungAbrufeDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="bauprogramm"
          :label="t('domain.report.common.bpg')"
          density="comfortable"
          clearable
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
  GetReportAuswertungAbrufeRequest,
  KrankenhausFormContextDTO,
  KurzbezeichnungFormContextDTO,
  ListennameStadtbezirkslisteFormContextDTO,
  ReferatFormContextDTO,
  ReportAuswertungAbrufeFormContext,
  ReportAuswertungAbrufeFormContextKrisofpsEnum,
  ReportAuswertungAntragFormContextDTO,
  SiedlungsgebietFormContextDTO,
  StadtbezirkFormContextDTO,
  UnterabschnittFormContextDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { computed, useTemplateRef, watch } from "vue";
import { useI18n } from "vue-i18n";

import { ReportAuswertungAbrufeDTOPropertyValidationAttributesMap } from "@/api/generated/foerdermittel-backend";
import FmAutocomplete from "@/components/common/FmAutocomplete.vue";
import FmCheckbox from "@/components/common/FmCheckbox.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";
import { getKrisofpTitle } from "@/util/krisofpUtils";
import { getJahrBisError, getJahrVonError } from "@/util/yearHelper.ts";

const { t } = useI18n();

const modelValue = defineModel<Partial<GetReportAuswertungAbrufeRequest>>({
  required: true,
});

const { reportauswertungabrufeformcontext } = defineProps<{
  reportauswertungabrufeformcontext?: DeepReadonly<ReportAuswertungAbrufeFormContext>;
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
  item: ReportAuswertungAbrufeFormContextKrisofpsEnum
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

function getReferatTitle(item: ReferatFormContextDTO) {
  return item ? `${item.refnr} (${item.bezeichnung})` : "";
}

function getKrankenhausTitle(item: KrankenhausFormContextDTO) {
  return item ? `${item.krhname} (${item.bezeichnung})` : "";
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
    return sortOptions.slice(0, 4);
  }

  return sortOptions;
});

watch(
  () => modelValue.value.parameters?.type,
  (type) => {
    const parameters = modelValue.value.parameters;

    if (type === "PDF" && parameters) {
      parameters.sort = "FOERDERBEREICH_PROJEKTNUMMER_ADATUM";
    }
  }
);

const sortOptions = [
  {
    title: t("domain.report.auswertungAbrufe.sortEnum.fbProjektnummerAdatum"),
    value: "FOERDERBEREICH_PROJEKTNUMMER_ADATUM",
  },
  {
    title: t("domain.report.auswertungAbrufe.sortEnum.fbEdatum"),
    value: "FOERDERBEREICH_EDATUM",
  },
  {
    title: t("domain.report.auswertungAbrufe.sortEnum.fbAdatum"),
    value: " FOERDERBEREICH_ADATUM",
  },
  {
    title: t(
      "domain.report.auswertungAbrufe.sortEnum.fbProjektnummerStrasseAdatum"
    ),
    value: "FOERDERBEREICH_PROJEKTSTRASSE_ADATUM",
  },
  {
    title: t("domain.report.auswertungAbrufe.sortEnum.projektnummerAdatum"),
    value: "PROJEKTNUMMER_ADATUM",
  },
  {
    title: t("domain.report.auswertungAbrufe.sortEnum.edatum"),
    value: "EDATUM",
  },
  {
    title: t("domain.report.auswertungAbrufe.sortEnum.adatum"),
    value: "ADATUM",
  },
  {
    title: t("domain.report.auswertungAbrufe.sortEnum.projektstrasse"),
    value: "PROJEKTSTRASSE",
  },
];

const formatOptions = [
  {
    title: t("domain.report.auswertungAbrufe.reportType.pdf"),
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

const jahrAbrufVonError = computed(() =>
  getJahrVonError(
    modelValue.value.parameters?.jahrAbrufVon,
    modelValue.value.parameters?.jahrAbrufBis
  )
);

const jahrAbrufBisError = computed(() =>
  getJahrBisError(
    modelValue.value.parameters?.jahrAbrufVon,
    modelValue.value.parameters?.jahrAbrufBis
  )
);
const jahrErhaltVonError = computed(() =>
  getJahrVonError(
    modelValue.value.parameters?.jahrErhaltVon,
    modelValue.value.parameters?.jahrErhaltBis
  )
);

const jahrErhaltBisError = computed(() =>
  getJahrBisError(
    modelValue.value.parameters?.jahrErhaltVon,
    modelValue.value.parameters?.jahrErhaltBis
  )
);
</script>
