import { createStore } from "vuex";
import createPersistedState from 'vuex-persistedstate'
import user from './modules/user';

const store = createStore ({
    modules: {
        user
    },
    plugins: [createPersistedState()]
})

export default store