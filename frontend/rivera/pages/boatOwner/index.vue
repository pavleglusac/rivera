<template>
  <div>
    <BoatOwnerNavbar :currentPage="'boats'" />
    <p>List of my boats</p>
  </div>
</template>

<script>
import BoatOwnerNavbar from '../../components/navbars/BoatOwnerNavbar.vue';

export default {
  components: { BoatOwnerNavbar },
}

</script>