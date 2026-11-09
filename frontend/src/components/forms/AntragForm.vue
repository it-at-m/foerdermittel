<template>
  <v-form
    ref="form"
    :readonly="displayMode === InputDisplayMode.READ"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col cols="6">
        <fm-autocomplete
          v-model="modelValue.projnr"
          :items="projektItems"
          item-title="anzeige"
          item-value="projnr"
          :display-mode="displayMode"
          :label="t('model.antrag.projnr')"
          :validation-attribute-map="
            AntragCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="projnr"
          :additional-rules="[projektExistiert]"
          :disable-edit="displayMode === InputDisplayMode.EDIT"
        />
      </v-col>
      <v-col
        v-if="selectedProjekt"
        cols="6"
      >
        <v-row>
          <v-col cols="5">
            <fm-text-field
              :model-value="selectedProjekt.pname"
              :label="t('model.antrag.pname')"
              :display-mode="InputDisplayMode.READ"
              rows="1"
            />
          </v-col>
          <v-col cols="5">
            <fm-text-field
              :model-value="selectedProjekt.pstrasse"
              :label="t('model.antrag.pstrasse')"
              :display-mode="InputDisplayMode.READ"
              rows="1"
            />
          </v-col>
          <v-col cols="2">
            <fm-text-field
              :model-value="selectedProjekt.fobFb"
              :label="t('model.antrag.fobFb')"
              :display-mode="InputDisplayMode.READ"
              rows="1"
            />
          </v-col>
        </v-row>
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="4">
        <fm-autocomplete
          v-model="modelValue.antragstyp"
          :items="antragstypItems"
          :display-mode="displayMode"
          :label="t('model.antrag.antragstyp')"
          :validation-attribute-map="
            AntragCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="antragstyp"
        />
      </v-col>
      <v-col cols="4">
        <fm-date-input
          v-model="modelValue.antragsdatum"
          :display-mode="displayMode"
          :label="t('model.antrag.antragsdatum')"
          :additional-rules="[antragsdatumGesetzt]"
          clearable
        />
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="4">
        <fm-date-input
          v-model="modelValue.unbeddat"
          :display-mode="displayMode"
          :label="t('model.antrag.unbeddat')"
          :additional-rules="[unbeddatGesetzt]"
          clearable
        />
      </v-col>
      <v-col cols="4">
        <fm-date-input
          v-model="modelValue.unbedja"
          :display-mode="displayMode"
          :label="t('model.antrag.unbedja')"
          :additional-rules="[unbedjaKonsistent]"
          clearable
        />
      </v-col>
      <v-col cols="4">
        <fm-date-input
          v-model="modelValue.unbedbis"
          :display-mode="displayMode"
          :label="t('model.antrag.unbedbis')"
          :additional-rules="[unbedbisKonsistent]"
          clearable
        />
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="4">
        <fm-number-input
          v-model="modelValue.geskosten"
          :display-mode="displayMode"
          prefix="€"
          grouping
          class="currency-input"
          :label="t('model.antrag.geskosten')"
          :validation-attribute-map="
            AntragCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="geskosten"
        />
      </v-col>
      <v-col cols="4">
        <fm-number-input
          v-model="modelValue.zwfkosten"
          :display-mode="displayMode"
          prefix="€"
          grouping
          class="currency-input"
          :label="t('model.antrag.zwfkosten')"
          :validation-attribute-map="
            AntragCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="zwfkosten"
        />
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="4">
        <fm-number-input
          v-model="modelValue.asuZ"
          :display-mode="displayMode"
          prefix="€"
          grouping
          class="currency-input"
          :label="t('model.antrag.asuZ')"
          :additional-rules="[betragBeantragt]"
          :validation-attribute-map="
            AntragCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="asuZ"
        />
      </v-col>
      <v-col cols="4">
        <fm-number-input
          v-model="modelValue.asuD"
          :display-mode="displayMode"
          prefix="€"
          grouping
          class="currency-input"
          :label="t('model.antrag.asuD')"
          :additional-rules="[betragBeantragt]"
          :validation-attribute-map="
            AntragCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="asuD"
        />
      </v-col>
      <v-col cols="4">
        <fm-number-input
          v-model="modelValue.asuK"
          :display-mode="displayMode"
          prefix="€"
          grouping
          class="currency-input"
          :label="t('model.antrag.asuK')"
          :additional-rules="[betragBeantragt]"
          :validation-attribute-map="
            AntragCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="asuK"
        />
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="4">
        <fm-number-input
          v-model="modelValue.bvorSuZ"
          :display-mode="displayMode"
          prefix="€"
          grouping
          class="currency-input"
          :label="t('model.antrag.bvorSuZ')"
          :validation-attribute-map="
            AntragCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="bvorSuZ"
        />
      </v-col>
      <v-col cols="4">
        <fm-number-input
          v-model="modelValue.bvorSuD"
          :display-mode="displayMode"
          prefix="€"
          grouping
          class="currency-input"
          :label="t('model.antrag.bvorSuD')"
          :validation-attribute-map="
            AntragCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="bvorSuD"
        />
      </v-col>
      <v-col cols="4">
        <fm-number-input
          v-model="modelValue.bvorSuK"
          :display-mode="displayMode"
          prefix="€"
          grouping
          class="currency-input"
          :label="t('model.antrag.bvorSuK')"
          :validation-attribute-map="
            AntragCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="bvorSuK"
        />
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="4">
        <fm-checkbox
          v-model="modelValue.vorzbeg"
          :display-mode="displayMode"
          :label="t('model.antrag.vorzbeg')"
        />
      </v-col>
      <v-col cols="4">
        <fm-date-input
          v-model="modelValue.vbdatum"
          :display-mode="displayMode"
          :label="t('model.antrag.vbdatum')"
          clearable
        />
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="12">
        <fm-text-area
          v-model="modelValue.notizen"
          :display-mode="displayMode"
          :label="t('model.antrag.notizen')"
        />
      </v-col>
    </v-row>

    <v-row v-if="modelValue.anlagedatum">
      <v-col
        cols="12"
        class="text-caption"
      >
        <div>
          {{
            t("model.antrag.angelegt", [
              formatDateTime(modelValue.anlagedatum),
              modelValue.anlagevon,
            ])
          }}
        </div>
        <div v-if="modelValue.aenderungsdatum">
          {{
            t("model.antrag.geaendert", [
              formatDateTime(modelValue.aenderungsdatum),
              modelValue.aenderungvon,
            ])
          }}
        </div>
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type {
  AntragResponseDTO,
  ProjektFormContextDTO,
} from "@/api/generated/foerdermittel-backend";
import type { VForm } from "vuetify/components";

