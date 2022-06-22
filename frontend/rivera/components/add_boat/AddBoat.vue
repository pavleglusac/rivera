<template>
	<div class="m-5">
		<form id="addBoatForm">
			<div class="form-row" style="text-align: center">
				<div class="form-group col-md-12">
					<h3>Boat addition</h3>
				</div>
			</div>

			<div class="form-row">
				<h5><font-awesome-icon icon="pen" /> Main data</h5>
			</div>
			<div class="form-row">
				<div class="form-group col-md-6">
					<label for="name">Boat Name</label>
					<input
						type="text"
						class="form-control"
						v-model="name"
						id="inputEmail4"
						placeholder="Name"
						v-bind:class="{ 'error-boarder': $v.name.$invalid && nameClicked }"
						@keydown="nameClicked = true"
					/>
					<ErrorDiv v-if="nameClicked" :parameter="$v.name" :name="'Name'">
					</ErrorDiv>
				</div>

				<div class="form-group col-6">
					<label for="inputAddress">Address</label>
					<input
						type="text"
						class="form-control"
						v-model="address"
						id="inputAddress"
						placeholder="1234 Main St"
						v-bind:class="{
							'error-boarder': $v.address.$invalid && addressClicked,
						}"
						@keydown="addressClicked = true"
					/>
					<ErrorDiv
						v-if="addressClicked"
						:parameter="$v.address"
						:name="'Adress'"
					>
					</ErrorDiv>
				</div>
			</div>

			<div class="form-row">
				<div class="form-group col-6">
					<label for="inputCity">City</label>
					<input
						type="text"
						class="form-control"
						v-model="city"
						id="inputCity"
						placeholder="City"
						v-bind:class="{ 'error-boarder': $v.city.$invalid && cityClicked }"
						@keydown="cityClicked = true"
					/>
					<ErrorDiv v-if="cityClicked" :parameter="$v.city" :name="'City'">
					</ErrorDiv>
				</div>
				<div class="form-group col-6">
					<label for="inputState">State</label>
					<select
						id="inputState"
						class="form-control"
						v-model="country"
						v-bind:class="{
							'error-boarder': $v.country.$invalid && countryClicked,
						}"
						@keydown="countryClicked = true"
					>
						<option selected>Choose...</option>
						<option
							v-for="country in countries"
							:key="country.label"
							:value="country.value"
						>
							{{ country.label }}
						</option>
					</select>
					<ErrorDiv
						v-if="countryClicked"
						:parameter="$v.country"
						:name="'Country'"
					>
					</ErrorDiv>
				</div>
			</div>

			<div class="form-row">
				<div class="form-group col-md-12">
					<label for="exampleFormControlTextarea1">Description</label>
					<textarea
						class="form-control"
						id="exampleFormControlTextarea1"
						v-model="description"
						rows="3"
						placeholder="Description"
						v-bind:class="{
							'error-boarder': $v.description.$invalid && descriptionClicked,
						}"
						@keydown="descriptionClicked = true"
					></textarea>
					<ErrorDiv
						v-if="descriptionClicked"
						:parameter="$v.description"
						:name="'Description'"
					>
					</ErrorDiv>
				</div>
			</div>

			<hr class="w-100" />

			<div class="form-row">
				<h5><font-awesome-icon icon="tag" /> Pricelist</h5>
			</div>

			<div class="form-row">
				<div class="form-group col-md-4">
					<label for="price">Price per hour (€)</label>
					<input
						type="number"
						class="form-control"
						v-model="perHour"
						id="perHour"
						name="perHour"
						min="0"
						placeholder="Price per hour"
						v-bind:class="{ 'error-boarder': $v.perHour.$invalid }"
					/>
					<ErrorDiv :parameter="$v.perHour" :name="'Per hour'"> </ErrorDiv>
				</div>

				<div class="form-group col-md-4">
					<label for="price">Price per day (€)</label>
					<input
						type="number"
						class="form-control"
						v-model="perDay"
						id="perDay"
						name="perDay"
						min="0"
						placeholder="Price per day"
						v-bind:class="{ 'error-boarder': $v.perDay.$invalid }"
					/>
					<ErrorDiv :parameter="$v.perDay" :name="'Per day'"> </ErrorDiv>
				</div>

				<div class="form-group col-md-4">
					<label for="price">Cancellation terms (%)</label>
					<input
						type="number"
						step="0.01"
						class="form-control"
						v-model="cancellationTerms"
						id="cancellationTerms"
						name="cancellationTerms"
						min="0"
						max="100"
						placeholder="Cancellation terms"
						v-bind:class="{
							'error-boarder':
								$v.cancellationTerms.$invalid && cancellationTermsClicked,
						}"
						@keydown="cancellationTermsClicked = true"
					/>
					<ErrorDiv
						v-if="cancellationTermsClicked"
						:parameter="$v.cancellationTerms"
						:name="'Cancellation terms'"
					>
					</ErrorDiv>
				</div>
			</div>

			<hr class="w-100" />

			<div class="form-row">
				<h5><font-awesome-icon icon="circle-info" /> Details</h5>
			</div>

			<div class="form-row">
				<div class="form-group col-12">
					<label for="inputAddress"
						>Tags - key words that describe your boat</label
					>
					<b-form-tags
						input-id="tags-basic"
						v-model="tags"
						placeholder="Tags"
						v-bind:class="{ 'error-boarder': $v.tags.$invalid && tagsClicked }"
						@input="tagsClicked = true"
					></b-form-tags>
					<ErrorDiv v-if="tagsClicked" :parameter="$v.tags" :name="'Tags'">
					</ErrorDiv>
				</div>
			</div>

			<div class="form-row">
				<div class="form-group col-12">
					<label for="inputAddress">Rules of conduct </label>
					<b-form-tags
						input-id="tags-basic"
						v-model="rulesOfConduct"
						placeholder="Rules of conduct"
						v-bind:class="{
							'error-boarder': $v.rulesOfConduct.$invalid && rulesClicked,
						}"
						@input="rulesClicked = true"
					></b-form-tags>
					<ErrorDiv
						v-if="rulesClicked"
						:parameter="$v.rulesOfConduct"
						:name="'Rules of conduct'"
					>
					</ErrorDiv>
				</div>
			</div>
			<div class="form-row">
				<div class="form-group col-12">
					<label for="inputAddress">Additional services you provide</label>
					<b-form-tags
						input-id="tags-basic"
						v-model="services"
						placeholder="Additional services"
						v-bind:class="{
							'error-boarder': $v.services.$invalid && servicesClicked,
						}"
						@input="servicesClicked = true"
					></b-form-tags>
					<ErrorDiv
						v-if="serviceClicked"
						:parameter="$v.services"
						:name="'Services'"
					>
					</ErrorDiv>
				</div>
			</div>

			<hr class="w-100" />
			<div class="form-row">
				<h5><font-awesome-icon icon="sailboat" /> Specifications</h5>
			</div>

			<div class="form-row">
				<div class="form-group col-4">
					<label for="inputType">Type</label>
					<select
						id="inputType"
						v-model="type"
						class="form-control"
						v-bind:class="{ 'error-boarder': $v.type.$invalid && typeClicked }"
						@keydown="typeClicked = true"
					>
						<option v-for="type in types" :key="type" :value="type">
							{{ type }}
						</option>
					</select>
					<ErrorDiv v-if="typeClicked" :parameter="$v.type" :name="'Type'">
					</ErrorDiv>
				</div>
				<div class="form-group col-4">
					<label for="inputLength">Length</label>
					<input
						type="text"
						class="form-control"
						v-model="length"
						id="inputLength"
						placeholder="Length"
						v-bind:class="{
							'error-boarder': $v.length.$invalid && lengthClicked,
						}"
						@keydown="lengthClicked = true"
					/>
					<ErrorDiv
						v-if="lengthClicked"
						:parameter="$v.length"
						:name="'Length'"
					>
					</ErrorDiv>
				</div>
				<div class="form-group col-4">
					<label for="inputCapacity">Capacity</label>
					<input
						type="text"
						class="form-control"
						v-model="capacity"
						id="inputCapacity"
						placeholder="Capacity"
						v-bind:class="{
							'error-boarder': $v.capacity.$invalid && capacityClicked,
						}"
						@keydown="capacityClicked = true"
					/>
					<ErrorDiv
						v-if="capacityClicked"
						:parameter="$v.capacity"
						:name="'Capacity'"
					>
					</ErrorDiv>
				</div>
			</div>

			<div class="form-row">
				<div class="form-group col-4">
					<label for="inputEnginesNumber">EnginesNumber</label>
					<input
						type="text"
						class="form-control"
						v-model="enginesNumber"
						id="inputEnginesNumber"
						placeholder="EnginesNumber"
						v-bind:class="{
							'error-boarder':
								$v.enginesNumber.$invalid && enginesNumberClicked,
						}"
						@keydown="enginesNumberClicked = true"
					/>
					<ErrorDiv
						v-if="enginesNumberClicked"
						:parameter="$v.enginesNumber"
						:name="'EnginesNumber'"
					>
					</ErrorDiv>
				</div>
				<div class="form-group col-4">
					<label for="inputEnginePower">EnginePower</label>
					<input
						type="text"
						class="form-control"
						v-model="enginePower"
						id="inputEnginePower"
						placeholder="EnginePower"
						v-bind:class="{
							'error-boarder': $v.enginePower.$invalid && enginesPowerClicked,
						}"
						@keydown="enginesPowerClicked = true"
					/>
					<ErrorDiv
						v-if="enginesPowerClicked"
						:parameter="$v.enginePower"
						:name="'EnginePower'"
					>
					</ErrorDiv>
				</div>
				<div class="form-group col-4">
					<label for="inputMaxSpeed">MaxSpeed</label>
					<input
						type="text"
						class="form-control"
						v-model="maxSpeed"
						id="inputMaxSpeed"
						placeholder="MaxSpeed"
						v-bind:class="{
							'error-boarder': $v.maxSpeed.$invalid && maxSpeedClicked,
						}"
						@keydown="maxSpeedClicked = true"
					/>
					<ErrorDiv
						v-if="maxSpeedClicked"
						:parameter="$v.maxSpeed"
						:name="'MaxSpeed'"
					>
					</ErrorDiv>
				</div>
			</div>

			<hr class="w-100" />
			<div class="form-row">
				<h5><font-awesome-icon icon="camera" /> Gallery</h5>
			</div>

			<div class="d-flex">
				<PictureUpload />
			</div>
			<b-button type="button" @click="upload" class="prime-btn float-right">
				Add boat
			</b-button>

			<div
				class="modal fade"
				id="emptyErrorModal"
				tabindex="-1"
				role="dialog"
				aria-labelledby="exampleModalLabel"
				aria-hidden="true"
			>
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel">Error</h5>
							<button
								type="button"
								class="close"
								data-dismiss="modal"
								aria-label="Close"
							>
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body">
							Fields must not be empty! Please fill in empty fields and try
							again!
						</div>
						<div class="modal-footer">
							<button class="btn btn-primary" id="errorModalClosure">
								Okay
							</button>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</template>

