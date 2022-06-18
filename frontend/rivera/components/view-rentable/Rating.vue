<template>
    <div class="w-100 d-flex">
        <font-awesome-icon icon="star" v-for="full in fulls" :key="full +'f'" class="full" />
        <font-awesome-icon icon="star-half-stroke" v-for="half in halfs" :key="half+'h'" class="half"  />
        <font-awesome-icon icon="star" v-for="empty in emptys" :key="empty+'e'" class="empty" />
    </div>
</template>

<script>
export default {
    props: ['score'],
    mounted() {
        this.fulls = parseInt(this.score);
        this.halfs = this.fulls == this.score ? 0 : 1;
        this.emptys = 5 - this.halfs - this.fulls;
    },
    data() {
        return {
            fulls : 0,
            halfs : 0,
            emptys : 0
        }
    },
    watch: {
      score(val, oldVal) {
        this.score = val;
        this.fulls = parseInt(this.score);
        this.halfs = this.fulls == this.score ? 0 : 1;
        this.emptys = 5 - this.halfs - this.fulls;
      }
    }
}
</script>

<style>
  .full {
    color: #16C79A;
  }
  
  .empty {
    color: #eee;
  }

  .half {
    color: #16C79A;
  }
</style>