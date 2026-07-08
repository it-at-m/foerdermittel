<template>
  <v-dialog
    :model-value="showDialog"
    :max-width="DialogWidth.SMALL"
  >
    <confirm-card
      :title="t('component.unsavedChangesDialog.title')"
      :text="t('component.unsavedChangesDialog.text')"
      :loading="loading"
      :confirm-icon="mdiPencil"
      :confirm-text="t('common.action.continueEditing')"
      :cancel-text="t('common.action.discardChanges')"
      @cancel="emit('cancel')"
      @confirm="emit('confirm')"
    />
  </v-dialog>
</template>

<script setup lang="ts">
import { mdiPencil } from "@mdi/js";
import { useI18n } from "vue-i18n";

import ConfirmCard from "@/components/common/ConfirmCard.vue";
import { DialogWidth } from "@/types/DialogWidth";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();

const showDialog = defineModel<boolean>();

const { loading = false } = defineProps<{
  loading?: boolean;
  displayMode?: InputDisplayMode;
  disableEdit?: boolean;
}>();

const emit = defineEmits<{
  cancel: [];
  confirm: [];
}>();
</script>

<style scoped></style>
