<template>
	<div>
		<b-card class="mb-3" style="margin: 40px; margin-top: 10px">
			<b-form>
				<div class="p-2">
					<form>
						<div class="form-row">
							<div class="form-group col-md-6">
								<b-form-input
									id="search-filter"
									size="sm"
									placeholder="Search..."
									v-model="searchText"
									v-on:input="resetTimer"
								></b-form-input>
							</div>
							<div class="form-group col-md-4">
								<b-form-select
									size="sm"
									v-model="sort"
									:options="orderBy"
									@change.native="reload"
								></b-form-select>
							</div>

							<div
								class="form-group col-md-2"
								v-if="adding == 'adventure'"
							>
								<b-button
									size="sm"
									class="w-100"
									style="
										background-color: #16c79a;
										border: 1px solid #16c79a;
									"
									@click="addModal"
									>Add adventure</b-button
								>
							</div>
							<div
								class="form-group col-md-2"
								v-else-if="adding == 'cottage'"
							>
								<b-button
									size="sm"
									class="w-100"
									style="
										background-color: #16c79a;
										border: 1px solid #16c79a;
									"
									@click="addModal"
									>Add cottage</b-button
								>
							</div>
							<div class="form-group col-md-2" v-else>
								<b-button
									size="sm"
									class="w-100"
									style="
										background-color: #16c79a;
										border: 1px solid #16c79a;
									"
									@click="addModal"
									>Add boat</b-button
								>
							</div>
						</div>
					</form>

					<div
						v-if="loadingRentables"
						class="d-flex justify-content-center mb-3"
					>
						<b-spinner
							variant="success"
							class="spinning m-2"
						></b-spinner>
					</div>

					<div v-else-if="!(offers.length === 0)">
						<EntityCard
							v-for="(entity, index) in offers"
							:entity="entity"
							:offerType="getActiveOffers()"
							:myRentable="true"
							class="mb-3"
							v-bind:key="entity.name + index"
						/>
					</div>
					<div v-else>
						<p style="text-align: center">No rentables found.</p>
					</div>
				</div>
			</b-form>
			<b-modal id="add_modal" size="xl" hide-header hide-footer>
				<div v-if="adding == 'adventure'">
					<AddAdventure />
				</div>
				<div v-if="adding == 'boat'">
					<AddBoat />
				</div>
				<div v-if="adding == 'cottage'">
					<AddCottage />
				</div>
			</b-modal>
		</b-card>
	</div>
</template>

<script>
import EntityCard from "../components/search/entity_card.vue";
import UnauthenticatedNavbar from "../components/navbars/UnauthenticatedNavbar.vue";
import { BIcon, BIconStarFill, BIconSearch } from "bootstrap-vue";
import AddCottage from "./add_cottage/AddCottage.vue";
import AddAdventure from "./add-adventure/AddAdventure.vue";
import AddBoat from "./add_boat/AddBoat.vue";

export default {
	components: {
		UnauthenticatedNavbar,
		EntityCard,
		BIcon,
		BIconStarFill,
		BIconSearch,
		AddCottage,
		AddAdventure,
		AddBoat,
	},
	data() {
		return {
			role: "",
			offers: [],
			filteredOffers: [],
			isFiltering: false,
			activeCottages: false,
			activeBoats: false,
			tags: [],
			activeAdventures: true,
			loadingRentables: false,
			typingTimer: "",
			adding: "",
			doneTypingInterval: 500,
			searchText: "",
			sort: "name-a",
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
		let that = this;
		this.$axios
			.get("/api/auth/getRole", {
				headers: {
					Authorization:
						"Bearer " + window.localStorage.getItem("JWT"),
				},
			})
			.then((resp) => {
				that.role = resp.data;
				console.log(that.role);

				if (that.role == "ROLE_COTTAGE_OWNER") {
					this.loadCottages();
					this.adding = "cottage";
				} else if (that.role == "ROLE_BOAT_OWNER") {
					this.loadBoats();
					this.adding = "boat";
				} else {
					this.loadAdventures();
					this.adding = "adventure";
				}
			})
			.catch((err) => {
				console.log(err);
			});
		console.log(this.role);
	},
	methods: {
		getActiveOffers() {
			if (this.role == "ROLE_COTTAGE_OWNER") return "cottage";
			else if (this.role == "ROLE_BOAT_OWNER") return "boat";
			else return "adventure";
		},
		reload() {
			if (this.role == "ROLE_COTTAGE_OWNER") this.loadCottages();
			else if (this.role == "ROLE_BOAT_OWNER") this.loadBoats();
			else this.loadAdventures();
		},
		addModal() {
			this.$bvModal.show("add_modal");
		},
		renamePicturesInResponse(response) {
			for (let index = 0; index < response.data.length; index++) {
				for (let j = 0; j < response.data[index].pictures.length; j++) {
					response.data[index].pictures[j] =
						process.env.backend + response.data[index].pictures[j];
				}
			}
		},
		loadBoats() {
			this.loadingRentables = true;
			let that = this;
			that.offers = [];
			this.$axios
				.get("/api/auth/get-logged-username", {
					headers: {
						Authorization:
							"Bearer " + window.localStorage.getItem("JWT"),
					},
				})
				.then((resp) => {
					console.log(resp.data);
					this.$axios
						.post(
							`/api/boat/search-boats?&numberOfResults=10&orderBy=${
								that.sort
							}&search=${that.searchText.trim()}&tags=${
								that.tags
							}&ownerUsername=${resp.data}`
						)
						.then((response) => {
							that.renamePicturesInResponse(response);
							that.offers = response.data;
							that.loadingRentables = false;
						});
					that.activeCottages = false;
					that.activeBoats = true;
					that.activeAdventures = false;
				})
				.catch((err) => {
					console.log(err);
				});
		},
		loadAdventures() {
			this.loadingRentables = true;
			let that = this;
			that.offers = [];
			this.$axios
				.get("/api/auth/get-logged-username", {
					headers: {
						Authorization:
							"Bearer " + window.localStorage.getItem("JWT"),
					},
				})
				.then((resp) => {
					console.log(resp.data);
					this.$axios
						.post(
							`/api/search-adventures?&numberOfResults=10&orderBy=${
								that.sort
							}&search=${that.searchText.trim()}&tags=${
								that.tags
							}&ownerUsername=${resp.data}`
						)
						.then((response) => {
							that.renamePicturesInResponse(response);
							that.offers = response.data;
							that.loadingRentables = false;
						});
					that.activeCottages = false;
					that.activeBoats = false;
					that.activeAdventures = true;
				})
				.catch((err) => {
					console.log(err);
				});
		},
		loadCottages() {
			this.loadingRentables = true;
			let that = this;
			that.offers = [];
			this.$axios
				.get("/api/auth/get-logged-username", {
					headers: {
						Authorization:
							"Bearer " + window.localStorage.getItem("JWT"),
					},
				})
				.then((resp) => {
					console.log(resp.data);
					this.$axios
						.post(
							`/api/cottage/search-cottages-for-owner?&numberOfResults=10&orderBy=${
								that.sort
							}&search=${that.searchText.trim()}&tags=${
								that.tags
							}&ownerUsername=${resp.data}`
						)
						.then((response) => {
							that.renamePicturesInResponse(response);
							that.offers = response.data;
							that.loadingRentables = false;
						});
					that.activeCottages = true;
					that.activeBoats = false;
					that.activeAdventures = false;
				})
				.catch((err) => {
					console.log(err);
				});
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