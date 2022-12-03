// 密码的解密解密
import CryptoJS from 'crypto-js'
// element弹窗组件
import { ElNotification , ElMessageBox } from 'element-plus'
import { ElMessage } from 'element-plus'


//DES 加密 key与后端一样的秘钥(8的倍数)   message(值)
export function encryptByDES(message, key = "{!LM&XG@2022%06%01+}") {
    var keyHex = CryptoJS.enc.Utf8.parse(key);
    var encrypted = CryptoJS.DES.encrypt(message, keyHex, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    });
    return encrypted.toString();
}

//DES 解密
export function decryptByDES(ciphertext, key = "{!LM&XG@2022%06%01+}") {
    var keyHex = CryptoJS.enc.Utf8.parse(key);
    // direct decrypt ciphertext
    var decrypted = CryptoJS.DES.decrypt({
        ciphertext: CryptoJS.enc.Base64.parse(ciphertext)
    }, keyHex, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    });
    return decrypted.toString(CryptoJS.enc.Utf8);
}

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