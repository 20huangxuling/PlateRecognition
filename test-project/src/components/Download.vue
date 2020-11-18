<template>
  <div>
    <el-table :data="tableData" style="font-size:15px">
      <el-table-column prop="name" label="文件" width="130"></el-table-column>
      <el-table-column prop="lastModified" label="更新日期" width="120"></el-table-column>
      <el-table-column label="操作" width="100">
        <template slot-scope="scope">
          <el-link icon="el-icon-download" :href="scope.row.uri">下载</el-link>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { getAllRecordsURI } from "@/http/api.js";

export default {
  data() {
    return {
      tableData: [
        {
          name: "识别记录",
          lastModified: "2020/3/3",
          uri: null
        },
        {
          name: "错误识别记录",
          lastModified: "2020/3/2",
          uri: null
        }
      ]
    };
  },
  mounted() {
    getAllRecordsURI()
      .then(res => {
        this.tableData[0].uri = res.data.path;
        console.log(this.tableData[0].uri);
      })
      .catch(err => {
        console.log(err);
      });
  },
};
</script>

<style scoped>
</style>