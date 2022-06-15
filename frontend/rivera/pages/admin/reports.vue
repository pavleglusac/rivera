<template>
	<div>
		<Navbar :currentPage="'terminations'" />
		<div
			class="d-flex flex align-items-start mt-1 parent"
			style="width: 70vw; margin-left: auto; margin-right: auto"
		>
			<b-card
				class="card mt-2 mb-5 ml-2"
				style="
					margin-top: 0;
					box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
				"
			>
				<Report
					class="h-100 child"
					:request="currentReuest"
				></Report>


				<div class="d-flex flex w-100 mt-3">
					<span class="w-50">
						<b-button @click="prevReport">Previous</b-button>
					</span>
					<span class="w-50 justify-content-end">
						<b-button @click="nextReport">Next</b-button>
					</span>
				</div>
			</b-card>
		</div>
	</div>
</template>

<script>
import Report from '@/components/admin/Report.vue';
export default {
	components: {Report}
};
</script>

<style>
</style>