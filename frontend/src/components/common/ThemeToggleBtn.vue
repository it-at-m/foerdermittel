<template>
  <v-tooltip
    :text="changeText"
    location="right"
  >
    <template #activator="{ props }">
      <v-btn
        v-bind="props"
        :icon="isDark ? mdiWeatherSunny : mdiWeatherNight"
        @click="toggleTheme"
      />
    </template>
  </v-tooltip>
</template>

<script setup lang="ts">
import { mdiWeatherNight, mdiWeatherSunny } from "@mdi/js";
import { computed } from "vue";
import { useI18n } from "vue-i18n";
import { useTheme } from "vuetify";

const theme = useTheme();

const { t } = useI18n();

function toggleTheme(e: MouseEvent) {
  theme.setTransitionOrigin(e.currentTarget as HTMLElement);
  theme.toggle();
}

const isDark = computed(() => theme.global.current.value.dark);

const changeText = computed(() => {
  const theme = isDark.value
    ? t("component.themeToggleBtn.light")
    : t("component.themeToggleBtn.dark");
  return t("component.themeToggleBtn.changeText", [theme]);
});
</script>
