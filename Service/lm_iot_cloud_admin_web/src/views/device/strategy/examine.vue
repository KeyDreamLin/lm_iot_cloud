<template>
    <!-- 查看、添加、修改策略信息 -->
    <div class="lm_main_box">
        <!-- 头部 返回上一页-策略名字-编辑按钮 策略描述-->
        <div class="lm_main_header_box">
            <!-- 头部上边  -->
            <div class="lm_mian_header_up_box">
                <!-- 头部上边 左边 上一页按钮 策略名字 -->
                <div class="lm_main_header_up_left_box">

                    <!-- 返回icon 当做按钮 -->
                    <div class="lm_main_header_up_left_icon_box" @click="looktgEvent">
                        <el-icon><ArrowLeftBold /></el-icon>
                    </div>
                    <span>{{strategyInfo.name}}</span>
                </div>

                <!-- 头部上边 右边 编辑按钮 -->
                <div class="lm_main_header_up_right_box">
                    <el-button type="info">编辑</el-button>
                </div>
            </div>
            <!-- 头部下边 策略描述  -->
            <div class="lm_mian_header_down_box">
                <span>描述：</span>
                <span>{{strategyInfo.describe}}</span>
            </div>
        </div>
        <!-- 展示 策略表达式、命令表达式 -->
        <div class="lm_body_box">
            <!-- 策略规则表达式表单 动态 -->
            <div class="lm_body_trigger_form">
                <div style="font-weight: 800; margin-top: 10px;">触发条件</div>
                <template v-for="(item,index) in parseStrategy.triggerList">
                    <template v-if="item.isLogicalOperator == false">
                        <div class="ml-2 mt-2">触发条件{{((index+1)/2)+0.5}}</div>
                        <el-select v-model="item.deviceSn" class="m-2" placeholder="请选择设备">
                            <el-option v-for="item in testList" :key="item" :label="item" :value="item" />
                        </el-select>
                        <el-select v-model="item.deviceModelIdentifier" class="m-2" placeholder="请选择设备标识符">
                            <el-option v-for="item in tes1tList" :key="item" :label="item" :value="item" />
                        </el-select>
                        <el-select v-model="item.operator" class="m-2" placeholder="请选择比较模式">
                            <el-option
                                v-for="item in relationalOperatorList"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                            />
                        </el-select>
                        <el-input v-model="item.cutOff" class="m-2" placeholder="请输入比较值"></el-input>
                        <el-button class="ml-2" type="info" @click="addTriggerItemEvent(index)">添加</el-button>
                        <el-button class="ml-2" type="info" @click="delTriggerItemEvent(item,index)">删除</el-button>
                    </template>
                    <template v-if="item.isLogicalOperator == true">
                        <div>
                            <el-select v-model="item.operator" class="m-2" placeholder="请选择比较模式">
                                <el-option
                                v-for="item in logicalOperatorList"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                            />
                            </el-select>
                        </div>
                    </template> 
                </template>
                <template v-if="parseStrategy.triggerList.length <= 0">
                    <el-button class="ml-2" type="info" @click="addTriggerItemEvent(index)">添加触发条件</el-button>
                </template>
            </div>
            <!-- 策略执行动作表达式表单 动态 -->
            <div class="lm_body_action_form">
                <div style="font-weight: 800; margin-top: 10px;">执行动作</div>
                <template v-for="(item, index) in parseStrategy.actionList">
                    <div class="ml-2 mt-2">执行动作{{index+1}}</div>
                    <div class="flex items-center">
                        <el-select v-model="item.deviceSn" class="m-2" placeholder="请选择设备">
                            <el-option v-for="item in testList" :key="item" :label="item" :value="item" />
                        </el-select>
                        <el-select v-model="item.deviceModelIdentifier" class="m-2" placeholder="请选择设备标识符">
                            <el-option v-for="item in tes1tList" :key="item" :label="item" :value="item" />
                        </el-select>
                        <span>发送</span>
                        <el-input v-model="item.cmdData" class="m-2" placeholder="请输入值"></el-input>
                        <span>到设备</span>
                        <el-button class="ml-2" type="info" @click="addActionItemEvent(index)">添加</el-button>
                        <el-button class="ml-2" type="info" @click="delActionItemEvent(index)">删除</el-button>
                    </div>
                    <div class="ml-2 flex items-center">
                        <span>延迟</span>
                        <el-input-number class="ml-2 mr-2" v-model="item.delayTime" :min="0" :max="3600" />
                        <span>秒</span>
                    </div>
                </template>
                <template v-if="parseStrategy.actionList.length <= 0">
                    <el-button class="ml-2" type="info" @click="addActionItemEvent(index)">添加执行动作</el-button>
                </template>

            </div>
            <div class="m-2">
                <el-button @click="test" type="info" >保存场景联动</el-button>
            </div>
        </div>

    </div>
