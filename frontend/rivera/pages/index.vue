<template>
  <div>
  
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/ekko-lightbox/5.3.0/ekko-lightbox.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.0/js/bootstrap.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>


    <script src="https://unpkg.com/vue-cal"></script>
    <link href="https://unpkg.com/vue-cal/dist/vuecal.css" rel="stylesheet">

    <Navbar />
    <!-- <Rentable /> -->
    <AddAdventure />
  </div>
</template>

<script>
import MainCard from './../components/rentable/MainCard.vue';
import Rentable from './../components/rentable/Rentable.vue';
import AddAdventure from './../components/add-rentable/AddAdventure.vue';

import Vue from 'vue';


export default {
  components: { MainCard, Rentable, AddAdventure },
  name: 'IndexPage',
  mounted() {
    $(function () {
      $('[data-toggle="popover"]').popover()
    });
  }
}
</script>
