<template>
    <span>
        {{ d.num.toFixed(0) }}
    </span>
</template>

<script setup>
import { reactive, watch } from 'vue'
import gsap from 'gsap'

const props = defineProps({
    value: {
        type: Number,
        default: 0
    }
})

const d = reactive({
    num: 0
})

function animateToNum() {
    gsap.to(d, {
        duration: 0.5,
        num: props.value
    })
}

// 执行动画
animateToNum();

// 监听数字得变化，如果变化就进行动画加载
watch(() => props.value, (count, prevCount) => {
    animateToNum();
})

</script>