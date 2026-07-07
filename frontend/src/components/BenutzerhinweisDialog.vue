<template>
  <v-dialog
    :model-value="showDialog"
    :max-width="DialogWidth.LARGE"
  >
    <confirm-card
      v-if="displayMode"
      :title="dialogTitle"
      :loading="loading"
      :disable-confirm="!isFormValid"
      :hide-confirm="displayMode === InputDisplayMode.READ"
      @cancel="emit('close')"
      @confirm="emit('save', activeItem)"
    >
      <benutzerhinweis-form
        v-model="activeItem"
        :display-mode="displayMode"
        @is-valid="updateFormValidity"
      />
    </confirm-card>
  </v-dialog>
</template>

<script setup lang="ts">
import type { BenutzerhinweisResponseDTO } from "@/api/generated/foerdermittel-backend";

import { computed, ref, watch } from "vue";
import { useI18n } from "vue-i18n";

import ConfirmCard from "@/components/common/ConfirmCard.vue";
import BenutzerhinweisForm from "@/components/forms/BenutzerhinweisForm.vue";
import { DialogWidth } from "@/types/DialogWidth";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const showDialog = computed(() => !!displayMode);

const emit = defineEmits<{
  save: [item: Partial<BenutzerhinweisResponseDTO>];
  close: [];
}>();

const { t } = useI18n();

const {
  benutzerhinweis,
  displayMode,
  loading = false,
} = defineProps<{
  benutzerhinweis: Readonly<Partial<BenutzerhinweisResponseDTO>>;
  displayMode?: InputDisplayMode;
  loading?: boolean;
}>();
const activeItem = ref<Partial<BenutzerhinweisResponseDTO>>(benutzerhinweis);

watch(
  () => benutzerhinweis,
  (newBenutzerhinweis) => {
    activeItem.value = { ...newBenutzerhinweis };
  }
);

watch(showDialog, (newShowDialog) => {
  if (newShowDialog) {
    activeItem.value = { ...benutzerhinweis };
  }
});

const isFormValid = ref(false);

function updateFormValidity(valid: boolean | null) {
  isFormValid.value = !!valid;
}

const domain = t("model.benutzerhinweis.modelName");

const dialogTitle = computed(() => {
  switch (displayMode) {
    case InputDisplayMode.CREATE:
    case InputDisplayMode.EDIT:
      return t("common.generics.update", [domain]);
    case InputDisplayMode.READ:
      return domain;
    default:
      return "";
  }
});
</script>
