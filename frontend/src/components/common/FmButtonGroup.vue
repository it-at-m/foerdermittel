<template>
  <v-btn-group
    :density="mobile ? 'default' : 'comfortable'"
    :direction="mobile ? 'vertical' : 'horizontal'"
  >
    <slot />
  </v-btn-group>
</template>

<script setup lang="ts">
import { useDisplay } from "vuetify";

const { mobile } = useDisplay();
</script>
