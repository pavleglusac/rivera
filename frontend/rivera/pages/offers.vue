<template>
  <div>
    <unauthenticated-navbar />
    <b-card class="mb-3" style="margin: 40px; margin-top: 10px">
      <b-form>
        <div class="container-fluid p-2">
          <ul class="nav nav-pills nav-fill" role="tablist">
            <li class="nav-item">
              <a class="nav-link active" href="#" data-toggle="pill">
                <span><img src="..\static\icons\fish.png" /> Fishing</span>
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#" data-toggle="pill">
                <span><img src="..\static\icons\cottage.png" /> Cottages</span>
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#" data-toggle="pill">
                <span><img src="..\static\icons\boat.png" /> Boats</span>
              </a>
            </li>
          </ul>
        </div>
        <div class="p-4">
          <form>
            <div class="form-row">
              <div class="form-group col-md-4">
                <b-form-input
                  id="search-filter"
                  placeholder="Search..."
                ></b-form-input>
              </div>
              <div class="form-group col-md-3">
                <b-form-datepicker placeholder="Start date"></b-form-datepicker>
              </div>
              <div class="form-group col-md-3">
                <b-form-datepicker placeholder="End date"></b-form-datepicker>
              </div>
              <div class="form-group col-md-2">
                <b-dropdown text="Order by ">
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
            <b-form-tags input-id="tags-basic"></b-form-tags>
          </div>

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
      </b-form>
    </b-card>
  </div>
</template>

<script>
import AdventureCard from "../components/search/adventure_card.vue";
import UnauthenticatedNavbar from "../components/UnauthenticatedNavbar.vue";

export default {
  components: { UnauthenticatedNavbar, AdventureCard },
  data() {
    return {
      offers: [],
      filteredOffers: [],
      isFiltering: false,
    };
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
  },
  mounted() {
    let that = this;
    this.$axios.get("/api/get-adventures").then((resp) => {
      console.log(resp.data);
      console.log("neeema");
      that.offers = resp.data;
    });
  },
};
</script>

<style>
li > a .active {
  color: #fff;
}

.nav-pills .nav-link.active,
.nav-pills .show > .nav-link {
  background-color: var(--prime-color);
}

a {
  color: var(--dark-blue-color);
}

@media screen and (max-width: 768px) {
  .nav-fill .nav-item {
    width: 100% !important;
    flex-basis: unset !important;
  }
}
</style>