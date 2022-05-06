<template>
<div>
    <ClientNavbar :currentPage="'trending'"/>
    <p>Client Trending</p>    
</div>  
</template>

<script>
import ClientNavbar from '../../components/navbars/ClientNavbar.vue';
export default {
    components: { ClientNavbar  },
    name: "ClientTrending"
}
</script>

<style>

</style>