<template>
  <div>
    <CottageOwnerNavbar :currentPage="'cottages'" />
    <p>List of my cottages</p>
  </div>
</template>

<script>
import CottageOwnerNavbar from '../../components/navbars/CottageOwnerNavbar.vue';

export default {
  components: { CottageOwnerNavbar },
}

</script>