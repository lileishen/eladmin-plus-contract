<template>
  <!--表单组件-->
  <el-dialog
    :close-on-click-modal="false"
    :before-close="crud.cancelCU"
    :visible.sync="crud.status.cu > 0"
    :title="crud.status.title"
    width="1000px"
    class="my-dialog"
  >
    <el-form
      ref="form"
      :model="form"
      :rules="rules"
      size="small"
      label-width="120px"
      :inline="true"
      class="demo-form-inline my-form"
      :label-position="labelPosition"
    >
      <el-form-item label="合同名称" prop="name">
        <el-input v-model="form.name" style="width: 450px;" />
      </el-form-item>
      <el-form-item label="合同编号" class="my-form-item" prop="no">
        <el-input v-model="form.no" style="width: 450px;" :disabled="true" />
      </el-form-item>
      <el-form-item label="合同甲方" prop="partyA">
        <el-select
          v-model="form.partyA"
          filterable
          placeholder="请选择"
          style="width: 450px"
          @change="handChangeA($event)"
        >
          <el-option
            v-for="item in sideOptions"
            :key="item.sideId"
            :label="item.name"
            :value="item.sideId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="甲方负责人" prop="chargeA">
        <el-input v-model="form.chargeA" style="width: 450px;" />
      </el-form-item>
      <el-form-item label="合同乙方" prop="partyB">
        <el-select
          v-model="form.partyB"
          filterable
          placeholder="请选择"
          style="width: 450px"
          @change="handChangeB($event)"
        >
          <el-option
            v-for="item in sideOptions"
            :key="item.sideId"
            :label="item.name"
            :value="item.sideId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="乙方负责人" prop="chargeB">
        <el-input v-model="form.chargeB" style="width: 450px;" />
      </el-form-item>
      <el-form-item label="合同类型">
        <!--        <el-radio v-for="item in dict.contract_type" :key="item.id" v-model="form.type" :label="item.value">-->
        <!--          {{ item.label }}-->
        <!--        </el-radio>-->
        <el-select
          v-model="form.type"
          filterable
          placeholder="请选择"
          style="width: 450px"
        >
          <el-option
            v-for="item in dict.contract_type"
            :key="item.id"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="部门/项目部" prop="dept">
        <treeselect
          v-model="form.dept"
          :options="deptOptions"
          style="width: 450px;"
          :load-options="loadDepts"
          placeholder="请选择"
        />
      </el-form-item>
      <el-form-item label="收付类型" prop="recPay" style="width: 900px;">
        <el-radio-group v-model="form.recPay">
        <el-radio v-for="item in dict.contract_rec_pay" :key="item.id"  :label="item.value">
          {{ item.label }}
        </el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="合同金额(元)" prop="amount">
        <el-input-number v-model="form.amount" controls-position="right" style="width: 450px;" />
      </el-form-item>
      <el-form-item label="合同状态" prop="status">
        <el-select v-model="form.status" filterable placeholder="请选择" style="width: 450px;">
          <el-option
            v-for="item in dict.contract_status"
            :key="item.id"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="签订日期" prop="signTime">
        <el-date-picker v-model="form.signTime" type="date" style="width: 450px;" />
      </el-form-item>
      <el-form-item label="有效期" prop="validTime">
        <el-input
          v-model="form.validTime"
          style="width: 450px;"
          @change="validTimeChange"
        >
          <template slot="append">年</template>
        </el-input>
      </el-form-item>
      <el-form-item label="合同生效日期" prop="effectTime">
        <el-date-picker v-model="form.effectTime" type="date" style="width: 450px;" @change="effectTimeChange" />
      </el-form-item>
      <el-form-item label="合同结束日期">
        <el-date-picker
          v-model="form.endTime"
          type="date"
          style="width: 450px;"
        />
      </el-form-item>
      <el-form-item class="my-sort" label="排序" style="width: 900px;">
        <el-input-number v-model="form.sort" controls-position="right" />
      </el-form-item>
      <RpItem :contract-amount="form.amount" :rec-pay="form.recPay" />
      <el-form-item label="关键条款">
        <el-input v-model="form.keyTerms" :rows="3" type="textarea" style="width: 880px;" />
      </el-form-item>
      <el-form-item label="合同附件" style="width: 900px;">
        <!--   上传文件-->
        <el-form-item  label="上传合同附件">
          <UploadFile  v-model="form.files"/>
        </el-form-item>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="form.comment" :rows="1" type="textarea" style="width: 880px;" />
      </el-form-item>
      <el-form-item label="协作人员">
        <el-select
          v-model="form.associateOptions"
          multiple
          filterable
          default-first-option
          placeholder="请选择"
          class="my-select"
          style="width: 880px"
        >
          <el-option
            v-for="item in associateOptions"
            :key="item.username"
            :label="item.nickName"
            :value="item.username"
          >
            <span class="nickName">{{ item.nickName }}</span>
            <span class="phone">{{ item.phone }}</span>
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="crud.cancelCU">取消</el-button>
      <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { presenter, header, form, crud } from '@crud/crud'
