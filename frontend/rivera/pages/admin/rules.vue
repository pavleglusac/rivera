<template>
	<div>
		<Navbar :currentPage="'rules'" />
		<div
			class="d-flex flex-column mt-1"
			style="width: 100vw; margin-left: auto; margin-right: auto"
		>
			<b-card
				class="mx-auto my-auto card w-50 mt-2 mb-5"
				style="margin-top: 0; box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px"
				title="Site rules"
			>
				<b-form class="w-100 p-0 mt-3">
					<b-input-group class="w-100 p-0 m-0">
						<b-col cols="6" class="p-0">
							<label class="w-100 p-0 m-0 ml-0">Number of points per reservation</label>
						</b-col>
						<b-col cols="6 p-0">
							<b-form-input
								type="number"
								placeholder=""
								class="form-control form-control-lg"
								style="font-size: 15px"
								v-model="pointsPerReservation"
								min=0
							/>
						</b-col>
					</b-input-group>
					<b-input-group class="mt-2">
						<b-col cols="6" class="p-0">
							<label class="mr-1">Income percentage</label>
						</b-col>
						<b-col cols="6 p-0">
							<b-form-input
								type="number"
								placeholder=""
								class="form-control form-control-lg"
								style="font-size: 15px"
								v-model="incomePercentage"
								min=0
								max=100
							/>
						</b-col>
					</b-input-group>
					<div>
						<b-row class="mt-2">
							<b-col cols="6"></b-col>
							<b-col cols="6" class="d-flex flex justify-content-end">
								<b-button variant="light" @click="updateRules">Update</b-button>
							</b-col>
						</b-row>
					</div>
				</b-form>
			</b-card>
			<b-card
				class="mx-auto my-auto card mt-5 w-50 mt-5"
				style="
					margin-top: 30px !important;
					box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
				"
				title="Owner loyalty"
			>
				<b-row class="mt-2 p-2">
					<b-col cols="4"> Name </b-col>
					<b-col cols="2"> Points </b-col>
					<b-col cols="2"> Income </b-col>
					<b-col cols="2" class="ml-2"> Color </b-col>
				</b-row>
				<div v-for="tier in ownerTiers" :key="tier.name">
					<b-row class="mt-2">
						<b-col cols="4">
							<b-form-input
								type="text"
								placeholder="Tier name"
								class="form-control form-control-lg"
								style="font-size: 15px"
								:value="tier.name"
								readonly
							/>
						</b-col>
						<b-col cols="2">
							<b-form-input
								type="number"
								placeholder="Points"
								class="form-control form-control-lg"
								style="font-size: 15px"
								:value="tier.points"
								readonly
							/>
						</b-col>
						<b-col cols="2">
							<b-form-input
								type="number"
								placeholder="Points"
								class="form-control form-control-lg"
								style="font-size: 15px"
								:value="tier.discount"
								readonly
							/>
						</b-col>
						<b-col cols="2">
							<b-form-input
								type="color"
								placeholder="Tier name"
								class="form-control form-control-lg"
								style="font-size: 15px"
								:value="tier.color"
								readonly
							/>
						</b-col>
						<b-col cols="2">
							<b-button variant="light" v-on:click="removeOwnerTier(tier)">Remove</b-button>
						</b-col>
					</b-row>
				</div>

				<b-row class="mt-4">
					<b-col cols="4">
						<b-form-input
							type="text"
							placeholder="Tier name"
							class="form-control form-control-lg"
							style="font-size: 15px"
							ref="nameInput"
							v-model="ownerName"
						/>
					</b-col>
					<b-col cols="2">
						<b-form-input
							type="number"
							placeholder="Points"
							class="form-control form-control-lg"
							style="font-size: 15px"
							ref="pointsInput"
							v-model="ownerPoints"
							:min="minOwnerPoints"
						/>
					</b-col>
					<b-col cols="2">
						<b-form-input
							type="number"
							placeholder="Income"
							class="form-control form-control-lg"
							style="font-size: 15px"
							v-model="ownerDiscount"
							:max="incomePercentage"
							:min="minOwnerDiscount"
						/>
					</b-col>
					<b-col cols="2">
						<b-form-input
							type="color"
							placeholder="Tier name"
							class="form-control form-control-lg"
							style="font-size: 15px"
							value="#f6b73c"
							ref="colorInput"
							v-model="ownerColor"
						/>
					</b-col>
					<b-col cols="2">
						<b-button variant="light" @click="addOwnerTier" class="w-100">Add</b-button>
					</b-col>
				</b-row>
			</b-card>
			<b-card
				class="mx-auto my-auto card mt-5 w-50 mt-5 mb-5"
				style="
					margin-top: 30px !important;
					margin-bottom: 30px !important;
					box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
				"
				title="Client loyalty"
			>
				<b-row class="mt-2 p-2">
					<b-col cols="4"> Name </b-col>
					<b-col cols="2"> Points </b-col>
					<b-col cols="2"> Discount </b-col>
					<b-col cols="2" class="ml-2"> Color </b-col>
				</b-row>
				<div v-for="tier in clientTiers" :key="tier.name + 'c'">
					<b-row class="mt-2">
						<b-col cols="4">
							<b-form-input
								type="text"
								placeholder="Tier name"
								class="form-control form-control-lg"
								style="font-size: 15px"
								:value="tier.name"
								readonly
							/>
						</b-col>
						<b-col cols="2">
							<b-form-input
								type="number"
								placeholder="Points"
								class="form-control form-control-lg"
								style="font-size: 15px"
								:value="tier.points"
								readonly
							/>
						</b-col>
						<b-col cols="2">
							<b-form-input
								type="number"
								placeholder="Points"
								class="form-control form-control-lg"
								style="font-size: 15px"
								:value="tier.discount"
								readonly
							/>
						</b-col>
						<b-col cols="2">
							<b-form-input
								type="color"
								placeholder="Tier name"
								class="form-control form-control-lg"
								style="font-size: 15px"
								:value="tier.color"
								readonly
							/>
						</b-col>
						<b-col cols="2">
							<b-button variant="light" v-on:click="removeClientTier(tier)">Remove</b-button>
						</b-col>
					</b-row>
				</div>

				<b-row class="mt-4">
					<b-col cols="4">
						<b-form-input
							type="text"
							placeholder="Tier name"
							class="form-control form-control-lg"
							style="font-size: 15px"
							ref="nameInput"
							v-model="clientName"
						/>
					</b-col>
					<b-col cols="2">
						<b-form-input
							type="number"
							placeholder="Points"
							class="form-control form-control-lg"
							style="font-size: 15px"
							ref="pointsInput"
							v-model="clientPoints"
							:min="minClientPoints"
						/>
					</b-col>
					<b-col cols="2">
						<b-form-input
							type="number"
							placeholder="Discount"
							class="form-control form-control-lg"
							style="font-size: 15px"
							v-model="clientDiscount"
							max="100"
							:min="minClientDiscount"
						/>
					</b-col>
					<b-col cols="2">
						<b-form-input
							type="color"
							placeholder="Tier name"
							class="form-control form-control-lg"
							style="font-size: 15px"
							value="#f6b73c"
							ref="colorInput"
							v-model="clientColor"
						/>
					</b-col>
					<b-col cols="2">
						<b-button variant="light" @click="addClientTier" class="w-100">Add</b-button>
					</b-col>
				</b-row>
			</b-card>
		</div>
	</div>
