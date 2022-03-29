<template>
  <div>
    <el-cascader
      v-model="address"
      size="large"
      :options="options"
      @change="handleChange"
      :style="'width:'+width+'px'"
    />
  </div>
</template>

<script>
import { regionDataPlus } from 'element-china-area-data'
export default {
  name: 'AddressCascader', // 省市区三级级联
  props: {
    width: {
      type: Number,
      required: true
    },
    address: {
      type: Array,
      default: function() {
        return ['110000', '110100', '110101']
      }
    }
  },
  data() {
    return {
      options: regionDataPlus
    }
  },
  methods: {
    handleChange(value) {
      console.log(value)
    }
  }
}
</script>
