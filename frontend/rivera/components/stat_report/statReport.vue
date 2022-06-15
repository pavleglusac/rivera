<template>
	<div>
		<b-card class="mb-3" style="margin: 40px; margin-top: 10px">
            <div class="container-fluid p-2">
          <ul class="nav nav-pills nav-fill" role="tablist">
            <li class="nav-item">
              <p class="nav-link" v-bind:class="{ active: activeAverageGrade }" id="adventures-btn" @click="loadAverageGrade">
                <span><font-awesome-icon icon="star" /> Average scores</span>
              </p>
            </li>
            <li class="nav-item">
              <p class="nav-link" v-bind:class="{ active: activeAttendance }" id="cottages-btn" @click="loadAttendance">
                <span><font-awesome-icon icon="person" /> Attendance graph</span>
              </p>
            </li>
            <li class="nav-item">
              <p class="nav-link" v-bind:class="{ active: activeIncomeGraph }" id="boats-btn" @click="loadIncome">
                <span><font-awesome-icon icon="coins" /> Income graph</span>
              </p>
            </li>
          </ul>
        </div>
        <div v-if="activeAttendance">
            <AttendanceGraph />
        </div>
        <div v-if="activeIncomeGraph">
            <IncomeGraph />
        </div>
        <div v-if="activeAverageGrade">
            <AverageScores />
        </div>
        </b-card>
    </div>
</template>


<script>
import AttendanceGraph from './attendanceGraph.vue'
import IncomeGraph from './incomeGraph.vue'
import myRentables from './../../components/myRentables.vue'
import AverageScores from './AverageScores.vue'

export default {
    components: { AttendanceGraph, IncomeGraph, myRentables, AverageScores },
    data() {
        return {
            activeAverageGrade: true,
            activeAttendance: false,
            activeIncomeGraph: false,
        }
    },
    mounted() {
    },
    methods: {
        loadIncome(){
            this.activeAverageGrade = false;
            this.activeAttendance = false;
            this.activeIncomeGraph = true;
        },
        loadAttendance(){
            this.activeAverageGrade = false;
            this.activeAttendance = true;
            this.activeIncomeGraph = false;
        },
        loadAverageGrade(){
            this.activeAverageGrade = true;
            this.activeAttendance = false;
            this.activeIncomeGraph = false;
        }
    }

}
</script>