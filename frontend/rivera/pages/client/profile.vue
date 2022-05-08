<template>
<div>
    <Navbar :currentPage="'profile'"/>
    <ClientProfile/>
</div>  
</template>

<script>
import Navbar from '../../components/navbar.vue';
import ClientProfile from '../../components/client/ClientProfile.vue';
export default {
  components: { Navbar, ClientProfile },

}
</script>

<style>

</style>