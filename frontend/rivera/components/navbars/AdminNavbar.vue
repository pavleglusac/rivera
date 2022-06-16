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
                <nuxt-link to="/"><li class="menu-link" :class="currentPage=='trending' ? 'active' : ''"><font-awesome-icon icon="house" /> Home</li></nuxt-link>
                <nuxt-link to="/admin/reports"><li class="menu-link" :class="currentPage=='reports' ? 'active' : ''"><font-awesome-icon icon="id-card" /> Reports</li></nuxt-link>
                <nuxt-link to="/admin/reviews"><li class="menu-link" :class="currentPage=='reviews' ? 'active' : ''"><font-awesome-icon icon="id-card" /> Reviews</li></nuxt-link>
                <nuxt-link to="/admin/registrate"><li class="menu-link" :class="currentPage=='registrate' ? 'active' : ''"><font-awesome-icon icon="id-card" /> Registrate admins</li></nuxt-link>
                <nuxt-link to="/admin/terminations"><li class="menu-link" :class="currentPage=='terminations' ? 'active' : ''"><font-awesome-icon icon="user-minus" /> Terminations</li></nuxt-link>
                <nuxt-link to="/admin/registrations"><li class="menu-link" :class="currentPage=='registrations' ? 'active' : ''"><font-awesome-icon icon="user-plus" /> Registrations</li></nuxt-link>
                <nuxt-link to="/admin/stats"><li class="menu-link" :class="currentPage=='stats' ? 'active' : ''"><font-awesome-icon icon="chart-line" /> Income</li></nuxt-link>
                <nuxt-link to="/admin/rules"><li class="menu-link" :class="currentPage=='rules' ? 'active' : ''"><font-awesome-icon icon="briefcase" /> Site rules</li></nuxt-link>
                <nuxt-link to="/admin/profile"><li class="menu-link" :class="currentPage=='profile' ? 'active' : ''"><b-icon icon="person-fill" /> My Profile</li></nuxt-link>
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