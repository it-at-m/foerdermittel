<template>
  <v-form
    ref="form"
    :readonly="displayMode === InputDisplayMode.READ"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col cols="3">
        <fm-number-input
          v-model="modelValue.fb"
          :display-mode="displayMode"
          disable-edit
          required
          :counter="2"
          :rules="[
            rules.required(),
            rules.number(),
            rules['min']!(1),
            rules['max']!(99),
            rules['unique']!(
              foerderbereichFormContext.fbs,
              currentFoerderbereich
            ),
          ]"
          :label="t('model.foerderbereich.fb')"
        />
      </v-col>
      <v-col cols="9">
        <fm-text-field
          v-model="modelValue.bezeichnung"
          :display-mode="displayMode"
          required
          :counter="200"
          :rules="[rules.required(), rules.maxLength(200)]"
          :label="t('model.foerderbereich.bezeichnung')"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="3"> </v-col>
      <v-col cols="2">
        <fm-checkbox
          v-model="modelValue.finanzausgleich"
          :label="t('model.foerderbereich.finanzausgleich')"
        />
      </v-col>
      <v-col cols="2">
        <fm-checkbox
          v-model="modelValue.jahresstatistik"
          :label="t('model.foerderbereich.jahresstatistik')"
        />
      </v-col>
      <v-col cols="2">
        <fm-checkbox
          v-model="modelValue.kindergarten"
          :label="t('model.foerderbereich.kindergarten')"
        />
      </v-col>
      <v-col cols="2">
        <fm-checkbox
          v-model="modelValue.nichtRelevant"
          :label="t('model.foerderbereich.nichtRelevant')"
        />
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type {
  FoerderbereichFormContext,
  FoerderbereichResponseDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { ref, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";
import { useRules } from "vuetify/labs/rules";

import FmCheckbox from "@/components/common/FmCheckbox.vue";
import FmNumberInput from "@/components/common/FmNumberInput.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();

const modelValue = defineModel<Partial<FoerderbereichResponseDTO>>({
  required: true,
});

// Reactivity is intentionally dropped here to maintain the initial state when form gets mounted.
const currentFoerderbereich = ref(modelValue.value.fb);

const { foerderbereichFormContext, displayMode = InputDisplayMode.CREATE } =
  defineProps<{
    foerderbereichFormContext: DeepReadonly<FoerderbereichFormContext>;
    displayMode?: InputDisplayMode;
  }>();

const emit = defineEmits<{
  isValid: [boolean | null];
}>();

function onValidityChanged(newIsValid: boolean | null) {
  emit("isValid", newIsValid);
}

const rules = useRules();

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
