<script setup lang="ts">
import { ref, reactive, defineProps } from "vue";
import { useRuntimeConfig, useFetch } from "#app"; // Adjust import based on your setup
import { useRouter } from "vue-router";
import {useAuthStore} from "~/store/auth-store";
import ClientOrder from './client-order.vue';
const router = useRouter();
const authStore = useAuthStore();

definePageMeta({
  middleware: ['auth']  // Adiciona o middleware de autenticação apenas para esta página
});

const props = defineProps({
  addedProducts: Array,
  order: Array
});

interface Product {
  id: number;
  name: string;
  price: number;
  category: number;
  description: string;
  quantity: number;
}

interface CartProduct {
  name:string;
  code: number;         // Código do produto
  quantity: number;     // Quantidade do produto no carrinho
  price:number;
  totalPrice: number;   // Preço total (quantidade * preço do produto)
}

interface ProductAmount{
  productId: number;
  amount: number;
  name:string;
  price:number;
}

interface Order{
  code:number;
  usernameClient: string;
  morada: string;
  precoTotal: number;
  products: ProductAmount[];
  isOrderCompleted: boolean;
  showAddressInput: boolean;
}

const addedProducts = ref<CartProduct[]>([]);
const totalPrice= ref(0);
const morada = ref("")
const order = ref<Order[]>([]);

function startOrder() {

  if(totalPrice.value <= 0){
    alert("Não é possível finalizar a ordem. Nao foram adicionados produtos ao carrinho.");
    return;
  }
  console.log(authStore.token);
  const productAmount: ProductAmount[] = addedProducts.value.map(product => ({
    productId: product.code,
    amount: product.quantity,
    name: product.name,
    price:product.price
  }));

  const orderCode = order.value.length + 1;
  order.value.push({
    code: orderCode,
    usernameClient: authStore.user.username,
    morada: "",
    precoTotal: totalPrice.value,
    products: productAmount,
    isOrderCompleted: false,
    showAddressInput: false
  });

  // Limpar apenas o carrinho de compras
  addedProducts.value = [];
  totalPrice.value = 0;
}




function addToCart(product: Product) {
  const existingProduct = addedProducts.value.find(p => p.code === product.code);
  if (existingProduct) {
    existingProduct.quantity++;
    existingProduct.totalPrice = existingProduct.quantity * product.price;
  } else {
    addedProducts.value.push({
      name: product.name,
      code: product.code,
      quantity: 1,
      price: product.price,
      totalPrice: product.price,
    });
  }
  totalPrice.value = addedProducts.value.reduce((total, product) => total + product.totalPrice, 0);
}

function removeFromCart(product: Product) {
  const existingProduct = addedProducts.value.find(p => p.code === product.code);

  if (existingProduct) {
    if (existingProduct.quantity > 1) {
      existingProduct.quantity--;
      existingProduct.totalPrice = existingProduct.quantity * product.price;
    } else if (existingProduct.quantity === 1) {
      const index = addedProducts.value.indexOf(existingProduct);
      if (index !== -1) {
        addedProducts.value.splice(index, 1);
      }
    }
  }

  totalPrice.value = addedProducts.value.reduce((total, product) => total + product.totalPrice, 0);
}


const config = useRuntimeConfig();
const api = config.public.API_URL;
const { data: products, error, refresh } = await useFetch(`${api}/product`, {
  method: "GET", // Ou o método apropriado
  headers: {
    Accept: "application/json",
    "Content-Type": "application/json",
    Authorization: `Bearer ${authStore.token}`, // Envia o token no cabeçalho
  },
});


function goToMyOrders(){
  router.push("./client-orders");
}
</script>

<template>
  <div class="shop-container">
    <header class="dashboard">
      <div class="dashboard-left">
        <h1>Online Shop</h1>
      </div>
      <div class="dashboard-right">
        <button class="orders-button" @click="goToMyOrders">My Orders</button>
      </div>
    </header>
        <ClientOrder :addedProducts="addedProducts" :order="order" />


    <main class="product-list">
      <div v-for="product in products" :key="product.id" class="product-card">
        <h2 class="product-name">{{ product.name }}</h2>
        <p class="product-description">{{ product.category }}</p>
        <p class="product-price">${{ product.price }}</p>
        <button class="add-to-cart" @click="addToCart(product)">Add to Cart</button>
      </div>
    </main>
    <button class="centered-button" @click="startOrder()">Finish Order</button>
    <section class="added-products">
      <h2 v-show="totalPrice != 0">Products Added to Cart</h2>
      <ul>
        <li v-for="product in addedProducts" :key="product.code">
          <button class="add-to-cart" @click="removeFromCart(product)">Remover</button>
          {{ product.name }} - Quantidade: {{ product.quantity }} - Preço Total: ${{ product.totalPrice.toFixed(2) }}
        </li>
      </ul>
    </section>
    <div v-show="totalPrice != 0">Total a pagar: {{ totalPrice.toFixed(2) }}</div>
  </div>
</template>

<style scoped>
/* Same styles as before */
.centered-button {
  display: block;  /* Torna o botão um elemento de bloco */
  margin: 0 auto;  /* Centraliza o botão horizontalmente */
  background-color: black;  /* Define a cor de fundo como preta */
  color: white;  /* Define a cor do texto como branca */
  border: none;  /* Remove a borda */
  padding: 10px 16px;  /* Espaçamento interno */
  border-radius: 4px;  /* Bordas arredondadas */
  cursor: pointer;  /* Altera o cursor para indicar que é clicável */
  font-size: 1em;  /* Tamanho da fonte */
  width: 10%;  /* Faz o botão ocupar a largura total do seu contêiner */
  margin-top: 20px;
}
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal {
  background: white;
  padding: 20px;
  border-radius: 5px;
  width: 300px;
}

.modal-actions {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
}

.shop-container {
  font-family: Arial, sans-serif;
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.dashboard {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  background-color: #f8f9fa;
  border-bottom: 1px solid #ddd;
  margin-bottom: 20px;
}

.dashboard-left h1 {
  margin: 0;
  font-size: 1.5em;
}

.dashboard-right {
  display: flex;
  gap: 10px;
}

.orders-button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 8px 12px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1em;
}

.orders-button:hover {
  background-color: #0056b3;
}

.product-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 20px;
  justify-content: center;
}

.product-card {
  width: 100%; /* Mantém o tamanho consistente */
  max-width: 250px; /* Define o tamanho máximo */
  margin: auto; /* Centraliza o card */
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 16px;
  text-align: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.product-name {
  font-size: 1.2em;
  margin: 12px 0;
}

.product-description {
  font-size: 0.9em;
  color: #666;
  margin-bottom: 12px;
}

.product-price {
  font-size: 1.1em;
  font-weight: bold;
  margin-bottom: 16px;
}

.add-to-cart {
  background-color: #28a745;
  color: white;
  border: none;
  padding: 10px 16px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1em;
}

.add-to-cart:hover {
  background-color: #218838;
}

.added-products {
  margin-top: 40px;
}

.added-products h2 {
  font-size: 1.5em;
  margin-bottom: 20px;
}

.added-products ul {
  list-style: none;
  padding: 0;
}

.added-products li {
  font-size: 1.1em;
  margin: 5px 0;
}
</style>
