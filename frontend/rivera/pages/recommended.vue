<template>
<div>
    <Navbar :currentPage="'recommended'"/>
    <entities-on-discount/>
</div>  
</template>

<script>
import Navbar from '../components/navbar.vue';
import EntitiesOnDiscount from '../components/client/EntitiesOnDiscount.vue';
export default {
    components: { Navbar, EntitiesOnDiscount },
    name: "ClientTrending"
}
</script>

<style>

</style>