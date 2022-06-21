<template>
  <div class="container">
      <div class="form-row align-items-center">
        <div class="form-group col-1"></div>
        <div class="form-group col-3">
          <b-form-datepicker
            size="sm"
            v-model="startDate"
            placeholder="Start date"
          ></b-form-datepicker>
        </div>
        <div class="form-group col-3">
          <b-form-datepicker
            size="sm"
            v-model="endDate"
            placeholder="End date"
          ></b-form-datepicker>
        </div>
        <div class="form-group col-2">
          <b-select id="type" v-model="type" size="sm">
            <option v-for="t in types" :key="t" :value="t" size="sm">
              {{ t }}
            </option>
          </b-select>
        </div>
        <div class="form-group col-2">
          <b-button @click="loadAttendance" class="prime-btn w-100" size="sm"
            >Load</b-button
          >
        </div>
      </div>
      <div class="form-row align-items-center">
        <div class="form-group col-md-12 m-1">
          <canvas id="myChart" style="max-width=100%"></canvas>
        </div>
      </div>
  </div>
</template>


<script>
export default {
  data() {
    return {
      xValues: "",
      yValues: "",
      startDate: "",
      endDate: "",
      type: "week",
      types: ["week", "month", "year"],
      chart: "",
    };
  },
  mounted() {},
  methods: {
    loadAttendance() {
      var that = this;
      if(this.startDate != "" && this.endDate != "") {
        this.$axios
          .get(
            `/api/get-attendance?&startDate=${this.startDate}&endDate=${this.endDate}&type=${this.type}`
          )
          .then((response) => {
            console.log(response.data);
            that.xValues = response.data.map((e) => e.timestamp);
            that.yValues = response.data.map((e) => e.count);
            if (this.type == "week")
              that.xValues = that.xValues.map((e) => e.split("T")[0]);
            if (this.type == "month")
              that.xValues = that.xValues.map(
                (e) => e.split("-")[0] + "-" + e.split("-")[1]
              );
            if (this.type == "year")
              that.xValues = that.xValues.map((e) => e.split("-")[0]);
            this.createChart();
          });
      } else {
        alert("You have to select dates first");
      }
    },
    createChart() {
      if (this.chart != "") this.chart.destroy();
      this.chart = new Chart("myChart", {
        type: "bar",
        data: {
          labels: this.xValues,
          datasets: [
            {
              data: this.yValues,
                            backgroundColor: "rgb(75, 192, 192)",

            },
          ],
        },
        options: {
          legend: { display: false },
          title: {
            display: true,
            text: "Attendance",
          },
          scales: {
            yAxes: [
              {
                ticks: {
                  beginAtZero: true,
                },
              },
            ],
          },
        },
      });
    },
  },
};
</script>