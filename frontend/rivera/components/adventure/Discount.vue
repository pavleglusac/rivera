<template>
  <b-card class="discount discount-card">
    <p style="font-weight: 500; font-size: 20px">
      <font-awesome-icon icon="calendar" /> {{ discount.start }} -
      {{ discount.end }}
    </p>
    <b-card-text>
      <p v-if="services != ''">Additional services included: {{ services }}</p>
      <li v-for="tag in discount.tags" :key="tag" class="mb-1">
        <span style="font-size: 12px" class="tag">{{ tag }}</span>
      </li>
      <hr />
      <s style="font-size: 20px; font-weight: 500" v-if="oldPrice != 0">{{ oldPrice }}$</s>&nbsp;&nbsp;&nbsp;
      <span style="font-size: 20px; color: #16c79a; font-weight: 600"
        ><font-awesome-icon icon="tag" /> {{ discount.price }}$</span
      >
      <div style="float: right; display: inline">
        <b-button v-if="isOwner" @click="deleteDiscount" style="border: none"
          >Delete discount</b-button
        >
        <b-button @click="reserve" v-if="!isOwner" class="prime-btn"
          >Book now!</b-button
        >
      </div>
    </b-card-text>
  </b-card>
</template>

<script>
export default {
  props: ["discount", "openModal", "openCantReserveModal", "openDeletedDiscountModal", "isOwner"],
  mounted() {},
  data() {
    return {
      services: "",
      oldPrice: 0,
    };
  },
  mounted() {
    this.calculateOldPrice();
    this.services = this.discount.additionalServices.join(", ");
  },
  methods: {
    formatDate(date) {
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
        current_datetime.getFullYear() +
        "-" +
        month +
        "-" + 
        day + 
        "T" +
        hours +
        ":" +
        minutes + 
        ":00"
      );
    },
    calculateOldPrice() {
      var that = this;
      this.$axios
        .get("/api/auth/get-logged-username", {
          headers: {
            Authorization: "Bearer " + window.localStorage.getItem("JWT"),
          },
        })
        .then((resp) => {
          this.$axios
            .get(
              `/api/get-reservation-price?&username=${resp.data}&rentableId=${
                this.$route.params.rentable
              }&start=${that.formatDate(that.discount.start)}&end=${that.formatDate(that.discount.end)}`
            )
            .then((response) => {
              console.log("OLD PRICE")
              console.log(response.data);
              this.oldPrice = response.data;
            });
        });
    },
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
    reloadPage() {
      window.location.reload();
    },
    deleteDiscount() {
      let that = this;
      this.$axios
        .post(`/api/delete-discount?id=${that.discount.id}`)
        .then((resp) => {
          this.openDeletedDiscountModal();
          setTimeout(this.reloadPage, 3000);
        });
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
                `/api/reserve?&username=${resp.data}&rentableId=${this.$route.params.rentable}
                &start=${startDateTime}&end=${endDateTime}&price=${price}&additionalServices=&discountId=${this.discount.id}`
              )
              .then((response) => {
                console.log(response.data);
                this.openModal();
              });
          } else {
            alert("Owner can't reserve discounts!");
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
.discount {
  border-top: 10px solid #11698e;
  margin-bottom: 10px;
}
</style>