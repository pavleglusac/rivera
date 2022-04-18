<template>
    <div class="mt-5">
        <p style="font-size: 0.9em; font-weight: 500;" class="p-0 mb-1">
            {{location}}
        </p>
        <h2 id="name" class="mb-0">
            
            {{name}}
            <div class="float-right">
            <button type="button" class="btn btn-primary rounded-pill" :disabled='canBeChanged'>Update</button>
            <button type="button" class="btn rounded-pill btn-info "  :disabled='canBeChanged' @click="deleteRentable">Delete</button>
            </div>
        </h2>
        <div class="text-secondary" style="font-size: 0.8em;">
            <font-awesome-icon icon="star" /> &nbsp; {{ score }}
        </div>
        <div id="desc" class="mt-4 w-75">
            {{description}}
        </div>




    <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Confirm delete dialog</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					</div>
					<div class="modal-body">
					Are you sure you want to proceed?
					</div>
					<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="roomUpdate" @click="confirmDeletion()">Yes</button>
					<button type="button" class="btn btn-secondary" id="modalClosure"  @click="closeModal('deleteModal')">No</button>
					</div>
				</div>
				</div>
			</div>
            
    </div>    
</template>

<script>
export default {
    mounted() {
        console.log(this.$route.params.cottage+"AAAAAAAAAAAAAAAAA");
    }, 
    methods: {
        deleteRentable(){
			$("#deleteModal").modal('show');		
        },
        confirmDeletion(){
		this.$axios
        .post('/api/cottage/delete-cottage?id=' + this.$route.params.cottage)
        .then((resp) => {
			console.log(resp);
		}).catch((err) => {
			console.log(err);   
        });
        this.closeModal("deleteModal");
        },
        closeModal(id){
			$("#"+id).modal('hide');
		}
        ,
        updateRentable() {

        }
    },
    data() {
        return {


        }
    },
    props: ['location', 'name', 'score', 'description', 'canBeChanged']
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