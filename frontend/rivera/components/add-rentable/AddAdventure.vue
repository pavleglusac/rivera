<template>
    <div class="p-5 mt-3">  
		<form>
			<div class="form-row">
				<div class="form-group col-md-4">
				<label for="name">Name</label>
				<input type="email" class="form-control" id="inputEmail4" placeholder="Name">
				</div>

				<div class="form-group col-md-4">
				<label for="name">Capacity</label>
				<input type="number" class="form-control"  id="capacity" name="capacity" min="1" max="100" placeholder="Capacity">
				</div>

				<div class="form-group col-md-2">
				<label for="name">Price per hour</label>
				<input type="number" class="form-control"  id="capacity" name="perHour" min="0" placeholder="Price per hour">
				</div>

				<div class="form-group col-md-2">
				<label for="name">Price per day</label>
				<input type="number" class="form-control"  id="capacity" name="perDay" min="0" placeholder="Price per day">
				</div>
			</div>
			<div class="form-group">
    			<label for="exampleFormControlTextarea1">Description</label>
				<textarea class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
  			</div>
			<div class="form-row">
				<div class="form-group col-6">
					<b-form-tags input-id="tags-basic" v-model="tags" placeholder="Tags"></b-form-tags>
				</div>
				<div class="form-group col-6">
					<b-form-tags input-id="tags-basic" v-model="rulesOfConduct" placeholder="Rules of conduct"></b-form-tags>
				</div>
			</div>
			<div class="form-row">
				<div class="form-group col-6">
					<b-form-tags input-id="tags-basic" v-model="equipment" placeholder="Equipment"></b-form-tags>
				</div>
				<div class="form-group col-6">
					<b-form-tags input-id="tags-basic" v-model="services" placeholder="Additional services"></b-form-tags>
				</div>
			</div>
			<div class="form-row">
				<div class="form-group col-4">
					<label for="inputAddress">Address</label>
					<input type="text" class="form-control" id="inputAddress" placeholder="1234 Main St">
				</div>
				<div class="form-group col-4">
					<label for="inputCity">City</label>
					<input type="text" class="form-control" id="inputCity">
				</div>
				<div class="form-group col-4">
					<label for="inputState">State</label>
					<select id="inputState" class="form-control">
						<option selected>Choose...</option>
						<option v-for="country in countries" :key="country.label" :value="country.value">
						{{ country.label }}
						</option>
					</select>
				</div>
			</div>
			<div class="d-flex">
				<PictureUpload />
			</div>
			<button type="submit" class="btn btn-primary float-right">Add adventure</button>
		</form>
	</div>
</template>

<script>
const countries = require('i18n-iso-countries')
countries.registerLocale(require('i18n-iso-countries/langs/en.json'))
import PictureUpload from './PictureUpload.vue';
export default {
	components: {PictureUpload},
	computed: {
    countries () {
      const list = countries.getNames('en', { select: 'official' })
      return Object.keys(list).map((key) => ({ value: key, label: list[key] }))
    }
  },
  data () {
    	return {
			selected: null,
			result: null,
			tags: [],
			rulesOfConduct: [],
			equipment: [],
			services: []
		}
	},
	mounted() {
		
	}
}
</script>

<style>

</style>