<template>
  <div v-if="is_data_fetched">
    <div class="col-12 section-intro text-center mt-5" data-aos="fade-up">
      <h1>Last minute offers</h1>
      <div class="divider"></div>
    </div>
    <b-container class="bv-example-row" data-aos="zoom-in">
      <b-row>
        <entity-on-discount
          v-for="(e, index) in lastMinuteOffers"
          :entity="e"
          v-bind:key="e.name + index"
          :openModal="openModal"
          :openCantReserveModal="openCantReserve"
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
          v-for="(e, index) in popularOffers"
          v-bind:key="e.name + index"
          :entity="e"
          :openModal="openModal"
          :openCantReserveModal="openCantReserve"
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
          v-for="(e, index) in bestPriceOffers"
          :entity="e"
          v-bind:key="e.name + index"
          :openModal="openModal"
          :openCantReserveModal="openCantReserve"
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
          v-for="(e, index) in recommendedOffers"
          :entity="e"
          v-bind:key="e.name + index"
          :openCantReserveModal="openCantReserve"
          :openModal="openModal"
        />
      </b-row>
    </b-container>
    <Popup
      title="Congritulations!"
      type="success"
      text="You have successfully booked your next adventure!"
      id="reservedModal"
      ref="reservedModal"
    />
    <b-modal id="cantReserve" title="You can't make reservations.">
      <p class="my-4">
        Because of your 3 penalties, we have to stop you from making a
        reservation. You will have to wait till the end of month to be able to
        reserve something again.
      </p>
    </b-modal>
  </div>
</template>

<script>
import EntityOnDiscount from "./EntityOnDiscount.vue";
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
      is_data_fetched: false,
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
      this.$axios.get(`/api/last-minute`).then((response) => {
        that.lastMinuteOffers = response.data;
        that.is_data_fetched = true;
      });
    },
    openCantReserve() {
      this.$bvModal.show("cantReserve");
    },
    loadRecommended() {
      let that = this;
      that.recommendedOffers = [];
      this.$axios.get(`/api/recommended`).then((response) => {
        that.recommendedOffers = response.data;
      });
    },
    loadPopular() {
      let that = this;
      that.popularOffers = [];
      this.$axios.get(`/api/most-popular`).then((response) => {
        that.popularOffers = response.data;
      });
    },
    loadBestPrice() {
      let that = this;
      that.bestPriceOffers = [];
      this.$axios.get(`/api/best-price`).then((response) => {
        that.bestPriceOffers = response.data;
      });
    },
    openModal() {
      this.$bvModal.show("reservedModal");
    },
  },
};
</script>

<style>
</style>