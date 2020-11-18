<template>
  <div>
    <div style="height: 350px">
      <el-table
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%; top:-5%"
        @selection-change="handleSelectionChange"
        max-height="350"
      >
        <el-table-column type="selection" width="40"></el-table-column>
        <el-table-column prop="cabinetId" label="屏柜号" width="70"></el-table-column>
        <!-- <el-table-column label="识别时间" width="110">
          <template slot-scope="scope">{{ scope.row.date }}</template>
        </el-table-column>
        -->
        <el-table-column
          prop="consistent"
          label="结果"
          width="90"
          :filters="[{ text: '一致', value: true }, { text: '不一致', value: false }]"
          :filter-method="filterTag"
          filter-placement="bottom-end"
        >
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.consistent === true ? 'success' : 'danger'"
              disable-transitions
            >{{scope.row.consistent === true ? '一致' : '不一致'}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100">
          <template slot-scope="scope">
            <el-dropdown trigger="click" @command="handleCommand">
              <el-button type="text">
                操作
                <i class="el-icon-arrow-down el-icon--right"></i>
              </el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item
                  v-for="op in operations"
                  :command="paramsObject(op, scope.$index, scope.row)"
                  :key="op"
                >{{ op }}</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>
      <el-dialog :visible.sync="dialogVisible" width="80%" :show-close="false">
        <resultdetail @close-dialog="dialogVisible = false;" :result="openRow"></resultdetail>
      </el-dialog>
    </div>
    <div style="margin-top:30%">
      <el-popconfirm
        confirmButtonText="确定"
        cancelButtonText="取消"
        icon="el-icon-info"
        iconColor="red"
        title="确定要删除选中项吗？"
        @onConfirm="deleteChoosed()"
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
        icon="el-icon-check"
        plain
        round
        style="position:relative; left: 5%"
        :disabled="confirmButtonDisabled"
      >确认</el-button>
    </div>
  </div>
</template>

<script>
import resultdetail from "@/components/ResultDetail";
import confirm from "@/http/api.js";

export default {
  data() {
    return {
      tableData: [], //cabinetId, dataTime, originImg, returnImg, switchs, consistent
      multipleSelection: [],
      dialogVisible: false,
      openRow: null,
      operations: ["详情", "确认", "删除"],
      operationsToFunction: {
        详情: this.checkDetailOp,
        确认: this.confirmOp,
        删除: this.deleteOp
      }
    };
  },
  components: {
    resultdetail
  },
  computed: {
    newResult() {
      return this.$store.getters.newResult;
    },
    confirmButtonDisabled() {
      return this.multipleSelection.length === 0;
    },
    deleteButtonDisabled() {
      return this.multipleSelection.length === 0;
    }
  },
  watch: {
    newResult: function(newVal, oldVal) {
      if (newVal === null) return;

      let newResult = newVal;

      //检查每一个压板的识别结果是否都和数据库一致
      //every判断数组中是否每个元素都满足条件，
      //都满足返回true，一个或以上不满足就返回false
      newResult.consistent = newResult["switchs"].every(
        (element, index, switchs) => {
          return element.correct;
        }
      );

      this.tableData.push(newResult);
      console.log(newResult);
    }
  },
  methods: {
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    deleteChoosed() {
      /*for(let i = 0; i < this.imgList.length; ) {
        if(this.imgList[i].choosed) {
          this.imgList.splice(i, 1);
          this.choosedCount--;
        } else {
          i++;
        }
      }*/
      for(let i = 0; i < this.multipleSelection.length; i++) {
        let index = this.tableData.indexOf(this.multipleSelection[i]);
        this.tableData.splice(index, 1);
      }
    },
    filterTag(value, row) {
      return row.consistent === value;
    },
    paramsObject(op, index, row) {
      return { op, index, row };
    },
    //查看结果详情
    checkDetailOp(index, row) {
      this.openRow = row;
      this.dialogVisible = true;
    },
    //向后台发送确认请求，即保存该结果
    confirmOp(index, row) {
      this.$store.dispatch("confirmToSave", row.cabinetId)
      
      this.tableData.splice(index, 1);
    },
    //在列表中删除该项
    deleteOp(index, row) {
      this.tableData.splice(index, 1);
    },
    //点击下来菜单触发该回调函数
    handleCommand(params) {
      this.operationsToFunction[params.op](params.index, params.row);
    }
  }
};
</script>

<style scoped>
</style>