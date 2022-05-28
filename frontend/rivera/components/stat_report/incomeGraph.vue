<template>
    <div>
<canvas id="myChart" style="width:100%;max-width:600px"></canvas>
    </div>
</template>


<script>
export default {
    data() {
        return {
            xValues: '',
            yValues: '',
        }
    },
    mounted() {
        this.loadIncome();
    },
    methods: {
        loadIncome(){
            var that = this;            
            this.$axios.get(`/api/get-income`)
            .then(response => {
                console.log(response.data);
                that.xValues = response.data.map(e => e.rentableDTO.name);
                that.yValues = response.data.map(e => e.income);
                console.log(that.xValues);
                console.log(that.yValues);
                that.yValues.push(2);
                that.xValues.push("2023-05-16T00:00:00");
                that.yValues.push(4);
                that.xValues.push("2024-05-16T00:00:00");
                this.createChart();
            });
        },
        
        createChart(){
            new Chart("myChart", {
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