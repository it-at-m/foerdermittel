<template>
  <v-form
    ref="form"
    :readonly="displayMode === InputDisplayMode.READ"
    @update:model-value="onValidityChanged"
  >
    <!-- Projektnummer -->
    <v-row>
      <v-col
        cols="12"
        md="3"
      >
        <fm-text-field
          :model-value="projnr"
          :label="t('model.projekt.projnr')"
          :display-mode="displayMode"
          readonly
        />
      </v-col>
    </v-row>

    <!-- Bestandteile der Projektnummer -->
    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <fm-autocomplete
          v-model="modelValue.uasUa"
          :items="projektFormContext.uas"
          item-value="ua"
          :item-title="getUnterabschnittTitle"
          :label="t('model.projekt.uasUa')"
          :readonly="displayMode !== InputDisplayMode.CREATE"
          :validation-attribute-map="
            ProjektCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="uasUa"
          clearable
        />
      </v-col>

      <v-col
        cols="12"
        md="2"
      >
        <fm-text-field
          :model-value="modelValue.jahr?.toString()"
          :display-mode="displayMode"
          :label="t('model.projekt.jahr')"
          :validation-attribute-map="
            ProjektCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="jahr"
          :readonly="displayMode !== InputDisplayMode.CREATE"
          @update:model-value="updateNumberField('jahr', $event)"
          @blur="formatJahr"
        />
      </v-col>

      <v-col
        cols="12"
        md="2"
      >
        <fm-text-field
          :model-value="modelValue.lfdnr1?.toString()"
          :display-mode="displayMode"
          :label="t('model.projekt.lfdnr1')"
          :validation-attribute-map="
            ProjektCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="lfdnr1"
          :readonly="displayMode !== InputDisplayMode.CREATE"
          @update:model-value="updateNumberField('lfdnr1', $event)"
        />
      </v-col>

      <v-col
        cols="12"
        md="2"
      >
        <fm-text-field
          :model-value="modelValue.lfdnr2?.toString()"
          :display-mode="displayMode"
          :label="t('model.projekt.lfdnr2')"
          :validation-attribute-map="
            ProjektCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="lfdnr2"
          :readonly="displayMode !== InputDisplayMode.CREATE"
          @update:model-value="updateNumberField('lfdnr2', $event)"
          @blur="formatLfdnr2"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <fm-autocomplete
          v-model="modelValue.fobFb"
          :items="projektFormContext.fbs"
          item-value="fb"
          :item-title="getFoerderbereichTitle"
          :label="t('model.projekt.fobFb')"
          :readonly="displayMode !== InputDisplayMode.CREATE"
          :validation-attribute-map="
            ProjektCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="fobFb"
          clearable
        />
      </v-col>

      <v-col
        cols="12"
        md="6"
      >
        <fm-autocomplete
          v-model="modelValue.kurzbez"
          :items="projektFormContext.kurzs"
          item-value="kurzbez"
          :item-title="getKurzbezeichnungTitle"
          :label="t('model.projekt.kurzbez')"
          :readonly="displayMode !== InputDisplayMode.CREATE"
          :validation-attribute-map="
            ProjektCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="kurzbez"
          clearable
        />
      </v-col>
    </v-row>

    <!-- Projektinformationen -->
    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <fm-text-field
          v-model="modelValue.pname"
          :display-mode="displayMode"
          :label="t('model.projekt.pname')"
          :validation-attribute-map="
            ProjektCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="pname"
        />
      </v-col>

      <v-col
        cols="12"
        md="6"
      >
        <fm-text-field
          v-model="modelValue.pstrasse"
          :display-mode="displayMode"
          :label="t('model.projekt.pstrasse')"
          :validation-attribute-map="
            ProjektCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="pstrasse"
        />
      </v-col>

      <v-col
        cols="12"
        md="6"
      >
        <fm-text-field
          v-model="modelValue.projart"
          :display-mode="displayMode"
          :label="t('model.projekt.projart')"
          :validation-attribute-map="
            ProjektCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="projart"
        />
      </v-col>

      <v-col
        cols="12"
        md="6"
      >
        <fm-text-area
          v-model="modelValue.notizen"
          :display-mode="displayMode"
          :label="t('model.projekt.notizen')"
        />
      </v-col>
    </v-row>

    <!-- Weitere Stammdaten -->
    <v-row>
      <v-col
        cols="12"
        md="4"
      >
        <fm-autocomplete
          v-model="modelValue.stadtbezirk"
          :items="projektFormContext.bezs"
          item-value="stadtbezirk"
          :item-title="getStadtbezirkTitle"
          :label="t('model.projekt.stadtbezirk')"
          :readonly="displayMode !== InputDisplayMode.CREATE"
          :validation-attribute-map="
            ProjektCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="stadtbezirk"
          clearable
        />
      </v-col>

      <v-col
        cols="12"
        md="4"
      >
        <fm-autocomplete
          v-model="modelValue.krhname"
          :items="projektFormContext.krhs"
          item-value="krhname"
          :item-title="getKrankenhausTitle"
          :label="t('model.projekt.krhname')"
          :readonly="displayMode !== InputDisplayMode.CREATE"
          :validation-attribute-map="
            ProjektCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="krhname"
          clearable
        />
      </v-col>

      <v-col
        cols="12"
        md="4"
      >
        <fm-autocomplete
          v-model="modelValue.siedlungsgebiet"
          :items="projektFormContext.sgts"
          item-value="siedlungsgebiet"
          :item-title="getSiedlungsgebietTitle"
          :label="t('model.projekt.siedlungsgebiet')"
          :readonly="displayMode !== InputDisplayMode.CREATE"
          :validation-attribute-map="
            ProjektCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="siedlungsgebiet"
          clearable
        />
      </v-col>

      <v-col
        cols="12"
        md="6"
      >
        <fm-autocomplete
          v-model="modelValue.krisofp"
          :items="projektFormContext?.krisofps ?? []"
          :item-title="getKrisofpTitle"
          :item-value="getKrisofpValue"
          :validation-attribute-map="
            ProjektCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="krisofp"
          :label="t('model.projekt.krisofp')"
          density="comfortable"
          clearable
        />
      </v-col>

      <v-col
        cols="12"
        md="6"
      >
        <fm-autocomplete
          v-model="modelValue.bauprogramm"
          :items="projektFormContext.bpgs"
          item-value="bauprogramm"
          :item-title="getBauprogrammTitle"
          :label="t('model.projekt.bauprogramm')"
          :readonly="displayMode !== InputDisplayMode.CREATE"
          :validation-attribute-map="
            ProjektCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="bauprogramm"
          clearable
        />
      </v-col>

      <v-col
        cols="12"
        md="6"
      >
        <fm-autocomplete
          v-model="modelValue.krhzweck"
          :items="projektFormContext?.krhzwecks ?? []"
          :item-title="getKrhzweckTitle"
          :item-value="getKrhzweckValue"
          :validation-attribute-map="
            ProjektCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="krhzweck"
          :label="t('model.projekt.krhzweck')"
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
        <fm-checkbox
          v-model="modelValue.refinanzierbar"
          :display-mode="displayMode"
          :label="t('model.projekt.refinanzierbar')"
        />
      </v-col>

      <v-col
        cols="12"
        md="3"
      >
        <fm-checkbox
          v-model="modelValue.kauf"
          :display-mode="displayMode"
          :label="t('model.projekt.kauf')"
        />
      </v-col>
      <v-col
        cols="12"
        md="2"
      >
        <fm-number-input
          :v-model="modelValue.kripplatz"
          :display-mode="displayMode"
          :label="t('model.projekt.kripplatz')"
          :validation-attribute-map="
            ProjektCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="kripplatz"
        />
      </v-col>

      <v-col
        cols="12"
        md="2"
      >
        <fm-number-input
          :v-model="modelValue.kigaplatz"
          :display-mode="displayMode"
          :label="t('model.projekt.kigaplatz')"
          :validation-attribute-map="
            ProjektCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="kigaplatz"
        />
      </v-col>
      <v-col
        cols="12"
        md="2"
      >
        <fm-number-input
          :v-model="modelValue.hortplatz"
          :display-mode="displayMode"
          :label="t('model.projekt.hortplatz')"
          :validation-attribute-map="
            ProjektCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="hortplatz"
        />
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type {
  BauprogrammFormContextDTO,
  FoerderbereichFormContextDTO,
  KrankenhausFormContextDTO,
  KurzbezeichnungFormContextDTO,
  ProjektFormContext,
  ProjektResponseDTO,
  SiedlungsgebietFormContextDTO,
  StadtbezirkFormContextDTO,
  UnterabschnittFormContextDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { computed, useTemplateRef, watch } from "vue";
import { useI18n } from "vue-i18n";

import {
  ProjektCreateDTOKrhzweckEnum,
  ProjektCreateDTOKrisofpEnum,
  ProjektCreateDTOPropertyValidationAttributesMap,
} from "@/api/generated/foerdermittel-backend";
import FmAutocomplete from "@/components/common/FmAutocomplete.vue";
import FmCheckbox from "@/components/common/FmCheckbox.vue";
import FmNumberInput from "@/components/common/FmNumberInput.vue";
import FmTextArea from "@/components/common/FmTextArea.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();

const modelValue = defineModel<Partial<ProjektResponseDTO>>({
  required: true,
});

const { projektFormContext, displayMode = InputDisplayMode.CREATE } =
  defineProps<{
    projektFormContext: DeepReadonly<ProjektFormContext>;
    displayMode?: InputDisplayMode;
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

defineExpose({ validate });

const projnr = computed(() => {
  const ua = modelValue.value.uasUa ?? "";
  const jahr = modelValue.value.jahr;
  const lfdnr1 = modelValue.value.lfdnr1;
  const lfdnr2 = modelValue.value.lfdnr2;

  if (
    !ua ||
    jahr === undefined ||
    lfdnr1 === undefined ||
    lfdnr2 === undefined
  ) {
    return "";
  }

  return [
    ua,
    String(jahr).slice(-2).padStart(2, "0"),
    String(lfdnr1),
    String(lfdnr2).padStart(2, "0"),
  ].join("");
});

watch(
  projnr,
  (newProjnr) => {
    if (displayMode === InputDisplayMode.CREATE) {
      modelValue.value.projnr = newProjnr;
    }
  },
  { immediate: true }
);

function updateNumberField(
  field: "jahr" | "lfdnr1" | "lfdnr2" | "stadtbezirk" | "siedlungsgebiet",
  value: string | number | null | undefined
) {
  const normalizedValue = String(value ?? "").trim();
  modelValue.value[field] =
    normalizedValue === "" ? undefined : Number(normalizedValue);
}

function formatJahr() {
  const value = String(modelValue.value.jahr ?? "").trim();
  if (!value) return;
  modelValue.value.jahr = Number(value.slice(-2));
}

function formatLfdnr2() {
  const value = String(modelValue.value.lfdnr2 ?? "").trim();
  if (!value) return;
  modelValue.value.lfdnr2 = Number(value.slice(-2));
}

function getFoerderbereichTitle(item: FoerderbereichFormContextDTO) {
  return item ? `${item.fb} (${item.bezeichnung})` : "";
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

function getKrisofpTitle(item: ProjektCreateDTOKrisofpEnum): string {
  return `${item} (${krisofpBezeichnungen.get(item) ?? item})`;
}

function getKrisofpValue(item: ProjektCreateDTOKrisofpEnum): string {
  return item;
}

const krhzweckBezeichnungen = new Map<string, string>([
  ["J", "Jahreskrankenhausprogramm"],
  ["S", "Jahreskrankenhausprogramm"],
  ["R", "Jahreskrankenhausprogramm"],
]);

function getKrhzweckTitle(item: ProjektCreateDTOKrhzweckEnum): string {
  return `${item} (${krhzweckBezeichnungen.get(item) ?? item})`;
}

function getKrhzweckValue(item: ProjektCreateDTOKrhzweckEnum): string {
  return item;
}
</script>
