<template>
  <b-container class="bv-example-row">
      <b-card>
    <b-form @submit.stop.prevent>
      <label for="feedback-first-name">First Name</label>
      <b-form-input
        v-model="firstName"
        :state="firstNameValidation"
        id="feedback-first-name"
        required
      ></b-form-input>
      <b-form-invalid-feedback :state="firstNameValidation">
        Wrong first name format. Try again.
      </b-form-invalid-feedback>

      <label for="feedback-last-name">Last Name</label>
      <b-form-input
        v-model="lastName"
        :state="lastNameValidation"
        id="feedback-last-name"
        required
      ></b-form-input>
      <b-form-invalid-feedback :state="lastNameValidation">
        Wrong last name format. Try again.
      </b-form-invalid-feedback>

      <label for="feedback-phone-number">Phone Number</label>
      <b-form-input
        v-model="phoneNumber"
        :state="phoneNumberValidation"
        id="feedback-phone-number"
        required
      ></b-form-input>
      <b-form-invalid-feedback :state="phoneNumberValidation">
        Wrong phone number format. Try again.
      </b-form-invalid-feedback>

      <label for="feedback-address">Address</label>
      <b-form-input
        v-model="address"
        :state="addressValidation"
        id="feedback-address"
        required
      ></b-form-input>
      <b-form-invalid-feedback :state="addressValidation">
        Wrong address format. Try again.
      </b-form-invalid-feedback>

      <b-row>
        <b-col><label>Country</label></b-col>
        <b-col><label>Region</label></b-col>
      </b-row>
      <b-row>
        <b-col
          ><country-select
            class="form-control"
            v-model="country"
            :country="country"
            topCountry="SR"
        /></b-col>
        <b-col>
          <region-select
            v-model="region"
            class="form-control"
            :country="country"
            :region="region"
          />
        </b-col>
      </b-row>
        <b-button block class="save-changes">Save changes</b-button>
    </b-form>
    </b-card>
  </b-container>
</template>

<script>
export default {
  name: "ClientProfile",
  data() {
    return {
      firstName: "",
      lastName: "",
      phoneNumber: "",
      address: "",
      country: "",
      region: "",
    };
  },
  computed: {
    firstNameValidation() {
      return this.firstName.length != 0;
    },
    lastNameValidation() {
      return this.lastName.length != 0;
    },
    phoneNumberValidation() {
      return this.phoneNumber.length >= 9;
    },
    addressValidation() {
      return this.address.length != 0;
    },
  },
};
</script>

<style scoped>
.form-control {
  display: block;
  width: 100%;
  height: calc(1.5em + 0.75rem + 2px);
  padding: 0.375rem 0.75rem;
  font-size: 1rem;
  font-weight: 400;
  line-height: 1.5;
  color: #495057;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid #ced4da;
  border-radius: 0.25rem;
  transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}

.save-changes {
    margin-top: 10px;
    background-color: #16C79A;
    border: none;
}
</style>