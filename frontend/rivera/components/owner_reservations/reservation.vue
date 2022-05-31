<template>
	<b-card img-top class="mb-2 reservation-card" no-body>
		<b-card-img
			class="reservation-img-top"
			:src="'http://localhost:8080' + reservation.rentable.pictures[0]"
		/>
		<h5 style="margin-top: -2.5rem; color: white; margin-left: 10px">
			{{ reservation.rentable.name }}
		</h5>
		<b-card-body>
			<b-card-text>
				<b-avatar
					variant="info"
					:src="'http://localhost:8080' + reservation.client.photo"
					class="mr-2"
				></b-avatar>
				<span
					@click="goToProfile(reservation.client.username)"
					style="cursor: pointer; font-weight: 500; font-size: 17px"
					class="mr-auto"
				>
					{{ reservation.client.name }} {{ reservation.client.surname }}
					<span class="text-muted"
						>@{{ reservation.client.username }}</span
					></span
				>
				<div
					v-if="!isDateAfterToday(new Date(this.reservation.endDateTime))"
					style="float: right"
				>
					<b-button
						v-if="!this.reservation.report"
						variant="primary"
						size="sm"
						class="prime-btn"
						@click="fileReport"
						>File a report</b-button
					>
					<b-button
						v-else
						variant="primary"
						size="sm"
						class="prime-btn"
						@click="viewReport"
						>View filed report</b-button
					>
				</div>
				<b-card-text style="margin-top: 10px"
					><font-awesome-icon icon="clock" />&nbsp;Start:
					{{ formatDate(new Date(reservation.startDateTime)) }} <br />
					&nbsp;&nbsp;&nbsp;&nbsp; End:
					{{ formatDate(new Date(reservation.endDateTime)) }}<br />
					<font-awesome-icon icon="credit-card" />&nbsp;Reservation cost:
					{{ reservation.price }}$</b-card-text
				>
			</b-card-text>
		</b-card-body>
	</b-card>
</template>

<script>
import ReservationReport from "./../owner_reservations/reservation_report";
import ViewReservationReport from "./../owner_reservations/view_reservation_report.vue";
export default {
	props: [
		'reservation',
		'reportModal','viewReportModal'
	],
	components: {
    ReservationReport: ReservationReport,
    ViewReservationReport: ViewReservationReport,
  },
	mounted(){
		console.log("-----------------------------------"+this.reservation);
		for (var x in this.reservation){
			console.log(x);
		}
	},
	methods: {
		goToProfile(id) {
			this.$router.push({ path: "/profile/" + id });
		},
		goToClient() {
			this.$router.push({
				path: "/profile/" + this.reservation.client.username,
			});
		},
		formatDate(date) {
			var hours = date.getHours();
			var minutes = date.getMinutes();
			var ampm = hours >= 12 ? "pm" : "am";
			hours = hours % 12;
			hours = hours ? hours : 12;
			minutes = minutes < 10 ? "0" + minutes : minutes;
			var strTime = hours + ":" + minutes + " " + ampm;
			return (
				date.toLocaleDateString("en-US", {
					weekday: "long",
					year: "numeric",
					month: "long",
					day: "numeric",
				}) +
				" at " +
				strTime
			);
		},
    fileReport() {
		this.reportModal(this.reservation.reservationId);
      //this.$router.push({ path: "/addReport/" + this.reservation.id });
    },
    viewReport() {
		this.viewReportModal(this.reservation.reservationId);
      //this.$bvModal.show("add_modal2");
      //this.$router.push({ path: "/viewReport/" + this.reservation.id });
    },
		isDateAfterToday(date) {
			return (
				new Date(date.toDateString()) > new Date(new Date().toDateString())
			);
		},
	},
	data() {
		return {};
	},
};
</script>

<style>
.reservation-card {
	box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 5px 0 rgba(0, 0, 0, 0.19);
}
.reservation-img-top {
	max-height: 100px;
	object-fit: cover;
	margin: 0;
}
</style>