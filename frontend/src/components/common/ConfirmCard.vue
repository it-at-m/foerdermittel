<template>
  <v-card
    :title="title"
    :loading="loading"
  >
    <template #text>
      <slot>
        {{ text }}
      </slot>
    </template>
    <template #actions>
      <v-spacer />

      <v-btn
        :class="{ 'mr-2': showConfirm }"
        :text="
          showConfirm ? t('common.action.cancel') : t('common.action.close')
        "
        :disabled="loading"
        @click="cancel"
      />
      <v-btn
        v-if="showConfirm"
        color="primary"
        variant="flat"
        :append-icon="confirmIcon"
        :text="confirmText ?? t('common.action.save')"
        :disabled="loading || disableConfirm"
        @click="confirm"
      />
    </template>
  </v-card>
</template>

<script setup lang="ts">
import { mdiContentSave } from "@mdi/js";
import { useI18n } from "vue-i18n";

const { t } = useI18n();

const {
  loading = false,
  disableConfirm = false,
  showConfirm = true,
  confirmIcon = mdiContentSave,
} = defineProps<{
  title: string;
  text?: string;
  confirmText?: string;
  confirmIcon?: string;
  loading?: boolean;
  disableConfirm?: boolean;
  showConfirm?: boolean;
}>();

const emit = defineEmits<{
  confirm: [];
  cancel: [];
}>();

const confirm = () => emit("confirm");
const cancel = () => emit("cancel");
</script>
