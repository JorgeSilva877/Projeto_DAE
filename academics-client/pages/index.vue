<template>
  <div class="store-opening">
    <h1>Welcome to the Store</h1>
    <button @click="goToLogin" class="login-button">Login</button>
    <button @click="goToCreate" class="login-button">Sign up</button>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from "vue-router";

const router = useRouter();

function goToCreate(){
  router.push("/createUser");
}

function goToLogin() {
  router.push("/auth"); // Redirects to the login page
}
</script>

<style scoped>
.store-opening {
  font-family: Arial, sans-serif;
  text-align: center;
  margin-top: 100px;
}

h1 {
  font-size: 2.5em;
  color: #333;
  margin-bottom: 20px;
}

.login-button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  font-size: 1.2em;
  cursor: pointer;
  margin-left: 10px;
  margin-right: 10px;
}

.login-button:hover {
  background-color: #0056b3;
}
</style>
