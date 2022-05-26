<template>
<div v-if="is_data_fetched">
    <div class="col-12 section-intro text-center mt-5" data-aos="fade-up">
        <h1>Last minute offers</h1>
        <div class="divider"></div>
    </div>
    <b-container class="bv-example-row" data-aos="zoom-in">
        <b-row>
            <entity-on-discount
              v-for="e in lastMinuteOffers"
              :entity="e"
              :openModal="openModal"
            />
        </b-row>
    </b-container>
    <div class="col-12 section-intro text-center mt-5" data-aos="fade-up">
        <h1>Most popular offers</h1> 
        <div class="divider"></div>
    </div>
    <b-container class="bv-example-row" data-aos="zoom-in">
        <b-row>
            <entity-on-discount
              v-for="e in popularOffers"
              :entity="e"
              :openModal="openModal"
            />
        </b-row>
    </b-container>
    <div class="col-12 section-intro text-center mt-5" data-aos="fade-up">
        <h1>Best price</h1> 
        <div class="divider"></div>
    </div>
    <b-container class="bv-example-row" data-aos="zoom-in">
        <b-row>
            <entity-on-discount
              v-for="e in bestPriceOffers"
              :entity="e"
              :openModal="openModal"
            />
        </b-row>
    </b-container>
    <div class="col-12 section-intro text-center mt-5" data-aos="fade-up">
        <h1>Recommended</h1> 
        <div class="divider"></div>
    </div>
    <b-container class="bv-example-row" data-aos="zoom-in">
        <b-row>
            <entity-on-discount
              v-for="e in recommendedOffers"
              :entity="e"
              :openModal="openModal"
            />
        </b-row>
    </b-container>
    <b-modal id="reservedModal" title="Congratulations!">
      <p class="my-4">You have successfully reserved your appointment.</p>
    </b-modal>
</div>
</template>

<script>
import EntityOnDiscount from './EntityOnDiscount.vue'
import AOS from "aos";
import "aos/dist/aos.css";
export default {
  components: { EntityOnDiscount },
  data() {
    return {
      lastMinuteOffers: [],
      recommendedOffers: [],
      popularOffers: [],
      bestPriceOffers: [],
      is_data_fetched: false
    };
  },
  mounted() {
    AOS.init();
    this.loadLastMinuteOffers();
    this.loadRecommended();
    this.loadPopular();
    this.loadBestPrice();
  },
   methods: {
    loadLastMinuteOffers() {
      let that = this;
      that.lastMinuteOffers = [];
      this.$axios.get(`/api/last-minute`)
      .then(response => {
        that.lastMinuteOffers = response.data;
        that.is_data_fetched = true;
      });
    },
    loadRecommended() {
      let that = this;
      that.recommendedOffers = [];
      this.$axios.get(`/api/recommended`)
      .then(response => {
        that.recommendedOffers = response.data;
      });
    },
    loadPopular() {
      let that = this;
      that.popularOffers = [];
      this.$axios.get(`/api/most-popular`)
      .then(response => {
        that.popularOffers = response.data;
      });
    },
    loadBestPrice() {
      let that = this;
      that.bestPriceOffers = [];
      this.$axios.get(`/api/best-price`)
      .then(response => {
        that.bestPriceOffers = response.data;
      });
    },
    openModal() {
      this.$bvModal.show('reservedModal');
    }
   }
}
</script>

<style>

</style>