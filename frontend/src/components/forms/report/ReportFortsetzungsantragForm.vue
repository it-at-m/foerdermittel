<template>
  <v-form
    ref="form"
    :disabled="!reportFortsetzungsantragFormContext"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.sbl"
          :items="reportFortsetzungsantragFormContext?.sbls"
          :item-title="getStadtbezirkslisteTitle"
          item-value="kurzbez"
          :validation-attribute-map="
            ReportFortsetzungsantragDTOPropertyValidationAttributesMap
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
          :items="reportFortsetzungsantragFormContext?.bezs"
          :item-title="getStadtbezirkTitle"
          item-value="stadtbezirk"
          :validation-attribute-map="
            ReportFortsetzungsantragDTOPropertyValidationAttributesMap
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
        md="3"
      >
        <v-checkbox
          v-model="modelValue.parameters!.fag"
          item-value="hh"
          :label="t('domain.report.fortsetzungsAntrag.fag')"
          true-value="1"
          false-value="0"
        />
      </v-col>
      <v-col
        cols="12"
        md="3"
      >
        <v-checkbox
          v-model="modelValue.parameters!.ofPro"
          item-value="hh"
          :label="t('domain.report.fortsetzungsAntrag.ofPro')"
          true-value="1"
          false-value="0"
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
  GetReportFortsetzungsantragRequest,
  ListennameStadtbezirkslisteFormContextDTO,
  ReportFortsetzungsantragFormContext,
  StadtbezirkFormContextDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import { ReportFortsetzungsantragDTOPropertyValidationAttributesMap } from "@/api/generated/foerdermittel-backend";
import FmAutocomplete from "@/components/common/FmAutocomplete.vue";

const { t } = useI18n();

const modelValue = defineModel<Partial<GetReportFortsetzungsantragRequest>>({
  required: true,
});

const { reportFortsetzungsantragFormContext } = defineProps<{
  reportFortsetzungsantragFormContext?: DeepReadonly<ReportFortsetzungsantragFormContext>;
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

function getStadtbezirkslisteTitle(
  item: ListennameStadtbezirkslisteFormContextDTO
) {
  return item ? `${item.kurzbez} (${item.bezeichnung})` : "";
}
function getStadtbezirkTitle(item: StadtbezirkFormContextDTO) {
  return item ? `${item.stadtbezirk} (${item.bezeichnung})` : "";
}

const formatOptions = [
  {
    title: t("domain.report.fortsetzungsAntrag.type.pdf"),
    value: "PDF",
  },
  {
    title: t("domain.report.fortsetzungsAntrag.type.excel"),
    value: "EXCEL",
  },
];
</script>
