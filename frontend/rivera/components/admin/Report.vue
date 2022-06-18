<template>
	<div class="w-100">
		<div class="w-100 d-flex">
			<span class="w-50 ml-5">
				<span class="display-6">
					<h4 style="display: inline;">
						From:
					</h4>
				</span>
				<img
					:src="ownerImage"
					alt=""
					class="z-depth-2"
					style="border-radius: 50%; object-fit: cover; max-width: 4vw;"
				/>
				<div
					class="d-inline ml-3 mr-5"
					style="font-size: 1.3em; font-weight: 500"
				>
					{{ownerName}}&nbsp;

				</div>
			</span>
			<span class="w-50 d-flex align-items-center mr-5 justify-content-end">			
				<span class="display-6">
					<h4 style="display: inline;">
						On:&nbsp;&nbsp;&nbsp;&nbsp;
					</h4>
				</span>
				<img
						:src="clientImage"
						alt=""
						class="z-depth-2"
						style="border-radius: 50%; object-fit: cover; max-width: 4vw;"
					/>
				<div
					class="d-inline ml-3"
					style="font-size: 1.3em; font-weight: 500"
				>
					{{clientName}}&nbsp;
				</div>
			</span>
		</div>
		<div class="w-100 mt-4 d-flex">
			<span class="display-6 ml-5 w-100">
				<h6 style="display: inline;">
					Type: 
				</h6>
				{{type}}
				
				<span v-if="type == 'REPORT'" class="display-6 ml-3">
					<h6 style="display: inline;">
						Showed up:
					</h6>
					{{
						showedUp ? 'Yes' : 'No'
					}}
				</span>

				<span v-if="type == 'REPORT'" class="display-6 ml-3">
					<h6 style="display: inline;">
						Requires sanction:
					</h6>
					{{
						sanction ? 'Yes' : 'No'
					}}
				</span>

			</span>
		</div>
		<div class="w-100 mt-4">
			<h6 class="ml-5">
				Text:
			</h6>	
			<b-form-textarea class="ml-5 w-75" name="" id="" readonly v-model="text">
			</b-form-textarea>
		</div>
		<div class="w-100 mt-4 ml-5">
			<b-button variant="success" @click="respondModal">Respond</b-button>
		</div>

		<b-modal ref="decline_modal" size="lg" hide-footer>
			<h3>Enter response text</h3>
			<b-form-textarea
				id="textarea-large"
				size="lg"
				placeholder=""
				v-model="responseText"
			>
			</b-form-textarea>

			<b-form-checkbox
				class="mt-5"
				id="checkbox-1"
				v-model="assignPenalty"
				name="checkbox-1"
				value=true
				unchecked-value=false
			>
      			Assign penalty
    		</b-form-checkbox>

			<b-button class="mt-5 float-right" @click="sendResponse">Send</b-button>
			
		</b-modal>

	</div>
</template>

<script>
export default {
	props: ['report'],
	data() {
		return {
			type: 'Sanction',
			showedUp: true,
			text: 'This person did show up but was rude an didnt respect the rules',
			responseText: '',
			assignPenalty: false,
			clientName: 'Client Client',
			clientImage: 'https://images.unsplash.com/photo-1520451644838-906a72aa7c86?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=550&q=80',
			ownerName: 'Owner Owner',
			ownerImage: 'https://images.unsplash.com/photo-1520451644838-906a72aa7c86?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=550&q=80',
			id: -1,
			sanction: false,
		} 
	},
	mounted() {
		this.type = this.report.type;
		this.showedUp = this.report.showedUp;
		this.text = this.report.text;
		this.clientName = this.report.clientName;
		this.clientImage = process.env.backend + this.report.clientImage;
		this.ownerName = this.report.ownerName;
		this.ownerImage = process.env.backend + this.report.ownerImage;
		this.text = this.report.text;	
		this.id = this.report.id;
		this.sanction = this.report.sanction;
	},
	methods: {
		respondModal() {
			this.$refs.decline_modal.show();
		},
		sendResponse() {
			let that = this;
			console.log(`${that.id}  ${that.responseText}  ${that.assignPenalty}`);
			this.$axios.post('/api/admin/resolve-report/', {
				reportId: that.id,
				responseText: that.responseText,
				assignPenalty: that.assignPenalty,
			}, {
				headers: {
					'Content-Type': 'application/json'
				}
			}).then(resp => {
				window.location.reload();
			});
		}
	},
	watch: {
		report(newVal, oldVal) {
			this.type = newVal.type;
			this.showedUp = newVal.showedUp;
			this.text = newVal.text;
			this.clientName = newVal.clientName;
			this.clientImage = process.env.backend + newVal.clientImage;
			this.ownerName = newVal.ownerName;
			this.ownerImage = process.env.backend + newVal.ownerImage;
			this.text = newVal.text;
			this.id = newVal.id;
			this.sanction = newVal.sanction;
		}
	}
};
</script>

<style>
</style>