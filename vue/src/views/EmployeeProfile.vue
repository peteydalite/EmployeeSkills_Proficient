<template>
  <div>
  <h1>
      {{employee.firstName}} {{employee.lastName}}
  </h1>
  <div id="about">
      <h3>Personal</h3>
    <input :disabled="true" type="text" v-bind:value="employee.address.street" :placeholder="employee.address.street">
    <input :disabled="true" type="text" v-bind:value="employee.address.suite" :placeholder="employee.address.suite">
    <input :disabled="true" type="text" v-bind:value="employee.address.city" :placeholder="employee.address.city">
    <input :disabled="true" type="text" v-bind:value="employee.address.region" :placeholder="employee.address.region"><br>
    <input :disabled="true" type="text" v-bind:value="employee.address.postal" :placeholder="employee.address.postal">
    <input :disabled="true" type="text" v-bind:value="employee.address.country" :placeholder="employee.address.country">
  </div>

  <div id="company-info">
      <h3>Company Information</h3>
      <input :disabled="true" type="text" v-bind:value="employee.companyEmail" :placeholder="employee.companyEmail">
      <input :disabled="true" type="text" v-bind:value="employee.role" :placeholder="employee.role">
      <input v-if="employee.businessUnit == null ? true : false" disabled="true" type="text" v-bind:value="employee.businessUnit" placeholder="Business Unit">
      <input v-else disabled=true v-bind:value="employee.businessUnit" :placeholder="BusinessUnit">
      <input :disabled="true" type="text" v-bind:value="employee.role" :placeholder="employee.role">
  </div>

  <div id='skills-info'>
      <h3>Skills</h3>
      <div v-for="skill in employee.skills" :key="skill.id">
          Field: {{skill.field.name}}
          Exp: {{skill.experience}}
          Details: {{skill.summary}}
      </div> 
      
  </div>
  </div>
</template>

<script>
import EmployeeService from '@/services/EmployeeService'

export default {
    data(){
        return{
            employee: {},
            id: this.$route.params.id
        }
    },
    methods: {
        getEmployee(){
            EmployeeService.getEmployeeInfo(this.id).then(response =>{
                if(response.status == 200){
                    console.log(response.data)
                    this.employee = response.data;
                }
            }).catch(err =>{
                console.log(err);
            });
        }
    },
    mounted: function() {
        this.getEmployee();
    }
}
</script>

<style>

</style>