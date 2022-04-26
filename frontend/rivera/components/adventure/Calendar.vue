<template>
<div class="w-100 h-100">
  <b-modal id="repeat_modal" title="Availability creation" ref="repeat_modal" size="xl" hide-footer no-stacking>
    <h2>Repeat:</h2>
    <b-form-group label="Button style radios">
      <b-form-radio v-model="selected_repeat" value="norepeat" button button-variant="light" size="lg">
        No repeat
      </b-form-radio>
      <b-form-radio v-model="selected_repeat" value="week" button button-variant="light" size="lg">
        Every week
      </b-form-radio>
      <b-form-radio v-model="selected_repeat"  value="otherweek" button button-variant="light" size="lg">
        Every other week
      </b-form-radio>
    </b-form-group>
    <b-button v-b-modal.time_modal class="float-right">Next</b-button>
  </b-modal>
  <!-- TIME picker -->
  <b-modal id="time_modal" title="Availability creation" ref="time_modal" size="xl" hide-footer no-stacking>
    <h2>Select time</h2>
    <label for="startTime">Select start time:</label>
    <input type="time" id="startTime" name="startTime" v-model="startTime">
    <label for="endTime">Select end time:</label>
    <input type="time" id="endTime" name="endTime" v-model="endTime">
    <b-form-checkbox
          id="checkbox-1"
          v-model="status"
          name="checkbox-1"
          value="accepted"
          unchecked-value="not_accepted"
        >
      All day
    </b-form-checkbox>
    <b-button v-b-modal.month_modal class="float-right">Next</b-button>
  </b-modal>
<!-- Month picker -->
  <b-modal id="month_modal" title="Availability creation" ref="month_modal" size="xl" hide-footer no-stacking>
    <div>
      <h2 class="mb-2">Apply for months:</h2>
    </div>
    <span class="mb-5 mt-2">
      <b-button variant="light" @click="selectedMonths.push(...months)">Select all</b-button>
      <b-button variant="light" @click="selectedMonths = []">Deselect all</b-button>
    </span>
    <b-form-group class="mt-3 ">
      <div class="d-flex justify-content-center">
        <b-form-checkbox v-for="month in months" :key="month" 
        v-model="selectedMonths" :value="month.toUpperCase()" button button-variant="light" size="lg"
        class="mr-1">
          {{month}}
        </b-form-checkbox>
      </div>
    </b-form-group>
    <b-button class="float-right" @click="createPatterns">Next</b-button>
  </b-modal>



  <!-- Mode -->
    <div class="d-flex w-100">
      <b-form-group label="" class="w-100">
        <b-form-radio v-model="mode" value="view" button button-variant="light" size="lg">
          <font-awesome-icon icon="eye" />
        </b-form-radio>
        <b-form-radio v-model="mode" value="add" button button-variant="light" size="lg">
          <font-awesome-icon icon="plus" />
        </b-form-radio>
        <b-form-radio v-model="mode"  value="subtract" button button-variant="light" size="lg">
          <font-awesome-icon icon="minus" />
        </b-form-radio>
        <b-button variant="light" size="lg" class="float-right ml-5 mr-0">
          <font-awesome-icon icon="trash-can" />
        </b-button>
      </b-form-group>
    </div>
  
  <FullCalendar ref="fullCalendar" :options="calendarOptions" />

</div>
</template>

<script>
//import '@fullcalendar/core/vdom' // solves problem with Vite
import FullCalendar from '@fullcalendar/vue'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import timeGridPlugin from '@fullcalendar/timegrid'

