<template>
  <div class="text-center discount-card" style="padding: 0; border-radius: 5px">
    <b-card-title
      @click="reserve"
      style="
        background-color: #16c79a;
        color: white;
        padding: 10px;
        cursor: pointer;
      "
      center
    >
      <font-awesome-icon icon="tag" /> Book for<br />only {{ discount.price }}$
    </b-card-title>
    <b-card-text>
      <div
        class="d-flex flex-column justify-content-center"
        style="font-weight: 400; font-size: 20px; margin-bottom: 15px"
      >
        <div class="d-flex justify-content-center">
          {{ discount.start }}
        </div>
        <div class="d-flex justify-content-center">
          <font-awesome-icon icon="arrow-down" />
        </div>
        <div class="d-flex justify-content-center">
          {{ discount.end }}
        </div>
      </div>
      <hr />
      <li v-for="tag in discount.tags" :key="tag" class="mb-3">
        <span class="tag">{{ tag }}</span>
      </li>
    </b-card-text>
  </div>
</template>

<script>
export default {
  props: ["discount", "openModal", "openCantReserveModal"],
  mounted() {},
  data() {
    return {};
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
      return (
        day +
        "/" +
        month +
        "/" +
        current_datetime.getFullYear() +
        " " +
        current_datetime.getHours() +
        ":" +
        minutes
      );
    },
    reserve() {
      var startDateTime = this.dateFormat(this.discount.start);
      var endDateTime = this.dateFormat(this.discount.end);
      var price = this.discount.price;
      this.$axios
        .get("/api/auth/client-can-reserve", {})
        .then((resp) => {
          if (resp.data === "3") {
            this.openCantReserveModal();
          } else if (resp.data != "no-client") {
            console.log(resp.data);
            this.$axios
              .post(
                `/api/reserve?&username=${resp.data}&rentableId=${this.$route.params.rentable}&start=${startDateTime}&end=${endDateTime}&price=${price}`
              )
              .then((response) => {
                console.log(response.data);
                this.openModal();
              });
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style>
.discount-card {
  box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.2), 0 3px 5px 0 rgba(0, 0, 0, 0.19);
}
.discount-card:hover {
  transform: translateY(-10px);
  transition: all ease 0.3s;
}
</style>