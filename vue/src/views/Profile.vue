<template>
  <div class="main-container">
    <div class="personal-container">
      <div class="img-container">
        <img
          id="prof-pic"
          src="https://www.oseyo.co.uk/wp-content/uploads/2020/05/empty-profile-picture-png-2.png"
          alt="No Pic"
        />
      </div>
      <h1>{{ employee.firstName }} {{ employee.lastName }}</h1>
      <h4>{{employee.companyEmail}}</h4>
    </div>
    <div class="skills-container">
      <h1>Skills</h1>
    </div>
  </div>
</template>

<script>
import EmpService from "@/services/EmployeeService";
// import { component } from 'vue/types/umd';

export default {
  name: "Profile",
  components: {},
  data() {
    return {
      employee: {},
    };
  },
  methods: {
    getEmployee() {
      EmpService.getEmployeeInfo(this.$route.params.id)
        .then((response) => {
          if (response.status === 200) {
            this.employee = response.data;
            console.log(this.employee);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  mounted() {
    this.getEmployee();
  },
};
</script>

<style>
.main-container {
  width: 100%;
  height: 100vh;
  /* background-color: lightgray; */
  display: grid;
  grid-template-columns: 1fr 2fr;
}
.personal-container {
  border: 1px solid lightgrey;
  border-radius: 8px;
  margin-left: 10px;
  margin-right: 10px;

}
.skills-container {
  border: 1px solid lightgrey;
  border-radius: 8px;
  margin-left: 10px;
  margin-right:10px;
}
.img-container {
  margin-top: 5px;
  height: 40vh;
}
#prof-pic {
  border: 1px solid black;
  border-radius: 50%;
  max-width: 100%;
  max-height: 100%;
  background-color: white;
}
</style>
