<template>
    <div class="w-100">
        <!-- <div id="carousel-thumbs" class="carousel slide" data-ride="myCarousel" v-if="pictures.length > 0">
            <div class="carousel-inner w-100">
                <div v-for="index in (parseInt(pictures.length / 3) + 1)" :key="index" :class="index == 1 ? 'carousel-item w-100 active': 'carousel-item w-100'">
                    <div class="row mx-0">
                        <div v-if="((index - 1)*3 + 0) < pictures.length" :id="'carousel-selector-' + ((index - 1)*3 + 0)" class="thumb" data-target="#myCarousel" :data-slide-to="((index - 1)*3 + 0).toString()" style="width: 33.33%; object-fit:cover;">
                            <img :src="pictures[(index - 1)*3 + 0]" class="img-fluid" alt="..." />
                        </div>
                        <div v-if="((index - 1)*3 + 1) < pictures.length" :id="'carousel-selector-' + ((index - 1)*3 + 1)" class="thumb" data-target="#myCarousel" :data-slide-to="((index - 1)*3 + 1).toString()" style="width: 33.33%; object-fit:cover;">
                            <img :src="pictures[(index - 1)*3 + 1]" class="img-fluid" alt="..." />
                        </div>
                        <div v-if="((index - 1)*3 + 2) < pictures.length" :id="'carousel-selector-' + ((index - 1)*3 + 2)" class="thumb" data-target="#myCarousel" :data-slide-to="((index - 1)*3 + 2).toString()" style="width: 33.33%; object-fit:cover;">
                            <img :src="pictures[(index - 1)*3 + 2]" class="img-fluid" alt="..." />
                        </div>
                    </div>
                </div>
            </div>
            <a class="carousel-control-prev" href="#carousel-thumbs" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            </a>
            <a class="carousel-control-next w-25" href="#carousel-thumbs" role="button" data-slide="next">
                <span class="carousel-control-next-icon ml-0" aria-hidden="true"></span>
            </a>
            <p>something</p>
        </div> -->
            <VueSlickCarousel  v-if="images.length" v-bind="settings">
                <div v-for="picture in images" :key="picture">
                    <img :src="picture" class="img-fluid" alt="..." />
                </div>
            </VueSlickCarousel>
    </div>
<!-- 
                <div class="carousel-item active w-100">
                    <div class="row mx-0 w-100">
                        <div id="carousel-selector-0" class="thumb selected" data-target="#myCarousel" data-slide-to="0" style="width: 33.33%; object-fit:cover;">
                            <img src="https://source.unsplash.com/Pn6iimgM-wo/600x400/" class="img-fluid" alt="...">
                        </div>
                        <div id="carousel-selector-1" class="thumb" data-target="#myCarousel" data-slide-to="1" style="width: 33.33%; object-fit:cover;">
                            <img src="https://source.unsplash.com/tXqVe7oO-go/600x400/" class="img-fluid" alt="...">
                        </div>
                        <div id="carousel-selector-2" class="thumb" data-target="#myCarousel" data-slide-to="2" style="width: 33.33%; object-fit:cover;">
                            <img src="https://source.unsplash.com/qlYQb7B9vog/600x400/" class="img-fluid" alt="...">
                        </div>
                    </div>
                </div>
                <div class="carousel-item w-100">
                    <div class="row mx-0">
                        <div id="carousel-selector-3" class="thumb" data-target="#myCarousel" data-slide-to="3" style="width: 33.33%; object-fit:cover;">
                            <img src="https://source.unsplash.com/QfEfkWk1Uhk/600x400/" class="img-fluid" alt="...">
                        </div>
                        <div id="carousel-selector-4" class="thumb" data-target="#myCarousel" data-slide-to="4" style="width: 33.33%; object-fit:cover;">
                            <img src="https://source.unsplash.com/CSIcgaLiFO0/600x400/" class="img-fluid" alt="...">
                        </div>
                        <div id="carousel-selector-5" class="thumb" data-target="#myCarousel" data-slide-to="5" style="width: 33.33%; object-fit:cover;">
                            <img src="https://source.unsplash.com/a_xa7RUKzdc/600x400/" class="img-fluid" alt="...">
                        </div>
                    </div>
                </div>
                <div class="carousel-item">
                <div class="row mx-0">
                    <div id="carousel-selector-6" class="thumb" data-target="#myCarousel" data-slide-to="6" style="width: 33.33%; object-fit:cover;">
                    <img src="https://source.unsplash.com/uanoYn1AmPs/600x400/" class="img-fluid" alt="...">
                    </div>
                    <div id="carousel-selector-7" class="thumb" data-target="#myCarousel" data-slide-to="7" style="width: 33.33%; object-fit:cover;">
                    <img src="https://source.unsplash.com/_snqARKTgoc/600x400/" class="img-fluid" alt="...">
                    </div>
                    <div id="carousel-selector-8" class="thumb" data-target="#myCarousel" data-slide-to="8" style="width: 33.33%; object-fit:cover;">
                    <img src="https://source.unsplash.com/M9F8VR0jEPM/600x400/" class="img-fluid" alt="...">
                    </div>
                </div>
                </div> -->
        
</template>

<script>
import VueSlickCarousel from 'vue-slick-carousel'
import 'vue-slick-carousel/dist/vue-slick-carousel.css'
import 'vue-slick-carousel/dist/vue-slick-carousel-theme.css'
export default {
    props: ['pictures'],
    components: { VueSlickCarousel },
    watch: {
        pictures: function(newVal, oldVal) {
            console.log("Changed");
            console.log(newVal);
            this.images = newVal;
        }
    },
    data() {
        return {
           settings: {
                "dots": true,
                "focusOnSelect": true,
                "infinite": true,
                "speed": 500,
                "slidesToShow": 3,
                "slidesToScroll": 3,
                "touchThreshold": 5,
            },
            images: []
        }
    },
    mounted() {
        // $('#myCarousel').carousel({
        //     interval: false
        // });
        // $('#carousel-thumbs').carousel({
        //     interval: false
        // });

        // $('[id^=carousel-selector-]').click(function() {
        //     var id_selector = $(this).attr('id');
        //     var id = parseInt( id_selector.substr(id_selector.lastIndexOf('-') + 1) );
        // $('#myCarousel').carousel(id);
        // });
        
        // if ($(window).width() < 575) {
        // $('#carousel-thumbs .row div:nth-child(4)').each(function() {
        //     var rowBoundary = $(this);
        //     $('<div class="row mx-0">').insertAfter(rowBoundary.parent()).append(rowBoundary.nextAll().addBack());
        // });
        // $('#carousel-thumbs .carousel-item .row:nth-child(even)').each(function() {
        //     var boundary = $(this);
        //     $('<div class="carousel-item">').insertAfter(boundary.parent()).append(boundary.nextAll().addBack());
        // });
        // }

        // if ($('#carousel-thumbs .carousel-item').length < 2) {
        //     $('#carousel-thumbs [class^=carousel-control-]').remove();
        //     $('.machine-carousel-container #carousel-thumbs').css('padding','0 5px');
        // }
        // // when the carousel slides, auto update
        // $('#myCarousel').on('slide.bs.carousel', function(e) {
        //     var id = parseInt( $(e.relatedTarget).attr('data-slide-number') );
        //     $('[id^=carousel-selector-]').removeClass('selected');
        //     $('[id=carousel-selector-'+id+']').addClass('selected');
        // });

        // $('#myCarousel .carousel-item img').on('click', function(e) {
        //     var src = $(e.target).attr('data-remote');
        //     if (src) $(this).ekkoLightbox();
        // });
    }

}
</script>