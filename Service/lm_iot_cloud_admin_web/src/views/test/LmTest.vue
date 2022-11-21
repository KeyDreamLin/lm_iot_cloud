<template>
   <div>
    {{viewSelectList}}
    <div>22222222222222222222222222222</div>
    <template v-for="(item,index) in viewSelectList">
        <div>
            <div> {{item}}</div>
            <template v-if="item.isLogicalOperator == false">
                <el-select @change="((val)=>{selectDeviceSnEvent(val, index)})" v-model="item.deviceSn" placeholder="请选择设备">
                    <el-option
                        v-for="item in selectDeviceSnNameList"
                        :key="item.sn"
                        :label="item.name"
                        :value="item.sn"
                    />
                </el-select>
                <el-select @change="((val)=>{selectDeviceIdentifierEvent(val, index)})" v-model="item.deviceModelIdentifier" placeholder="请选择设备标识符">
                    <el-option
                        v-for="modelItem in item.databaseModelIdetifier"
                        :key="modelItem.identifier"
                        :label="modelItem.name"
                        :value="modelItem.identifier"
                    />
                </el-select>
                <el-select v-model="item.operator" placeholder="请选择关系运算符">
                    <el-option
                        v-for="item in relationalOperatorList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
            </template>
            <template v-if="item.isLogicalOperator == true">
                <el-select v-model="item.operator" placeholder="请选择逻辑运算符">
                    <el-option
                        v-for="item in logicalOperatorList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
                
            </template>
           
        </div>
    </template>
    <el-button @click="test">测试</el-button>
   </div>
</template>

