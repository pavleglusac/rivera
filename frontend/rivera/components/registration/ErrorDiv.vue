<template>
  <div style="font-size: 12px; float: left; color: #dc143c">
    <span
      class="error"
      v-if="!parameter.required && parameter.required != null"
    >
      {{ name }} must not be empty!
    </span>
    <span v-else-if="!parameter.minLength && parameter.minLength != null">
      {{ name }} must be more than 2 characters!
    </span>
    <span v-else-if="!parameter.maxLength && parameter.maxLength != null">
      {{ name }} must be less than 20 characters!
    </span>
    <span v-else-if="!parameter.email && parameter.email != null">
      Email must be proper format!
    </span>
    <span
      v-else-if="!parameter.sameAsPassword && parameter.sameAsPassword != null"
    >
      Passwords must match!
    </span>
  </div> 
</template>

<script>
export default {
  props: ["parameter", "name"],
  mounted() {
    console.log("asfafs");
  },
};
</script>