<script setup>
import Banner from '@/views/Banner.vue'
</script>

<template>
  <header>
    <div class="wrapper">
      <Banner msg="Url Shortener built w/ Vue, Java, and Spring Boot"/>
    </div>
  </header>

  <main>
    <router-view />
  </main>
</template>

<style scoped>
header {
  line-height: 1.5;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }
}
</style>
