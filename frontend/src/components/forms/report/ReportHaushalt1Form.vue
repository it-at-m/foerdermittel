<template>
  <v-form
    ref="form"
    :disabled="!reportHaushalt1FormContext"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.haushaltsjahr"
          :items="haushaltsjahre"
          :item-title="getHaushaltsjahrTitle"
          item-value="haushaltsjahr"
          :validation-attribute-map="
            ReportHaushalt1DTOPropertyValidationAttributesMap
          "
          validation-attribute-key="haushaltsjahr"
          :label="t('model.haushaltsjahr.modelName')"
        />
      </v-col>

      <v-col
        cols="12"
        md="6"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.fb"
          :items="reportHaushalt1FormContext?.fbs"
          :item-title="getFoerderbereichTitle"
          item-value="fb"
          :validation-attribute-map="
            ReportHaushalt1DTOPropertyValidationAttributesMap
          "
          validation-attribute-key="foerderbereich"
          :label="t('model.foerderbereich.modelName')"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.sbl"
          :items="reportHaushalt1FormContext?.sbls"
          :item-title="getStadtbezirkslisteTitle"
          item-value="kurzbez"
          :validation-attribute-map="
            ReportHaushalt1DTOPropertyValidationAttributesMap
          "
          validation-attribute-key="kurzbez"
          :label="t('model.stadtbezirksliste.modelName')"
          @update:model-value="delete modelValue.parameters!.bez"
        />
      </v-col>
      <v-col
        cols="12"
        md="6"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.bez"
          :items="reportHaushalt1FormContext?.bezs"
          :item-title="getStadtbezirkTitle"
          item-value="stadtbezirk"
          :validation-attribute-map="
            ReportHaushalt1DTOPropertyValidationAttributesMap
          "
          validation-attribute-key="kurzbez"
          :label="t('model.stadtbezirk.modelName')"
          @update:model-value="delete modelValue.parameters!.sbl"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.fipo"
          :items="gefilterteFipos"
          :item-title="getFipoTitle"
          item-value="fipo"
          :validation-attribute-map="
            ReportHaushalt1DTOPropertyValidationAttributesMap
          "
          validation-attribute-key="fipo"
          :label="t('model.fipo.modelName')"
        />
      </v-col>
      <v-col
        cols="12"
        md="6"
      >
        <v-checkbox
          v-model="modelValue.parameters!.hh"
          item-value="hh"
          :label="t('domain.report.haushalt1.hh')"
          true-value="1"
          false-value="0"
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
    <v-row>
      <v-col cols="12">
        <v-select
          v-model="modelValue.parameters!.type"
          :items="formatOptions"
          item-value="value"
          label="Format"
          variant="outlined"
        />
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type {
  FoerderbereichFormContextDTO,
  GetReportHaushalt1Request,
  HhplanFormContextDTO,
  ListennameStadtbezirkslisteFormContextDTO,
  ReportHaushalt1FormContext,
  StadtbezirkFormContextDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import { ReportHaushalt1DTOPropertyValidationAttributesMap } from "@/api/generated/foerdermittel-backend";
import FmAutocomplete from "@/components/common/FmAutocomplete.vue";

const { t } = useI18n();

const modelValue = defineModel<Partial<GetReportHaushalt1Request>>({
  required: true,
});

const { reportHaushalt1FormContext } = defineProps<{
  reportHaushalt1FormContext?: DeepReadonly<ReportHaushalt1FormContext>;
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

function getFipoTitle(item: HhplanFormContextDTO) {
  return item ? `${item.fipo}` : "";
}
const gefilterteFipos = computed(() => {
  const fipos = reportHaushalt1FormContext?.fipos ?? [];
  const haushaltsjahr = modelValue.value.parameters?.haushaltsjahr;

  const gefiltert = !haushaltsjahr
    ? fipos
    : fipos.filter((item) => item.hhjJahr.toString() === haushaltsjahr);

  return [...new Map(gefiltert.map((item) => [item.fipo, item])).values()];
});

function getHaushaltsjahrTitle(item: HhplanFormContextDTO) {
  return item ? `${item.hhjJahr}` : "";
}

const haushaltsjahre = computed(() => {
  const fipos = reportHaushalt1FormContext?.fipos ?? [];

  return Array.from(
    new Map(fipos.map((item) => [item.hhjJahr, item])).values()
  );
});

const sortOptions = [
  {
    title: t("domain.report.haushalt1.sortEnum.projektnummer"),
    value: "PROJEKTNUMMER",
  },
  {
    title: t("domain.report.haushalt1.sortEnum.strasse"),
    value: "STRASSE",
  },
  {
    title: t("domain.report.haushalt1.sortEnum.fipo"),
    value: "FIPO",
  },
  {
    title: t("domain.report.haushalt1.sortEnum.fbProjektnummer"),
    value: "FB_PROJEKTNUMMER",
  },
  {
    title: t("domain.report.haushalt1.sortEnum.fbStrasseProjektnummer"),
    value: "FB_STRASSE_PROJEKTNUMMER",
  },
  {
    title: t("domain.report.haushalt1.sortEnum.fbFipo"),
    value: "FB_FIPO",
  },
];

const formatOptions = [
  {
    title: t("domain.report.haushalt1.type.pdf"),
    value: "PDF",
  },
  {
    title: t("domain.report.haushalt1.type.pdfFlat"),
    value: "PDF_FLAT",
  },
  {
    title: t("domain.report.haushalt1.type.excel"),
    value: "EXCEL",
  },
];
</script>
