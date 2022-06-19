<template>
    <b-modal
      class="success-modal"
      hide-footer
      header-bg-variant="success"
      ref='modal'
    >
      <template #modal-header>
        <div class="w-100" style="color: white; display: inline-block; text-align: center;">
          <font-awesome-icon icon="circle-check" style="font-size: 90px;" />
        </div>
      </template>
      <h4>{{title}}</h4>
      <p>{{text}}</p>
    </b-modal>
</template>

<script>
export default {
  name: "Popup",
  props: ["title", "text", "type"],
  data() {
      return {
          
      }
  },
  methods: {
      show() {
          this.$refs.modal.show();
      }
  },
};
</script>
