<template>
  <div class="mt-2">
    <b-card
      img-top
      tag="article"
      style="max-width: 30rem"
      class="mb-2"
      img-src="../../assets/cottage.jpg"
      title="Cute cottage"
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
          <font-awesome-icon icon="calendar" />&nbsp;{{ entity.start }} -
          {{ entity.end }}
        </div>
        <b-button href="#" class="prime-btn mt-2" variant="primary"
          >Book now for only {{ entity.price }} $</b-button
        >
      </b-card-body>
    </b-card>
  </div>
</template>

<script>
export default {
  name: "EntityOnDiscount",
  data() {
    return {
      entity: {
        start: "11.05.2022.",
        end: "19.05.2022.",
        capacity: "5",
        averageScore: "5.0",
        price: "200",
        city: "Novi Sad",
        country: "Serbia",
        picture: "",
        name: "Cute little cottage",
      },
    };
  },
  props: {},
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