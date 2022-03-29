<template>
  <div v-if="crud.props.searchToggle">
    <el-input
      v-model="query.blurry"
      clearable
      size="small"
      placeholder="请输入公司名称或公司编号"
      style="width: 240px;"
      class="filter-item"
      @change="crud.toQuery"
    />
    <el-input
      v-model="query.conPer"
      clearable
      size="small"
      placeholder="请输入联系人"
      style="width: 240px;"
      class="filter-item"
    />
    <el-input
      v-model="query.conTel"
      clearable
      size="small"
      placeholder="联系电话"
      style="width: 240px;"
      class="filter-item"
    />
<!--    <el-select-->
<!--      v-model="query.enabled"-->
<!--      clearable-->
<!--      size="small"-->
<!--      placeholder="状态"-->
<!--      class="filter-item"-->
<!--      style="width: 90px"-->
<!--      @change="crud.toQuery"-->
<!--    >-->
<!--      <el-option-->
<!--        v-for="item in enabledTypeOptions"-->
<!--        :key="item.key"-->
<!--        :label="item.display_name"-->
<!--        :value="item.key"-->
<!--      />-->
<!--    </el-select>-->
    <date-range-picker v-model="query.createTime" class="date-item" />
    <rrOperation />
  </div>
</template>

<script>
import { header } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import DateRangePicker from '@/components/DateRangePicker'
import { crud } from '@crud/crud'
export default {
  components: { rrOperation, DateRangePicker },
  mixins: [header(), crud()]
}
</script>
