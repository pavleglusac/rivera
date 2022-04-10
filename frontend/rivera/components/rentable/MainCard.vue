<template>
    <div class="mt-5">
        <p style="font-size: 0.9em; font-weight: 500;" class="p-0 mb-1">
            {{location}}
        </p>
        <h2 id="name" class="mb-0">
            {{name}}
        </h2>
        <div class="text-secondary" style="font-size: 0.8em;">
            <font-awesome-icon icon="star" /> &nbsp; {{ score }}
        </div>
        <div id="desc" class="mt-4 w-75">
            {{description}}
        </div>
    </div>    
</template>

<script>
export default {
    mounted() {

    }, 
    methods: {

    },
    data() {
        return {


        }
    },
    props: ['location', 'name', 'score', 'description']
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans:wght@400&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Rubik:wght@600&display=swap');
#desc {
    font-family: 'Noto Sans', sans-serif;
}

#name {
    font-family: 'Rubik', sans-serif;
}
</style>