<script>
const countries = require("i18n-iso-countries");
countries.registerLocale(require("i18n-iso-countries/langs/en.json"));
import Vue from "vue";
import Vuelidate from "vuelidate";
import PictureUpload from "./../add-adventure/PictureUpload.vue";
Vue.use(Vuelidate);
import {
	required,
	minLength,
	between,
	email,
	maxLength,
} from "vuelidate/lib/validators";
import useValidate from "@vuelidate/core";
import ErrorDiv from "./../registration/ErrorDiv.vue";
export default {
	computed: {
		countries() {
			const list = countries.getNames("en", { select: "official" });
			return Object.keys(list).map((key) => ({ value: key, label: list[key] }));
		},
	},
	data() {
		return {
			selected: null,
			result: null,
			tags: [],
			rulesOfConduct: [],
			equipment: [],
			services: [],
			name: "",
			country: "",
			city: "",
			address: "",
			perHour: 0,
			perDay: 0,
			description: "",
			cancellationTerms: "",
			type: "",
			length: "",
			capacity: "",
			enginesNumber: "",
			enginePower: "",
			maxSpeed: "",
			nameClicked: false,
			addressClicked: false,
			cityClicked: false,
			countryClicked: false,
			descriptionClicked: false,
			tagsClicked: false,
			rulesClicked: false,
			serviceClicked: false,
			cancellationTermsClicked: false,
			typeClicked: false,
			lengthClicked: false,
			capacityClicked: false,
			enginesNumberClicked: false,
			enginesPowerClicked: false,
			maxSpeedClicked: false,
			types: [
				"YACHT",
				"FERRY",
				"CRUISE",
				"CANOE",
				"RAFT",
				"SAILBOAT",
				"ROWBOAT",
			],
		};
	},
	components: {
		ErrorDiv: ErrorDiv,
		PictureUpload,
	},
	validations: {
		cancellationTerms: {
			required,
		},
		type: {
			required,
		},
		length: {
			required,
		},
		capacity: {
			required,
		},
		enginesNumber: {
			required,
		},
		enginePower: {
			required,
		},
		maxSpeed: {
			required,
		},
		description: {
			required,
			minLength: minLength(2),
		},
		perDay: {
			required,
		},
		perHour: {
			required,
		},
		name: {
			required,
			minLength: minLength(2),
		},
		city: {
			required,
			minLength: minLength(2),
			maxLength: maxLength(20),
		},
		address: {
			required,
			minLength: minLength(2),
			maxLength: maxLength(20),
		},
		services: {
			required,
			minLength: minLength(1),
			maxLength: maxLength(20),
		},
		rulesOfConduct: {
			required,
			minLength: minLength(1),
			maxLength: maxLength(20),
		},
		country: {
			required,
		},
		tags: {
			required,
			minLength: minLength(1),
			maxLength: maxLength(20),
		},
	},
	mounted() {
		function closeModal(id) {
			$("#" + id).modal("hide");
		}
	},
	methods: {
		upload() {
			var formData = new FormData();
			var images = document.getElementById("files");

			if (this.$v.$invalid) {
				console.log(this.$v);
				alert(
					"Fields must not be empty! Please fill in empty fields and try again!"
				);
				this.nameClicked = true;
				this.addressClicked = true;
				this.cityClicked = true;
				this.countryClicked = true;
				this.descriptionClicked = true;
				this.tagsClicked = true;
				this.rulesClicked = true;
				this.serviceClicked = true;
				this.cancellationTermsClicked = true;
				this.typeClicked = true;
				this.lengthClicked = true;
				this.capacityClicked = true;
				this.enginesNumberClicked = true;
				this.enginesPowerClicked = true;
				this.maxSpeedClicked = true;
				//$("#emptyErrorModal").modal("show");
				$("#addBoatForm").submit(function (e) {
					e.preventDefault();
				});
				return;
			}
			formData.append("name", this.name);
			formData.append("description", this.description);
			formData.append("perHour", this.perHour);
			formData.append("perDay", this.perDay);
			formData.append("tags", this.tags);
			formData.append("country", this.country);
			formData.append("city", this.city);
			formData.append("address", this.address);
			formData.append("cancellationTerms", this.cancellationTerms);
			formData.append("rulesOfConduct", this.rulesOfConduct);
			formData.append("services", this.services);
			formData.append("type", this.type);
			formData.append("length", this.length);
			formData.append("capacity", this.capacity);
			formData.append("enginesNumber", this.enginesNumber);
			formData.append("enginePower", this.enginePower);
			formData.append("maxSpeed", this.maxSpeed);
			formData.append("averageScore", 0);
			console.log(formData);
			for (let index = 0; index < images.files.length; index++) {
				formData.append("images", images.files[index]);
			}
			this.$axios
				.post("/api/boat/add-boat", formData, {
					headers: {
						"Content-Type": "multipart/form-data",
					},
				})
				.then((resp) => {
					this.$router.push({ path: "/rentable/" + resp.data });
					console.log(resp);
				})
				.catch((err) => {
					console.log(err);
				});
		},
	},
};
</script>

<style>
</style>