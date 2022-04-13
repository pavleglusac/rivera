<template>
<div class="w-100">
  <b-navbar toggleable="lg" class="navbar w-100">
    <div class="logo">
        <img src="@/static/logo_white.png" alt="">
    </div>
    <b-navbar-toggle target="nav-collapse">
      <template>
        <b-icon icon="justify-right" style="border: none; color: white;"></b-icon>
      </template>
    </b-navbar-toggle>
    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav class="ml-auto my-auto" align="end">
            <ul>
                <li><NuxtLink to="/trending" exact class="menu-link"><b-icon icon="star-fill" /> Trending</NuxtLink></li>
                <li><NuxtLink to="/offers" class="menu-link"><b-icon icon="search" /> Offers</NuxtLink></li>
                <b-nav-item><b-button size="sm" variant="outline-light">Log in</b-button></b-nav-item>
                <b-nav-item><b-button size="sm" variant="light">Create an account</b-button></b-nav-item>
            </ul>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</div>
</template>

<script>
import { BIcon, BIconStarFill, BIconSearch } from 'bootstrap-vue'

export default {
  name: 'UnauthenticatedNavbar',
  components: {
    BIcon,
    BIconStarFill,
    BIconSearch
  }
};
</script>

<style>
</style>