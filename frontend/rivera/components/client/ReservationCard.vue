<template>
  <b-card
    class="mb-2 reservation-card"
    :class="{
      'adventure-reservation-card': reservation.entity.kind == 'ADVENTURE',
      'boat-reservation-card': reservation.entity.kind == 'BOAT',
      'cottage-reservation-card': reservation.entity.kind == 'COTTAGE',
    }"
  >
    <b-avatar
      variant="info"
      :src="'http://localhost:8080' + reservation.entity.profilePicture"
      class="mr-2"
    ></b-avatar>
    <span
      @click="goToProfile(reservation.entity.id)"
      style="cursor: pointer; font-weight: 500; font-size: 17px"
      class="mr-auto"
    >
      {{ reservation.entity.name }}</span
    >
    <b-card-text style="margin-top: 10px"
      ><font-awesome-icon icon="clock" />&nbsp;{{ new Date(reservation.endDateTime).toDateString() }} -
      {{ new Date(reservation.endDateTime).toDateString() }}<br />
      <font-awesome-icon icon="location-dot" />&nbsp;&nbsp;{{
        reservation.entity.address
      }}, {{ reservation.entity.city }},
      {{ reservation.entity.country }}</b-card-text
    >
    <b-button size="sm" disabled>Cancel reservation</b-button>
    <b-button size="sm" disabled>Review</b-button>
  </b-card>
</template>

<script>
export default {
  props: {
      reservation: {
          type: Object,
          required: true
      }
    },
  methods: {
  },
  data() {
    return {
    };
  },
  methods: {},
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