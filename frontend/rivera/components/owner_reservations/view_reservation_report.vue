<template>
	<div
		class="
			registration-form
			fixed-top
			d-flex
			align-items-center
			justify-content-center
		"
		style="bottom: 0; overflow-y: auto"
	>
		<b-card class="text-center mx-auto my-auto card mt-5">
			<img src="..\..\static\rivera_logo_hor.png" />
			<form id="registrationForm">
				<div class="form-row">
					<div class="form-group col-4">
						<label>Sanction</label>
						<input
							type="text"
							class="form-control form-control-lg"
							id="sanction"
							v-model="sanction"
							disabled
						/>
					</div>
					<div class="form-group col-4">
						<label>Showed up</label>
						<input
							type="text"
							class="form-control form-control-lg"
							id="showedUp"
							v-model="showedUp"
							disabled
						/>
					</div>
					<div class="form-group col-4">
						<label>Report type</label>
						<input
							type="text"
							class="form-control form-control-lg"
							id="type"
							v-model="type"
							disabled
						/>
					</div>
				</div>
				<div class="form-row">
					<div class="form-group col-12">
						<label>Text</label>
						<textarea
							placeholder="Enter text"
							class="form-control form-control-lg"
							id="text"
							v-model="text"
							disabled
						/>
					</div>
				</div>
			</form>
		</b-card>
	</div>
</template>
<script>
import Vue from "vue";
import Vuelidate from "vuelidate";
Vue.use(Vuelidate);
export default {
	data() {
		return {
			text: "",
			sanction: "",
			showedUp: "",
			type: "",
		};
	},
	mounted() {
		let that = this;
		this.$axios
			.get(
				"/api/reservationReport/get-report?id=" + this.$route.params.viewReport
			)
			.then((resp) => {
				let data = resp.data;
				that.text = data.text;
				console.log(data);
				that.showedUp = data.showedUp;
				that.sanction = data.sanction;
				that.type = data.reservationReportType;
				console.log(data.text);
			})
			.catch((err) => {
				console.log(err);
			});
	},
};
</script>