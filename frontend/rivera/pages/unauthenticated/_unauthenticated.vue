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
                    <b-nav-item><b-button size="sm" variant="outline-light">Log in</b-button></b-nav-item>
                    <b-nav-item><b-button size="sm" variant="light">Create an account</b-button></b-nav-item>
                </ul>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </div>
    <offers v-if="currentlyActive=='offers'"/>
    <trending v-if="currentlyActive=='trending'"/>
  </div>
</template>

<script>
import Offers from '../../components/offers.vue'
import Trending from '../../components/trending.vue'
import { BIcon, BIconStarFill, BIconSearch } from 'bootstrap-vue'

export default {
  components: { 
    Offers, 
    Trending, 
    BIcon,
    BIconStarFill,
    BIconSearch
  },
  data() {
    return {
      currentlyActive: "offers"
    }
  },
  methods: {
    showOffers() {
      this.currentlyActive = "offers";
    },
    showTrending() {
      this.currentlyActive = "trending";
    },
  }
}
</script>

<style>

</style>