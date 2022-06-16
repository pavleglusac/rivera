<template>
	<div>
		<h5><font-awesome-icon icon="calendar-check" /> Reservation</h5>
		<p>You can reserve the whole appointment or just the part of it.</p>
		<div v-if="!isClient">
			<label> Username: </label>
			<input
				type="text"
				v-model="username"
				id="inputUsername"
				placeholder="Name"
				@input="inputFunction"
				v-bind:class="{ 'error-boarder': !clientExists }"
			/>
			<b-button type="button" @click="calculateReservationPrice">
				Calculate price
			</b-button>
			<div
				v-if="!clientExists && calculateClicked"
				style="font-size: 15px; float: left"
			>
				<span><pre>            Client does not exist!</pre></span>
			</div>
		</div>
		<b-form-radio-group
			v-model="selectedAppointment"
			@change="calculateReservationPrice"
		>
			<b-form-radio id="whole-appointment" value="whole"
				>I want the whole appointment.</b-form-radio
			>
			<b-form-radio id="part-appointment" value="part"
				>I want to choose date and time.</b-form-radio
			>
		</b-form-radio-group>
		<div v-if="selectedAppointment == 'part'">
			<hr />
			<label>Choose starting time:</label>
			<b-form inline>
				<b-form-datepicker
					v-model="chosenStartDate"
					:min="appointment.start"
					:max="chosenEndDate"
					class="mr-2"
					@input="
						calculateReservationPrice;
						setTimeMinMax;
					"
					locale="en"
				></b-form-datepicker>
				<b-form-timepicker
					v-model="chosenStartTime"
					:max="maxStartTime"
					:min="minStartTime"
					@input="
						calculateReservationPrice;
						setTimeMinMax;
					"
				></b-form-timepicker>
			</b-form>
			<label class="mt-2">Choose ending time:</label>
			<b-form inline>
				<b-form-datepicker
					v-model="chosenEndDate"
					:min="chosenStartDate"
					class="mr-2"
					:max="appointment.end"
					@input="
						calculateReservationPrice;
						setTimeMinMax;
					"
					locale="en"
				></b-form-datepicker>
				<b-form-timepicker
					v-model="chosenEndTime"
					:max="maxEndTime"
					:min="minEndTime"
					@input="
						calculateReservationPrice;
						setTimeMinMax;
					"
				></b-form-timepicker>
			</b-form>
		</div>
		<hr />
		<p>Total price for your reservation: {{ price }}$</p>
		<div style="float: right">
			<b-button @click="closeThis">Cancel</b-button>
			<b-button
				@click="reserveAppointment"
				class="prime-btn"
				:disabled="!checkDone"
				>Reserve now</b-button
			>
		</div>
	</div>
</template>

