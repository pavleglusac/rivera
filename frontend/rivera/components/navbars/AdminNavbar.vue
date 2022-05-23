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
                <nuxt-link to="/recommended"><li class="menu-link" :class="currentPage=='recommended' ? 'active' : ''"><font-awesome-icon icon="id-card" /> Registrate admins</li></nuxt-link>
                <nuxt-link to="/admin/terminations"><li class="menu-link" :class="currentPage=='terminations' ? 'active' : ''"><font-awesome-icon icon="user-minus" /> Termination requests</li></nuxt-link>
                <nuxt-link to="/admin/registrations"><li class="menu-link" :class="currentPage=='registrations' ? 'active' : ''"><font-awesome-icon icon="user-plus" /> Registration requests</li></nuxt-link>
                <nuxt-link to="/admin/rules"><li class="menu-link" :class="currentPage=='rules' ? 'active' : ''"><font-awesome-icon icon="briefcase" /> Site rules</li></nuxt-link>
                <li class="menu-link"><b-icon icon="arrow90deg-left" /> Log out</li>
            </ul>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</div>
</template>

<script>
import { BIcon, BIconSearch, BIconPersonFill, BIconBellFill, BIconCalendarCheckFill, BIconArrow90degLeft } from 'bootstrap-vue'

export default {
  name: 'ClientNavbar',
  components: {
    BIcon,
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