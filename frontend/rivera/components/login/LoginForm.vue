<template>
  <div>
    <b-card class="text-center mx-auto my-auto card">
      <img src="..\..\static\rivera_logo_ver.png" />
      <form>
        <div class="form-group">
          <label>Email</label>
          <input
            type="email"
            placeholder="Enter your email address"
            class="form-control form-control-lg"
          />
        </div>
        <div class="form-group">
          <label>Password</label>
          <input
            type="password"
            placeholder="Enter your password"
            class="form-control form-control-lg"
          />
        </div>
        <b-form-invalid-feedback>
          Wrong email or password. Please try again.
        </b-form-invalid-feedback>

        <b-button block id="login-btn" variant="primary">Log In</b-button>
        <b-button block id="signup-btn" variant="outline-primary">Create an account</b-button>
        <b-button block id="just-looking">Just looking...</b-button>
      </form>
    </b-card>
  </div>
</template>
<script>
export default {
  data() {
    return {};
  },
};
</script>
<style>

html,
body {
  background: linear-gradient(
    90deg,
    var(--light-blue-color) 0%,
    var(--prime-color) 100%
  );
}

.card {
    border-radius: 20;
    filter: drop-shadow(0px 2px 3px);
    padding: 10px;
    margin: 0 auto; 
    float: none;
    margin-bottom: 10px;
    max-width: 40rem;
    padding: 50px;
}

.card img {
    height: 200px;
    margin-bottom: 30px;
}

#login-btn {
    background-color: var(--prime-color);
    border: none;
}

#signup-btn {
    border: 2px solid var(--prime-color);
    color: var(--prime-color);
}

#signup-btn:hover {
    background-color: var(--prime-color);
    color: white;
}

#just-looking {
    border: none;
    background: white;
    color: var(--dark-blue-color);
    font-weight: 500;
}

#just-looking:hover {
    border: 2px solid var(--dark-blue-color);
}

label {
    float: left;
    font-size: 18px;
}

input[type="email"],
input[type="password"] {
    font-size: 18px;
}
</style>