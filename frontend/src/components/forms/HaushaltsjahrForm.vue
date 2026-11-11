<template>
  <v-form
    ref="form"
    :readonly="props.displayMode === InputDisplayMode.READ"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col cols="3">
        <fm-number-input
          v-model="modelValue.hhjahr"
          :display-mode="props.displayMode"
          disable-edit
          :validation-attribute-map="
            HhjahrCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="hhjahr"
          :additional-rules="[
            rules['unique']!(props.hhjahrFormContext.hhj, currentHhjahr),
          ]"
          :label="t('model.haushaltsjahr.hhjahr')"
        />
      </v-col>

      <v-col cols="4">
        <fm-text-field
          v-model="modelValue.hhbezeichnung"
          :display-mode="props.displayMode"
          :validation-attribute-map="
            HhjahrCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="hhbezeichnung"
          :label="t('model.haushaltsjahr.hhbezeichnung')"
        />
      </v-col>

      <v-col cols="4">
        <fm-text-field
          v-model="modelValue.notizen"
          :display-mode="props.displayMode"
          :validation-attribute-map="
            HhjahrCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="notizen"
          :label="t('model.haushaltsjahr.notizen')"
        />
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type {
  HhjahrFormContext,
  HhjahrResponseDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";
import { useRules } from "vuetify";

import { HhjahrCreateDTOPropertyValidationAttributesMap } from "@/api/generated/foerdermittel-backend";
import FmNumberInput from "@/components/common/FmNumberInput.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();
const rules = useRules();

const modelValue = defineModel<Partial<HhjahrResponseDTO>>({
  required: true,
});

const props = defineProps<{
  hhjahrFormContext: DeepReadonly<HhjahrFormContext>;
  displayMode?: InputDisplayMode;
}>();

const emit = defineEmits<{
  isValid: [boolean | null];
}>();

const currentHhjahr = computed(() => modelValue.value.hhjahr);

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
