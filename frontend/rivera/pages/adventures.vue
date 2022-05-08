<template>
  <div>
    <navbar :currentPage="'adventures'" />
    <p>My adventures</p>
  </div>
</template>

<script>
import Navbar from '../components/navbar.vue';

export default {
  components: { Navbar },
}

</script>