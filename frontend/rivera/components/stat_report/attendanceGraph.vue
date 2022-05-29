<template>
    <div>
        <div class="form-row align-items-center">
            
              <div class="form-group col-md-2 m-1">
                <b-form-datepicker size="sm"  v-model="startDate" placeholder="Start date"></b-form-datepicker>
        </div>
              <div class="form-group col-md-2 m-1">
                <b-form-datepicker size="sm"  v-model="endDate" placeholder="End date"></b-form-datepicker>
        </div>
              <div class="form-group col-md-2 m-1">
                <select id="type" v-model="type" class="form-control" size="1">
						<option v-for="t in types" :key="t" :value="t">
						{{ t }}
						</option>
					</select>
        </div>
            <div class="form-group col-md-2 m-1">
                <b-button pill variant="success" @click="loadAttendance" class="w-25">Load</b-button>
            </div>
        </div>
<canvas id="myChart" style="width:100%;max-width:600px"></canvas>
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
            type: 'week',
            types:['week','month','year'],
            chart : '',
        }
    },
    mounted() {
    },
    methods: {
        loadAttendance(){
            var that = this;            
            this.$axios.get(`/api/get-attendance?&startDate=${this.startDate}&endDate=${this.endDate}&type=${this.type}`)
            .then(response => {
                console.log(response.data);
                that.xValues = response.data.map(e => e.timestamp);
                that.yValues = response.data.map(e => e.count);
                that.xValues = that.xValues.map(e => e.split('T')[0]);
                that.xValues[0] = this.startDate;
                console.log(that.xValues);
                console.log(that.yValues);
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
                text: "Attendance"
                },
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
    }
            }
            });
        }
    }
    
    

}
</script>