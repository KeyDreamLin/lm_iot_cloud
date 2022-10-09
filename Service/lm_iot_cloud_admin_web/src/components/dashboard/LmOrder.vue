<template>
    <el-row :gutter="30" class="lm-order_box">
         <!-- 设备总数 -->
         <el-col :span="6">
            <el-card class="lm-card_box">
                <div class="lm-card-title1_box">
                    <span>设备总数</span>
                </div>
                <div class="lm-card-title2_box">
                    <div class="lm-card-title-wrapper_box">
                        <lm-num :value="1" class="lm-card-num_box"></lm-num>
                        <span>台</span>
                    </div>
                </div>
                <div class="lm-card-title3_box">
                    <div class="lm-card-title-wrapper_box">
                        <span>今天上线</span>
                        <lm-num :value="0" class="lm-card-num_box"></lm-num>
                        <span>台</span>
                    </div>
                </div>
            </el-card>  
        </el-col>

        <!-- 传感器总数 -->
        <el-col :span="6">
            <el-card class="lm-card_box">
                <div class="lm-card-title1_box">
                    <span>传感器总数</span>
                </div>
                <div class="lm-card-title2_box">
                    <div class="lm-card-title-wrapper_box">
                        <lm-num :value="12" class="lm-card-num_box"></lm-num>
                        <span>个</span>
                    </div>
                </div>
                <div class="lm-card-title3_box">
                    <div class="lm-card-title-wrapper_box">
                        <span>今日新增</span>
                        <lm-num :value="0" class="lm-card-num_box"></lm-num>
                        <span>个</span>
                    </div>
                </div>
            </el-card>  
        </el-col>

        <!-- 传感器数据总数 -->
        <el-col :span="6">
            <el-card class="lm-card_box">
                <div class="lm-card-title1_box">
                    <span>传感器数据总数</span>
                </div>
                <div class="lm-card-title2_box">
                    <div class="lm-card-title-wrapper_box">
                        <lm-num :value="121322" class="lm-card-num_box"></lm-num>
                        <span>条</span>
                    </div>
                </div>
                <div class="lm-card-title3_box">
                    <div class="lm-card-title-wrapper_box">
                        <span>今日上报</span>
                        <lm-num :value="0" class="lm-card-num_box"></lm-num>
                        <span>条</span>
                    </div>
                </div>
            </el-card>  
        </el-col>

        <!-- 策略总数 -->
        <el-col :span="6">
            <el-card class="lm-card_box">
                <div class="lm-card-title1_box">
                    <span>策略总数</span>
                </div>
                <div class="lm-card-title2_box">
                    <div class="lm-card-title-wrapper_box">
                        <lm-num :value="12" class="lm-card-num_box"></lm-num>
                        <span>个</span>
                    </div>
                </div>
                <div class="lm-card-title3_box">
                    <div class="lm-card-title-wrapper_box">
                        <span>已启用</span>
                        <lm-num :value="0" class="lm-card-num_box"></lm-num>
                        <span>个</span>
                    </div>
                </div>
            </el-card>  
        </el-col>

    </el-row>
</template>

<script setup>
    
</script>

<style scoped>

/* 去除卡片的阴影、边框 */
.lm-order_box :deep(.el-card){
    box-shadow: none;
    /* 圆角 */
    border-radius: 15px;
    border :0;
}
.lm-order_box :deep(.el-card__body){
    padding-top: 15px;
    padding-left: 15px;
}
.lm-card_box{
  
}
.lm-card-title1_box{
    display: block;
    font-size: 15px;
    font-weight: 700;
}
.lm-card-title2_box{
    display: flex;
    justify-content: center;
    align-items: center;
}
.lm-card-title3_box{
   display: flex;
    justify-content:right;
}
.lm-card-title3-wrapper_box{
    
}
/* 数字 */
.lm-card-num_box{
    color:#a162f7;
    padding: 0 5px;
    font-size: 25px;
    font-weight: 700;
}
</style>