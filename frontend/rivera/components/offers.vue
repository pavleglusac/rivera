<template>
  <div>
	<section class="bg-cover cover3" style="height: 430px">
      <div class="overlay" style="opacity: 0.25"></div>
	 <h1 style="color: white; font-size: 70px; margin-top: 50px; margin-left: 200px;">Discover</h1>
	 <p style="color: white; font-size: 20px; margin-left: 200px; margin-top: -15px;">Find and book a great experience.</p>
    </section>
    <b-card
      class="mb-3 discount-card w-75"
      style="margin: -150px auto 0 auto; z-index: 99"
    >
      <b-form>
        <div class="container-fluid p-2">
          <ul class="nav nav-pills nav-fill" role="tablist">
            <li class="nav-item">
              <p
                class="nav-link"
                v-bind:class="{ active: activeAdventures }"
                id="adventures-btn"
                @click="loadAdventures"
				 style="font-size: 20px"
              >
                <span><font-awesome-icon icon="fish" /> Fishing</span>
              </p>
            </li>
            <li class="nav-item">
              <p
                class="nav-link"
                v-bind:class="{ active: activeCottages }"
                id="cottages-btn"
                 style="font-size: 20px"
				 @click="loadCottages"
              >
                <span><font-awesome-icon icon="house" /> Cottages</span>
              </p>
            </li>
            <li class="nav-item">
              <p
                class="nav-link"
                v-bind:class="{ active: activeBoats }"
                id="boats-btn"
				 style="font-size: 20px"
                @click="loadBoats"
              >
                <span><font-awesome-icon icon="sailboat" /> Boats</span>
              </p>
            </li>
          </ul>
        </div>
        <div class="p-2">
          <div class="form-row">
            <div class="form-group col-md-4">
              <b-form-input
                id="search-filter"
                size="md"
                placeholder="Search by name or location..."
                v-model="searchText"
                v-on:input="resetTimer"
              ></b-form-input>
            </div>
            <div class="form-group col-md-4">
              <b-form-datepicker
                size="md"
                v-model="startDate"
                v-on:input="reload"
                placeholder="Start date"
              ></b-form-datepicker>
            </div>
            <div class="form-group col-md-4">
              <b-form-datepicker
                size="md"
                v-model="endDate"
                v-on:input="reload"
                placeholder="End date"
              ></b-form-datepicker>
            </div>
          </div>
          <div class="form-row">
            <div class="form-group col-md-3">
              <b-form-select
                size="md"
                v-model="sort"
                :options="orderBy"
                @change="reload"
              ></b-form-select>
            </div>
            <div class="form-group col-md-8">
              <b-form-tags
                size="md"
                v-on:input="reload"
                v-model="tags"
                input-id="tags-basic"
              ></b-form-tags>
            </div>
            <div class="form-group col-md-1">
              <b-button
                size="md"
                @click="clearSearch"
                variant="outline-secondary"
              >Clear</b-button>
            </div>
          </div>
        </div>
      </b-form>
    </b-card>
    <b-card class="mb-3 search-shadow w-75" style="margin: auto">
      <div v-if="loadingRentables" class="d-flex justify-content-center mb-3">
        <b-spinner variant="success" class="spinning m-2"></b-spinner>
      </div>
      <div v-else-if="offers.length === 0">
        <p style="text-align: center; font-size: 20px;">No offers found.</p>
      </div>
      <div v-else>
        <EntityCard
          v-for="(entity, index) in offers"
          :entity="entity"
          :offerType="getActiveOffers()"
          class="mb-3"
          v-bind:key="entity.name + index"
        />
      </div>
    </b-card>
  </div>
</template>

<script>
import EntityCard from "../components/search/entity_card.vue";
import UnauthenticatedNavbar from "../components/navbars/UnauthenticatedNavbar.vue";
import { BIcon, BIconStarFill, BIconSearch } from "bootstrap-vue";