</template>
<script setup>
import deviceStrategyService from '@/services/devicestrategy/DeviceStrategyService';
import { computed } from '@vue/reactivity';
import { map } from 'lodash';
import { onActivated, ref } from 'vue';
// 用于获取当前路由的状态和地址
import { useRoute } from 'vue-router';
// 用于获取当前路由的状态和地址
const route = useRoute();
// 策略id
const pathStrategyID = computed(()=>{return route.query.id});
// 策略信息
const strategyInfo = ref({});
// 解析后的策略信息  
const parseStrategy = ref({
    triggerList:[],
    actionList:[],
});
// 关系运算符
const relationalOperatorList = [
    {label:"小于等于",value:"<="},
    {label:"大于等于",value:">="},
    {label:"大于",value:">"},
    {label:"小于",value:"<"},
    {label:"等于",value:"=="},
    {label:"不等于",value:"!="},
];
// 逻辑运算符
const logicalOperatorList = [ 
    {label:"并且",value:"&&"},
    {label:"或者",value:"||"},
];
const testList = ["a","b","c","d","e","f","g","h","i"];
const tes1tList = ["t1","t2","t3","t4","t5","t6","t7","t8","t9"];

// 获取策略信息
const getStrategyInfo = (async ()=>{
    let responseStrategyInfo = await  deviceStrategyService.getInfoById(pathStrategyID.value);
    strategyInfo.value = responseStrategyInfo.data;
    console.log("strategy---------",responseStrategyInfo);
});
// 添加一条规则表达式对象
const addTriggerItemEvent = ((triggerIndex)=>{

    // 如果添加的关系表达式下面没有逻辑表达式的时候 - 在关系表达式的最后一条的时候
    if(parseStrategy.value.triggerList[triggerIndex+1] == undefined){
        // 先加一条逻辑表达式
        parseStrategy.value.triggerList.splice(triggerIndex+2,0,{deviceSn:"",deviceModelIdentifier:"",operator:"",cutOff:"",isLogicalOperator:true,});
        // 再加一条关系表达式
        parseStrategy.value.triggerList.splice(triggerIndex+3,0,{deviceSn:"",deviceModelIdentifier:"",operator:"",cutOff:"",isLogicalOperator:false,});
        console.log("添加 逻辑表达式 关系表达式 ");
    }
    // 如果添加的关系表达式下面有逻辑表达式的时候  如果这一条是逻辑表达式的时候 
    else if(parseStrategy.value.triggerList[triggerIndex+1].isLogicalOperator == true){
        // 那就先加一条关系表达式
        parseStrategy.value.triggerList.splice(triggerIndex+2,0,{deviceSn:"",deviceModelIdentifier:"",operator:"",cutOff:"",isLogicalOperator:false,});
        // 再加一条逻辑表达式
        parseStrategy.value.triggerList.splice(triggerIndex+3,0,{deviceSn:"",deviceModelIdentifier:"",operator:"",cutOff:"",isLogicalOperator:true,});
        console.log("添加 关系表达式 逻辑表达式 ");
    }

    // console.log(parseStrategy.value.triggerList);
});
// 删除一条规则表达式对象
const delTriggerItemEvent = ((triggerItem,triggerIndex)=>{
    // 如果是删除最后一条的时候 
    if(parseStrategy.value.triggerList[triggerIndex+1] == undefined ){
        // 删除自己  关系表达式
        parseStrategy.value.triggerList.splice(triggerIndex,1);
        // 再删除上一条 逻辑表达式
        parseStrategy.value.triggerList.splice(triggerIndex-1,1);
        console.log("删除 当前 关系表达式 上一条逻辑表达式");
    }
    // 如果存在逻辑表达式
    else if(parseStrategy.value.triggerList[triggerIndex+1].isLogicalOperator==true){
        // 删除自己和下一条  就是关系表达式和逻辑表达式
        parseStrategy.value.triggerList.splice(triggerIndex,2);
        console.log("删除 自己和下一条 就是关系表达式和逻辑表达式");
    }
});

