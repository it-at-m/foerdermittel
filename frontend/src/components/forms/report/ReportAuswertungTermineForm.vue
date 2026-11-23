<template>
  <v-form
    ref="form"
    :disabled="!reportauswertungtermineformcontext"
    @update:model-value="onValidityChanged"
  >
    <v-row align="center">
      <v-col
        cols="12"
        md="3"
      >
        <fm-number-input
          v-model="modelValue.parameters!.jahr"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportAuswertungTermineDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="jahr"
          :label="t('domain.report.auswertungTermine.jahr')"
          density="comfortable"
          control-variant="hidden"
          clearable
        />
      </v-col>
      <v-col
        cols="12"
        md="3"
      >
        <fm-number-input
          v-model="modelValue.parameters!.monat"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportAuswertungTermineDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="monat"
          :label="t('domain.report.common.monat')"
          density="comfortable"
          control-variant="hidden"
          clearable
        />
      </v-col>

      <v-col
        cols="12"
        md="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.sbl"
          :items="reportauswertungtermineformcontext?.sbls ?? []"
          :item-title="getStadtbezirkslisteTitle"
          item-value="kurzbez"
          :validation-attribute-map="
            ReportAuswertungTermineDTOPropertyValidationAttributesMap
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
          :items="reportauswertungtermineformcontext?.bezs ?? []"
          :item-title="getStadtbezirkTitle"
          item-value="stadtbezirk"
          :validation-attribute-map="
            ReportAuswertungTermineDTOPropertyValidationAttributesMap
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
          :items="reportauswertungtermineformcontext?.fbs ?? []"
          :item-title="getFoerderbereichTitle"
          item-value="fb"
          :validation-attribute-map="
            ReportAuswertungTermineDTOPropertyValidationAttributesMap
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
          :items="reportauswertungtermineformcontext?.uas ?? []"
          :item-title="getUnterabschnittTitle"
          item-value="ua"
          :validation-attribute-map="
            ReportAuswertungTermineDTOPropertyValidationAttributesMap
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
          :items="reportauswertungtermineformcontext?.kurzs ?? []"
          :item-title="getKurzbezeichnungTitle"
          item-value="kurzbez"
          :validation-attribute-map="
            ReportAuswertungTermineDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="kurz"
          :label="t('domain.report.common.kurz')"
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
            ReportAuswertungTermineDTOPropertyValidationAttributesMap
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
            ReportAuswertungTermineDTOPropertyValidationAttributesMap
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
          v-model="modelValue.parameters!.zukunft"
          :label="t('domain.report.auswertungTermine.zukunft')"
          density="compact"
        />
      </v-col>

      <v-col
        cols="12"
        md="3"
      >
        <fm-checkbox
          v-model="modelValue.parameters!.ueb"
          :label="t('domain.report.auswertungTermine.ueb')"
          density="compact"
        />
      </v-col>
    </v-row>

    <v-row>
      <v-col
        cols="12"
        md="3"
      >
        <v-select
          v-model="modelValue.parameters!.sort"
          :items="sortOptions"
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
  FoerderbereichFormContextDTO,
  GetReportAuswertungTermineRequest,
  KurzbezeichnungFormContextDTO,
  ListennameStadtbezirkslisteFormContextDTO,
  ReportAuswertungTermineFormContext,
  StadtbezirkFormContextDTO,
  UnterabschnittFormContextDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import { ReportAuswertungTermineDTOPropertyValidationAttributesMap } from "@/api/generated/foerdermittel-backend";
import FmAutocomplete from "@/components/common/FmAutocomplete.vue";
import FmCheckbox from "@/components/common/FmCheckbox.vue";
import FmNumberInput from "@/components/common/FmNumberInput.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();

const modelValue = defineModel<Partial<GetReportAuswertungTermineRequest>>({
  required: true,
});

const { reportauswertungtermineformcontext } = defineProps<{
  reportauswertungtermineformcontext?: DeepReadonly<ReportAuswertungTermineFormContext>;
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
    title: t("domain.report.auswertungTermine.sortEnum.projektnummer"),
    value: "PROJEKTNUMMER",
  },
  {
    title: t("domain.report.auswertungTermine.sortEnum.termine"),
    value: "TERMINE",
  },
];
</script>
