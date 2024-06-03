<template>
  <div id="app">
    <router-view  :key="$route.fullPath + Math.random()" />
  </div>
</template>
