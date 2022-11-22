import userService from '@/services/user/UserService';
export default {
    namespaced: true,

    state() {
        return {
           userId: "", // 用户id
           username: "",  // 用户名称
           avatar: "",  // 用户头像
           tokenJj: "",  // jwt
           roleCode: "",    // 角色信息
        }
    },
    // 调用的方法commit("名称") 通常用于同步数据、变量
    mutations: {
        savaUserData(state, serverUserData) {
            state.tokenJj = serverUserData.tokenJj;
            state.userId = serverUserData.user.id;
            state.username = serverUserData.user.username;
            state.avatar = serverUserData.user.avatar;
            state.roleCode = serverUserData.roleCode;
            console.log("stroe:用户信息保存---->", state);
        },
    },
    // 调用的方法dispatch("名称")  通常用于异步查询
    actions: {
        async toLogin(context, adminLoginData) {
            try{
                // 用户登录服务
                let serverReponse = await userService.login(adminLoginData);
                console.log("服务器回调:登录成功信息--------->",serverReponse.data);
                context.commit("savaUserData", serverReponse.data);
                // 抛出成功
                return Promise.resolve(serverReponse);
            }catch(err){
                // console.log("服务器回调:登录异常信息--------->",err);
                return Promise.reject(err);
            }
        }
    },
    // getters["名称"]  获取数据
    getters: {
        // 获取用户ID
        getUserId(state) {
            return state.userId;
        },
        // 获取用户名称
        getUserName(state){
            return state.username;
        },
        // 获取jwt
        getTokenJj(state){
            return state.tokenJj;
        },
        // 获取用户头像
        getUserAvatar(state){
            return state.avatar;
        },
        // 判断是否登录
        isLogin(state) {
            return state.userId != "";
        },
        // 获取角色代号
        getRoleCode(state){
            return state.roleCode;
        }
    }

}