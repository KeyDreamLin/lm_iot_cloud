// 导入vue-router，引入createRouter和createWebHashHistory方法
//import { createRouter, createWebHashHistory } from 'vue-router'
import { createRouter, createWebHistory } from 'vue-router'

import deviceList from '@/views/device/list.vue'
import deviceModelList from '@/views/device/model/list.vue'
// 子路由
const routes_children = [
    {
        path: '/',
        name: "LmDashboard",
        meta: { title: '仪表盘' },
        component: () =>
        import('@/views/dashboard/LmDashboard.vue'),
    },
    {
        path: '/device/list',
        name: "设备列表",
        meta: { title: '设备列表' },
        component : deviceList,
    },
    {
        path: '/device/model/list',
        name: "/device/model/list",
        meta: { title: '物模型管理' },
        component : deviceModelList,
    },
    {
        path: '/test',
        name: "test",
        meta: { title: '测试页面' },
        component: () =>
        import('@/views/test/LmTest.vue'),
    },
   
];

//4 :定义路由配置规则
const routes = [
    {
        // 后续可以动态生成这个地址 跟根据数据库 再说啦
        path: "/",
        meta: { title: "管理页面" },
        name: "WebManage",
        component: () =>
            import('@/views/WebManage.vue'),
        children:routes_children
    },
    {
        // 后续可以动态生成这个地址 跟根据数据库 再说啦
        path: "/asdasdasd",
        meta: { title: "后台登录" },
        name: "AdminLogin",
        component: () =>
            import('@/views/AdminLogin.vue'),
    },
    {
        // 后续可以动态生成这个地址 跟根据数据库 再说啦
        path: "/login",
        meta: { title: "用户登录" },
        name: "UserLogin",
        component: () =>
            import('@/views/UserLogin.vue'),
    },
    {
        path:"/:pathMatch(.*)*",
        name:"NotFound",
        component: () =>
            import('@/views/error/_404.vue'),
    }
]
// 创建路由对象
const router = createRouter({
    // createWebHashHistory() 路由访问模式，hash模式 /#/路由地址
    //history: createWebHashHistory(),
    // createWebHistory 路由访问模式， /路由地址 
    history: createWebHistory(),
    routes
})
router.afterEach((to, from) => {
    // 标题切换
    document.title = to.meta.title + "-LmCloud物联云" || "LmCloud物联云";
})

// 导出路由对象
export default router