<template>
  <el-form ref="form" :model="form" :rules="rules" style="margin-top: 6px;" size="small" label-width="100px">
    <el-form-item label="企业ID" prop="corpid">
      <el-input v-model="form.corpid" style="width: 40%"/>
      <span
        style="color: #C0C0C0;margin-left: 10px;">每个企业都拥有唯一的corpid，获取此信息可在管理后台“我的企业”－“企业信息”下查看“企业ID”（需要有管理员权限）</span>
    </el-form-item>
    <el-form-item label="凭证密钥" prop="corpsecret">
      <el-input v-model="form.corpsecret" style="width: 40%;"/>
      <span style="color: #C0C0C0;margin-left: 10px;">secret是企业应用里面用于保障数据安全的“钥匙”，每一个应用都有一个独立的访问密钥，为了保证数据的安全，secret务必不能泄漏。</span>
    </el-form-item>
    <el-form-item label="应用id" prop="agentid">
      <el-input v-model="form.agentid" type="agentid" style="width: 40%;"/>
      <span style="color: #C0C0C0;margin-left: 10px;">每个应用都有唯一的agentid。在管理后台->“应用与小程序”->“应用”，点进某个应用，即可看到agentid。</span>
    </el-form-item>
    <el-form-item label="备注" prop="comment">
      <el-input type="textarea" v-model="form.comment"  style="width: 40%;"/>
      <span style="color: #C0C0C0;margin-left: 10px;"></span>
    </el-form-item>
    <el-form-item label="">
      <el-button :loading="loading" size="medium" type="primary" @click="doSubmit">保存配置</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { get, update } from '@/api/tools/weixin'

export default {
  name: 'Config',
  data() {
    return {
      loading: false,
      form: { configId: 1, corpid: null, corpsecret: null, agentid: null, comment: null },
      rules: {
        corpid: [
          { required: true, message: '企业ID不能为空', trigger: 'blur' }
        ],
        corpsecret: [
          { required: true, message: '凭证密钥不能为空', trigger: 'blur' }
        ],
        agentid: [
          { required: true, message: '	应用id不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.init()
  },
  methods: {
    init() {
      get().then(res => {
        this.form = res.data
      })
    },
    doSubmit() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.loading = true
          update(this.form).then(res => {
            // this.$notify({
            //   title: '修改成功',
            //   type: 'success',
            //   duration: 2500
            // })
            console.log('企业微信配置')
            console.log(res)
            this.$message({
              message: res.msg,
              // eslint-disable-next-line eqeqeq
              type: res.code == 200 ? 'success' : 'warning'
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
    }
  }
}
</script>

<style scoped>

</style>
