<template>
  <div class="block">
    <ul class="time-line">
      <li class="time-line-item" v-for="(item,index) in activities" :key="index">
        <i :class="[{active:(index==activeIndex)}]"></i>
        <span>{{ item.content }}</span>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'HorizontalTimeLine',
  props: {
    activeIndex: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      reverse: true,
      activities: [{
        content: '选择EXCEL表'
      }, {
        content: '数据预览'
      }, {
        content: '导入设置'
      },
      {
        content: '导入数据'
      }
      ]
    }
  }
}
</script>

<style scoped>
ul,li{
  list-style: none;
}
.time-line{ display: flex;
  flex-direction: row;/*这里可以不写，flex布局默认方向横向即row*/
  justify-content: center;
  align-items: center;}
.time-line-item { padding: 20px 0px; width: 140px; overflow: hidden}

.time-line-item > i{
  display: block;
  width: 20px;
  height: 20px;
  border: 3px solid #cccccc;
  margin: auto;
  border-radius: 50%;
  position: relative;
  z-index: 1;
}

.active {
  background: #0089FF;
  border: 3px solid #0089FF !important;
}
.time-line-item >i:after{
  content: '';
  width: 65px;
  height: 2px;
  border-bottom: 2px solid #dcdfe6;
  display: inline-block;
  position: absolute;
  top: 7px;
  left: 20px;
  z-index: 100;
}
.time-line-item >i:before{
   content: '';
   width: 68px;
   height: 2px;
   border-bottom: 2px solid #dcdfe6;
   display: inline-block;
   position: absolute;
  top:7px;
  right: 20px;
  z-index: 100;
 }

.time-line-item:nth-of-type(1)  >i:before {
  border-bottom: 0px solid #dcdfe6;
}
.time-line-item:last-child >i:after {
  border-bottom: 0px solid #dcdfe6;
}

.time-line-item > span {
  display: block;
  text-align: center;
  line-height: 30px;
  padding: 5px 0px;
}
</style>
