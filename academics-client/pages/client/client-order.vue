<script setup lang="ts">
import {ref, reactive, onMounted, defineProps} from 'vue';
import {useAuthStore} from "~/store/auth-store";
const config = useRuntimeConfig();
const authStore = useAuthStore();
const api = config.public.API_URL;


const props = defineProps({
  addedProducts: Array,
  order: Array
});

interface ProductAmountApi{
  productId: number;
  amount: number;
}

interface OrderApi{
  usernameClient: string;
  morada: string;
  precoTotal: number;
  products: ProductAmountApi[];
}
const newAddress = ref(''); // Valor do novo endereço
const cont = ref(0)
const completeOrder = async (order : OrderApi) => {
  if (newAddress.value.trim()) {
    // Supondo que você tenha algum método para processar a compra
    console.log('Order completed with address:', newAddress.value);

    order.isOrderCompleted = true;
    order.showAddressInput = false; // Esconde o campo de morada
    order.morada = newAddress;
    const productAmountApi: ProductAmountApi[] = order.products.map(product => ({
      code: 4,
      productId: product.productId,
      amount: product.amount
    }));
    const precoTotal = parseFloat(order.precoTotal.toFixed(2));

    const orderApi: OrderApi = {
      usernameClient: authStore.user.username,
      morada: order.morada,
      precoTotal: precoTotal,
      products: productAmountApi
    };
  console.log(orderApi)
    try {
      const response = await $fetch(`${api}/order`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
          Authorization: `Bearer ${authStore.token}`,
        },
        body: JSON.stringify(orderApi),  // Use JSON.stringify for the body
      });
      console.log("Response:", response)
          if (response.status == 200) {
          console.log("nice");
          }
    } catch (e) {
      console.error("login request failed: ", e);
    }

  } else {
    alert('Please enter a valid address');
  }
};


</script>

<template>
  <div class="orders-page">
    <h1>My Orders</h1>
    <div v-if="props.order.length > 0" class="orders-list">
      <div v-for="order in props.order" :key="order.code" class="order-card">
        <h2>Order #{{ order.code }}</h2>
        <ul>
          <li v-for="product in order.products" :key="product.productId">
            {{ product.amount }} x {{ product.name }} - {{(product.amount * product.price).toFixed(2)}}
          </li>
        </ul>

        <!-- Exibir o total do pedido -->
        <p class="order-total">Total: ${{ order.precoTotal.toFixed(2) }}</p>

        <!-- Checkbox para atualizar o endereço -->
        <div v-if="!order.showAddressInput && !order.isOrderCompleted">
          <button @click="order.showAddressInput = true">Finish Order</button>
        </div>
        <!-- Mostrar o campo de entrada para morada quando o botão for clicado -->
        <div v-if="order.showAddressInput && !order.isOrderCompleted">
          <label for="newAddress">Address:</label>
          <input type="text" id="newAddress" v-model="newAddress" placeholder="Enter your address" />

          <button @click="completeOrder(order)">Complete Order</button>
        </div>
        <!-- Mensagem após a compra ser concluída -->
        <div v-if="order.isOrderCompleted">
          <p>Your order has been completed and is being processed. Thank you for your purchase!</p>
        </div>
      </div>
    </div>
    <div v-else>
      <p>You have no orders yet.</p>
    </div>
  </div>
</template>

<style scoped>
.orders-page {
  font-family: Arial, sans-serif;
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}

.orders-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.order-card {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 16px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.order-card h2 {
  margin: 0 0 10px;
}

.order-card ul {
  list-style: none;
  padding: 0;
  margin: 10px 0;
}

.order-card li {
  margin: 5px 0;
}

.order-total {
  font-weight: bold;
  margin-top: 10px;
}
</style>