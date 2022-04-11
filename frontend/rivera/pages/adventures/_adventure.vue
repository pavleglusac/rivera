<template>
    <div>
      Adventure {{this.$route.params.adventure}}
    </div>
</template>

<script>
export default {
    mounted() {
        console.log(this.$route.params.adventure);
    }
}
</script>

<style>

</style>