<template>
  <div class="container">
    <h3><font-awesome-icon icon="tag" /> Add discount</h3>

    <p>Start:</p>
    <b-row style="margin-top: -10px; margin-bottom: 5px">
      <b-col cols="7">
        <b-form-datepicker
          v-model="startDate"
          placeholder="Start date"
        ></b-form-datepicker>
      </b-col>
      <b-col cols="5">
        <b-form-timepicker
          v-model="startTime"
          placeholder="Start time"
        ></b-form-timepicker>
      </b-col>
    </b-row>

    <p>End:</p>
    <b-row style="margin-top: -10px; margin-bottom: 5px">
      <b-col cols="7">
        <b-form-datepicker
          v-model="endDate"
          placeholder="End date"
        ></b-form-datepicker>
      </b-col>
      <b-col cols="5">
        <b-form-timepicker
          v-model="endTime"
          placeholder="End time"
        ></b-form-timepicker>
      </b-col>
    </b-row>

    <p>Tags (key words that describe this discount):</p>
    <b-form-tags
      style="margin-top: -10px; margin-bottom: 5px"
      input-id="tags-basic"
      v-model="tags"
      placeholder="Tags"
    ></b-form-tags>

    <div v-if="additionalServices.length > 0">
      <p>Select additional services:</p>
      <b-form-checkbox-group
        v-model="selectedAdditionalServices"
        :options="additionalServices"
        switches
        stacked
      ></b-form-checkbox-group>
    </div>

    <p>Price:</p>
    <b-input
      style="margin-top: -10px; margin-bottom: 5px"
      v-model="price"
      id="inputPrice"
      placeholder="Price"
    />

    <p>Capacity:</p>
    <b-input
      style="margin-top: -10px; margin-bottom: 5px"
      v-model="capacity"
      id="inputCapacity"
      placeholder="Capacity"
    />

    <p id="errorMessage" style="color: #dc143c"></p>
    <b-button @click="upload" class="prime-btn w-100 mt-2" size="m"
      >Add discount</b-button
    >
  </div>
</template>


<script>
export default {
  data() {
    return {
      startDate: "",
      endDate: "",
      startTime: "",
      endTime: "",
      capacity: "",
      price: "",
      tags: [],
      selectedAdditionalServices: []
    };
  },
  props: ["openModal", "additionalServices"],
  mounted() {
    console.log(this.additionalServices)
  },
  methods: {
    isPositiveInteger(str) {
      if (typeof str !== "string") {
        return false;
      }
      const num = Number(str);
      if (Number.isInteger(num) && num > 0) {
        return true;
      }
      return false;
    },
    isAfterYesterday(date) {
      const yesterday = new Date();
      yesterday.setDate(yesterday.getDate() - 1);
      return date > yesterday;
    },
    startBeforeEnd() {
      var start = new Date(this.startDate + " " + this.startTime);
      var end = new Date(this.endDate + " " + this.endTime);
      return start < end;
    },
    validData() {
      if (this.startDate == "")
        document.getElementById("errorMessage").innerHTML =
          "You have to enter start date!";
      else if (this.endDate == "")
        document.getElementById("errorMessage").innerHTML =
          "You have to enter end date!";
      else if (this.startTime == "")
        document.getElementById("errorMessage").innerHTML =
          "You have to enter start time!";
      else if (this.endTime == "")
        document.getElementById("errorMessage").innerHTML =
          "You have to enter end time!";
      else if (!this.isPositiveInteger(this.price))
        document.getElementById("errorMessage").innerHTML =
          "You have to enter discount price! It has to be positive number.";
      else if (!this.isPositiveInteger(this.capacity))
        document.getElementById("errorMessage").innerHTML =
          "You have to enter capacity! It has to be positive number.";
      else if (!this.isAfterYesterday(new Date(this.startDate + " " + this.startTime)))
        document.getElementById("errorMessage").innerHTML =
          "You have to enter valid start date! It has to be in the future.";
      else if (!this.startBeforeEnd())
        document.getElementById("errorMessage").innerHTML =
          "Start date has to be before end date!";
      // check if there is something in that period
      else if(this.isPeriodAvailable())
              document.getElementById("errorMessage").innerHTML =
          "There is already a reservation in that period of time!";
      else document.getElementById("errorMessage").innerHTML = "";
      return document.getElementById("errorMessage").innerHTML == "";
    },
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
    isPeriodAvailable() {
      var start = this.formatDate(this.startDate + " " + this.startTime);
      console.log(this.start);
      var end = this.formatDate(this.endDate + " " + this.endTime);
      console.log(this.end);
      this.$axios
        .get(
          `/api/is-period-reserved?&id=${this.$route.params.rentable}&start=${start}&end=${end}`
        )
        .then((response) => {
          console.log("TADA IMA VEC NEKA REZERVACIJA");
          console.log(response.data);
          return !response.data;
        });
    },
    reloadPage() {
      window.location.reload();
    },
    upload() {
      if (this.validData()) {
        var formData = new FormData();
        formData.append("startDateTime", this.startDate + "T" + this.startTime);
        formData.append("endDateTime", this.endDate + "T" + this.endTime);
        formData.append("capacity", parseInt(this.capacity));
        formData.append("price", parseFloat(this.price).toFixed(2));
        formData.append("tags", this.tags);
        formData.append("rentableId", this.$route.params.rentable);
        formData.append("additionalServices", this.selectedAdditionalServices);
        this.$axios
          .post("/api/add-discount", formData)
          .then((resp) => {
            this.openModal();
            setTimeout(this.reloadPage, 3000);
            console.log(resp);
          })
          .catch((err) => {
            console.log(err);
          });
      }
    },
  },
};
</script>