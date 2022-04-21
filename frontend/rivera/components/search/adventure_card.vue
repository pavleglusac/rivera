<template>
  <b-card no-body class="shadow-sm" img-src="https://placekitten.com/200/200" img-left>
    <b-card-body class="d-flex flex-column h-100">
        <div class="d-flex justify-content-between align-items-center">
            <h4 class="card-title">{{adventure.name}}</h4>
        </div>
        <div class="d-flex flex-wrap align-items-center mb-2">
            <span class="text-muted">{{adventure.address}}</span>
            <span class="mx-2">&#8226;</span>
            <span>{{adventure.city}}, {{adventure.country}}</span>
            <span class="mx-2">&#8226;</span>
            <b-button class="p-0" variant="link" v-b-modal.map-modal>Show On Map</b-button>
        </div>
        
        <hr class="w-100">
        
        <b-card-text>{{adventure.description}}</b-card-text>
        <li v-for="tag in adventure.tags">
            <span class="mx-2">{{ tag }}&#8226;</span>
        </li>

        <hr class="w-100">
            
        <div class="d-flex justify-content-between align-items-center">
            <span class="font-1h">{{adventure.perHour}}$ per hour / {{adventure.perDay}}$ per day</span>
            <b-button variant="primary">Book Now</b-button>
        </div>
    </b-card-body>
  </b-card>
</template>

<script>
export default {
  name: "AdventureCard",
  components: {},
  data() {
    return {};
  },
  props: {
    adventure: {
      type: Object,
      required: true
    },
  },
};
</script>
