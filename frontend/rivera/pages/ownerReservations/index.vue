<template>
  <div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/ekko-lightbox/5.3.0/ekko-lightbox.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.0/js/bootstrap.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
    <Navbar :currentPage="'reservations'" />
    <All_reservations/>
  </div>
</template>

<script>
import Navbar from '../../components/navbar.vue';
import All_reservations from '../../components/owner_reservations/all_reservations.vue';
export default {
  name: 'ReservationHistoryPage',
  components: { Navbar, All_reservations }
}
</script>
