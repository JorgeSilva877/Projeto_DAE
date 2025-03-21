<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useAuthStore } from '~/store/auth-store';
import { useRouter } from 'vue-router';

const config = useRuntimeConfig();
const authStore = useAuthStore();
const router = useRouter();
const api = config.public.API_URL;

const orders = ref([]);
const loading = ref(true);
const error = ref('');

// Fetch orders from the API
const fetchOrders = async () => {
  try {
    // Validate user authentication
    if (!authStore.user?.username) {
      throw new Error('User is not logged in');
    }

    const username = authStore.user.username;

    const response = await $fetch(`${api}/client/${username}/orders`, {
      method: 'GET',
      headers: {
        "Content-Type": "application/json",
        Accept: 'application/json',
        Authorization: `Bearer ${authStore.token}`,
      },
    });

    if (response && Array.isArray(response)) {
      orders.value = response;
      console.log(orders)
    } else {
      throw new Error('Invalid response structure');
    }
  } catch (e) {
    console.error('Error fetching orders:', e);

    if (e.message === 'User is not logged in') {
      router.push('/login');
    } else {
      error.value = 'Failed to fetch orders. Please try again later.';
    }
  } finally {
    loading.value = false;
  }
};

// Fetch orders when the component is mounted
onMounted(fetchOrders);
</script>

<template>
  <div class="orders-page">
    <h1>My Orders</h1>
    <div v-if="loading">Loading orders...</div>
    <div v-else-if="error">{{ error }}</div>
    <div v-else-if="orders.length === 0">
      <p>You have no orders yet.</p>
    </div>
    
    <div v-else class="orders-list">
      <div v-for="order in orders" :key="order.code" class="order-card">
        <h2>Order #{{ order.code }}</h2>
        <ul>
          <li v-for="product in order.products" :key="product.productId">
            Quantity:{{ product.amount }}
             ProductID: {{ product.productId }}
          </li>
        </ul>
        <p class="order-total">Total: ${{ order.precoTotal.toFixed(2) }}</p>
        <p class="order-total">Estado: {{ order.estado }}</p>
      </div>
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
