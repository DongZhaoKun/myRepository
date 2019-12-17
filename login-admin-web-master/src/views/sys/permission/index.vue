<template> 
<div class="app-container">
  <el-card class="filter-container" shadow="never">
        <el-button
          style="float:right"
          type="primary"
          @click="handleSearchList()"
          size="small">
          查询搜索
        </el-button>
        <el-button
          style="float:right;margin-right: 15px"
          @click="handleResetSearch()"
          size="small">
          重置
        </el-button>
     
      <div style="margin-top: 15px">
        <el-form :inline="true" :model="listQuery" size="small" label-width="140px">
          <el-form-item label="输入搜索：">
            <el-input v-model="listQuery.username" class="input-width" placeholder="用户名"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div>
          <el-button 
          type="primary"
          size="mini"
          @click="addRoleDialog()">
              新增菜单
          </el-button>
      </div>
    </el-card>
  
  <el-card class="operate-container" shadow="never">
      <i class="el-icon-tickets"></i>
      <span>数据列表</span>
  </el-card>
  <el-table
    :data="tableData"
    style="width: 100%"
    row-key="id"
    border
    lazy
    :load="load"
    @selection-change="handleSelectionChange"
    :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
    <el-table-column type="selection" width="60" align="center"></el-table-column>
    <el-table-column prop="name" label="名称"  width="180">
      <template slot-scope="scope">{{scope.row.name}}</template>
    </el-table-column>
    <el-table-column  prop="value" label="权限值" >
      <template slot-scope="scope">{{scope.row.value}}</template>
    </el-table-column>
    <el-table-column prop="type" label="类型" width="80">
      <template slot-scope="scope"><el-tag effect="dark" size="mini" :type="scope.row.type== 1 ?'warning':scope.row.type==2 ?'success':'primary'">{{ scope.row.type|typeFormat }}</el-tag></template>
    </el-table-column>
    <el-table-column prop="status" label="状态" width="80">
      <template slot-scope="scope"><el-tag effect="dark" size="mini" :type="scope.row.status== 0 ?'success':'primary'">{{scope.row.status|statusFormat}}</el-tag></template>
    </el-table-column>
    <el-table-column prop="create_time" label="创建时间" width="180">
      <template slot-scope="scope">{{scope.row.create_time | formatCreateTime}}</template>
    </el-table-column>
    <el-table-column label="操作" width="200" align="center">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="editPermission(scope.row)">编辑</el-button>
            <el-button type="primary" size="mini" @click="deletePermission(scope.row)">删除</el-button>
          </template>
    </el-table-column>
  </el-table>
</div>
</template>
<script>
  import {permissionList,permissionListByPId} from '@/api/permission'
  import {formatDate} from '@/utils/date';
  export default {
    name: "permissionList",
    components:{},
    data() {
      return {
        listQuery: {
            username:''
        },
        tableData:[],
        listLoading: true,
        list: null,
        total: null,
        closeOrder:{
          dialogVisible:false,
          content:null,
          orderIds:[]
        },
      }
    },
    created() {
      this.getList();
    },
    filters: {
      formatCreateTime(time) {
        let date = new Date(time);
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      },
      statusFormat(val){
        if(val == 0){
          return '正常';
        }else if(val == 1){
          return '冻结';
        }else{
          return '未知';
        }
      },
      typeFormat(val){
        if(val == 0){
          return '目录';
        }else if(val == 1){
          return '菜单';
        }else if(val == 2){
          return '按钮';
        }else{
          return '未知';
        }
      }
    },
    methods: {
       load(tree, treeNode, resolve) {
        console.log('tree',tree)
        console.log('treeNode',treeNode)
        setTimeout(() => {
          permissionListByPId(tree.id).then(resp=>{
            resp.data.forEach(function(item,index){
              permissionListByPId(item.id).then(resp1 =>{
                if(resp1.data !=null && resp1.data !=''){
                  this.$set(item,'hasChildren',true)
                  this.$set(item,'children',resp1.data)
                }
                console.log('resp data',resp.data)
              })
            },this)
            resolve(resp.data)
          })
        }, 1000)
      },
      getList() {
        this.listLoading = true;
          permissionList().then(response => {
          this.listLoading = false;
          response.data.forEach(function(item,index){
            if(item.pid == 0){
              this.$set(item,'hasChildren',true)
              permissionListByPId(item.id).then(resp =>{
                this.$set(item,'children',resp.data)
              })
              this.tableData.push(item)
            }
          },this)
          console.log('list',this.tableData)
        });
      },
      handleSelectionChange(){

      }
    }
  }
</script>
<style scoped>
   .input-width {
    width: 203px;
  }
</style>


