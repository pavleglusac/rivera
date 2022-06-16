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
                <nuxt-link to="/myRentables"><li class="menu-link" :class="currentPage=='adventures' ? 'active' : ''"><font-awesome-icon icon="fish" /> My Adventures</li></nuxt-link>
                <nuxt-link to="/ownerReservations"><li class="menu-link" :class="currentPage=='cottages' ? 'active' : ''"><font-awesome-icon icon="house" />  Reservations</li></nuxt-link>
                 <nuxt-link to="/statReport"><li class="menu-link" :class="currentPage=='profile' ? 'active' : ''"><b-icon icon="bar-chart-line-fill" /> Statistics</li></nuxt-link>
                <nuxt-link to="/owner/profile"><li class="menu-link" :class="currentPage=='profile' ? 'active' : ''"><b-icon icon="person-fill" /> My Profile</li></nuxt-link>
                <li class="menu-link" @click="logout"><b-icon icon="arrow90deg-left" /> Log out</li>
            </ul>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</div>
</template>

<script>
import { BIcon, BIconSearch, BIconCalendarCheckFill, BIconPersonFill, BIconArrow90degLeft, BIconBarChartLineFill } from 'bootstrap-vue'

export default {
  name: 'FishingInstructorNavbar',
  components: {
    BIcon,
    BIconPersonFill,
    BIconSearch,
    BIconCalendarCheckFill,
    BIconArrow90degLeft,
    BIconBarChartLineFill
  },
props: {
    currentPage: {
      type: String,
      required: false,
      default: 'adventures'
    },  
  },
  methods: {
    logout() {
      window.localStorage.setItem('JWT', '');
      this.$store.commit('removeRole');
      this.$router.push({path: "/"});
      this.$nuxt.$emit('refresh-navbar')
    }
  }
};
</script>

<style>
</style>