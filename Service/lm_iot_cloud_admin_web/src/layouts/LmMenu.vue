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
            <el-menu
                :default-active="thisPath"
                @select="selectMenuEvent"
            >
                <template v-for="(menu_root, index) in menuTerrData" :key="menu_root.id +''">
                    <!-- 父元素有子元素的 -->
                    <template v-if="menu_root.children.length > 0">
                        <!-- 因为父元素的path是一样的 所以index不能用path用id代替就行 -->
                        <el-sub-menu :index="menu_root.id +''">
                            <template #title>
                                <span>{{ menu_root.name }}</span>
                            </template>
                            <!-- 遍历子元素出来 -->
                            <template 
                                v-for="(menu_c ,index) in menu_root.children" 
                                :key="menu_c.id +''"
                            >
                                <!-- index 用path是方便获取到路由路径后选中对应的项 -->
                                <el-menu-item :index="menu_c.path" >
                                    <template #title>
                                        <span>{{ menu_c.name }}</span>
                                    </template>
                                </el-menu-item>

                            </template>
                        </el-sub-menu>
                    </template>
                    <!-- 无子元素的父元素 -->
                    <template v-else>
                        <!-- index 用pathname是方便获取到 子路由中的子路由 路径后选中对应的项 -->
                        <el-menu-item :index="menu_root.path">
                            <template #title>
                                <span>{{ menu_root.name }}</span>
                            </template>
                        </el-menu-item>
                    </template>
                </template>
            </el-menu> 
            {{thisPathName}}
        </div>
    </div>
</template>

<script setup>
import { ref , onMounted, computed } from 'vue'
import permissionService from '@/services/common/permission/PermissionService'
// 用于路由对象 对路由进行操作
import { useRouter } from 'vue-router';
// 用于获取当前路由的状态和地址
import { useRoute } from 'vue-router';
// 用于路由对象 对路由进行操作
const router = useRouter();
// 用于获取当前路由的状态和地址
const route = useRoute();

// 菜单数据
const menuTerrData  =ref([]);
// 当前路由的地址
const thisPath = computed(() => { 
    
    return route.meta.Lname; 
});
// 获取菜单信息
const initMenu = async()  =>{
    let response = await permissionService.menuTree();
    menuTerrData.value = response.data;
    console.log(menuTerrData.value);
}
// 菜单选中项回调
const selectMenuEvent = (indexPath) =>{
    console.log("菜单选中项回调--->",indexPath);
    router.push(indexPath);
}

onMounted(()=>{
    console.log(route);
    initMenu();
});
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

/* 去除默认的右边线 */
.lm-menu-main_box :deep(.el-menu){
    border-right: none;
}

/* 不显示滚动条 */
.lm-menu-main_box::-webkit-scrollbar {
    display: none;
    width: 0;
}
.lm-menu-main_box :deep(.el-sub-menu__title) {
    border-radius: 15px;
    margin: 5px 0 ;
}
.lm-menu-main_box :deep(.el-menu-item) {
    border-radius: 15px;
    margin: 5px 0 ;
}

/* 改变elementui 侧边栏移入颜色 */
.lm-menu-main_box :deep(.el-sub-menu__title:hover) {
    background:#ebdffe;
}
.lm-menu-main_box :deep(.el-menu-item:hover) {
    background:#ebdffe;
}
.lm-menu-main_box :deep(.el-submenu__title:hover) {
    background:#ebdffe;
}
/* 改变elementui 侧边栏移入颜色 */


 /* tree选中高亮 */
 .lm-menu-main_box :deep(.el-menu-item.is-active){
    color: #fff;
    background:#a162f7;
    border-radius: 15px;
}
</style>