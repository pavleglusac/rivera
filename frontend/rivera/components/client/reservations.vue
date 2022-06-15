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
      hide-header
      hide-footer
      @show="resetModal"
      @hidden="resetModal"
    >
      <h4><font-awesome-icon icon="star" /> Submit Your Review</h4>
      <form ref="form">
        <label class="form-label">Who are you reviewing?</label>
        <div @click="reviewOwnerSelected" style="cursor: pointer; padding: 10px; font-weight: 500; border-radius: 5px;" :class="{selectedClass : this.selectedReview == 'owner'}">
          <b-avatar variant="info" :src='"http://localhost:8080" + clickedReservation.entity.owner.photo' class="mr-3"></b-avatar>
          <span class="mr-auto">{{clickedReservation.entity.owner.name}} {{clickedReservation.entity.owner.surname}}</span>
        </div>
        <div @click="reviewEntitySelected" style="cursor: pointer; padding: 10px; font-weight: 500; border-radius: 5px;" :class="{selectedClass : this.selectedReview == 'entity'}">
          <b-avatar variant="info" :src='"http://localhost:8080" + clickedReservation.entity.photo' class="mr-3"></b-avatar>
          <span class="mr-auto">{{clickedReservation.entity.name}}</span>
        </div>
        <label class="form-label">Score a reservation:</label>
        <b-form-rating color="#16C79A" v-model="rating"></b-form-rating>
        <b-form-group
          label="Do you have any comments?"
          label-for="review-input"
        >
          <b-textarea
            id="review-input"
            v-model="reviewText"
            rows="3"
          ></b-textarea>
        </b-form-group>
        <div style="float: right">
          <b-button @click="closeReviewModal">Cancel</b-button>
          <b-button class="prime-btn" @click="reviewReservation">Review</b-button>
        </div>
      </form>
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
      hide-header
      hide-footer
      @show="resetModal"
      @hidden="resetModal"
    >
      <h4><font-awesome-icon icon="heart-crack" /> Submit Your Complaint</h4>
      <form ref="form">
        <label class="form-label">Who are you complaining to?</label>
        <div @click="complaintOwnerSelected" style="cursor: pointer; padding: 10px; font-weight: 500; border-radius: 5px;" :class="{selectedClass : this.selectedComplaint == 'owner'}">
          <b-avatar variant="info" :src='"http://localhost:8080" + clickedReservation.entity.owner.photo' class="mr-3"></b-avatar>
          <span class="mr-auto">{{clickedReservation.entity.owner.name}} {{clickedReservation.entity.owner.surname}}</span>
        </div>
        <div @click="complaintEntitySelected" style="cursor: pointer; padding: 10px; font-weight: 500; border-radius: 5px;" :class="{selectedClass : this.selectedComplaint == 'entity'}">
          <b-avatar variant="info" :src='"http://localhost:8080" + clickedReservation.entity.photo' class="mr-3"></b-avatar>
          <span class="mr-auto">{{clickedReservation.entity.name}}</span>
        </div>
        <b-form-group
          label="What was wrong with your reservation? Please tell us in a few sentances."
          label-for="complaint-input"
          invalid-feedback="Complaint text is required."
          :state="complainState"
        >
          <b-textarea
            id="complaint-input"
            v-model="complainText"
            rows="3"
            :state="complainState"
            required
          ></b-textarea>
        </b-form-group>
        <div style="float: right">
          <b-button @click="closeComplaintModal">Cancel</b-button>
          <b-button class="prime-btn" @click="lodgeAComplaint">Submit</b-button>
        </div>
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
    closeReviewModal() {
      this.$bvModal.hide("reviewModal");
    },
    closeComplaintModal() {
      this.$bvModal.hide("complaintModal");
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
          this.$axios
            .post(
              `/api/cancelReservation?&username=${resp.data}&reservationId=${this.clickedReservation.reservationId}`
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
          console.log(this.clickedReservation);
          this.$axios
            .post(
              `/api/reviewReservation?&username=${resp.data}&reservationId=${this.clickedReservation.reservationId}&reviewFor=${this.selectedReview}&rating=${this.rating}&reviewText=${this.reviewText}`
            )
            .then((response) => {
              this.$refs.review_sent.show();
              this.closeReviewModal();
            });
        });
    },
    lodgeAComplaint(bvModalEvent) {
      if (this.checkFormValidity()) {
        this.$axios
        .get("/api/auth/get-logged-username", {
          headers: {
            Authorization: "Bearer " + window.localStorage.getItem("JWT"),
          },
        })
        .then((resp) => {
          this.$axios
            .post(
              `/api/complainOnReservation?&username=${resp.data}&reservationId=${this.clickedReservation.reservationId}&reviewFor=${this.selectedComplaint}&reviewText=${this.complainText}`
            )
            .then((response) => {
              this.$refs.complain_sent.show();
              this.closeComplaintModal();
            });
        });
      }
    },
    openComplaintModal(id) {
      this.clickedReservation = id;
      this.$bvModal.show("complaintModal");
    },
    openCancelModal(id) {
      this.clickedReservation = id;
      this.$bvModal.show("cancelModal");
    },
    openReviewModal(id) {
      this.clickedReservation = id;
      this.$bvModal.show("reviewModal");
    },
    complaintOwnerSelected() {
      this.selectedComplaint = "owner";
    },
    complaintEntitySelected() {
      this.selectedComplaint = "entity";
    },
    reviewOwnerSelected() {
      this.selectedReview = "owner";
    },
    reviewEntitySelected() {
      this.selectedReview = "entity";
    }
  },
  data() {
    return {
      reservations: [],
      search: "",
      clickedReservation: {
        entity: {
          owner: {
            name: "",
            surname: "",
            photo: ""
          },
          name: "",
          photo: ""
        }
      },
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
      complainText: "",
      rating: 0,
      reviewText: "",
      complainState: null,
    };
  },
};
</script>

<style>
.selectedClass {
  background-color: var(--prime-color);
  color: white;
}
</style>