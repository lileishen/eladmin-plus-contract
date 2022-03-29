<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <Search />
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="800px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="120px">
          <el-form-item label="相对方名称" prop="name">
            <el-input v-model="form.name" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="编号" prop="no">
            <el-input v-model="form.no" :disabled="true" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="类型" prop="type">
            <el-radio v-for="item in dict.side_type" :key="item.id" v-model="form.type" :label="item.id">{{ item.label }}</el-radio>
          </el-form-item>
          <el-form-item label="联系人" prop="conPer">
            <el-input v-model="form.conPer" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="联系电话" prop="conTel">
            <el-input v-model="form.conTel" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="开户银行" prop="bank">
            <el-input v-model="form.bank" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="银行账号" prop="bankAcc">
            <el-input v-model="form.bankAcc" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="地址" prop="address">
            <el-cascader
              v-model="form.address"
              size="large"
              :options="options"
              :style="'width:370px'"
              @change="handleChange"
            />
          </el-form-item>
          <el-form-item label="详细地址" prop="addrDetail">
            <el-input v-model="form.addrDetail" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="状态" prop="enabled">
            <!--            <el-radio-group v-model="form.enabled">-->
            <!--            <el-radio v-for="item in dict.common_status" :key="item.id"  :value="item.value">{{ item.label }}</el-radio>-->
            <!--            </el-radio-group>-->
            <el-radio-group v-model="form.enabled">
              <el-radio v-for="item in dict.common_status" :key="item.id" :label="item.value">{{ item.label }}</el-radio>
            </el-radio-group>
          </el-form-item>
          <!--   上传文件-->
          <el-form-item  label="上传附件">
            <UploadFile  v-model="form.files"/>
          </el-form-item>
          <el-form-item label="备注">
            <el-input v-model="form.comment" :rows="3" type="textarea" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
<!--        <el-table-column prop="sideId" label="id" />-->
        <el-table-column prop="name" label="相对方名称" />
        <el-table-column prop="no" label="编号" />
        <el-table-column prop="typeVal" label="类型">
        </el-table-column>
        <el-table-column prop="conPer" label="联系人" />
        <el-table-column prop="conTel" label="联系电话" />
        <el-table-column prop="bank" label="开户银行" />
        <el-table-column prop="bankAcc" label="银行账号" />
        <el-table-column prop="address" label="地址" />
        <el-table-column prop="addrDetail" label="详细地址" />
        <el-table-column prop="comment" label="备注" />
        <el-table-column prop="enabled" label="状态" />
        <el-table-column prop="createBy" label="创建者" />
        <el-table-column prop="createDeptBy" label="创建部门" />
        <el-table-column prop="updateBy" label="更新者" />
        <el-table-column prop="createTime" label="创建日期" />
        <el-table-column prop="updateTime" label="更新时间" />
        <el-table-column v-if="checkPer(['admin','side:edit','side:del'])" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import Search from './search'
import crudSide from '@/api/contract/side'
import { getToken } from '@/utils/auth'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'
import AddressCascader from '@/components/AddressCascader/index'
import UploadFile from '@/components/UploadFile/index'
import { regionDataPlus } from 'element-china-area-data'
import { mapGetters } from 'vuex'
const defaultForm = { sideId: null, name: null, no: null, type: 9, conPer: null, conTel: null, bank: null, bankAcc: null, address: null, addrDetail: null, comment: null, enabled: '1', files: [], deleted: 0, version: 0 }
export default {
  name: 'Side',
  // eslint-disable-next-line vue/no-unused-components
  components: { Search, pagination, crudOperation, rrOperation, udOperation, AddressCascader, UploadFile },
  mixins: [presenter(), header(), form(defaultForm), crud()], // 混合模式
  dicts: ['side_type', 'common_status'],
  cruds() {
    return CRUD({ title: '合同：合同相对方', url: 'api/cm/side', idField: 'sideId', sort: 'sideId,desc', crudMethod: { ...crudSide }})
  },
  data() {
    return {
      headers: { 'Authorization': getToken() },
      permission: {
        add: ['admin', 'side:add'],
        edit: ['admin', 'side:edit'],
        del: ['admin', 'side:del']
      },
      rules: {
        name: [
          { required: true, message: '相对方名称不能为空', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '类型', trigger: 'blur' }
        ],
        conPer: [
          { required: true, message: '联系人不能为空', trigger: 'blur' }
        ],
        conTel: [
          { required: true, message: '联系电话不能为空', trigger: 'blur' }
        ],
        address: [
          { required: true, message: '地址不能为空', trigger: 'blur' }
        ],
        addrDetail: [
          { required: true, message: '详细地址不能为空', trigger: 'blur' }
        ]
      },
      fileList: [], // 文件列表
      options: regionDataPlus // 地址组件数据
    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    },
    uploadSuccess(response) {
      if (response.code === 200) {
        this.msgSuccess(response.msg)
      } else {
        this.msgError(response.msg)
      }
    },
    handleChange(value) {
      console.log(value)
    }
  },
  computed: {
    ...mapGetters([
      'baseApi',
      'fileUploadApi'
    ])
  },
  created() {
  }
}
</script>

<style scoped>

</style>
