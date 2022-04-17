<template>
    <div>
    <input type="text" id="idCottage"/>
    <button id="deleteCottageButton" @click="deleteCottage"> Delete cottage </button>
    </div>
</template>
<script>
export default {
    mounted() {
    },
	methods: {
		deleteCottage() {
            let cottageId = parseInt(document.getElementById("idCottage").value);
			this.$axios.post('/api/cottage/delete-cottage?id='+cottageId).then((resp) => {
				console.log(resp);
			}).catch((err) => {
				console.log(err);
			});
		}
	}
}
</script>