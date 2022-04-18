<template>
    <div>
      <edit-adventure />
    </div>
</template>

<script>
import EditAdventure from './../../components/add-rentable/EditAdventure.vue'
export default {
    components: { EditAdventure }, 
    mounted() {
        console.log(this.$route.params.adventure);
    }
}
</script>

<style>

</style>