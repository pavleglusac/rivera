<template>
	<div class="mt-5">
		<div class="float-left mr-1" v-if="logged || isClient || (owner && isOwner)">
			<b-button
				v-if="owner && isOwner"
				size="sm"
				class="btn btn-light"
				style="font-weight: 500"
				@click="viewReservations"
			>
				<font-awesome-icon icon="calendar-check" /> View reservations</b-button
			>
			<b-button
				v-if="owner && isOwner"
				size="sm"
				class="btn btn-light"
				style="font-weight: 500"
				:disabled="!canBeChanged"
				@click="updateRentable"
			>
				<font-awesome-icon icon="pen-to-square" /> Update</b-button
			>
			<b-button
				v-if="owner && isOwner"
				size="sm"
				class="btn btn-light"
				style="font-weight: 500"
				:disabled="!canBeChanged"
				@click="deleteRentable"
			>
				<font-awesome-icon icon="trash" /> Delete</b-button
			>
			<b-button
				size="sm"
				v-if="!isSubscribed && isClient"
				class="subscribe-btn"
				@click="subscribe"
			>
				+ Subscribe
			</b-button>
			<b-button
				size="sm"
				v-else-if="isClient"
				class="subscribe-btn"
				@click="unsubscribe"
			>
				- Unsubscribe
			</b-button>
		</div>
		<div v-else style="margin-top: -10px"></div>
		<div v-if="isClient || (owner && isOwner)"><br/><br/></div>
		<b-modal
			id="update_modal"
			title="Update data"
			ref="update_modal"
			size="xl"
			hide-footer
			hide-header
		>
			<div v-if="currentType == 'adventure'">
				<EditAdventure />
			</div>
			<div v-if="currentType == 'boat'">
				<EditBoat />
			</div>
			<div v-if="currentType == 'cottage'">
				<EditCottage />
			</div>
		</b-modal>

		<div
			class="modal fade"
			id="deleteModal"
			tabindex="-1"
			role="dialog"
			aria-labelledby="exampleModalLabel"
			aria-hidden="true"
		>
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">
							Confirm delete dialog
						</h5>
						<button
							type="button"
							class="close"
							data-dismiss="modal"
							aria-label="Close"
						>
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">Are you sure you want to proceed?</div>
					<div class="modal-footer">
						<button
							type="button"
							class="btn btn-primary"
							id="roomUpdate"
							@click="confirmDeletion()"
						>
							Yes
						</button>
						<button
							type="button"
							class="btn btn-secondary"
							id="modalClosure"
							@click="closeModal('deleteModal')"
						>
							No
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import EditBoat from "../add_boat/EditBoat.vue";
import EditCottage from "../add_cottage/EditCottage.vue";
import EditAdventure from "./../add-adventure/EditAdventure.vue";
export default {
	components: { EditAdventure, EditBoat, EditCottage },
	data() {
		return {
			isSubscribed: false,
			isClient: true,
			logged: true,
			owner: true,
		};
	},
	mounted() {
		this.subscribed();
	},
	methods: {
		deleteRentable() {
			$("#deleteModal").modal("show");
		},
		confirmDeletion() {
			var temp = "/" + this.currentType;
			let that = this;
			if (temp == "/adventure") {
				temp = "";
			}
			this.$axios
				.post(
					`/api${temp}/delete-${that.currentType}?id=` +
						this.$route.params.rentable
				)
				.then((resp) => {
					console.log(resp);
				})
				.catch((err) => {
					console.log(err);
				});
			this.closeModal("deleteModal");
		},
		viewReservations() {
			this.openReservationsModal();
		},
		closeModal(id) {
			$("#" + id).modal("hide");
		},
		updateRentable() {
			this.$refs.update_modal.show();
		},
		subscribed() {
			let that = this;
			this.$axios
				.get("/api/auth/get-logged-username", {
					headers: {
						Authorization: "Bearer " + window.localStorage.getItem("JWT"),
					},
				})
				.then((resp) => {
					if (resp.data === "") {
						that.logged = false;
						that.isClient = false;
					} else {
						var username = resp.data;
						this.$axios
							.get("/api/auth/getRoleByUsername?username=" + username)
							.then((r) => {
								if (r.data == "CLIENT") {
									that.client = true;
									that.owner = false;
									this.$axios
										.post(
											"/api/is-subscribed?username=" +
												username +
												"&id=" +
												this.$route.params.rentable
										)
										.then((response) => {
											that.isSubscribed = response.data;
										});
								} else {
									this.isClient = false;
									this.owner = true;
								}
							});
					}
				});
		},
		subscribe() {
			let that = this;
			this.$axios
				.get("/api/auth/get-logged-username", {
					headers: {
						Authorization: "Bearer " + window.localStorage.getItem("JWT"),
					},
				})
				.then((resp) => {
					this.$axios
						.post(
							"/api/subscribe?username=" +
								resp.data +
								"&id=" +
								this.$route.params.rentable
						)
						.then((resp) => {
							that.isSubscribed = true;
						})
						.catch((err) => {
							console.log(err);
						});
				});
		},
		unsubscribe() {
			let that = this;
			this.$axios
				.get("/api/auth/get-logged-username", {
					headers: {
						Authorization: "Bearer " + window.localStorage.getItem("JWT"),
					},
				})
				.then((resp) => {
					this.$axios
						.post(
							"/api/unsubscribe?username=" +
								resp.data +
								"&id=" +
								this.$route.params.rentable
						)
						.then((resp) => {
							that.isSubscribed = false;
						})
						.catch((err) => {
							console.log(err);
						});
				});
		},
	},
	props: [
		"location",
		"name",
		"score",
		"description",
		"canBeChanged",
		"currentType",
		"openReservationsModal",
		"isOwner",
	],
};
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Noto+Sans:wght@400&display=swap");
@import url("https://fonts.googleapis.com/css2?family=Rubik:wght@600&display=swap");
#desc {
	font-family: "Noto Sans", sans-serif;
}

#name {
	font-family: "Rubik", sans-serif;
}

.subscribe-btn {
	background-color: var(--prime-color);
	color: #fff;
	border: none;
	font-weight: 500;
}
.subscribe-btn:hover {
	opacity: 0.9;
	background-color: var(--prime-color);
}
</style>