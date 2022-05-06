<template>
<div>
    <div class="col-12 section-intro text-center mt-5" data-aos="fade-up">
        <h1>Special offers</h1>
        <div class="divider"></div>
    </div>
    <b-container class="bv-example-row" data-aos="zoom-in">
        <b-row>
            <b-col><entity-on-discount/></b-col>
            <b-col><entity-on-discount/></b-col>
            <b-col><entity-on-discount/></b-col>
            <b-col><entity-on-discount/></b-col>
        </b-row>
    </b-container>
    <div class="col-12 section-intro text-center mt-5" data-aos="fade-up">
        <h1>For you</h1> 
        <div class="divider"></div>
    </div>
    <b-container class="bv-example-row" data-aos="zoom-in">
        <b-row>
            <b-col><entity-on-discount/></b-col>
            <b-col><entity-on-discount/></b-col>
            <b-col><entity-on-discount/></b-col>
            <b-col><entity-on-discount/></b-col>
        </b-row>
    </b-container>
    <div class="col-12 section-intro text-center mt-5" data-aos="fade-up">
        <h1>Popular this week</h1>
        <div class="divider"></div>
    </div>
    <b-container class="bv-example-row mb-5" data-aos="zoom-in">
        <b-row>
            <b-col><entity-on-discount/></b-col>
            <b-col><entity-on-discount/></b-col>
            <b-col><entity-on-discount/></b-col>
            <b-col><entity-on-discount/></b-col>
        </b-row>
    </b-container>
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
      discountOffers: [],
      recommended: [],
      popular: []
    };
  },
  mounted() {
    AOS.init();
    this.loadOffers();
    this.loadRecommended();
    this.loadPopular();
  },
   methods: {
    loadOffers() {
      let that = this;
      that.discountOffers = [];
      this.$axios.post(`/api/discountOffers`)
      .then(response => {
        that.discountOffers = response.data;
      });
    },
    loadRecommended() {
      let that = this;
      that.discountOffers = [];
      this.$axios.post(`/api/recommendedOffers`)
      .then(response => {
        that.recommended = response.data;
      });
    },
    loadPopular() {
      let that = this;
      that.discountOffers = [];
      this.$axios.post(`/api/popularOffers`)
      .then(response => {
        that.popular = response.data;
      });
    },
   }
}
</script>

<style>

</style>