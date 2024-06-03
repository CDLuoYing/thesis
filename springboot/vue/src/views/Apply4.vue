<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
      <!--      <el-input style="width: 200px" placeholder="请输入" suffix-icon="el-icon-message" class="ml-5" v-model="email"></el-input>-->
      <!--      <el-input style="width: 200px" placeholder="请输入" suffix-icon="el-icon-position" class="ml-5" v-model="address"></el-input>-->
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd" v-if="user.role === 'ROLE_USER'">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量删除这些数据吗？"
          @confirm="delBatch"
      >
        <el-button type="danger" slot="reference" v-if="user.role != 'ROLE_USER'">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
      <!-- <el-upload action="http://localhost:9090/apply4/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">
        <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="exp" class="ml-5">导出 <i class="el-icon-top"></i></el-button> -->
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" v-if="user.role != 'ROLE_USER'"></el-table-column>
      <el-table-column prop="id" label="ID" width="80" sortable></el-table-column>
      <el-table-column prop="name" label="毕设题目"></el-table-column>
      <el-table-column prop="notes" label="备注"></el-table-column>
      <el-table-column label="文件"><template slot-scope="scope"><el-button type="primary" @click="download(scope.row.file)">下载</el-button></template></el-table-column>
      <el-table-column prop="user" label="学生"></el-table-column>
      <el-table-column prop="reviewer" label="审核人"></el-table-column>
      <el-table-column prop="state" label="审核状态"></el-table-column>
      <el-table-column prop="reason" label="审核描述"></el-table-column>
      <el-table-column prop="scores" label="评分"></el-table-column>

      <el-table-column label="操作"  width="180" align="center" v-if="user.role != 'ROLE_USER'">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)" v-if="scope.row.state === '待审核'">审核 <i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="del(scope.row.id)"
          >
            <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[2, 5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="信息" :visible.sync="dialogFormVisible" width="40%" :close-on-click-modal="false">
      <el-form label-width="140px" size="small" style="width: 85%;" :model="form" :rules="rules" ref="ruleForm">
        <el-form-item prop="name" label="毕设题目" v-if="user.role === 'ROLE_USER'">
          <el-input v-model="form.name" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item prop="notes" label="备注" v-if="user.role === 'ROLE_USER'">
          <el-input type="textarea" v-model="form.notes" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="file" label="材料"v-if="user.role === 'ROLE_USER'">
          <el-upload action="http://localhost:9090/file/upload" ref="file" :on-success="handleFileUploadSuccess">
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item prop="state" label="审核状态" v-if="user.role != 'ROLE_USER'">
          <el-radio v-model="form.state" label="审核通过">审核通过</el-radio>
          <el-radio v-model="form.state" label="审核不通过">审核不通过</el-radio>
        </el-form-item>
        <el-form-item prop="reason" label="审核描述" v-if="user.role != 'ROLE_USER'">
          <el-input type="textarea" v-model="form.reason" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="scores" label="评分" v-if="user.role != 'ROLE_USER' && form.state === '审核通过'">
          <el-input v-model="form.scores" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Apply2",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      name: "",
      form: {},
      dialogFormVisible: false,
      multipleSelection: [],
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      rules: {
        name: [
          { required: true, message: '请先选择毕设题目', trigger: 'blur'}
        ],
      },
      tasks: {},
      value: ''
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/apply4/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })


    },
    save() {
      if (typeof this.form.name === 'undefined'){
        this.$message.error("请选择毕设题目")
        return;
      }
      this.request.post("/apply4", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleAdd() {
      this.dialogFormVisible = true
      if (this.user.role === 'ROLE_USER'){
        this.request.get("/apply/user/" + this.user.username).then(res => {
          this.form.name = res.data.name
          this.form.projectsId = res.data.projectsId
          this.form.reviewer = res.data.reviewer
          this.$forceUpdate()
        })
      }
      console.log(this.form)
      this.$nextTick(() => {
        if(this.$refs.img) {
          this.$refs.img.clearFiles();
        }
        if(this.$refs.file) {
          this.$refs.file.clearFiles();
        }
      })
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
      this.$nextTick(() => {
        if(this.$refs.img) {
          this.$refs.img.clearFiles();
        }
        if(this.$refs.file) {
          this.$refs.file.clearFiles();
        }
      })
    },
    del(id) {
      this.request.delete("/apply4/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    delBatch() {
      if (!this.multipleSelection.length) {
        this.$message.error("请选择需要删除的数据")
        return
      }
      let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/apply4/del/batch", ids).then(res => {
        if (res.code === '200') {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    reset() {
      this.name = ""
      this.load()
    },
    handleSizeChange(pageSize) {
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    },
    handleFileUploadSuccess(res) {
      this.form.file = res
    },
    handleImgUploadSuccess(res) {
      this.form.img = res
    },
    download(url) {
      window.open(url)
    },
    exp() {
      window.open("http://localhost:9090/apply4/export")
    },
    handleExcelImportSuccess() {
      this.$message.success("导入成功")
      this.load()
    }
  }
}
</script>


<style>
.headerBg {
  background: #eee!important;
}
</style>
