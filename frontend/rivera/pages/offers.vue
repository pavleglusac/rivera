<template>
  <div>
    <unauthenticated-navbar />
    <b-card class="mb-3" style="margin: 40px; margin-top: 10px">
      <div class="p-3 mt-1">
        <form>
          <div class="form-row">
            <div class="form-group col-md-1">
              <span
                ><img src="..\static\icons\boat.png" alt="Boats" /> Boats</span
              >
            </div>
            <div class="form-group col-md-1">
              <span
                ><img src="..\static\icons\cottage.png" alt="Boats" /> Cottages</span
              >
            </div>
            <div class="form-group col-md-1">
              <span
                ><img src="..\static\icons\fish.png" alt="Boats" /> Fishing</span
              >
            </div>

            <div class="form-group col-md-4">
              <b-form-input id="search-filter" placeholder="Search..."></b-form-input>
            </div>

            <div class="form-group col-md-2">
              <b-form-datepicker placeholder="Start date"></b-form-datepicker>
            </div>
            <div class="form-group col-md-2">
              <b-form-datepicker placeholder="End date"></b-form-datepicker>
            </div>

            <div class="form-group col-md-1">
              <b-dropdown text="Order by">
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
          <div class="form-row">
              <b-form-tags input-id="tags-basic"></b-form-tags>
          </div>
        </form>
        <div v-if="isFiltering && filteredOffers.length === 0">
          <p>No offers found.</p>
        </div>
        <div v-else>
        <!--v-for="adventure, i in (filteredOffers.length > 0 ? filteredOffers : offers)"-->
        <AdventureCard
          v-for="adventure in offers"
          :key="i"
          :adventure="adventure"
          class="mb-3"
        />
        </div>
      </div>
    </b-card>
  </div>
</template>

<script>
import AdventureCard from '../components/search/adventure_card.vue';
import UnauthenticatedNavbar from "../components/UnauthenticatedNavbar.vue";

export default {
  components: { UnauthenticatedNavbar, AdventureCard },
  data() {
    return {
      offers: [],
      filteredOffers: [],
      isFiltering: false
    }
  },
  methods: {
    // filters offers by search query
    filter() {
      let offers = this.offers;
      //const to = this.$route;

      this.filteredOffers = [];
      //const filter = document.getElementById("search-filter");
      //this.filteredHotels = hotels.filter(hotel => (facility => hotel.facilities.includes(facility)));
    }
  },
  mounted() {
		let that = this;
		this.$axios
        .get('/api/get-adventures')
        .then((resp) => {
              console.log(resp.data);
              console.log("neeema");
              that.offers = resp.data;
    });
  }
};
</script>

<style>
</style>