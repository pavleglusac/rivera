<template>
  <div>
    <CottageOwnerNavbar :currentPage="'profile'" />
    <p>My profile</p>
  </div>
</template>

<script>
import CottageOwnerNavbar from '../../components/navbars/CottageOwnerNavbar.vue';

export default {
  components: { CottageOwnerNavbar },
}

</script>