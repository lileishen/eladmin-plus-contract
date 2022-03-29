<template>
  <div>
    <el-upload
      class="upload-demo"
      drag
      :action="url"
      :on-change="handleChange"
      :file-list="fileList"
      name="file"
      :on-success="handlSuccess"
      :on-preview="handlePreview"
      :on-remove="handleRemove"
      :before-remove="beforeRemove"
      :multiple="false"
      :headers="headers"
    >
      <i class="el-icon-upload" />
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
    </el-upload>
  </div>
</template>

<script>

import { Base64 } from 'js-base64'
import { delById } from '@/api/contract/file'
import { getToken } from '@/utils/auth'
export default {
  name: 'UploadFile',
  props:
    {
      value: {
        type: Array,
        default: () => []
      }
    },
  data() {
    return {
      fileList: [],
      headers: { 'Authorization': getToken() },
      url: '../api/cm/file/upload'
    }
  },
  mounted: function() {
    this.loadFile(this.value)
  },
  methods: {
    handleChange(file, fileList) {
      console.log('handleChange')
    },
    handlSuccess(res, file) {
      this.fileList.push(res.data)
      this.$emit('input', this.fileList)
    },
    handlePreview(file) { // 文件预览
      var url = 'http://localhost:8001/file/' + file.type + '/' + file.realName // 要预览文件的访问地址
      window.open('http://127.0.0.1:8012/onlinePreview?url=' + encodeURIComponent(Base64.encode(url)))
    },
    loadFile(value) {
      this.fileList = value
    },
    handleRemove(file, fileList) {
      this.value = fileList
      this.$emit('input', fileList)
      // 数据库发送请求 删除
      console.log(file.id)
      delById(file.id).then((res) => {
        console.log('删除文件后')
        console.log(res)
      })
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name},移除后文件将不可恢复？`)
    }
  }
}
</script>

<style scoped>

</style>
