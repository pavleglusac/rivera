<template>
<div>
    <navbar :currentPage="'reservations'"/>
    <reservations/>    
</div>  
</template>

<script>
import Reservations from '@/components/client/reservations.vue';
import Navbar from '../components/navbar.vue';
export default {
  components: { Reservations, Navbar },

}
</script>

<style>

</style>