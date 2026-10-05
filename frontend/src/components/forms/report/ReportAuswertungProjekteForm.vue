<template>
  <v-form
      ref="form"
      @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col cols="12">
        <!-- Projektjahr -->
        <fm-autocomplete
            v-model="modelValue.parameters!.jahr"
            :items="jahrOptions"
            item-title="title"
            item-value="value"
            :label="t('model.projekt.jahr')"
        />

        <!-- Stadtbezirk -->
        <fm-autocomplete
            v-model="modelValue.parameters!.bez"
            :items="props.reportAuswertungProjektFormContext?.bezs ?? []"
            :item-title="getStadtbezirkTitle"
            item-value="stadtbezirk"
            :validation-attribute-map="
            ReportAuswertungProjekteDTOPropertyValidationAttributesMap
          "
            validation-attribute-key="bez"
            :label="t('model.stadtbezirk.modelName')"
        />

        <!-- Stadtbezirksliste -->
        <fm-autocomplete
            v-model="modelValue.parameters!.sbl"
            :items="props.reportAuswertungProjektFormContext?.sbls ?? []"
            :item-title="getStadtbezirkslisteTitle"
            item-value="kurzbez"
            :validation-attribute-map="
            ReportAuswertungProjekteDTOPropertyValidationAttributesMap
          "
            validation-attribute-key="sbl"
            :label="t('model.stadtbezirksliste.modelName')"
        />

        <!-- Förderbereich -->
        <fm-autocomplete
            v-model="modelValue.parameters!.fb"
            :items="props.reportAuswertungProjektFormContext?.fbs ?? []"
            :item-title="getFoerderbereichTitle"
            item-value="fb"
            :validation-attribute-map="
            ReportAuswertungProjekteDTOPropertyValidationAttributesMap
          "
            validation-attribute-key="fb"
            :label="t('model.foerderbereich.modelName')"
        />

        <!-- Unterabschnitt -->
        <fm-autocomplete
            v-model="modelValue.parameters!.ua"
            :items="props.reportAuswertungProjektFormContext?.uas ?? []"
            :item-title="getUnterabschnittTitle"
            item-value="ua"
            :validation-attribute-map="
            ReportAuswertungProjekteDTOPropertyValidationAttributesMap
          "
            validation-attribute-key="ua"
            :label="t('model.unterabschnitt.modelName')"
        />

        <!-- Kurzbezeichnung -->
        <fm-autocomplete
            v-model="modelValue.parameters!.kurz"
            :items="props.reportAuswertungProjektFormContext?.kurzs ?? []"
            :item-title="getKurzbezeichnungTitle"
            item-value="kurzbez"
            :validation-attribute-map="
            ReportAuswertungProjekteDTOPropertyValidationAttributesMap
          "
            validation-attribute-key="kurz"
            :label="t('model.kurzbezeichnung.modelName')"
        />

        <!-- Projektname -->
        <fm-text-field
            v-model="modelValue.parameters!.pname"
            :display-mode="InputDisplayMode.EDIT"
            :validation-attribute-map="
            ReportAuswertungProjekteDTOPropertyValidationAttributesMap
          "
            validation-attribute-key="pname"
            :label="t('model.projekt.pname')"
        />

        <!-- Straße -->
        <fm-text-field
            v-model="modelValue.parameters!.pstrasse"
            :display-mode="InputDisplayMode.EDIT"
            :validation-attribute-map="
            ReportAuswertungProjekteDTOPropertyValidationAttributesMap
          "
            validation-attribute-key="pstrasse"
            :label="t('model.projekt.pstrasse')"
        />

        <!-- Zusätzliche Filter -->
        <v-row class="mt-2">
          <v-col cols="12">
            <v-row>
              <fm-checkbox
                  v-model="modelValue.parameters!.offen"
                  :label="t('domain.report.auswertungProjekt.offen')"
                  true-value="1"
                  false-value="0"
              />

              <fm-checkbox
                  v-model="modelValue.parameters!.kauf"
                  :label="t('domain.report.auswertungProjekt.kauf')"
                  true-value="1"
                  false-value="0"
              />

              <fm-checkbox
                  v-model="modelValue.parameters!.relevant"
                  :label="t('domain.report.auswertungProjekt.relevant')"
                  true-value="1"
                  false-value="0"
              />
            </v-row>
          </v-col>
        </v-row>
        <fm-autocomplete
            v-model="modelValue.parameters!.krisofp"
            :items="props.reportAuswertungProjektFormContext?.krisofps ?? []"
            :item-title="getKrisofpTitle"
            :item-value="getKrisofpValue"
            clearable
            :validation-attribute-map="
        ReportAuswertungProjekteDTOPropertyValidationAttributesMap
    "
            validation-attribute-key="krisofp"
            :label="t('model.projekt.krisofp')"
        />

        <!-- Siedlungsgebiet -->
        <fm-autocomplete
            v-model="modelValue.parameters!.sgt"
            :items="props.reportAuswertungProjektFormContext?.sgts ?? []"
            :item-title="getSiedlungsgebietTitle"
            item-value="siedlungsgebiet"
            :validation-attribute-map="
            ReportAuswertungProjekteDTOPropertyValidationAttributesMap
          "
            validation-attribute-key="sgt"
            :label="t('model.siedlungsgebiet.modelName')"
        />

        <!-- Bauprogramm -->
        <fm-autocomplete
            v-model="modelValue.parameters!.bpg"
            :items="props.reportAuswertungProjektFormContext?.bpgs ?? []"
            :item-title="getBauprogrammTitle"
            item-value="bauprogramm"
            :validation-attribute-map="
            ReportAuswertungProjekteDTOPropertyValidationAttributesMap
          "
            validation-attribute-key="bpg"
            :label="t('model.bauprogramm.modelName')"
        />

        <!-- Sortierung -->
        <fm-autocomplete
            v-model="modelValue.parameters!.sort"
            :items="sortOptions"
            item-value="value"
            item-title="title"
            :label="t('domain.report.auswertungProjekt.sortierung')"
        />

      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type {
  GetReportAuswertungProjektRequest,
  ReportAuswertungProjektFormContext,
  FoerderbereichFormContextDTO,
  ListennameStadtbezirkslisteFormContextDTO,
  StadtbezirkFormContextDTO,
  UnterabschnittFormContextDTO,
  KurzbezeichnungFormContextDTO,
  BauprogrammFormContextDTO,
  SiedlungsgebietFormContextDTO,
  Krisofp,
} from "@/api/generated/foerdermittel-backend";

