<template>
    <!-- 查看、添加、修改策略信息 -->
    <div>测试测试子-子路由</div>
    {{strategyID}}
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
// 解析后的策略信息  
const parseStrategy = ref({
    triggerList:[
        {
            deviceSn:"a",
            deviceModelIdentifier:"t1",
            operator:">",
            cutOff:"1"
        },
        {
            deviceSn:"",
            deviceModelIdentifier:"",
            operator:"&&"
        },
        {
            deviceSn:"b",
            deviceModelIdentifier:"t2",
            operator:">=",
            cutOff:"2"
        },
        {
            deviceSn:"",
            deviceModelIdentifier:"",
            operator:"||"
        },
        {
            deviceSn:"c",
            deviceModelIdentifier:"t3",
            operator:">",
            cutOff:"3"
        },
        {
            deviceSn:"",
            deviceModelIdentifier:"",
            operator:"||"
        },
        {
            deviceSn:"w",
            deviceModelIdentifier:"t4",
            operator:"<=",
            cutOff:"4"
        },
        {
            deviceSn:"",
            deviceModelIdentifier:"",
            operator:"||"
        },
        {
            deviceSn:"ff",
            deviceModelIdentifier:"t5",
            operator:"==",
            cutOff:"'Xiao马2'"
        },
    ],
    actionList:[
        {
            deviceSn:"sadasdf",
            deviceModelIdentifier:"let",
            cmdData:"1",
            delayTime:"0",
        },
        {
            deviceSn:"",
            deviceModelIdentifier:"",
            cmdData:"",
            delayTime:"",
            separator:"&"
        },
        {
            deviceSn:"saasghgdf",
            deviceModelIdentifier:"let2",
            cmdData:"1",
            delayTime:"10",
        },
    ],
});
// 获取策略信息
const getStrategyInfo = (async ()=>{
    let responseStrategyInfo = await  deviceStrategyService.getInfoById(pathStrategyID.value);
    console.log("strategy---------",responseStrategyInfo);
});
// 关系运算符
const relationalOperatorList = ["<=",">=",">","<","==","!="];
// 逻辑运算符
const logicalOperatorList = ["&&","||"];
// /\{(.*?)\}/g                     提取{}中的值
// /([^_]+)/g                         提取sn_identifier的值 0是sn 1是identifier
// /[<= >= > < == !=]+/g            提取关系运算符的正则表达式
// /[&& ||]+/g                      提取逻辑运算符的正则表达式
// /[^ <= >= >< == != && ||]+ /g    提取除了关系、逻辑表达式的值
// 将触发策略规则的表达式字符串 转换为 对象列表 
const triggerStrToObjList = ((triggerStr)=>{
    // {sdaf_tag1}>10&&{asfsd_tag2}<10
    console.log("triggerStrToObjList_处理前-->",triggerStr);

    // 1、通过正则表达式提取{}中的值
    // 保存sn码和标识符的列表
    let sn_identifier_list = triggerStr.match(/\{(.*?)\}/g);

    // 2、创建一个临时变量用于保存删除{}和{}中的值

    // 保存 删除sn_Identifier后的策略表达式
    let delSI_triggerStr = "";
    var reg1 = new RegExp('{','g');
    var reg2 = new RegExp('}','g');
    triggerStr = triggerStr.replace(reg1,"");
    triggerStr = triggerStr.replace(reg2,"");
    delSI_triggerStr = triggerStr;
    for(let i = 0 ; i<sn_identifier_list.length;i++){
        // 取出 策略表达式中的sn_Identifier 后 删除策略表达式中的{}
        sn_identifier_list[i] = sn_identifier_list[i].replace(reg1,"");
        sn_identifier_list[i] = sn_identifier_list[i].replace(reg2,"");
        // 然后将策略表达式中的sn_Identifier 删除
        delSI_triggerStr = delSI_triggerStr.replace(sn_identifier_list[i],"");
    }
    console.log("strategy_examine-triggerObjListToStr 去除{}、sn_Identifier后的策略表达式的值->",sn_identifier_list,delSI_triggerStr);

    // 提取策略表达式 中的 关系运算符
    let relationalOperatorList = delSI_triggerStr.match(/[<= >= > < == !=]+/g);
    // 提取策略表达式 中的 判断值cutOff
    let cutOffList = delSI_triggerStr.match(/[^ <= >= >< == != && ||]+/g);
    console.log("策略表达式中的关系运算符",relationalOperatorList,"以及判断的值",cutOffList);
    // 提取策略表达式 中的 逻辑运算符 
    let logicalOperatorList = delSI_triggerStr.match(/[&& ||]+/g );
    console.log("策略表达式中的逻辑运算符",logicalOperatorList);

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
            cutOff:""
        };
        let tempTrigger2 = {
            deviceSn:"",
            deviceModelIdentifier:"",
            operator:"",
            cutOff:""
        };
        
        let sn_or_identifier = sn_identifier_list[i].match(/([^_]+)/g);
        tempTrigger1.deviceSn = sn_or_identifier[0];
        tempTrigger1.deviceModelIdentifier = sn_or_identifier[1];
        tempTrigger1.operator = relationalOperatorList[i];
        tempTrigger1.cutOff = cutOffList[i];
        // 关系表达式必定是会比逻辑关系符要少一个的 要加判断
        if(i<logicalOperatorList.length){
            tempTrigger2.operator = logicalOperatorList[i];
        }

        tempParseStrategy.triggerList.push(tempTrigger1);
        if(i<logicalOperatorList.length){
            tempParseStrategy.triggerList.push(tempTrigger2);
        }

        console.log(tempTrigger1,tempTrigger2);
    }
    console.log(tempParseStrategy);

});

// 将对象列表 转换为 触发策略规则的表达式字符串
const triggerObjListToStr = (()=>{
    let triggerStr = "";
    // 遍历列表中的策略表达式
    parseStrategy.value.triggerList.forEach((triggerItem)=>{
        if(triggerItem.deviceModelIdentifier.length<=0){
            triggerStr += triggerItem.operator;
        }
        else{
            // temp += "{"+triggerItem.deviceSn+"_"+triggerItem.deviceModelIdentifier+"}"+triggerItem.operator+triggerItem.cutOff;
            triggerStr += `{${triggerItem.deviceSn}_${triggerItem.deviceModelIdentifier}}${triggerItem.operator}${triggerItem.cutOff}`;
        }
        // console.log("strategy_examine-triggerObjListToStr->",triggerItem);
    });
    console.log("strategy_examine-triggerObjListToStr-triggerStr->",triggerStr);
    return triggerStr;
});
onActivated(()=>{

    getStrategyInfo();
    console.log("strategy_examine-onActivated->",triggerObjListToStr());
    
    triggerStrToObjList(triggerObjListToStr());
});
// onDeactivated
</script>