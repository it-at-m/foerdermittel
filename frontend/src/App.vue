<template>
  <v-app>
    <the-snackbar-queue />
    <the-navigation-drawer />
    <v-main>
      <v-container fluid>
        <router-view v-slot="{ Component }">
          <v-fade-transition mode="out-in">
            <component :is="Component" />
          </v-fade-transition>
        </router-view>
      </v-container>
    </v-main>
  </v-app>
</template>

<script setup lang="ts">
import TheNavigationDrawer from "@/components/TheNavigationDrawer.vue";
import TheSnackbarQueue from "@/components/TheSnackbarQueue.vue";
</script>
