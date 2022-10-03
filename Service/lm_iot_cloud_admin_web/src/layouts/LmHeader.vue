<template>
    <div class="lm-header_box">
        <div class="lm-header-container_box">
            <div class="lm-header-router-name_box">
                我是路由的名字
            </div>
            <div class="lm-header-user-info_box">
                <el-avatar 
                class="userImg_box"
                    shape="square" size="default" 
                    style="background: #e7e9ff; border-radius: 10px;"
                    src="https://img.js.design/assets/img/6249a8eae5886b18e2a60a87.png" 
                />
                <span class="userNanme_box">
                    AR Jakir
                </span>
                <el-dropdown class="userIcon_box">
                    <span class="el-dropdown-link">
                        <el-icon class="el-icon--right">
                            <ArrowDownBold />
                        </el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item>修改密码</el-dropdown-item>
                            <el-dropdown-item>退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
                <!-- <el-icon class="userIcon_box"><ArrowDownBold /></el-icon> -->
            </div>
        </div>
       
    </div>
</template>
<script setup>

</script>
<style scoped>
    
.lm-header_box{
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
}
/* 可视区域 */
.lm-header-container_box{
    width: 100%;
    height: 46px;
    display: flex;
    align-items: center;
    /* background: #aaa; */
}
/* 显示路由名字的盒子 */
.lm-header-router-name_box{
    /** 文本1 */
    font-size: 26px;
    font-weight: 700;
    letter-spacing: 0px;
    text-align: left;
    vertical-align: top;
}
/* 用户信息盒子 */
.lm-header-user-info_box{
    display: flex;
    align-items: center;
    width: 189px;
    height: 50px;
    margin-left: auto;
    border-radius: 10px;
    background: #fff;
    box-shadow: 0px 20px 40px 0px rgba(122, 137, 254, 0.05);
}
.userImg_box{
    margin: 5px 0 5px 5px;
}
.userNanme_box{
    display: flex;
    justify-content: center;
    flex-grow: 1;
}
.userIcon_box{
    color: #000;
    margin-left: auto;
    margin-right: 15px;
}
</style>