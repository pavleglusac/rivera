<template>
	<div>
        <Navbar :currentPage="'entities'" />
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
                        <li class="nav-item">
							<p
								class="nav-link"
								v-bind:class="{ active: activeClients }"
								id="boats-btn"
								@click="loadClients"
							>
								<span><font-awesome-icon icon="user" /> Clients</span>
							</p>
						</li>
                        <li class="nav-item">
							<p
								class="nav-link"
								v-bind:class="{ active: activeOwners }"
								id="boats-btn"
								@click="loadOwners"
							>
								<span><font-awesome-icon icon="user-tie" /> Owners</span>
							</p>
						</li>
					</ul>
				</div>
				<div class="p-2">
					<form>
						<div class="form-row">
							<div class="form-group col-6">
								<b-form-input
									id="search-filter"
									size="sm"
									placeholder="Search by name..."
									v-model="searchText"
									v-on:input="resetTimer"
								></b-form-input>
							</div>
						</div>
					</form>

					<div
						v-if="loadingRentables"
						class="d-flex justify-content-center mb-3"
					>
						<b-spinner variant="success" class="spinning m-2"></b-spinner>
					</div>
					<div v-else-if="offers.length === 0">
						<p style="text-align: center">No results found.</p>
					</div>
					<div v-else-if="!activeClients && !activeOwners">
						<EntityCard
							v-for="(entity, index) in offers"
							:entity="entity"
							:offerType="getActiveOffers()"
							class="mb-3"
							v-bind:key="entity.name + index"
                            :adminView='true'
						/>
					</div>
                    <div v-else>
                        <PersonCard
							v-for="person in offers"
                            v-bind:key="person.username"
                            :person="person"
                            :adminView='true'
                        />
                    </div>
				</div>
			</b-form>
		</b-card>
	</div>
</template>

<script>
import EntityCard from "@/components/search/entity_card.vue";
import PersonCard from "@/components/search/PersonCard.vue";
import Navbar from "@/components/navbar.vue";
import { BIcon, BIconStarFill, BIconSearch } from "bootstrap-vue";

export default {
	components: {
		Navbar,
		EntityCard,
        PersonCard,
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
			activeAdventures: true,
            activeOwners: false,
            activeClients: false,
			loadingRentables: false,
			searchText: "",
			typingTimer: "",
			doneTypingInterval: 500,	
		};
	},
	mounted() {
		this.loadAdventures();
	},
	methods: {
		getActiveOffers() {
			if (this.activeCottages) return "cottage";
			else if (this.activeBoats) return "boat";
            else if (this.activeClients) return "client";
            else if (this.activeOwners) return "owner";
			else return "adventure";
		},
		reload() {
			if (this.activeCottages) this.loadCottages();
			else if (this.activeBoats) this.loadBoats();
            else if (this.activeClients) this.loadClients();
            else if (this.activeOwners) this.loadOwners();
			else this.loadAdventures();
		},
		loadBoats() {
			this.loadingRentables = true;
			let that = this;
			that.offers = [];
			this.$axios
				.post(
					`/api/boat/search-boats?&numberOfResults=10&orderBy=name-a&search=${that.searchText.trim()}&tags=`
				)
				.then((response) => {
					console.log(response.data + "-------");
					that.offers = response.data;
					that.loadingRentables = false;
				});
			that.activeCottages = false;
			that.activeBoats = true;
			that.activeAdventures = false;
            that.activeOwners = false;
            that.activeClients = false;
		},
		loadAdventures() {
			this.loadingRentables = true;
			let that = this;
			that.offers = [];
			this.$axios
				.post(
					`/api/search-adventures?&numberOfResults=10&orderBy=name-a&search=${that.searchText.trim()}&tags=`
				)
				.then((response) => {
					that.offers = response.data;
					that.loadingRentables = false;
					console.log(that.offers);
				});
			that.activeCottages = false;
			that.activeBoats = false;
			that.activeAdventures = true;
            that.activeOwners = false;
            that.activeClients = false;
		},
		loadCottages() {
			this.loadingRentables = true;
			let that = this;
			that.offers = [];
			this.$axios
				.post(
					`/api/cottage/search-cottages?&numberOfResults=10&orderBy=name-a&search=${that.searchText.trim()}&tags=`
				)
				.then((response) => {
					that.offers = response.data;
					that.loadingRentables = false;
				});
			that.activeCottages = true;
			that.activeBoats = false;
			that.activeAdventures = false;
            that.activeOwners = false;
            that.activeClients = false;
		},
        loadClients() {
            this.loadingRentables = true;
			let that = this;
			that.offers = [];
			this.$axios
				.get(
					`/api/search-person?&numberOfResults=10&search=${that.searchText.trim()}&type=client`
				)
				.then((response) => {
					console.log(response.data + "-------");
					that.offers = response.data;
					that.loadingRentables = false;
				});
            that.activeCottages = false;
			that.activeBoats = false;
			that.activeAdventures = false;
            that.activeOwners = false;
            that.activeClients = true;

        },
        loadOwners() {
            this.loadingRentables = true;
			let that = this;
			that.offers = [];
			this.$axios
				.get(
					`/api/search-person?&numberOfResults=10&search=${that.searchText.trim()}&type=owner`
				)
				.then((response) => {
					console.log(response.data + "-------");
					that.offers = response.data;
					that.loadingRentables = false;
			    });
            that.activeCottages = false;
			that.activeBoats = false;
			that.activeAdventures = false;
            that.activeOwners = true;
            that.activeClients = false;

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