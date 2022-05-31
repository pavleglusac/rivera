<template>
  <b-card class="text-center discount-card">
    <b-card-title center>{{ discount.price }}$</b-card-title>
    <b-card-text>
      <div class="d-flex flex-column justify-content-center">
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
    </b-card-text>
    <b-button @click="reserve" class="prime-btn mt-2" variant="primary">Book now</b-button>
  </b-card>
</template>

<script>
export default {
  props: ["discount", "openModal"],
  mounted() {
  },
  data() {
    return {};
  },
  methods: {
      reserve() {
      var startDateTime = this.discount.start;
      var endDateTime = this.discount.end;
      var price = this.discount.price;
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
              `/api/reserve?&username=${resp.data}&rentableId=${this.$route.params.rentable}&start=${startDateTime}&end=${endDateTime}&price=${price}`
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
  },
};
</script>

<style>
.discount-card {
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 5px 0 rgba(0, 0, 0, 0.19);
  padding: 10px;
}
</style>