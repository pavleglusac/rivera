<template>
  <div class="w-100 h-100">
    <b-modal
      id="repeat_modal"
      title="Availability creation"
      ref="repeat_modal"
      size="xl"
      hide-footer
      no-stacking
    >
      <h2>Repeat:</h2>
      <b-form-group label="Button style radios">
        <b-form-radio
          v-model="selected_repeat"
          value="norepeat"
          button
          button-variant="light"
          size="lg"
        >
          No repeat
        </b-form-radio>
        <b-form-radio
          v-model="selected_repeat"
          value="week"
          button
          button-variant="light"
          size="lg"
        >
          Every week
        </b-form-radio>
        <b-form-radio
          v-model="selected_repeat"
          value="date"
          button
          button-variant="light"
          size="lg"
        >
          On date
        </b-form-radio>
      </b-form-group>
      <b-button @click="timeModal" class="float-right">Next</b-button>
    </b-modal>
    <!-- TIME picker -->
    <b-modal
      id="time_modal"
      title="Availability creation"
      ref="time_modal"
      size="xl"
      hide-footer
      no-stacking
    >
      <h2>Select time</h2>
      <label for="startTime">Select start time:</label>
      <input type="time" id="startTime" name="startTime" v-model="startTime" />
      <label for="endTime">Select end time:</label>
      <input type="time" id="endTime" name="endTime" v-model="endTime" />
      <b-form-checkbox
        id="checkbox-1"
        v-model="status"
        name="checkbox-1"
        value="accepted"
        unchecked-value="not_accepted"
      >
        All day
      </b-form-checkbox>
      <b-button @click="timePicked" class="float-right">Next</b-button>
    </b-modal>
    <!-- Month picker -->
    <b-modal
      id="month_modal"
      title="Availability creation"
      ref="month_modal"
      size="xl"
      hide-footer
      no-stacking
    >
      <div>
        <h2 class="mb-2">Apply for months:</h2>
      </div>
      <span class="mb-5 mt-2">
        <b-button variant="light" @click="selectedMonths.push(...months)"
          >Select all</b-button
        >
        <b-button variant="light" @click="selectedMonths = []"
          >Deselect all</b-button
        >
      </span>
      <b-form-group class="mt-3">
        <div class="d-flex justify-content-center">
          <b-form-checkbox
            v-for="month in months"
            :key="month"
            v-model="selectedMonths"
            :value="month.toUpperCase()"
            button
            button-variant="light"
            size="lg"
            class="mr-1"
          >
            {{ month }}
          </b-form-checkbox>
        </div>
      </b-form-group>
      <b-button class="float-right" @click="createPatterns">Next</b-button>
    </b-modal>

    <!-- Mode -->
    <div class="d-flex w-100">
      <b-form-group label="" class="w-100">
        <b-form-radio
          v-model="mode"
          value="view"
          button
          button-variant="light"
          size="lg"
        >
          <font-awesome-icon icon="eye" /> View
        </b-form-radio>
        <b-form-radio
          v-model="mode"
          value="add"
          button
          button-variant="light"
          size="lg"
        >
          <font-awesome-icon icon="plus" /> Add
        </b-form-radio>
        <b-form-radio
          v-model="mode"
          value="subtract"
          button
          button-variant="light"
          size="lg"
        >
          <font-awesome-icon icon="minus" />  Subtract
        </b-form-radio>
        <b-button @click="removeAvailabilities" variant="light" size="lg" class="float-right ml-5 mr-0">
          <font-awesome-icon icon="trash-can" />
        </b-button>
      </b-form-group>
    </div>

    <FullCalendar ref="fullCalendar" :options="calendarOptions" />
  </div>
</template>

<script>
//import '@fullcalendar/core/vdom' // solves problem with Vite
import FullCalendar from "@fullcalendar/vue";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import timeGridPlugin from "@fullcalendar/timegrid";

