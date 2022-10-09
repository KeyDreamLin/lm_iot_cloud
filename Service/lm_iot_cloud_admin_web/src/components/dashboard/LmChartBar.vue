<template>
    <!-- 图表-柱状图组件 -->
    <el-card>
        <template #header>
            <div class="flex flex-row justify-between align-center ">
                <span>统计订单</span>
                <div>
                    <el-check-tag checked @click="selectEvent(1)" class="mr-1">近一个月</el-check-tag>
                    <el-check-tag checked @click="selectEvent(2)" class="mr-1">近一周</el-check-tag>
                    <el-check-tag checked @click="selectEvent(3)" class="mr-1">近一三</el-check-tag>
                </div>
            </div>
        </template>
        <div :id="chartId" ref="charRef" style="width: 100%; height:300px ;"></div>
    </el-card>
</template>
<script setup>
import { useResizeObserver } from '@vueuse/core';
import * as echarts from 'echarts';
import { onBeforeUnmount, onMounted, ref } from 'vue';

const charRef = ref(null);
let myChart = null;
// 子组件向父组件提供属性
const props = defineProps({
    chartId: {
        type: String,
        default: "test"
    },
});

onMounted(() => {

    // 模拟服务器传过来的数据
    let serverRet = {
        title: "",
        legend: ["销量", "金额"],
        xdata: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子'],
        ydata: {},
        series: [
            {
                name: '销量',
                type: 'bar',
                data: [5, 20, 36, 10, 10, 20]
            },
            {
                name: '金额',
                type: 'bar',
                data: [15, 220, 336, 410, 160, 720]
            },
            {
                name: '金额',
                type: 'bar',
                data: [23, 223, 212, 140, 140, 260]
            }

        ]
    }
    initChart();
    updataChart(serverRet);
    // console.log(document.getElementById(props.chartId));
});
// 初始化图表组件
function initChart() {
    // 如果想要在vue里面获取dom对象，那么dum对象有点要在mounted执行之后才可以获取  这个是唯一需要注意的
    myChart = echarts.init(document.getElementById(props.chartId));
}
// 更新图表数据
function updataChart(ChartData) {
    // 如果没有初始化到的话就重新初始化一次呗
    if (!myChart) { initChart() }
    let option = {
        title: {
            text: ChartData.title
        },
        tooltip: {},
        legend: {
            data: ChartData.legend
        },
        xAxis: {
            data: ChartData.xdata
        },
        yAxis: ChartData.ydata,
        series: ChartData.series
    };
    // 执行统计报表动画, 切记动画是统计报表
    myChart.showLoading();


    // 统计报表的类型和数据
    myChart.setOption(option);
    //动画执行结束 哈哈哈给放个延时不然看起来怪怪的
    setTimeout(() => {
        myChart.hideLoading();
    }, 100)
}
// 选择统计时间事件
const selectEvent = (num) => {
    // console.log(num);
    let serverRet = {
        title: "",
        legend: ["销量", "金额"],
        xdata: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子'],
        ydata: {},
        series: [
            {
                name: '销量',
                type: 'bar',
                data: [15 * num, 20 * num, 36 * num, 15 * num, 10 * num, 20 * num]
            },
            {
                name: '金额',
                type: 'bar',
                data: [15 * num, 2 * num, 6 * num, 4 * num, 10 * num, 70 * num]
            },
            {
                name: '金额',
                type: 'bar',
                data: [3 * num, 24 * num, 6 * num, 10 * num, 1 * num, 60 * num]
            }

        ]
    }
    updataChart(serverRet);
}
// 监听浏览器的变化 去刷新表格的大小
useResizeObserver(charRef, (e) => {
    // 重新改变统计报表的大小
    myChart.resize();

});
// 在页面离开之前销毁统计报表组件 其实也不用写 写上也行
onBeforeUnmount(() => {
    if (myChart) {
        myChart.dispose();
    }
})
</script>
<style scoped>
</style>