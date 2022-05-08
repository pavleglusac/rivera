<template>
<div>
    <unauthenticated-navbar v-if="role=='unauthenticated'" :currentPage="currentPage" />
    <client-navbar v-if="role=='client'" :currentPage="currentPage" />
    <boat-owner-navbar v-if="role=='boatOwner'" :currentPage="currentPage" />
    <cottage-owner-navbar v-if="role=='cottageOwner'" :currentPage="currentPage" />
    <fishing-instructor-navbar v-if="role=='instructor'" :currentPage="currentPage" />
</div>
</template>

<script>
import UnauthenticatedNavbar from './navbars/UnauthenticatedNavbar.vue';
import ClientNavbar from './navbars/ClientNavbar.vue';
import BoatOwnerNavbar from './navbars/BoatOwnerNavbar.vue';
import CottageOwnerNavbar from './navbars/CottageOwnerNavbar.vue';
import FishingInstructorNavbar from './navbars/FishingInstructorNavbar.vue';
export default {
  components: { UnauthenticatedNavbar, ClientNavbar, BoatOwnerNavbar, FishingInstructorNavbar, CottageOwnerNavbar },
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
            console.log(window.localStorage.getItem("JWT"));
            this.$axios.get('/api/auth/getRole', {
                                headers: { 'Authorization' : 'Bearer ' + window.localStorage.getItem("JWT") } 
                            }).then((resp) => {
                console.log(that.role);
                that.role = resp.data;
            }).catch((err) => {
                console.log(err);
            });
        },
    },
    mounted() {
        this.getRole();
    },
}
</script>

<style>

</style>