import crudSide from '@/api/contract/side'
import Treeselect, { LOAD_CHILDREN_OPTIONS } from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import crudDept from '@/api/system/dept'
import crudUser from '@/api/system/user'
import RpItem from './rpItem'
import { getToken } from '@/utils/auth'
import UploadFile from '@/components/UploadFile/index'
const defaultForm = {
  contractId: null,
  name: null,
  no: null,
  partyA: null,
  chargeA: null,
  partyB: null,
  chargeB: null,
  type: null,
  dept: null,
  recPay: '1',
  amount: null,
  status: null,
  signTime: null,
  effectTime: null,
  endTime: 2,
  keyTerms: null,
  comment: null,
  sort: null,
  top: null,
  deleted: null,
  version: null,
  createBy: null,
  createDeptBy: null,
  updateBy: null,
  createTime: null,
  updateTime: null,
  validTime: 2,
  files: []
}
export default {
  name: 'ContractForm',
  mixins: [presenter(), header(), form(defaultForm), crud()],
  dicts: ['contract_type', 'contract_rec_pay', 'contract_status', 'top'],
  components: { Treeselect, RpItem, UploadFile },
  headers: { 'Authorization': getToken() },
  data() {
    return {
      labelPosition: 'top',
      outerVisible: false,
      innerVisible: false,
      fileList3: [],
      fileData: '',
      // form: {
      //   chargeA: null,
      //   effectTime: null,
      //   validTime: 2,
      //   endTime: null,
      //   recPay: '1'
      // },
      rules: {
        name: [
          { required: true, message: '合同名称不能为空', trigger: 'blur' }
        ],
        no: [
          // { required: true, message: '合同编号不能为空', trigger: 'blur' }
        ],
        partyA: [
          { required: true, message: '合同甲方不能为空', trigger: 'blur' }
        ],
        chargeA: [
          { required: true, message: '甲方负责人不能为空', trigger: 'blur' }
        ],
        partyB: [
          { required: true, message: '合同乙方不能为空', trigger: 'blur' }
        ],
        chargeB: [
          { required: true, message: '乙方负责人不能为空', trigger: 'blur' }
        ],
        dept: [
          { required: true, message: '部门/项目部不能为空', trigger: 'blur' }
        ],
        recPay: [
          { required: true, message: '合同收付类型不能为空', trigger: 'blur' }
        ],
        amount: [
          { required: true, message: '合同金额不能为空', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '合同状态不能为空', trigger: 'blur' }
        ],
        signTime: [
          { required: true, message: '签订日期不能为空', trigger: 'blur' }
        ],
        effectTime: [
          { required: true, message: '合同生效日期不能为空', trigger: 'blur' }
        ]
      },
      sideOptions: [],
      deptOptions: [],
      associateOptions: [{
        value: 'HTML',
        label: 'HTML'
      }, {
        value: 'CSS',
        label: 'CSS'
      }, {
        value: 'JavaScript',
        label: 'JavaScript'
      }]
    }
  },
  created() {
  },
  mounted() {
    this.getSides()
    this.getDeptS()
    this.getUsers()
  },
  methods: {
    getSides() {
      crudSide.list().then((res) => {
        if (res.code === 200) {
          this.sideOptions = res.data
        }
      })
    },
    handChangeA(data) {
      this.sideOptions.forEach(item => {
        if (item.sideId === data) {
          this.form.chargeA = item.conPer
        }
      })
    },
    handChangeB(data) {
      this.sideOptions.forEach(item => {
        if (item.sideId === data) {
          this.form.chargeB = item.conPer
        }
      })
    },
    getDeptS() {
      crudDept.getDepts({ enabled: true }).then(res => {
        this.deptOptions = res.content.map(function(obj) {
          if (obj.hasChildren) {
            obj.children = null
          }
          return obj
        })
      })
    },
    // 获取弹窗内部门数据
    loadDepts({ action, parentNode, callback }) {
      if (action === LOAD_CHILDREN_OPTIONS) {
        crudDept.getDepts({ enabled: true, pid: parentNode.id }).then(res => {
          parentNode.children = res.content.map(function(obj) {
            if (obj.hasChildren) {
              obj.children = null
            }
            return obj
          })
          setTimeout(() => {
            callback()
          }, 100)
        })
      }
    },
    // 根据有效期计算合同结束时间
    validTimeChange(value) {
      var date = this.form.effectTime
      this.form.endTime = new Date().setTime(date.getTime() + value * 365 * 3600 * 1000 * 24)
    },
    // 合同生效期签订改变事件
    effectTimeChange(value) {
      // 获取有效期
      const validTime = this.form.validTime
      var date = value
      this.form.endTime = new Date().setTime(date.getTime() + validTime * 365 * 3600 * 1000 * 24)
    },
    getUsers() {
      crudUser.getUsers().then((res) => {
        this.associateOptions = res.data
      })
    }
  }
}
</script>

<style scoped>

.nickName {
  text-overflow: ellipsis;
  overflow: hidden;
}
.phone {
  font-size: 12px;
  color: #b4b4b4;
}

.my-dialog{
  text-align: center;
}
.my-form >>> .el-form-item{
  text-align: left;
}

.my-sort{
  text-align: left;
}

.my-dialog >>>.el-dialog__header{
  text-align: left;
}

</style>
