<template>
  <div>
    <h5><font-awesome-icon icon="calendar-check" /> Reservation</h5>
    <p>You can reserve the whole appointment or just the part of it.</p>
    <b-form-radio-group
      v-model="selectedAppointment"
      @change="calculateReservationPrice"
    >
      <b-form-radio id="whole-appointment" value="whole"
        >I want the whole appointment.</b-form-radio
      >
      <b-form-radio id="part-appointment" value="part"
        >I want to choose date and time.</b-form-radio
      >
    </b-form-radio-group>
    <div v-if="selectedAppointment == 'part'">
      <hr />
      <label>Choose starting time:</label>
      <b-form inline>
        <b-form-datepicker
          v-model="chosenStartDate"
          :min="appointment.start"
          :max="chosenEndDate"
          class="mr-2"
          @input="calculateReservationPrice; setTimeMinMax"
          locale="en"
        ></b-form-datepicker>
        <b-form-timepicker
          v-model="chosenStartTime"
          :max="maxStartTime"
          :min="minStartTime"
          @input="calculateReservationPrice; setTimeMinMax"
        ></b-form-timepicker>
      </b-form>
      <label class="mt-2">Choose ending time:</label>
      <b-form inline>
        <b-form-datepicker
          v-model="chosenEndDate"
          :min="chosenStartDate"
          class="mr-2"
          :max="appointment.end"
          @input="calculateReservationPrice; setTimeMinMax"
          locale="en"
        ></b-form-datepicker>
        <b-form-timepicker
          v-model="chosenEndTime"
          :max="maxEndTime"
          :min="minEndTime"
          @input="calculateReservationPrice; setTimeMinMax"
        ></b-form-timepicker>
      </b-form>
    </div>
    <hr />
    <p>Total price for your reservation: {{ price }}$</p>
    <div style="float: right">
      <b-button @click="closeThis">Cancel</b-button>
      <b-button @click="reserveAppointment" class="prime-btn"
        >Reserve now</b-button
      >
      <b-modal id="reservedModal" title="Congratulations!">
        <p class="my-4">You have successfully reserved your appointment.</p>
      </b-modal>
    </div>
  </div>
</template>

<script>
export default {
  props: ["appointment", "close", "openModal"],
  data() {
    return {
      selectedAppointment: "whole",
      chosenStartDate: this.appointment.start,
      chosenStartTime: this.appointment.start.toString().split(" ")[4],
      chosenEndDate: this.appointment.end,
      chosenEndTime: this.appointment.end.toString().split(" ")[4],
      maxStartTime: "23:59",
      maxEndTime: "23:59",
      minStartTime: "00:00",
      minEndTime: "00:00",
      price: 0,
    };
  },
  mounted() {
    this.calculateReservationPrice();
  },
  methods: {
    getStartDateTime() {
      if (this.selectedAppointment == "whole") {
        return new Date(this.appointment.start);
      }
      return new Date(
        this.chosenStartDate.toISOString().slice(0, 10) +
          " " +
          this.chosenStartTime
      );
    },
    getEndDateTime() {
      if (this.selectedAppointment == "whole") {
        return new Date(this.appointment.end);
      }
      return new Date(
        this.chosenEndDate.toISOString().slice(0, 10) + " " + this.chosenEndTime
      );
    },
    datesEqual(d1, d2) {
      const date1 = new Date(d1);
      const date2 = new Date(d2);
      if (
        date1.getFullYear() === date2.getFullYear() &&
        date1.getMonth() === date2.getMonth() &&
        date1.getDate() === date2.getDate()
      )
        return true;
      return false;
    },
    reserveAppointment() {
      var startDateTime = this.getStartDateTime();
      var endDateTime = this.getEndDateTime();
      var price = this.price;
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
              `/api/reserve?&username=${resp.data}&rentableId=${
                this.$route.params.rentable
              }&start=${startDateTime.toISOString()}&end=${endDateTime.toISOString()}&price=${price}`
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
    setTimeMinMax() {
      var sameStart = this.datesEqual(this.chosenStartDate, this.appointment.start.date);
      var sameEnd = this.datesEqual(this.chosenEndDate, this.appointment.end.date);
      if (sameStart) 
        this.minStartTime = this.appointment.start.time;
      if (sameEnd)
        this.maxEndTime = this.appointment.end.time;
      if (sameStart && sameEnd) {
        this.maxStartTime = this.chosenEndTime;
        this.minEndTime = this.chosenStartTime;
      }
    },
    calculateReservationPrice() {
      var startDateTime = this.getStartDateTime();
      var endDateTime = this.getEndDateTime();
      this.$axios
        .get("/api/auth/get-logged-username", {
          headers: {
            Authorization: "Bearer " + window.localStorage.getItem("JWT"),
          },
        })
        .then((resp) => {
          console.log(resp.data);
          this.$axios
            .get(
              `/api/get-reservation-price?&username=${resp.data}&rentableId=${
                this.$route.params.rentable
              }&start=${startDateTime.toISOString()}&end=${endDateTime.toISOString()}`
            )
            .then((response) => {
              this.price = response.data;
              console.log(response.data);
            });
        })
        .catch((err) => {
          console.log(err);
        });
    },
    closeThis() {
      this.close();
    },
  },
};
</script>

<style>
</style>