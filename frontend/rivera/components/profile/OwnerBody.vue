<template>
	<div class="mt-5 border-top border-bottom">
		<div class="mt-2 mb-2">
			<b-form>
				<div class="container-fluid p-2">
					<ul class="nav nav-pills nav-fill" role="tablist">
						<li class="nav-item">
							<p
								class="nav-link nav-mn"
								v-bind:class="{ active: activeSection == 0 }"
								id="adventures-btn"
								v-on:click="activeSection = 0"
							>
								<span><font-awesome-icon icon="fish" /> Rentables</span>
							</p>
						</li>
						<li class="nav-item">
							<p
								class="nav-link nav-mn"
								v-bind:class="{ active: activeSection == 1 }"
								id="cottages-btn"
								v-on:click="activeSection = 1"
							>
								<span><font-awesome-icon icon="percent" /> Discounts</span>
							</p>
						</li>
						<li class="nav-item">
							<p
								class="nav-link nav-mn"
								v-bind:class="{ active: activeSection == 2 }"
								id="boats-btn"
								v-on:click="activeSection = 2"
							>
								<span><font-awesome-icon icon="star" /> Reviews</span>
							</p>
						</li>
					</ul>
				</div>
			</b-form>
            <div>
                <OwnerBodyRentables v-if="activeSection == 0" :rentableType="rentableType" />
                <OwnerBodyDiscounts v-if="activeSection == 1" />
                <OwnerBodyReviews v-if="activeSection == 2" />
            </div>
		</div>
	</div>
</template>

<script>
import OwnerBodyRentables from './OwnerBodyRentables.vue';
import OwnerBodyDiscounts from './OwnerBodyDiscounts.vue';
import OwnerBodyReviews from './OwnerBodyReviews.vue';

export default {
    components: {OwnerBodyRentables, OwnerBodyReviews, OwnerBodyDiscounts},
	props: ["role"],
	data() {
		return {
			id: "",
			selected: 0,
			activeSection: 0,
            rentableType: '',
		};
	},
	mounted() {
		this.id = this.$route.params.profile;
        if(this.role == 'Fishing Instructor') {
            this.rentableType = "adventure";
        } else if(this.role == 'Fishing Instructor') {
            this.rentableType = "cottage";
        } else {
            this.rentableType = "boat";
        }
	},
	watch: {
		activeSection(val, oldVal) {
			if (val == oldVal) return;
		},
	},
	methods: {

	},
};
</script>

<style></style>
