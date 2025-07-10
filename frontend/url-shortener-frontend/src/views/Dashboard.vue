<template>
  <div class="container">
    <form class="form" @submit.prevent="postData">
      <span v-if="error.length > 0">{{ error }}</span>
      <input
        v-model="url"
        type="text"
        class="input"
        placeholder="Enter a long URL"
      />
      <button type="submit" class="button">Shorten</button>
    </form>

    <div v-if="data" class="result-row">
      <div class="result-column-left">
        <p><strong>Shortened URL:</strong></p>
        <p>{{ data.shortUrl }}</p>
      </div>
      <div class="result-column-right">
        <p><strong>Original URL:</strong></p>
        <p>{{ data.longUrl }}</p>
      </div>
    </div>
    <CachedUrls />
    <a href="#" class="button" @click.prevent="logout">Logout</a>
  </div>
</template>

<script>
import axios from 'axios'
import CachedUrls from './CachedUrls.vue'
import { mapActions } from 'vuex'

export default {
  components: {
    CachedUrls
  },
  data() {
    return {
      url: '',
      data: null,
      error: ''
    }
  },
  methods: {
    ...mapActions('user', ['updateUsername']),
    verifyForm() {
      this.error = ''
      if (this.url.trim().length === 0) {
        this.error = 'Please enter a valid URL'
        return false
      }
      return true
    },
    async postData() {
      try {
        if (this.verifyForm()) {
          const longUrlJsonPayload = JSON.stringify(this.url)
          const response = await axios.post(
            'https://localhost/api/v1/urls',
            longUrlJsonPayload,
            {
              withCredentials: true,
              headers: {
                'Content-Type': 'application/json'
              }
            }
          )
          this.data = response.data
        } else {
          throw new Error('Check form fields and try again')
        }
      } catch (error) {
        console.error('Error posting data:', error)
        this.error = 'Failed to shorten URL. Please try again.'
      }
    },
    async logout() {
      try {
        await axios.post(
          'https://localhost/api/v1/auth/logout', {}, { withCredentials: true }
        )
        this.updateUsername('')
        this.$router.push('/login')
      } catch (error) {
        console.error('Logout failed', error)
      }
    }
  }
}
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 25vh;
}

.form {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 20px;
}

.input {
  padding: 10px;
  width: 600px;
  font-size: 16px;
}

.button {
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
}

.result-row {
  display: flex;
  justify-content: space-between;
  gap: 40px;
  width: 100%;
  max-width: 700px;
}

.result-column-left {
  flex: 1;
  text-align: left;
}

.result-column-right {
  flex: 1;
  text-align: left;
}
</style>
