<template>
    <div class="container">
        <form @submit.prevent="createAccount">
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
        };
    },
    methods: {
        async createAccount() {
            try {
                const newAccount = JSON.stringify(this.newAccount)
                await axios.post('https://localhost/api/v1/account/register', newAccount, {
                    headers: {
                        'Content-Type': 'application/json',
                    }
                })
                this.$router.push('/login')

            }
            catch(error){
                console.error('Error creating account: ', error)
            }
        }
    }
}
</script>