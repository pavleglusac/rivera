<template>
  <div style="border: 1px solid #9c9a9a; border-radius: 5%;" class="p-0 card-shadow">
	<div style="background-color: #F3F9FA; border-radius: 7%;" class="p-0">
		<div class="d-flex justify-content-center pt-4">
			<span class="align-middle p-0" style="font-size: 2em; font-weight: 500;">${{discount.price}}</span>
		</div>
		<div class="d-flex flex-column justify-content-center">
			<div class="d-flex justify-content-center">
				{{discount.start}} 
			</div>
			<div class="d-flex justify-content-center">
				<font-awesome-icon icon="arrow-down"/>
			</div>
			<div class="d-flex justify-content-center">
			 {{discount.end}}
			</div>
			<br>
		</div>
		<div class="d-flex mt-2 justify-content-center pb-3">
			<b-button class="mt-2" style="background-color: #171F30; border-radius: 10%;">reserve</b-button>
		</div>
	</div>
	<div class="pl-2 mb-3 mt-3">
		<b-form-tag  v-for="tag in discount.tags"
									:key="tag"
									class="mr-1 mt-2 d-flex">
									{{tag}}
		</b-form-tag>
	</div>
		
		
  </div>
</template>

<script>
export default {
	props: ['discount'],
	mounted() {
		this.discount.tags.splice(0, 0, 'capacity ' + this.discount.capacity + '🧍')
	},
	data() {
		return {

		}
	}
}
</script>

<style>
.card-shadow {
	box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 4px 20px 0 rgba(0, 0, 0, 0.19);
}
</style>