export default {
  props: ['reservations'],
  components: {
    FullCalendar // make the <FullCalendar> tag available
  },
  data() {
    return {
      createAvailabilityModal: true,
      options: [
        { text: 'No repeat', value: 'norepeat' },
        { text: 'Every week', value: 'everyweek' },
        { text: 'Every other week', value: 'everyotherweek' },
      ],
      selected_repeat: "norepeat",
      months: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Avg", "Sep", "Oct", "Nov", "Dec"],
      status: "",
      mode: "view",
      startTime: "09:00",
      endTime: "15:00",
      selectedMonths: [],
      days: [],
      year: 2022,
      calendarOptions: {
        plugins: [ dayGridPlugin, interactionPlugin, timeGridPlugin ],
        headerToolbar: {
          left: 'prev,next today',
          center: 'title',
          right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        height: "100%",
        initialView: 'dayGridMonth',
        events: [
            {
                title: 'BCH237',
                start: '2022-04-10T10:30:00',
                end: '2022-04-10T11:30:00',
                extendedProps: {
                    department: 'BioChemistry'
                },
                description: 'Lecture',
                display: 'background'
            }
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
        firstDay: 1,
        /* you can update a remote database when these fire:
        eventAdd:
        eventChange:
        eventRemove:
        */
      },
      currentEvents: [],
    }
  },
  mounted() {
    // this.calendarOptions.events.push(...this.reservations)
  },

  watch: {
    reservations: function(val, oldVal) {
      console.log(val);
      val.map((x) => { x.title="Reservation for: "+x.client.username; return x; })
      this.calendarOptions.events = val
    },
    mode: function(val, oldVal) {
      if(val == "view") {
        this.calendarOptions.selectable = false;
        this.calendarOptions.dateClick = this.handleDayClick;
      } else {
        this.calendarOptions.selectable = true;
        this.calendarOptions.dateClick = this.handleDayClick;
      }
    }
  },
  methods: {
    handleWeekendsToggle() {
      this.calendarOptions.weekends = !this.calendarOptions.weekends // update a property
    },
    handleDateSelect(selectInfo) {
      let startMoment = moment(selectInfo.start);
      let endMoment = moment(selectInfo.end);
      this.year = startMoment.year;
      let days = ['MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT', 'SUN'];

      let diff = moment.duration(endMoment.diff(startMoment));
      if(diff.days >= 7) {
        this.days = days;
      } else {
        let tempStartMoment = startMoment;
        while(tempStartMoment.isBefore(endMoment)) {
          this.days.push(days[tempStartMoment.day() - 1]);
          tempStartMoment.add(1, 'd');
        }
      }

      console.log(this.days);
      
      this.$refs.repeat_modal.show();

      // let calendarApi = selectInfo.view.calendar

      // calendarApi.unselect() // clear date selection

      // if (title) {
      //   calendarApi.addEvent({
      //     id: 1,
      //     title,
      //     start: selectInfo.startStr,
      //     end: selectInfo.endStr,
      //     allDay: selectInfo.allDay,
      //     display: 'background'
      //   })
      // }
    },

    handleDayClick(clickInfo) {
      let calendarApi = this.$refs.fullCalendar.getApi();
      console.log(clickInfo);
      calendarApi.gotoDate(clickInfo.date);
      calendarApi.changeView('timeGridDay');
    },

    handleEventClick(clickInfo) {
      confirm(`Clicked event '${clickInfo.event.title}'`);
    },
    handleEvents(events) {
      this.currentEvents = events
    },
    createPatterns() {

      if(this.selected_repeat == "week") {
        let pattern = "0 ";
        let startHour = parseInt(this.startTime.split(":")[0]);
        let endHour = parseInt(this.endTime.split(":")[0]);
        let startMin = parseInt(this.startTime.split(":")[1]);
        let endMin = parseInt(this.endTime.split(":")[1]);
        pattern += `${startMin},${endMin} ${startHour},${endHour} `
  
        for(let month of this.selectedMonths) {
          pattern += month + ",";
        }
        pattern = pattern.substring(0, pattern.length - 1);
        pattern += " ";
        for(let day of this.days) {
          pattern += day + ",";
        }
        pattern = pattern.substring(0, pattern.length - 1);
        console.log(pattern);

        let to_send = {
          pattern: [pattern],
          startDate: moment(selectInfo.start),
          endDate: moment(selectInfo.start),
          repeat: this.selected_repeat
        }
      }


    }
  }
}
</script>