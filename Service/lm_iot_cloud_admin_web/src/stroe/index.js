import { createStore } from 'vuex'
// 持久化管理信息
import VuexPersistence from 'vuex-persist'
import user from '@/stroe/modules/user'

// 本地缓存vuex管理信息
// 为什么要适应vuex-persist组件，因为vuex数据库如果不持久化有一个bug
// 当然用户刷新F5或者右键刷新的时候，vuex数据就会自动丢失。 
// localStorage 可以跨域浏览器窗口、选项卡之间进行共享数据
// SessionStorage 独立于浏览器窗口、选项卡的，不同浏览器窗口，或者两个选项卡之间都无法共享数据，数据只存在于当前这个浏览器窗口中的这一个选项卡
const vuexLocal = new VuexPersistence({
    key: "lm_iot_cloud_admin_vuex",
    storage: window.sessionStorage
});

// 创建一个新的 store 实例
// 创建状态管
const store = createStore( {
    plugins: [
        vuexLocal.plugin,
    ],
    modules: {
        user,
    }
})

// 导出状态管理
export default store;