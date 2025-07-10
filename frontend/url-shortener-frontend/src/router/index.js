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

router.beforeEach(async (to, from, next) => {
  if (from.path === '/login' && to.path === '/dashboard') {
    try {
      await axios.get('https://localhost/api/v1/auth/status', { withCredentials: true });
      next();
    } catch (err) {
      next('/login');
    }
  } else {
    next();
  }
});

export default router;
