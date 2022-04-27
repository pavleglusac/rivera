<template>
  <div>
    <unauthenticated-navbar />
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
              <div class="form-group col-md-4">
                <b-form-input
                  id="search-filter"
                  placeholder="Search..."
                  v-model="searchText"
                  v-on:input="reload"
                ></b-form-input>
              </div>
              <div class="form-group col-md-3">
                <b-form-datepicker v-on:input="reload" placeholder="Start date"></b-form-datepicker>
              </div>
              <div class="form-group col-md-3">
                <b-form-datepicker v-on:input="reload" placeholder="End date"></b-form-datepicker>
              </div>
              <div class="form-group col-md-2">
                <b-dropdown v-on:input="reload" text="Order by ">
                  <b-dropdown-divider></b-dropdown-divider>
                  <b-dropdown-group id="asc" header="Ascending">
                    <b-dropdown-item-button>Price</b-dropdown-item-button>
                    <b-dropdown-item-button>Name</b-dropdown-item-button>
                    <b-dropdown-item-button>Score</b-dropdown-item-button>
                  </b-dropdown-group>
                  <b-dropdown-group id="desc" header="Descending">
                    <b-dropdown-item-button>Price</b-dropdown-item-button>
                    <b-dropdown-item-button>Name</b-dropdown-item-button>
                    <b-dropdown-item-button>Score</b-dropdown-item-button>
                  </b-dropdown-group>
                </b-dropdown>
              </div>
            </div>
          </form>
          <div style="margin-bottom: 15px">
            <b-form-tags v-on:input="reload" input-id="tags-basic"></b-form-tags>
          </div>

          <div v-if="isFiltering && filteredOffers.length === 0">
            <p>No offers found.</p>
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
import EntityCard from "../components/search/entity_card.vue";
import UnauthenticatedNavbar from "../components/UnauthenticatedNavbar.vue";

export default {
  components: { UnauthenticatedNavbar, EntityCard },
  data() {
    return {
      offers: [],
      filteredOffers: [],
      isFiltering: false,
      activeCottages: false,
      activeBoats: false,
      activeAdventures: true,
      searchText: ''
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

      /*var formData = new FormData();
      formData.append("search", this.searchText);
      formData.append("start", null);
      formData.append("end", null);
      formData.append("orderBy", "name");
      formData.append("ascending", true);
      formData.append("tags", []);
      formData.append("numberOfResults", 10);
      
			this.$axios.post('/api/search-adventures', formData, {
				headers: {
					'Content-Type': 'form-data'
				}
			}).then((resp) => {
        that.offers = resp.data;
			});*/

      this.$axios.post(`/api/search-adventures?ascending=true&numberOfResults=2&orderBy=name&search=${that.searchText.trim()}`)
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