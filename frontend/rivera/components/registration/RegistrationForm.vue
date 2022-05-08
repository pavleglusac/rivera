<template>
  <div
    class="
      registration-form
      fixed-top
      d-flex
      align-items-center
      justify-content-center
    "
    style="bottom: 0; overflow-y: auto"
  >
    <b-card class="text-center mx-auto my-auto card mt-5">
      <img src="..\..\static\rivera_logo_hor.png" />
      <form id="registrationForm">
        <div class="form-row">
          <div class="form-group col-6">
            <label>Email</label>
            <input
              type="email"
              placeholder="Enter your email address"
              class="form-control form-control-lg"
              id="email"
              v-model="email"
              v-bind:class="{ 'error-boarder': $v.email.$invalid }"
            />
            <ErrorDiv :parameter="$v.email" :name="'Email'"> </ErrorDiv>
          </div>
          <div class="form-group col-6">
            <label>Username</label>
            <input
              type="text"
              placeholder="Enter your username"
              class="form-control form-control-lg"
              id="username"
              v-model="username"
              v-bind:class="{ 'error-boarder': $v.username.$invalid }"
            />
            <ErrorDiv :parameter="$v.username" :name="'Username'"> </ErrorDiv>
          </div>
        </div>
        <div class="form-row">
          <div class="form-group col-6">
            <label>Password</label>
            <input
              type="password"
              placeholder="Enter your password"
              class="form-control form-control-lg"
              id="password"
              v-model="password"
              v-bind:class="{ 'error-boarder': $v.password.$invalid }"
            />
            <ErrorDiv :parameter="$v.password" :name="'Password'"> </ErrorDiv>
          </div>
          <div class="form-group col-6">
            <label>Reenter password</label>
            <input
              type="password"
              placeholder="Reenter your password"
              class="form-control form-control-lg"
              id="password2"
              v-model="password2"
              v-bind:class="{ 'error-boarder': $v.password2.$invalid }"
            />
            <ErrorDiv :parameter="$v.password2" :name="'Password'"> </ErrorDiv>
          </div>
        </div>
        <div class="form-row">
          <div class="form-group col-6">
            <label>Name</label>
            <input
              type="text"
              placeholder="Enter your surname"
              class="form-control form-control-lg"
              v-model="name"
              v-bind:class="{ 'error-boarder': $v.name.$invalid }"
            />
            <ErrorDiv :parameter="$v.name" :name="'Name'"> </ErrorDiv>
          </div>
          <div class="form-group col-6">
            <label>Surname</label>
            <input
              type="text"
              placeholder="Enter your surname"
              class="form-control form-control-lg"
              id="surname"
              v-model="surname"
              v-bind:class="{ 'error-boarder': $v.surname.$invalid }"
            />
            <ErrorDiv :parameter="$v.surname" :name="'Surname'"> </ErrorDiv>
          </div>
        </div>

        <div class="form-row">
          <div class="form-group col-6">
            <label>Phone number</label>
            <input
              type="text"
              placeholder="Enter your phone number"
              class="form-control form-control-lg"
              id="phoneNumber"
              v-model="phoneNumber"
              v-bind:class="{ 'error-boarder': $v.phoneNumber.$invalid }"
            />
            <ErrorDiv :parameter="$v.phoneNumber" :name="'Phone number'">
            </ErrorDiv>
          </div>
          <div class="form-group col-6">
            <label>Address</label>
            <input
              type="text"
              placeholder="Enter your address"
              class="form-control form-control-lg"
              id="address"
              v-model="address"
              v-bind:class="{ 'error-boarder': $v.address.$invalid }"
            />
            <ErrorDiv :parameter="$v.address" :name="'Address'"> </ErrorDiv>
          </div>
        </div>
        <div class="form-row">
          <div class="form-group col-3">
            <label>Country</label>
            <select
              id="inputState"
              v-model="country"
              class="form-control"
              v-bind:class="{ 'error-boarder': $v.country.$invalid }"
            >
              <option selected>Choose...</option>
              <option
                v-for="country in countries"
                :key="country.label"
                :value="country.value"
              >
                {{ country.label }}
              </option>
            </select>
            <ErrorDiv :parameter="$v.country" :name="'Country'"> </ErrorDiv>
          </div>
          <div class="form-group col-3">
            <label>City</label>
            <input
              type="text"
              placeholder="Enter your city"
              class="form-control form-control-lg"
              id="city"
              v-model="city"
              v-bind:class="{ 'error-boarder': $v.city.$invalid }"
            />
            <ErrorDiv :parameter="$v.city" :name="'City'"> </ErrorDiv>
          </div>
          <div class="form-group col-6">
            <label>Choose your Rivera role</label>
            <select
              v-model="type"
              class="form-control"
              v-bind:class="{ 'error-boarder': $v.type.$invalid }"
            >
              <option v-for="tip in types">{{ tip }}</option>
            </select>
            <ErrorDiv :parameter="$v.type" :name="'Type'"> </ErrorDiv>
          </div>
        </div>
        <div class="form-row" v-if="this.type != 'Regular User'">
          <div class="form-group col-12">
            <label>Description</label>
            <textarea
              placeholder="Enter description"
              class="form-control form-control-lg"
              id="description"
              style="font-size: 16px;"
              v-model="description"
              v-bind:class="{ 'error-boarder': $v.description.$invalid }"
            />
            <ErrorDiv :parameter="$v.description" :name="'Description'">
            </ErrorDiv>
          </div>
        </div>

        <b-form-invalid-feedback>
          Wrong email or password. Please try again.
        </b-form-invalid-feedback>

        <b-button block id="register-btn" variant="primary" @click="register"
          >Register</b-button
        >
        <b-button
          block
          id="login-btn"
          variant="outline-primary"
          @click="changeToLogin"
          >Already have an accont? Log in</b-button
        >
      </form>
    </b-card>
  </div>
