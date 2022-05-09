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
                <nuxt-link to="/"><li class="menu-link" :class="currentPage=='trending' ? 'active' : ''"><font-awesome-icon icon="house" /> Homepage</li></nuxt-link>
                <nuxt-link to="/offers"><li class="menu-link" :class="currentPage=='offers' ? 'active' : ''"><b-icon icon="search" /> Offers</li></nuxt-link>
                <b-nav-item><nuxt-link to="/login"><b-button size="sm" variant="outline-light">Log in</b-button></nuxt-link></b-nav-item>
                <b-nav-item><nuxt-link to="/registration"><b-button size="sm" variant="light">Create an account</b-button></nuxt-link></b-nav-item>
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
  },
  props: {
    currentPage: {
      type: String,
      required: false,
      default: 'trending'
    },  
  }
};
</script>

<style>
</style>