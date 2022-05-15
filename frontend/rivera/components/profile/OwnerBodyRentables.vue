<template>
	<div class="p-4">
		<div v-if="offers.length === 0">
			<p style="text-align: center">No offers found.</p>
		</div>
		<div v-else>
			<EntityCard
				v-for="entity in offers"
				:entity="entity"
				:offerType="rentableType"
				class="mb-3"
				v-bind:key="entity.name"
			/>
		</div>
	</div>
</template>

<script>
import EntityCard from "../search/entity_card.vue";
export default {
	components: { EntityCard },
	props: ["rentableType"],
	data() {
		return {
			offers: [],
			filteredOffers: [],
			tags: [],
			searchText: '',
			sort: 'name-a',
			id: '',
		};
	},
	mounted() {
		this.id = this.$route.params.profile;
		this.reload();
	},
	methods: {
		reload() {
			this.loadRentables();
		},
		loadRentables() {
			let that = this;
			that.offers = [];
			this.$axios
				.get(
					`/api/rentables-for-owner?ownerId=${that.id}`
				)
				.then((response) => {
					that.offers = response.data;
				});
		},
		
	},
};
</script>

<style></style>
