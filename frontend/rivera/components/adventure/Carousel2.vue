<template>
  <div class="w-100">
    <b-carousel
      id="carousel-1"
      v-model="slide"
      :interval="4000"
      controls
      indicators
      background="#ababab"
      style="max-height: 600px;"
      @sliding-start="onSlideStart"
      @sliding-end="onSlideEnd"
    >
      <b-carousel-slide v-for="pic in pictures" style="max-height: 600px; object-fit: scale-down;" :img-src="pic"></b-carousel-slide>
    </b-carousel>
  </div>
</template>

<script>
export default {
  props: ["pictures"],
  components: {},
  data() {
    return {
      slide: 0,
      sliding: null,
    };
  },
  mounted() {},
  methods: {
    onSlideStart(slide) {
      this.sliding = true;
    },
    onSlideEnd(slide) {
      this.sliding = false;
    },
  },
};
</script>