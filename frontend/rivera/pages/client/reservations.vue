<template>
<div>
    <ClientNavbar :currentPage="'reservations'"/>
    <Reservations/>    
</div>  
</template>

<script>
import Reservations from '../../components/client/Reservations.vue';
import ClientNavbar from '../../components/navbars/ClientNavbar.vue';
export default {
  components: { Reservations, ClientNavbar },

}
</script>

<style>

</style>