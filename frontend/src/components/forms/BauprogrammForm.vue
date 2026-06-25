<template>
  <v-form
    :readonly="displayMode === InputDisplayMode.READ"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col cols="3">
        <fm-number-input
          v-model="modelValue.bauprogramm"
          :display-mode="displayMode"
          disable-edit
          required
          :rules="[
            rules.required(),
            rules.number(),
            rules['min']!(1),
            rules['max']!(99),
            rules['unique']!(
              bauprogrammFormContext.bauprogramme,
              currentBauprogramm
            ),
          ]"
          :label="t('model.bauprogramm.bauprogramm')"
        />
      </v-col>
      <v-col cols="9">
        <fm-text-field
          v-model="modelValue.bezeichnung"
          :display-mode="displayMode"
          required
          :counter="200"
          :rules="[rules.required(), rules.maxLength(200)]"
          :label="t('model.bauprogramm.bezeichnung')"
        />
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type {
  BauprogrammFormContext,
  BauprogrammResponseDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";

import { ref } from "vue";
import { useI18n } from "vue-i18n";
import { useRules } from "vuetify/labs/rules";

import FmNumberInput from "@/components/common/FmNumberInput.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();

const modelValue = defineModel<Partial<BauprogrammResponseDTO>>({
  required: true,
});
const currentBauprogramm = ref(modelValue.value.bauprogramm);

const { bauprogrammFormContext, displayMode = InputDisplayMode.CREATE } =
  defineProps<{
    bauprogrammFormContext: DeepReadonly<BauprogrammFormContext>;
    displayMode?: InputDisplayMode;
  }>();

const emit = defineEmits<{
  isValid: [boolean | null];
}>();

const onValidityChanged = (newIsValid: boolean | null) => {
  emit("isValid", newIsValid);
};

const rules = useRules();
</script>
