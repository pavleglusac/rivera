<template>
    <div style="font-size:15px; float: left;">
        <span class="error"  v-if="!parameter.required  &&  parameter.required!=null">
            {{name}} must not be empty!
        </span>
        <span v-else-if="!parameter.minLength &&  parameter.minLength!=null">
            {{name}} must be more then 2 characters!
        </span>
        <span v-else-if="!parameter.maxLength && parameter.maxLength!=null">
            {{name}} must be less then 20 characters!
        </span>
        <span v-else-if="!parameter.email && parameter.email!=null">
            Email must be proper format!
        </span>
    </div>
</template>

<script>
    export default {
      props:['parameter','name']
      , mounted(){
        console.log  ("asfafs");
      }
    }
</script>