<template>
  <v-avatar color="white">
    <img
      :src="avatarUrl"
      :alt="altText"
    />
  </v-avatar>
</template>
<script setup lang="ts">
import { computed } from "vue";

import { getAvatarHref } from "@/api/ad2imageavatar-client";

const {
  username,
  avatarMode = "fallbackGeneric",
  avatarSize = "64",
} = defineProps<{
  username: string;
  avatarMode?: string;
  avatarSize?: string;
}>();

const avatarUrl = computed(() => {
  return getAvatarHref(username, avatarMode, avatarSize);
});

const altText = computed(() => `Bild von ${username}`);
</script>