// 添加一条执行动作
const addActionItemEvent = ((actionIndex)=>{
    parseStrategy.value.actionList.splice(actionIndex+1,0,{deviceSn: '', deviceModelIdentifier: '', cmdData: '', delayTime: 0});
    console.log("---->",actionIndex);
});
// 删除一条执行动作
const delActionItemEvent = ((actionIndex)=>{
    parseStrategy.value.actionList.splice(actionIndex,1);
});

// 测试保存
const test = (()=>{
    console.log("--------------策略规则对象列表转字符串-->",triggerObjListToStr(parseStrategy.value.triggerList));
    console.log("--------------策略执行动作对象列表转字符串-->",actionObjListToStr(parseStrategy.value.actionList));
});

// 将触发策略规则的表达式字符串 转换为 对象列表 
const triggerStrToObjList = ((triggerStr)=>{
    // /\{(.*?)\}/g                     提取{}中的值
    // /([^_]+)/g                       提取sn_identifier的值 0是sn 1是identifier
    // /[<= >= > < == !=]+/g            提取关系运算符的正则表达式
    // /[&& ||]+/g                      提取逻辑运算符的正则表达式
    // /[^ <= >= >< == != && ||]+ /g    提取除了关系、逻辑表达式的值
    // 1、通过正则表达式提取{}中的值
    // 保存sn码和标识符的列表
    let sn_identifier_list = triggerStr.match(/\{(.*?)\}/g);
    if(sn_identifier_list == undefined){
        sn_identifier_list = [];
    }
    // 2、创建一个临时变量用于保存删除{}和{}中的值

    // 保存 删除sn_Identifier后的策略表达式
    let delSI_triggerStr = "";
    var reg1 = new RegExp('{','g');
    var reg2 = new RegExp('}','g');
    var reg3 = new RegExp("'",'g');
    triggerStr = triggerStr.replace(reg1,"");
    triggerStr = triggerStr.replace(reg2,"");
    triggerStr = triggerStr.replace(reg3,"");
    delSI_triggerStr = triggerStr;
    for(let i = 0 ; i<sn_identifier_list.length;i++){
        // 取出 策略表达式中的sn_Identifier 后 删除策略表达式中的{}
        sn_identifier_list[i] = sn_identifier_list[i].replace(reg1,"");
        sn_identifier_list[i] = sn_identifier_list[i].replace(reg2,"");
        // 然后将策略表达式中的sn_Identifier 删除
        delSI_triggerStr = delSI_triggerStr.replace(sn_identifier_list[i],"");
    }
    // console.log("strategy_examine-triggerObjListToStr 去除{}、sn_Identifier后的策略表达式的值->",sn_identifier_list,delSI_triggerStr);

    // 提取策略表达式 中的 关系运算符
    let relationalOperatorList = delSI_triggerStr.match(/[<= >= > < == !=]+/g);
    if(relationalOperatorList == null){
        relationalOperatorList = [];
    }
     // 提取策略表达式 中的 判断值cutOff
    let cutOffList = delSI_triggerStr.match(/[^ <= >= >< == != && ||]+/g);
    // console.log("策略表达式中的关系运算符",relationalOperatorList,"以及判断的值",cutOffList);
    // 提取策略表达式 中的 逻辑运算符 
    let logicalOperatorList = delSI_triggerStr.match(/[&& ||]+/g );
    if(logicalOperatorList == null){
        logicalOperatorList = [];
    }

    let tempParseStrategy = {
        triggerList:[]
    }
    // 使用逻辑运算符遍历
    for(let i = 0; i<relationalOperatorList.length; i++){
        // 一个临时变量 用于保存策略表达式拆解出来的值
        let tempTrigger1 = {
            deviceSn:"",
            deviceModelIdentifier:"",
            operator:"",
            cutOff:"",
            isLogicalOperator:false,
        };
        let tempTrigger2 = {
            deviceSn:"",
            deviceModelIdentifier:"",
            operator:"",
            cutOff:"",
            isLogicalOperator:true,
        };
        // 处理sn_identifier
        let sn_or_identifier = sn_identifier_list[i].match(/([^_]+)/g);
        // 将sn传入
        tempTrigger1.deviceSn = sn_or_identifier[0];
        // 将identifier传入
        tempTrigger1.deviceModelIdentifier = sn_or_identifier[1];
        // 将关系标识符传入
        tempTrigger1.operator = relationalOperatorList[i];
        // 将关系判断值传入
        tempTrigger1.cutOff = cutOffList[i];
        // 关系表达式必定是会比逻辑关系符要少一个的 要加判断
        if(i<logicalOperatorList.length){
            // 将逻辑判断值传入
            tempTrigger2.operator = logicalOperatorList[i];
        }
        // 传入数组
        tempParseStrategy.triggerList.push(tempTrigger1);
        if(i<logicalOperatorList.length){
            tempParseStrategy.triggerList.push(tempTrigger2);
        }
        // console.log(tempTrigger1,tempTrigger2);
    }
    console.log("strategy_examine-triggerObjListToStr 触发策略规则表达式字符串解析出对象列表--->",tempParseStrategy.triggerList);
    return tempParseStrategy.triggerList;
});

