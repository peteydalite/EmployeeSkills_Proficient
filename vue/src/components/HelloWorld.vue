<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <h2>Employee Directory</h2>
    <!-- <div id="directory" v-for="employee in employees" :key="employee.id">
      {{employee.firstName}}
      {{employee.lastName}}
      {{employee.companyEmail}}
      {{employee.role}}
      <button v-on:click.prevent="showEmployee(employee.id)">View Profile</button>
    </div> -->
    <table class="table table-striped" id="directory-container">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">First Name</th>
          <th scope="col">Last Name</th>
          <th scope="col">Company Email</th>
          <th scope="col">Role</th>
        </tr>
      </thead>
      <tbody>
        <tr id="directory" v-for="employee in employees" :key="employee.id">
          <th scope="row" id="employee.id">View</th>
          <td>{{ employee.firstName }}</td>
          <td>{{ employee.lastName }}</td>
          <td>{{ employee.companyEmail }}</td>
          <td>{{ employee.role }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import EmpService from "@/services/EmployeeService";

export default {
  name: "HelloWorld",
  components: {},
  props: {
    msg: String,
  },
  data() {
    return {
      employees: [],
    };
  },
  methods: {
    getEmployees() {
      EmpService.getAllEmployee()
        .then((response) => {
          if (response.status === 200) {
            this.employees = response.data;
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    showEmployee(empId) {
      this.$router.push({
        name: "EmployeeProfile",
        params: { id: empId },
      });
    },
  },
  mounted: function () {
    this.getEmployees();
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}

#directory-container{
  margin-right: 50px;

}
</style>
