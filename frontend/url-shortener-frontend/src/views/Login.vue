<template>
    <div class="container">
        <form @submit.prevent="login">
            <div>
                <label for="email">Email:</label>
                <input type="text" v-model="user_credentials.email" placeholder="email">
            </div>

            <div>
                <label for="password">Password:</label>
                <input type="password" v-model="user_credentials.password" placeholder="password">
            </div>

            <button type="submit">Login</button>
            <RouterLink to="/create_account">Create new account</RouterLink>
        </form>
    </div>
</template>
<script>
import axios from 'axios';
import { mapActions } from 'vuex';
export default {
    data() {
        return {
            user_credentials: {
                email: '',
                password: ''
            }
        };
    },
    methods: {
        ...mapActions('user', ['updateUsername']),
        async login() {
            try {
                const user_cred = JSON.stringify(this.user_credentials)
                await axios.post('https://localhost/api/v1/auth/login', user_cred, {
                    headers: {
                        'Content-Type': 'application/json',
                    },
                })
                this.updateUsername(this.user_credentials.email)
                this.$router.push('/dashboard')
            }
            catch (error){
                console.error('Error logging in:', error)
            }
        }
    }
}
</script>