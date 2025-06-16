import { createRouter, createWebHistory } from 'vue-router';
import Dashboard from '@/views/Dashboard.vue';
import CreateAccount from '@/views/CreateAccount.vue';
import Login from '@/views/Login.vue';

const routes = [
  { path: '', component: Login },
  { path: '/login', component: Login },
  { path: '/create_account', component: CreateAccount},
  { path: '/dashboard', component: Dashboard },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
    const isAuthenticated = document.cookie.includes('jwt');
    if(from.path === '/login' && to.path === '/dashboard' && !isAuthenticated) {
        next('/login');
    } 
    else if(to.path === '/dashboard' && isAuthenticated) {
      next('/dashboard')
    }
    else next()
});

export default router;
