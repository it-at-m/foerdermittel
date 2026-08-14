<template>
  <v-container
    class="fill-height d-flex flex-column align-center justify-center"
  >
    <div class="text-center">
      <h1 class="text-headline-medium mt-0">
        {{ t("view.index.welcomeTitle") }}
      </h1>
      <p
        v-if="hasRole"
        class="text-body-large"
      >
        {{ t("view.index.welcomeText") }}
      </p>
      <v-alert
        v-else
        :text="t('common.message.noPermission')"
        :icon="mdiShieldLock"
        type="error"
        class="flex-grow-0"
      />
    </div>
  </v-container>
</template>

<script setup lang="ts">
import { mdiShieldLock } from "@mdi/js";
import { useI18n } from "vue-i18n";

import useHasAnyRole from "@/composables/useHasAnyRole";
import { Role } from "@/types/Role";

const { t } = useI18n();

const hasRole = useHasAnyRole([
  Role.SACHBEARBEITUNG,
  Role.SACHBEARBEITUNG_HAUSHALT,
  Role.ADMIN,
]);
</script>