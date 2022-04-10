<template>
  <div class="container">
    <navbar/>
  </div>
</template>

<script>
import Navbar from '../components/Navbar.vue';
export default {
  components: { Navbar },
  name: 'IndexPage'
}
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700;900&display=swap");
body {
  font-family: "Roboto", sans-serif;
  display: flex;
  justify-content: center;
  align-items: center;
}

.container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
}
</style>