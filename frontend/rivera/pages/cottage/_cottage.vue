<template>
    <div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity=""></script>  
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

  <profileCottage/>
    </div>
</template>

<script>
import editCottage from './../../components/add_rentable/editCottage.vue'
import profileCottage from './../../components/add_rentable/profileCottage.vue'
export default {
    components: {profileCottage },
    mounted() {
        console.log(this.$route.params.cottage);
    }
}
</script>

<style>
</style>