<script setup>
import { useAuthStore } from "~/store/auth-store.js";
const router = useRouter();
const authStore = useAuthStore();

function logout() {
  authStore.logout();
  router.push("/");
}

function goBack() {// Retorna à página anterior
  if(route.name === 'client-initial-page' || route.name === 'employee-initial-page' || route.name === 'manager-initial-page'){
    authStore.logout();
  }
  router.back();
}
const route = useRoute();

const isLoginPage = computed(() => route.name === 'index');
const cantLogout = computed(() => (route.name === 'index' || route.name === 'auth'));
</script>
<template>
  <div class="container">
    <a class="login-link" v-if="authStore.user">Login as {{authStore.user.username}} </a>
    <button @click="logout" v-if="!cantLogout">Logout</button>
    <button @click="goBack" v-if="route.name !== 'index'">Back</button>
  </div>
  <div>
    <slot />
  </div>

</template>
<style scoped>

button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 5px 16px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1em;
  margin-left: 10px;
}

button:hover {
  background-color: #0056b3;
}
.container {
  display: flex; /* Definindo o contêiner como flex */
  justify-content: flex-end; /* Alinha os itens à direita */
  align-items: center; /* Alinha os itens verticalmente ao centro */
}

.login-link {
  text-decoration: none; /* Remove o sublinhado do link */
  color: black;
  font-size: 16px;
  margin-right: 10px; /* Adiciona 10px de espaço entre o link Login e Back */
  margin-left: 10px;
}

.back-link {
  text-decoration: none; /* Remove o sublinhado do link */
  color: blue;
  font-size: 16px;
}
</style>