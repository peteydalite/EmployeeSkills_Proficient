<template>
  <div class="empSkills-container">
      <div class="type">
          <strong>Skill:</strong> {{skill.field.name}} <br />
          <strong>Experience:</strong> {{skill.experience}} months
      </div>
      <div class="category">
          <strong>Type:</strong> {{skill.field.type}} <br />
          <strong>Summary:</strong> {{skill.summary}}
      </div>
  </div>
</template>

<script>
export default {
    name: "Skill",
    props: {
        skill: {},
    }
}
</script>

<style>
.empSkills-container{
    background-color: white;
    display:grid;
    grid-template-columns: 1fr 1fr;
    border: 1px solid grey;
    border-radius: 8px;
    margin-left: 10px;
    margin-right: 10px;
}
.type{
    justify-self: center;
    text-align: left;
}
.category{
    /* justify-self: center; */
    text-align: left;
    word-wrap: break-word;
}
</style>