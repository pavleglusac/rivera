<template>
	<div>
		<b-card class="mb-3" style="margin: 40px; margin-top: 10px">
			<b-form>
				<div class="container-fluid p-2">
					<ul class="nav nav-pills nav-fill" role="tablist">
						<li class="nav-item">
							<p
								class="nav-link"
								v-bind:class="{ active: activeAdventures }"
								id="adventures-btn"
								@click="loadAdventures"
							>
								<span><font-awesome-icon icon="fish" /> Fishing</span>
							</p>
						</li>
						<li class="nav-item">
							<p
								class="nav-link"
								v-bind:class="{ active: activeCottages }"
								id="cottages-btn"
								@click="loadCottages"
							>
								<span><font-awesome-icon icon="house" /> Cottages</span>
							</p>
						</li>
						<li class="nav-item">
							<p
								class="nav-link"
								v-bind:class="{ active: activeBoats }"
								id="boats-btn"
								@click="loadBoats"
							>
								<span><font-awesome-icon icon="sailboat" /> Boats</span>
							</p>
						</li>
					</ul>
				</div>
				<div class="p-2">
					<form>
						<div class="form-row">
							<div class="form-group col-md-3">
								<b-form-input
									id="search-filter"
									size="sm"
									placeholder="Search by name or location..."
									v-model="searchText"
									v-on:input="resetTimer"
								></b-form-input>
							</div>
							<div class="form-group col-md-3">
								<b-form-datepicker
									size="sm"
									v-on:input="reload"
									placeholder="Start date"
								></b-form-datepicker>
							</div>
							<div class="form-group col-md-3">
								<b-form-datepicker
									size="sm"
									v-on:input="reload"
									placeholder="End date"
								></b-form-datepicker>
							</div>
							<div class="form-group col-md-3">
								<b-form-select
									size="sm"
									v-model="sort"
									:options="orderBy"
									@change="reload"
								></b-form-select>
							</div>
						</div>
					</form>
					<div style="margin-bottom: 15px">
						<b-form-tags
							size="sm"
							v-on:input="reload"
							v-model="tags"
							input-id="tags-basic"
						></b-form-tags>
					</div>

					<div
						v-if="loadingRentables"
						class="d-flex justify-content-center mb-3"
					>
						<b-spinner variant="success" class="spinning m-2"></b-spinner>
					</div>
					<div v-else-if="offers.length === 0">
						<p style="text-align: center">No offers found.</p>
					</div>
					<div v-else>
						<EntityCard
							v-for="(entity, index) in offers"
							:entity="entity"
							:offerType="getActiveOffers()"
							class="mb-3"
							v-bind:key="entity.name + index"
						/>
					</div>
				</div>
			</b-form>
		</b-card>
	</div>
</template>

<script>
import EntityCard from "../components/search/entity_card.vue";
import UnauthenticatedNavbar from "../components/navbars/UnauthenticatedNavbar.vue";
import { BIcon, BIconStarFill, BIconSearch } from "bootstrap-vue";

export default {
	components: {
		UnauthenticatedNavbar,
		EntityCard,
		BIcon,
		BIconStarFill,
		BIconSearch,
	},
	data() {
		return {
			offers: [],
			filteredOffers: [],
			isFiltering: false,
			activeCottages: false,
			activeBoats: false,
			tags: [],
			activeAdventures: true,
			loadingRentables: false,
			searchText: "",
			sort: "name-a",
			typingTimer: "",
			doneTypingInterval: 500,
			orderBy: [
				{ value: "name-a", text: "Sort by name ascending" },
				{ value: "price-a", text: "Sort by price ascending" },
				{ value: "score-a", text: "Sort by score ascending" },
				{ value: "name-d", text: "Sort by name descending" },
				{ value: "price-d", text: "Sort by price descending" },
				{ value: "score-d", text: "Sort by score descending" },
			],
		};
	},
	mounted() {
		this.loadAdventures();
	},
	methods: {
		getActiveOffers() {
			if (this.activeCottages) return "cottage";
			else if (this.activeBoats) return "boat";
			else return "adventure";
		},
		reload() {
			if (this.activeCottages) this.loadCottages();
			else if (this.activeBoats) this.loadBoats();
			else this.loadAdventures();
		},
		loadBoats() {
			this.loadingRentables = true;
			let that = this;
			that.offers = [];
			this.$axios
				.post(
					`/api/boat/search-boats?&numberOfResults=10&orderBy=${
						that.sort
					}&search=${that.searchText.trim()}&tags=${that.tags}`
				)
				.then((response) => {
					console.log(response.data + "-------");
					that.offers = response.data;
					that.loadingRentables = false;
				});
			that.activeCottages = false;
			that.activeBoats = true;
			that.activeAdventures = false;
		},
		loadAdventures() {
			this.loadingRentables = true;
			let that = this;
			that.offers = [];
			this.$axios
				.post(
					`/api/search-adventures?&numberOfResults=10&orderBy=${
						that.sort
					}&search=${that.searchText.trim()}&tags=${that.tags}`
				)
				.then((response) => {
					that.offers = response.data;
					that.loadingRentables = false;
					console.log(that.offers);
				});
			that.activeCottages = false;
			that.activeBoats = false;
			that.activeAdventures = true;
		},
		loadCottages() {
			this.loadingRentables = true;
			let that = this;
			that.offers = [];
			this.$axios
				.post(
					`/api/cottage/search-cottages?&numberOfResults=10&orderBy=${
						that.sort
					}&search=${that.searchText.trim()}&tags=${that.tags}`
				)
				.then((response) => {
					that.offers = response.data;
					that.loadingRentables = false;
				});
			that.activeCottages = true;
			that.activeBoats = false;
			that.activeAdventures = false;
		},
		resetTimer() {
			clearTimeout(this.typingTimer);
			this.usernameExists = false;
			this.loadingUsername = true;
			this.loadingRentables = true;
			this.typingTimer = setTimeout(this.reload, this.doneTypingInterval);
		},
	},
};
</script>

<style>
li > p .active {
	color: #fff;
}

.nav-pills .nav-link.active,
.nav-pills .show > .nav-link {
	background-color: var(--prime-color);
}

.nav-link:hover {
	opacity: 0.9;
	background-color: var(--prime-color);
	cursor: pointer;
}

.nav-link {
	font-weight: bold;
	color: var(--dark-blue-color);
}

@media screen and (max-width: 768px) {
	.nav-fill .nav-item {
		width: 100% !important;
		flex-basis: unset !important;
	}
}
</style>