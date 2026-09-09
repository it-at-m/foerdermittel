<template>
  <v-icon-btn
    v-tooltip:start="changeText"
    variant="text"
    color="accent"
    :icon="isDark ? mdiWeatherSunny : mdiWeatherNight"
    :aria-label="changeText"
    @click="toggleTheme"
  />
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
