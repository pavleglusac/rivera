<template>
  <div>
    <Navbar :currentPage="'profile'" />
    <p>My profile</p>
  </div>
</template>

<script>
import Navbar from '../../components/navbar.vue';

export default {
  components: { Navbar },
}

</script>