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
          <ReservationCard
            v-for="(reservation, index) in reservations"
            :reservation="reservation"
            :openCancelReservation="openCancelModal"
            :openReviewReservation="openReviewModal"
            :openComplaintReservation="openComplaintModal"
            v-bind:key="reservation.entity.name + index"
          />
        </b-col>
      </b-row>
    </b-card>

    <b-modal
      id="cancelModal"
      hide-header
      @ok="cancelReservation"
      ok-title="Yes"
      cancel-title="No"
    >
      <p>Are you sure you want to cancel your reservation?</p>
    </b-modal>

    <b-modal
      id="reviewModal"
      title="Submit Your Review"
      ok-title="Submit"
      @ok="reviewReservation"
    >
      <label class="form-label">Who are you reviewing?</label>
        <b-form-radio-group
          v-model="selectedReview"
          :options="ReviewOptions"
        ></b-form-radio-group>
      <label class="form-label">Score a reservation:</label>
      <b-form-rating color="#16C79A" v-model="rating"></b-form-rating>
      <div class="mb-3">
        <label class="form-label">Write a reservation review:</label>
        <textarea class="form-control" v-model="reviewText" rows="3"></textarea>
      </div>
    </b-modal>

    <Popup
      ref="reservation_canceled"
      id="reservation_canceled"
      title="Reservation canceled"
      text="You have successfully canceled your reservation. Reservation can be canceled only 3 days in advance."
    />
    <Popup
      ref="review_sent"
      id="review_sent"
      title="Sent!"
      text="You have successfully reviewed reservation."
    />
    <Popup
      ref="complain_sent"
      id="complain_sent"
      title="Sent!"
      text="You have successfully complained on your reservation."
    />

    <b-modal
      id="complaintModal"
      title="Submit Your Complaint"
      ok-title="Submit"
      @show="resetModal"
      @hidden="resetModal"
      @ok="lodgeAComplaint"
    >
      <form ref="form" @submit.stop.prevent="lodgeAComplaint">
        <label class="form-label">Who are you complaining to?</label>
        <b-form-radio-group
          v-model="selectedComplaint"
          :options="ComplaintOptions"
        ></b-form-radio-group>
        <b-form-group
          label="Complaint text:"
          label-for="complaint-input"
          invalid-feedback="Complaint text is required."
          :state="complainState"
        >
          <b-form-input
            id="complaint-input"
            v-model="complainText"
            :state="complainState"
            required
          ></b-form-input>
        </b-form-group>
      </form>
    </b-modal>
  </b-container>
</template>

<script>
import ReservationCard from "./ReservationCard.vue";
import Popup from "../popup.vue";
export default {
  name: "Reservations",
  components: { ReservationCard, Popup },
  mounted() {
    this.loadReservations();
  },
  methods: {
    loadReservations() {
      let that = this;
      that.reservations = [];
      console.log(that.selectedDate);
      this.$axios
        .get("/api/auth/get-logged-username", {
          headers: {
            Authorization: "Bearer " + window.localStorage.getItem("JWT"),
          },
        })
        .then((resp) => {
          this.$axios
            .get(
              `/api/get-reservations?&username=${
                resp.data
              }&search=${that.search.trim()}&date=${
                that.selectedDate
              }&upcoming=${that.selected}`
            )
            .then((response) => {
              that.reservations = response.data;
              console.log(response.data);
            });
        });
    },
    checkFormValidity() {
      this.complainState = this.complainText.trim() !== "";
      return this.complainState;
    },
    resetModal() {
      this.complainText = "";
      this.complaintState = null;
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
    cancelReservation() {
      this.$axios
        .get("/api/auth/get-logged-username", {
          headers: {
            Authorization: "Bearer " + window.localStorage.getItem("JWT"),
          },
        })
        .then((resp) => {
          console.log(resp.data);
          console.log(this.clickedReservationId);
          this.$axios
            .post(
              `/api/cancelReservation?&username=${resp.data}&reservationId=${this.clickedReservationId}`
            )
            .then((response) => {
              this.$refs.reservation_canceled.show();
              this.loadReservations();
            });
        });
    },
    reviewReservation() {
      this.$axios
        .get("/api/auth/get-logged-username", {
          headers: {
            Authorization: "Bearer " + window.localStorage.getItem("JWT"),
          },
        })
        .then((resp) => {
          console.log("REVIEW");
          console.log(resp.data);
          console.log(this.clickedReservationId);
          console.log(this.rating);
          this.$axios
            .post(
              `/api/reviewReservation?&username=${resp.data}&reservationId=${this.clickedReservationId}&reviewFor=${this.selectedReview}&rating=${this.rating}&reviewText=${this.reviewText}`
            )
            .then((response) => {
              this.$refs.review_sent.show();
            });
        });
    },
    lodgeAComplaint(bvModalEvent) {
      if (this.checkFormValidity()) {
      }
    },
    openComplaintModal(id) {
      console.log(id);
      this.clickedReservationId = id;
      this.$bvModal.show("complaintModal");
    },
    openCancelModal(id) {
      console.log(id);
      this.clickedReservationId = id;
      this.$bvModal.show("cancelModal");
    },
    openReviewModal(id) {
      this.clickedReservationId = id;
      this.$bvModal.show("reviewModal");
    },
  },
  data() {
    return {
      reservations: [],
      search: "",
      clickedReservationId: 0,
      selectedDate: new Date().toISOString().slice(0, 10),
      selected: "all",
      complaintFor: null,
      reviewFor: null,
      options: [
        { text: "Upcoming reservations", value: "upcoming" },
        { text: "Past reservations", value: "past" },
        { text: "Reservations on selected date", value: "date" },
        { text: "Show all reservations", value: "all" },
      ],
      selectedReview: "owner",
      ReviewOptions: [
        { text: "Onwer", value: "owner" },
        { text: "Entity", value: "entity" },
      ],
      selectedComplaint: "owner",
      ComplaintOptions: [
        { text: "Onwer", value: "owner" },
        { text: "Entity", value: "entity" },
      ],
      complainText: "",
      rating: 0,
      reviewText: "",
      complainState: null,
    };
  },
};
</script>

<style>
</style>