<template>
    <div class="lm_box">
        <!-- 菜单 -->
        <div class="lm-left_box">
            <lm-menu></lm-menu>
        </div>

        <div class="lm-right_box">
            <!-- 头 -->
            <div class="lm-right-up_box">
                <lm-header></lm-header>
            </div>
            <!-- 内容区 -->
            <div class="lm-right-down_box">
                <router-view v-slot="{ Component }">
                    <transition name="fade">
                        <keep-alive>
                            <component :is="Component"></component>
                        </keep-alive>
                    </transition>
                </router-view>
            </div>
        </div>
    </div>
</template>

<script setup>
import LmHeader from '@/layouts/LmHeader.vue';
import LmMenu from '@/layouts/LmMenu.vue';
</script>
<style scoped>
.lm_box {
    display: flex;
    flex-direction: row;
    height: 100vh;
}
/* 左边盒子 用于菜单 */
.lm-left_box{
    width: 250px;
    height: 100%;
    background: #fff;
    overflow-y:auto;
}
/* 右边盒子 上下 上标题、信息等 下暂时内容 */
.lm-right_box{
    position: absolute;
    left: 250px;
    right: 0;
    top: 0;
    bottom: 0;
    height: 100%;
    background: #f9f9f9;
    /* background: rgb(143, 75, 75); */
    display: flex;
    flex-direction: column;
    /* 左右30px */
    padding: 0 30px;
    
}
/* 右边上盒子用于用户信息 路由名称等暂时 */
.lm-right-up_box{
    width: 100%;
    height: 80px;
    /* background: rgb(212, 84, 84); */
}
/* 右边下盒子用于子路由展示 */
.lm-right-down_box{
    width: 100%;
    flex-grow: 1;
    /* background: rgb(171, 9, 9); */
}
</style>