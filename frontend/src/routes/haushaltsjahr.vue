<template>
  <v-form
    ref="form"
    :readonly="displayMode === InputDisplayMode.READ"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col cols="3">
        <fm-text-field
          v-model="hhjahr"
          :display-mode="displayMode"
          disable-edit
          :validation-attribute-map="
            HhjahrCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="hhjahr"
          :label="t('model.haushaltsjahr.hhjahr')"
        />
      </v-col>

      <v-col cols="4">
        <fm-text-field
          v-model="modelValue.hhbezeichnung"
          :display-mode="displayMode"
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
          :display-mode="displayMode"
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

import { HhjahrCreateDTOPropertyValidationAttributesMap } from "@/api/generated/foerdermittel-backend";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();

const modelValue = defineModel<Partial<HhjahrResponseDTO>>({
  required: true,
});

const { haushaltsjahrFormContext, displayMode = InputDisplayMode.CREATE } =
  defineProps<{
    haushaltsjahrFormContext: DeepReadonly<HhjahrFormContext>;
    displayMode?: InputDisplayMode;
  }>();

const emit = defineEmits<{
  isValid: [boolean | null];
}>();

const hhjahr = computed<string | undefined>({
  get: () =>
    modelValue.value.hhjahr !== undefined
      ? String(modelValue.value.hhjahr)
      : undefined,
  set: (value) => {
    if (value === undefined || value.trim() === "") {
      modelValue.value.hhjahr = undefined;
      return;
    }

    const numericValue = Number(value);

    modelValue.value.hhjahr = Number.isNaN(numericValue)
      ? undefined
      : numericValue;
  },
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
