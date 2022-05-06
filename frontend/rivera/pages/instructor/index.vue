<template>
  <div>
    <FishingInstructorNavbar :currentPage="'adventures'" />
    <p>My adventures</p>
  </div>
</template>

<script>
import FishingInstructorNavbar from '../../components/navbars/FishingInstructorNavbar.vue';

export default {
  components: { FishingInstructorNavbar },
}

</script>