import type { VForm } from "vuetify/components";

import {
  computed,
  type DeepReadonly,
  useTemplateRef,
} from "vue";

import { useI18n } from "vue-i18n";

import {
  ReportAuswertungProjekteDTOPropertyValidationAttributesMap,
} from "@/api/generated/foerdermittel-backend";

import FmAutocomplete from "@/components/common/FmAutocomplete.vue";
import FmCheckbox from "@/components/common/FmCheckbox.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();

const modelValue =
    defineModel<Partial<GetReportAuswertungProjektRequest>>({
      required: true,
    });

const props = defineProps<{
  reportAuswertungProjektFormContext?: DeepReadonly<
      ReportAuswertungProjektFormContext
  >;
}>();

const emit = defineEmits<{
  isValid: [boolean | null];
}>();

const formRef = useTemplateRef<VForm>("form");

function onValidityChanged(newIsValid: boolean | null) {
  emit("isValid", newIsValid);
}

async function validate() {
  if (formRef.value) {
    await formRef.value.validate();
  }
}

defineExpose({
  validate,
});

/**
 * Projektjahre aus dem FormContext.
 *
 * Beispiel:
 * 24, 25, 26
 */
const jahrOptions = computed(() => {
  const projekte =
      props.reportAuswertungProjektFormContext?.projekte ?? [];

  return [...new Set(
      projekte
          .map((projekt) => projekt.jahr)
          .filter((jahr): jahr is string => !!jahr)
          .map((jahr) =>
              jahr.length === 2 ? `20${jahr}` : jahr,
          ),
  )].map((jahr) => ({
    title: jahr,
    value: jahr,
  }));
});


