<template>
<b-container class="bv-example-row">
    <b-card style="margin-bottom: 10px; margin-top: 10px">
        <b-row>
          <b-col>
            <h4>Reservation history</h4>
            <b-form-input
                id="search-filter"
                size="sm"
                placeholder="Search reservations..."
                v-model="searchText"
                v-on:input="reload"
            ></b-form-input>
            <b-button-group class="reservation-buttons" size="sm">
              <b-button variant="info">Upcoming reservations</b-button>
              <b-button variant="info">Past reservations</b-button>
              <b-button variant="info">Show all</b-button>
            </b-button-group>
            <h6>21.05.2022.</h6>
          </b-col>
          <b-col>
            <h4>My Calendar</h4>
              <b-calendar block locale="en-US"></b-calendar>
          </b-col>
        </b-row>
    </b-card>
  </b-container>
</template>

<script>
export default {
  components: {  },

}
</script>

<style>

</style>