<template>
  <div>
    <b-card class="mb-3" style="margin: 40px; margin-top: 10px">
      <b-form>
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
              :offerType="getActiveOffers()"
              class="mb-3"
              v-bind:key="entity.name"
            />
          </div>
        </div>
      </b-form>
    </b-card>
  </div>
</template>

<script>
import EntityCard from "../components/search/entity_card.vue";
import UnauthenticatedNavbar from '../components/navbars/UnauthenticatedNavbar.vue';
import { BIcon, BIconStarFill, BIconSearch } from 'bootstrap-vue'

export default {
  components: { UnauthenticatedNavbar, EntityCard, BIcon, BIconStarFill, BIconSearch },
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
    let that = this;
    this.$axios.get('/api/auth/getRole', {
      headers: { 'Authorization' : 'Bearer ' + window.localStorage.getItem("JWT") } 
      }).then((resp) => {
        that.role = resp.data;
        console.log(that.role);
        
        if (that.role=="ROLE_COTTAGE_OWNER")
            this.loadCottages();
        else if (that.role=="ROLE_BOAT_OWNER")
            this.loadBoats();
        else
            this.loadAdventures();
      }).catch((err) => {
        console.log(err);
      });
      console.log(this.role);
  },
  methods: {
    getActiveOffers() {
      if (this.role=="ROLE_COTTAGE_OWNER")
        return "cottage";
      else if (this.role=="ROLE_BOAT_OWNER")
        return "boat";
      else
        return "adventure";
    },
    reload() {
      if (this.role=="ROLE_COTTAGE_OWNER")
        this.loadCottages();
      else if (this.role=="ROLE_BOAT_OWNER")
            this.loadBoats();
      else
            this.loadAdventures();
    },
    loadBoats() {
      let that = this;
      that.offers = [];
      this.$axios.get('/api/auth/get-logged-username',{
								headers: { 'Authorization' : 'Bearer ' + window.localStorage.getItem("JWT") } 
							}).then((resp) => {
                console.log(resp.data);
              this.$axios.post(`/api/search-boats?&numberOfResults=10&orderBy=${that.sort}&search=${that.searchText.trim()}&tags=${that.tags}&ownerUsername=${resp.data}`)
                .then(response => {
                  that.offers = response.data;
                });
                that.activeCottages = false;
                that.activeBoats = true;
                that.activeAdventures = false;
			}).catch((err) => {
				console.log(err);
			});
      
    },
    loadAdventures() {
      let that = this;
      that.offers = [];
      this.$axios.post(`/api/search-adventures?&numberOfResults=10&orderBy=${that.sort}&search=${that.searchText.trim()}&tags=${that.tags}&ownerUsername=`)
      .then(response => {
        that.offers = response.data;
      });
      that.activeCottages = false;
      that.activeBoats = false;
      that.activeAdventures = true;
    },
    loadCottages() {
      let that = this;
      that.offers = [];
      this.$axios.get('/api/auth/get-logged-username',{
								headers: { 'Authorization' : 'Bearer ' + window.localStorage.getItem("JWT") } 
							}).then((resp) => {
                console.log(resp.data);
              this.$axios.post(`/api/cottage/search-cottages-for-owner?&numberOfResults=10&orderBy=${that.sort}&search=${that.searchText.trim()}&tags=${that.tags}&ownerUsername=${resp.data}`)
                .then(response => {
                  that.offers = response.data;
                });
                that.activeCottages = true;
                that.activeBoats = false;
                that.activeAdventures = false;
			}).catch((err) => {
				console.log(err);
			});
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