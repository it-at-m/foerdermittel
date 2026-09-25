<template>
  <v-form
    ref="form"
    :readonly="displayMode === InputDisplayMode.READ"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col cols="5">
        <fm-autocomplete
          v-model="modelValue.projnr"
          :items="projektItems"
          item-title="anzeige"
          item-value="projnr"
          :display-mode="displayMode"
          :label="t('model.istkosten.projnr')"
          :rules="[rules.required()]"
          :validation-attribute-map="
            IstkostenCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="projnr"
          :disable-edit="displayMode === InputDisplayMode.EDIT"
        />
      </v-col>
      <v-col cols="2">
        <fm-number-input
          v-model="modelValue.jahr"
          :display-mode="displayMode"
          :rules="[rules.required()]"
          :label="t('model.istkosten.jahr')"
          min="1970"
          max="2100"
          :validation-attribute-map="
            IstkostenCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="jahr"
          :disable-edit="displayMode === InputDisplayMode.EDIT"
        ></fm-number-input>
      </v-col>
      <v-col cols="2">
        <fm-autocomplete
          v-model="modelValue.monat"
          :items="monatOptions"
          :label="t('model.istkosten.monat')"
          :rules="[rules.required()]"
          :display-mode="displayMode"
          :disable-edit="displayMode === InputDisplayMode.EDIT"
        />
      </v-col>
      <v-col cols="3">
        <fm-number-input
          v-model="modelValue.istkosten"
          :display-mode="displayMode"
          min="0"
          :rules="[rules.required()]"
          :label="t('model.istkosten.istkosten')"
        ></fm-number-input>
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type {
  IstkostenResponseDTO,
  ProjektFormContextDTO,
} from "@/api/generated/foerdermittel-backend";
import type { VForm } from "vuetify/components";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";
import { useRules } from "vuetify";

import { IstkostenCreateDTOPropertyValidationAttributesMap } from "@/api/generated/foerdermittel-backend";
import FmAutocomplete from "@/components/common/FmAutocomplete.vue";
import FmNumberInput from "@/components/common/FmNumberInput.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();
const rules = useRules();

const { projekte, displayMode = InputDisplayMode.CREATE } = defineProps<{
  projekte: ProjektFormContextDTO[];
  displayMode?: InputDisplayMode;
}>();

const modelValue = defineModel<Partial<IstkostenResponseDTO>>({
  required: true,
});

const projektItems = computed(() =>
  projekte.map((projekt) => ({
    ...projekt,
    anzeige: `${projekt.projnr} (${projekt.pname}, ${projekt.pstrasse}, FB: ${projekt.foerderbereich} )`,
  }))
);

const emit = defineEmits<{
  isValid: [boolean | null];
}>();

function onValidityChanged(value: boolean | null) {
  emit("isValid", value);
}

const formRef = useTemplateRef<VForm>("form");

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

const monatOptions = Array.from({ length: 12 }, (_, i) => i + 1);

</script>
