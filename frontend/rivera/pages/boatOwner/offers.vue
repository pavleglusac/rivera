<template>
  <div>
    <BoatOwnerNavbar :currentPage="'offers'" />
    <offers/>
  </div>
</template>

<script>
import BoatOwnerNavbar from '../../components/navbars/BoatOwnerNavbar.vue';
import Offers from '../../components/offers.vue';

export default {
  components: { BoatOwnerNavbar, Offers },
}

</script>