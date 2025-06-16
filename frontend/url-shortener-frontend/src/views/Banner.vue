<script>
import { mapGetters } from 'vuex';
export default {
  props: {
    msg: {
      type: String,
      required: true,
    },
  },
  computed: {
    ...mapGetters('user', ['getUsername']),
    username() {
      return this.getUsername
    }
  }
}
</script>

<template>
  <div class="greetings">
    <h1 class="green">{{ msg }}</h1>
    <h3>Author: Joseph Curto</h3>
    <h2 v-if="username"> Welcome, {{ username }}</h2>
    <h2 v-else="username"> Welcome, Guest</h2>
  </div>
</template>

<style scoped>
h1 {
  font-weight: 500;
  font-size: 2.6rem;
  position: relative;
  top: -10px;
}

h3 {
  font-size: 1.2rem;
}

.greetings h1,
.greetings h3 {
  text-align: center;
}

@media (min-width: 1024px) {
  .greetings h1,
  .greetings h3 {
    text-align: left;
  }
}
</style>
