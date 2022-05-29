<template>
  <b-card
    class="mb-2 reservation-card"
  >
    <b-avatar
      variant="info"
      :src="'http://localhost:8080' + reservation.client.photo"
      class="mr-2"
    ></b-avatar>
    <span
      @click="goToProfile(reservation.rentableId)"
      style="cursor: pointer; font-weight: 500; font-size: 17px"
      class="mr-auto"
    >
      {{ reservation.client.name }}</span
    >
    <b-card-text style="margin-top: 10px"
      ><font-awesome-icon icon="clock" />&nbsp;{{
        formatDate(new Date(reservation.start))
      }}
      - {{ formatDate(new Date(reservation.end)) }}<br />
      <font-awesome-icon icon="location-dot" />&nbsp;&nbsp;{{
        reservation.entity.address
      }}, {{ reservation.entity.city }}, {{ reservation.entity.country }} <br />
      <font-awesome-icon icon="credit-card" />&nbsp;Reservation cost: {{
        reservation.price 
      }}$</b-card-text
    >
    <div
          v-if="!isDateAfterToday(new Date(this.reservation.end))"
          style="float: right"
        >
          <b-button
            v-if="!this.reservation.report"
            variant="primary"
            size="sm"
            class="book-btn"
            @click="fileReport"
            >File a report</b-button
          >
          <b-button
            v-else
            variant="primary"
            size="sm"
            class="book-btn"
            @click="viewReport"
            >View filed report</b-button
          >
        </div>
  </b-card>
</template>

<script>
export default {
  props: {
    reservation: {
      type: Object,
      required: true,
    },
  },
  methods: {
    goToProfile(id) {
      this.$router.push({ path: "/rentable/" + id });
    },
    goToClient() {
      this.$router.push({
        path: "/profile/" + this.reservation.client.username,
      });
    },
    formatDate(date) {
      var hours = date.getHours();
      var minutes = date.getMinutes();
      var ampm = hours >= 12 ? "pm" : "am";
      hours = hours % 12;
      hours = hours ? hours : 12;
      minutes = minutes < 10 ? "0" + minutes : minutes;
      var strTime = hours + ":" + minutes + " " + ampm;
      return (
        date.toLocaleDateString("en-US", {
          weekday: "long",
          year: "numeric",
          month: "long",
          day: "numeric",
        }) +
        " at " +
        strTime
      );
    },
    fileReport() {
      this.$router.push({ path: "/addReport/" + this.reservation.id });
    },
    viewReport() {
      this.$router.push({ path: "/viewReport/" + this.reservation.id });
    },
    isDateAfterToday(date) {
      return (
        new Date(date.toDateString()) > new Date(new Date().toDateString())
      );
    },
  },
  data() {
    return {};
  },
};
</script>

<style>
.reservation-card {
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 5px 0 rgba(0, 0, 0, 0.19);
}
</style>