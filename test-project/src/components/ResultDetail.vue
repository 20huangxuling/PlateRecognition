<template>
  <div>
    <div id="result-item-header" style="position:absolute; top:5%; width:90%; left:5%">
      <el-page-header @back="goBack" content="详情"></el-page-header>
    </div>
    <div id="result-item-main" style="width:100%; height:400px; overflow:scroll">
      <el-collapse v-model="activeNames">
        <el-collapse-item title="识别信息" name="information">
          <div id="info">
            <p>
              <span>屏柜号：</span>
              {{ result.cabinetId }}
            </p>
            <p>
              <span>识别时间：</span>
              {{ result.dateTime }}
            </p>
            <p>
              <span>是否与原始库一致:</span>
              {{ result.consistent ? "一致" : "不一致" }}
            </p>
          </div>
        </el-collapse-item>
        <el-collapse-item title="状态矩阵" name="compare">
          <div id="orginImg" style="position:relative">
            <div style="position:absolute;width:80%; margin-left:10%" class="mask">原图</div>
            <el-image :src="result.originImg" style="width: 80%;">
              <div slot="placeholder" class="image-slot">
                加载中
                <span class="dot">...</span>
              </div>
            </el-image>
          </div>
          <div
            id="resultMatrix"
            style="position:relative; width:80%; margin-left:10%; margin-top: 5%"
          >
            <div style="position:absolute; width:100%" class="mask">状态矩阵</div>
            <el-card class="box-card" ref="switchsMatrixCard">
              <div style="margin-top: 10%" v-html="switchsMatrix"></div>
            </el-card>
          </div>
        </el-collapse-item>
        <el-collapse-item title="非一致数据" name="inconsistent">
          <el-table :data="inconsistentSwitchs" style="width: 100%" border>
            <el-table-column prop="id" label="编号" width="65"></el-table-column>
            <el-table-column prop="name" label="名称" width="95"></el-table-column>
            <el-table-column prop="standardState" label="原始状态" width="50"></el-table-column>
            <el-table-column prop="returnState" label="识别结果" width="50"></el-table-column>
          </el-table>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    result: null
  },
  data() {
    return {
      activeNames: ["information", "compare", "inconsistent"],
      matrixRow: 0,
      matrixColumn: 0,
      state: {
        on: "投",
        off: "退",
        nothing: "备"
      }
    };
  },
  computed: {
    switchsArr() {
      let switchsArr = null;
      let switchs = this.result.switchs;
      let switchsNum = switchs.length;
      let matrixRow = 0;
      let matrixColumn = 0;

      switchs.forEach((item, index) => {
        matrixRow = Math.max(matrixRow, item.dotDetected.row);
        matrixColumn = Math.max(matrixColumn, item.dotDetected.column);
      });

      this.matrixRow = matrixRow;
      this.matrixColumn = matrixColumn;

      switchsArr = new Array();
      for (let i = 0; i <= matrixRow; i++) {
        switchsArr[i] = new Array();
        for (let j = 0; j <= matrixColumn; j++) {
          switchsArr[i][j] = null;
        }
      }

      switchs.forEach((item, index) => {
        switchsArr[item.dotDetected.row][item.dotDetected.column] = item;
      });

      return switchsArr;
    },
    switchsMatrix() {
      //显示在网页上的压板矩阵
      let switchsMatrixHtml = "";

      for (let i = 0; i <= this.matrixRow; i++) {
        for (let j = 0; j <= this.matrixColumn; j++) {
          let singleSwitch = this.switchsArr[i][j];
          if (singleSwitch !== null) {
            if (!singleSwitch.correct) {
              switchsMatrixHtml += `<span style='color: red'>${
                this.state[singleSwitch.dotDetected.state]
              }</span>`;
            } else {
              switchsMatrixHtml += `<span>${
                this.state[singleSwitch.dotDetected.state]
              }</span>`;
            }

            if (j === this.matrixColumn) {
              switchsMatrixHtml += "<br/>";
            } else {
              switchsMatrixHtml += " ";
            }
          }
        }
      }

      return switchsMatrixHtml;
    },
    inconsistentSwitchs() {
      let inconsistentSwitchs = [];
      let switchs = this.result.switchs;

      switchs.forEach((item, index) => {
        if (item.aSwitch && !item.correct) {
          inconsistentSwitchs.push({
            id: item.aSwitch.id,
            name: item.aSwitch.name,
            standardState: this.state[item.aSwitch.standardState],
            returnState: this.state[item.dotDetected.state]
          });
        }
      });

      return inconsistentSwitchs;
    }
  },
  methods: {
    goBack() {
      this.$emit("close-dialog");
    },
    /**
     * @methodName initSwitchsArr
     * @description 把传入的result的switchs数组组织成二维形式
     */
    initSwitchsArr() {
      let switchs = this.result.switchs;
      let switchsNum = switchs.length;
      let matrixRow = 0;
      let matrixColumn = 0;

      switchs.forEach((item, index) => {
        matrixRow = Math.max(matrixRow, item.dotDetected.row);
        matrixColumn = Math.max(matrixColumn, item.dotDetected.column);
      });

      this.matrixRow = matrixRow;
      this.matrixColumn = matrixColumn;

      this.switchsArr = new Array();
      for (let i = 0; i <= matrixRow; i++) {
        this.switchsArr[i] = new Array();
        for (let j = 0; j <= matrixColumn; j++) {
          this.switchsArr[i][j] = null;
        }
      }

      switchs.forEach((item, index) => {
        this.switchsArr[item.dotDetected.row][item.dotDetected.column] = item;
      });
    }
  }
};
</script>

<style scoped>
#info p {
  margin-left: 10%;
  text-align: left;
}

.mask {
  color: white;
  background: rgba(0, 0, 0, 0.3);
  z-index: 1;
}

#info p {
  font-size:14px;
}
</style>

<style>
#result-item-main .el-collapse-item__header {
  /* display: -webkit-box; */
  /* display: -ms-flexbox; */
  display: flex;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  height: 48px;
  line-height: 48px;
  background-color: #fff;
  color: #303133;
  cursor: pointer;
  border-bottom: 1px solid #ebeef5;
  font-size: 15px;
  font-weight: 500;
  -webkit-transition: border-bottom-color 0.3s;
  transition: border-bottom-color 0.3s;
  outline: 0;
  
}

#result-item-main .el-collapse-item__header.is-active {
  border-bottom-color: transparent;
  color: #409EFF;
}
</style>