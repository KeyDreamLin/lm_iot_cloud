import { createApp } from 'vue'
import './style.css'
import App from './App.vue'

// element组件导入
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
// 导入windicss库
import 'virtual:windi.css'
// 注册路由
import router from '@/router'

const app = createApp(App);

// 注册element
app.use(ElementPlus)
// 引入element所有图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
// 注册路由
app.use(router)
app.mount('#app')
