<template>
<div>
    <Navbar :currentPage="'subscribed'"/>
    <p>Subscribed</p>    
</div>  
</template>

<script>
import Navbar from '../components/navbar.vue';
export default {
  components: { Navbar },

}
</script>

<style>

</style>