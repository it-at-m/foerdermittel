<template>
  <div>
    <unsaved-changes-dialog
      :model-value="showUnsavedChangesDialog"
      :loading="loading"
      @cancel="discardDialogChanges"
      @confirm="continueEditing"
    />
    <v-dialog
      :model-value="showDialog"
      :max-width="DialogWidth.LARGE"
    >
      <confirm-card
        v-if="displayMode"
        :title="dialogTitle"
        :loading="loading"
        :disable-confirm="!isFormValid || !isDirty"
        :hide-confirm="displayMode === InputDisplayMode.READ"
        @cancel="requestCloseDialog"
        @confirm="emit('save', currentValue)"
      >
        <benutzerhinweis-form
          v-model="currentValue"
          :display-mode="displayMode"
          @is-valid="updateFormValidity"
        />
      </confirm-card>
    </v-dialog>
  </div>
</template>

<script setup lang="ts">
import type { BenutzerhinweisResponseDTO } from "@/api/generated/foerdermittel-backend";

import { computed, ref, watch } from "vue";
import { useI18n } from "vue-i18n";

import ConfirmCard from "@/components/common/ConfirmCard.vue";
import UnsavedChangesDialog from "@/components/common/UnsavedChangesDialog.vue";
import BenutzerhinweisForm from "@/components/forms/BenutzerhinweisForm.vue";
import { useDirtyFlag } from "@/composables/useDirtyFlag";
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

const enableDirtyCheck = computed(() => {
  return (
    displayMode === InputDisplayMode.CREATE ||
    displayMode === InputDisplayMode.EDIT
  );
});

const {
  currentValue,
  isDirty,
  showUnsavedChangesDialog,
  reset,
  track,
  requestClose,
  continueEditing,
  continuePendingNavigation,
  discardChanges,
} = useDirtyFlag<Partial<BenutzerhinweisResponseDTO>>(
  benutzerhinweis,
  enableDirtyCheck
);

watch(
  () => displayMode,
  () => {
    track(benutzerhinweis);
  }
);

const requestCloseDialog = () => {
  requestClose(closeDialog);
};

const closeDialog = () => {
  reset();
  continuePendingNavigation();
  emit("close");
};

const discardDialogChanges = () => {
  discardChanges();
  emit("close");
};
</script>