</template>

<script>
import Navbar from "../../components/navbar.vue";

export default {
	components: { Navbar },
	
	data() {
		return {
			ownerTiers: [
				
			],
			clientTiers: [
				
			],
			ownerName: "",
			ownerPoints: 0,
			ownerDiscount: 0,
			ownerColor: "",
			clientName: "",
			clientPoints: 0,
			clientDiscount: 0,
			clientColor: "",
			minClientPoints: 0,
			minClientDiscount: 0,
			minOwnerPoints: 0,
			minOwnerDiscount: 0,
			pointsPerReservation: 5,
			incomePercentage: 25,
		};
	}, 
	methods: {
		addOwnerTier() {
			let tier = {
				name: this.ownerName,
				points: this.ownerPoints,
				discount: this.ownerDiscount,
				color: this.ownerColor,
			};
			this.ownerTiers.push(tier);
			this.sendCategories();
		},
		addClientTier() {
			let tier = {
				name: this.clientName,
				points: this.clientPoints,
				discount: this.clientDiscount,
				color: this.clientColor,
			};
			this.clientTiers.push(tier);
			this.sendCategories();
		},
		setMins() {
			for (let tier of this.ownerTiers) {
				this.minOwnerPoints = Math.max(tier.points, this.minOwnerPoints);
				this.minOwnerDiscount = Math.max(tier.discount, this.minOwnerDiscount);
			}
			this.ownerPoints = this.minOwnerPoints;
			this.ownerDiscount = this.minOwnerDiscount;

			for (let tier of this.clientTiers) {
				this.minClientPoints = Math.max(tier.points, this.minClientPoints);
				this.minClientDiscount = Math.max(tier.discount, this.minClientDiscount);
			}
			this.clientPoints = this.minClientPoints;
			this.clientDiscount = this.minClientDiscount;
		},
		getRules() {
			let that = this;
			this.ownerTiers.splice(0, this.ownerTiers.length);
			this.clientTiers.splice(0, this.clientTiers.length);
			this.$axios.get('/api/admin/categories').then(resp => {
				for(let rule of resp.data) {
					let tier = {
						name: rule.name,
						points: rule.numberOfPoints,
						discount: rule.percentage,
						color: rule.color
					}
					if(rule.forOwner) {
						that.ownerTiers.push(tier);
					} else {
						that.clientTiers.push(tier);
					}
				}
				that.setMins();
			})
		},
		getCategories() {
			let that = this;
			this.$axios.get('/api/admin/rules').then(resp => {
				that.incomePercentage = resp.data.incomePercentage;
				that.pointsPerReservation = resp.data.pointsPerReservation;
				that.setMins();
			})
		},
		removeClientTier(tier) {
			let that = this;
			let index = that.clientTiers.indexOf(tier);
			if (index !== -1) {
				that.clientTiers.splice(index, 1);
			}
			this.sendCategories();
		},
		removeOwnerTier(tier) {
			let that = this;
			let index = that.ownerTiers.indexOf(tier);
			if (index !== -1) {
				that.ownerTiers.splice(index, 1);
			}
			this.sendCategories();
		},
		sendCategories() {
			let to_send = [];
			this.clientTiers.forEach(x => to_send.push({name: x.name, percentage: x.discount, numberOfPoints: x.points, color: x.color, forOwner: false}));
			this.ownerTiers.forEach(x => to_send.push({name: x.name, percentage: x.discount, numberOfPoints: x.points, color: x.color, forOwner: true}));
			this.$axios.post('/api/admin/categories', to_send)
			.then(resp => {
				
			});
		},
		updateRules() {
			this.$axios.post('/api/admin/rules', {pointsPerReservation: this.pointsPerReservation, incomePercentage: this.incomePercentage })
			.then(resp => {
				
			});
		}
	},
	
	mounted() {
		this.getRules();
		this.getCategories();
	},
	watch: {
		tiers: function(newVal, oldVal) {
			this.setMins();
		}
	}
};
</script>

<style>
</style>