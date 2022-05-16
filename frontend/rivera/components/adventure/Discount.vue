<template>
  <b-card class="text-center discount-card">
    <b-card-title center>{{ discount.price }}$</b-card-title>
    <b-card-text>
      <div class="d-flex flex-column justify-content-center">
        <div class="d-flex justify-content-center">
          {{ discount.start }}
        </div>
        <div class="d-flex justify-content-center">
          <font-awesome-icon icon="arrow-down" />
        </div>
        <div class="d-flex justify-content-center">
          {{ discount.end }}
        </div>
      </div>
    </b-card-text>
    <b-button href="#" class="prime-btn mt-2" variant="primary">Book now</b-button>
  </b-card>
</template>

<script>
export default {
  props: ["discount"],
  mounted() {
  },
  data() {
    return {};
  },
};
</script>

<style>
.discount-card {
  box-shadow: 2px 2px 30px rgba(0, 0, 0, 0.1);
  padding: 10px;
}
</style>