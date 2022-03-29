<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission"/>
      <!--表单渲染 -->
      <ContractForm></ContractForm>
      <!--表格渲染-->
      <el-table
        ref="table"
        v-loading="crud.loading"
        :data="crud.data"
        size="small"
        style="width: 100%;"
        @selection-change="crud.selectionChangeHandler"
      >
        <el-table-column type="selection" width="55"/>
        <el-table-column prop="contractId" label="id"/>
        <el-table-column prop="name" label="合同名称"/>
        <el-table-column prop="no" label="合同编号"/>
        <el-table-column prop="partyA" label="合同甲方"/>
        <el-table-column prop="chargeA" label="甲方负责人"/>
        <el-table-column prop="partyB" label="合同乙方"/>
        <el-table-column prop="chargeB" label="乙方负责人"/>
        <el-table-column prop="type" label="合同类型">
          <template slot-scope="scope">
            {{ dict.label.contract_type[scope.row.type] }}
          </template>
        </el-table-column>
        <el-table-column prop="dept" label="部门/项目部"/>
        <el-table-column prop="recPay" label="合同收付类型">
          <template slot-scope="scope">
            {{ dict.label.contract_rec_pay[scope.row.recPay] }}
          </template>
        </el-table-column>
        <el-table-column prop="amount" label="合同金额/元"/>
        <el-table-column prop="status" label="合同状态">
          <template slot-scope="scope">
            {{ dict.label.contract_status[scope.row.status] }}
          </template>
        </el-table-column>
        <el-table-column prop="signTime" label="签订日期"/>
        <el-table-column prop="effectTime" label="合同生效日期"/>
        <el-table-column prop="endTime" label="合同结束日期"/>
        <el-table-column prop="keyTerms" label="关键条款"/>
        <el-table-column prop="comment" label="备注"/>
        <el-table-column prop="sort" label="排序"/>
        <el-table-column prop="top" label="置顶">
          <template slot-scope="scope">
            {{ dict.label.top[scope.row.top] }}
          </template>
        </el-table-column>
        <el-table-column prop="deleted" label="逻辑删除"/>
        <el-table-column prop="version" label="版本"/>
        <el-table-column prop="createBy" label="创建者"/>
        <el-table-column prop="createDeptBy" label="创建部门"/>
        <el-table-column prop="updateBy" label="更新者"/>
        <el-table-column prop="createTime" label="创建日期"/>
        <el-table-column prop="updateTime" label="更新时间"/>
        <el-table-column
          v-if="checkPer(['admin','contract:edit','contract:del'])"
          label="操作"
          width="150px"
          align="center"
        >
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination/>
    </div>
  </div>
</template>

<script>
import crudContract from '@/api/contract/contract'
import CRUD, {presenter, header, form, crud} from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'
import ContractForm from './form'
import Search from './search'


export default {
  name: 'Contract',
  components: {pagination, crudOperation, rrOperation, udOperation, ContractForm, Search},
  mixins: [presenter(), header(), crud()],
  dicts: ['contract_type', 'contract_rec_pay', 'contract_status', 'top'],
  cruds() {
    return CRUD({
      title: '合同：合同档案',
      url: 'api/cm/contract',
      idField: 'contractId',
      sort: 'contractId,desc',
      crudMethod: {...crudContract}
    })
  },
  data() {
    return {
      permission: {
        add: ['admin', 'contract:add'],
        edit: ['admin', 'contract:edit'],
        del: ['admin', 'contract:del']
      }
    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
