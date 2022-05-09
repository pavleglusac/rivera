<template>
  <div>
    <Navbar :currentPage="'boats'" />
    <p>List of my boats</p>
  </div>
</template>

<script>
import Navbar from '../components/navbar.vue';

export default {
  components: { Navbar },
}

</script>