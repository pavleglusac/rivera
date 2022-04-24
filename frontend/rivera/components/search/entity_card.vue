<template>
  <b-card no-body class="shadow-sm" img-left max-height="300">
    <b-card-img class="cover-img" :src='"http://localhost:8080" + entity.pictures[0]' /> 
    <b-card-body class="d-flex flex-column h-100">
        <div class="d-flex justify-content-between align-items-center">
            <h4 class="card-title">{{entity.name}}</h4>
        </div>
        <div class="d-flex flex-wrap align-items-center mb-2">
            <span class="text-muted">{{entity.address}}</span>
            <span class="mx-2">&#8226;</span>
            <span>{{entity.city}}, {{entity.country}}</span>
            <span class="mx-2">&#8226;</span>
            <div class="text-secondary" style="font-size: 0.8em;">
                <font-awesome-icon icon="star" /> &nbsp;{{ entity.averageScore }}
            </div>
        </div>
        
        <hr class="w-100">
        
        <b-card-text style="font-size: 0.8em;">{{entity.description}}</b-card-text>
        <div>
          <li v-for="tag in entity.tags">
              <span class="tag">{{ tag }}</span>
          </li>
        </div>

        <hr class="w-100">
            
        <div class="d-flex justify-content-between align-items-center">
            <span class="font-1h">{{entity.perHour}}$ per hour / {{entity.perDay}}$ per day</span>
            <b-button variant="primary" class="book-btn">Book Now</b-button>
        </div>
    </b-card-body>
  </b-card>
</template>

<script>
export default {
  name: "EntityCard",
  components: {},
  data() {
    return {};
  },
  props: {
    entity: {
      type: Object,
      required: true,
    },
  },
};
</script>

<style scoped>
li {
  display: inline-block;
  list-style-type: none;
}

.tag {
  background-color: var(--light-blue-color);
  border: none;
  border-radius: 5px;
  padding: 5px;
  margin: 3px;
  text-align: center;
  color: #fff;
  display: inline;
  font-size: 12px;
}

.book-btn {
  background-color: var(--prime-color);
  border: none;
}
.book-btn:hover {
  background-color: #39AEA9;
}

.cover-img {
  width: 300px;
  height: 300px;
  object-fit: cover;
}
</style>
