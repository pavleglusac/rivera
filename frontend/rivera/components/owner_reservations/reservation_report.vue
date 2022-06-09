<template>
  <form id="addCottageForm">
	<h4><font-awesome-icon icon="pen" /> File a report</h4>
        <b-form-group label="Should a client be sanctioned?" v-slot="{ first }">
          <b-form-radio
            v-model="selectedSanction"
            :aria-describedby="first"
            name="some-radios"
            value="True"
            >Yes</b-form-radio
          >
          <b-form-radio
            v-model="selectedSanction"
            :aria-describedby="first"
            name="some-radios"
            value="False"
            >No</b-form-radio
          >
        </b-form-group>
        <b-form-group label="Did the client show up?" v-slot="{ second }">
          <b-form-radio
            v-model="selectedShowedUp"
            :aria-describedby="second"
            name="some-radios2"
            value="True"
            >Yes</b-form-radio
          >
          <b-form-radio
            v-model="selectedShowedUp"
            :aria-describedby="second"
            name="some-radios2"
            value="False"
            >No</b-form-radio
          >
        </b-form-group>
        <label>Choose type of report</label>
        <select
          v-model="type"
          class="form-control"
          v-bind:class="{ 'error-boarder': $v.type.$invalid && typeClicked }"
          @click="typeClicked = true"
        >
          <option v-for="tip in types" :key="tip">{{ tip }}</option>
        </select>
        <ErrorDiv v-if="typeClicked" :parameter="$v.type" :name="'Type'">
        </ErrorDiv>
    <div class="form-group">
      <label for="exampleFormControlTextarea1">Report text:</label>
      <b-textarea
        class="form-control"
        id="exampleFormControlTextarea1"
        v-model="text"
        rows="3"
        v-bind:class="{ 'error-boarder': $v.text.$invalid && typeClicked }"
        @click="textClicked = true"
      ></b-textarea>
      <ErrorDiv v-if="textClicked" :parameter="$v.text" :name="'Text'">
      </ErrorDiv>
    </div>
    <b-button type="button" @click="upload" class="prime-btn float-right">
      File report
    </b-button>
  </form>
</template>

<script>
const countries = require("i18n-iso-countries");
countries.registerLocale(require("i18n-iso-countries/langs/en.json"));
import Vue from "vue";
import Vuelidate from "vuelidate";
import PictureUpload from "./../add-adventure/PictureUpload.vue";
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
    countries() {
      const list = countries.getNames("en", { select: "official" });
      return Object.keys(list).map((key) => ({ value: key, label: list[key] }));
    },
  },
  data() {
    return {
      selected: null,
      selectedShowedUp: "True",
      selectedSanction: "False",
      result: null,
      text: "",
      type: "Report",
      textClicked: false,
      typeClicked: false,
      types: ["Report", "Comment", "Praise"],
    };
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
    },
  },
  props: ["selectedId"],
  mounted() {
    console.log("asdasd" + this.selectedId);
  },
  methods: {
    upload() {
      var formData = new FormData();
      if (this.$v.$invalid) {
        console.log(this.$v);
        $("#emptyErrorModal").modal("show");
        $("#addCottageForm").submit(function (e) {
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
      this.$axios
        .post("/api/reservationReport/file-report", formData, {
          headers: {
            Authorization: "Bearer " + window.localStorage.getItem("JWT"),
          },
        })
        .then((resp) => {
          this.$router.push({ path: "/ownerReservations/" });
          console.log(resp);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style>
</style>