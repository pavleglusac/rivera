<template>
	<div>
		<Navbar :currentPage="'reports'" />
		<div
			class="d-flex flex align-items-start mt-1 parent"
			style="width: 60vw; margin-left: auto; margin-right: auto"
		>
			<b-card
				class="card mt-2 mb-5 w-100"
				style="
					margin-top: 0;
					box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
				"
				v-if="reports.length > 0"
			>
				<Report
					class="h-100 child w-100 p-0"
					:report="currentReport"
				></Report>


				<div class="d-flex flex w-100 mt-5">
					<span class="w-50 ml-5">
						<b-button @click="prevReport">&lt; Previous</b-button>
					</span>
					<span class="w-50 mr-5 d-flex flex-row-reverse">
						<b-button @click="nextReport">Next &gt;</b-button>
					</span>
				</div>
			</b-card>
			<div v-else class="mt-4 w-100 d-flex justify-content-center">
				<h4>Currently there are no pending reports.</h4>
			</div>
		</div>
	</div>
</template>

<script>
import Report from '@/components/admin/Report.vue';
export default {
	components: {Report},
	data() {
		return {
			reports: [],
			currentReport: {
				type: '',
				showedUp: '',
				text: '',
				responseText: '',
				clientName: '',
				clientImage: '',
				ownerName: '',
				ownerImage: '',
			}
		};
	},
	mounted() {
		this.redirectAdmin();
		this.getPendingReports();	
	},
	methods: {
		getPendingReports() {
			let that = this;
			this.$axios
				.get("/api/admin/pending-reports")
				.then((resp) => {
					console.log(resp.data);
					that.reports.push(...resp.data);
					if (that.reports.length > 0)
						that.currentReport = that.reports[0];
				});
		},
		prevReport() {
			if (this.reports.length > 0) {
				let report = this.reports.pop();
				this.reports.splice(0, 0, report);
				this.currentReport = report;
			}
		},
		nextReport() {
			if (this.reports.length > 0) {
				let report = this.reports.shift();
				this.reports.push(report);
				this.currentReport = report;
			}
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
	}
};
</script>

<style>
</style>