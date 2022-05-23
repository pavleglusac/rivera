export default function({ $axios, isClient }) {
    $axios.onRequest(config => {
        config.headers['Authorization'] = `Bearer ${window.localStorage.getItem('JWT')}`
    })
}