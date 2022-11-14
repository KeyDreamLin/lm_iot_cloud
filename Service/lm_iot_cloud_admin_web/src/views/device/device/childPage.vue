<template>
    <!-- 这里是设备子页面容器 -->
    <div class="lm_device_child_box">
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
.lm_device_child_box{

}
/* 将这个页面的按钮文字都设置为白色 */
.lm_device_child_box :deep(.el-button ){
    margin-top: 1px;
    color: #fff;
    border:none;
}
.lm_device_child_box :deep(.el-button--info ){
    background-color:#a162f7;
}
.lm_device_child_box:deep(.el-switch.is-checked .el-switch__core ){
    border-color: #a162f7;
    background: #a162f7;
}
</style>