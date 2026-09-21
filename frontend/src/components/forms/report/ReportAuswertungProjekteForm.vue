<template>
  <v-form
      ref="form"
      @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col cols="12">
        <fm-autocomplete
            v-model="modelValue.parameters!.jahr"
            :items="jahre"
            :label="t('model.projekt.jahr')"
        />

        <fm-text-field
            v-model="modelValue.parameters!.bez"
            :display-mode="InputDisplayMode.EDIT"
            :label="t('model.projekt.bez')"
        />

        <fm-text-field
            v-model="modelValue.parameters!.fb"
            :display-mode="InputDisplayMode.EDIT"
            :label="t('model.projekt.fb')"
        />

        <fm-text-field
            v-model="modelValue.parameters!.ua"
            :display-mode="InputDisplayMode.EDIT"
            :label="t('model.projekt.ua')"
        />

        <fm-text-field
            v-model="modelValue.parameters!.kurz"
            :display-mode="InputDisplayMode.EDIT"
            :label="t('model.projekt.kurz')"
        />

        <fm-text-field
            v-model="modelValue.parameters!.pname"
            :display-mode="InputDisplayMode.EDIT"
            :label="t('model.projekt.pname')"
        />

        <fm-text-field
            v-model="modelValue.parameters!.pstrasse"
            :display-mode="InputDisplayMode.EDIT"
            :label="t('model.projekt.pstrasse')"
        />

        <v-col cols="12">
          <v-row>
            <fm-checkbox
                v-model="modelValue.parameters!.offen"
                :label="t('model.projekt.offen')"
            />

            <fm-checkbox
                v-model="modelValue.parameters!.kauf"
                :label="t('model.projekt.kauf')"
            />

            <fm-checkbox
                v-model="modelValue.parameters!.relevant"
                :label="t('model.projekt.relevant')"
            />
          </v-row>
        </v-col>

        <fm-text-field
            v-model="modelValue.parameters!.krisofp"
            :display-mode="InputDisplayMode.EDIT"
            :label="t('model.projekt.krisofp')"
        />

        <fm-text-field
            v-model="modelValue.parameters!.sgt"
            :display-mode="InputDisplayMode.EDIT"
            :label="t('model.projekt.sgt')"
        />

        <fm-text-field
            v-model="modelValue.parameters!.bpg"
            :display-mode="InputDisplayMode.EDIT"
            :label="t('model.projekt.bpg')"
        />
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type {
  GetReportAuswertungProjektRequest,
  ReportAuswertungProjekteFormContext,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import {
  ReportAuswertungProjekteDTOPropertyValidationAttributesMap,
} from "@/api/generated/foerdermittel-backend";
import FmAutocomplete from "@/components/common/FmAutocomplete.vue";
import FmCheckbox from "@/components/common/FmCheckbox.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();

const modelValue = defineModel<Partial<GetReportAuswertungProjektRequest>>({
  required: true,
});

const { reportAuswertungProjekteFormContext } = defineProps<{
  reportAuswertungProjekteFormContext?: DeepReadonly<ReportAuswertungProjekteFormContext>;
}>();

const emit = defineEmits<{
  isValid: [boolean | null];
}>();

/**
 * Die Jahre werden aus dem FormContext gelesen.
 *
 * Der FormContext enthält alle Projekte.
 * Der Benutzer wählt kein einzelnes Projekt aus.
 */
const jahre = computed<string[]>(() => {
  const projekte = reportAuswertungProjekteFormContext?.projekte ?? [];

  return [...new Set(
      projekte
          .map((projekt) => projekt.jahr)
          .filter((jahr): jahr is string => !!jahr)
  )].sort((a, b) => Number(a) - Number(b));
});

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
</script>
