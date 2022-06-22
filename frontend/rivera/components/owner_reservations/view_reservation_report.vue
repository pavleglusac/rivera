<template>
	<div
	>
		<h3><font-awesome-icon icon="pen"/> Report</h3>
			<form id="registrationForm">
				<div class="form-row">
					<div class="form-group col-4">
						<label>Sanction</label>
						<b-input
							id="sanction"
							v-model="sanction"
							disabled
						/>
					</div>
					<div class="form-group col-4">
						<label>Showed up</label>
						<b-input
							id="showedUp"
							v-model="showedUp"
							disabled
						/>
					</div>
					<div class="form-group col-4">
						<label>Report type</label>
						<b-input
							id="type"
							v-model="type"
							disabled
						/>
					</div>
				</div>
				<div class="form-row">
					<div class="form-group col-12">
						<label>Text</label>
						<b-textarea
							placeholder="Enter text"
							id="text"
							v-model="text"
							disabled
						/>
					</div>
				</div>
			</form>	
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
  props : ["selectedId"],
	mounted() {
		let that = this;
		this.$axios
			.get(
				"/api/reservationReport/get-report?id=" + that.selectedId
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