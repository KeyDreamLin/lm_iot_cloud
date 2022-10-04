<template>
    <div class="lm-menu_box">
        <!-- logo位置 -->
        <div class="lm-menu-logo_box">
            LmCloud
        </div>
        <!-- 分割线盒子 -->
        <div class="lm-menu-line_box">
            <div class="lm-menu-line"></div>
        </div>
        <!-- 菜单位置 -->
        <div class="lm-menu-main_box">
            <el-tree 
                :data="data" accordion
                @node-click="handleNodeClick" 
            >
              <template #default="{ node, data }">
                  <div class="flex items-center">
                      <div class="pr-2 flex items-center">
                          <el-icon >
                              <component :is="data.icon" />
                          </el-icon>
                      </div>
                      <span>{{ node.label }}</span>
                  </div>
              </template>
          </el-tree>
        </div>
    </div>
</template>

<script setup>
import WebManage from '@/views/WebManage.vue';
const handleNodeClick = (data) => {
    console.log(data)
}
const data  = [
  {
    label: '仪表盘',
    icon:"House",
    path:"/"
  },
  {
    icon:"House",
    label: 'Level one 2',
  },
  {
    icon:"House",
    label: 'Level one 3',
  },
]
</script>
<style scoped>
/* 菜单整体盒子 */
.lm-menu_box{
    display: flex;
    flex-direction: column;  
    width: 250px;
    height: 100%;
    background: #fff;;
}
/* logo位置 */
.lm-menu-logo_box{
    height: 80px !important;
    /* flex高度失效问题 */
    flex-shrink: 0;
    display: flex;
    align-items: center;
    justify-content: center;

    /* 文本 */
    font-size: 26px;
    font-weight: 700;
    letter-spacing: 0px;
    text-align: left;
    vertical-align: top;
}
/* 分割线盒子 */
.lm-menu-line_box{
    width: 100%;
    height: 1.5px;
    display: flex;
    align-items: center;
    justify-content: center;
    padding-bottom: 35px;
}
/* 分割线 */
.lm-menu-line{
    width: 206px;
    height: 1.5px;
    background: rgba(242, 242, 242, 1);
}
/* 放菜单的盒子 */
.lm-menu-main_box{
    width: 100%;
    flex-grow:1;
    padding: 0 22px 0;
    overflow-y:auto;
}
/* 隐藏tree的icon */
.lm-menu-main_box :deep(.el-tree-node__content > .el-icon){
    height: 0;
    width: 0;
    line-height: 0;
    display: none;
}

/* tree内容区域 */
.lm-menu-main_box :deep(.el-tree-node__content){
    height: 48px;
    padding: 20px !important;
    display: flex;
    align-self: center;
    justify-self: center;
    border-radius: 15px;
    margin-bottom: 10px;
}

/* tree选中高亮 */
.lm-menu-main_box :deep(.el-tree-node.is-current > .el-tree-node__content) {
    color: #fff;
    background:#a162f7;
    box-shadow: 0px 8px 20px 0px rgba(122, 137, 254, 0.25);
}
/* tree的标签 */
.lm-menu-main_box :deep(.el-tree-node__label){
    width: 100%;
}


</style>