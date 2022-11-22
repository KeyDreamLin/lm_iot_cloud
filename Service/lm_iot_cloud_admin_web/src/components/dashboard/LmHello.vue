<template>
    <div class="lm-hello_box">
        <div class="lm-hello-left_box">
            <div class="lm-hello-title">
                <span class="lm-title_1">{{getTimeState()}}好 , XiaoMa</span>
                <!--  -->
                <div class="lm-title-line"></div>
                <span class="lm-title_2">人生最精彩的不是成功的那一瞬间，而是回头看，那段漆黑看似没有尽头、苦苦摸索的过程。</span>
            </div>
          
        </div>
        <div class="lm-hello-right_box">
            <el-image class="lm-hello-right-img"  src="src/assets/helloimg/img_1.png" fit="cover" />
        </div>
    </div>
</template>


<script setup>
// 状态管理
import storage from '@/storage';


let getTimeState = () => {
    // 获取当前时间
    let timeNow = new Date();
    // 获取当前小时
    let hours = timeNow.getHours();
    // 设置默认文字
    let text = '';
    // 判断当前时间段
    if (hours >= 0 && hours <= 6) {
        text = '凌晨';
    }
    if (hours > 6 && hours <= 12) {
        text = '上午';
    }
    if (hours > 12 && hours <= 13) {
        text = '中午';
    }
    if (hours > 13 && hours <= 18) {
        text = '下午';
    }
    if (hours > 18 && hours <= 24) {
        text = '晚上';
    }
    // 返回当前时间段对应的状态
    return text;
}
</script>
<style scoped>

.lm-hello_box{
    height: 200px;
    background: #fff;
    border-radius: 15px;
    display: flex;
    position: relative;
}
/* 左边盒子 */
.lm-hello-left_box{
    display: flex;
    flex-direction: column;
    overflow: hidden;
}
.lm-hello-left-bg_box{
    overflow: hidden;
    border-radius: 0 0 0 15px;
}
/* 文案等 */
.lm-hello-title{
    padding: 15px 0 0 15px;
}
/* 问候1 时段+名字 */
.lm-title_1{
    display: block;
}
/* 小小分割线 */
.lm-title-line{
    width: 200px;
    margin: 8px 0 15px;
    flex-grow: 1;
    height: 1px;
    opacity: 1;
    background: #a162f7;
}
/* 文案 */
.lm-title_2{
    display: block;
    text-indent: 1em;
}
/* 左边盒子 */

/* 右边盒子 */
.lm-hello-right_box{
    margin-left: auto;
}

/* 右边盒子 */

/* 右边的图片 */
.lm-hello-right-img{
    width: 600px;
    height: 200px;
    border-radius: 15px;
    overflow: hidden;
}

</style>