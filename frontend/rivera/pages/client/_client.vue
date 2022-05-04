<template>
  <div>
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
                    <li class="menu-link" :class="currentlyActive=='trending' ? 'active' : ''" v-on:click="showTrending"><b-icon icon="star-fill" /> Trending</li>
                    <li class="menu-link" :class="currentlyActive=='offers' ? 'active' : ''" v-on:click="showOffers"><b-icon icon="search" /> Offers</li>
                    <li class="menu-link" :class="currentlyActive=='reservations' ? 'active' : ''" v-on:click="showReservations"><b-icon icon="calendar-check-fill" /> Reservations</li>
                    <li class="menu-link" :class="currentlyActive=='subscribed' ? 'active' : ''" v-on:click="showSubscribed"><b-icon icon="bell-fill" /> Subscribed</li>
                    <li class="menu-link" :class="currentlyActive=='profile' ? 'active' : ''" v-on:click="showProfile"><b-icon icon="person-fill" /> My Profile</li>
                    <li class="menu-link"><b-icon icon="arrow90deg-left" /> Log out</li>
                </ul>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </div>
    <client-profile v-if="currentlyActive=='profile'"/>
    <offers v-if="currentlyActive=='offers'"/>
    <trending v-if="currentlyActive=='trending'"/>
    <subscribed v-if="currentlyActive=='subscribed'"/>
    <reservations v-if="currentlyActive=='reservations'"/>
  </div>
</template>

<script>
import ClientProfile from '../../components/client/ClientProfile.vue'
import Offers from '../../components/offers.vue'
import Trending from '../../components/trending.vue'
import Subscribed from './subscribed.vue'
import Reservations from '../../components/client/reservations.vue'
import { BIcon, BIconStarFill, BIconSearch, BIconCalendarCheckFill, BIconBellFill, BIconPersonFill, BIconArrow90degLeft } from 'bootstrap-vue'

export default {
  components: { 
    ClientProfile, 
    Offers, 
    Trending, 
    BIcon,
    BIconStarFill,
    BIconSearch, 
    BIconCalendarCheckFill,
    BIconBellFill,
    BIconPersonFill,
    BIconArrow90degLeft,
    Subscribed,
    Reservations },
  data() {
    return {
      currentlyActive: "trending"
    }
  },
  methods: {
    showOffers() {
      this.currentlyActive = "offers";
    },
    showProfile() {
      this.currentlyActive = "profile";
    },
    showTrending() {
      this.currentlyActive = "trending";
    },
    showReservations() {
      this.currentlyActive = "reservations";
    },
    showSubscribed() {
      this.currentlyActive = "subscribed";
    },
  }
}
</script>

<style>

</style>