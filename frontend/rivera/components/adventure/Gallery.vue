<template>
<section class="gallery">
  <div class="gallery__item">
    <input type="radio" id="img-1" checked name="gallery" class="gallery__selector"/>
    <img class="gallery__img" :src="pictures[0]" alt=""/>
    <label for="img-1" class="gallery__thumb"><img :src="pictures[0]" style="max-height: 100px; max-width: 150px; object-fit: cover;"/></label>
  </div>
  <div class="gallery__item">
    <input type="radio" id="img-2" name="gallery" class="gallery__selector"/>
    <img class="gallery__img" :src="pictures[1]" alt=""/>
    <label for="img-2" class="gallery__thumb"><img :src="pictures[1]" style="max-height: 100px; max-width: 150px; object-fit: cover;"/></label>
  </div>
  <div class="gallery__item">
    <input type="radio" id="img-3" name="gallery" class="gallery__selector"/>
    <img class="gallery__img" :src="pictures[2]" alt=""/>
    <label for="img-3" class="gallery__thumb"><img :src="pictures[2]" style="max-height: 100px; max-width: 150px; object-fit: cover;"/></label>
  </div>
  <div class="gallery__item">
    <label style="margin-top: 40px; margin-left: 10px; font-weight: 500; cursor: pointer;" @click="openGalerry"><font-awesome-icon icon="image" /> View all</label>  
  </div>
</section>
</template>

<script>
export default {
    props: ["pictures", "openGalleryModal"],
    methods: {
        openGalerry() {
            this.openGalleryModal();
        },
    },
}
</script>

<style>
.gallery {
  display: flex;
  margin: 5px auto;
  max-width: 600px;
  position: relative;
}
@media screen and (min-width: 600px) {
  .gallery {
    padding-top: 400px;
  }
}
.gallery__img {
  position: absolute;
  max-height: 400px;
  object-fit: cover;
  top: 0;
  left: 0;
  opacity: 0;
  transition: opacity 0.3s ease-in-out;
}
.gallery__thumb {
  padding-top: 6px;
  margin: 6px;
  display: block;
}
.gallery__selector {
  position: absolute;
  opacity: 0;
  visibility: hidden;
}
.gallery__selector:checked + .gallery__img {
  opacity: 1;
}
.gallery__selector:checked ~ .gallery__thumb > img {
  box-shadow: 0 0 0 3px gray;
}
</style>