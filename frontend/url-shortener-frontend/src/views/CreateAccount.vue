<template>
    <div class="container">
        <form @submit.prevent="createAccount">
            <span v-if="error.length > 0"> {{ error }}</span>
            <div>
                <label for="email">Email:</label>
                <input type="text" v-model="newAccount.email" placeholder="email">
            </div>

            <div>
                <label for="password">Password:</label>
                <input type="password" v-model="newAccount.password" placeholder="password">
            </div>

            <div>
                <label for="verify_password">Verify Password:</label>
                <input type="password" v-model="newAccount.verifyPassword" placeholder="verify password">
            </div>

            <button type="submit">Create new account</button>
            <RouterLink to="/login">Cancel</RouterLink>
        </form>
    </div>    
</template>
<script>
import axios from 'axios';
export default {
    data() {
        return {
            newAccount: {
                email: '',
                password: '',
                verifyPassword: ''
            },
            error: ''
        };
    },
    methods: {
        verifyForm() {
            this.error = '';
            const { email, password, verifyPassword } = this.newAccount;
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!email || !emailRegex.test(email)) {
                this.error = 'Please enter a valid email address.';
                return false;
            }
            if (!password || password.length < 8) {
                this.error = 'Password must be at least 8 characters.';
                return false;
            }
            if (password !== verifyPassword) {
                this.error = 'Passwords do not match.';
                return false;
            }
            return true;
        },
        async createAccount() {
            try {
                if(this.verifyForm()) {
                    const newAccount = JSON.stringify(this.newAccount)
                    await axios.post('https://localhost/api/v1/account/register', this.newAccount, {
                        headers: {
                            'Content-Type': 'application/json',
                        }
                    })
                    this.$router.push('/login')
                }
                else throw new Error("Check form fields and try again");
                

            }
            catch(error){
                console.error('Error creating account: ', error)
            }
        }
    }
}
</script>