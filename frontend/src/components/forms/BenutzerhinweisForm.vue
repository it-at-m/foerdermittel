<template>
  <v-form
    :readonly="displayMode === InputDisplayMode.READ"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col cols="4">
        <fm-text-area
          v-model="modelValue.funktionsbeschreibung"
          :display-mode="displayMode"
          :counter="4000"
          :rules="[rules.maxLength(4000)]"
          :label="t('model.benutzerhinweis.funktionsbeschreibung')"
        />
      </v-col>
      <v-col cols="4">
        <fm-text-area
          v-model="modelValue.bedienung"
          :display-mode="displayMode"
          :counter="4000"
          :rules="[rules.maxLength(4000)]"
          :label="t('model.benutzerhinweis.bedienung')"
        />
      </v-col>
      <v-col cols="4">
        <fm-text-area
          v-model="modelValue.pruefungVorgaben"
          :display-mode="displayMode"
          :counter="4000"
          :rules="[rules.maxLength(4000)]"
          :label="t('model.benutzerhinweis.pruefungVorgaben')"
        />
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type { BenutzerhinweisResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { VForm } from "vuetify/components";

import { useI18n } from "vue-i18n";
import { useRules } from "vuetify/labs/rules";

import FmTextArea from "@/components/common/FmTextArea.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();

const modelValue = defineModel<Partial<BenutzerhinweisResponseDTO>>({
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

const rules = useRules();
</script>