export default {
  components: {
    UnauthenticatedNavbar,
    EntityCard,
    BIcon,
    BIconStarFill,
    BIconSearch,
  },
  data() {
    return {
      offers: [],
      filteredOffers: [],
      isFiltering: false,
      activeCottages: false,
      activeBoats: false,
      tags: [],
      activeAdventures: true,
      loadingRentables: false,
      startDate: "",
      endDate: "",
      searchText: "",
      sort: "name-a",
      typingTimer: "",
      doneTypingInterval: 500,
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
    this.loadAdventures();
  },
  methods: {
    getActiveOffers() {
      if (this.activeCottages) return "cottage";
      else if (this.activeBoats) return "boat";
      else return "adventure";
    },
    clearSearch() {
      this.searchText = "";
      this.tags = [];
      this.startDate = "";
      this.endDate = "";
      this.reload();
    },
    reload() {
      if (this.activeCottages) this.loadCottages();
      else if (this.activeBoats) this.loadBoats();
      else this.loadAdventures();
    },
    loadBoats() {
      this.loadingRentables = true;
      let that = this;
      that.offers = [];
      this.$axios
        .post(
          `/api/boat/search-boats?&numberOfResults=10&orderBy=${
            that.sort
          }&search=${that.searchText.trim()}&tags=${that.tags}&start=${that.startDate}&end=${that.endDate}`
        )
        .then((response) => {
          for (let index = 0; index < response.data.length; index++) {
            for(let j = 0; j < response.data[index].pictures.length; j++) {
              response.data[index].pictures[j] = process.env.backend + response.data[index].pictures[j];
            }
          }
          that.offers = response.data;
          that.loadingRentables = false;
        });
      that.activeCottages = false;
      that.activeBoats = true;
      that.activeAdventures = false;
    },
    loadAdventures() {
      this.loadingRentables = true;
      let that = this;
      that.offers = [];
      this.$axios
        .post(
          `/api/search-adventures?&numberOfResults=10&orderBy=${
            that.sort
          }&search=${that.searchText.trim()}&tags=${that.tags}&start=${that.startDate}&end=${that.endDate}`
        )
        .then((response) => {
          for (let index = 0; index < response.data.length; index++) {
            for(let j = 0; j < response.data[index].pictures.length; j++) {
              response.data[index].pictures[j] = process.env.backend + response.data[index].pictures[j];
            }
          }
          that.offers = response.data;
          that.loadingRentables = false;
          console.log(that.offers);
        });
      that.activeCottages = false;
      that.activeBoats = false;
      that.activeAdventures = true;
    },
    loadCottages() {
      this.loadingRentables = true;
      let that = this;
      that.offers = [];
      this.$axios
        .post(
          `/api/cottage/search-cottages?&numberOfResults=10&orderBy=${
            that.sort
          }&search=${that.searchText.trim()}&tags=${that.tags}&start=${that.startDate}&end=${that.endDate}`
        )
        .then((response) => {
          for (let index = 0; index < response.data.length; index++) {
            for(let j = 0; j < response.data[index].pictures.length; j++) {
              response.data[index].pictures[j] = process.env.backend + response.data[index].pictures[j];
            }
          }
          that.offers = response.data;
          that.loadingRentables = false;
        });
      that.activeCottages = true;
      that.activeBoats = false;
      that.activeAdventures = false;
    },
    resetTimer() {
      clearTimeout(this.typingTimer);
      this.usernameExists = false;
      this.loadingUsername = true;
      this.loadingRentables = true;
      this.typingTimer = setTimeout(this.reload, this.doneTypingInterval);
    },
  },
};
</script>

<style>
li > p .active {
  color: #fff;
}

.nav-pills .nav-link.active,
.nav-pills .show > .nav-link {
  background-color: var(--prime-color);
}

.cover3 {
  background-image: url(https://images.unsplash.com/photo-1508705152659-209db714f16f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1523&q=80)
}

.nav-link:hover {
  opacity: 0.9;
  background-color: var(--prime-color);
  cursor: pointer;
}

.nav-link {
  font-weight: bold;
  color: var(--dark-blue-color);
}

@media screen and (max-width: 768px) {
  .nav-fill .nav-item {
    width: 100% !important;
    flex-basis: unset !important;
  }
}
.search-shadow {
  box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.2), 0 3px 5px 0 rgba(0, 0, 0, 0.19);
}
</style>