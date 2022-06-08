<template>
<div>
    <unauthenticated-navbar v-if="role=='unauthenticated'" :currentPage="currentPage" />
    <client-navbar v-if="role=='ROLE_CLIENT'" :currentPage="currentPage" />
    <boat-owner-navbar v-if="role=='ROLE_BOAT_OWNER'" :currentPage="currentPage" />
    <cottage-owner-navbar v-if="role=='ROLE_COTTAGE_OWNER'" :currentPage="currentPage" />
    <fishing-instructor-navbar v-if="role=='ROLE_FISHING_INSTRUCTOR'" :currentPage="currentPage" />
    <admin-navbar v-if="role=='ROLE_ADMIN'" :currentPage="currentPage" />
</div>
</template>

<script>
import UnauthenticatedNavbar from './navbars/UnauthenticatedNavbar.vue';
import ClientNavbar from './navbars/ClientNavbar.vue';
import BoatOwnerNavbar from './navbars/BoatOwnerNavbar.vue';
import CottageOwnerNavbar from './navbars/CottageOwnerNavbar.vue';
import FishingInstructorNavbar from './navbars/FishingInstructorNavbar.vue';
import AdminNavbar from './navbars/AdminNavbar.vue';
export default {
  components: { UnauthenticatedNavbar, ClientNavbar, BoatOwnerNavbar, FishingInstructorNavbar, CottageOwnerNavbar, AdminNavbar, },
    data() {
        return {
            role: "unauthenticated",
        }
    },
    props: {
        currentPage: {
            type: String,
            required: false,
            default: ''
        },  
    },
    methods: {
        getRole() {
            let that = this;
            let role = this.$store.getters.getRole;
            if(role == 'unauthenticated') {
                console.log(window.localStorage.getItem("JWT"));
                this.$axios.get('/api/auth/getRole').then((resp) => {
                    that.role = resp.data;
                    that.$store.commit('setRole', resp.data);
                }).catch((err) => {
                    console.log(err);
                });
            } else {
                this.role = role;
            }
        },
    },
    mounted() {
        this.getRole();
    },
}
</script>

<style>

</style>