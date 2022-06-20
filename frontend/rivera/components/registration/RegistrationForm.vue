<template>
  <div class="register-photo">
    <div class="form-container" id="register-form-container">
      <form>
        <label style="font-size: 20px; margin-bottom: 20px; font-weight: 500"
          >Registrate to Rivera</label
        >

        <!--FIRST TAB-->
        <div class="tab">
          <label>Please enter your login credentials.</label>
          <div style="height: 70px">
            <input
              type="email"
              placeholder="Enter your email address"
              class="form-control"
              id="email"
              v-model="email"
              v-bind:class="{
                'error-boarder': $v.email.$invalid && emailClicked,
              }"
              @click="emailClicked = true"
            />
            <ErrorDiv
              v-if="emailClicked"
              :parameter="$v.email"
              :name="'Email'"
            />
          </div>
          <div style="height: 70px">
            <input
              type="text"
              placeholder="Enter your username"
              class="form-control"
              id="username"
              v-model="username"
              @keyup="resetTimer"
              v-bind:class="{
                'error-boarder':
                  ($v.username.$invalid || usernameExists) && usernameClicked,
              }"
              @click="usernameClicked = true"
            />
            <b-spinner
              small
              label="Small Spinner"
              style="float: left"
              class="m-1"
              v-if="loadingUsername"
            ></b-spinner>
            <div v-if="usernameClicked">
              <span
                v-if="usernameExists"
                style="font-size: 12px; float: left; color: #dc143c"
              >
                Username already exists!
              </span>
              <ErrorDiv v-else :parameter="$v.username" :name="'  Username'" />
            </div>
          </div>
          <div style="height: 70px">
            <input
              type="password"
              placeholder="Enter your password"
              class="form-control"
              id="password"
              v-model="password"
              v-bind:class="{
                'error-boarder': $v.password.$invalid && passwordClicked,
              }"
              @click="passwordClicked = true"
            />
            <ErrorDiv
              v-if="passwordClicked"
              :parameter="$v.password"
              :name="'Password'"
            />
          </div>
          <div style="height: 70px">
            <input
              type="password"
              placeholder="Confirm your password"
              class="form-control"
              id="password2"
              v-model="password2"
              v-bind:class="{
                'error-boarder': $v.password2.$invalid && password2Clicked,
              }"
              @click="password2Clicked = true"
            />
            <ErrorDiv
              v-if="password2Clicked"
              :parameter="$v.password2"
              :name="'Password'"
            />
          </div>
        </div>

        <!--SECOND TAB-->
        <div class="tab">
          <label>Your primary data.</label>
          <div style="height: 70px">
            <input
              type="text"
              placeholder="Enter your name"
              class="form-control"
              v-model="name"
              v-bind:class="{
                'error-boarder': $v.name.$invalid && nameClicked,
              }"
              @click="nameClicked = true"
            />
            <ErrorDiv v-if="nameClicked" :parameter="$v.name" :name="'Name'" />
          </div>
          <div style="height: 70px">
            <input
              type="text"
              placeholder="Enter your surname"
              class="form-control"
              id="surname"
              v-model="surname"
              v-bind:class="{
                'error-boarder': $v.surname.$invalid && surnameClicked,
              }"
              @click="surnameClicked = true"
            />
            <ErrorDiv
              v-if="surnameClicked"
              :parameter="$v.surname"
              :name="'Surname'"
            />
          </div>
          <div style="height: 70px">
            <input
              type="text"
              placeholder="Enter your phone number"
              class="form-control"
              id="phoneNumber"
              v-model="phoneNumber"
              v-bind:class="{
                'error-boarder': $v.phoneNumber.$invalid && phoneNumberClicked,
              }"
              @click="phoneNumberClicked = true"
            />
            <ErrorDiv
              v-if="phoneNumberClicked"
              :parameter="$v.phoneNumber"
              :name="'Phone number'"
            />
          </div>
        </div>

        <!--THIRD TAB-->
        <div class="tab">
          <label>Where are you from?</label>
          <div style="height: 70px">
            <input
              type="text"
              placeholder="Enter your address"
              class="form-control"
              id="address"
              v-model="address"
              v-bind:class="{
                'error-boarder': $v.address.$invalid && addressClicked,
              }"
              @click="addressClicked = true"
            />
            <ErrorDiv
              v-if="addressClicked"
              :parameter="$v.address"
              :name="'Address'"
            />
          </div>
          <div style="height: 70px">
            <select
              id="inputState"
              v-model="country"
              class="form-control"
              v-bind:class="{
                'error-boarder': $v.country.$invalid && countryClicked,
              }"
              @click="countryClicked = true"
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
            <ErrorDiv
              v-if="countryClicked"
              :parameter="$v.country"
              :name="'Country'"
            />
          </div>
          <div style="height: 70px">
            <input
              type="text"
              placeholder="Enter your city"
              class="form-control"
              id="city"
              v-model="city"
              v-bind:class="{
                'error-boarder': $v.city.$invalid && cityClicked,
              }"
              @click="cityClicked = true"
            />
            <ErrorDiv v-if="cityClicked" :parameter="$v.city" :name="'City'" />
          </div>
        </div>

        <!--FOURTH TAB-->
        <div class="tab">
          <label>What is going to be your Rivera role?</label>
          <div style="height: 70px">
            <select
              v-model="type"
              class="form-control"
              v-bind:class="{
                'error-boarder': $v.type.$invalid && typeClicked,
              }"
            >
              <option v-for="tip in types" :key="tip">{{ tip }}</option>
            </select>
            <ErrorDiv :parameter="$v.type" :name="'Type'" />
          </div>
          <div style="height: 70px">
            <div v-if="this.type != 'Regular User'">
              <b-form-textarea
                placeholder="Enter description"
                id="description"
                rows="3"
                no-resize
                style="font-size: 16px"
                v-model="description"
                v-bind:class="{ 'error-boarder': $v.description.$invalid }"
              />
              <ErrorDiv :parameter="$v.description" :name="'Description'">
              </ErrorDiv>
            </div>
          </div>
          <div style="height: 70px">
            <div v-if="type == 'Fishing Instructor'">
              <b-form-textarea
                lazy-formatter
                placeholder="Enter biography"
                id="biography"
                rows="3"
                no-resize
                style="font-size: 16px"
                v-model="biography"
                v-bind:class="{ 'error-boarder': $v.biography.$invalid }"
              />
              <ErrorDiv :parameter="$v.biography" :name="'Biography'">
              </ErrorDiv>
            </div>
            <div v-else-if="type == 'Boat Owner'">
              <select
              v-model="boatOwnertype"
              class="form-control"
            >
              <option value="captain" selected>Captain</option>
              <option value="firstOfficer">First Officer</option>
            </select>
            </div>
          </div>
        </div>

        <div style="overflow: auto; margin-top: 20px">
          <div style="float: right">
            <b-spinner
              variant="success"
              v-if="emailSending"
              class="spinning mr-5"
            ></b-spinner>
            <b-button id="prevBtn" style="border: none" @click="nextPrev(-1)"
              >Previous</b-button
            >
            <b-button
              v-if="currentTab != 3"
              id="nextBtn"
              class="prime-btn"
              @click="nextPrev(1)"
              >Next</b-button
            >
            <b-button v-else @click="register" class="prime-btn"
              >Submit</b-button
            >
          </div>
        </div>
        <b-modal id="success" hide-footer>
          <div class="d-block text-center">
            <h4 v-if="type == 'Regular User'">
              Your registration is almost done. Go check out your email to
              verify your account.
            </h4>
            <h4 v-else>
              Your request has been sent to admin. You will get notified when he
              verifies your account.
            </h4>
          </div>
        </b-modal>
        <div style="text-align: center; margin-top: 20px">
          <span class="step"></span>
          <span class="step"></span>
          <span class="step"></span>
          <span class="step"></span>
        </div>
      </form>
      <div class="image-holder"></div>
    </div>
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
  sameAs,
} from "vuelidate/lib/validators";
import useValidate from "@vuelidate/core";
import ErrorDiv from "./ErrorDiv.vue";
export default {
  mounted() {
    this.showTab(0);
  },
  computed: {
    countries() {
      const list = countries.getNames("en", { select: "official" });
      return Object.keys(list).map((key) => ({ value: key, label: list[key] }));
    },
  },
  data() {
    return {
      v$: useValidate(),
      currentTab: 0,
      email: "",
      username: "",
      password: "",
      name: "",
      surname: "",
      phoneNumber: "",
      country: "",
      city: "",
      address: "",
      boatOwnertype: "captain",
      type: "",
      description: "",
      password2: "",
      emailClicked: false,
      usernameClicked: false,
      passwordClicked: false,
      nameClicked: false,
      surnameClicked: false,
      phoneNumberClicked: false,
      countryClicked: false,
      cityClicked: false,
      addressClicked: false,
      typeClicked: false,
      descriptionClicked: false,
      password2Clicked: false,
      usernameExists: false,
      typingTimer: "",
      doneTypingInterval: 500,
      loadingUsername: false,
      types: [
        "Cottage Owner",
        "Boat Owner",
        "Fishing Instructor",
        "Regular User",
      ],
      biography: "",
      emailSending: false,
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
    description: {},
    biography: {
      required,
      minLength: minLength(5),
    },
    password2: {
      required,
      minLength: minLength(2),
      maxLength: maxLength(20),
      sameAsPassword: sameAs("password"),
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
      if (this.type != "Regular User") this.registerOwner();
      else this.registerClient();
    },
    showTab(n) {
      var x = document.getElementsByClassName("tab");
      x[n].style.display = "block";
      if (n == 0) document.getElementById("prevBtn").style.display = "none";
      else document.getElementById("prevBtn").style.display = "inline";
      this.fixStepIndicator(n);
    },
    validateForm() {
      return true;
    },
    nextPrev(n) {
      var x = document.getElementsByClassName("tab");
      if (n == 1 && !this.validateForm()) return false;
      x[this.currentTab].style.display = "none";
      this.currentTab = this.currentTab + n;
      if (this.currentTab >= x.length) {
        return false;
      }
      this.showTab(this.currentTab);
    },
    fixStepIndicator(n) {
      var x = document.getElementsByClassName("step");
      for (var i = 0; i < x.length; i++)
        x[i].className = x[i].className.replace(" active", "");
      x[n].className += " active";
      document.getElementsByClassName("step")[this.currentTab].className +=
        " finish";
    },
    getFormData() {
      var formData = new FormData();
      formData.append("email", "mrsisatim20+" + this.email);
      formData.append("username", this.username);
      formData.append("password", this.password);
      formData.append("name", this.name);
      formData.append("surname", this.surname);
      formData.append("phoneNumber", this.phoneNumber);
      formData.append("country", this.country);
      formData.append("city", this.city);
      formData.append("address", this.address);
      console.log(this.type);
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
      this.emailSending = true;
      console.log("ok");
      var formData = this.getFormData();
      formData.append("type", this.type);
      formData.append("description", this.description);
      if (this.type == "Fishing Instructor")
        formData.append("biography", this.biography);
      if (this.type == "Boat Owner")
        formData.append("boatOwnerType", this.boatOwnertype);
      this.$axios
        .post("/api/auth/signup", formData)
        .then((resp) => {
          console.log(resp);
          this.$bvModal.show("success");
          this.emailSending = false;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    registerClient() {
      console.log(this.$v.$invalid);
      console.log(this.$v.name.$invalid);
      console.log("OVDE JE");
      console.log(this.$v.description.$invalid);
      console.log(this.$v.biography.$invalid);
      this.$v.$touch();
      if (this.$v.$invalid) {
        alert("Validation failed!");
        return;
      }
      this.emailSending = true;
      console.log("ok");
      var formData = this.getFormData();
      this.$axios
        .post("/api/auth/signupClient", formData)
        .then((resp) => {
          console.log(resp);
          this.$bvModal.show("success");
          this.emailSending = false;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    changeToLogin() {
      this.$router.push({ path: "/login" });
    },
    doneTyping() {
      console.log(username.value);
      $("#username").addClass("ok-border");
      this.$axios
        .get("/api/auth/check-if-username-exists?username=" + username.value)
        .then((resp) => {
          console.log("PRoverenoooo", resp.data);
          this.loadingUsername = false;
          this.usernameExists = resp.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    setTrue(parameter) {
      parameter = true;
    },
    resetTimer() {
      clearTimeout(this.typingTimer);
      this.usernameExists = false;
      this.loadingUsername = true;
      $("#username").removeClass("ok-border");
      if ($("#username").val()) {
        this.typingTimer = setTimeout(this.doneTyping, this.doneTypingInterval);
      }
    },
  },
  watch: {
    type: function (val) {
      if (val != "Fishing Instructor") {
        this.biography = "fhnfksdfgksdjdfsdjk";
      } else {
        this.biography = "";
      }
    },
  },
};
</script>


<style>
.ok-border {
  border-color: #16c79a;
}
#regForm {
  background-color: #ffffff;
  margin: 100px auto;
  font-family: Raleway;
  padding: 40px;
  width: 70%;
  min-width: 300px;
}

/* Hide all steps by default: */
.tab {
  display: none;
}

#prevBtn {
  background-color: #bbbbbb;
}

/* Make circles that indicate the steps of the form: */
.step {
  height: 15px;
  width: 15px;
  margin: 0 2px;
  background-color: #bbbbbb;
  border: none;
  border-radius: 50%;
  display: inline-block;
  opacity: 0.5;
}

.step.active {
  opacity: 1;
}

.step.finish {
  background-color: #04aa6d;
}
</style>