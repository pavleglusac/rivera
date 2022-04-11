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
                <li><NuxtLink to="/client/reservations" class="menu-link"><b-icon icon="calendar-check-fill" /> Reservations</NuxtLink></li>
                <li><NuxtLink to="/client/subscribed" class="menu-link"><b-icon icon="bell-fill" /> Subscribed</NuxtLink></li>
                <li><NuxtLink to="/client/client-profile" class="menu-link"><b-icon icon="person-fill" /> My Profile</NuxtLink></li>
                <li><NuxtLink to="/log-in" class="menu-link"><b-icon icon="arrow90deg-left" /> Log out</NuxtLink></li>
            </ul>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</div>
</template>

<script>
import { BIcon, BIconStarFill, BIconSearch, BIconCalendarCheckFill, BIconBellFill, BIconPersonFill, BIconArrow90degLeft } from 'bootstrap-vue'
export default {
  components: {
    BIcon,
    BIconStarFill,
    BIconSearch, 
    BIconCalendarCheckFill,
    BIconBellFill,
    BIconPersonFill,
    BIconArrow90degLeft
  }
};
</script>

<style>
</style>