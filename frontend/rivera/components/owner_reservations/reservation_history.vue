<template>
  <b-form>
    <div class="p-2">
      <form>
        <div class="form-row">
          <div class="form-group col-md-6">
            <b-form-input
              id="search-filter"
              size="sm"
              placeholder="Search by reservee..."
              v-model="searchText"
              v-on:input="resetTimer"
            ></b-form-input>
          </div>
          <div class="form-group col-md-3">
            <b-form-datepicker
              size="sm"
              v-on:input="loadReservations"
              placeholder="Start date"
            ></b-form-datepicker>
          </div>
          <div class="form-group col-md-3">
            <b-form-datepicker
              size="sm"
              v-on:input="loadReservations"
              placeholder="End date"
            ></b-form-datepicker>
          </div>
        </div>
      </form>

      <div
        v-if="loadingReservations"
        class="d-flex justify-content-center mb-3"
      >
        <b-spinner variant="success" class="spinning m-2"></b-spinner>
      </div>
      <div v-else-if="reservations.length === 0">
        <p style="text-align: center">No reservations found.</p>
      </div>
      <div v-else>
        <EntityReservation
          v-for="res in reservations"
          :entity="res.rentable"
          :offerType="getActiveOffers()"
          :reservation="res"
          class="mb-3"
          v-bind:key="res.rentable.name"
        />
      </div>
    </div>
  </b-form>
</template>

<script>
import EntityReservation from "../owner_reservations/entity_reservation.vue";
import UnauthenticatedNavbar from "../../components/navbars/UnauthenticatedNavbar.vue";
import { BIcon, BIconStarFill, BIconSearch } from "bootstrap-vue";

export default {
  components: {
    UnauthenticatedNavbar,
    EntityReservation,
    BIcon,
    BIconStarFill,
    BIconSearch,
  },
  data() {
    return {
      role: "",
      entityObj: "",
      reservations: [],
      entities: [],
      filteredOffers: [],
      isFiltering: false,
      activeCottages: false,
      activeBoats: false,
      loadingReservations: false,
      typingTimer: "",
      doneTypingInterval: 500,
      tags: [],
      entity: {},
      activeAdventures: true,
      searchText: "",
      sort: "date-a",
      orderBy: [
        { value: "date-a", text: "Sort by date ascending" },
        { value: "date-d", text: "Sort by date descending" },
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
        this.loadReservations();
      })
      .catch((err) => {
        console.log(err);
      });
    console.log(this.role);
  },
  methods: {
    loadReservations() {
      this.loadingReservations = true;
      let that = this;
      that.reservations = [];
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
              `/api/cottage/search-reservations-for-owner?&numberOfResults=10&orderBy=${
                that.sort
              }&search=${that.searchText.trim()}&tags=${
                that.tags
              }&ownerUsername=${resp.data}`
            )
            .then((response) => {
              that.reservations = response.data;
              that.loadingReservations = false;
            });
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getActiveOffers() {
      return "cottage";
    },
    resetTimer() {
      clearTimeout(this.typingTimer);
      this.usernameExists = false;
      this.loadingUsername = true;
      this.loadingReservations = true;
      this.typingTimer = setTimeout(
        this.loadReservations,
        this.doneTypingInterval
      );
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