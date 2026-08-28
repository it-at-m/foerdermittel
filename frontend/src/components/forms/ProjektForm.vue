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
          :model-value="projnr"
          :label="t('model.projekt.projnr')"
          :display-mode="displayMode"
          readonly
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <fm-autocomplete
          v-model="modelValue.uasUa"
          :items="unterabschnitt?.content ?? []"
          item-value="ua"
          :item-title="formatUnterabschnitt"
          :label="t('model.projekt.uasUa')"
          :loading="unterabschnitteLoading"
          :readonly="displayMode === InputDisplayMode.READ"
          :validation-attribute-map="
            ProjektCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="uasUa"
          clearable
        />
      </v-col>
      <v-col
        cols="12"
        md="2"
      >
        <fm-text-field
          v-model="modelValue.jahr"
          :display-mode="displayMode"
          :label="t('model.projekt.jahr')"
          :validation-attribute-map="
            ProjektCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="jahr"
          @blur="formatJahr"
        />
      </v-col>
      <v-col
        cols="12"
        md="2"
      >
        <fm-text-field
          v-model="modelValue.lfdnr1"
          :display-mode="displayMode"
          :label="t('model.projekt.lfdnr1')"
          :validation-attribute-map="
            ProjektCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="lfdnr1"
        />
      </v-col>
      <v-col
        cols="12"
        md="2"
      >
        <fm-text-field
          v-model="modelValue.lfdnr2"
          :display-mode="displayMode"
          :label="t('model.projekt.lfdnr2')"
          :validation-attribute-map="
            ProjektCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="lfdnr2"
          @blur="formatLfdnr2"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <fm-autocomplete
          v-model="modelValue.fobFb"
          :items="foerderbereiche?.content ?? []"
          item-value="fb"
          :item-title="formatFoerderbereich"
          :label="t('model.projekt.fobFb')"
          :loading="foerderbereicheLoading"
          :readonly="displayMode === InputDisplayMode.READ"
          clearable
          :validation-attribute-map="
            ProjektCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="fobFb"
        />
      </v-col>

      <v-col
        cols="12"
        md="6"
      >
        <fm-autocomplete
          v-model="modelValue.kurKurzbez"
          :items="kurzbezeichnung?.content ?? []"
          item-value="kurzbez"
          :item-title="formatKurzbezeichnung"
          :label="t('model.projekt.kurKurzbez')"
          :loading="kurzbezeichnungenLoading"
          :readonly="displayMode === InputDisplayMode.READ"
          :validation-attribute-map="
            ProjektCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="kurKurzbez"
          clearable
        />
      </v-col>

      <v-col cols="12">
        <fm-text-field
          v-model="modelValue.pstrasse"
          :display-mode="displayMode"
          :label="t('model.projekt.pstrasse')"
          :validation-attribute-map="
            ProjektCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="pstrasse"
        />
      </v-col>
      <v-col
        cols="12"
        md="12"
      >
        <fm-text-field
          v-model="modelValue.pname"
          :display-mode="displayMode"
          :label="t('model.projekt.pname')"
          :validation-attribute-map="
            ProjektCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="pname"
        />
      </v-col>
    </v-row>
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

import { ProjektCreateDTOPropertyValidationAttributesMap } from "@/api/generated/foerdermittel-backend";
import FmAutocomplete from "@/components/common/FmAutocomplete.vue";
import FmTextField from "@/components/common/FmTextField.vue";
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

const projnr = computed(() => {
  return [
    modelValue.value.uasUa,
    modelValue.value.jahr,
    modelValue.value.lfdnr1,
    modelValue.value.lfdnr2,
  ]
    .filter((value) => value !== undefined && value !== null && value !== "")
    .join("");
});

watch(
  projnr,
  (newProjnr) => {
    modelValue.value.projnr = newProjnr;
  },
  { immediate: true }
);

const formatJahr = () => {
  const value = String(modelValue.value.jahr ?? "").trim();
  if (!value) {
    return;
  }
  modelValue.value.jahr = value.padStart(2, "0").slice(-2);
};

const formatLfdnr2 = () => {
  const value = String(modelValue.value.lfdnr2 ?? "").trim();
  if (!value) {
    return;
  }
  modelValue.value.lfdnr2 = value.padStart(2, "0").slice(-2);
};

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
  data: kurzbezeichnung,
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

const {
  data: unterabschnitt,
  call: getUnterabschnitte,
  loading: unterabschnitteLoading,
} = useGetUnterabschnitte();

onMounted(async () => {
  await getUnterabschnitte({
    page: 0,
    size: 100,
  });
});

const formatUnterabschnitt = (unterabschnitt: UnterabschnittResponseDTO) =>
  `${unterabschnitt.ua} – ${unterabschnitt.bezeichnung}`;
</script>