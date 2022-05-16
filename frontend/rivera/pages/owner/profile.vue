<template>
<div>
  <Navbar :currentPage="'profile'"/>
  <b-container class="bv-example-row" style="max-width: 700px;">
    <ClientProfile/>
    <b-card style="margin-bottom: 10px">
      <b-tabs content-class="mt-3" justified>
        <b-tab title="Personal data" active><profile-data/></b-tab>
        <b-tab title="Change password"><password-change/></b-tab>
        <b-tab title="Loyalty program"><p>I'm a disabled tab!</p></b-tab>
      </b-tabs>
    </b-card>
  </b-container>
</div>  
</template>

<script>
import Navbar from '../../components/navbar.vue';
import ClientProfile from '../../components/client/ClientProfile.vue';
import ProfileData from '../../components/client/ProfileData.vue';
import PasswordChange from '../../components/client/PasswordChange.vue';
export default {
  components: { Navbar, ClientProfile, ProfileData, PasswordChange },
}
</script>

<style>
.nav-link:hover {
  opacity: 0.9;
  background-color: #fff;
  cursor: pointer;
}
</style>