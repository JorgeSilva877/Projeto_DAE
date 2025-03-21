<template>
  <div class="login-container">
    <div class="login-box">
      <h1 class="login-title">Login</h1>
      <div class="login-field">
        <label for="username">Username:</label>
        <input id="username" v-model="loginFormData.username" placeholder="Enter your username" />
      </div>
      <div class="login-field">
        <label for="password">Password:</label>
        <input id="password" type="password" v-model="loginFormData.password" placeholder="Enter your password" />
      </div>
      <div class="login-buttons">
        <button @click="login" class="login-button">Login</button>
        <button @click="reset" class="reset-button">Reset</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useAuthStore } from "~/store/auth-store.js";

const authStore = useAuthStore();
const router = useRouter();

const config = useRuntimeConfig();
const api = config.public.API_URL;
const loginFormData = reactive({
  username: null,
  password: null,
});
const apiFormData = reactive({
  path: "auth/user",
});
const token = ref(null);
const messages = ref([]);
async function login() {
  try {
    await $fetch(`${api}/auth/login`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: loginFormData,
      onResponse: async ({ request, response, options }) => {
        messages.value.push({
          method: options.method,
          request: request,
          status: response.status,
          statusText: response.statusText,
          payload: response._data,
        });
        if (response.status == 200) {
          token.value = response._data;
          authStore.token = response._data;

          await sendRequest();
          // Redireciona com base no dtype
          if (authStore.user && authStore.user.dtype) {
            if (authStore.user.dtype === "Client") {
              router.push("/client/initial-page"); // Página do cliente
            } else if (authStore.user.dtype === "Employee") {
              router.push("/employee/initial-page"); // Página do funcionário
            } else if (authStore.user.dtype === "Manager") {
              router.push("/manager/initial-page"); // Página do administrador
            }
          }
        }
        if (response.status === 401 || response.status === 400) {
          alert("Username or password wrong");
        }
      },
    });
  } catch (e) {
    console.error("login request failed: ", e);
  }
}
function reset() {
  token.value = null;
  messages.value = [];
  loginFormData.password = "";
  loginFormData.username = "";
}
async function sendRequest() {
  try {
    await $fetch(`${api}/${apiFormData.path}`, {
      method: "GET",
      headers: {
        Accept: "application/json",
        Authorization: `Bearer ${token.value}`,
      },
      onResponse({ request, response, options }) {
        messages.value.push({
          method: options.method,
          request: request,
          status: response.status,
          statusText: response.statusText,
          payload: response._data,
        });
        console.log(response._data)
        authStore.setUser(response._data);
      },
    });
  } catch (e) {
    console.error("api request failed: ", e);
  }
}
</script>

<style scoped>
/* Container styling */
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f0f4f8;
  font-family: Arial, sans-serif;
  margin: 0;
}

/* Login box styling */
.login-box {
  background: white;
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
  width: 100%;
  max-width: 400px;
}

/* Title styling */
.login-title {
  font-size: 2em;
  margin-bottom: 20px;
  color: #333;
}

/* Input field styling */
.login-field {
  margin-bottom: 20px;
  text-align: left;
}

.login-field label {
  display: block;
  margin-bottom: 8px;
  font-size: 0.9em;
  color: #555;
}

.login-field input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 1em;
}

/* Button styling */
.login-buttons {
  display: flex;
  justify-content: space-between;
  gap: 10px;
}

.login-button,
.reset-button {
  padding: 10px 20px;
  font-size: 1em;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  width: 48%;
}

.login-button {
  background-color: #007bff;
  color: white;
}

.login-button:hover {
  background-color: #0056b3;
}

.reset-button {
  background-color: #6c757d;
  color: white;
}

.reset-button:hover {
  background-color: #5a6268;
}
</style>
