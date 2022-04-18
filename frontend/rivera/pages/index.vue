<template>
  <div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/ekko-lightbox/5.3.0/ekko-lightbox.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.0/js/bootstrap.min.js"></script>
<Rentable />
  </div>
</template>

<script>
import MainCard from './../components/view-rentable/MainCard.vue';
import Discount from './../components/view-rentable/Discount.vue';
import Carousel from './../components/view-rentable/Carousel.vue';
import Rentable from './../components/view-rentable/Rentable.vue';
export default {
  name: 'IndexPage',
  components: {MainCard,Discount, Carousel, Rentable}
}
</script>
