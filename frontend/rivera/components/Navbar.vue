<template>
<div class="w-100">
  <b-navbar toggleable="sm" fixed="top" class="navbar w-100">
    <div class="logo">
        <img src="@/static/logo_white.png" alt="">
    </div>
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav class="ml-auto" align="end">
        <NuxtLink to="/trending" class="menu-link">Trending</NuxtLink>
        <NuxtLink to="/offers" class="menu-link">Offers</NuxtLink>
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
:root {
  --prime-color: #16C79A;
  --link-text-color: #4F5051;
  --light-blue-color: #11698E;
  --dark-blue-color: #19456B;
  --background-color: #F4F7F8;
  --white-color: #ffffff;
}

.navbar {
    background: linear-gradient(90deg, var(--light-blue-color) 0%, var(--prime-color) 100%);    
    width: 100%;
    padding: 0;
    margin: 0;
    min-width: 100%;
    font-size: 14px;
}
.logo {
    display: flex;
    align-items: center;
    margin-left: 20px;
}
img {
    margin: 0;
    height: 30px;
}
.menu-link {
  display: inline-flex;
  align-items: center;
  padding-left: 15px;
  padding-right: 15px;
  color: var(--white-color);
  text-decoration: none;
}
.active {
    font-weight: bold;
}
</style>