const krisofpBezeichnungen = new Map<string, string>([
  ["X", "1.+2. SIP - KriSoFö 2008-2014"],
  ["Y", "3. SIP - Kinderbetreuung 2015-2019"],
  ["Z", "4. SIP - Kinderbetreuung 2017-2020/2021"],
  ["W", "5. SIP - Schulkindbetreuung Land (Hortförd. GS)"],
  ["V", "Schulkindbetreuung - Bund (GaFöG)"],
  ["K", "KJP II"],
  ["F", "FAGplus15"],
  ["S", "Startchancen-Programm (SCP)"],
]);

function getKrisofpTitle(item: Krisofp) {
  return `${item} (${krisofpBezeichnungen.get(item) ?? item})`;
}

function getKrisofpValue(item: Krisofp) {
  return item;
}


/**
 * Förderbereich.
 */
function getFoerderbereichTitle(
    item: FoerderbereichFormContextDTO,
) {
  return item
      ? `${item.fb} (${item.bezeichnung})`
      : "";
}

/**
 * Stadtbezirksliste.
 */
function getStadtbezirkslisteTitle(
    item: ListennameStadtbezirkslisteFormContextDTO,
) {
  return item
      ? `${item.kurzbez} (${item.bezeichnung})`
      : "";
}

/**
 * Stadtbezirk.
 */
function getStadtbezirkTitle(
    item: StadtbezirkFormContextDTO,
) {
  return item
      ? `${item.stadtbezirk} (${item.bezeichnung})`
      : "";
}

/**
 * Unterabschnitt.
 */
function getUnterabschnittTitle(
    item: UnterabschnittFormContextDTO,
) {
  return item
      ? `${item.ua} (${item.bezeichnung})`
      : "";
}

/**
 * Kurzbezeichnung.
 */
function getKurzbezeichnungTitle(
    item: KurzbezeichnungFormContextDTO,
) {
  return item
      ? `${item.kurzbez ?? ""} (${item.bezeichnung})`
      : "";
}

/**
 * Siedlungsgebiet.
 */
function getSiedlungsgebietTitle(
    item: SiedlungsgebietFormContextDTO,
) {
  return item
      ? `${item.siedlungsgebiet ?? ""} (${item.bezeichnung})`
      : "";
}

/**
 * Bauprogramm.
 */
function getBauprogrammTitle(
    item: BauprogrammFormContextDTO,
) {
  return item
      ? `${item.bauprogramm} (${item.bezeichnung})`
      : "";
}

/**
 * Sortierung.
 */
const sortOptions = computed(() => [
  {
    title: t(
        "domain.report.auswertungProjekt.sortEnum.stadtbezirkStrasse",
    ),
    value: "STADTBEZIRK_STRASSE",
  },
  {
    title: t(
        "domain.report.auswertungProjekt.sortEnum.projektnummer",
    ),
    value: "PROJEKTNUMMER",
  },
  {
    title: t(
        "domain.report.auswertungProjekt.sortEnum.strasseProjektnummer",
    ),
    value: "STRASSE_PROJEKTNUMMER",
  },
  {
    title: t(
        "domain.report.auswertungProjekt.sortEnum.foerderbereichStrasse",
    ),
    value: "FOERDERBEREICH_STRASSE",
  },
  {
    title: t(
        "domain.report.auswertungProjekt.sortEnum.foerderbereichProjektnummer",
    ),
    value: "FOERDERBEREICH_PROJEKTNUMMER",
  },
]);
</script>
