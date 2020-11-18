<template>
  <div>
    <el-card class="box-card" ref="boxCard">
      <div slot="header" class="clearfix">
        <el-checkbox
          v-model="selectAll"
          id="selectAllCheckBox"
          style="float:left;margin-top:-3%;"
          :disabled="checkBoxDisabled"
          @change="setSelectAll"
          :indeterminate="isIndeterminate"
        ></el-checkbox>
      </div>
      <div class="mask" v-show="imgList.length === 0">
        <span style="font-size:125%;line-height:30px">
          点击下方中间按钮
          <br />拍摄或上传图片
        </span>
      </div>
      <div class="view-container">
        <ul style="overflow: auto;">
          <li v-for="item in imgList" :key="item.index" @click="updateSelection(item.index)">
            <div
              style="position: relative; right: 10%; margin-bottom: 10%"
              :class="[item.selected?'item-selected':'']"
            >
              <el-card :body-style="{ padding: '0px' }">
                <img :src="item.url" class="image" />
                <div style="padding: 14px;">
                  <span>{{ item.name }}</span>
                  <div class="bottom">
                    <!-- <time class="time">{{ currentDate }}</time> -->
                    <el-button
                      type="text"
                      class="button"
                      icon="el-icon-delete"
                      @click="removeItem(item.index)"
                    ></el-button>
                  </div>
                </div>
              </el-card>
            </div>
          </li>
        </ul>
      </div>
    </el-card>

    <div id="button-container" style="margin-top: 20%;">
      <input
        type="file"
        ref="fileInput"
        accept="image/*"
        @change="getFile"
        style="display: none"
        multiple
      />
      <div style="margin-bottom:10%">
        <el-popconfirm
          confirmButtonText="确定"
          cancelButtonText="取消"
          icon="el-icon-info"
          iconColor="red"
          title="确定要删除选中项吗？"
          @onConfirm="deleteSelected()"
          style="position: relative; left: -5%"
        >
          <el-button
            type="primary"
            icon="el-icon-delete"
            :disabled="deleteButtonDisabled"
            plain
            slot="reference"
            round
          >删除</el-button>
        </el-popconfirm>
        <el-button
          type="primary"
          @click="$refs.fileInput.click();"
          icon="el-icon-upload2"
          plain
          style="position:relative; right:-1.5%;font-size:23px !important"
          circle
        ></el-button>
        <el-button
          type="primary"
          @click="identity"
          icon="el-icon-view"
          :disabled="identityButtonDisable"
          plain
          style="position: relative; right: -5%; margin-left:10px"
          round
        >识别</el-button>
      </div>
      <div class="row2"></div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      index: 1, //图片的起始index
      imgList: [], //一个item的index及图片的src和name
      selecteddCount: 0, //选中的item的数量
      selectAll: false,
      isIndeterminate: false //表示checkbox是否处于不确定状态，主要用来控制样式
    };
  },
  computed: {
    identityButtonDisable() {
      return this.selecteddCount === 0 || this.imgList.length === 0;
    },
    checkBoxDisabled() {
      return this.imgList.length === 0;
    },
    deleteButtonDisabled() {
      return this.imgList.length === 0;
    }
  },
  watch: {
    selecteddCount: function(newVal, oldVal) {
      if (newVal === this.imgList.length && newVal !== 0) {
        this.selectAll = true;
        this.isIndeterminate = false;
      } else if (newVal === 0) {
        this.selectAll = false;
        this.isIndeterminate = false;
      } else if (newVal != this.imgList.length) {
        this.selectAll = false;
        this.isIndeterminate = true;
      }
    }
  },
  methods: {
    handleInvalidFile(invalidFiles) {
      let tipMessageHtml = "<ul>";

      for (let i = 0; i < invalidFiles.length; i++) {
        if (invalidFiles[i].error === "form") {
          tipMessageHtml +=
            "<li style='list-style: none;'>" +
            `${invalidFiles[i].name}上传失败，格式错误。` +
            "</li>";
        } else {
          tipMessageHtml +=
            "<li style='list-style: none;'>" +
            `${invalidFiles[i].name}上传失败，大小不能超过5M。` +
            "</li>";
        }
      }

      tipMessageHtml += "</ul>";

      this.$message.error({
        message: tipMessageHtml,
        dangerouslyUseHTMLString: true,
        duration: 0,
        showClose: true
      });
    },
    getFile(event) {
      const _this = this;
      const files = event.target.files;
      let invalidFiles = []; //存放非法文件的名称，即不是指定格式的文件

      for (let i = 0; i < files.length; i++) {
        let fileName = files[i].name;
        let pattern = new RegExp("^.*?.(jpeg|jpg|png)$", "gi");
        //let pattern = /^.*?\.(jpeg|jpg|png)$/;

        if (pattern.test(fileName) === false) {
          invalidFiles.push({ name: fileName, error: "form" });
          continue;
        }

        if (files[i].size > 5242880) {
          invalidFiles.push({ name: fileName, error: "size" });
          continue;
        }

        let _imgList = this.imgList;
        let file = files[i];

        const fileReader = new FileReader();
        fileReader.onload = function() {
          let fileUrl = fileReader.result;

          if (fileUrl !== null) {
            _imgList.push({
              index: _this.index++,
              file: file,
              url: fileUrl,
              name: fileName,
              selected: false
            });
          } else {
            console.log("error");
          }
        };

        fileReader.readAsDataURL(files[i]);
      }

      if (invalidFiles.length !== 0) this.handleInvalidFile(invalidFiles);
    },
    removeItem(index) {
      let target = null;

      for (let i = 0; i < this.imgList.length; i++) {
        if (this.imgList[i].index === index) {
          target = i;
          break;
        }
      }

      if (target !== null) {
        this.imgList.splice(target, 1);
        this.selecteddCount--;
      }
    },
    updateSelection(index) {
      let target = null;
      for (let i = 0; i < this.imgList.length; i++) {
        if (this.imgList[i].index === index) {
          this.imgList[i].selected = !this.imgList[i].selected;
          this.selecteddCount += this.imgList[i].selected ? 1 : -1;
          break;
        }
      }
    },
    deleteSelected() {
      for (let i = 0; i < this.imgList.length; ) {
        if (this.imgList[i].selected) {
          this.imgList.splice(i, 1);
          this.selecteddCount--;
        } else {
          i++;
        }
      }
    },
    setSelectAll(value) {
      this.isIndeterminate = false;
      if (this.selectAll) {
        for (let i = 0; i < this.imgList.length; i++) {
          this.imgList[i].selected = true;
        }
        this.selecteddCount = this.imgList.length;
      } else {
        for (let i = 0; i < this.imgList.length; i++) {
          this.imgList[i].selected = false;
        }
        this.selecteddCount = 0;
      }
    },
    identity() {
      const loading = this.$loading({
        lock: true,
        text: "识别中",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)",
        target: ".el-card"
      });

      let _imgList = this.imgList;
      let _selecteddCount = this.selecteddCount;
      let currentCount = 0;

      //每张图片给3秒时间
      setTimeout(() => {
        loading.close();
      }, 3000 * _imgList.length);

      _imgList.forEach((item, index) => {
        console.log(index);
        if (item.selected) {
          this.$store
            .dispatch("identity", { file: item.file, base64: item.url })
            .then(res => {
              console.log(res.data);
            })
            .catch(err => {
              console.log(err);
            })
            .finally(() => {
              //识别以后要把这张图从列表中去掉
              let _index = _imgList.indexOf(item);
              _imgList.splice(_index, 1);
              this.selecteddCount--;

              if (++currentCount === _selecteddCount) {
                loading.close();
                this.$message(`识别完毕，请在“识别结果”中查看结果`);
              }
            });
        }
      });
    }
  }
};
</script>

<style scoped>
.view-container {
  height: 300px;
  width: 100%;
  overflow: scroll;
}

.mask {
  position: absolute;
  top: 30%;
  left: 0px;
  right: 0px;
  color: grey;
}

.item-selected {
  border: 1px solid rgb(74, 173, 253);
  box-shadow: none;
}

.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  margin-right: -5px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.small {
  width: 20%;
  padding: 0px;
}
</style>