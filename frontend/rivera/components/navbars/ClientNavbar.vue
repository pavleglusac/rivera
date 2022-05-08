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
                <nuxt-link to="/recommended"><li class="menu-link" :class="currentPage=='recommended' ? 'active' : ''"><b-icon icon="star-fill" /> Recommended</li></nuxt-link>
                <nuxt-link to="/offers"><li class="menu-link" :class="currentPage=='offers' ? 'active' : ''"><b-icon icon="search" /> Offers</li></nuxt-link>
                <nuxt-link to="/reservations"><li class="menu-link" :class="currentPage=='reservations' ? 'active' : ''"><b-icon icon="calendar-check-fill" /> Reservations</li></nuxt-link>
                <nuxt-link to="/subscribed"><li class="menu-link" :class="currentPage=='subscribed' ? 'active' : ''"><b-icon icon="bell-fill" /> Subscribed</li></nuxt-link>
                <nuxt-link to="/client/profile"><li class="menu-link" :class="currentPage=='profile' ? 'active' : ''"><b-icon icon="person-fill" /> My Profile</li></nuxt-link>
                <li class="menu-link"><b-icon icon="arrow90deg-left" /> Log out</li>
            </ul>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</div>
</template>

<script>
import { BIcon, BIconStarFill, BIconSearch, BIconPersonFill, BIconBellFill, BIconCalendarCheckFill, BIconArrow90degLeft } from 'bootstrap-vue'

export default {
  name: 'ClientNavbar',
  components: {
    BIcon,
    BIconStarFill,
    BIconSearch,
    BIconPersonFill,
    BIconCalendarCheckFill,
    BIconBellFill,
    BIconArrow90degLeft
  },
props: {
    currentPage: {
      type: String,
      required: false,
      default: 'recommended'
    },  
  }
};
</script>

<style>
</style>