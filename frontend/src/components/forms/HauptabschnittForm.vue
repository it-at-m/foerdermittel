<template>
  <v-form
    ref="form"
    :readonly="displayMode === InputDisplayMode.READ"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col cols="3">
        <fm-text-field
          v-model="modelValue.ha"
          :display-mode="displayMode"
          disable-edit
          :validation-attribute-map="
            HauptabschnittCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="ha"
          :additional-rules="[
            rules['unique']!(hauptabschnittFormContext.has, currentHa),
          ]"
          :label="t('model.hauptabschnitt.ha')"
        />
      </v-col>
      <v-col cols="9">
        <fm-text-field
          v-model="modelValue.bezeichnung"
          :display-mode="displayMode"
          :validation-attribute-map="
            HauptabschnittCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="bezeichnung"
          :label="t('model.hauptabschnitt.bezeichnung')"
        />
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type {
  HauptabschnittFormContext,
  HauptabschnittResponseDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { ref, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";
import { useRules } from "vuetify";

import { HauptabschnittCreateDTOPropertyValidationAttributesMap } from "@/api/generated/foerdermittel-backend";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();

const modelValue = defineModel<Partial<HauptabschnittResponseDTO>>({
  required: true,
});

// Reactivity is intentionally dropped here to maintain the initial state when form gets mounted.
const currentHa = ref(modelValue.value.ha);

const { hauptabschnittFormContext, displayMode = InputDisplayMode.CREATE } =
  defineProps<{
    hauptabschnittFormContext: DeepReadonly<HauptabschnittFormContext>;
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
