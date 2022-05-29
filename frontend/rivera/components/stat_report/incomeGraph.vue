<template>
    <div>
        <div class="form-row align-items-center">
              <div class="form-group col-md-3 m-1">
        </div>
            
              <div class="form-group col-md-3 m-1">
                <b-form-datepicker size="sm"  v-model="startDate" placeholder="Start date"></b-form-datepicker>
        </div>
              <div class="form-group col-md-3 m-1">
                <b-form-datepicker size="sm"  v-model="endDate" placeholder="End date"></b-form-datepicker>
        </div>
        </div>
        <div class="form-row align-items-center">
              <div class="form-group col-md-5 m-1">
        </div>        
            <div class="form-group col-md-2 m-1 mt-3 align-items-center" style="text-align:center">
                <b-button pill variant="success" @click="loadAttendance" class="w-25">Load</b-button>
            </div>
        </div>
        
        <div class="form-row align-items-center">
              <div class="form-group col-md-3 m-1">
            </div>
            <div class="form-group col-md-6 m-1">
<canvas id="myChart" style="max-width=100%"></canvas>
            </div>
              <div class="form-group col-md-3 m-1">
            </div>
        </div>
    <div style="text-align:center">
       Full income: {{fullIncome}}
    </div>
    </div>
</template>


<script>
export default {
    data() {
        return {
            xValues: '',
            yValues: '',
            startDate: '',
            endDate: '',
            fullIncome: 0,
            chart : '',
        }
    },
    mounted() {
    },
    methods: {
        loadAttendance(){
            var that = this;            
            this.$axios.get(`/api/get-income?&startDate=${this.startDate}&endDate=${this.endDate}`)
            .then(response => {
                console.log(response.data);
                that.xValues = response.data.map(e => e.rentableName);
                that.yValues = response.data.map(e => e.income);
                console.log(that.xValues);
                console.log(that.yValues);
                that.fullIncome = (that.yValues).reduce((a, b) => a + b, 0)
                this.createChart();
            });
        },
        
        createChart(){
            if(this.chart != '')
            this.chart.destroy();
            this.chart = new Chart("myChart", {
            type: "bar",
            data: {
                labels: this.xValues,
                datasets: [{
                data: this.yValues
                }]
            },
            options: {
                legend: {display: false},
                title: {
                display: true,
                text: "Income"
                },
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true,
                        }
                    }]
    }
            }
            });
        }
    }
    
    

}
</script>