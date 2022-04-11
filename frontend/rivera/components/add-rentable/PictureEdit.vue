<template>
<div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>  
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
    
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
.ui-sortable-placeholder { 
    border: 1px dashed black!important; 
    visibility: visible !important;
    background: #eeeeee78 !important;
}
.ui-sortable-placeholder * { visibility: hidden; }
.RearangeBox.dragElemThumbnail{opacity:0.6;}
.RearangeBox {
    width: 180px;
    height:240px;
    cursor: all-scroll;
    float: left;
    border: 1px solid #9E9E9E;
    font-family: sans-serif;
    display: inline-block;            
    margin: 5px!important;
    text-align: center;
    color: black;
    background: #9c9a9a;
}

.IMGthumbnail{
    max-width:220px;
    height:220px;
    margin:auto;
    background-color: #ececec;
    border:none;
}

.IMGthumbnail img{
    max-width: 100%;
    max-height: 100%;
    width: 100%;
    height: 100%;
}

.imgThumbContainer{
  margin:4px;
  border: solid;
  display: inline-block;
  justify-content: center;
    position: relative;
    border: 1px solid rgba(0,0,0,0.14);
  -webkit-box-shadow: 0 0 4px 0 rgba(0,0,0,0.2);
    box-shadow: 0 0 4px 0 rgba(0,0,0,.2);
}

.imgThumbContainer > .imgName{
  text-align:center;
  font-size: 12px;
  height: 15px;
  overflow: hidden;
}

.imgThumbContainer > .imgRemoveBtn{
    position: absolute;
    color: red;
    right: 2px;
    top: 2px;
    cursor: pointer;
    display: none;
}

.RearangeBox:hover > .imgRemoveBtn{ 
    display: block;
}
</style>