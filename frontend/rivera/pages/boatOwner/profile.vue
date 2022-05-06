<template>
  <div>
    <BoatOwnerNavbar :currentPage="'profile'" />
    <p>My profile</p>
  </div>
</template>

<script>
import BoatOwnerNavbar from '../../components/navbars/BoatOwnerNavbar.vue';

export default {
  components: { BoatOwnerNavbar },
}

</script>