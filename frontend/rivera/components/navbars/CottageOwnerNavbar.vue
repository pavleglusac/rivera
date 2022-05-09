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
                <nuxt-link to="/cottages"><li class="menu-link" :class="currentPage=='cottages' ? 'active' : ''"><font-awesome-icon icon="house" /> My Cottages</li></nuxt-link>
                <nuxt-link to="/cottageOwner/profile"><li class="menu-link" :class="currentPage=='profile' ? 'active' : ''"><b-icon icon="person-fill" /> My Profile</li></nuxt-link>
                <li class="menu-link"><b-icon icon="arrow90deg-left" /> Log out</li>
            </ul>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</div>
</template>

<script>
import { BIcon, BIconSearch, BIconCalendarCheckFill, BIconPersonFill, BIconArrow90degLeft } from 'bootstrap-vue'

export default {
  name: 'CottageOwnerNavbar',
  components: {
    BIcon,
    BIconPersonFill,
    BIconSearch,
    BIconCalendarCheckFill,
    BIconArrow90degLeft
  },
  props: {
    currentPage: {
      type: String,
      required: false,
      default: 'cottages'
    },  
  }
};
</script>

<style>
</style>