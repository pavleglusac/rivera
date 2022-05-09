<template>
  <div>
    <navbar :currentPage="'offers'" />
    <offers/>
  </div>
</template>

<script>
import Navbar from '../components/navbar.vue';
import Offers from '../components/offers.vue';

export default {
  components: { Offers, Navbar },
}

</script>