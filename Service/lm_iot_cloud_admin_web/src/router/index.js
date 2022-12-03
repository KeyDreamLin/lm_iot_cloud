// 导入vue-router，引入createRouter和createWebHashHistory方法
//import { createRouter, createWebHashHistory } from 'vue-router'
import { createRouter, createWebHistory } from 'vue-router'

// ------------------- 设备、数据、信息 -------------------
import deviceChildPage from '@/views/device/device/childPage.vue'
import deviceInfoList from '@/views/device/device/infoList.vue'
import deviceLookOneInfo from '@/views/device/device/lookOneInfo.vue'
// ------------------- 设备、数据、信息 -------------------


// ------------------- 设备分组 -------------------
import deviceGroupingChildPage from '@/views/device/grouping/childPage.vue'
import deviceGroupingList from '@/views/device/grouping/list.vue'
import deviceGroupingLookOneInfo from '@/views/device/grouping/lookOneInfo.vue'
// ------------------- 设备分组 -------------------

// ------------------- 策略 -------------------
import deviceStrategyChildPage from '@/views/device/strategy/childPage.vue'
import deviceStrategyList from '@/views/device/strategy/list.vue'
import deviceStrategyForm from '@/views/device/strategy/examine.vue'
// ------------------- 策略 -------------------

// ------------------- 用户管理 -------------------
import userChildPage from '@/views/user/childPage.vue'
import userList from '@/views/user/list.vue'
// ------------------- 用户管理 -------------------


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
        path: '/device/info',
        meta: { title: '设备信息容器', Lname: '/device/info/list'},
        component : deviceChildPage,
        children:[
            {
                path: '/device/info/list',
                meta: { title: '设备列表', Lname: '/device/info/list'},
                component : deviceInfoList,
            },  {
                path: '/device/info/lookOneInfo',
                meta: { title: '设备详情', Lname: '/device/info/list'},
                component : deviceLookOneInfo,
            },
        ]
    },
    {
        path: '/device/grouping',
        meta: { title: '设备分组',Lname : "/device/grouping/list" },
        component : deviceGroupingChildPage,
        children:[
            {
                path: '/device/grouping/list',
                meta: { title: '设备分组', Lname: '/device/grouping/list'},
                component : deviceGroupingList,
            },
            {
                path: '/device/grouping/lookOneInfo',
                meta: { title: '设备分组详情', Lname: '/device/grouping/list'},
                component : deviceGroupingLookOneInfo,
            },
        ]
    },
    {
        path: '/device/strategy',
        meta: { title: '场景联动', Lname: '/device/strategy/list'},
        component : deviceStrategyChildPage,
        children:[
            {
                path: '/device/strategy/list',
                meta: { title: '场景联动', Lname: '/device/strategy/list'},
                component : deviceStrategyList,
            },
            {
                path: '/device/strategy/examine',
                meta: { title: '场景联动详情', Lname: '/device/strategy/list'},
                component : deviceStrategyForm,
            }
        ],
    },
    {
        path: '/user',
        meta: { title: '用户列表' , Lname: '/user/list'},
        component: userChildPage,
        children:[
            {
                path: '/user/list',
                meta: { title: '用户列表' , Lname: '/user/list'},
                component: userList,
            }
        ],

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
        meta: { title: "管理页面" ,Lname: '/'},
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
        path: "/test",
        meta: { title: "LmTest" },
        name: "LmTest",
        component: () =>
            import('@/views/test/LmTest.vue'),
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