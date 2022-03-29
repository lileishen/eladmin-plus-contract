<template>
  <div class="app-container">
    <mavon-editor v-model="from"  :ref="md" :style="'height:' + height" @imgAdd="imgAdd" />
  </div>
</template>

<script>
import { upload } from '@/utils/upload'
import { mapGetters } from 'vuex'
export default {
  name: 'Markdown',
  data() {
    return {
      height: document.documentElement.clientHeight - 200 + 'px',
      from: null
    }
  },
  computed: {
    ...mapGetters([
      'imagesUploadApi',
      'baseApi'
    ])
  },
  mounted() {
    const that = this
    window.onresize = function temp() {
      that.height = document.documentElement.clientHeight - 200 + 'px'
    }
  },
  methods: {
    imgAdd(pos, $file) {
      upload(this.imagesUploadApi, $file).then(res => {
        console.log('上传图片')
        const data = res.data
        const url = this.baseApi + '/file/' + data.type + '/' + data.realName
        this.$refs.md.$img2Url(pos, url)
      })
    }
  }
}
</script>

<style scoped>
</style>
