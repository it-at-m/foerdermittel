<template>
  <v-form
    ref="form"
    :readonly="displayMode === InputDisplayMode.READ"
    @update:model-value="onValidityChanged"
  >
  </v-form>
</template>

<script setup lang="ts">
import type {
  FoerderbereichResponseDTO,
  KurzbezeichnungResponseDTO,
  ProjektResponseDTO,
  UnterabschnittResponseDTO,
} from "@/api/generated/foerdermittel-backend";
import type { VForm } from "vuetify/components";

import { computed, onMounted, useTemplateRef, watch } from "vue";
import { useI18n } from "vue-i18n";


import { useGetFoerderbereiche } from "@/composables/api/useFoerderbereichApi";
import { useGetKurzbezeichnungen } from "@/composables/api/useKurzbezeichnungApi";
import { useGetUnterabschnitte } from "@/composables/api/useUnterabschnittApi";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();

const modelValue = defineModel<Partial<ProjektResponseDTO>>({
  required: true,
});

const { displayMode = InputDisplayMode.CREATE } = defineProps<{
  displayMode?: InputDisplayMode;
}>();

const emit = defineEmits<{
  isValid: [boolean | null];
}>();

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