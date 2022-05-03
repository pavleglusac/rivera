<template>
    <div style="font-size:15px">
        <span class="error"  v-if="!parameter.required">
            {{name}} must not be empty!
        </span>
        <span v-else-if="!parameter.minLength">
            {{name}} must be more then 2 letters!
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