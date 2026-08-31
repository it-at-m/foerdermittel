<template>
  <v-form
    ref="form"
    :readonly="displayMode === InputDisplayMode.READ"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col cols="3">
        <fm-autocomplete
          v-model="modelValue.projnr"
          :items="projektItems"
          item-title="anzeige"
          item-value="projnr"
          :display-mode="displayMode"
          :label="t('model.archiv.projnr')"
          :rules="[rules.required()]"
          :validation-attribute-map="
            ArchivCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="projnr"
          :disable-edit="displayMode === InputDisplayMode.EDIT"
        />
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="4">
        <fm-date-input
          v-model="modelValue.speicherDatum"
          :display-mode="displayMode"
          :label="t('model.archiv.speicherDatum')"
          clearable
        />
      </v-col>

      <v-col cols="4">
        <fm-date-input
          v-model="modelValue.mikroDatPlan"
          :display-mode="displayMode"
          :label="t('model.archiv.mikroDatPlan')"
          clearable
        />
      </v-col>

      <v-col cols="4">
        <fm-date-input
          v-model="modelValue.mikroDat"
          :display-mode="displayMode"
          :label="t('model.archiv.mikroDat')"
          clearable
        />
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="6">
        <fm-checkbox
          v-model="modelValue.speicherAkt"
          :display-mode="displayMode"
          :label="t('model.archiv.speicherAkt')"
        />
      </v-col>

      <v-col cols="6">
        <fm-checkbox
          v-model="modelValue.speicherRechnungen"
          :display-mode="displayMode"
          :label="t('model.archiv.speicherRechnungen')"
        />
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="12">
        <fm-text-field
          v-model="modelValue.notizen"
          :display-mode="displayMode"
          :label="t('model.archiv.notizen')"
        />
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type {
  ArchivResponseDTO,
  ProjektFormContextDTO,
} from "@/api/generated/foerdermittel-backend";
import type { VForm } from "vuetify/components";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";
import { useRules } from "vuetify/labs/rules";

import { ArchivCreateDTOPropertyValidationAttributesMap } from "@/api/generated/foerdermittel-backend";
import FmAutocomplete from "@/components/common/FmAutocomplete.vue";
import FmCheckbox from "@/components/common/FmCheckbox.vue";
import FmDateInput from "@/components/common/FmDateInput.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();
const rules = useRules();

const { projekte, displayMode = InputDisplayMode.CREATE } = defineProps<{
  projekte: ProjektFormContextDTO[];
  displayMode?: InputDisplayMode;
}>();

const modelValue = defineModel<Partial<ArchivResponseDTO>>({
  required: true,
});

const projektItems = computed(() =>
  projekte.map((projekt) => ({
    ...projekt,
    anzeige: `${projekt.projnr} (${projekt.pname})`,
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
</script>
