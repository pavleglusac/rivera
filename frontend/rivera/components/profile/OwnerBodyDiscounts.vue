<template>
    <div>
        <div v-if="discounts.length === 0">
			<p style="text-align: center">No offers found.</p>
		</div>
        <div v-else class="d-flex justify-content-around" >
            <div class="d-flex w-100" style="flex-wrap: wrap;">
                <EntityOnDiscount v-for="entity in discounts" :entity="entity" :key="entity" style="flex: 0 0 33.333333%;" />
            </div>
            
        </div>
    </div>
</template>

<script>
import EntityOnDiscount from '../client/EntityOnDiscount.vue'
export default {
    components: { EntityOnDiscount },
    props: [],
    data() {
        return {
            id: '',
            discounts: [],
        }
    },
    mounted() { 
        this.id = this.$route.params.profile;
        this.loadDiscounts();
    },
    methods: {
        loadDiscounts() {
            let that = this;
            this.$axios.get(`/api/discounts-for-owner?ownerId=${this.id}`)
            .then(resp => {
                that.discounts = resp.data;
            })
        }
    }
};
</script>

<style>
</style>