// 将对象列表 转换为 触发策略规则的表达式字符串
const triggerObjListToStr = (()=>{
    let triggerStr = "";
    // 遍历列表中的策略表达式
    for (let i = 0; i < parseStrategy.value.triggerList.length; i++) {
        let triggerItem = parseStrategy.value.triggerList[i];
        console.log("objlist to str-->",triggerItem);        
        console.log("objlist to str-->",triggerItem.operator.length);        
        // 关系关系符
        if(triggerItem.isLogicalOperator == false){
            // 判空
            if(
                triggerItem.deviceSn.length>0 && 
                triggerItem.deviceModelIdentifier.length>0 && 
                triggerItem.operator.length>0 && 
                triggerItem.cutOff.length>0 
            ){
                // 判断是否是数字
                var tempSun = Number(triggerItem.cutOff);
                if (!isNaN(tempSun))
                {
                    triggerStr += `{${triggerItem.deviceSn}_${triggerItem.deviceModelIdentifier}}${triggerItem.operator}${triggerItem.cutOff}`;
                }
                else{
                    // 是字符串就给cutOff加上''
                    triggerStr += `{${triggerItem.deviceSn}_${triggerItem.deviceModelIdentifier}}${triggerItem.operator}'${triggerItem.cutOff}'`;
                }
            }
        }
        // 逻辑关系符
        if(triggerItem.isLogicalOperator == true){
            // 判断下一条 有没有数据 如果没有下一条或者下一条的数据是空的时候 就不加逻辑运算符
            let triggerItem1 = parseStrategy.value.triggerList[i+1];
            if(
                triggerItem1 != undefined && 
                triggerItem1.deviceSn.length>0 && 
                triggerItem1.deviceModelIdentifier.length>0 && 
                triggerItem1.operator.length>0 && 
                triggerItem1.cutOff.length>0 
                ){
                if(triggerItem.operator.length > 0 ){
                    console.log(".................");
                    triggerStr += triggerItem.operator;
                }
            }
          
        }
    }
    return triggerStr;
});

