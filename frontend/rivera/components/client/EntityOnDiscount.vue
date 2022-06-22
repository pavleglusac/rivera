<template>
  <b-col>
    <div class="mt-2">
      <b-card
        img-top
        tag="article"
        style="max-width: 30rem"
        img-height="200"
        class="mb-2 discount-card"
        :img-src="'http://localhost:8080' + entity.picture"
      >
        <b-card-title
          style="cursor: pointer"
          @click="goToProfile(entity.rentableId)"
          >{{ entity.name }}</b-card-title
        >
        <b-card-body style="padding: 0; align-text: center">
          <div class="text-secondary" style="font-size: 1em">
            <font-awesome-icon icon="star" />&nbsp;{{
              entity.averageScore
            }}&nbsp; <font-awesome-icon icon="person" /> &nbsp;{{
              entity.capacity
            }}
          </div>
          <div class="text-secondary" style="font-size: 1em">
            <font-awesome-icon icon="location-dot" /> &nbsp;{{ entity.city }},
            {{ entity.country }}
          </div>
          <div class="text-secondary" style="font-size: 1em">
            <font-awesome-icon icon="calendar" />&nbsp; Start:
            {{ new Date(entity.start).toString().substring(4, 21) }}
          </div>
          <div class="text-secondary" style="font-size: 1em">
            &nbsp;&nbsp;&nbsp;&nbsp; End:
            {{ new Date(entity.end).toString().substring(4, 21) }}
          </div>
          <b-button
            @click="reserveAppointment"
            class="prime-btn mt-2"
            variant="primary"
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
    return { entity: this.entity };
  },
  methods: {
    reserveAppointment() {
      var startDateTime = this.entity.start;
      var endDateTime = this.entity.end;
      var price = this.entity.price;
      var id = this.entity.rentableId;
      var that = this;
      this.$axios
        .get("/api/auth/client-can-reserve", {})
        .then((resp) => {
          if (resp.data === "3") {
            that.openCantReserveModal();
          } else if (resp.data != "no-client") {
            console.log(resp.data);
            this.$axios
              .post(
                `/api/reserve?&username=${resp.data}&rentableId=${id}&start=${startDateTime}&end=${endDateTime}&price=${price}&additionalServices=`
              )
              .then((response) => {
                console.log(response.data);
                that.openModal();
              }).catch((resp) => {
                alert("Couldn't reserve");
              });
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    goToProfile(id) {
      this.$router.push({ path: "/rentable/" + id });
    },
  },
  props: ["entity", "openModal", "openCantReserveModal"],
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
.discount-card:hover {
  transform: translateY(-10px);
  transition: all ease 0.3s;
}
</style>