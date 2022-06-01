<template>
  <div class="container">
    <b-card>
      <div class="form-row" style="text-align: center">
        <div class="form-group col-md-12">
          <h3>Add discount</h3>
        </div>
      </div>
      <div class="form-row align-items-center">
          
        <div class="form-group col-4">
          <label for="inputType">Start date</label>
          <b-form-datepicker
            size="sm"
            v-model="startDate"
            placeholder="Start date"
          ></b-form-datepicker>
          <b-form-timepicker
            size="sm"
            v-model="startTime"
            placeholder="Start time"
          ></b-form-timepicker>
        </div>
        <div class="form-group col-4">
          <label for="inputType">End date</label>
          <b-form-datepicker
            size="sm"
            v-model="endDate"
            placeholder="End date"
          ></b-form-datepicker>
          <b-form-timepicker
            size="sm"
            v-model="endTime"
            placeholder="End time"
          ></b-form-timepicker>
        </div>        
        <div class="form-group col-4">
          <label for="inputAddress"
            >Tags - key words that describe this discount</label
          >
          <b-form-tags
            input-id="tags-basic"
            v-model="tags"
            placeholder="Tags"
          ></b-form-tags>
        </div>
      </div>
      <div class="form-row">
        <div class="form-group col-4">
          <label for="inputPrice">Price</label>
          <input
            type="text"
            class="form-control"
            v-model="price"
            id="inputPrice"
            placeholder="Price"
          />
        </div>
        <div class="form-group col-4">
          <label for="inputCapacity">Capacity</label>
          <input
            type="text"
            class="form-control"
            v-model="capacity"
            id="inputCapacity"
            placeholder="Capacity"
          />
        </div>
        <div  class="form-group col-2">
        </div>
        <div  class="form-group col-2">
          <label for="btn"> <br></label>
          <b-button @click="upload" class="prime-btn w-100" size="m"
            >Add discount</b-button
          >
        </div>
      </div>
      
      
    </b-card>
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
  mounted(){},
  methods: {
    upload() {
      var formData = new FormData();
      formData.append("startDateTime", this.startDate+"T"+this.startTime);
      formData.append("endDateTime", this.endDate+"T"+this.endTime);
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
    }
};
</script>