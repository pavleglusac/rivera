<template>
	<div
		class="
			admin-registration-form
			d-flex
			align-items-center
			justify-content-center
		"
		style=""
	>
		<b-card class="text-center mx-auto my-auto card mt-5">
			<img src="..\..\static\rivera_logo_hor.png" />
			<form id="registrationForm">
				<div class="form-row">
					<div class="form-group col-6">
						<label>Email</label>
						<input
							type="email"
							placeholder="Enter your email address"
							class="form-control form-control-lg"
							id="email"
							v-model="email"
							v-bind:class="{
								'error-boarder': $v.email.$invalid && emailClicked,
							}"
							@click="emailClicked = true"
						/>
						<ErrorDiv v-if="emailClicked" :parameter="$v.email" :name="'Email'">
						</ErrorDiv>
					</div>
					<div class="form-group col-6">
						<label>Username</label>
						<input
							type="text"
							placeholder="Enter your username"
							class="form-control form-control-lg"
							id="username"
							v-model="username"
							@keyup="resetTimer"
							v-bind:class="{
								'error-boarder':
									($v.username.$invalid || usernameExists) && usernameClicked,
							}"
							@click="usernameClicked = true"
						/>
						<div>
							<b-spinner
								small
								label="Small Spinner"
								style="float: left"
								class="m-1"
								v-if="loadingUsername"
							></b-spinner>
							<div v-if="usernameClicked">
								<span v-if="usernameExists"> Username already exists! </span>
								<ErrorDiv v-else :parameter="$v.username" :name="'  Username'">
								</ErrorDiv>
							</div>
						</div>
					</div>
				</div>
				<div class="form-row">
					<div class="form-group col-6">
						<label>Password</label>
						<input
							type="password"
							placeholder="Enter your password"
							class="form-control form-control-lg"
							id="password"
							v-model="password"
							v-bind:class="{
								'error-boarder': $v.password.$invalid && passwordClicked,
							}"
							@click="passwordClicked = true"
						/>
						<ErrorDiv
							v-if="passwordClicked"
							:parameter="$v.password"
							:name="'Password'"
						>
						</ErrorDiv>
					</div>
					<div class="form-group col-6">
						<label>Reenter password</label>
						<input
							type="password"
							placeholder="Reenter your password"
							class="form-control form-control-lg"
							id="password2"
							v-model="password2"
							v-bind:class="{
								'error-boarder': $v.password2.$invalid && password2Clicked,
							}"
							@click="password2Clicked = true"
						/>
						<ErrorDiv
							v-if="password2Clicked"
							:parameter="$v.password2"
							:name="'Password'"
						>
						</ErrorDiv>
					</div>
				</div>
				<div class="form-row">
					<div class="form-group col-6">
						<label>Name</label>
						<input
							type="text"
							placeholder="Enter your surname"
							class="form-control form-control-lg"
							v-model="name"
							v-bind:class="{
								'error-boarder': $v.name.$invalid && nameClicked,
							}"
							@click="nameClicked = true"
						/>
						<ErrorDiv v-if="nameClicked" :parameter="$v.name" :name="'Name'">
						</ErrorDiv>
					</div>
					<div class="form-group col-6">
						<label>Surname</label>
						<input
							type="text"
							placeholder="Enter your surname"
							class="form-control form-control-lg"
							id="surname"
							v-model="surname"
							v-bind:class="{
								'error-boarder': $v.surname.$invalid && surnameClicked,
							}"
							@click="surnameClicked = true"
						/>
						<ErrorDiv
							v-if="surnameClicked"
							:parameter="$v.surname"
							:name="'Surname'"
						>
						</ErrorDiv>
					</div>
				</div>

				<div class="form-row">
					<div class="form-group col-6">
						<label>Phone number</label>
						<input
							type="text"
							placeholder="Enter your phone number"
							class="form-control form-control-lg"
							id="phoneNumber"
							v-model="phoneNumber"
							v-bind:class="{
								'error-boarder': $v.phoneNumber.$invalid && phoneNumberClicked,
							}"
							@click="phoneNumberClicked = true"
						/>
						<ErrorDiv
							v-if="phoneNumberClicked"
							:parameter="$v.phoneNumber"
							:name="'Phone number'"
						>
						</ErrorDiv>
					</div>
					<div class="form-group col-6">
						<label>Address</label>
						<input
							type="text"
							placeholder="Enter your address"
							class="form-control form-control-lg"
							id="address"
							v-model="address"
							v-bind:class="{
								'error-boarder': $v.address.$invalid && addressClicked,
							}"
							@click="addressClicked = true"
						/>
						<ErrorDiv
							v-if="addressClicked"
							:parameter="$v.address"
							:name="'Address'"
						>
						</ErrorDiv>
					</div>
				</div>
				<div class="form-row">
					<div class="form-group col-3">
						<label>Country</label>
						<select
							id="inputState"
							v-model="country"
							class="form-control"
							v-bind:class="{
								'error-boarder': $v.country.$invalid && countryClicked,
							}"
							@click="countryClicked = true"
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
					<div class="form-group col-3">
						<label>City</label>
						<input
							type="text"
							placeholder="Enter your city"
							class="form-control form-control-lg"
							id="city"
							v-model="city"
							v-bind:class="{
								'error-boarder': $v.city.$invalid && cityClicked,
							}"
							@click="cityClicked = true"
						/>
						<ErrorDiv v-if="cityClicked" :parameter="$v.city" :name="'City'">
						</ErrorDiv>
					</div>
				</div>

				<b-button block id="register-btn" variant="primary" @click="register"
					>Register</b-button
				>
				<b-spinner
					variant="success"
					v-if="emailSending"
					class="spinning m-2"
				></b-spinner>
				<b-modal id="success" hide-footer>
					<div class="d-block text-center">
						<h4>Success</h4>
					</div>
				</b-modal>
			</form>
		</b-card>
	</div>
