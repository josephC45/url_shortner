<template>
    <div class="container">
        <form @submit.prevent="login">
            <span v-if="error.length > 0"> {{ error }}</span>
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
            },
            error: ''
        };
    },
    methods: {
        ...mapActions('user', ['updateUsername']),
        verifyForm() {
            this.error = '';
            const { email, password } = this.user_credentials;
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!email || !emailRegex.test(email)) {
                this.error = 'Please enter a valid email address.';
                return false;
            }
            if (!password || password.length < 8) {
                this.error = 'Password must be at least 8 characters.';
                return false;
            }
            return true;
        },
        async login() {
            try {
                if(this.verifyForm()) {
                    const user_cred = JSON.stringify(this.user_credentials)
                    await axios.post('https://localhost/api/v1/auth/login', user_cred, {
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        withCredentials: true
                    })
                    await axios.get('https://localhost/api/v1/auth/status', { withCredentials: true });
                    this.$router.push('/dashboard')
                    this.updateUsername(this.user_credentials.email)
                }
                else throw new Error("Check form fields and try again");
            }
            catch (error){
                console.error('Error logging in:', error)
            }
        }
    }
}
</script>