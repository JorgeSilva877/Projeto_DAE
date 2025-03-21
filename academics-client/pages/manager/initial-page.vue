<script setup lang="ts">
import { reactive } from "vue";
import { useRuntimeConfig, useFetch } from "#app";
import { useAuthStore } from "~/store/auth-store";


const authStore = useAuthStore();

definePageMeta({
  middleware: ['auth']
});

interface Product {
  code: string;
  name: string;
  category: string;
  limite: number;
  stock: number;
  price: number;
  warehouseId: number;
}

interface Employee {
  username: string,
  password: string,
  name: string,
  email: string,
  warehouseId: number,
}

// Reactive product object
const newProduct = reactive<Product>({
  code: "",
  name: "",
  category: "comida",
  limite: 0,
  stock: 0,
  price: 0.0,
  warehouseId: 1,
});

const newEmployee = reactive<Employee>({
  username: "",
  password: "",
  name: "",
  email: "",
  warehouseId: 0,
});

const config = useRuntimeConfig();
const api = config.public.API_URL;

const { data: employees, refresh: refreshEmployees } = await useFetch<Employee[]>(`${api}/employee`, {
  method: "GET",
  headers: {
    "Content-Type": "application/json",
    Accept: "application/json",
    Authorization: `Bearer ${authStore.token}`,
  },
});
const { data: products, error, refresh } = await useFetch<Product[]>(`${api}/product`, {
  method: "GET",
  headers: {
    Accept: "application/json",
    Authorization: `Bearer ${authStore.token}`,
  },
});

const { data: users} = await useFetch(`${api}/client`, {
  method: "GET",
  headers: {
    Accept: "application/json",
    Authorization: `Bearer ${authStore.token}`,
  },
});

const { data: orders} = await useFetch(`${api}/order`, {
  method: "GET",
  headers: {
    Accept: "application/json",
    Authorization: `Bearer ${authStore.token}`,
  },
});

const { data: sensores} = await useFetch(`${api}/sensor`, {
  method: "GET",
  headers: {
    Accept: "application/json",
    Authorization: `Bearer ${authStore.token}`,
  },
});

const { data: volumes} = await useFetch(`${api}/volume`, {
  method: "GET",
  headers: {
    Accept: "application/json",
    Authorization: `Bearer ${authStore.token}`,
  },
});

async function addProduct() {
  if (!authStore.token) {
    console.error("No authentication token found.");
    return;
  }

  try {
    console.log("Request Body:", JSON.stringify(newProduct));

    const response = await $fetch(`${api}/product/`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${authStore.token}`,
      },
      body: {
        name: newProduct.name,
        category: newProduct.category,
        limite: newProduct.limite,
        stock: newProduct.stock,
        price: parseFloat(newProduct.price.toString()), // Ensure price is sent as a float
        warehouseId: newProduct.warehouseId,
      },
    });

    console.log("Product added successfully:", response);
    products.value.push(response);

    Object.assign(newProduct, {
      name: "",
      category: "comida",
      limite: 0,
      stock: 0,
      price: 0.0,
      warehouseId: 1,
    });
  } catch (err) {
    console.error("Error adding product:", err);
  }
}

async function addEmployee() {
  try {
    console.log(newEmployee);

    const response = await $fetch(`${api}/employee/`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${authStore.token}`,
      },
      body: JSON.stringify(newEmployee)
    });

    console.log("Employee added successfully:", response);


    employees.value.push(response);


    Object.assign(newEmployee, {
      username: "",
      password: "",
      name: "",
      email: "",
      warehouseId: "",
    });
  } catch (err) {
    console.error("Error adding employee:", err);
  }
}

async function updateProduct(product: Product) {
  if (!authStore.token) {
    console.error("No authentication token found.");
    return;
  }

  try {
    if (!product.code) {
      console.error("Product code is missing or invalid.");
      return;
    }

    const response = await $fetch(`${api}/product/${product.code}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${authStore.token}`,
      },
      body: { ...product },
    });

    console.log("Product updated successfully:", response);


    await refresh();
  } catch (err) {
    console.error("Error updating product:", err);
  }
}
</script>

