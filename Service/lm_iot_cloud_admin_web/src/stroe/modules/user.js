import adminLoginService from '@/services/admin_user/AdminLoginService';

export default {
    namespaced: true,

    state() {
        return {
           // 管理用户信息
           userId: "",
           username: "",
           avatar: "",
           tokenjj: "",
           // 这个是用户角色
           roleList: [],
           // 权限
           permissionList: [],
           tokenUuid: ""
        }
    },
    // 调用的方法commit("名称") 通常用于同步数据、变量
    mutations: {
        savaUserData(state, serverUserData) {
            state.tokenJj = serverUserData.tokenJj;
            state.tokenUuid = serverUserData.tokenUuid;
            state.userId = serverUserData.user.id;
            state.username = serverUserData.user.username;
            state.avatar = serverUserData.user.avatar;
            state.roleList = serverUserData.roleNames;
            state.permissionList = serverUserData.permissions;
            console.log("stroe:用户信息保存---->", state);
        },
    },
    // 调用的方法dispatch("名称")  通常用于异步查询
    actions: {
        async toLogin(context, adminLoginData) {
            try{
                // 管理员登录服务
                let serverReponse = await adminLoginService.login(adminLoginData);
                // console.log("服务器回调:登录成功信息--------->",serverReponse.data);
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
        // 组装一个角色信息返回
        getRoleName(state) {
            return state.roleList.map(role => role.name).join(",");
        },
        // 获取权限
        getPermissions(state) {
            return state.permissionList;
        },
        // 获取用户ID
        getUserId(state) {
            return state.userId;
        },
        // 获取用户名称
        getUserName(state){
            return state.username;
        },
         // 获取用户头像
         getUserAvatar(state){
            return state.avatar;
        },
        // 获取token
        getTokenJj(state) {
            return state.tokenJj;
        },
        // 获取tokenUuid 用于同一账号挤下线
        // 因为你每次登录都会产生新的uuid。所以旧的就被你新挤掉
        getTokenUuid(state) {
            return state.tokenUuid;
        },
        // 判断是否登录
        isLogin(state) {
            return state.userId != "";
        },
        // 获取角色列表
        getRoleNames(state){
            return state.roleList.join(",");
        }
    }

}