<template>
<div>
    <div style='margin:auto;'>
        <div id="sortableImgThumbnailPreviewEdit">
            <Thumbnail v-for="image in images" :key="image" :path='image' @removedThumbnail="removeThumbnail" />
        </div>
    </div>
</div>

</template>

<script>
import Thumbnail from './Thumbnail.vue'
export default {
    props: ['pictures'],
    components: {Thumbnail},
    data() {
        return {
            images: []
        }
    },
    methods: {
        loadImages() {
            var output = document.getElementById("sortableImgThumbnailPreviewEdit");
        },
        getImages() {
            return this.images;
        },
        trimImageName(name) {
            var n = name.lastIndexOf('\\');
            var result = name.substring(n + 1);
            return result;
        },
        removeIndex(index) {
            
        },

        removeThumbnail(path) {
            var index = this.images.indexOf(path);
            if (index !== -1) {
                this.images.splice(index, 1);
            }
            this.$emit('picturesChange', this.images);
        }
        
    },
    mounted() {
        this.images = this.pictures;
        $("#sortableImgThumbnailPreviewEdit").sortable({
            connectWith: ".RearangeBox",        
            start: function( event, ui ) { 
                $(ui.item).addClass("dragElemThumbnail");
                ui.placeholder.height(ui.item.height());
        
            },
            stop:function( event, ui ) { 
                $(ui.item).removeClass("dragElemThumbnail");
            }
        });
        $("#sortableImgThumbnailPreviewEdit").disableSelection();

        this.loadImages();

    },
    watch: {
        pictures() {
            this.loadImages();
        },
        images() {
            console.log(this.images);
        }
    }
}
</script>

<style>

</style>