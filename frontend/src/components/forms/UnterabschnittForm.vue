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
          :items="unterabschnittFormContext.hasHas"
          :item-title="getHaTitle"
          item-value="ha"
          :validation-attribute-map="
            UnterabschnittCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="hasHa"
          :label="t('model.hauptabschnitt.ha')"
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
          :validation-attribute-map="
            UnterabschnittCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="ua"
          :additional-rules="[
            rules['unique']!(unterabschnittFormContext.uas, currentUa),
          ]"
          :label="t('model.unterabschnitt.ua')"
        />
      </v-col>

      <v-col cols="9">
        <fm-text-field
          v-model="modelValue.bezeichnung"
          :display-mode="displayMode"
          :disabled="!modelValue.hasHa"
          :validation-attribute-map="
            UnterabschnittCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="bezeichnung"
          :label="t('model.unterabschnitt.bezeichnung')"
        />
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type {
  UnterabschnittFormContext,
  UnterabschnittFormContextHauptabschnitt,
  UnterabschnittResponseDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { ref, useTemplateRef } from "vue";
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

const { unterabschnittFormContext, displayMode = InputDisplayMode.CREATE } =
  defineProps<{
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

function getHaTitle(item: UnterabschnittFormContextHauptabschnitt) {
  return item ? `${item.ha} (${item.bezeichnung})`: "";
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
