<template>
  <div style="margin-bottom: 100px">
    <div style="margin: 10px 0">
      <el-carousel height="450px" :interval="10000">
        <el-carousel-item v-for="item in imgs" :key="item">
          <img :src="item" alt="" style="width: 100%; height: 100%">
        </el-carousel-item>
      </el-carousel>
    </div>

    <div style="padding: 10px 0; margin: 10px 0; border-bottom: 1px solid #ccc; color: #1E90FF; font-size: 20px">
      系统简介
    </div>
    <div style="margin: 10px 0; line-height: 30px; color: #666;">
      系统简介系统简介系统简介系统简介系统简介系统简介系统简介系统简介系统简介系统简介系统简介系统简介系统简介系统简介系统简介
      系统简介系统简介系统简介系统简介系统简介系统简介系统简介系统简介系统简介系统简介系统简介系统简介系统简介系统简介系统简介
      系统简介系统简介系统简介系统简介系统简介系统简介系统简介系统简介系统简介系统简介系统简介系统简介系统简介系统简介系统简介
    </div>
  </div>
</template>

<script>
export default {
  name: "FrontHome",
  data() {
    return {
      imgs: [
        require('../../assets/111.webp'),
        require('../../assets/2222.webp'),
      ],
      files: []
    }
  },
  created() {
    // this.request.get("/").then(res => {
    //   this.files = res.data
    // })
  },
  methods: {}
}
</script>

<style>

</style>
