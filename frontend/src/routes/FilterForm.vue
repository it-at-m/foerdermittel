<template>
  <v-form @update:model-value="onValidityChanged">
    <v-row>
      <v-col
        cols="12"
        md="2"
      >
        <fm-text-field
          v-model="modelValue.projnr"
          :label="t('model.projekt.projnr')"
          clearable
          density="comfortable"
        />
      </v-col>

      <v-col
        cols="12"
        md="2"
      >
        <fm-text-field
          v-model="modelValue.fobFb"
          :label="t('model.projekt.fobFb')"
          clearable
          density="comfortable"
        />
      </v-col>

      <v-col
        cols="12"
        md="2"
      >
        <fm-text-field
          v-model="modelValue.kurz"
          :label="t('model.projekt.kurKurzbez')"
          clearable
          density="comfortable"
        />
      </v-col>

      <v-col
        cols="12"
        md="2"
      >
        <fm-text-field
          v-model="modelValue.ua"
          :label="t('model.projekt.uasUa')"
          clearable
          density="comfortable"
        />
      </v-col>

      <v-col
        cols="12"
        md="2"
      >
        <fm-text-field
          v-model="modelValue.pname"
          :label="t('model.projekt.name')"
          clearable
          density="comfortable"
        />
      </v-col>

      <v-col
        cols="12"
        md="2"
      >
        <fm-text-field
          v-model="modelValue.pstrasse"
          :label="t('model.projekt.strasse')"
          clearable
          density="comfortable"
        />
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type { ProjekteFilterType } from "@/types/ProjekteFilterType.ts";

import { useI18n } from "vue-i18n";
import { useRules } from "vuetify";

import FmTextField from "@/components/common/FmTextField.vue";

const modelValue = defineModel<Partial<ProjekteFilterType>>({
  required: true,
});

const rules = useRules();

const { t } = useI18n();

const emit = defineEmits<{
  isValid: [boolean | null];
}>();

function onValidityChanged(newIsValid: boolean | null) {
  emit("isValid", newIsValid);
}
</script>
