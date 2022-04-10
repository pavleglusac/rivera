<template>
<div class="w-100">
  <b-navbar toggleable="sm" class="navbar w-100">
    <div class="logo">
        <img src="@/static/logo_white.png" alt="">
    </div>
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav class="ml-auto" align="end">
            <ul>
      <li><NuxtLink to="/trending" exact class="menu-link">Trending</NuxtLink></li>
      <li><NuxtLink to="/offers" class="menu-link">Offers</NuxtLink></li>
    </ul>
        <b-nav-item><b-button size="sm" variant="outline-light">Log in</b-button></b-nav-item>
        <b-nav-item><b-button size="sm" variant="light">Create an account</b-button></b-nav-item>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</div>
</template>

<script>
export default {
data() {
    return {
    }
},
methods: {
},
    computed: {
    },
};
</script>

<style>


</style>