</template>


<script>
const countries = require("i18n-iso-countries");
countries.registerLocale(require("i18n-iso-countries/langs/en.json"));
import Vue from "vue";
import Vuelidate from "vuelidate";
Vue.use(Vuelidate);
import {
  required,
  minLength,
  between,
  email,
  maxLength,
} from "vuelidate/lib/validators";
import useValidate from "@vuelidate/core";
import ErrorDiv from "./ErrorDiv.vue";
export default {
  computed: {
    countries() {
      const list = countries.getNames("en", { select: "official" });
      return Object.keys(list).map((key) => ({ value: key, label: list[key] }));
    },
  },
  data() {
    return {
      v$: useValidate(),
      email: "",
      username: "",
      password: "",
      name: "",
      surname: "",
      phoneNumber: "",
      country: "",
      city: "",
      address: "",
      type: "",
      description: "",
      password2: "",
      types: ["Cottage Owner", "Boat Owner", "Regular User"],
    };
  },
  components: {
    ErrorDiv: ErrorDiv,
  },
  validations: {
    name: {
      required,
      minLength: minLength(2),
      maxLength: maxLength(20),
    },
    email: {
      required,
      email,
      minLength: minLength(2),
    },
    username: {
      required,
      minLength: minLength(2),
      maxLength: maxLength(20),
    },
    password: {
      required,
      minLength: minLength(2),
      maxLength: maxLength(20),
    },
    surname: {
      required,
      minLength: minLength(2),
      maxLength: maxLength(20),
    },
    phoneNumber: {
      required,
      minLength: minLength(2),
      maxLength: maxLength(20),
    },
    city: {
      required,
      minLength: minLength(2),
      maxLength: maxLength(20),
    },
    address: {
      required,
      minLength: minLength(2),
      maxLength: maxLength(20),
    },
    description: {
    },
    password2: {
      required,
      minLength: minLength(2),
      maxLength: maxLength(20),
    },
    type: {
      required,
    },
    country: {
      required,
    },
  },
  methods: {
    register() {
      if (this.type != "Regular User")
        this.registerOwner();
      else
        this.registerClient();
    },
    getFormData() {
      var formData = new FormData();
      formData.append("email", this.email);
      formData.append("username", this.username);
      formData.append("password", this.password);
      formData.append("name", this.name);
      formData.append("surname", this.surname);
      formData.append("phoneNumber", this.phoneNumber);
      formData.append("country", this.country);
      formData.append("city", this.city);
      formData.append("address", this.address);
      return formData;
    },
    registerOwner() {
      console.log(this.$v.$invalid);
      console.log(this.$v.name.$invalid);
      this.$v.$touch();
      if (this.$v.$invalid) {
        alert("Validation failed!");
        return;
      }
      console.log("ok");
      var formData = this.getFormData();
      formData.append("type", this.type);
      formData.append("description", this.type != "Regular User" ? this.description : '');
      this.$axios
        .post("/api/auth/signup", formData)
        .then((resp) => {
          console.log(resp);
          <b-modal>Your registration is almost done. Check out your email!</b-modal>
        })
        .catch((err) => {
          console.log(err);
        });
    },
     registerClient() {
      console.log(this.$v.$invalid);
      console.log(this.$v.name.$invalid);
      this.$v.$touch();
      if (this.$v.$invalid) {
        alert("Validation failed!");
        return;
      }
      console.log("ok");
      var formData = this.getFormData();
      this.$axios
        .post("/api/auth/signupClient", formData)
        .then((resp) => {
          console.log(resp);
          <b-modal>Your registration is almost done. Check out your email!</b-modal>
        })
        .catch((err) => {
          console.log(err);
        });
    },
    changeToLogin() {
      this.$router.push({ path: "/login" });
    },
  },
};
</script>


<style>
</style>