</template>


<script>
const countries = require("i18n-iso-countries");
countries.registerLocale(require("i18n-iso-countries/langs/en.json"));
import Vue from "vue";
import Vuelidate from "vuelidate";
Vue.use(Vuelidate);
import {
	required,
	minLength,
	between,
	email,
	maxLength,
	sameAs,
} from "vuelidate/lib/validators";
import useValidate from "@vuelidate/core";
import ErrorDiv from "./ErrorDiv.vue";
export default {
	mounted() {},
	computed: {
		countries() {
			const list = countries.getNames("en", { select: "official" });
			return Object.keys(list).map((key) => ({ value: key, label: list[key] }));
		},
	},
	data() {
		return {
			v$: useValidate(),
			email: "",
			username: "",
			password: "",
			name: "",
			surname: "",
			phoneNumber: "",
			country: "",
			city: "",
			address: "",
			password2: "",
			emailClicked: false,
			usernameClicked: false,
			passwordClicked: false,
			nameClicked: false,
			surnameClicked: false,
			phoneNumberClicked: false,
			countryClicked: false,
			cityClicked: false,
			addressClicked: false,
			typeClicked: false,
			descriptionClicked: false,
			password2Clicked: false,
			usernameExists: false,
			typingTimer: "",
			doneTypingInterval: 500,
			loadingUsername: false,
			emailSending: false,
		};
	},
	components: {
		ErrorDiv: ErrorDiv,
	},
	validations: {
		name: {
			required,
			minLength: minLength(2),
			maxLength: maxLength(20),
		},
		email: {
			required,
			email,
			minLength: minLength(2),
		},
		username: {
			required,
			minLength: minLength(2),
			maxLength: maxLength(20),
		},
		password: {
			required,
			minLength: minLength(2),
			maxLength: maxLength(20),
		},
		surname: {
			required,
			minLength: minLength(2),
			maxLength: maxLength(20),
		},
		phoneNumber: {
			required,
			minLength: minLength(2),
			maxLength: maxLength(20),
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
		password2: {
			required,
			minLength: minLength(2),
			maxLength: maxLength(20),
			sameAsPassword: sameAs("password"),
		},
		country: {
			required,
		},
	},
	methods: {
		register() {
			this.registerAdmin();
		},
		getFormData() {
			var formData = new FormData();
			formData.append("email", "mrsisatim20+" + this.email);
			formData.append("username", this.username);
			formData.append("password", this.password);
			formData.append("name", this.name);
			formData.append("surname", this.surname);
			formData.append("phoneNumber", this.phoneNumber);
			formData.append("country", this.country);
			formData.append("city", this.city);
			formData.append("address", this.address);
			console.log(this.type);
			return formData;
		},
		registerAdmin() {
			this.emailSending = true;
			this.$v.$touch();
			if (this.$v.$invalid) {
				alert("Validation failed!");
				return;
			}
			var formData = this.getFormData();
			this.$axios
				.post("/api/auth/signupAdmin", formData)
				.then((resp) => {
					this.$bvModal.show("success");
					this.emailSending = false;
				})
				.catch((err) => {
					console.log(err);
				});
		},
		doneTyping() {
			console.log(username.value);
			$("#username").addClass("ok-border");
			this.$axios
				.get("/api/auth/check-if-username-exists?username=" + username.value)
				.then((resp) => {
					this.loadingUsername = false;
					this.usernameExists = resp.data;
				})
				.catch((err) => {
					console.log(err);
				});
		},
		setTrue(parameter) {
			parameter = true;
		},
		resetTimer() {
			clearTimeout(this.typingTimer);
			this.usernameExists = false;
			this.loadingUsername = true;
			$("#username").removeClass("ok-border");
			if ($("#username").val()) {
				this.typingTimer = setTimeout(this.doneTyping, this.doneTypingInterval);
			}
		},
	},
	watch: {},
};
</script>


<style scoped>
.ok-border {
	border-color: #16c79a;
}

.admin-registration-form .card {
	border-radius: 20;
	filter: drop-shadow(0px 2px 3px);
	float: none;
	min-width: 60rem;
	padding: 10px;
}

.admin-registration-form .card img {
	height: 100px;
}

.admin-registration-form #register-btn {
	background-color: var(--prime-color);
	border: none;
}

.admin-registration-form #login-btn {
	border: 2px solid var(--prime-color);
	color: var(--prime-color);
}

.admin-registration-form #login-btn:hover {
	background-color: var(--prime-color);
	color: white;
}

.admin-registration-form label {
	float: left;
	font-size: 16px;
}

.admin-registration-form input[type="email"],
.admin-registration-form input[type="text"],
select,
.admin-registration-form input[type="password"],
.admin-registration-form text-area {
	font-size: 15px;
}
</style>