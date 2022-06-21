<template>
    <b-modal
      :header-bg-variant='typeColor'
      class="success-modal"
      hide-footer
      ref='modal'
    >
      <template #modal-header>
        <div class="w-100" style="color: white; display: inline-block; text-align: center;">
          <font-awesome-icon v-if="typeColor=='success'" icon="circle-check" style="font-size: 90px;" />
          <font-awesome-icon v-else icon="circle-xmark" style="font-size: 90px;" />
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
          typeColor: "success"
      }
  },
  mounted() {
    if(this.type == "error")
      this.typeColor = "danger";
  },
  methods: {
      show() {
          this.$refs.modal.show();
      }
  },
};
</script>
