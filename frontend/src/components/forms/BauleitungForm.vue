<template>
  <v-form
    ref="form"
    :readonly="displayMode === InputDisplayMode.READ"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col cols="3">
        <fm-text-field
          v-model="modelValue.bauleitung"
          :display-mode="displayMode"
          disable-edit
          required
          uppercase
          :counter="1"
          :rules="[
            rules.required(),
            rules.strictLength(1),
            rules.pattern(/^[A-Z1-9]+$/),
            rules['unique']!(
              bauleitungFormContext.bauleitungen,
              currentBauleitung
            ),
          ]"
          :label="t('model.bauleitung.bauleitung')"
        />
      </v-col>
      <v-col cols="9">
        <fm-text-field
          v-model="modelValue.bezeichnung"
          :display-mode="displayMode"
          required
          :counter="200"
          :rules="[rules.required(), rules.maxLength(200)]"
          :label="t('model.bauleitung.bezeichnung')"
        />
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type {
  BauleitungFormContext,
  BauleitungResponseDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { ref, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";
import { useRules } from "vuetify/labs/rules";

import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();

const modelValue = defineModel<Partial<BauleitungResponseDTO>>({
  required: true,
});
const currentBauleitung = ref(modelValue.value.bauleitung);

const { bauleitungFormContext, displayMode = InputDisplayMode.CREATE } =
  defineProps<{
    bauleitungFormContext: DeepReadonly<BauleitungFormContext>;
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
