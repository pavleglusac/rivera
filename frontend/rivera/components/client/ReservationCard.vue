<template>
  <b-card class="mb-2 reservation-card" img-top no-body>
    <b-card-img
      class="reservation-img-top"
      :src="'http://localhost:8080' + reservation.entity.profilePicture"
    />
    <h5
      style="
        margin-top: -2.5rem;
        cursor: pointer;
        color: white;
        margin-left: 10px;
      "
      @click="goToProfile(reservation.entity.id)"
    >
      <font-awesome-icon
        v-if="reservation.entity.kind == 'ADVENTURE'"
        icon="fish"
      />
      <font-awesome-icon
        v-else-if="reservation.entity.kind == 'COTTAGE'"
        icon="house"
      />
      <font-awesome-icon v-else icon="sailboat" /> {{ reservation.entity.name }}
    </h5>
    <b-card-body>
      <b-card-text style="margin: 10px"
        ><font-awesome-icon icon="clock" />&nbsp;{{
          new Date(reservation.startDateTime).toString().slice(0, 21)
        }}
        - {{ new Date(reservation.endDateTime).toString().slice(0, 21) }}<br />
        <font-awesome-icon icon="location-dot" />&nbsp;&nbsp;{{
          reservation.entity.address
        }}, {{ reservation.entity.city }}, {{ reservation.entity.country }}
        <br />
        <font-awesome-icon icon="credit-card" />&nbsp;Reservation cost:
        {{ reservation.price }}$</b-card-text
      >
      <b-button
        size="sm"
        class="prime-btn"
        :disabled="numberOfDaysUntilReservation > 3"
        @click="openCancel"
        >Cancel reservation</b-button
      >
      <b-button
        size="sm"
        class="prime-btn"
        :disabled="numberOfDaysUntilReservation < 0"
        @click="openReview"
        >Review</b-button
      >
      <b-button
        class="prime-btn"
        size="sm"
        :disabled="numberOfDaysUntilReservation < 0"
        @click="openComplaint"
        >Complain</b-button
      >
    </b-card-body>
  </b-card>
</template>

<script>
export default {
  props: [
    "reservation",
    "reservationId",
    "openCancelReservation",
    "openReviewReservation",
    "openComplaintReservation",
  ],
  mounted() {
    this.calculateNumberOfDaysUntilReservation();
    this.reservationId = this.reservation.id;
  },
  methods: {
    openCancel() {
      this.openCancelReservation(this.reservation.id);
    },
    openReview() {
      this.openReviewReservation(this.reservation.id);
    },
    openComplaint() {
      this.openComplaintReservation(this.reservation.id);
    },
    goToProfile(id) {
      this.$router.push({ path: "/rentable/" + id });
    },
    calculateNumberOfDaysUntilReservation() {
      this.numberOfDaysUntilReservation = this.daysBetween(
        new Date(),
        new Date(this.reservation.startDateTime)
      );
      console.log(this.numberOfDaysUntilReservation);
    },
    daysBetween(startDate, endDate) {
      return (
        (startDate.getTime() - endDate.getTime()) /
        (1000 * 3600 * 24)
      );
    },
  },
  data() {
    return {
      numberOfDaysUntilReservation: 0,
    };
  },
};
</script>

<style>
.reservation-card {
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 5px 0 rgba(0, 0, 0, 0.19);
}
.adventure-reservation-card {
  border-top: 10px solid #11698e;
}

.boat-reservation-card {
  border-top: 10px solid #19456b;
}

.cottage-reservation-card {
  border-top: 10px solid #16c79a;
}
</style>