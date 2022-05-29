export const state = () => ({
    role: 'unauthenticated'
  })
  
export const mutations = {
    setRole(state, name) {
        state.role = name
    },
    removeRole(state) {
        state.role = 'unauthenticated'
    },
}

export const getters = {
    getRole(state) {
        return state.role
    },
}
  