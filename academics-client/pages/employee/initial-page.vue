<script setup lang="ts">
import { ref, reactive } from "vue";
import { useRuntimeConfig, useFetch } from "#app"; // Adjust import based on your setup
import { useRouter } from "vue-router";
import {useAuthStore} from "~/store/auth-store";

const router = useRouter();

definePageMeta({
  middleware: ['auth']
});
const authStore = useAuthStore();



const config = useRuntimeConfig();
const api = config.public.API_URL;




const { data: warehouse} = await useFetch(`${api}/employee/${authStore.user.username}`, {
  method: "GET",
  headers: {
    Accept: "application/json",
    Authorization: `Bearer ${authStore.token}`,
  },
});

const { data: volumes, error, refresh } = await useFetch(`${api}/employee/${warehouse.value.warehouseId}/volumes`, {
  method: "GET", // Ou o método apropriado
  headers: {
    Accept: "application/json",
    Authorization: `Bearer ${authStore.token}`, // Envia o token no cabeçalho
  },
});

// Estado para controlar se o volume foi empacotado
const packedVolumes = reactive<{ [key: number]: boolean }>({});

interface DadosApi{
  id:number;
  employeeUsername:string
}

async function empacotar(volumeId: number) {

  const orderApi: DadosApi = {
    id:volumeId,
    employeeUsername:authStore.user.username
  };
  try {
    const response = await $fetch(`${api}/volume/enrollEmployee`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json", // Define o tipo do conteúdo
        Authorization: `Bearer ${authStore.token}`, // Envia o token
      },

      // Se a requisição for bem-sucedida, marcar o volume como empacotado

      body: JSON.stringify(orderApi),
    });
    packedVolumes[volumeId] = true;
    await refresh(); // Atualiza a lista de volumes
  } catch (err) {
    console.error("Erro na solicitação:", err);
    alert("Erro na conexão com o servidor.");
  }
}

</script>

<template>
  <div class="shop-container">
    <header class="dashboard">
      <div class="dashboard-left">
        <h1>Welcome {{ authStore.user.username }}</h1>
      </div>
    </header>

    <!-- Exibindo os volumes -->
    <div v-for="volume in volumes" :key="volume.id" class="product-card">
      <p class="product-description">Volume ID: {{ volume.id }} --- Por empacotar</p>

      <!-- Exibir o botão "Empacotar" ou a mensagem "Empacotado" -->
      <button
          v-if="!packedVolumes[volume.id]"
          @click="empacotar(volume.id)"
          class="checkbox-label">
        Empacotar
      </button>
      <span v-else class="packed-message">Empacotado</span>
    </div>
  </div>
</template>

<style scoped>
/* Container geral da loja */
.shop-container {
  font-family: Arial, sans-serif;
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

/* Estilo do card de produto */
.product-card {
  background-color: #fff; /* Fundo branco */
  border: 1px solid #ddd; /* Borda suave */
  border-radius: 8px; /* Cantos arredondados */
  padding: 20px; /* Espaçamento interno */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* Sombra suave */
  margin-bottom: 20px; /* Espaçamento entre os cards */
  display: flex; /* Flexbox para organizar o conteúdo */
  justify-content: space-between; /* Distribui o espaço entre os itens */
  align-items: center; /* Alinha os itens verticalmente no centro */
  position: relative; /* Necessário para o posicionamento da checkbox */
}

/* Título do volume */
.product-description {
  font-size: 1.1em;
  color: #333; /* Cor do texto */
  margin-bottom: 10px; /* Espaçamento entre as linhas */
}

/* Estilos para a checkbox e o texto */
.checkbox-label {
  display: flex;
  align-items: center; /* Alinha o conteúdo na vertical */
  justify-content: center; /* Centraliza o texto e o conteúdo */
  position: absolute; /* Posiciona o botão */
  right: 20px; /* Margem à direita */
  top: 50%; /* Centraliza verticalmente */
  transform: translateY(-50%); /* Ajusta para centralizar */

  /* Estilos aprimorados */
  background: linear-gradient(45deg, #6a11cb, #2575fc); /* Gradiente */
  color: white; /* Cor do texto */
  font-size: 16px; /* Tamanho do texto */
  font-weight: bold; /* Negrito para o texto */
  padding: 10px 20px; /* Espaçamento interno */
  border: none; /* Remove bordas */
  border-radius: 8px; /* Cantos arredondados */
  cursor: pointer; /* Ícone de ponteiro no hover */
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1); /* Sombra */
  transition: transform 0.2s ease, box-shadow 0.2s ease; /* Animações suaves */
}

/* Efeito de hover */
.checkbox-label:hover {
  transform: translateY(-50%) scale(1.05); /* Aumenta levemente o tamanho */
  box-shadow: 0px 6px 8px rgba(0, 0, 0, 0.15); /* Sombra mais intensa */
}

/* Efeito de clique */
.checkbox-label:active {
  transform: translateY(-50%) scale(0.95); /* Reduz levemente o tamanho */
  box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.2); /* Sombra mais fraca */
}

.checkbox {
  width: 32px; /* Tamanho maior para a checkbox */
  height: 32px; /* Tamanho maior para a checkbox */
  margin-right: 10px; /* Espaço entre a checkbox e o texto */
}
</style>