<template>
  <div class="manager-container">
    <header class="dashboard">
      <div class="dashboard-left">
        <h1>Manager Dashboard</h1>
      </div>
    </header>


    <section class="add-product">
      <h2>Add New Product</h2>
      <form @submit.prevent="addProduct">
        <label>
          Name:
          <input v-model="newProduct.name" type="text" required />
        </label>
        <label>
          Category:
          <select v-model="newProduct.category" required>
            <option value="comida">Comida</option>
            <option value="eletrodomesticos">Eletrodomésticos</option>
          </select>
        </label>
        <label>
          Limite:
          <input v-model.number="newProduct.limite" type="number" required />
        </label>
        <label>
          Stock:
          <input v-model.number="newProduct.stock" type="number" required />
        </label>
        <label>
          Price:
          <input v-model.number="newProduct.price" type="number" step="0.01" required />
        </label>
        <label>
          Warehouse ID:
          <input v-model.number="newProduct.warehouseId" type="number" required />
        </label>
        <button type="submit">Add Product</button>
      </form>
    </section>

    <h2>Product Inventory</h2>
    <section class="product-list">

      <div v-for="product in products" :key="product.code" class="product-card">
        <h3>{{ product.name }}</h3>


        <label>
          Name:
          <input v-model="product.name" type="text" @change="updateProduct(product)" />
        </label>
        <label>
          Category:
          <select v-model="product.category" @change="updateProduct(product)">
            <option value="comida">Comida</option>
            <option value="eletrodomesticos">Eletrodomésticos</option>
          </select>
        </label>
        <label>
          Limite:
          <input v-model.number="product.limite" type="number" @change="updateProduct(product)" />
        </label>
        <label>
          Stock:
          <input v-model.number="product.stock" type="number" @change="updateProduct(product)" />
        </label>
        <label>
          Price:
          <input v-model.number="product.price" type="number" step="0.01" @change="updateProduct(product)" />
        </label>
        <label>
          Warehouse ID:
          <input v-model.number="product.warehouseId" type="number" @change="updateProduct(product)" />
        </label>
      </div>
    </section>
    <h2>Users</h2>
    <section class="product-list">
      <div v-for="user in users" :key="user.username" class="product-card">
          <h4>Username: {{user.username}}</h4>
          <h4>Email: {{user.email}}</h4>
          <h4>Name: {{user.name}}</h4>
    </div>
    </section>
    <section class="add-product">
      <h2>Add New Employee</h2>
      <form @submit.prevent="addEmployee">
        <label>
          Username:
          <input v-model="newEmployee.username" type="text" required />
        </label>
        <label>
          Password:
          <input v-model="newEmployee.password" type="text" required />
        </label>
        <label>
          Name:
          <input v-model="newEmployee.name" type="text" required />
        </label>
        <label>
          Email:
          <input v-model="newEmployee.email" type="text" required />
        </label>
        <label>
          Warehouse ID:
          <input v-model.number="newEmployee.warehouseId" type="number" required />
        </label>
        <button type="submit">Add Employee</button>
      </form>
    </section>

    <h2>Orders</h2>
    <section class="product-list">
      <div v-for="order in orders" :key="order.code" class="product-card">
        <h4>Code: {{order.code}}</h4>
        <h4>Client: {{order.usernameClient}}</h4>
        <h4>Morada: {{order.morada}}</h4>
        <h4>Preço: {{order.precoTotal}}</h4>

      </div>
    </section>
    <h2>Sensores</h2>
    <section class="product-list">
      <div v-for="sensor in sensores" :key="sensor.id" class="product-card">
        <h4>ID: {{sensor.id}}</h4>
        <h4>Type: {{sensor.type}}</h4>
        <h4>Valor: {{sensor.valor}}</h4>
        <h4>VolumeId: {{sensor.volumeId}}</h4>
      </div>
    </section>

    <h2>Volumes</h2>
    <section class="product-list">
      <div v-for="volume in volumes" :key="volume.id" class="product-card">
        <h4>ID: {{volume.id}}</h4>
        <h4>Sensor ID: {{volume.sensorId}}</h4>
        <h4>Employee Username: {{volume.employeeUsername}}</h4>

      </div>
    </section>
  </div>
</template>

<style scoped>
.manager-container {
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

.add-product {
  margin-bottom: 40px;
}

.add-product form {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.add-product label {
  display: flex;
  flex-direction: column;
}

.add-product button {
  align-self: flex-start;
  background-color: #007bff;
  color: white;
  border: none;
  padding: 8px 12px;
  border-radius: 4px;
  cursor: pointer;
}

.add-product button:hover {
  background-color: #0056b3;
}

.product-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 20px;
}

.product-card {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 16px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.product-card h3 {
  margin-bottom: 10px;
}
</style>
