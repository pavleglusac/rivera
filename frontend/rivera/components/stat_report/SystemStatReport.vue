<template>
  <div class="container">
    <b-card>
      <div class="form-row align-items-center">
        <div class="form-group col-md-1"></div>
        <div class="form-group col-md-3">
          <b-form-datepicker
            size="sm"
            v-model="startDate"
            placeholder="Start date"
          ></b-form-datepicker>
        </div>
        <div class="form-group col-md-3">
          <b-form-datepicker
            size="sm"
            v-model="endDate"
            placeholder="End date"
          ></b-form-datepicker>
        </div>
        <div class="form-group col-md-2">
          <b-select id="type" v-model="type" size="sm">
            <option v-for="t in types" :key="t" :value="t" size="sm">
              {{ t }}
            </option>
          </b-select>
        </div>
        <div class="form-group col-md-2" style="text-align: center">
          <b-button class="prime-btn w-100" size="sm" @click="loadAttendance"
            >Load</b-button
          >
        </div>
      </div>

      <div class="form-row align-items-center">
        <div class="form-group col-md-3 m-1"></div>
        <div class="form-group col-md-6 m-1">
          <canvas id="myChart" style="max-width=100%"></canvas>
        </div>
        <div class="form-group col-md-3 m-1"></div>
      </div>
    </b-card>
  </div>
</template>


<script>
import Chart from "chart.js/auto";

export default {
  data() {
    return {
      xValues: "",
      yValues: "",
      startDate: "",
      endDate: "",
      type: "day",
      types: ["day", "week", "month", "year"],
      chart: "",
    };
  },
  mounted() {},
  methods: {
    loadAttendance() {
      var that = this;
      this.$axios
        .get(
          `/api/admin/system-income?&startDate=${
            this.startDate + "T00:00:00"
          }&endDate=${this.endDate + "T00:00:00"}&type=${this.type}`
        )
        .then((response) => {
          console.log(response.data);
          that.xValues = response.data.map((e) => e.label);
          that.yValues = response.data.map((e) => e.income);
          console.log(that.xValues);
          console.log(that.yValues);
          this.createChart();
        });
    },

    createChart() {
      if (this.chart != "") this.chart.destroy();
      this.chart = new Chart("myChart", {
        type: "bar",
        data: {
          labels: this.xValues,
          datasets: [
            {
              label: "System income",
              data: this.yValues,
              backgroundColor: "rgb(75, 192, 192)",
            },
          ],
        },
        options: {
          legend: { display: false },
          title: {
            display: true,
            text: "System income",
          },
          scales: {
            y: {
              beginAtZero: true,
            },
          },
        },
      });
    },
  },
};
</script>