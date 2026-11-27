<template>
  <v-form
    ref="form"
    :disabled="!reportauswertungarchivformcontext"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col
        cols="12"
        sm="6"
        lg="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.bez"
          :items="reportauswertungarchivformcontext?.bezs ?? []"
          :item-title="getStadtbezirkTitle"
          item-value="stadtbezirk"
          :validation-attribute-map="
            ReportAuswertungArchivDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="stadtbezirk"
          :label="t('domain.report.auswertungArchiv.bez')"
          density="comfortable"
          clearable
        />
      </v-col>

      <v-col
        cols="12"
        sm="6"
        lg="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.fb"
          :items="reportauswertungarchivformcontext?.fbs ?? []"
          :item-title="getFoerderbereichTitle"
          item-value="fb"
          :validation-attribute-map="
            ReportAuswertungArchivDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="fb"
          :label="t('domain.report.common.fb')"
          density="comfortable"
          clearable
        />
      </v-col>

      <v-col
        cols="12"
        sm="6"
        lg="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.ua"
          :items="reportauswertungarchivformcontext?.uas ?? []"
          :item-title="getUnterabschnittTitle"
          item-value="ua"
          :validation-attribute-map="
            ReportAuswertungArchivDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="ua"
          :label="t('domain.report.common.ua')"
          density="comfortable"
          clearable
        />
      </v-col>

      <v-col
        cols="12"
        sm="6"
        lg="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.kurz"
          :items="reportauswertungarchivformcontext?.kurzs ?? []"
          :item-title="getKurzbezeichnungTitle"
          item-value="kurzbez"
          :validation-attribute-map="
            ReportAuswertungArchivDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="kurz"
          :label="t('domain.report.common.kurz')"
          density="comfortable"
          clearable
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="12"
        sm="6"
        lg="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.projnr"
          :items="reportauswertungarchivformcontext?.projs"
          :item-title="getProjektTitle"
          item-value="projnr"
          :validation-attribute-map="
            ReportAuswertungArchivDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="projnr"
          :label="t('model.projekt.projnr')"
          density="comfortable"
          clearable
        />
      </v-col>

      <v-col
        cols="12"
        sm="6"
        lg="3"
      >
        <fm-text-field
          v-model="modelValue.parameters!.pname"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportAuswertungArchivDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="pname"
          :label="t('domain.report.common.pname')"
          density="comfortable"
          clearable
        />
      </v-col>

      <v-col
        cols="12"
        sm="6"
        lg="3"
      >
        <fm-text-field
          v-model="modelValue.parameters!.pstrasse"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportAuswertungArchivDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="pstrasse"
          :label="t('domain.report.common.pstrasse')"
          density="comfortable"
          clearable
        />
      </v-col>

      <v-col
        cols="12"
        sm="6"
        lg="3"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.krisofp"
          :items="reportauswertungarchivformcontext?.krisofps"
          :item-title="getKrisofpTitle"
          :item-value="getKrisofpValue"
          :validation-attribute-map="
            ReportAuswertungArchivDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="krisofp"
          :label="t('domain.report.common.krisofp')"
          density="comfortable"
          clearable
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="12"
        md="4"
      >
        <fm-checkbox
          v-model="modelValue.parameters!.film"
          :label="t('domain.report.auswertungArchiv.film')"
          density="compact"
          clearable
        />
      </v-col>

      <v-col
        cols="12"
        md="4"
      >
        <fm-checkbox
          v-model="modelValue.parameters!.filmohne"
          :label="t('domain.report.auswertungArchiv.filmohne')"
          density="compact"
          clearable
        />
      </v-col>

      <v-col
        cols="12"
        md="4"
      >
        <fm-checkbox
          v-model="modelValue.parameters!.offen"
          :label="t('domain.report.auswertungArchiv.offen')"
          density="compact"
          clearable
        />
      </v-col>
    </v-row>

    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <fm-date-input
          v-model="modelValue.parameters!.planab"
          :validation-attribute-map="
            ReportAuswertungArchivDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="planab"
          :label="t('domain.report.auswertungArchiv.planab')"
          density="comfortable"
          clearable
        />
      </v-col>

      <v-col
        cols="12"
        md="6"
      >
        <fm-date-input
          v-model="modelValue.parameters!.spseit"
          :validation-attribute-map="
            ReportAuswertungArchivDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="spseit"
          :label="t('domain.report.auswertungArchiv.spseit')"
          density="comfortable"
          clearable
        />
      </v-col>
    </v-row>
    <v-row align="center">
      <v-col
        cols="12"
        md="4"
      >
        <fm-checkbox
          v-model="modelValue.parameters!.op"
          :label="t('domain.report.auswertungArchiv.op')"
          density="compact"
          clearable
        />
      </v-col>

      <v-col
        cols="12"
        md="4"
      >
        <fm-checkbox
          v-model="modelValue.parameters!.mp"
          :label="t('domain.report.auswertungArchiv.mp')"
          true-value="1"
          false-value="0"
          density="compact"
          clearable
        />
      </v-col>

      <v-col
        cols="12"
        md="4"
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
  GetReportAuswertungArchivRequest,
  KurzbezeichnungFormContextDTO,
  ReportAuswertungAntragFormContextDTO,
  ReportAuswertungArchivFormContext,
  ReportAuswertungArchivFormContextKrisofpsEnum,
  StadtbezirkFormContextDTO,
  UnterabschnittFormContextDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import { ReportAuswertungArchivDTOPropertyValidationAttributesMap } from "@/api/generated/foerdermittel-backend";
import FmAutocomplete from "@/components/common/FmAutocomplete.vue";
import FmCheckbox from "@/components/common/FmCheckbox.vue";
import FmDateInput from "@/components/common/FmDateInput.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";
import { getKrisofpTitle } from "@/util/krisofpUtils";

const { t } = useI18n();

const modelValue = defineModel<Partial<GetReportAuswertungArchivRequest>>({
  required: true,
});

const { reportauswertungarchivformcontext } = defineProps<{
  reportauswertungarchivformcontext?: DeepReadonly<ReportAuswertungArchivFormContext>;
}>();

const emit = defineEmits<{
  isValid: [boolean | null];
}>();

function onValidityChanged(newIsValid: boolean | null) {
  emit("isValid", newIsValid);
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
  item: ReportAuswertungArchivFormContextKrisofpsEnum
): string {
  return item;
}

function getProjektTitle(item: ReportAuswertungAntragFormContextDTO) {
  return item ? `${item.projnr} (${item.pname})` : "";
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
    title: t("domain.report.auswertungArchiv.sortEnum.strasseProjektnummer"),
    value: "STRASSE_PROJEKTNUMMER",
  },
  {
    title: t("domain.report.auswertungArchiv.sortEnum.fbStrasse"),
    value: "FOERDERBEREICH_STRASSE",
  },
  {
    title: t("domain.report.auswertungArchiv.sortEnum.projektnummer"),
    value: "PROJEKTNUMMER",
  },
  {
    title: t("domain.report.auswertungArchiv.sortEnum.notiz"),
    value: "NOTIZ",
  },
];
</script>
