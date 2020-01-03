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
            <el-input v-model="listQuery.name" class="input-width" placeholder="权限名"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div>
          <el-button 
          type="primary"
          size="mini"
          @click="addRoleDialog">
              新增权限
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
    <el-table-column  prop="uri" label="路径" >
      <template slot-scope="scope">{{scope.row.uri}}</template>
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
  <el-dialog title="新增权限" :visible.sync="dialogVisible" >
    <el-form :model="addForm">
      <el-form-item label="类型：" label-width="120px">
        <el-radio-group v-model="radio" @change="radioChange()">
          <el-radio :label="0">目录</el-radio>
          <el-radio :label="1">菜单</el-radio>
          <el-radio :label="2">按钮</el-radio>
        </el-radio-group>   
       </el-form-item>
      <el-form-item label="名称：" label-width="120px">
          <el-input v-model="addForm.name" ></el-input>
      </el-form-item>
      <el-form-item label="父级菜单：" label-width="120px" >
        <el-input  v-model="pidObj.name" @focus="pidFocus()"></el-input>
      </el-form-item>
      <el-form-item label="权限值：" label-width="120px">
          <el-input  v-model="addForm.value" ></el-input>
      </el-form-item>
      <el-form-item label="路径：" label-width="120px">
          <el-input  v-model="addForm.uri" ></el-input>
      </el-form-item>
      <el-form-item label="状态：" label-width="120px">
          <el-select v-model="addForm.status" placeholder="请选择角色状态">
              <el-option v-for='item in statusOptions' :key="item.value" :label="item.label" :value='item.value' ></el-option>
          </el-select>
      </el-form-item>
      </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="addSub(addForm)">确 定</el-button>
    </span>
  </el-dialog>
  <el-dialog title="编辑权限" :visible.sync="editdialogVisible" >
    <el-form :model="editForm">
      <el-form-item label="类型：" label-width="120px">
        <el-radio-group v-model="editradio" @change="editradioChange()">
          <el-radio :label="0">目录</el-radio>
          <el-radio :label="1">菜单</el-radio>
          <el-radio :label="2">按钮</el-radio>
        </el-radio-group>   
       </el-form-item>
      <el-form-item label="名称：" label-width="120px">
          <el-input v-model="editForm.name" ></el-input>
      </el-form-item>
      <el-form-item label="父级菜单：" label-width="120px" >
        <el-input  v-model="pidObj.name" @focus="pidFocus()"></el-input>
      </el-form-item>
      <el-form-item label="权限值：" label-width="120px">
          <el-input  v-model="editForm.value" ></el-input>
      </el-form-item>
      <el-form-item label="路径：" label-width="120px">
          <el-input  v-model="editForm.uri" ></el-input>
      </el-form-item>
      <el-form-item label="状态：" label-width="120px">
          <el-select v-model="editForm.status" placeholder="请选择角色状态">
              <el-option v-for='item in statusOptions' :key="item.value" :label="item.label" :value='item.value' ></el-option>
          </el-select>
      </el-form-item>
      </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="editdialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="editSub(editForm)">确 定</el-button>
    </span>
  </el-dialog>
  <el-dialog width="30%" title="选择父菜单"
            :visible.sync="innerVisinnerible">
    <el-tree
      :data="data"
      :props="defaultProps"
      accordion
      @node-click="handleNodeClick">
    </el-tree>
    <span slot="footer" class="dialog-footer">
      <el-button @click="innerVisinnerible = false">取 消</el-button>
      <el-button type="primary" @click="innerVisinnerible = false">确 定</el-button>
    </span>
  </el-dialog>
</div>