export default {
  props: ["reservations"],
  components: {
    FullCalendar, // make the <FullCalendar> tag available
  },
  data() {
    return {
      createAvailabilityModal: true,
      options: [
        { text: "No repeat", value: "norepeat" },
        { text: "Every week", value: "everyweek" },
        { text: "Every other week", value: "everyotherweek" },
      ],
      selected_repeat: "norepeat",
      months: [
        "Jan",
        "Feb",
        "Mar",
        "Apr",
        "May",
        "Jun",
        "Jul",
        "Aug",
        "Sep",
        "Oct",
        "Nov",
        "Dec",
      ],
      status: "",
      mode: "view",
      startTime: "09:00",
      endTime: "15:00",
      selectedMonths: [],
      selectedDays: [],
      continuous: false,
      ids: 0,
      year: 2022,
      select_info: {},
      calendarOptions: {
        plugins: [dayGridPlugin, interactionPlugin, timeGridPlugin],
        headerToolbar: {
          left: "prev,next today",
          center: "title",
          right: "dayGridMonth,timeGridWeek,timeGridDay",
        },
        height: "100%",
        initialView: "dayGridMonth",
        events: [
          {
            title: "BCH237",
            start: "2022-04-10T10:30:00",
            end: "2022-04-10T11:30:00",
            extendedProps: {
              department: "BioChemistry",
            },
            description: "Lecture",
            display: "background",
          },
        ],
        editable: true,
        selectable: false,
        selectMirror: true,
        dayMaxEvents: true,
        weekends: true,
        select: this.handleDateSelect,
        eventClick: this.handleEventClick,
        dateClick: this.handleDayClick,
        eventsSet: this.handleEvents,
        datesSet: this.handleMonthChange,
        firstDay: 1,
        eventOverlap: false,
        eventDurationEditable: false,
        eventStartEditable: false,
        background: "green",
        eventBackgroundColor: "#7bb888",
        eventBorderColor: "#7bb888"
        /* you can update a remote database when these fire:
        eventAdd:
        eventChange:
        eventRemove:
        */
      },
      currentEvents: [],
    };
  },
  mounted() {
    // this.calendarOptions.events.push(...this.reservations)
    // this.getAvailabilities();
  },

  watch: {
    reservations: function (val, oldVal) {
      console.log(val);
      val.map((x) => {
        x.title = "Reservation for: " + x.client.username;
        return x;
      });
      this.calendarOptions.events = val;
    },
    mode: function (val, oldVal) {
      let calendarApi = this.$refs.fullCalendar.getApi();
      console.log(calendarApi);
      if (val == "view") {
        this.calendarOptions.selectable = false;
        this.calendarOptions.dateClick = this.handleDayClick;
      } else if(val == "add") {
        calendarApi.setOption("eventBackgroundColor", "#7bb888");
        calendarApi.setOption("eventBorderColor", "#7bb888");
        this.calendarOptions.selectable = true;
      } else {
        calendarApi.setOption("eventBackgroundColor", "#D25034");
        calendarApi.setOption("eventBorderColor", "#D25034");
        this.calendarOptions.selectable = true;
      }
    },
  },
  methods: {
    handleWeekendsToggle() {
      this.calendarOptions.weekends = !this.calendarOptions.weekends; // update a property
    },
    handleDateSelect(selectInfo) {
      console.log(selectInfo);
      let startMoment = moment(selectInfo.start);
      let endMoment = moment(selectInfo.end);
      this.year = startMoment.year;
      let days = ["SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"];
      this.selectedDays = [];
      let diff = moment.duration(endMoment.diff(startMoment));
      if (diff.days >= 7) {
        this.selectedDays = days;
      } else {
        let tempStartMoment = startMoment;
        while (tempStartMoment.isSameOrBefore(endMoment, 'day')) {
          this.selectedDays.push(days[tempStartMoment.day()]);
          tempStartMoment.add(1, "d");
        }
      }

      this.select_info = selectInfo;
      this.$refs.repeat_modal.show();
    },

    handleDayClick(clickInfo) {
      let calendarApi = this.$refs.fullCalendar.getApi();
      console.log(clickInfo);
      if(this.mode == "view") {
        calendarApi.gotoDate(clickInfo.date);
        calendarApi.changeView("timeGridDay");
      }
    },

    handleEventClick(clickInfo) {
      confirm(`Clicked event '${clickInfo.event.title}'`);
    },
    handleEvents(events) {
      this.currentEvents = events;
    },
    handleMonthChange(event) {
      this.getAvailabilities();
    },
    timePicked() {
      if (this.selected_repeat != 'norepeat') {
        this.$refs.month_modal.show();
      }
      else {
        this.$refs.repeat_modal.hide();
        this.createPatterns();
      }
    },
    timeModal() {
      let calendarApi = this.$refs.fullCalendar.getApi();
      console.log("time modal() ", calendarApi.view.type);
      if(calendarApi.view.type == "dayGridMonth") {
        this.$refs.time_modal.show()
      } else {
        this.startTime = moment(this.select_info.start).format("HH:MM:ss")
        this.endTime = moment(this.select_info.end).format("HH:MM:ss")
        this.timePicked();
      }
    },
    createPatterns() {
      let calendarApi = this.$refs.fullCalendar.getApi();
      if(calendarApi.view.type == "timeGridWeek") {
        this.continuous = true;
      }
      if (this.selected_repeat == "week") {
        console.log("select info ", this.select_info);
        this.create_repeat_week_patterns();
      } else if (this.selected_repeat == "norepeat") {
        this.create_no_repeat_patterns();
      }  else {
        this.create_date_patterns();
      }
      this.selectedMonths = [];
      this.selectedDays = [];
      this.$refs.month_modal.hide();
    },
    addMonthsAndDaysToPattern(patternStart, patternEnd) {
      console.log("here 1");
      for (let month of this.selectedMonths) {
        patternStart += month + ",";
        patternEnd += month + ",";
      }
      patternStart = patternStart.substring(0, patternStart.length - 1);
      patternStart += " ";
      patternEnd = patternEnd.substring(0, patternEnd.length - 1);
      patternEnd += " ";
      console.log("here 2");
      for (let day of this.selectedDays) {
        patternStart += day + ",";
        patternEnd += day + ",";
      }
      patternStart = patternStart.substring(0, patternStart.length - 1);
      patternEnd = patternEnd.substring(0, patternEnd.length - 1);
      return { start: patternStart, end: patternEnd };
    },
    sendPattern(patterns) {
      let to_send={
        patterns: patterns,
        selectedStartDate: moment(this.select_info.start).format("YYYY-MM-DD")+"T00:00:00",
        selectedEndDate: moment(this.select_info.end).format("YYYY-MM-DD")+"T00:00:00",
        repeat: this.selected_repeat,
        addition: this.mode=="add",
        rentableId: this.$route.params.adventure,
      };
      let that=this;
      console.log(to_send);
      this.$axios
        .post("/api/define-availability", JSON.stringify(to_send), {
          headers: {
            "Content-Type": "application/json",
          },
        })
        .then((response) => {
          console.log(response);
          that.getAvailabilities();
        });
    },
    create_repeat_week_patterns() {
      let patternStart = "0 ";
      let patternEnd = "0 ";
      let startHour = parseInt(this.startTime.split(":")[0]);
      let endHour = parseInt(this.endTime.split(":")[0]);
      let startMin = parseInt(this.startTime.split(":")[1]);
      let endMin = parseInt(this.endTime.split(":")[1]);
      patternStart += `${startMin} ${startHour} * `;
      patternEnd += `${endMin} ${endHour} * `;
      let ret = this.addMonthsAndDaysToPattern(patternStart, patternEnd);
      patternStart = ret.start;
      patternEnd = ret.end;
      this.sendPattern([[patternStart, patternEnd]]);
    },
    create_no_repeat_patterns() {
      let startDate = moment(this.select_info.start);
      let endDate = moment(this.select_info.end);
      let startHour = parseInt(this.startTime.split(":")[0]);
      let endHour = parseInt(this.endTime.split(":")[0]);
      let startMin = parseInt(this.startTime.split(":")[1]);
      let endMin = parseInt(this.endTime.split(":")[1]);
      let patterns = []
      this.selectedMonths = [this.months[startDate.month()]]
      let startDateCopy = startDate.clone();
      while(startDate.isSameOrBefore(endDate, 'day')) {
        let patternStart = "";
        let patternEnd = "";
        if(this.continuous) {
          if(startDateCopy.isSame(startDate)) {
            patternStart += `0 ${startMin} ${startHour} ` + startDate.date() + " ";
            patternEnd += `59 59 23 ` + startDate.date() + " ";
          } else if(this.isLastDateBeforeEnd(startDate, endDate)) {
            patternStart += `0 0 0 ` + startDate.date() + " ";
            patternEnd += `0 ${endMin} ${endHour} ` + startDate.date() + " ";
          } else {
            patternStart += `0 0 0 ` + startDate.date() + " ";
            patternEnd += `59 59 23 ` + startDate.date() + " ";
          }
        } else {
          patternStart += `${startMin} ${startHour} ` + startDate.date() + " ";
          patternEnd += `${endMin} ${endHour} ` + startDate.date() + " ";
        }
        let ret = this.addMonthsAndDaysToPattern(patternStart, patternEnd);
        patternStart = ret.start;
        patternEnd = ret.end;
        startDate.add(1, 'days');
        patterns.push([patternStart, patternEnd])
      }
      this.sendPattern(patterns);
    },
    isLastDateBeforeEnd(startDate, endDate){
      let s = startDate.clone();
      s.add(1, 'days');
      return startDate.isSame(endDate, 'day');
    },
    removeAvailabilities() {
      let that = this;
      this.$axios
        .post(
          `/api/remove-availabilities?id=${this.$route.params.adventure}` ,
          {
            headers: {
              "Content-Type": "application/json",
            },
          }
        ).then(that.getAvailabilities());
    },
    async getAvailabilities() {
      let calendarApi = this.$refs.fullCalendar.getApi();
      calendarApi.removeAllEvents();
      this.$axios
        .get(
          "/api/get-availabilities",
          {
            params: {
              from: moment(calendarApi.view.currentStart).format(
                "YYYY-MM-DDT00:00"
              ),
              to: moment(calendarApi.view.currentEnd).format(
                "YYYY-MM-DDT00:00"
              ),
              adventureId: this.$route.params.adventure,
            },
          },
          {
            headers: {
              "Content-Type": "application/json",
            },
          }
        )
        .then((response) => {
          for (let temp of response.data) {
            // console.log(temp);
            calendarApi.addEvent({
              id: temp.startDateTime + temp.endDateTime,
              title:
                temp.startDateTime.split("T")[1] +
                " - " +
                temp.endDateTime.split("T")[1],
              start: temp.startDateTime,
              end: temp.endDateTime,
              display: "block",
              color: "#7bb888",
            });
          }
        });
    },
  },
};
</script>

<style>

</style>

