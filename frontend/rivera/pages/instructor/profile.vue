<template>
  <div>
    <FishingInstructorNavbar :currentPage="'profile'" />
    <p>My profile</p>
  </div>
</template>

<script>
import FishingInstructorNavbar from '../../components/navbars/FishingInstructorNavbar.vue';

export default {
  components: { FishingInstructorNavbar },
}

</script>