<template>
    <!-- 这里是规则子页面容器 -->
    <div class="lm_child_box">
        <router-view v-slot="{ Component }">
            <transition name="fade">
                <keep-alive>
                    <component :is="Component"></component>
                </keep-alive>
            </transition>
        </router-view>
    </div>
</template>
<style scoped>
.lm_child_box{

}
/* 将这个页面的按钮文字都设置为白色 */
.lm_child_box :deep(.el-button--cancel){
    color: #000;
    background-color: #f4f4f4;
    border:none;
}

.lm_child_box :deep(.el-button--info ){
    margin-top: 1px;
    color: #fff;
    border:none;
    background-color:#a162f7;
}
.lm_child_box:deep(.el-switch.is-checked .el-switch__core ){
    border-color: #a162f7;
    background: #a162f7;
}
.lm_child_box:deep(.el-input){
    width: inherit;
    display: inline-block;
    position: relative;
    vertical-align: middle;
    line-height: 32px;
}
.lm_child_box:deep(.el-input-number){
    width: inherit;
    line-height: inherit;
}
.lm_child_box:deep(.el-select){
    margin-right: 8px;
}


</style>