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
          <div class="text-secondary" v-if="services != ''" style="font-size: 1em">
            &nbsp;&nbsp;&nbsp;&nbsp; Additional services:
            {{ services }}
          </div>
          <b-button
            @click="reserve"
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
    return { 
      services: "" 
    };
  },
  mounted() {
    this.services = this.entity.additionalServices.join(", ");
  },
  methods: {
    dateFormat(date) {
      var current_datetime = new Date(date);
      var month = current_datetime.getMonth() + 1;
      if (month < 10) month = "0" + month;
      var day = current_datetime.getDate();
      if (day < 10) day = "0" + day;
      var minutes = current_datetime.getMinutes();
      if (minutes < 10) minutes = "0" + minutes;
      var hours = current_datetime.getHours();
      if (hours < 10) hours = "0" + hours;
      return (
        day +
        "/" +
        month +
        "/" +
        current_datetime.getFullYear() +
        " " +
        hours +
        ":" +
        minutes
      );
    },
    reserve() {
      var startDateTime = this.dateFormat(this.entity.start);
      var endDateTime = this.dateFormat(this.entity.end);
      var price = this.entity.price;
      console.log(this.entity);
      var discountId = this.entity.id;
      var rentableId = this.entity.rentableId;
      this.$axios
        .get("/api/auth/client-can-reserve", {})
        .then((resp) => {
          console.log(resp.data);
          if (resp.data.numberOfPenalties === 3) {
            this.openCantReserveModal();
          } else if (resp.data.username != "") {
            this.$axios
              .post(
                `/api/reserve?&username=${resp.data.username}&rentableId=${rentableId}&start=${startDateTime}&end=${endDateTime}&price=${price}&additionalServices=${this.services}&discountId=${discountId}`
              )
              .then((response) => {
                console.log(response.data);
                this.openModal();
              });
          } else {
            this.$router.push({ path: "/login" });
          }
        })
        .catch((err) => {
          alert("Couldn't reserve.")
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