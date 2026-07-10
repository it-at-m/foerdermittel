<template>
  <v-form
    ref="form"
    :readonly="displayMode === InputDisplayMode.READ"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col cols="3">
        <fm-text-field
          v-model="modelValue.kurzbez"
          :display-mode="displayMode"
          disable-edit
          required
          uppercase
          :counter="3"
          :rules="[
            rules.required(),
            rules.minLength(1),
            rules.maxLength(3),
            rules.pattern(/^[A-Z]{1,3}$/),
            rules['unique']!(
              kurzbezeichnungFormContext.kurzbezeichnungen,
              currentKurzbezeichnung
            ),
          ]"
          :label="t('model.kurzbezeichnung.kurzbez')"
        />
      </v-col>
      <v-col cols="9">
        <fm-text-field
          v-model="modelValue.bezeichnung"
          :display-mode="displayMode"
          required
          :counter="200"
          :rules="[rules.required(), rules.maxLength(200)]"
          :label="t('model.kurzbezeichnung.bezeichnung')"
        />
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type {
  KurzbezeichnungFormContext,
  KurzbezeichnungResponseDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { ref, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";
import { useRules } from "vuetify/labs/rules";

import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();

const modelValue = defineModel<Partial<KurzbezeichnungResponseDTO>>({
  required: true,
});

// Reactivity is intentionally dropped here to maintain the initial state when form gets mounted.
const currentKurzBez = ref(modelValue.value.kurzbez);

const { kurzbezeichnungFormContext, displayMode = InputDisplayMode.CREATE } =
  defineProps<{
    kurzbezeichnungFormContext: DeepReadonly<KurzbezeichnungFormContext>;
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