</template>
<script>
  import {permissionList,permissionListByPId,addPermission,editPermission,permissionListById,deletePermission} from '@/api/permission'
  import {formatDate} from '@/utils/date';
  export default {
    name: "permissionList",
    components:{},
    data() {
      return {
        listQuery: {
            name:''
        },
        data:[],
        defaultProps: {
          children: 'children',
          label: 'label',
          id: 0,
        },
        tableData:[],
        dialogVisible: false,
        editdialogVisible: false,
        innerVisinnerible: false,
        listLoading: true,
        radio:0,
        editradio:0,
        addForm:{
          type:this.radio,
          name:'',
          pid:0,
          value:'',
          uri:'',
          status:0
        },
        editForm:{
          id:'',
          type:this.radio,
          name:'',
          pid:0,
          value:'',
          uri:'',
          status:0
        },
        pidObj:{id:0,name:'一级目录'},
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
        this.tableData = [];
        this.listLoading = true;
          permissionList().then(response => {
          this.listLoading = false;
          response.data.forEach(function(item,index){
            if(item.pid == 0){
              this.$set(item,'hasChildren',true)
              permissionListByPId(item.id).then(resp =>{
                if(resp.data.length >0){
                  this.$set(item,'children',resp.data)
                }
                
              })
              this.tableData.push(item)
            }
          },this)
          console.log('list',this.tableData)
        });
      },
      addRoleDialog(){
        this.dialogVisible = true
      },
      radioChange(){
        console.log("radioChange",this.radio)
      },
      editradioChange(){
        console.log("radioChange",this.editradio)
        this.editForm.type = this.editradio
      },
      handleNodeClick(data) {
        console.log('handleNodeClick',data);
        this.pidObj.id = data.id
        this.pidObj.name = data.name
        this.editForm.pid = this.pidObj.id
      },
      addSub(val){
        this.dialogVisible = false
        this.addForm.type = this.radio
        this.addForm.pid = this.pidObj.id
        console.log(this.addForm)
        addPermission(this.addForm).then(resp =>{
            if(resp.code == 200){
              this.$message.success('新增权限成功')
              this.getList()
            }else{
              this.$message.info(resp.message)
            }
        }).catch(err =>{
          this.$message.error('新增权限失败')
        })
      },
      pidFocus(){
        this.innerVisinnerible = true
        this.data = []
        permissionList().then(response =>{
          response.data.forEach(function(item,index){
            
            if(item.pid == 0){
              
              this.$set(item,'label',item.name)
              permissionListByPId(item.id).then(resp =>{
                resp.data.forEach(function(item1,index){
                  this.$set(item1,'label',item1.name)
                  // permissionListByPId(item1.id).then(respon =>{
                  //   respon.data.forEach(function(item2,index){
                  //     this.$set(item2,'label',item2.name)
                  //   },this)
                  //   this.$set(item1,'children',respon.data)
                  // })
                },this)
                this.$set(item,'children',resp.data)
              })
              this.data.push(item)
            }
            
          },this)
          console.log('data',this.data)
        })
      },
      editPermission(val){
        if(val.pid == 0){
          this.pidObj.id = 0
          this.pidObj.name = '一级目录'
        }else{
          permissionListById(val.pid).then(resp =>{
            console.log('编辑权限',resp.data)
            this.pidObj.id = resp.data.id 
            console.log('编辑权限pid',this.pidObj.id)
            this.pidObj.name = resp.data.name
          })
        }
       
        this.editdialogVisible = true
        console.log(val)
        this.editForm.id = val.id
        this.editForm.pid = val.pid
        this.editForm.name = val.name
        
        this.editradio = val.type
        this.editForm.type = this.editradio
        this.editForm.value = val.value
        this.editForm.uri = val.uri
        this.editForm.status = val.status
        
      },
      editSub(val){
        this.editdialogVisible = false
        console.log('editForm',val)
        editPermission(val).then(resp =>{
          if(resp.code == 200){
            this.$message.success('编辑成功')
          }
        }).catch(err =>{
          this.$message.error('编辑失败')
        })
      },
      deletePermission(val){
          this.$confirm('是否要进行该删除操作?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => { 
            let param =[{
              id : val.id
          }]
          deletePermission(param).then(resp =>{
              if(resp.code == 200){
                  this.$message.success('删除成功')
                  this.getList()
              }
              
          })
        }).catch(() => {
            this.$message.info('已取消删除')
        })
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


