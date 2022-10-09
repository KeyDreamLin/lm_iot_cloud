import LmNum from "@/components/LmNum.vue";
// plugins/i18n.js
export default {
    install: (app, options) => {
        // alert("注册成功")
        // 在这里编写插件代码
        app.component("LmNum", LmNum);
    }
}