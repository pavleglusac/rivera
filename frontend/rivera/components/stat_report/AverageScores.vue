<template>
  <div class="container">
    <div class="form-row">
      <div class="form-group col-md-6">
        <p>Average rating of all selected rentables:</p>
      </div>
      <div class="form-group col-md-6">
        <b-form-rating
          v-model="averageScore"
          inline
          color="#16C79A"
          readonly
          show-value
          precision="2"
        ></b-form-rating>
      </div>
    </div>
    <div class="form-row">
      <div class="form-group col-md-6">
        <b-form-input
          id="search-filter"
          size="sm"
          placeholder="Search..."
          v-model="searchText"
          v-on:input="reload"
          @change="reload"
        ></b-form-input>
      </div>
      <div class="form-group col-md-6">
        <b-form-select
          size="sm"
          v-model="sort"
          :options="orderBy"
          @change="reload"
        ></b-form-select>
      </div>
    </div>
    <b-list-group v-if="offers.length > 0">
      <b-list-group-item
        v-for="entity in offers"
        :key="entity.id"
        class="d-flex align-items-center"
      >
        <b-avatar
          variant="info"
          :src="'http://localhost:8080' + entity.pictures[0]"
          class="mr-3"
        ></b-avatar>
        <span
          @click="goToProfile(entity)"
          style="cursor: pointer"
          class="mr-auto"
        >
          {{ entity.name }}</span
        >
        <b-form-rating
          v-model="entity.averageScore"
          inline
          color="#16C79A"
          readonly
          show-value
          precision="2"
        ></b-form-rating>
      </b-list-group-item>
    </b-list-group>
    <p v-else>No rentables found.</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      role: "",
      offers: [],
      filteredOffers: [],
      isFiltering: false,
      activeCottages: false,
      activeBoats: false,
      tags: [],
      activeAdventures: true,
      loadingRentables: false,
      typingTimer: "",
      adding: "",
      doneTypingInterval: 500,
      searchText: "",
      sort: "name-a",
      averageScore: 0,
      orderBy: [
        { value: "name-a", text: "Sort by name ascending" },
        { value: "price-a", text: "Sort by price ascending" },
        { value: "score-a", text: "Sort by score ascending" },
        { value: "name-d", text: "Sort by name descending" },
        { value: "price-d", text: "Sort by price descending" },
        { value: "score-d", text: "Sort by score descending" },
      ],
    };
  },
  mounted() {
    let that = this;
    this.$axios
      .get("/api/auth/getRole", {
        headers: {
          Authorization: "Bearer " + window.localStorage.getItem("JWT"),
        },
      })
      .then((resp) => {
        that.role = resp.data;
        console.log(that.role);
        if (that.role == "ROLE_COTTAGE_OWNER") {
          this.loadCottages();
        } else if (that.role == "ROLE_BOAT_OWNER") {
          this.loadBoats();
        } else {
          this.loadAdventures();
        }
      })
      .catch((err) => {
        console.log(err);
      });
  },
  methods: {
    calculateAverageScore() {
      this.averageScore = 0;
      console.log(this.offers);
      for (var offer of this.offers) {
        this.averageScore += offer.averageScore;
      }
      this.averageScore /= this.offers.length;
    },
    loadBoats() {
      this.loadingRentables = true;
      let that = this;
      that.offers = [];
      this.$axios
        .get("/api/auth/get-logged-username", {
          headers: {
            Authorization: "Bearer " + window.localStorage.getItem("JWT"),
          },
        })
        .then((resp) => {
          console.log(resp.data);
          this.$axios
            .post(
              `/api/boat/search-boats?&numberOfResults=10&orderBy=${
                that.sort
              }&search=${that.searchText.trim()}&tags=${
                that.tags
              }&ownerUsername=${resp.data}`
            )
            .then((response) => {
              that.offers = response.data;
              that.loadingRentables = false;

              that.calculateAverageScore();
            });
          that.activeCottages = false;
          that.activeBoats = true;
          that.activeAdventures = false;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    loadAdventures() {
      this.loadingRentables = true;
      let that = this;
      that.offers = [];
      this.$axios
        .get("/api/auth/get-logged-username", {
          headers: {
            Authorization: "Bearer " + window.localStorage.getItem("JWT"),
          },
        })
        .then((resp) => {
          console.log(resp.data);
          this.$axios
            .post(
              `/api/search-adventures?&numberOfResults=10&orderBy=${
                that.sort
              }&search=${that.searchText.trim()}&tags=${
                that.tags
              }&ownerUsername=${resp.data}`
            )
            .then((response) => {
              that.offers = response.data;
              that.loadingRentables = false;

              that.calculateAverageScore();
            });
          that.activeCottages = false;
          that.activeBoats = false;
          that.activeAdventures = true;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    loadCottages() {
      this.loadingRentables = true;
      let that = this;
      that.offers = [];
      this.$axios
        .get("/api/auth/get-logged-username", {
          headers: {
            Authorization: "Bearer " + window.localStorage.getItem("JWT"),
          },
        })
        .then((resp) => {
          console.log(resp.data);
          this.$axios
            .post(
              `/api/cottage/search-cottages-for-owner?&numberOfResults=10&orderBy=${
                that.sort
              }&search=${that.searchText.trim()}&tags=${
                that.tags
              }&ownerUsername=${resp.data}`
            )
            .then((response) => {
              that.offers = response.data;
              that.loadingRentables = false;

              that.calculateAverageScore();
            });
          that.activeCottages = true;
          that.activeBoats = false;
          that.activeAdventures = false;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    reload() {
      if (this.role == "ROLE_COTTAGE_OWNER") this.loadCottages();
      else if (this.role == "ROLE_BOAT_OWNER") this.loadBoats();
      else this.loadAdventures();
    },
  },
};
</script>

<style>
</style>