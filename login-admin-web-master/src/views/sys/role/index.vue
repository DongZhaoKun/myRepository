<template> 
  <div class="app-container">
    <el-card class="filter-container" shadow="never">
      <div>
        <i class="el-icon-search"></i>
        <span>筛选搜索</span>
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
      </div>
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
              新增角色
          </el-button>
           <el-button 
          type="primary"
          size="mini"
          @click="handleRolePermissionOperate()">
              角色授权
          </el-button>
      </div>
    </el-card>
    <el-card class="operate-container" shadow="never">
      <i class="el-icon-tickets"></i>
      <span>数据列表</span>
    </el-card>
    <div class="table-container">
      <el-table ref="orderTable"
                :data="list"
                style="width: 100%;"
                @selection-change="handleSelectionChange"
                v-loading="listLoading" border>
        <el-table-column type="selection" width="60" align="center"></el-table-column>
        <el-table-column label="编号" width="80" align="center">
          <template slot-scope="scope">{{scope.$index+1}}</template>
        </el-table-column>
        <el-table-column label="角色名" width="180" align="center">
          <template slot-scope="scope">{{scope.row.name}}</template>
        </el-table-column>
        <el-table-column label="角色描述" width="180" align="center">
          <template slot-scope="scope">{{scope.row.description}}</template>
        </el-table-column>
        <el-table-column label="创建时间" align="center">
          <template slot-scope="scope">{{scope.row.createTime | formatCreateTime}}</template>
        </el-table-column>
        <el-table-column label="状态" width="120" align="center">
          <template slot-scope="scope">{{scope.row.status | formatStatus}}</template>
        </el-table-column>
        <el-table-column label="操作" width="200" align="center">
          <template slot-scope="scope">
            <el-button
              type="primary"
              size="mini"
              @click="editRole(scope.row)"
            >编辑角色</el-button>
            <el-button
              type="primary"
              size="mini"
              @click="deleteRole(scope.row)"
            >删除角色</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog title="新增角色"
      :visible.sync="dialogVisible" >
        <el-form :model="addForm">
            <el-form-item label="角色名" label-width="120px">
                <el-input v-model="addForm.name" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="角色描述" label-width="120px">
                <el-input  v-model="addForm.description" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="状态" label-width="120px">
                <el-select v-model="addForm.status" placeholder="请选择角色状态">
                    <el-option v-for='item in statusOptions' :key="item.value" :label="item.label" :value='item.value' ></el-option>
                </el-select>
            </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addRole(addForm)">确 定</el-button>
      </span>
      </el-dialog>
      <el-dialog title="编辑角色"
      :visible.sync="editdialogVisible" >
        <el-form :model="editForm">
            <el-form-item label="角色名" label-width="120px">
                <el-input v-model="editForm.name" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="角色描述" label-width="120px">
                <el-input v-model="editForm.description" autocomplete="off"></el-input>
            </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editdialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editRoleSub(editForm)">确 定</el-button>
      </span>
      </el-dialog>
      <el-dialog title="角色授权"
      :visible.sync="rolePermissiondialogVisible" >
        <el-tree
          :data="data"
          show-checkbox
          node-key="id"
          ref="tree"
          :default-expand-all="true"
          :default-checked-keys="checkedKeys"
          :props="defaultProps">
        </el-tree>
      <span slot="footer" class="dialog-footer">
        <el-button @click="rolePermissiondialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="rolePermissionSub()">确 定</el-button>
      </span>
      </el-dialog>
    <div class="pagination-container">
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        layout="total, sizes,prev, pager, next,jumper"
        :current-page.sync="listQuery.pageNum"
        :page-size="listQuery.pageSize"
        :page-sizes="[5,10,15]"
        :total="total">
      </el-pagination>
    </div>
  </div>
