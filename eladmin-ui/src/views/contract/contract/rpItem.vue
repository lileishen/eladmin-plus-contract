<template>
  <div>
    <el-form
      size="small"
      label-width="120px"
      :inline="true"
      class="demo-form-inline"
      :label-position="labelPosition"
    >
      <template v-if="recPay=='0'">
      <el-form-item  label="应收项" prop="name">
        <template>
          <el-table
            :data="rtableData"
            :header-cell-style="{background:'#E9EBEF',textAlign:'left',color:'#5E5F61'}"
            style="width: 900px"
          >
            <el-table-column
              fixed
              prop="num"
              label="序号"
              width="50"
            />
            <el-table-column
              prop="name"
              label="应收款项"
              width="180"
            >
              <template slot-scope="scope" inline-template>
                <el-input v-model="scope.row.name" placeholder="请输入" />
              </template>
            </el-table-column>
            <el-table-column
              prop="condition"
              label="收款条件"
              width="180"
            >
              <template slot-scope="scope" inline-template>
                <el-input v-model="scope.row.condition" placeholder="请输入" />
              </template>
            </el-table-column>
            <el-table-column
              prop="proportion"
              label="收款比例"
              width="180"
            >
              <template slot-scope="scope" inline-template>
                <el-input
                  v-model="scope.row.proportion"
                  placeholder="请输入"
                  @change="(val)=>{handleRecChange(val, scope.$index,scope.row)}"
                />
              </template>
            </el-table-column>
            <el-table-column
              prop="amount"
              label="应收金额"
              width="180"
            >
              <template slot-scope="scope" inline-template>
                <el-input v-model="scope.row.amount" placeholder="请输入" />
              </template>
            </el-table-column>
            <el-table-column
              prop="estimatedTime"
              label="预计收款日期"
              width="180"
            >
              <template slot-scope="scope" inline-template>
                <el-date-picker
                  v-model="scope.row.estimatedTime"
                  type="date"
                  placeholder="选择日期"
                />
              </template>
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作"
              width="100"
            >
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="delRow(scope.$index, rtableData)"
                >删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </template>
      </el-form-item>
      <el-form-item  style="width: 900px">
        <el-button type="primary" icon="el-icon-plus" plain @click="addItem">新增一项</el-button>
        <el-button type="primary" plain @click="initDialog"><i class="el-icon-upload el-icon--left" />批量上传
        </el-button>
      </el-form-item>
      <el-form-item  style="width: 450px;text-align: left">
        <p>累计开票金额/元</p>
        <p>0元</p>
      </el-form-item>
      <el-form-item  style="width: 450px;text-align: left">
        <p>累计未开票金额/元</p>
        <p>{{ unRecToTal }}元</p>
      </el-form-item>
      <el-form-item  style="width: 450px;text-align: left">
        <p>累计收款金额/元</p>
        <p>0元</p>
      </el-form-item>
      <el-form-item  style="width: 450px;text-align: left">
        <p>累计未收款金额/元</p>
        <p>{{ unRecToTal }}元</p>
      </el-form-item>
      </template>
      <template v-if="recPay=='1'">
      <el-form-item  label="应付项" prop="name">
        <template>
          <el-table
            :data="ptableData"
            :header-cell-style="{background:'#E9EBEF',textAlign:'left',color:'#5E5F61'}"
            style="width: 900px"
          >
            <el-table-column
              fixed
              prop="num"
              label="序号"
              width="50"
            />
            <el-table-column
              prop="name"
              label="应付款项"
              width="180"
            >
              <template slot-scope="scope" inline-template>
                <el-input v-model="scope.row.name" placeholder="请输入" />
              </template>
            </el-table-column>
            <el-table-column
              prop="condition"
              label="付款条件"
              width="180"
            >
              <template slot-scope="scope" inline-template>
                <el-input v-model="scope.row.condition" placeholder="请输入" />
              </template>
            </el-table-column>
            <el-table-column
              prop="proportion"
              label="付款比例"
              width="180"
            >
              <template slot-scope="scope" inline-template>
                <el-input
                  v-model="scope.row.proportion"
                  placeholder="请输入"
                  @change="(val)=>{handlePayChange(val, scope.$index,scope.row)}"
                />
              </template>
            </el-table-column>
            <el-table-column
              prop="amount"
              label="应付金额"
              width="180"
            >
              <template slot-scope="scope" inline-template>
                <el-input v-model="scope.row.amount" placeholder="请输入" />
              </template>
            </el-table-column>
            <el-table-column
              prop="estimatedTime"
              label="预计付款日期"
              width="180"
            >
              <template slot-scope="scope" inline-template>
                <el-date-picker
                  v-model="scope.row.estimatedTime"
                  type="date"
                  placeholder="选择日期"
                />
              </template>
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作"
              width="100"
            >
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="delRow(scope.$index, ptableData)"
                >删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </template>
      </el-form-item>
      <el-form-item  style="width: 900px">
        <el-button type="primary" icon="el-icon-plus" plain @click="addPayItem">新增一项</el-button>
        <el-button type="primary" plain @click="initDialog"><i class="el-icon-upload el-icon--left" />批量上传
        </el-button>
      </el-form-item>
      <el-form-item  style="width: 450px;text-align: left">
        <p>累计付款金额/元</p>
        <p>0元</p>
      </el-form-item>
      <el-form-item  style="width: 450px;text-align: left">
        <p>累计未付款金额/元</p>
        <p>{{ unPaidToTal }}元</p>
      </el-form-item>
      </template>
    </el-form>
    <el-dialog
      width="600px"
      title="批量导入"
      :visible.sync="innerVisible"
      append-to-body
    >
      <el-divider />
      <!--步骤条-->
      <el-steps :active="activeIndex" align-center>
        <el-step title="选择Excel" />
        <el-step title="数据预览" />
        <el-step title="导入设置" />
        <el-step title="导入数据" />
      </el-steps>
      <template v-if="activeIndex==1">
        <!--上传组件-->
        <el-upload
          class="upload-demo"
          drag
          action="../api/rpItem/importData"
          multiple
          :show-file-list="false"
          accept=".xls,.xlsx"
          style="text-align: center;width:550px "
          :headers="headers"
          :on-success="handleSuccess1"
          :on-progress="handleProgress"
        >
          <i class="el-icon-upload" />
          <div class="el-upload__text">
            将文件拖到此处，或<em>点击上传</em>
            <div style="font-size: 12px;line-height: 30px;color: #b4b4b4">支持xls,xlsx等类型的文件</div>
          </div>
          <div
            slot="tip"
            class="el-upload__tip"
            style="text-align: left;font-size: 14px;font-weight: bold; color: #000000"
          ><i class="el-icon-download" />
            为了保证数据导入顺利，推荐您下载使用<em style="color: #0089FF;cursor:pointer;" @click="importTemplate">导入模版</em>，并按照规范示例录入数据。
          </div>
        </el-upload>
        <div class="note">
          <p class="note-title">上传的 Excel 表符合以下规范:</p>
          <ul class="note-content">
            <li class="note-content-item">文件大小不超过20MB</li>
            <li class="note-content-item">仅支持 (*.xls 和 *.xlsx)文件</li>
            <li class="note-content-item">请确保您需要导入的excel：不含合并单元格、sheet表头不含空单元格</li>
            <li class="note-content-item">批量导入的数据不支持以“内置变量”作为条件的过滤</li>
            <li class="note-content-item">导入文件不支持Excel公式计算，如SUM，=H2*J2等</li>
            <li class="note-content-item">目前导入图片/附件会转存，占用一定附件容量</li>
          </ul>
        </div>
      </template>
      <template v-if="activeIndex==2">
        <el-table
          :data="rtableDataTemp"
          :header-cell-style="{background:'#E9EBEF',textAlign:'left',color:'#5E5F61'}"
          style="width: 900px"
          border
        >
          <el-table-column
            fixed
            prop="num"
            label="序号"
            width="50"
          />
          <el-table-column
            prop="name"
            label="应收款项"
            width="180"
          />
          <el-table-column
            prop="condition"
            label="收款条件"
            width="180"
          />
          <el-table-column
            prop="proportion"
            label="收款比例"
            width="180"
          />
          <el-table-column
            prop="amount"
            label="应收金额"
            width="180"
          />
          <el-table-column
            prop="estimatedTime"
            label="预计收款日期"
            width="180"
          />
        </el-table>
        <div slot="footer" class="dialog-footer">
          <el-button @click="prev">上一步</el-button>
          <el-button type="primary" @click="loadTableData">导入</el-button>
        </div>
      </template>
      <template v-if="activeIndex==3">
        <el-result icon="success" title="完成">
        </el-result>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import rpItem from '@/api/contract/rpItem'