// 将 执行规则命令 的 表达式字符串 转换为 对象列表 
const actionStrToObjList = ((actionStr)=>{
//  /[^&]+/g    拆解 执行命令
// /([^_]+)/g                       提取sn_identifier的值 0是sn 1是identifier

    var reg1 = new RegExp("'",'g');
    actionStr = actionStr.replace(reg1,"");
    // 通过& 拆解多个执行规则命令
    let sn_identifier_cmdCal_delayTime_list = actionStr.match(/[^&]+/g);
    if(sn_identifier_cmdCal_delayTime_list == undefined){
        sn_identifier_cmdCal_delayTime_list = [];
    }
    // console.log("strategy_examine-actionStrToObjList 解析执行命令后的列表-->",sn_identifier_cmdCal_delayTime_list);
    let actionList = [];
    for (let i = 0; i < sn_identifier_cmdCal_delayTime_list.length; i++) {
        let temp_sn_identifier_cmdCal_delayTime = sn_identifier_cmdCal_delayTime_list[i].match(/[^:]+/g);
        let tmepAction = {
            deviceSn:"",
            deviceModelIdentifier:"",
            cmdData:"",
            delayTime:"0",
        };
        // 将sn码和设备标识符解析出来 
        let temp_sn_identifier = temp_sn_identifier_cmdCal_delayTime[0].match(/([^_]+)/g);
        tmepAction.deviceSn = temp_sn_identifier[0];
        tmepAction.deviceModelIdentifier = temp_sn_identifier[1];
        tmepAction.cmdData = temp_sn_identifier_cmdCal_delayTime[1].match(/([^_]+)/g)[0];
        tmepAction.delayTime = temp_sn_identifier_cmdCal_delayTime[2].match(/([^_]+)/g)[0];
        actionList.push(tmepAction);
    }
    console.log("strategy_examine-actionStrToObjList 解析规则执行命令字符串后的对象列表-->",actionList);
    return actionList;
});

// 将 执行规则命令 的 对象列表 转换为 表达式字符串
const actionObjListToStr = (()=>{
    let actionStr = "";
    for (let i = 0; i < parseStrategy.value.actionList.length; i++) {
        let temp = parseStrategy.value.actionList[i];
        if(
            temp != undefined && 
            temp.deviceSn.length>0&&
            temp.deviceModelIdentifier.length>0&&
            temp.cmdData.length>0&&
            temp.delayTime>=0
        ){
            var tempSum = Number(temp.cmdData);
            if (!isNaN(tempSum)){
                // 是数字的时候
                actionStr+= `${temp.deviceSn}_${temp.deviceModelIdentifier}:${temp.cmdData}:${temp.delayTime}`;
            }
            else{
                // 是字符串是时候就加上 ''
                actionStr+= `${temp.deviceSn}_${temp.deviceModelIdentifier}:'${temp.cmdData}':${temp.delayTime}`;
            }
            // 最后一个就不加拼接符啦
            if(i!=parseStrategy.value.actionList.length-1){
                actionStr+='&'
            }
        }
    }
    console.log("-----------------",actionStr);
    return actionStr;
});

onActivated(async()=>{
    await getStrategyInfo();
    // 解析服务器传过来的策略规则表达式
    parseStrategy.value.triggerList = await triggerStrToObjList(strategyInfo.value.triggerStr);
    parseStrategy.value.actionList = await actionStrToObjList(strategyInfo.value.actionStr);
    console.log("strategy_examine-onActivated 将表达式字符串解析完后的对象列表--->",parseStrategy.value);
    // actionStrToObjList(actionObjListToStr());
});
// onDeactivated
</script>

<style scoped>
/*  */
.lm_main_box{

}
/* 主体头部 */
.lm_main_header_box{
    display: flex;
    flex-direction: column;
    justify-items: center;
}
/* 主体头部上半部分 */
.lm_mian_header_up_box{
    display: flex;
    font-size: 20px;
    font-weight: 800;
}
/* 主体头部上半部分 左边 */
.lm_main_header_up_left_box{
    display: flex;
    align-items: center;
}
/* 主体头部上半部分 左边 icon按钮 */
.lm_main_header_up_left_icon_box{
    display: flex;
    align-items: center;
}
/* 主体头部上半部分 右边 */
.lm_main_header_up_right_box{
    margin-left: auto;
}
/* 主体头部下半部分 */
.lm_mian_header_down_box{
    margin-top: 10px;
}
</style>