<script>
export default {
	props: ["appointment", "close", "openModal"],
	data() {
		return {
			selectedAppointment: "whole",
			chosenStartDate: this.appointment.start,
			chosenStartTime: this.appointment.start.toString().split(" ")[4],
			chosenEndDate: this.appointment.end,
			chosenEndTime: this.appointment.end.toString().split(" ")[4],
			maxStartTime: "23:59",
			maxEndTime: "23:59",
			minStartTime: "00:00",
			minEndTime: "00:00",
			price: 0,
			username: "",
			isClient: false,
			clientExists: true,
			checkDone: true,
			calculateClicked: false,
			listOfClients: [],
		};
	},
	mounted() {
		console.log("POSLATO START VREME: " + this.appointment.start);
		console.log("POSLATO KRAJNJE VREME: " + this.appointment.end);
		let that = this;
		this.$axios
			.get("/api/auth/get-logged-username", {
				headers: {
					Authorization: "Bearer " + window.localStorage.getItem("JWT"),
				},
			})
			.then((resp) => {
				that.username = resp.data;
			});
		this.$axios
			.get("/api/auth/getRole", {
				headers: {
					Authorization: "Bearer " + window.localStorage.getItem("JWT"),
				},
			})
			.then((resp) => {
				if (resp.data == "ROLE_CLIENT") {
					that.isClient = true;
					that.checkDone = true;
					console.log("asdijasdkalshdkj"+this.checkDone);
					if (this.isClient) {
						this.calculateReservationPrice();
					}
				}
			});
	},
	methods: {
		inputFunction() {
			this.checkDone = false;
			document
				.getElementById("inputUsername")
				.classList.remove("error-boarder");
			this.calculateClicked = false;
		},
		getStartDateTime() {
			console.log("GETTING START DATE TIME");
			console.log(this.appointment.start);
			console.log(new Date(this.appointment.start));
			if (this.selectedAppointment == "whole") {
				return new Date(this.appointment.start);
			}
			console.log(this.chosenStartDate);
			return new Date(
				this.chosenStartDate.toISOString().slice(0, 10) +
					" " +
					this.chosenStartTime
			);
		},
		getEndDateTime() {
			if (this.selectedAppointment == "whole") {
				return new Date(this.appointment.end);
			}
			return new Date(
				this.chosenEndDate.toISOString().slice(0, 10) + " " + this.chosenEndTime
			);
		},
		datesEqual(d1, d2) {
			const date1 = new Date(d1);
			const date2 = new Date(d2);
			if (
				date1.getFullYear() === date2.getFullYear() &&
				date1.getMonth() === date2.getMonth() &&
				date1.getDate() === date2.getDate()
			)
				return true;
			return false;
		},
		isoDateWithoutTimeZone(date) {
			if (date == null) return date;
			var timestamp = date.getTime() - date.getTimezoneOffset() * 60000;
			var correctDate = new Date(timestamp);
			return correctDate.toISOString();
		},
		reserveAppointment() {
			if (!this.clientExists) {
				return;
			}
			this.close();
			var startDateTime = this.getStartDateTime();
			var endDateTime = this.getEndDateTime();
			let that = this;
			var price = this.price;
			console.log("SENDING TO BACK");
			console.log(startDateTime);
			console.log(this.isoDateWithoutTimeZone(startDateTime));
			console.log(endDateTime.toString());
			this.$axios
				.post(
					`/api/reserve?&username=${that.username}&rentableId=${
						this.$route.params.rentable
					}&start=${this.isoDateWithoutTimeZone(
						startDateTime
					)}&end=${this.isoDateWithoutTimeZone(endDateTime)}&price=${price}`
				)
				.then((response) => {
					console.log(response.data);
					this.openModal();
				});
		},
		setTimeMinMax() {
			var sameStart = this.datesEqual(
				this.chosenStartDate,
				this.appointment.start.date
			);
			var sameEnd = this.datesEqual(
				this.chosenEndDate,
				this.appointment.end.date
			);
			if (sameStart) this.minStartTime = this.appointment.start.time;
			if (sameEnd) this.maxEndTime = this.appointment.end.time;
			if (sameStart && sameEnd) {
				this.maxStartTime = this.chosenEndTime;
				this.minEndTime = this.chosenStartTime;
			}
		},
		calculateReservationPrice() {
			var startDateTime = this.getStartDateTime();
			var endDateTime = this.getEndDateTime();
			let that = this;
			this.$axios
				.get(`/api/check-if-client-exists?&username=${that.username}`, {
					headers: {
						Authorization: "Bearer " + window.localStorage.getItem("JWT"),
					},
				})
				.then((resp) => {
					that.clientExists = resp.data;
					that.calculateClicked = true;
					if (!resp.data) {
						return;
					}
					that.checkDone = true;
					this.$axios
						.get(
							`/api/get-reservation-price?&username=${
								that.username
							}&rentableId=${
								this.$route.params.rentable
							}&start=${startDateTime.toISOString()}&end=${endDateTime.toISOString()}`
						)
						.then((response) => {
							this.price = response.data;
							console.log(response.data);
						});
				});
		},
		closeThis() {
			this.close();
		},
	},
};
</script>

<style>
</style>