<template>
	<div>
		<Navbar :currentPage="'terminations'" />
		<div
			class="d-flex flex justify-content-center mt-1 parent w-100"
			style="width: 70vw; margin-left: auto; margin-right: auto"
		>
			

			<b-card
				class="card mt-2 mb-3"
				style="
					margin-top: 0;
					box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
				"
			>
				<div>
					<termination-request
						class="h-100 child"
						:request="currentReuest"
					></termination-request>

					<div class="d-flex flex w-100 justify-content-end mt-3">
						<b-button @click="nextUsername">Next</b-button>
					</div>
				</div>
			</b-card>
		</div>
	</div>
</template>

<script>
import TerminationRequest from "@/components/client/TerminationRequest.vue";
export default {
	components: { TerminationRequest },
	data() {
		return {
			totalRequests: 1389,
			activeAccounts: 28,
			terminatedAccounts: 6,
			terminationRequests: [],
			currentReuest: { username: "", reason: "" },
		};
	},
	mounted() {
		this.redirectAdmin();
		this.getToTerminateUsernames();
		this.getStats();
	},
	methods: {
		getToTerminateUsernames() {
			let that = this;
			this.$axios
				.get("/api/admin/pending-termination-requests")
				.then((resp) => {
					console.log(resp.data);
					that.terminationRequests.push(...resp.data);
					if (that.terminationRequests.length > 0)
						that.currentReuest = that.terminationRequests[0];
				});
		},
		nextUsername() {
			if (this.terminationRequests.length > 0) {
				let ko = this.terminationRequests.shift();
				this.terminationRequests.push(ko);
				this.currentReuest = this.terminationRequests[0];
			}
		},
		getStats() {
			let that = this;
			// this.$axios.get("/api/admin/terminaiton-stats").then((resp) => {
			// 	console.log(resp.data);
			// 	that.activeAccounts =
			// 		typeof resp.data.ACTIVE !== "undefined"
			// 			? resp.data.ACTIVE
			// 			: 0;
			// 	that.totalRequests =
			// 		typeof resp.data.WAITING !== "undefined"
			// 			? resp.data.WAITING
			// 			: 0;
			// 	that.terminatedAccounts =
			// 		typeof resp.data.TERMINATED !== "undefined"
			// 			? resp.data.TERMINATED
			// 			: 0;
			// });
		},
		redirectAdmin() {
			this.$axios
				.get("/api/auth/logged-user-info")
				.then((resp) => {
					console.log(resp.data);
					if (resp.data != null) {
						if (
							resp.data.role == "ROLE_ADMIN" &&
							resp.data.lastPasswordChange == null
						) {
							this.$router.push({ path: "/admin/profile" });
						}
					}
				})
				.catch((err) => {
					console.log(err);
				});
		},
	},
};
</script>

<style scoped>
.parent >>> .child label {
	float: left;
	font-size: 16px;
}
</style>