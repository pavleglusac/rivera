<template>
    <div style="font-size:15px">
        <span class="error"  v-if="!parameter.required  && parameter.$invalid">
            {{name}} must not be empty!
        </span>
        <span v-else-if="!parameter.minLength && parameter.$invalid">
            {{name}} must be more then 2 characters!
        </span>
        <span v-else-if="!parameter.maxLength && parameter.$invalid">
            {{name}} must be less then 20 characters!
        </span>
        <span v-else-if="!parameter.email && parameter.$invalid">
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