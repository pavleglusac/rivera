<template>
<b-container class="bv-example-row">
    <b-card style="margin-bottom: 10px; margin-top: 10px">
        <b-row>
          <b-col>
            <h4>My Calendar</h4>
              <p>You can finally track all your reservations easier!</p>
              <b-calendar v-model="selectedDate" v-on:input="loadReservationsCalendar" :date-info-fn="dateClass" block locale="en-US"></b-calendar>
          </b-col>
          <b-col>
            <h4>Reservation history</h4>
            <b-form-input
                id="search-filter"
                size="sm"
                placeholder="Search reservations..."
                v-model="search"
                v-on:input="loadReservations"
            ></b-form-input>
            <b-form-select class="mt-2 mb-2" v-model="selected" v-on:input="loadReservations" size="sm" :options="options"></b-form-select>
            <ReservationCard 
              v-for="(reservation, index) in reservations"
              :reservation="reservation"
              v-bind:key="reservation.entity.name + index"
            />
          </b-col>
        </b-row>
    </b-card>
  </b-container>
</template>

<script>
import ReservationCard from "./ReservationCard.vue";
export default {
  name: 'Reservations',
  components: { ReservationCard },
  mounted() {
    this.loadReservations();
  },
  methods: {
    loadReservations() {
      let that = this;
      that.reservations = [];
      console.log(that.selectedDate);
      this.$axios.get('/api/auth/get-logged-username',{
								headers: { 'Authorization' : 'Bearer ' + window.localStorage.getItem("JWT") } 
							}).then((resp) => {
        this.$axios.get(`/api/get-reservations?&username=${resp.data}&search=${that.search.trim()}&date=${that.selectedDate}&upcoming=${that.selected}`)
        .then(response => {
          that.reservations = response.data;
          console.log(response.data);
        });
      });
    },
    loadReservationsCalendar() {
      if(this.selected == "date")
        this.loadReservations();
    },
    dateClass(ymd, date) {
        return this.dateHasReservation(ymd) ? 'table-info' : ''
    },
    dateHasReservation(date) {
      var dayStarts = new Date(date);
      dayStarts.setHours(0,0,0,0);
      var dayEnds = new Date(date);
      dayEnds.setHours(23,59,59,999);
      for(const r of this.reservations) {
        var start = new Date(r.startDateTime);
        var end = new Date(r.endDateTime);
        // appointment started in this day
        if(dayStarts <= start && start <= dayEnds) {
          return true;
        }
        // appointment ended in this day
        if(dayStarts <= end && end <= dayEnds) {
          return true;
        }
        // appointment lasted whole day and more
        if(start <= dayStarts && end >= dayEnds) {
          return true;
        }
      }
      return false;
    }
  },
  data() {
    return {
      reservations: [],
      search: '',
      selectedDate: new Date().toISOString().slice(0, 10),
      selected: 'all',
      options: [
        { text: 'Upcoming reservations', value: 'upcoming' },
        { text: 'Past reservations', value: 'past' },
        { text: 'Reservations on selected date', value: 'date' },
        { text: 'Show all reservations', value: 'all' }
      ]
    }
  },
}
</script>

<style>

</style>