import {
  downloadFile
} from '@/utils/index'

import { getToken } from '@/utils/auth'

export default {
  name: 'RpItem',
  components: {},
  props: {
    contractAmount: {
      type: Number,
      required: true,
      default: 0
    },
    recPay: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      labelPosition: 'top',
      innerVisible: false,
      headers: { 'Authorization': getToken() },
      activeIndex: 1, // 时间线的位置
      rtableData: [{
        rpiId: null,
        num: 1,
        name: null,
        condition: null,
        proportion: null,
        amount: null,
        estimatedTime: null
      }], // 应收款项
      ptableData: [{
        rpiId: null,
        num: 1,
        name: null,
        condition: null,
        proportion: null,
        amount: null,
        estimatedTime: null
      }], // 应付款项
      ritems: [], // 应收项
      pitems: [], // 应付项
      rindex: 1,
      pindex: 1,
      unPaidToTal: 0, // 未付款合计
      unRecToTal: 0, // 未收款合计
      rtableDataTemp: [], // 临时应收款项目
      ptableDataTemp: [] // 临时应付款项目
    }
  },
  methods: {
    downloadFile,
    // 新增一行
    addItem() {
      this.rindex++
      const obj = {}
      obj.num = this.rindex
      obj.condition = ''
      obj.proportion = ''
      obj.amount = ''
      obj.estimatedTime = ''
      this.rtableData.push(obj)
    },
    // 删除行
    delRow(index, rows) {
      rows.splice(index, 1)
      this.getUnRecToTal() // 删除行，
    },
    // 根据应收比例计算应收金额
    handleRecChange(val, index, row) {
      this.rtableData[index].amount = this.contractAmount * val
      this.getUnRecToTal()
    },
    // 应收 新增一行
    addPayItem() {
      this.rindex++
      const obj = {}
      obj.num = this.rindex
      obj.condition = ''
      obj.proportion = ''
      obj.amount = ''
      obj.estimatedTime = ''
      this.ptableData.push(obj)
    },
    // 根据应付比例计算应付金额
    handlePayChange(val, index, row) {
      this.ptableData[index].amount = this.contractAmount * val
      this.getUnPayToTal()
    },
    // 未收款合计
    getUnRecToTal() {
      let total = 0
      this.rtableData.forEach(function(item, index) {
        total += item.amount
      })
      this.unRecToTal = total
    },
    // 未付款合计
    getUnPayToTal() {
      let total = 0
      this.ptableData.forEach(function(item, index) {
        total += item.amount
      })
      this.unPaidToTal = total
    },
    // 下载应收应付模版
    importTemplate() {
      // 获取应收应付类型
      var recPay = this.recPay
      // 发送下载模版的事件
      console.log('recPay')
      console.log(recPay)
      rpItem.importTemplate(recPay).then(result => {
        if (result.code === 200) {
          const link = document.createElement('a')
          link.style.display = 'none'
          link.href = '/api/common/download?fileName=' + encodeURI(result.msg) + '&delete=' + true
          link.setAttribute('importTemplate', result.msg)
          document.body.appendChild(link)
          link.click()
          document.body.removeChild(link)
        } else {
          this.$notify.error({
            title: '错误',
            message: '模版下载失败!请稍后重试'
          })
        }
      }).catch(() => {
      })
    },
    // 文件上传成功执行的方法
    handleSuccess1(res, file, fileList) {
      // 时间线加1
      this.activeIndex++
      // 对文件进行解析
      // 获取应收应付类型
      // 对Excel 数据进行解析
      if (res.code === 200) {
        if (this.recPay === '0') {
          this.rtableDataTemp = res.data
        } else {
          this.ptableDataTemp = res.data
        }
      }
    },
    handleProgress() {

    },
    // 导入
    loadTableData() {
      var that = this
      if (this.recPay === '0') {
        this.rtableDataTemp.forEach(function(item, index) {
          that.rtableData.push(item)
        })
      } else {
        this.ptableDataTemp.forEach(function(item, index) {
          that.ptableData.push(item)
        })
      }
      this.activeIndex++
      this.getUnRecToTal()
      this.getUnPayToTal()
    },
    // 上一步
    prev() {
      this.activeIndex = 1
      this.rtableDataTemp = []
      this.ptableDataTemp = []
    },
    // 打开批量上传
    initDialog() {
      this.innerVisible = true
      this.activeIndex = 1
      this.rtableDataTemp = []
      this.ptableDataTemp = []
    }
  }
}
</script>

<style scoped>
.my-btn {
  text-align: left;
}

/deep/ .el-upload {
  width: 100%;
}

/deep/ .el-upload .el-upload-dragger {
  width: 100%;
}

.note-content-item {
  line-height: 20px;
  color: #8F9192;
  font-size: 14px;
}

</style>
