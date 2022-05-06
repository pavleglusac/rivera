<template>
  <div>
    <UnauthenticatedNavbar :currentPage="'offers'" />
    <offers/>
  </div>
</template>

<script>
import UnauthenticatedNavbar from '../components/navbars/UnauthenticatedNavbar.vue';
import Offers from '../components/offers.vue';

export default {
  components: { UnauthenticatedNavbar, Offers },
}

</script>