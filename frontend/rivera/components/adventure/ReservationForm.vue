<template>
  <div id="mainDiv">
    <h5><font-awesome-icon icon="calendar-check" /> Reservation</h5>
    <div v-if="!isClient">
      <p>Current client:</p>
      <div style="display: flex">
        <b-form-input
          :disabled="true"
          v-model="username"
          id="inputUsername"
          placeholder="Client Username"
          @input="inputFunction"
        />
      </div>
    </div>
    <p>You can reserve the whole appointment or just the part of it.</p>
    <b-form-radio-group
      v-model="selectedAppointment"
      @change="calculateReservationPrice"
      button-variant="outline-secondary"
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
          @input="
            calculateReservationPrice();
            setTimeMinMax();
          "
          locale="en"
        ></b-form-datepicker>
        <b-form-timepicker
          v-model="chosenStartTime"
          :max="maxStartTime"
          :min="minStartTime"
          @input="
            calculateReservationPrice();
            setTimeMinMax();
          "
        ></b-form-timepicker>
      </b-form>
      <label class="mt-2">Choose ending time:</label>
      <b-form inline>
        <b-form-datepicker
          v-model="chosenEndDate"
          :min="chosenStartDate"
          class="mr-2"
          :max="appointment.end"
          @input="
            calculateReservationPrice();
            setTimeMinMax();
          "
          locale="en"
        ></b-form-datepicker>
        <b-form-timepicker
          v-model="chosenEndTime"
          :max="maxEndTime"
          :min="minEndTime"
          @input="
            calculateReservationPrice();
            setTimeMinMax();
          "
        ></b-form-timepicker>
      </b-form>
    </div>
    <hr />
    <p>You can select which additional services you want included.</p>
    <b-form-checkbox-group
      v-model="selectedAdditionalServices"
      :options="additionalServices"
      switches
      stacked
    ></b-form-checkbox-group>
    <hr />
    <h3 class="text-center">{{ price }}$</h3>
    <p class="text-center text-muted">Total price for your reservation</p>
    <b-button @click="reserveAppointment" class="prime-btn w-100"
      >Reserve now</b-button
    ><br />
    <b-button
      @click="closeThis"
      class="w-100 mt-2"
      style="background-color: white; color: gray"
      >Cancel</b-button
    >
  </div>
</template>

<script>
export default {
  props: ["appointment", "close", "openModal", "openCantReserveAgain", "additionalServices"],
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
      username: "",
      isClient: false,
      selectedAdditionalServices: [],
      listOfClients: [],
      usernames: [],
    };
  },
  mounted() {
    document.getElementById("mainDiv").style.display = "none";
    console.log("POSLATO START VREME: " + this.appointment.start);
    console.log("POSLATO KRAJNJE VREME: " + this.appointment.end);
    var currentUsername;
    let that = this;
    this.$axios
      .get("/api/auth/get-logged-username", {
        headers: {
          Authorization: "Bearer " + window.localStorage.getItem("JWT"),
        },
      })
      .then((resp) => {
        currentUsername = resp.data;
      });
    this.$axios
      .get("/api/auth/getRole", {
        headers: {
          Authorization: "Bearer " + window.localStorage.getItem("JWT"),
        },
      })
      .then((resp) => {
        if (resp.data == "ROLE_CLIENT") {
          that.isClient = true;
          that.username = currentUsername;
          document.getElementById("mainDiv").style.display = "block";
          this.calculateReservationPrice();
        } else {
          this.$axios
            .get(
              `/api/get-client-username-for-current-reservation?&id=${this.$route.params.rentable}`
            )
            .then((resp) => {
              console.log(resp.data + "aasdfasdf");
              if (!resp.data) {
                this.noCurrentClient();
              } else {
                that.username = resp.data;
                document.getElementById("mainDiv").style.display = "block";
                this.calculateReservationPrice();
              }
            });
        }
      });
  },
  methods: {
    noCurrentClient() {
      alert("There is no client that has a reservation at this moment in time");
      this.close();
    },
    inputFunction() {
      document
        .getElementById("inputUsername")
        .classList.remove("error-boarder");
    },
    getStartDateTime() {
      console.log("GETTING START DATE TIME");
      console.log(this.appointment.start);
      console.log(new Date(this.appointment.start));
      if (this.selectedAppointment == "whole") {
        return new Date(this.appointment.start);
      }
      console.log(this.chosenStartDate);
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
    isoDateWithoutTimeZone(date) {
      if (date == null) return date;
      var timestamp = date.getTime() - date.getTimezoneOffset() * 60000;
      var correctDate = new Date(timestamp);
      return correctDate.toISOString();
    },
    reserveAppointment() {
      this.close();
      var startDateTime = this.getStartDateTime();
      var endDateTime = this.getEndDateTime();
      let that = this;
      var price = this.price;
      console.log("SENDING TO BACK");
      console.log(startDateTime);
      console.log(this.isoDateWithoutTimeZone(startDateTime));
      console.log(endDateTime.toString());
      var services = that.selectedAdditionalServices.join("|");
      console.log(services);

      this.$axios
        .get(
          `/api/didClientReserveEarlier?&username=${that.username}&rentableId=${
            this.$route.params.rentable
          }&start=${this.isoDateWithoutTimeZone(
            startDateTime
          )}&end=${this.isoDateWithoutTimeZone(endDateTime)}`
        )
        .then((response) => {
          console.log(response.data);
          if (response.data == false) {
            this.$axios
              .post(
                `/api/reserve?&username=${that.username}&rentableId=${
                  this.$route.params.rentable
                }&start=${this.isoDateWithoutTimeZone(
                  startDateTime
                )}&end=${this.isoDateWithoutTimeZone(
                  endDateTime
                )}&price=${price}&additionalServices=${services}`
              )
              .then((response) => {
                console.log(response.data);
                this.openModal();
              });
          } else {
            this.openCantReserveAgain();
          }
        });
    },
    setTimeMinMax() {
      console.log(
        "---------------------------------------------------------------------------------------------------------"
      );
      var sameStart = this.datesEqual(
        this.chosenStartDate,
        this.appointment.start.date
      );
      var sameEnd = this.datesEqual(
        this.chosenEndDate,
        this.appointment.end.date
      );
      if (sameStart) this.minStartTime = this.appointment.start.time;
      if (sameEnd) this.maxEndTime = this.appointment.end.time;
      if (sameStart && sameEnd) {
        this.maxStartTime = this.chosenEndTime;
        this.minEndTime = this.chosenStartTime;
      }
    },
    calculateReservationPrice() {
      console.log("RACUNAJ");
      var startDateTime = this.getStartDateTime();
      var endDateTime = this.getEndDateTime();
      let that = this;
      this.$axios
        .get(
          `/api/get-reservation-price?&username=${that.username}&rentableId=${
            this.$route.params.rentable
          }&start=${startDateTime.toISOString()}&end=${endDateTime.toISOString()}`
        )
        .then((response) => {
          this.price = response.data;
          console.log(response.data);
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