import { computed, useTemplateRef, watch } from "vue";
import { useI18n } from "vue-i18n";

import {
  AntragCreateDTOAntragstypEnum,
  AntragCreateDTOPropertyValidationAttributesMap,
} from "@/api/generated/foerdermittel-backend";
import FmAutocomplete from "@/components/common/FmAutocomplete.vue";
import FmCheckbox from "@/components/common/FmCheckbox.vue";
import FmDateInput from "@/components/common/FmDateInput.vue";
import FmNumberInput from "@/components/common/FmNumberInput.vue";
import FmTextArea from "@/components/common/FmTextArea.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();

const { projekte, displayMode = InputDisplayMode.CREATE } = defineProps<{
  projekte: ProjektFormContextDTO[];
  displayMode?: InputDisplayMode;
}>();

const modelValue = defineModel<Partial<AntragResponseDTO>>({
  required: true,
});

const projektItems = computed(() =>
  projekte.map((projekt) => ({
    ...projekt,
    anzeige: `${projekt.projnr} (${projekt.pname}, ${projekt.pstrasse})`,
  }))
);

const selectedProjekt = computed(() =>
  projekte.find((projekt) => projekt.projnr === modelValue.value.projnr)
);

const antragstypLabels: Record<AntragCreateDTOAntragstypEnum, string> = {
  E: t("model.antrag.typ.erstantrag"),
  V: t("model.antrag.typ.schlussantrag"),
  A: t("model.antrag.typ.aenderungsantrag"),
  F: t("model.antrag.typ.fortsetzungsantrag"),
  U: t("model.antrag.typ.unbedenklichkeitsantrag"),
};

const antragstypItems = Object.values(AntragCreateDTOAntragstypEnum).map(
  (typ) => ({ value: typ, title: `${typ} – ${antragstypLabels[typ]}` })
);

// Mirrors the backend plausibility rules in AntragDTO.java
const projektExistiert = (projnr?: string) =>
  !projnr ||
  projekte.some((projekt) => projekt.projnr === projnr) ||
  t("model.antrag.validation.projektNichtVorhanden");

const antragsdatumGesetzt = () =>
  modelValue.value.antragstyp === AntragCreateDTOAntragstypEnum.U ||
  !!modelValue.value.antragsdatum ||
  t("model.antrag.validation.antragsdatumPflicht");

const unbeddatGesetzt = () =>
  modelValue.value.antragstyp !== AntragCreateDTOAntragstypEnum.U ||
  !!modelValue.value.unbeddat ||
  t("model.antrag.validation.unbeddatPflicht");

const betragBeantragt = () =>
  !modelValue.value.antragsdatum ||
  [modelValue.value.asuZ, modelValue.value.asuD, modelValue.value.asuK].some(
    (betrag) => (betrag ?? 0) > 0
  ) ||
  t("model.antrag.validation.betragPflicht");

const unbedjaKonsistent = () => {
  const { unbeddat, unbedja } = modelValue.value;
  return (
    !unbedja ||
    (!!unbeddat && unbedja >= unbeddat) ||
    t("model.antrag.validation.unbedjaKonsistent")
  );
};

const unbedbisKonsistent = () => {
  const { unbedja, unbedbis } = modelValue.value;
  return (
    !unbedbis ||
    (!!unbedja && unbedbis >= unbedja) ||
    t("model.antrag.validation.unbedbisKonsistent")
  );
};

const formatDateTime = (value?: Date) => value?.toLocaleString("de-DE") ?? "";

const emit = defineEmits<{
  isValid: [boolean | null];
}>();

function onValidityChanged(value: boolean | null) {
  emit("isValid", value);
}

const formRef = useTemplateRef<VForm>("form");

// cross-field rules: re-check only fields showing an error, e.g. unbedja after unbeddat was entered
// isValid is null for untouched fields whose errors Vuetify keeps hidden
watch(
  () => ({ ...modelValue.value }),
  () =>
    formRef.value?.items
      .filter((item) => item.isValid === false)
      .forEach((item) => item.validate())
);

async function validate(): Promise<boolean> {
  if (!formRef.value) {
    return false;
  }

  const result = await formRef.value.validate();

  emit("isValid", result.valid);

  return result.valid;
}

defineExpose({
  validate,
});
</script>

<style>
.currency-input :deep(input) {
  text-align: right;
}
</style>
