<template>
<div class="container">
  <div class="row mt-5">
    <div class="col-12 text-center">
      <img style="max-height: 400px" src="@/assets/403.jpg" />
      <h3 class="mt-1">Access Forbidden</h3>
      <p>You don't have permission to access this page.</p>
      <hr/>
      <b-button class="get-started mt-2" @click="changeToLogin">GO BACK HOME</b-button>
    </div>
  </div> 
</div>
</template>

<script>

export default({
  methods: {
    changeToLogin() {
      this.$router.push({ path: "/" });
    },
  }
})
</script>
