<template>
    <div class='RearangeBox imgThumbContainer' id="+index+">
        <i class='material-icons imgRemoveBtn' @click='remove()'>REMOVE</i>
        <div class='IMGthumbnail'>
            <img :src="'http://localhost:8080' + path" />
        </div>
    <div class='imgName'> {{ trimImageName(path) }} </div></div>
</template>

<script>
export default {
    props: ['path'],
    methods: {
        remove() {
            console.log('REMOVED');
            this.$emit('removedThumbnail', this.path);
        },
        trimImageName(name) {
            console.log(name);
            var n = name.lastIndexOf('\\');
            var result = name.substring(n + 1);
            return result;
        },
    }
}
</script>

<style>

</style>