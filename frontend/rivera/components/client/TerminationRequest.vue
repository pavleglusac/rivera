<template>
	<div
		
		style="bottom: 0; overflow-y: auto"
	>
		<b-card class="text-center mx-auto my-auto card mt-5">
			<img src="@/static/rivera_logo_hor.png" style="height: 100px;" />
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
							disabled
						/>
					</div>
					<div class="form-group col-6">
						<label>Username</label>
						<input
							type="text"
							placeholder="Enter your username"
							class="form-control form-control-lg"
							id="username"
							v-model="username"
							disabled
						/>
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
							disabled
						/>
					</div>
					<div class="form-group col-6">
						<label>Surname</label>
						<input
							type="text"
							placeholder="Enter your surname"
							class="form-control form-control-lg"
							id="surname"
							v-model="surname"
							disabled
						/>
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
							disabled
						/>
					</div>
					<div class="form-group col-6">
						<label>Address</label>
						<input
							type="text"
							placeholder="Enter your address"
							class="form-control form-control-lg"
							id="address"
							v-model="address"
							disabled
						/>
					</div>
				</div>
				<div class="form-row">
					<div class="form-group col-6">
						<label>Country</label>
						<input
							type="text"
							placeholder="Enter your country"
							class="form-control form-control-lg"
							id="country"
							v-model="country"
							disabled
						/>
					</div>
					<div class="form-group col-6">
						<label>City</label>
						<input
							type="text"
							placeholder="Enter your city"
							class="form-control form-control-lg"
							id="city"
							v-model="city"
							disabled
						/>
					</div>
				</div>
				<div class="form-row">
					<div class="form-group col-12">
						<label>Reason</label>
						<textarea
							placeholder="Enter description"
							class="form-control form-control-lg"
							id="description"
							v-model="description"
							disabled
						/>
					</div>
				</div>

				<b-form-invalid-feedback>
					Wrong email or password. Please try again.
				</b-form-invalid-feedback>

				<b-modal ref="decline_modal" size="xl" hide-footer>
					<h3>Enter reason for declination</h3>
					<b-form-textarea
						id="textarea-large"
						size="lg"
						placeholder=""
						v-model="reason"
						v-bind:class="{ 'error-boarder': $v.reason.$invalid }"
					>
					</b-form-textarea>
					<ErrorDiv :parameter="$v.reason" :name="'Reason'"> </ErrorDiv>
					<b-button class="mt-5 float-right" @click="send_decline"
						>Send</b-button
					>
				</b-modal>

				<div>
					<b-button block id="login-btn" variant="primary" @click="accept"
						>Accept</b-button
					>
					<b-button
						block
						id="signup-btn"
						variant="outline-primary"
						@click="decline"
						>Decline</b-button
					>
				</div>
			</form>
		</b-card>
	</div>
</template>
<script>
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
import ErrorDiv from "../registration/ErrorDiv.vue";

export default {
	props: ["request"],
	data() {
		return {
			email: "",
			username: "",
			password: "",
			name: "",
			surname: "",
			services: "",
			phoneNumber: "",
			country: "",
			city: "",
			address: "",
			type: "",
			description: "",
			status: "",
			reason: "",
		};
	},
	mounted() {
		this.getUser();
	},
	components: {
		ErrorDiv: ErrorDiv,
	},
	validations: {
		reason: {
			required,
			minLength: minLength(2),
		},
	},
	methods: {
		accept() {
			this.$axios
				.post(
					`/api/admin/resolve-termination?username=${this.request.username}&requestId=${this.request.requestId}&accept=true`
				)
				.then((resp) => {
					console.log("Uspesno");
					window.location.reload();
				})
				.catch((err) => {
					console.log(err);
					alert("Couldn't proccess termination.");
					window.location.reload();
				});
		},
		decline() {
			this.$refs.decline_modal.show();
		},
		send_decline() {
			this.$axios
				.post(
					`/api/admin/resolve-termination?username=${this.request.username}&requestId=${this.request.requestId}&reason=${this.reason}&accept=false`
				)
				.then((resp) => {
					console.log("Uspesno");
					window.location.reload();
				})
				.catch((err) => {
					console.log(err);
					alert("Couldn't proccess termination.");
					window.location.reload();
				});
		},
		getUser() {
			let that = this;
			this.$axios
				.get("/api/person-by-username?username=" + this.request.username)
				.then((resp) => {
					let owner = resp.data;
					that.email = owner.email;
					that.username = owner.username;
					that.password = owner.password;
					that.name = owner.name;
					that.surname = owner.surname;
					that.services = owner.services;
					that.phoneNumber = owner.phoneNumber;
					that.country = owner.country;
					that.city = owner.city;
					that.address = owner.address;
					that.status = "ACTIVE";
				})
				.catch((err) => {
					console.log(err);
				});
            this.description = this.request.description;
		},
        getTerminationRequest() {

        },
	},
	watch: {
		request: function(newVal, oldVal) {
			console.log(newVal);
			this.getUser();
		}
	}
};
</script>