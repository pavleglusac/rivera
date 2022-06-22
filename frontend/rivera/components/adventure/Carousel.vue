<template>
    <div class="w-100">
            <VueSlickCarousel  v-if="images.length" v-bind="settings">
                <div v-for="picture in images" :key="picture">
                    <img :src="picture" style="height: 100px; width: 300px" alt="..." />
                </div>
            </VueSlickCarousel>
    </div>
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
                "infinite": false,
                "speed": 500,
                "slidesToShow": 3,
                "slidesToScroll": 3,
                "touchThreshold": 5,
            },
            images: []
        }
    },
    mounted() {
    }

}
</script>