<template>
<div>
    <div>
        <label for="files">Select multiple files: </label>
        <input id="files" type="file" multiple/>        
    </div>
  <div style='margin:auto;'>
    <div id="sortableImgThumbnailPreview">

    </div>
  </div>
</div>

</template>

<script>
export default {
    methods: {
    },
    mounted() {
        $("#sortableImgThumbnailPreview").sortable({
            connectWith: ".RearangeBox",        
            start: function( event, ui ) { 
                $(ui.item).addClass("dragElemThumbnail");
                ui.placeholder.height(ui.item.height());
        
            },
            stop:function( event, ui ) { 
                $(ui.item).removeClass("dragElemThumbnail");
            }
        });
        $("#sortableImgThumbnailPreview").disableSelection();


        document.getElementById('files').addEventListener('change', handleFileSelect, false);

        function handleFileSelect(evt) {
            var files = evt.target.files; 
            var output = document.getElementById("sortableImgThumbnailPreview");
            
            for (var i = 0, f; f = files[i]; i++) {
            if (!f.type.match('image.*')) {
                continue;
            }

            var reader = new FileReader();
            reader.onload = (function(theFile) {
                return function(e) {
                // Render thumbnail.
                var imgThumbnailElem = "<div class='RearangeBox imgThumbContainer'>\
                <i class='material-icons imgRemoveBtn' onclick='this.parentNode.outerHTML=``;'>REMOVE</i>\
                <div class='IMGthumbnail' ><img  src='" + e.target.result + "'" + "title='"+ theFile.name + "'/></div>\
                <div class='imgName'>"+ theFile.name +"</div></div>";
                            
                    output.innerHTML = output.innerHTML + imgThumbnailElem; 
                
                };
            })(f);

            reader.readAsDataURL(f);
        }
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