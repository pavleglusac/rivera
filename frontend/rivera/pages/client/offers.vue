<template>
  <div>
    <ClientNavbar :currentPage="'offers'" />
    <offers/>
  </div>
</template>

<script>
import ClientNavbar from '../../components/navbars/ClientNavbar.vue';
import Offers from '../../components/offers.vue';

export default {
  components: { ClientNavbar, Offers },
}

</script>