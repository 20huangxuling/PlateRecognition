<template>
  <div>
    <el-popover placement="bottom" title="操作提示" style="position: absolute;right:10%;top:10%;z-index:999">
      <i class="el-icon-question" slot="reference"></i>
      <div v-html="tip"></div>
    </el-popover>
    <el-tabs
      v-model="activeName"
      style="position:absolute; width: 80%; left: 10%; top: 10%"
    >
      <el-tab-pane label="上传识别" name="upload">
        <uploadui></uploadui>
      </el-tab-pane>
      <el-tab-pane label="识别结果" name="result">
        <result></result>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import uploadui from "@/components/Upload";
import result from "@/components/Result";

export default {
  data() {
    return {
      activeName: "upload",
    };
  },
  components: {
    uploadui,
    result
  },
  computed: {
    tip: function() {
      if(this.activeName === "upload") {
        return `
          <p>1、点击“添加”，选择拍照或者上传本地图片。</p>
          <p>2、点击选中（可多选）将要识别的图片。</p>
          <p>3、点击“识别结果”，查看结果。</p>
        `;
      } else if(this.activeName === "result") {
        return `
          <p>1、点击“操作”->“详情”，查看识别结果。</p>
          <p>2、点击“操作”->“确认”，向后台确认保存结果。</p>
          <p>3、点击“操作”->“删除”，丢弃单个识别结果。</p>
          <p>4、选中单个或多个结果，点击“确认”保存结果。</p>
          <p>5、点击“清空”，丢弃所有识别结果。</p>
        `;
      }
    }
  },
  methods: {
  }
};
</script>

<style scoped>
.el-carousel__item h3 {
  color: #475669;
  font-size: 18px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}

.item {
  margin-top: 10px;
  margin-right: 40px;
}
</style>