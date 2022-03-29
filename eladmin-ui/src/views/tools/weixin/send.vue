<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" style="margin-top: 6px;" size="small" label-width="100px">
      <el-form-item label="标题" prop="subject">
        <el-input v-model="form.subject" style="width: 646px" />
      </el-form-item>
      <!--      <el-form-item-->
      <!--        v-for="(domain, index) in tos"-->
      <!--        :key="domain.key"-->
      <!--        :label="'接收人' + (index === 0 ? '': index)"-->
      <!--      >-->
      <!--        <el-input v-model="domain.value" style="width: 550px" />-->
      <!--        <el-button icon="el-icon-plus" @click="addDomain" />-->
      <!--        <el-button style="margin-left:0;" icon="el-icon-minus" @click.prevent="removeDomain(domain)" />-->
      <!--      </el-form-item>-->
      <el-form-item
        v-for="(domain, index) in tos"
        :key="domain.key"
        :label="'接收人' + (index === 0 ? '': index)"
      >
        <el-autocomplete
          v-model="tos[index].userid"
          popper-class="my-autocomplete"
          :fetch-suggestions="querySearchAsync"
          placeholder="请输入内容"
          style="width: 550px"
          @select="((item) => {handleSelect(item, index)})"
        >
          <i
            slot="suffix"
            class="el-icon-edit el-input__icon"
          />
          <template slot-scope="{ item }">
            <div class="name">工号：{{ item.userid }}</div>
            <span class="addr">姓名{{ item.name }}</span>
            <span class="addr">联系电话：{{ item.mobile }}</span>
          </template>
        </el-autocomplete>
        <el-button icon="el-icon-plus" @click="addDomain" />
        <el-button style="margin-left:0;" icon="el-icon-minus" @click.prevent="removeDomain(domain)" />
      </el-form-item>
      <MarkDownEdit></MarkDownEdit>
      <el-button :loading="loading" style="margin-left:1.6%;" size="medium" type="primary" @click="doSubmit">发送消息</el-button>
    </el-form>
  </div>
</template>

<script>
import { send } from '@/api/tools/weixin'
// import { upload } from '@/utils/upload'
import { mapGetters } from 'vuex'
import { getUserList } from '@/api/weixin/cgi'
// import E from 'wangeditor'
import MarkDownEdit from '@/components/MarkDownEdit'
export default {
  name: 'Send',
  components: {
    MarkDownEdit
  },
  data() {
    return {
      loading: false,
      form: { subject: '', tos: [], content: '' },
      tos: [{
        userid: '',
        name: ''
      }],
      rules: {
        tos: [
          { required: true, message: '接收人不能为空', trigger: 'blur' }
        ]
      },
      restaurants: [],
      state: '',
      timeout: null
    }
  },
  computed: {
    ...mapGetters([
      'imagesUploadApi'
    ])
  },
  async mounted() { // 通常是初始化页面完成后
    this.restaurants = this.loadUserAll()
  },
  methods: {
    removeDomain(item) {
      var index = this.tos.indexOf(item)
      if (index !== -1 && this.tos.length !== 1) {
        this.tos.splice(index, 1)
      } else {
        this.$message({
          message: '请至少保留一位联系人',
          type: 'warning'
        })
      }
    },
    addDomain() {
      this.tos.push({
        userid: '',
        name: ''
      })
    },
    doSubmit() {
      const _this = this
      this.$refs['form'].validate((valid) => {
        this.form.tos = []
        if (valid) {
          let sub = false
          this.tos.forEach(function(data, index) {
            if (data.userid === '') {
              _this.$message({
                message: '接收人不能为空',
                type: 'warning'
              })
              sub = true
            } else {
              _this.form.tos.push(data.userid)
            }
          })
          if (sub) { return false }
          this.loading = true
          console.log(this.form)
          send(this.form).then(res => {
            this.$notify({
              title: '发送成功',
              type: 'success',
              duration: 2500
            })
            this.loading = false
          }).catch(err => {
            this.loading = false
            console.log(err.response.data.message)
          })
        } else {
          return false
        }
      })
    },
    querySearchAsync(queryString, cb) {
      var restaurants = this.restaurants
      var results = queryString ? restaurants.filter(this.createStateFilter(queryString)) : restaurants

      clearTimeout(this.timeout)
      this.timeout = setTimeout(() => {
        cb(results)
      }, 3000 * Math.random())
    },
    createStateFilter(queryString) {
      return (state) => {
        return (state.name.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
      }
    },
    handleSelect(item, index) {
      this.tos[index].userid = item.userid
      this.tos[index].name = item.name
      // 添加联系人
    },
    loadUserAll() { // 加载全部企业微信用户
      const params = { department_id: 1, fetch_child: 0 }
      getUserList(params).then(res => {
        this.restaurants = res.data
      })
    }
  }
}
</script>

<style scoped>
  .editor{
    text-align:left;
    margin: 20px;
    width: 730px;
  }
 ::v-deep .w-e-text-container {
    height: 360px !important;
  }

  .my-autocomplete li {
    line-height: normal;
    padding: 7px;
  }

  .my-autocomplete li .name {
    text-overflow: ellipsis;
    overflow: hidden;
  }

  .my-autocomplete li .addr {
    font-size: 12px;
    color: #b4b4b4;
  }

  .my-autocomplete li .highlighted .addr {
    color: #ddd;
  }
</style>
