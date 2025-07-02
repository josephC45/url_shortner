<script setup>
import { ref } from 'vue'
import axios from 'axios'
import CachedUrls from './CachedUrls.vue'

const url = ref('')
const data = ref(null)
const error = ref('')

const verifyForm = () => {
  error.value = '';
  if(url.value.trim.length === 0) { 
    error.value = 'Please enter a valid URL';
    return false;
  }
  return true;
}

const postData = async () => {
  try {
    if(verifyForm()) {
      const longUrlJsonPayload = JSON.stringify(url.value)
      const response = await axios.post('https://localhost/api/v1/urls', longUrlJsonPayload, {
        withCredentials: true,
        headers: {
          'Content-Type': 'application/json',
        }
      })
      data.value = response.data
    }
    else throw new Error("Check form fields and try again");
  } catch (error) {
    console.error('Error posting data:', error)
  }
}
</script>

<template>
  <div class="container">
    <form class="form" @submit.prevent="postData">
      <span v-if="error.length > 0"> {{ error }}</span>
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
    <CachedUrls/>
  </div>
</template>

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
</style>
