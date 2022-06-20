<template>
  <b-card no-body class="shadow-sm discount-card" img-left max-height="300" style="cursor: pointer;" @click="detailedOffer()">
    <b-card-img class="cover-img" id="cvr-img" :src='entity.pictures[0]' /> 
    <b-card-body class="d-flex flex-column h-100">
      <div class="d-flex justify-content-between align-items-center">
        <h3 class="card-title">{{ entity.name }}</h3>
      </div>
      <div class="d-flex flex-wrap align-items-center mb-1">
        <span class="text-muted">{{ entity.address }}</span>
        <span class="mx-2">&#8226;</span>
        <span>{{ entity.city }}, {{ entity.country }}</span>
        <span class="mx-2">&#8226;</span>
        <div class="text-secondary" style="font-size: 0.8em">
          <font-awesome-icon icon="star" /> &nbsp;{{ entity.averageScore }}
        </div>
      </div>

      <hr class="w-100" />

      <b-card-text
        v-if="entity.description.length < 200"
        style="font-size: 0.9em"
        >{{ entity.description }}</b-card-text
      >
      <b-card-text v-else style="font-size: 0.8em"
        >{{ entity.description.substring(0, 200) }}...</b-card-text
      >
      <div>
        <li v-for="tag in entity.tags" :key="tag + entity.id">
          <span class="tag">{{ tag }}</span>
        </li>
      </div>

      <hr class="w-100" />

      <div class="d-flex justify-content-between align-items-center">
        <span class="font-1h"
          ><b style="font-size: 20px">{{ entity.perHour }}$</b> per hour /
          <b style="font-size: 20px">{{ entity.perDay }}$</b> per day</span
        >
        <b-button variant="primary" v-if="myRentable" class="book-btn"
          >Edit rentable</b-button
        >
        <b-button variant="primary" v-else-if="!adminView" class="book-btn"
          >Book Now</b-button
        >
        <b-button
          variant="primary"
          v-else
          class="book-btn"
          @click="deleteRentable"
          >Delete rentable</b-button
        >
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
  mounted() {
    this.entity.pictures[0] =  (process.env.backend) + "" + this.entity.pictures[0];
  },
  methods: {
    detailedOffer() {
      this.$router.push({ path: "/rentable/" + this.entity.id });
    },
    deleteRentable() {
      if (confirm("Are you sure you want to delete this rentable?")) {
        this.trulyDeleteRentable();
      } else {
        console.log("Not deleted.");
      }
    },
    trulyDeleteRentable() {
      this.$axios
        .delete(`/api/rentable?id=` + this.entity.id)
        .then((response) => {
          window.location.reload();
        });
    },
  },
  props: {
    entity: {
      type: Object,
      required: true,
    },
    offerType: {
      type: String,
      required: true,
    },
    myRentable: {
      type: Boolean,
      required: false,
    },
    adminView: false,
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
  font-weight: 500;
  display: inline;
  font-size: 12px;
}

.book-btn {
  background-color: var(--prime-color);
  border: none;
}
.book-btn:hover {
  background-color: #39aea9;
}

.cover-img {
  width: 300px;
  height: 300px;
  object-fit: cover;
}
</style>
