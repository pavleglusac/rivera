<template>
  <div class="container">
    <h3>Add discount</h3>
    <p>Start:</p>
    <b-row style="margin-top: -10px; margin-bottom: 5px">
      <b-col>
        <b-form-datepicker
          v-model="startDate"
          placeholder="Start date"
        ></b-form-datepicker>
      </b-col>
      <b-col>
        <b-form-timepicker
          v-model="startTime"
          placeholder="Start time"
        ></b-form-timepicker>
      </b-col>
    </b-row>
    <p>End:</p>
    <b-row style="margin-top: -10px; margin-bottom: 5px">
      <b-col>
        <b-form-datepicker
          v-model="endDate"
          placeholder="End date"
        ></b-form-datepicker>
      </b-col>
      <b-col>
        <b-form-timepicker
          v-model="endTime"
          placeholder="End time"
        ></b-form-timepicker>
      </b-col>
    </b-row>

    <p>Tags (key words that describe this discount):</p>
    <b-form-tags
    style="margin-top: -10px; margin-bottom: 5px"
      input-id="tags-basic"
      v-model="tags"
      placeholder="Tags"
    ></b-form-tags>
    <p>Price:</p>
    <b-input
    style="margin-top: -10px; margin-bottom: 5px"
      v-model="price"
      id="inputPrice"
      placeholder="Price"
    />
    <p>Capacity:</p>
    <b-input
    style="margin-top: -10px; margin-bottom: 5px"
      v-model="capacity"
      id="inputCapacity"
      placeholder="Capacity"
    />
    <b-button @click="upload" class="prime-btn w-100 mt-2" size="m"
      >Add discount</b-button
    >
  </div>
</template>


<script>
export default {
  data() {
    return {
      startDate: "",
      endDate: "",
      startTime: "",
      endTime: "",
      capacity: "",
      price: "",
      tags: [],
    };
  },
  mounted() {},
  methods: {
    upload() {
      var formData = new FormData();
      formData.append("startDateTime", this.startDate + "T" + this.startTime);
      formData.append("endDateTime", this.endDate + "T" + this.endTime);
      formData.append("capacity", this.capacity);
      formData.append("price", this.price);
      formData.append("tags", this.tags);
      formData.append("rentableId", this.$route.params.rentable);
      this.$axios
        .post("/api/add-discount", formData)
        .then((resp) => {
          window.location.reload();
          console.log(resp);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>