</template>
<script>
  import {roleList,addRole,editRole,deleteRole,updateRP} from '@/api/role'
  import {permissionList,permissionListByRoleId,permissionListByPId} from '@/api/permission'
  import {formatDate} from '@/utils/date';
  const defaultListQuery = {
    pageNum: 1,
    pageSize: 10,
    orderSn: null,
    receiverKeyword: null,
    status: null,
    orderType: null,
    sourceType: null,
    createTime: null,
  };
  export default {
    name: "roleList",
    components:{},
    data() {
      return {
        listQuery: {
            username:''
        },
        dialogVisible: false,
        editdialogVisible: false,
        rolePermissiondialogVisible: false,
        addForm: {
            name:'',
            description:'',
            status:0,
        },
        editForm: {
            id: '',
            username:'',
            password:'',
            nick_name:'',
            email:'',
            status:0,
        },
        data: [],
        defaultProps: {
          children: 'children',
          label: 'name'
        },
        expandedKeys:[1,2,3,4,5,15],
        checkedKeys:[],
        statusOptions:[
          {
            label: '正常',
            value: 0
          },
          {
            label: '冻结',
            value: 1
          }
        ],
        listLoading: true,
        list: null,
        total: null,
        operateType: null,
        multipleSelection: [],
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
      formatStatus(value) {
        if (value === 0) {
          return '正常';
        } else if (value === 1) {
          return '冻结';
        } else {
          return '未知';
        }
      },
    },
    methods: {
      addRoleDialog(){
        this.addForm ={
            name:'',
            description:'',
            status:0
          }
        this.dialogVisible = true

      },
      editRole(row){
          this.editdialogVisible = true
          this.editForm.id = row.id
          this.editForm.name = row.name
          this.editForm.description = row.description
      },  
      editRoleSub(param){
          this.editdialogVisible = false
          editRole(this.editForm).then(response =>{
              if(response.code == 200){
                  
                  this.$message.success('编辑成功')
                  this.getList()
              }
          })
      },
      addRole(params){
         
          addRole(params).then(response =>{
              this.dialogVisible = false
              if(response.code==200){
                  this.$message.success('新增成功')
                  this.getList()
              }
              
          })
      },
      deleteRole(row){
          this.$confirm('是否要进行该删除操作?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => { 
            let param =[{
              id : row.id
          }]
          deleteRole(param).then(resp =>{
              if(resp.code == 200){
                  this.$message.success('删除成功')
                  this.getList()
              }
              
          })
        }).catch(() => {
            this.$message.info('已取消删除')
        })
          
      },
      handleResetSearch() {
        this.listQuery = Object.assign({}, defaultListQuery);
      },
      handleSearchList() {
        this.listQuery.pageNum = 1;
        this.getList();
      },
      handleSelectionChange(val){
        this.multipleSelection = val;
      },
     
      handleDeleteOrder(index, row){
        let ids=[];
        ids.push(row.id);
        this.deleteOrder(ids);
      },
      handleRolePermissionOperate(){
        if(this.multipleSelection==null||this.multipleSelection.length<1){
          this.$message({
            message: '请选择要操作的角色',
            type: 'warning',
            duration: 1000
          });
          return;
        }else if(this.multipleSelection.length > 1){
            this.$message.warning('只能选择一项')
        }else{
          this.rolePermissiondialogVisible = true
          console.log('multipleSelection',this.multipleSelection)
          this.data = []
          
          console.log('multipleSelection',this.multipleSelection)
          permissionListByRoleId(this.multipleSelection[0].id).then(resp =>{
            console.log('permissionListByRoleId',resp.data)
            resp.data.forEach((item,index) =>{
              this.checkedKeys.push(item.id)
            });
            console.log('checkedKeys',this.checkedKeys)
          })
          this.getTreeData()
        }
          
      },
      getTreeData(){
         
        permissionList().then(resp =>{
          let arr = resp.data;
          console.log(this.arrayToTree(arr,0))
          this.data = this.arrayToTree(arr,0)
         
        })
       
      },
      arrayToTree(arr, pid) {
            let temp = [];
            let treeArr = arr;
            treeArr.forEach((item, index) => {
                if (item.pid == pid) {
                    if (this.arrayToTree(treeArr, treeArr[index].id).length > 0) {
                         // 递归调用此函数
                      treeArr[index].children = this.arrayToTree(treeArr, treeArr[index].id);
                    }
                    temp.push(treeArr[index]);
                }
           });
           return temp;
         console.log('data',temp)
      },
      rolePermissionSub(){
        this.rolePermissiondialogVisible = false
        console.log('getCheckedKeys', this.$refs.tree.getCheckedKeys())
        var param = {"permIdList":this.$refs.tree.getCheckedKeys(),id:this.multipleSelection[0].id}
        updateRP(param).then(resp =>{
          if(resp.code == 200){
            this.$message.success('更新角色权限成功')
          }
        }).catch(err =>{
          this.$message.error('更新角色权限失败')
        })

      },
      handleSizeChange(val){
        this.listQuery.pageNum = 1;
        this.listQuery.pageSize = val;
        this.getList();
      },
      handleCurrentChange(val){
        this.listQuery.pageNum = val;
        this.getList();
      },
     
      getList() {
        this.listLoading = true;
          roleList().then(response => {
          this.listLoading = false;
          
          this.list = response.data;
          this.list.forEach(function(item,index){
             if(item.status == 0){
                 item.status = '正常'
             }
          });
          console.log('list',this.list)
          this.total = response.data.length;
        });
      },
      
     
    }
  }
</script>
<style scoped>
  .input-width {
    width: 203px;
  }
</style>


