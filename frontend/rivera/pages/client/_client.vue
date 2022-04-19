<template>
  <div>
    <client-navbar/>  
    <client-profile/>
  </div>
</template>

<script>
import ClientNavbar from '../../components/client/ClientNavbar.vue'
import ClientProfile from '../../components/client/ClientProfile.vue'
export default {
  components: { ClientNavbar, ClientProfile },

}
</script>

<style>

</style>