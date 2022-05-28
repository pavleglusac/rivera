<template>
    <div>
        
    </div>
</template>


<script>
export default {
    data() {
        return {
        }
    },
    mounted() {
        this.loadIncome();
    },
    methods: {
        loadIncome(){            
            this.$axios.get(`/api/get-income`)
            .then(response => {
                console.log(response.data);
            });
        },
    }

}
</script>