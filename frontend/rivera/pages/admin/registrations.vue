<template>
	<div>
		<Navbar :currentPage="'registrations'" />
		<div
			class="d-flex flex align-items-start mt-1 parent"
			style="width: 70vw; margin-left: auto; margin-right: auto"
		>
			<b-card
				class=" card w-25 mt-2 mb-5"
				style="margin-top: 0; box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px"
			>
                <p>
                    Total requests: {{totalRequests}}
                </p>
                <p>
                    Active accounts: {{activeAccounts}}
                </p>
                <p>
                    Terminated accounts: {{terminatedAccounts}}
                </p>
			</b-card>

            <b-card
				class=" card w-75 mt-2 mb-5 ml-2"
				style="margin-top: 0; box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px"
			>
                <validate-registration class="h-100 child" :usernameToRegister="'marko'"></validate-registration>
			</b-card>
		</div>
	</div>
</template>

<script>
import ValidateRegistration from '../../components/registration/ValidateRegistration.vue';
export default {
	components: { ValidateRegistration },
    data() {
        return {
            totalRequests: 1389,
            activeAccounts: 28,
            terminatedAccounts: 6,
        }
    },

};
</script>

<style scoped>
.parent >>> .child label {
    float: left;
    font-size: 16px;
}

</style>