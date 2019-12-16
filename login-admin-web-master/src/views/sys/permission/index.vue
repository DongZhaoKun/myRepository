<template> 
  <div class="app-container">
    <el-card class="operate-container" shadow="never">
      <i class="el-icon-tickets"></i>
      <span>数据列表</span>
    </el-card>
   
    <el-tree
      :data="list"
      :props="props"
      :load="loadNode"
      :default-expand-all= "true"
      lazy
      show-checkbox
      @check-change="handleCheckChange">
    </el-tree>
    
  </div>
</template>
<script>
  import {permissionList} from '@/api/permission'
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
    name: "permissionList",
    components:{},
    data() {
      return {
         props: {
          label: 'name',
          children: 'zones'
        },
        count: 1,
        
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
      
    },
    methods: {
       handleCheckChange(data, checked, indeterminate) {
        console.log(data, checked, indeterminate);
      },
      handleNodeClick(data) {
        console.log(data);
      },
       loadNode(node, resolve) {
         console.log('node',node)
        if (node.level === 0) {
          
          permissionList().then(response => {
            this.listLoading = false;
            
            this.list = response.data;
            
            console.log('list',this.list)
            this.total = response.data.length;
          });
      
          return resolve(this.list);
        }
        if (node.level > 3) return resolve([]);

        var hasChild;
        if (node.data.name === 'region1') {
          hasChild = true;
        } else if (node.data.name === 'region2') {
          hasChild = false;
        } else {
          hasChild = Math.random() > 0.5;
        }
        setTimeout(() => {
          var data;
          if (hasChild) {
            data = [{
              name: 'zone' + this.count++
            }, {
              name: 'zone' + this.count++
            }];
          } else {
            data = [];
          }

          resolve(data);
        }, 500);
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
          permissionList().then(response => {
          this.listLoading = false;
          
          this.list = response.data;
          
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


