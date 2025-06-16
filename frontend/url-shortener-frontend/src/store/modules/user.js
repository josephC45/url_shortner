export default {
    namespaced: true,
    state: () => ({
        username: ''
    }),
    mutations: {
        setUsername(state, username) {
            state.username = username
        }
    },
    actions: {
        updateUsername({ commit }, username) {
            commit('setUsername', username)
        }
    },
    getters: {
        getUsername: (state) => state.username
    }
}