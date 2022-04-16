<template>
  <div>
    <label>Current Password</label>
    <div v-if="cpasswordHidden" class="bv-example-row">
        <b-input-group>
            <b-form-input v-model="cpasswordText" type="password"></b-form-input>
            <b-input-group-append>
                <b-button @click="showCPassword">Show</b-button>
            </b-input-group-append>
        </b-input-group>
    </div>
    <div v-if="!cpasswordHidden" class="bv-example-row">
        <b-input-group>
            <b-form-input v-model="cpasswordText" type="text"></b-form-input>
            <b-input-group-append>
                <b-button @click="hideCPassword">Hide</b-button>
            </b-input-group-append>
        </b-input-group>
    </div>
    <b-form-invalid-feedback :state="currentPasswordValidation">
      That is not your current password. Try again.
    </b-form-invalid-feedback>

    <label>New Password</label>
    <div v-if="passwordHidden" class="bv-example-row">
        <b-input-group>
            <b-form-input v-model="passwordText" type="password"></b-form-input>
            <b-input-group-append>
                <b-button @click="showPassword">Show</b-button>
            </b-input-group-append>
        </b-input-group>
    </div>
    <div v-if="!passwordHidden" class="bv-example-row">
        <b-input-group>
            <b-form-input v-model="passwordText" type="text"></b-form-input>
            <b-input-group-append>
                <b-button @click="hidePassword">Hide</b-button>
            </b-input-group-append>
        </b-input-group>
    </div>
    <b-form-invalid-feedback :state="newPasswordValidation">
      Password must be at least 8 characters. Try again.
    </b-form-invalid-feedback>

    <label>Re-enter New Password</label>
    <div v-if="rpasswordHidden" class="bv-example-row">
        <b-input-group>
            <b-form-input v-model="rpasswordText" type="password"></b-form-input>
            <b-input-group-append>
                <b-button @click="showRPassword">Show</b-button>
            </b-input-group-append>
        </b-input-group>
    </div>
    <div v-if="!rpasswordHidden" class="bv-example-row">
        <b-input-group>
            <b-form-input v-model="rpasswordText" type="text"></b-form-input>
            <b-input-group-append>
                <b-button @click="hideRPassword">Hide</b-button>
            </b-input-group-append>
        </b-input-group>
    </div>
    <b-form-invalid-feedback :state="newPasswordAgainValidation">
      Re-entered password is not the same as your new password.
    </b-form-invalid-feedback>
    <b-button block class="change-password">Change password</b-button>
  </div>
</template>

<script>
import { BIcon, BIconEyeFill, BIconEyeSlashFill } from "bootstrap-vue";
export default {
  name: "ClientProfile",
  components: {
    BIcon,
    BIconEyeFill,
    BIconEyeSlashFill,
  },
  data() {
    return {
      passwordHidden: {
        default: false,
        type: Boolean,
      },
      rpasswordHidden: {
        default: false,
        type: Boolean,
      },
      cpasswordHidden: {
        default: false,
        type: Boolean,
      },
      passwordText: "",
      rpasswordText: "",
      cpasswordText: "",
    };
  },
  methods: {
    hidePassword() {
      this.passwordHidden = true;
    },
    showPassword() {
      this.passwordHidden = false;
    },
    hideRPassword() {
      this.rpasswordHidden = true;
    },
    showRPassword() {
      this.rpasswordHidden = false;
    },
    hideCPassword() {
      this.cpasswordHidden = true;
    },
    showCPassword() {
      this.cpasswordHidden = false;
    },
  },
  computed: {
    currentPasswordValidation() {
      return this.cpasswordText == "current";
    },
    newPasswordValidation() {
      return this.passwordText.length >= 8;
    },
    newPasswordAgainValidation() {
      return this.rpasswordText == this.passwordText;
    },
  },
};
</script>

<style scoped>
.change-password {
  margin-top: 8px;
  background-color: #fff;
  border: 1px solid #16c79a;
  color: #16c79a;
}
</style>