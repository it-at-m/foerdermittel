<script setup lang="ts">
import JsonSchemaCrudIndexPage from "@/components/json-schema-form/JsonSchemaCrudIndexPage.vue";
import { krankenhausResource } from "@/resources/krankenhausResource";
</script>

<template>
  <json-schema-crud-index-page :resource="krankenhausResource" />
</template>
