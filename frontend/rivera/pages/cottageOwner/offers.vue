<template>
  <div>
    <CottageOwnerNavbar :currentPage="'offers'" />
    <offers/>
  </div>
</template>

<script>
import CottageOwnerNavbar from '../../components/navbars/CottageOwnerNavbar.vue';
import Offers from '../../components/offers.vue';

export default {
  components: { CottageOwnerNavbar, Offers },
}

</script>