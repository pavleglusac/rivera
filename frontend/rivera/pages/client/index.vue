<template>
<div>
    <ClientNavbar :currentPage="'trending'"/>
    <entities-on-discount/>
</div>  
</template>

<script>
import ClientNavbar from '../../components/navbars/ClientNavbar.vue';
import EntitiesOnDiscount from '../../components/client/EntitiesOnDiscount.vue';
export default {
    components: { ClientNavbar, EntitiesOnDiscount },
    name: "ClientTrending"
}
</script>

<style>

</style>