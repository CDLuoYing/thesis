<template>
  <div style="color: #666;font-size: 14px;">
    <div style="padding-bottom: 20px">
      <b>您好！{{ user.nickname }}，欢迎使用本系统</b>
    </div>

    <el-card style="width: 100%;  margin: 10px 0">
      <h2 style="margin: 20px 0">项目公告</h2>
      <div >
        <el-timeline  reverse slot="reference" v-if="tableData.length === 0">
          <el-empty description="暂无数据"></el-empty>
        </el-timeline>
        <el-timeline  reverse slot="reference">
          <el-timeline-item v-for="item in tableData" :key="item.id" :timestamp="item.state">
            <el-popover
                placement="right"
                width="200"
                trigger="hover"
                :content="item.content">
              <span slot="reference">{{ item.name }}</span>
            </el-popover>
          </el-timeline-item>
        </el-timeline>
      </div>
    </el-card>

    <el-row style="margin-top: 10px">
      <el-col :span="24">
        <el-card>
          <div id="pie" style="width: 100%; height: 400px;text-align: center;"></div>
        </el-card>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: "Home",
  data() {
    return {
      activeNames: ['0'],
      tableData: [],
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.request.get("/projects").then(res => {
      this.tableData = res.data.filter(v => v.state === '未选').splice(0, 10)
    })
  },
  mounted() {  // 页面元素渲染之后再触发
    const pieOption = {
      title: {
        text: '各课题选择数统计',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          type: 'pie',
          radius: '55%',
          center: ['50%', '50%'],
          data: [],  // 填空
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        },
      ],

    };

    var pieDom = document.getElementById('pie');
    var pieChart = echarts.init(pieDom);


    this.request.get("/echarts/data").then(res => {
      pieOption.series[0].data = res.data
      pieChart.setOption(pieOption)

    })
  }
}
</script>
