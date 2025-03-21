<script setup lang="ts">

</script>


<template>
  <div class="shop-container">
  <header class="dashboard">
    <div class="dashboard-left">
      <h1>My Orders</h1>
    </div>
    <div class="dashboard-right">
    </div>
  </header>
  </div>

</template>


<style>
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

.shop-container {
  font-family: Arial, sans-serif;
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}
</style>