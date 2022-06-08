<template>
    <div>
        <Review v-for="review in reviews" :key="review" :review="review" class="w-100 pl-4 pr-4 pb-1" />
    </div>
</template>

<script>
import Review from '../view-rentable/Review.vue'
export default {
    components: {Review},
    props: [],
    mounted(){
        this.id = this.$route.params.profile;
        this.loadReviews();
    },
    data(){
        return{
            id:'',
            reviews: [],
        }
    },
    methods: {
        loadReviews() {
            let that = this;
            this.$axios.get(`/api/reviews-for-owner?ownerId=${this.id}`)
            .then(resp => {
                that.reviews = resp.data;
                console.log(resp.data);
                that.reviews.forEach(x => x.client.photo = "http://localhost:8080" + x.client.photo)
            });
        }
    },
};
</script>

<style>
</style>