<template>
    <div class="p-5">  
		<form id="addCottageForm">
			<div class="form-row" style="text-align:center">
				<div class="form-group col-md-12">
			<h3>File a report</h3>
				</div>
			</div>
			<div class="form-row">
				<div class="form-group col-4">
                    <b-form-group label="Request sanction" v-slot="{ first }">
                        <b-form-radio v-model="selectedSanction" :aria-describedby="first" name="some-radios" value="True">Yes</b-form-radio>
                        <b-form-radio v-model="selectedSanction" :aria-describedby="first" name="some-radios" value="False">No</b-form-radio>
                    </b-form-group>
				</div>
				<div class="form-group col-4">
                    <b-form-group label="Did the client show up?" v-slot="{ second }">
                        <b-form-radio v-model="selectedShowedUp" :aria-describedby="second" name="some-radios2" value="True">Yes</b-form-radio>
                        <b-form-radio v-model="selectedShowedUp" :aria-describedby="second" name="some-radios2" value="False">No</b-form-radio>
                    </b-form-group>
                </div>
				<div class="form-group col-4">
                    <label>Choose type of report</label>
                    <select
                    v-model="type"
                    class="form-control"
                    v-bind:class="{ 'error-boarder': $v.type.$invalid && typeClicked }"
				   @click="typeClicked = true;"
                    >
                    <option v-for="tip in types" :key="tip">{{ tip }}</option>
                    </select>
                    <ErrorDiv v-if="typeClicked" :parameter="$v.type" :name="'Type'"> </ErrorDiv>
                </div>
			</div>
            <div class="form-group">
    			<label for="exampleFormControlTextarea1">Text</label>
				<textarea class="form-control" id="exampleFormControlTextarea1" v-model="text" rows="3" placeholder="Text"
				   v-bind:class="{ 'error-boarder': $v.text.$invalid && typeClicked }"
				   @click="textClicked = true;"
				></textarea>
            	<ErrorDiv v-if="textClicked" :parameter="$v.text" :name="'Text'"> </ErrorDiv>
  			</div>
			<button type="button" @click="upload" class="btn btn-primary float-right">File report</button>
			


		</form>
	</div>
</template>

<script>
	
const countries = require('i18n-iso-countries')
countries.registerLocale(require('i18n-iso-countries/langs/en.json'))
import Vue from "vue";
import Vuelidate from "vuelidate";
import PictureUpload from './../add-adventure/PictureUpload.vue';
Vue.use(Vuelidate);
import {
  required,
  minLength,
  between,
  email,
  maxLength,
} from "vuelidate/lib/validators";
import useValidate from "@vuelidate/core";
import ErrorDiv from "./../registration/ErrorDiv.vue";
export default {
	computed: {
    countries () {
      const list = countries.getNames('en', { select: 'official' })
      return Object.keys(list).map((key) => ({ value: key, label: list[key] }))
    }
  },
  data() {
    	return {
			selected: null,
			selectedShowedUp: 'True',
			selectedSanction: 'False',
			result: null,
			text: "",
			type: "",
			textClicked: false,
			typeClicked: false,
            types: ["Report","Comment","Praise"]
		}
	},
	components: {
    ErrorDiv: ErrorDiv,
  },
  validations: {
    text: {
      required,
      minLength: minLength(2),
    },
    type: {
      required,
    }
  },
  props : ['selectedId'],
	mounted() {
		console.log("asdasd"+this.selectedId);
	},
	methods: {
		upload() {
			var formData = new FormData();
			if (this.$v.$invalid) {
				console.log(this.$v);
				$('#emptyErrorModal').modal('show');
					$("#addCottageForm").submit(function(e) {
    					e.preventDefault();
					});
					return;
			}
			formData.append("text", this.text);
			formData.append("id", this.selectedId);
			formData.append("sanction", this.selectedSanction);
			formData.append("showedUp", this.selectedShowedUp);
            console.log(this.type);
			formData.append("reservationReportType", this.type.toUpperCase());
			console.log(formData);
			this.$axios.post('/api/reservationReport/file-report', formData, {
				headers: {
					'Authorization' : 'Bearer ' + window.localStorage.getItem("JWT")
				}
			}).then((resp) => {
				this.$router.push({ path: "/ownerReservations/"});
				console.log(resp);
			}).catch((err) => {
				console.log(err);
			});
		}
	}
}
</script>

<style>

</style>