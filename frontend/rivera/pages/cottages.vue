<template>
  <div>
    <Navbar :currentPage="'cottages'" />
    <p>List of my cottages</p>
  </div>
</template>

<script>
import Navbar from '../components/navbar.vue';

export default {
  components: { Navbar },
}

</script>