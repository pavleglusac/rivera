<template>
<div>
    <ClientNavbar :currentPage="'profile'"/>
    <ClientProfile/>
</div>  
</template>

<script>
import ClientNavbar from '../../components/navbars/ClientNavbar.vue';
import ClientProfile from '../../components/client/ClientProfile.vue';
export default {
  components: { ClientNavbar },

}
</script>

<style>

</style>