<script setup>
import deviceService from '@/services/device/DeviceService';
import deviceModelService from '@/services/devicemodel/DeviceModelService';
import { onActivated, ref } from 'vue';
// 策略信息（用这个的id，这个是第一次从服务器查询出来的，不会变动）
const strategyInfo = ref({
    triggerStr:"{asfdhwuvdsuklbv_light}<40||{asdfgfdhgjmkftfzxb_wendu}>20",
    // triggerStr:"{a1_i1}>1||{b2_i2}>1&&{c2_i2}>=10||{d3_i3}<=12||{c4_i4}!='小马s2J89'",
    actionStr:"sdg345ghdgh345gweqer23_led:1:0&test_tag:1:0"
});
// 解析后的策略信息  
const parseStrategy = ref({
    triggerList:[
    ],
    actionList:[
    ],
});
// 下拉框的设备数据
const selectDeviceSnNameList  = ref([]);
const testList = ["a","b","c","d","e","f","g","h","i"];
const tes1tList = ["t1","t2","t3","t4","t5","t6","t7","t8","t9"];
// 根据解析出来的策略信息 来来创建有多少个页面
// 反正这个玩意就是代表有多少个 触发器 然后在前端展示
const viewSelectList =  ref([

]);

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
const triggerObjListToStr = ((parseStrategyList)=>{
    let triggerStr = "";
    // 遍历列表中的策略表达式
    for (let i = 0; i < parseStrategyList.length; i++) {
        let triggerItem = parseStrategyList[i];
        console.log("objlist to str-->",parseStrategyList[i]);  
        // console.log("objlist to str-->",triggerItem.operator.length);        
        // 关系关系符
        if(triggerItem.isLogicalOperator == false){
            // 判空
            if(
                triggerItem.deviceSn.length>0 && 
                triggerItem.deviceModelIdentifier.length>0 && 
                triggerItem.operator.length>0 
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
const test = (()=>{
    console.log(triggerObjListToStr(viewSelectList.value));
});
// 获取设备sn和设备名称
const getDeviceSnNameData = ( async()=>{
    selectDeviceSnNameList.value = await deviceService.snname();
    selectDeviceSnNameList.value =  selectDeviceSnNameList.value.data;
    console.log("----------------------",selectDeviceSnNameList.value);
});

onActivated( async()=>{
    await getDeviceSnNameData();
    // 模拟服务器回调的数据 规则表达式
    parseStrategy.value.triggerList = triggerStrToObjList(strategyInfo.value.triggerStr);
    // 明天用这个 别用上面的了
    viewSelectList.value.triggerList = triggerStrToObjList(strategyInfo.value.triggerStr);
    let temp = parseStrategy.value.triggerList;

    for (let i = 0; i < temp.length; i++) {
        if(temp[i].isLogicalOperator == false){
            // 查询物模型数据 根据sn
            let modelData = await deviceModelService.getDeviceModelSelectData({sn:temp[i].deviceSn});
            modelData = modelData.data;
            console.log("---",modelData);
            // 当前标识符的物模型数据类型
            let thisModelType = "";
            // 循环服务器传过来的物模型数据
            for (let j = 0; j < modelData.length; j++) {
                // 匹配规则表达式中的表达式和物模型的标识符
                if(temp[i].deviceModelIdentifier == modelData[j].identifier){
                    // 获取当前标识符的数据类型
                    thisModelType = modelData[j].dataType;
                }
            }
            let data = {
                deviceSn:temp[i].deviceSn,
                deviceModelIdentifier:temp[i].deviceModelIdentifier,
                operator:temp[i].operator,
                cutOff:temp[i].cutOff,
                databaseModelType:thisModelType,        // 当前标识符的物模型数据类型
                databaseModelIdetifier:modelData,       // 所有物模型的标识符  这里根据设备sn查询一次物模型数据
                isLogicalOperator:false
            };
            viewSelectList.value.push(data);
            console.log("---------解析并插入物模型数据-关系表达式---->",data);
        }
        else if(temp[i].isLogicalOperator == true){
            let data = {
                deviceSn:"",
                deviceModelIdentifier:"",
                operator:temp[i].operator,
                cutOff:"",
                databaseModelType:"",        // 当前标识符的物模型数据类型
                databaseModelIdetifier:"",       // 所有物模型的标识符  这里根据设备sn查询一次物模型数据
                isLogicalOperator:true
            };

            viewSelectList.value.push(data);
            console.log("---------解析后的数据逻辑表达式---->",data);
        }
    }
    
});
// 选择设备sn下拉框数据变动的时候
const selectDeviceSnEvent = (async (val,index)=>{
    console.log("设备sn下拉框数据变动selectDeviceSnEvent--->",val,"---index->",index);

     // 获取 更新索引 获取 设备 表达式信息
     let viewDeviceData = viewSelectList.value[index];

    // 查询物模型数据 根据sn
    let modelData = await deviceModelService.getDeviceModelSelectData({sn:val});
    modelData = modelData.data;

    // 修改设备sn码
    viewDeviceData.deviceSn = val;
    // 标识符设置为空
    viewDeviceData.deviceModelIdentifier = "";
    // 运算符设置为空
    viewDeviceData.operator = "";
    // 判断值设置为空
    viewDeviceData.cutOff = "";
    // 物模型类型设置为空
    viewDeviceData.databaseModelType = "";
    // 设置设备拥有的物模型标识符数据
    viewDeviceData.databaseModelIdetifier = modelData;

    console.log("selectDeviceSnEvent--->",viewDeviceData);
});

// 选择设备标识符下拉框数据变动的时候
const selectDeviceIdentifierEvent = ((val,index)=>{
    console.log("设备标识符下拉框数据变动selectDeviceSnEvent--->",val,"---index->",index);
    // 获取 更新索引 获取 设备 表达式信息
    let viewDeviceData = viewSelectList.value[index];
    // 遍历当前设备的物模型列表
    for (let i = 0; i < viewDeviceData.databaseModelIdetifier.length; i++) {
        // 匹配当前设备标识符下拉框的数据 和 设备拥有的物模型 
        if(viewDeviceData.databaseModelIdetifier[i].identifier == val){
            // 放入设备物模型的类型
            viewDeviceData.databaseModelType = viewDeviceData.databaseModelIdetifier[i].dataType;
        }
    }
    // 判断值设置为空  让用户重新选择
    if(viewDeviceData.databaseModelType != "text"){
        viewDeviceData.cutOff = 0;
    }
    else{
        viewDeviceData.cutOff = "";
    }

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
</script>