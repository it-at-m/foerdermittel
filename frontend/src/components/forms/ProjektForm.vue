<template>
  <v-form
    ref="form"
    :readonly="displayMode === InputDisplayMode.READ"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col
        cols="12"
        md="3"
      >
        <fm-text-field
          v-model="modelValue.projnr"
          :label="t('model.projekt.projnr')"
          :display-mode="displayMode"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <v-autocomplete
          v-model="modelValue.fobFb"
          :items="foerderbereiche?.content ?? []"
          item-value="fb"
          :item-title="formatFoerderbereich"
          :label="t('model.projekt.fobFb')"
          :loading="foerderbereicheLoading"
          :readonly="displayMode === InputDisplayMode.READ"
          clearable
        />
      </v-col>

      <v-col
        cols="12"
        md="6"
      >
        <v-autocomplete
          v-model="modelValue.kurKurzbez"
          :items="kurzbezeichnungen?.content ?? []"
          item-value="kurzbez"
          :item-title="formatKurzbezeichnung"
          :label="t('model.projekt.kurKurzbez')"
          :loading="kurzbezeichnungenLoading"
          :readonly="displayMode === InputDisplayMode.READ"
          clearable
        />
      </v-col>

      <v-col
        cols="12"
        md="6"
      >
        <fm-text-field
          v-model="modelValue.uasUa"
          :display-mode="displayMode"
          :label="t('model.projekt.uasUa')"
        />
      </v-col>

      <v-col
        cols="12"
        md="6"
      >
        <fm-text-field
          v-model="modelValue.pname"
          :display-mode="displayMode"
          :label="t('model.projekt.pname')"
        />
      </v-col>

      <v-col cols="12">
        <fm-text-field
          v-model="modelValue.pstrasse"
          :display-mode="displayMode"
          :label="t('model.projekt.pstrasse')"
        />
      </v-col>
      <v-col
        cols="12"
        md="3"
      >
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type {
  FoerderbereichResponseDTO,
  KurzbezeichnungResponseDTO,
  ProjektResponseDTO,
} from "@/api/generated/foerdermittel-backend";
import type { VForm } from "vuetify/components";

import { onMounted, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import FmTextField from "@/components/common/FmTextField.vue";
import { useGetFoerderbereiche } from "@/composables/api/useFoerderbereichApi";
import { useGetKurzbezeichnungen } from "@/composables/api/useKurzbezeichnungApi";
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

const {
  data: foerderbereiche,
  call: getFoerderbereiche,
  loading: foerderbereicheLoading,
} = useGetFoerderbereiche();

onMounted(async () => {
  await getFoerderbereiche({
    page: 0,
    size: 100,
  });
});

const formatFoerderbereich = (foerderbereich: FoerderbereichResponseDTO) =>
  `${foerderbereich.fb} – ${foerderbereich.bezeichnung}`;

const {
  data: kurzbezeichnungen,
  call: getKurzbezeichnungen,
  loading: kurzbezeichnungenLoading,
} = useGetKurzbezeichnungen();

onMounted(async () => {
  await getKurzbezeichnungen({
    page: 0,
    size: 100,
  });
});

const formatKurzbezeichnung = (kurzbezeichnung: KurzbezeichnungResponseDTO) =>
  `${kurzbezeichnung.kurzbez} – ${kurzbezeichnung.bezeichnung}`;
</script>