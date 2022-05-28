<template>
    <div>
            
        <div class="container-fluid p-2">
          <ul class="nav nav-pills nav-fill" role="tablist">
            <li class="nav-item">
              <p class="nav-link" v-bind:class="{ active: activeAverageGrade }" id="adventures-btn" @click="loadAverageGrade">
                <span><font-awesome-icon icon="fish" /> Average rentable grade</span>
              </p>
            </li>
            <li class="nav-item">
              <p class="nav-link" v-bind:class="{ active: activeAttendance }" id="cottages-btn" @click="loadAttendance">
                <span><font-awesome-icon icon="house" /> Attendance graph</span>
              </p>
            </li>
            <li class="nav-item">
              <p class="nav-link" v-bind:class="{ active: activeIncomeGraph }" id="boats-btn" @click="loadIncome">
                <span><font-awesome-icon icon="sailboat" /> Income graph</span>
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
    </div>
</template>


<script>
import AttendanceGraph from './attendanceGraph.vue'
import IncomeGraph from './incomeGraph.vue'
export default {
    components: { AttendanceGraph, IncomeGraph },
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