<template>
  <div>
    <FishingInstructorNavbar :currentPage="'offers'" />
    <offers/>
  </div>
</template>

<script>
import FishingInstructorNavbar from '../../components/navbars/FishingInstructorNavbar.vue';
import Offers from '../../components/offers.vue';

export default {
  components: { FishingInstructorNavbar, Offers },
}

</script>