<template>
<div class="container">
  <div class="row mt-5">
    <div class="col-12 text-center">
      <img style="max-height: 400px" src="@/assets/404.jpg" />
      <h3 class="mt-1">ARE YOU LOST?</h3>
      <p>The page you are looking for was moved, removed, renamed or might never existed.</p>
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
