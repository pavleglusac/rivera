<template>
  <div>
    <b-card class="mb-3" style="margin: 40px; margin-top: 10px">
      <b-form>
        <div class="container-fluid p-2">
          <ul class="nav nav-pills nav-fill" role="tablist">
            <li class="nav-item">
              <p class="nav-link" v-bind:class="{ active: activeAdventures }" id="adventures-btn" @click="loadAdventures">
                <span><img src="..\static\icons\fish.png" /> Fishing</span>
              </p>
            </li>
            <li class="nav-item">
              <p class="nav-link" v-bind:class="{ active: activeCottages }" id="cottages-btn" @click="loadCottages">
                <span><img src="..\static\icons\cottage.png" /> Cottages</span>
              </p>
            </li>
            <li class="nav-item">
              <p class="nav-link" v-bind:class="{ active: activeBoats }" id="boats-btn" @click="loadBoats">
                <span><img src="..\static\icons\boat.png" /> Boats</span>
              </p>
            </li>
          </ul>
        </div>
        <div class="p-2">
          <form>
            <div class="form-row">
              <div class="form-group col-md-3">
                <b-form-input
                  id="search-filter"
                  size="sm"
                  placeholder="Search..."
                  v-model="searchText"
                  v-on:input="reload"
                ></b-form-input>
              </div>
              <div class="form-group col-md-3">
                <b-form-datepicker size="sm" v-on:input="reload" placeholder="Start date"></b-form-datepicker>
              </div>
              <div class="form-group col-md-3">
                <b-form-datepicker size="sm" v-on:input="reload" placeholder="End date"></b-form-datepicker>
              </div>
              <div class="form-group col-md-3">
                <b-form-select size="sm" v-model="sort" :options="orderBy" @change.native="reload"></b-form-select>
              </div>
            </div>
          </form>
          <div style="margin-bottom: 15px">
            <b-form-tags size="sm" v-on:input="reload" v-model="tags" input-id="tags-basic"></b-form-tags>
          </div>

          <div v-if="offers.length === 0">
            <p style="text-align:center;">No offers found.</p>
          </div>
          <div v-else>
            <EntityCard
              v-for="entity in offers"
              :entity="entity"
              class="mb-3"
            />
          </div>
        </div>
      </b-form>
    </b-card>
  </div>
</template>

<script>
import EntityCard from "./search/entity_card.vue";
import UnauthenticatedNavbar from "./UnauthenticatedNavbar.vue";

export default {
  components: { UnauthenticatedNavbar, EntityCard },
  data() {
    return {
      offers: [],
      filteredOffers: [],
      isFiltering: false,
      activeCottages: false,
      activeBoats: false,
      tags: [],
      activeAdventures: true,
      searchText: '',
      sort: 'name-a',
      orderBy: [
        { value: 'name-a', text: "Sort by name ascending" },
        { value: 'price-a', text: "Sort by price ascending" },
        { value: 'score-a', text: "Sort by score ascending" },
        { value: 'name-d', text: "Sort by name descending" },
        { value: 'price-d', text: "Sort by price descending" },
        { value: 'score-d', text: "Sort by score descending" }
      ],
    };
  },
  mounted() {
    this.loadAdventures();
  },
  methods: {
    // filters offers by search query
    filter() {
      let offers = this.offers;
      //const to = this.$route;

      this.filteredOffers = [];
      //const filter = document.getElementById("search-filter");
      //this.filteredHotels = hotels.filter(hotel => (facility => hotel.facilities.includes(facility)));
    },
    reload() {
      if (this.activeCottages)
        this.loadCottages();
      else if (this.activeBoats)
        this.loadBoats();
      else
        this.loadAdventures();
    },
    loadBoats() {
      let that = this;
      that.offers = [];
      this.$axios.get("/api/get-boats").then((resp) => {
        console.log(resp.data);
        that.offers = resp.data;
      });
      that.activeCottages = false;
      that.activeBoats = true;
      that.activeAdventures = false;
    },
    loadAdventures() {
      let that = this;
      that.offers = [];
      console.log(that.sort);
      this.$axios.post(`/api/search-adventures?&numberOfResults=2&orderBy=${that.sort}&search=${that.searchText.trim()}&tags=${that.tags}`)
      .then(response => {
        that.offers = response.data;
        console.log(response);
      });
      that.activeCottages = false;
      that.activeBoats = false;
      that.activeAdventures = true;
    },
    loadCottages() {
      let that = this;
      that.offers = [];
      this.$axios.get("/api/get-cottages").then((resp) => {
        console.log(resp.data);
        that.offers = resp.data;
      });
      that.activeCottages = true;
      that.activeBoats = false;
      that.activeAdventures = false;
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
</style>