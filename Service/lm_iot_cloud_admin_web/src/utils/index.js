// element弹窗组件
import { ElNotification , ElMessageBox } from 'element-plus'
import { ElMessage } from 'element-plus'


// 弹窗 start
export const LmMessageSuccess = (msg,title) => {
    ElMessage.closeAll()
    ElNotification({
        title: title,
        message: msg,
        type: 'success',
    })
}
export const LmMessageWarning = (msg,title) => {
    ElMessage.closeAll()
    ElNotification({
        title: title,
        message: msg,
        type: 'warning',
    })
}
export const LmMessageInfo = (msg,title) => {
    ElMessage.closeAll()
    ElNotification({
        title: title,
        message: msg,
        type: 'info',
    })
}
export const LmMessageError = (msg,title) => {
    ElMessage.closeAll()
    ElNotification({
        title: title,
        message: msg,
        type: 'error',
    })
}
// 弹窗 end
/*确认提示 */
export function LmMessageConfirm(message = "你确定要离开吗？", title = "提示", type = "warning") {
    // 使用Promise封装 使用者 用await同步返回的结果在进行处理就行 默认异步
    return new Promise((resolve, reject) => {
        ElMessageBox.confirm(message, title, {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type,
        }).then(() => {
            resolve(true)
        }).catch(() => {
            resolve(false)
        })
    })

}