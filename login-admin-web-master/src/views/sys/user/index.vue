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
          @click="addUserDialog()">
              新增用户
          </el-button>
          <el-button 
          type="primary"
          size="mini"
          @click="addUserRoleDialog()">
              用户授权
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
        <el-table-column label="用户名" width="180" align="center">
          <template slot-scope="scope">{{scope.row.username}}</template>
        </el-table-column>
        <el-table-column label="昵称" width="180" align="center">
          <template slot-scope="scope">{{scope.row.nick_name}}</template>
        </el-table-column>
        <el-table-column label="邮箱" align="center">
          <template slot-scope="scope">{{scope.row.email}}</template>
        </el-table-column>
        <el-table-column label="状态" width="120" align="center">
          <template slot-scope="scope">{{scope.row.status | formatStatus}}</template>
        </el-table-column>
        <el-table-column label="操作" width="200" align="center">
          <template slot-scope="scope">
            <el-button
              type="primary"
              size="mini"
              @click="editUser(scope.$index, scope.row)"
            >编辑用户</el-button>
            <el-button
              type="primary"
              size="mini"
              @click="deleteUser(scope.row)"
            >删除用户</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog title="新增用户"
      :visible.sync="UserdialogVisible" >
        <el-form :model="addForm">
            <el-form-item label="用户名" label-width="120px">
                <el-input v-model="addForm.username" ></el-input>
            </el-form-item>
            <el-form-item label="密码" label-width="120px">
                <el-input  v-model="addForm.password" ></el-input>
            </el-form-item>
            <el-form-item label="昵称" label-width="120px">
                <el-input v-model="addForm.nick_name" ></el-input>
            </el-form-item>
            <el-form-item label="邮箱" label-width="120px">
                <el-input v-model="addForm.email" ></el-input>
            </el-form-item>
            <el-form-item label="状态" label-width="120px">
                <el-select v-model="addForm.status" placeholder="请选择用户状态">
                    <el-option v-for='item in statusOptions' :key="item.value" :label="item.label" :value='item.value' ></el-option>
                </el-select>
            </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="UserdialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addUser(addForm)">确 定</el-button>
      </span>
      </el-dialog>
      <el-dialog title="编辑用户"
      :visible.sync="UserEditdialogVisible" >
        <el-form :model="editForm">
            <el-form-item label="用户名" label-width="120px">
                <el-input v-model="editForm.username" ></el-input>
            </el-form-item>
            <el-form-item label="昵称" label-width="120px">
                <el-input v-model="editForm.nick_name" ></el-input>
            </el-form-item>
            <el-form-item label="邮箱" label-width="120px">
                <el-input v-model="editForm.email" ></el-input>
            </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="UserEditdialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editUserSub(editForm)">确 定</el-button>
      </span>
      </el-dialog>
      <el-dialog title="用户授权"
      :visible.sync="addUserRoledialogVisible" >
        <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
            <div style="margin: 15px 0;"></div>
            <el-checkbox-group v-model="checkedRoles" @change="handleCheckedCitiesChange">
                <el-checkbox v-for="role in roleOptions" :label="role.label" :key="role.value" >{{role.label}}</el-checkbox>
            </el-checkbox-group>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addUserRoledialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editUserSub(editForm)">确 定</el-button>
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
  import {userList,addUser,editUser,deleteUser} from '@/api/user'
  import {roleList,userRole} from '@/api/role'
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
    name: "userList",
    components:{},
    data() {
      return {
        listQuery: {
            username:''
        },
        UserdialogVisible: false,
        UserEditdialogVisible: false,
        addUserRoledialogVisible: false,
        isIndeterminate: true,
        checkAll: false,
        addForm: {
            username:'',
            password:'',
            nick_name:'',
            email:'',
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
        roleOptions:[
          
        ],
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
        checkedRoles:[

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
      
      formatPayType(value) {
        if (value === 1) {
          return '支付宝';
        } else if (value === 2) {
          return '微信';
        } else {
          return '未支付';
        }
      },
      formatSourceType(value) {
        if (value === 1) {
          return 'APP订单';
        } else {
          return 'PC订单';
        }
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
        test(){
            test().then(response =>{
                console.log('test resp',response)
            })
        },
      addUserDialog(){
        this.addForm ={
            username:'',
            password:'',
            nick_name:'',
            email:'',
            status:0
          }
        this.UserdialogVisible = true

      },
      editUser(index,row){
          this.UserEditdialogVisible = true
          this.editForm.id = row.id
          this.editForm.username = row.username
          this.editForm.nick_name = row.nick_name
          this.editForm.email = row.email
      },  
      editUserSub(param){
          this.UserEditdialogVisible = false
          editUser(this.editForm).then(response =>{
              if(response.code == 200){
                  
                  this.$message.success('编辑成功')
                  this.getList()
              }
          })
      },
      addUser(params){
          console.log('adduser param',params)
          if(params.status == '正常'){
              params.status = 0
          }
          if(params.status == '冻结'){
              params.status = 1
          }
          addUser(params).then(response =>{
              this.UserdialogVisible = false
              if(response.code==200){
                  this.$message.success('新增成功')
                  this.getList()
              }
              
          })
      },
      deleteUser(row){
          this.$confirm('是否要进行该删除操作?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => { 
            let param =[{
              id : row.id
          }]
          deleteUser(param).then(resp =>{
              if(resp.code == 200){
                  this.$message.success('删除成功')
                  this.getList()
              }
              
          })
        }).catch(() => {
            this.$message.info('已取消删除')
        })
          
      },
      addUserRoleDialog(){
          let param = 1
          userRole(param).then(resp =>{
              console.log('userRole resp',resp)
              this.checkedRoles = []
               resp.data.forEach(function(item,index){
                  this.checkedRoles.push(item.id)
              },this);
              console.log('checkedRoles ',this.checkedRoles)
          }).catch(err =>{
              this.$message.error('获取用户已有角色失败')
          })
          this.addUserRoledialogVisible = true
          roleList().then(resp =>{
              this.roleOptions = []
              resp.data.forEach(function(item,index){
                  this.roleOptions.push({label:item.description,value:item.id})
              },this);
              console.log(this.roleOptions)
          }).catch(err =>{
              this.$message.error('获取角色列表失败')
          })
      },
      handleCheckAllChange(val) {
        this.checkedRoles = val ? roleOptions.value : [];
        this.isIndeterminate = false;
      },
      handleCheckedCitiesChange(value) {
        let checkedCount = value.length;
        this.checkAll = checkedCount === this.roleOptions.length;
        this.isIndeterminate = checkedCount > 0 && checkedCount < this.roleOptions.length;
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
      handleBatchOperate(){
        if(this.multipleSelection==null||this.multipleSelection.length<1){
          this.$message({
            message: '请选择要操作的订单',
            type: 'warning',
            duration: 1000
          });
          return;
        }
        if(this.operateType===1){
          //批量发货
          let list=[];
          for(let i=0;i<this.multipleSelection.length;i++){
            if(this.multipleSelection[i].status===1){
              list.push(this.covertOrder(this.multipleSelection[i]));
            }
          }
          if(list.length===0){
            this.$message({
              message: '选中订单中没有可以发货的订单',
              type: 'warning',
              duration: 1000
            });
            return;
          }
          this.$router.push({path:'/oms/deliverOrderList',query:{list:list}})
        }else if(this.operateType===2){
          //关闭订单
          this.closeOrder.orderIds=[];
          for(let i=0;i<this.multipleSelection.length;i++){
            this.closeOrder.orderIds.push(this.multipleSelection[i].id);
          }
          this.closeOrder.dialogVisible=true;
        }else if(this.operateType===3){
          //删除订单
          let ids=[];
          for(let i=0;i<this.multipleSelection.length;i++){
            ids.push(this.multipleSelection[i].id);
          }
          this.deleteOrder(ids);
        }
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
          userList(this.listQuery).then(response => {
          this.listLoading = false;
          
          this.list = response.data;
        //   this.list.forEach(function(item,index){
        //      if(item.status == 0){
        //          item.status = '正常'
        //      }
        //   });
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


