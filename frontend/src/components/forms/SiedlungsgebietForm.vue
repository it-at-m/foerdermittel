<template>
  <v-form
    ref="form"
    :readonly="displayMode === InputDisplayMode.READ"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col cols="3">
        <fm-number-input
          v-model="modelValue.siedlungsgebiet"
          :display-mode="displayMode"
          disable-edit
          :validation-attribute-map="
            SiedlungsgebietCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="siedlungsgebiet"
          :additional-rules="[
            rules['unique']!(
              siedlungsgebietFormContext.siedlungsgebiete,
              currentSiedlungsgebiet
            ),
          ]"
          :label="t('model.siedlungsgebiet.siedlungsgebiet')"
        />
      </v-col>
      <v-col cols="9">
        <fm-text-field
          v-model="modelValue.bezeichnung"
          :display-mode="displayMode"
          :validation-attribute-map="
            SiedlungsgebietCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="bezeichnung"
          :label="t('model.siedlungsgebiet.bezeichnung')"
        />
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type {
  SiedlungsgebietFormContext,
  SiedlungsgebietResponseDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { ref, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";
import { useRules } from "vuetify/labs/rules";

import { SiedlungsgebietCreateDTOPropertyValidationAttributesMap } from "@/api/generated/foerdermittel-backend";
import FmNumberInput from "@/components/common/FmNumberInput.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();

const modelValue = defineModel<Partial<SiedlungsgebietResponseDTO>>({
  required: true,
});

// Reactivity is intentionally dropped here to maintain the initial state when form gets mounted.
const currentSiedlungsgebiet = ref(modelValue.value.siedlungsgebiet);

const { siedlungsgebietFormContext, displayMode = InputDisplayMode.CREATE } =
  defineProps<{
    siedlungsgebietFormContext: DeepReadonly<SiedlungsgebietFormContext>;
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
