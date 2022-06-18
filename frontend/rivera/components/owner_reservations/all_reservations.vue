<template>
  <b-container class="bv-example-row">
    <b-card style="margin-bottom: 10px; margin-top: 10px">
      <b-row>
        <b-col>
          <h4>My Calendar</h4>
          <p>You can finally track all your reservations easier!</p>
          <b-calendar
            v-model="selectedDate"
            v-on:input="loadReservationsCalendar"
            :date-info-fn="dateClass"
            block
            locale="en-US"
          ></b-calendar>
        </b-col>
        <b-col>
          <h4>Reservation history</h4>
          <b-form-input
            id="search-filter"
            size="sm"
            placeholder="Search reservations..."
            v-model="search"
            v-on:input="loadReservations"
          ></b-form-input>
          <b-form-select
            class="mt-2 mb-2"
            v-model="selected"
            v-on:input="loadReservations"
            size="sm"
            :options="options"
          ></b-form-select>
          <div v-if="reservations.length > 0">
            <Reservation
              v-for="(reservation, index) in reservations"
              :reservation="reservation"
              :reportModal="reportModal"
              :viewReportModal="viewReportModal"
              v-bind:key="reservation.rentable.name + index"
            />
          </div>
          <p v-else>There are no reservations at the moment.</p>
        </b-col>
      </b-row>
    </b-card>
    <b-modal id="add_modal" size="md" hide-header hide-footer>
      <ReservationReport :selectedId="selectedId" />
    </b-modal>
    <b-modal id="add_modal2" size="md" hide-header hide-footer>
      <ViewReservationReport :selectedId="selectedId" />
    </b-modal>
  </b-container>
</template>

<script>
import Reservation from "./reservation.vue";
import ReservationReport from "./../owner_reservations/reservation_report";
import ViewReservationReport from "./../owner_reservations/view_reservation_report.vue";
export default {
  data() {
    return {
      reservations: [],
      selectedId: "",
      search: "",
      selectedDate: new Date().toISOString().slice(0, 10),
      selected: "all",
      sort: "date-a",
      tags: [],
      options: [
        { text: "Upcoming reservations", value: "upcoming" },
        { text: "Past reservations", value: "past" },
        { text: "Reservations on selected date", value: "date" },
        { text: "Show all reservations", value: "all" },
      ],
    };
  },
  name: "AllReservations",
  components: { Reservation, ReservationReport, ViewReservationReport },
  mounted() {
    this.loadReservations();
  },
  methods: {
    loadReservations() {
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
              `/api/cottage/search-all-reservations-for-owner?&username=${
                resp.data
              }&search=${that.search.trim()}&date=${
                that.selectedDate
              }&upcoming=${that.selected}`
            )
            .then((response) => {
              that.reservations = response.data;
              console.log(response.data);
            });
        })
        .catch((err) => {
          console.log(err);
        });
    },
    loadReservationsCalendar() {
      if (this.selected == "date") this.loadReservations();
    },
    dateClass(ymd, date) {
      return this.dateHasReservation(ymd) ? "table-info" : "";
    },
    dateHasReservation(date) {
      var dayStarts = new Date(date);
      dayStarts.setHours(0, 0, 0, 0);
      var dayEnds = new Date(date);
      dayEnds.setHours(23, 59, 59, 999);
      for (const r of this.reservations) {
        var start = new Date(r.startDateTime);
        var end = new Date(r.endDateTime);
        // appointment started in this day
        if (dayStarts <= start && start <= dayEnds) {
          return true;
        }
        // appointment ended in this day
        if (dayStarts <= end && end <= dayEnds) {
          return true;
        }
        // appointment lasted whole day and more
        if (start <= dayStarts && end >= dayEnds) {
          return true;
        }
      }
      return false;
    },
    reportModal(param) {
      this.selectedId = param;
      console.log(this.selectedId + "aaa");
      this.$bvModal.show("add_modal");
    },
    viewReportModal(param) {
      this.selectedId = param;
      this.$bvModal.show("add_modal2");
    },
  },
};
</script>

<style>
</style>