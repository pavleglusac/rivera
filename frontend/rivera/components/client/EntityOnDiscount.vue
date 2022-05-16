<template>
<b-col>
  <div class="mt-2">
    <b-card
      img-top
      tag="article"
      style="max-width: 30rem;"
      img-height="200"
      class="mb-2"
      :img-src='"http://localhost:8080" + entity.picture'
      :title="entity.name"
    >
      <b-card-body style="padding: 0; align-text: center">
        <div class="text-secondary" style="font-size: 1em">
          <font-awesome-icon icon="star" />&nbsp;{{ entity.averageScore }}&nbsp;
          <font-awesome-icon icon="person" /> &nbsp;{{ entity.capacity }}
        </div>
        <div class="text-secondary" style="font-size: 1em">
          <font-awesome-icon icon="location-dot" /> &nbsp;{{ entity.city }},
          {{ entity.country }}
        </div>
        <div class="text-secondary" style="font-size: 1em">
          <font-awesome-icon icon="calendar" />&nbsp; Start: {{ new Date(entity.start).toDateString() }}
        </div>
        <div class="text-secondary" style="font-size: 1em">
          &nbsp;&nbsp;&nbsp;&nbsp; End: {{ new Date(entity.end).toDateString() }}
        </div>
        <b-button href="#" class="prime-btn mt-2" variant="primary"
          >Book now for only {{ entity.price }} $</b-button
        >
      </b-card-body>
    </b-card>
  </div>
</b-col>
</template>

<script>
export default {
  name: "EntityOnDiscount",
  components: {},
  data() {
    return { entity: this.entity, };
  },
  props: {
    entity: {
      type: Object,
      required: true,
    },
  },
};
</script>

<style>
.prime-btn {
  background-color: var(--prime-color);
  border: none;
}
.prime-btn:hover {
  background-color: #39aea9;
  border: none;
}
</style>