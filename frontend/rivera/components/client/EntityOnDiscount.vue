<template>
<b-col>
  <div class="mt-2">
    <b-card
      img-top
      tag="article"
      style="max-width: 30rem;"
      img-height="200"
      class="mb-2"
      :img-src='"http://localhost:8080" + entity.picture'
    >
      <b-card-title style="cursor: pointer;" @click="goToProfile(entity.rentableId)">{{entity.name}}</b-card-title>
      <b-card-body style="padding: 0; align-text: center">
        <div class="text-secondary" style="font-size: 1em">
          <font-awesome-icon icon="star" />&nbsp;{{ entity.averageScore }}&nbsp;
          <font-awesome-icon icon="person" /> &nbsp;{{ entity.capacity }}
        </div>
        <div class="text-secondary" style="font-size: 1em">
          <font-awesome-icon icon="location-dot" /> &nbsp;{{ entity.city }},
          {{ entity.country }}
        </div>
        <div class="text-secondary" style="font-size: 1em">
          <font-awesome-icon icon="calendar" />&nbsp; Start: {{ new Date(entity.start).toDateString() }}
        </div>
        <div class="text-secondary" style="font-size: 1em">
          &nbsp;&nbsp;&nbsp;&nbsp; End: {{ new Date(entity.end).toDateString() }}
        </div>
        <b-button @click="reserveAppointment" class="prime-btn mt-2" variant="primary"
          >Book now for only {{ entity.price }} $</b-button
        >
      </b-card-body>
    </b-card>
  </div>
</b-col>
</template>

<script>
export default {
  name: "EntityOnDiscount",
  components: {},
  data() {
    return { entity: this.entity, };
  },
  methods: {
    reserveAppointment() {
      var startDateTime = this.entity.start;
      var endDateTime = this.entity.end;
      var price = this.entity.price;
      var id = this.entity.rentableId;
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
              `/api/reserve?&username=${resp.data}&rentableId=${id}&start=${startDateTime}&end=${endDateTime}&price=${price}`
            )
            .then((response) => {
              console.log(response.data);
              this.openModal();
            });
        })
        .catch((err) => {
          console.log(err);
        });
    },
    goToProfile(id) {
      this.$router.push({ path: "/rentable/" + id });
    }
  },
  props: ["entity", "openModal"],
};
</script>

<style>
.prime-btn {
  background-color: var(--prime-color);
  border: none;
}
.prime-btn:hover {
  background-color: #39aea9;
  border: none;
}
</style>