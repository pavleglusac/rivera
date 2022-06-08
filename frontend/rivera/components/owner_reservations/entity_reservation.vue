<template>
  <b-card no-body class="shadow-sm" :class="{cancelledCard : reservation.cancelled}">
	<span v-if="reservation.cancelled == 'true'" class="cancelled-tag">Cancelled</span>
    <b-card-body class="h-100">
      <b-container>
      <div class="d-flex align-items-center justify-content-between">
        <div
          @click="goToClient()"
          class="d-flex align-items-center"
          style="cursor: pointer"
        >
          <b-avatar
            :src="'http://localhost:8080' + reservation.client.photo"
            size="3rem"
          ></b-avatar>
          <div class="ml-2">
            <p style="font-size: 1.2em; font-weight: 500">
              {{ reservation.client.name }} {{ reservation.client.surname }}
              <span style="font-size: 1em; font-weight: 400" class="text-muted"
                >@{{ reservation.client.username }}
              </span>
            </p>
          </div>
        </div>
        <div class="d-flex justify-content-between align-items-center">
          <font-awesome-icon
            style="font-size: 1.5em; color: #11698e"
            icon="clock"
          />&nbsp;&nbsp;
          <span style="font-weight: 400">
            Start: {{ formatDate(new Date(reservation.start)) }} <br />
            End: {{ formatDate(new Date(reservation.end)) }}
          </span>
        </div>

        <div
          style="float: right"
        >
          <b-button
            :disabled="isDateAfterToday(new Date(this.reservation.end))"
						v-bind:class="{ 'disabled-report': isDateAfterToday(new Date(this.reservation.end)) }"
            v-if="!this.reservation.report"
            variant="primary"
            class="book-btn"
            @click="fileReport"
            >File a report</b-button
          >
          <b-button
            :disabled="isDateAfterToday(new Date(this.reservation.end))"
						v-bind:class="{ 'disabled-report': isDateAfterToday(new Date(this.reservation.end)) }"
            v-else
            variant="primary"
            class="book-btn"
            @click="viewReport"
            >View filed report</b-button
          >
        </div>
      </div>
      </b-container>
    </b-card-body>
  </b-card>
</template>

<script>
export default {
  mounted() {
    console.log(this.reservation.cancelled);
  },
  name: "EntityReservation",
  components: {},
  data() {
    return {};
  },
  methods: {
    goToClient() {
      this.$router.push({
        path: "/profile/" + this.reservation.client.username,
      });
    },
    formatDate(date) {
      var hours = date.getHours();
      var minutes = date.getMinutes();
      var ampm = hours >= 12 ? "pm" : "am";
      hours = hours % 12;
      hours = hours ? hours : 12;
      minutes = minutes < 10 ? "0" + minutes : minutes;
      var strTime = hours + ":" + minutes + " " + ampm;
      return (
        date.toLocaleDateString("en-US", {
          weekday: "long",
          year: "numeric",
          month: "long",
          day: "numeric",
        }) +
        " at " +
        strTime
      );
    },
    fileReport() {
		this.reportModal(this.reservation.id);
    },
    viewReport() {
		this.viewReportModal(this.reservation.id);
    },
    isDateAfterToday(date) {
      return (
        new Date(date.toDateString()) > new Date(new Date().toDateString())
      );
    },
  },
	props: ['entity','offerType',
		'reservation',
		'reportModal','viewReportModal'
	],
};
</script>

<style scoped>
li {
  display: inline-block;
  list-style-type: none;
}

.tag {
  background-color: var(--light-blue-color);
  border: none;
  border-radius: 5px;
  padding: 5px;
  margin: 3px;
  text-align: center;
  color: #fff;
  display: inline;
  font-size: 12px;
}

.cancelled-tag {
  font-size: 10px;
  background-color: #cc1100;
  padding: 5px;
  border: none;
  color: white;
  font-weight: 500;
}

.cancelledCard {
	border: 1px solid #cc1100;
}

.book-btn {
  background-color: var(--prime-color);
  border: none;
}
.book-btn:hover {
  background-color: #39aea9;
}

.cover-img {
  width: 300px;
  height: 300px;
  object-fit: cover;
}

.disabled-report{
  background-color: grey;
}
.disabled-report:hover{
  background-color: grey;
}
</style>
