<template>
<div class="w-100 h-100">
  <FullCalendar :options="calendarOptions" />
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
                description: 'Lecture'
            }
        ],
        editable: true,
        selectable: true,
        selectMirror: true,
        dayMaxEvents: true,
        weekends: true,
        select: this.handleDateSelect,
        eventClick: this.handleEventClick,
        eventsSet: this.handleEvents
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
    console.log("HEEJJ");
    console.log(this.calendarOptions.events);
    console.log(this.reservations);

    // this.calendarOptions.events.push(...this.reservations)
  },
  watch: {
    reservations: function(val, oldVal) {
      console.log(val);
      val.map((x) => { x.title="Reservation for: "+x.client.username; return x; })
      this.calendarOptions.events = val
    }
  },
  methods: {
    handleWeekendsToggle() {
      this.calendarOptions.weekends = !this.calendarOptions.weekends // update a property
    },
    handleDateSelect(selectInfo) {
      let title = prompt('Please enter a new title for your event')
      let calendarApi = selectInfo.view.calendar

      calendarApi.unselect() // clear date selection

      if (title) {
        calendarApi.addEvent({
          id: createEventId(),
          title,
          start: selectInfo.startStr,
          end: selectInfo.endStr,
          allDay: selectInfo.allDay
        })
      }
    },
    handleEventClick(clickInfo) {
      confirm(`Clicked event '${clickInfo.event.title}'`);
    },
    handleEvents(events) {
      this.currentEvents = events
    },
  }
}
</script>