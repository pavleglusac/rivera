<template>
  <b-card no-body class="shadow-sm" img-left max-height="300" style="cursor: pointer;">
    <b-card-img class="cover-img" :src='"http://localhost:8080" + entity.pictures[0]' /> 
    <b-card-body class="d-flex flex-column h-100">
        <div class="d-flex justify-content-between align-items-center">
            <h4 class="card-title">{{entity.name}}</h4>
        </div>
        <div class="d-flex flex-wrap align-items-center mb-2">
            <span class="text-muted">{{entity.address}}</span>
            <span class="mx-2">&#8226;</span>
            <span>{{entity.city}}, {{entity.country}}</span>
            <span class="mx-2">&#8226;</span>
            <div class="text-secondary" style="font-size: 0.8em;">
                <font-awesome-icon icon="star" /> &nbsp;{{ entity.averageScore }}
            </div>
        </div>
        
        <hr class="w-100">
        
        <b-card-text @click="goToClient()" style="font-size: 1em;" class="m-0">
            <h6 class="card-title">Reservee: </h6>
        <b-card-text  style="font-size: 1em;" >
            <b-avatar :src='"http://localhost:8080" + reservation.client.photo' size="6rem"></b-avatar>
                {{reservation.client.name}} {{reservation.client.surname}}
            </b-card-text>
        </b-card-text>
        <div v-if="!reservation.cancelled" class="mt-3">
          <div class="d-flex justify-content-between align-items-center">
              <span class="font-1h" style="font-size: 1.3em;"><b>Cancelled</b></span>
          </div>
        </div>
        <hr class="w-100">
            
        <div class="d-flex justify-content-between align-items-center">
            <span class="font-1h">Start date: {{formatDate(new Date(reservation.start))}}<br> End date: {{formatDate(new Date(reservation.end))}}</span>
        </div>
    </b-card-body>
  </b-card>
</template>

<script>
export default {
  name: "EntityCard",
  components: {},
  data() {
    return {};
  },
  methods: {
    goToClient(){
        this.$router.push({ path: "/client/" + this.reservation.client.username });
    },
    formatDate(date) {
      var hours = date.getHours();
      var minutes = date.getMinutes();
      var ampm = hours >= 12 ? 'pm' : 'am';
      hours = hours % 12;
      hours = hours ? hours : 12; // the hour '0' should be '12'
      minutes = minutes < 10 ? '0'+minutes : minutes;
      var strTime = hours + ':' + minutes + ' ' + ampm;
      return (date.getMonth()+1) + "/" + date.getDate() + "/" + date.getFullYear() + "  " + strTime;
}

  },
  props: {
    entity: {
      type: Object,
      required: true,
    },
    reservation: {
      type: Object,
      required: true,
    },
    offerType: {
      type: String,
      required: true,
    }
  },
};
</script>

<style scoped>
li {
  display: inline-block;
  list-style-type: none;
}

.tag {
  background-color: var(--light-blue-color);
  border: none;
  border-radius: 5px;
  padding: 5px;
  margin: 3px;
  text-align: center;
  color: #fff;
  display: inline;
  font-size: 12px;
}

.book-btn {
  background-color: var(--prime-color);
  border: none;
}
.book-btn:hover {
  background-color: #39AEA9;
}

.cover-img {
  width: 300px;
  height: 300px;
  object-fit: cover;
}
</style>
