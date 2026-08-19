<template>
  <v-form
    ref="form"
    :readonly="displayMode === InputDisplayMode.READ"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col>
        <fm-autocomplete
          v-model="modelValue.hasHa"
          :display-mode="displayMode"
          :items="HaItems"
          item-title="anzeige"
          item-value="ha"
          :validation-attribute-map="
            UnterabschnittCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="hasHa"
          :label="t('model.unterabschnitt.hasHa')"
        />
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="3">
        <fm-text-field
          v-model="modelValue.ua"
          :display-mode="displayMode"
          :disabled="!modelValue.hasHa"
          disable-edit
          required
          :counter="2"
          :rules="[
            rules.required(),
            rules.minLength(1),
            rules.maxLength(2),
            rules.pattern(/^[a-zA-Z0-9]+$/),
            rules['unique']!(
              unterabschnittFormContext.uas,
              currentUa,
            ),
          ]"
          :label="t('model.unterabschnitt.ua')"
        />
      </v-col>

      <v-col cols="9">
        <fm-text-field
          v-model="modelValue.bezeichnung"
          :display-mode="displayMode"
          :disabled="!modelValue.hasHa"
          required
          :counter="200"
          :rules="[rules.required(), rules.maxLength(200)]"
          :label="t('model.unterabschnitt.bezeichnung')"
        />
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type {
  UnterabschnittFormContext,
  UnterabschnittResponseDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { computed, ref, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";
import { useRules } from "vuetify/labs/rules";

import { UnterabschnittCreateDTOPropertyValidationAttributesMap } from "@/api/generated/foerdermittel-backend";
import FmAutocomplete from "@/components/common/FmAutocomplete.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();

const modelValue = defineModel<Partial<UnterabschnittResponseDTO>>({
  required: true,
});

const currentUa = ref(modelValue.value.ua);

const {
  unterabschnittFormContext,
  displayMode = InputDisplayMode.CREATE,
} = defineProps<{
  unterabschnittFormContext: DeepReadonly<UnterabschnittFormContext>;
  displayMode?: InputDisplayMode;
}>();

const emit = defineEmits<{
  isValid: [boolean | null];
}>();

function onValidityChanged(newIsValid: boolean | null) {
  emit("isValid", newIsValid);
}

const rules = useRules();

const HaItems = computed(() =>
  unterabschnittFormContext.hasHas.map((hauptabschnitt) => ({
    ...hauptabschnitt,
    anzeige: `${hauptabschnitt.ha} (${hauptabschnitt.bezeichnung})`,
  })),
);

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