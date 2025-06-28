<!--This component will go within the Dashboard component-->
<script>
import axios from 'axios';
export default {
    data() {
        return {
            urls: [], 
        };
    },
    mounted() {
        axios.get('https://localhost/api/v1/feed/recent', {
            withCredentials: true
        })
            .then(response => {
                this.urls = response.data;
            })
            .catch(error => {
                console.log('Error fetching urls: ', error);
            });
    }
    
}
</script>
<template>
    <div>
        <h3>Last 10 Global Urls Shortened:</h3>
        <div v-for="url in urls" class="result-row">
        <div class="result-column-left">
            <ul>Short URL: {{ url.shortUrl }}, Original URL: {{ url.longUrl }}</ul>
        </div>
        </div>
    </div>
</template>
<style scoped>
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