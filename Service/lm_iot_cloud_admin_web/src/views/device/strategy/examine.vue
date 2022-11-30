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
                    <span>{{strategyInfoAndParsingList.name}}</span>
                </div>

                <!-- 头部上边 右边 编辑按钮 -->
                <div class="lm_main_header_up_right_box">
                    <el-button @click="upadteOpenEvetn" type="info">编辑</el-button>
                </div>
            </div>
            <!-- 头部下边 策略描述  -->
            <div class="lm_mian_header_down_box">
                <span>描述：</span>
                <span>{{strategyInfoAndParsingList.describe}}</span>
            </div>
        </div>
        <!-- 展示 策略表达式、命令表达式 -->
        <div class="lm_body_box">
            <!-- 策略规则表达式表单 动态 -->
            <div class="lm_body_trigger_form">
                <div style="font-weight: 800; margin-top: 10px;">触发条件</div>
                <template v-for="(tItem,index) in strategyInfoAndParsingList.triggerList">
                    <!-- 包裹一个条件表达式 -->
                    <div class="ml-2 ">
                        <!--设备sn-设备标识符-关系标识符-判断值 -->
                        <template v-if="tItem.isLogicalOperator == false">
                            <div class="mt-2 mb-1">触发条件{{((index+1)/2)+0.5}}</div>
                            <el-select v-model="tItem.deviceSn" placeholder="请选择设备" @change="((val)=>{selectDeviceSnEvent(val, index, 1)})">
                                <el-option
                                    v-for="item in selectDeviceSnNameList"
                                    :key="item.sn"
                                    :label="item.name"
                                    :value="item.sn"
                                />
                            </el-select>
                            <el-select v-model="tItem.identifier" placeholder="请选择设备标识符" @change="((val)=>{selectDeviceIdentifierEvent(val, index, 1)})">
                                <el-option
                                    v-for="item in tItem.deviceModelList"
                                    :key="item.identifier"
                                    :label="item.name"
                                    :value="item.identifier"
                                />
                            </el-select>
                            <el-select v-model="tItem.operator" placeholder="请选择关系运算符">
                                <el-option
                                    v-for="item in relationalOperatorList"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value"
                                />
                            </el-select>
                            <!-- 规则判断值 -->
                            <el-input v-model="tItem.cutOff"></el-input>
                            <el-button class="ml-2" type="info" @click="addTriggerItemEvent(index)">添加</el-button>
                            <el-button class="ml-2" type="info" @click="delTriggerItemEvent(index)">删除</el-button>
                        </template>
                        <!-- 逻辑运算符 -->
                        <template v-if="tItem.isLogicalOperator == true">
                            <div class="mt-2">
                                <el-select v-model="tItem.operator" placeholder="请选择逻辑运算符">
                                    <el-option
                                        v-for="item in logicalOperatorList"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value"
                                    />
                                </el-select>
                            </div>
                        </template>
                    </div>
                </template>
                <template v-if="strategyInfoAndParsingList.triggerList.length<=0">
                    <el-button class="ml-2" type="info" @click="addTriggerItemEvent(0)">添加触发条件</el-button>
                </template>
            </div>
            
            <!-- 策略执行动作表达式表单 动态 -->
            <div class="lm_body_action_form">
                <div style="font-weight: 800; margin-top: 10px;">执行动作</div>
                <template v-for="(aItem,index) in strategyInfoAndParsingList.actionList">
                    <div class="ml-2">
                        <div class="mt-2 mb-1">触发条件{{index+1}}</div>
                        <el-select v-model="aItem.deviceSn" placeholder="请选择设备" @change="((val)=>{selectDeviceSnEvent(val, index,2)})">
                            <el-option
                                v-for="item in selectDeviceSnNameList"
                                :key="item.sn"
                                :label="item.name"
                                :value="item.sn"
                            />
                        </el-select>
                        <el-select v-model="aItem.identifier" placeholder="请选择设备标识符" @change="((val)=>{selectDeviceIdentifierEvent(val, index,2)})">
                            <el-option
                                v-for="item in aItem.deviceModelList"
                                :key="item.identifier"
                                :label="item.name"
                                :value="item.identifier"
                            />
                        </el-select>
                        <span>发送</span>
                        <el-input v-model="aItem.cmdData" class="m-2" placeholder="请输入值"></el-input>
                        <span>到设备</span>
                        <el-button class="ml-2" type="info" @click="addActionItemEvent(index)">添加</el-button>
                        <el-button class="ml-2" type="info" @click="delActionItemEvent(index)">删除</el-button>
                        <div class="ml-2 flex items-center">
                            <span>延迟</span>
                            <el-input-number class="ml-2 mr-2" v-model="aItem.delayTime" :min="0" :max="3600" />
                            <span>秒</span>
                        </div>
                    </div>
                </template>
                <template v-if="strategyInfoAndParsingList.actionList.length<=0">
                        <el-button class="ml-2" type="info" @click="addActionItemEvent(0)">添加执行动作</el-button>
                </template>
            </div>
              
            <div class="m-2">
                <el-button @click="updataStrategyinfoEvent" type="info" >保存场景联动</el-button>
            </div>
        </div>

    </div>
    <lm-device-strteagy-info-dialog :is-upadate="true" ref="strteagyDialogRef"></lm-device-strteagy-info-dialog>
</template>
<script setup>
import LmDeviceStrteagyInfoDialog from '@/components/device/devicestrtegy/LmDeviceStrteagyInfoDialog.vue';
import deviceService from '@/services/device/DeviceService';
import deviceModelService from '@/services/devicemodel/DeviceModelService';
// 获取设备策略
import deviceStrategyService from '@/services/devicestrategy/DeviceStrategyService';
import { LmMessageError, LmMessageSuccess } from '@/utils';
import { computed } from '@vue/reactivity';
import { onActivated, ref } from 'vue';
import { useRouter } from 'vue-router';
// 用于获取当前路由的状态和地址
import { useRoute } from 'vue-router';

// 用于路由对象 对路由进行操作
const router = useRouter();
// 用于获取当前路由的状态和地址
const route = useRoute();

// 路径的策略id（不用用这个id去更新策略）
const pathStrategyID = computed(()=>{return route.query.id});
// 策略列表信息和解析后的列表 
const strategyInfoAndParsingList = ref({
    id: 0,
    name: "",
    status: 0,
    describe: "",
    actionStr: "",
    triggerStr: "",
    triggerList:[],     // 规则表达式列表
    actionList:[],      // 触发规则命令列表
    deviceModelList:[], // 当前设备物模型列表
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
// 下拉框的设备数据
const selectDeviceSnNameList  = ref([]);
const strteagyDialogRef  = ref(null);
const upadteOpenEvetn = (()=>{
    strteagyDialogRef.value.open(strategyInfoAndParsingList.value);
});
// 获取设备sn和设备名称
const getDeviceSnNameData = ( async()=>{
    let temp =  await deviceService.idsnname();
    temp =  temp.data;
    return temp;
});

// 获取数据库中的策略信息  传入策略id
const getDeviceStrategy = (async (Sid)=>{
    let serverResponseStrategy = await deviceStrategyService.getInfoById(Sid);
    if(serverResponseStrategy.data == null){
        LmMessageError("策略不存在！");
        return;
    }
    console.log("获取数据库中的策略信息-->getDeviceStrategy--->>",serverResponseStrategy.data);
    return serverResponseStrategy.data;
});

// 获取设备sn的物模型数据并解析  设备sn 和 当前设备标识符 和 当前设备的物模型列表
const getDeviceModelAndParsing = (async (Dsn,thisIdentifier,thisModelList)=>{
    let temp = {
        thisModelType:"",
        deviceModelList:{}
    };
    let responseData = null;
    // 如果传入的物模型列表为空 或者 列表没值 
    if(thisModelList==undefined || thisModelList.length<=0){
        // 就去服务器获取
        responseData = await deviceModelService.getDeviceModelSelectData({sn:Dsn});
        responseData = responseData.data;
    }
    else{
        // 如果有就直接用
        responseData = thisModelList;
    }
   
    if(responseData == null){
        return temp;
    }
    // 将设备拥有的物模型数据放进去 
    temp.deviceModelList = responseData;
    // 遍历当前设备的物模型数据
    for (let i = 0; i < responseData.length; i++) {
        // 匹配物模型标识符 提取当前标识符的数据类型
        if(responseData[i].identifier == thisIdentifier){
            // 放入当前匹配到的标识符 数据类型
            temp.thisModelType = responseData[i].dataType;
        }
        // 如果是bool类型的物模型
        if(responseData[i].dataType == "bool"){
            // 将dataSpecs转为jsonObj
            if(typeof responseData[i].dataSpecs == "string"){
                responseData[i].dataSpecs = JSON.parse(responseData[i].dataSpecs);
            }
        }
    }
    console.log("获取到",Dsn,"设备物模型的数据--->",responseData,"--解析后-->",temp);        
    return temp;
});

// 选择设备sn下拉框数据变动的时候
const selectDeviceSnEvent = (async (val,index,is)=>{
    console.log("设备sn下拉框数据变动selectDeviceSnEvent--->",val,"---当前设备的index->",index);
    let thisSelectDevice = null;
    if(is == 1){
        thisSelectDevice =  strategyInfoAndParsingList.value.triggerList[index];
    }
    else if(is == 2){
        thisSelectDevice =  strategyInfoAndParsingList.value.actionList[index];
    }
    // 把设备sn码放进去 返回deviceModelList 
    let tmp  =  await getDeviceModelAndParsing(val,"",[]);
    // 当前的设备标识符设置为空
    thisSelectDevice.identifier = "";
    // 根据设备sn查询到的物模型数据 存入
    thisSelectDevice.deviceModelList = tmp.deviceModelList;
    // 当前的关系运算符设置为空
    thisSelectDevice.operator = "";
    // 当前的 关系判断值 设置为空
    thisSelectDevice.cutOff = "";
    // 当前的 设备物模型数据类型 设置为空
    thisSelectDevice.modelType = "";
});

// 选择设备标识符下拉框数据变动的时候
const selectDeviceIdentifierEvent = ((val,index,is)=>{
    console.log("设备标识符下拉框数据变动selectDeviceSnEvent--->",val,"---当前设备的index->",index);
    let thisSelectDevice = null;
    if(is == 1){
        thisSelectDevice =  strategyInfoAndParsingList.value.triggerList[index];
    }
    else if(is == 2){
        thisSelectDevice =  strategyInfoAndParsingList.value.actionList[index];
    }
    // 当前的 关系判断值 设置为空
    thisSelectDevice.cutOff = "";
    // 循环遍历设备拥有的物模型数据
    for (let i = 0; i < thisSelectDevice.deviceModelList.length; i++) {
        // 匹配 当前标识符 获取 当前标识符的物模型数据类型
        if(val == thisSelectDevice.deviceModelList[i].identifier){
            thisSelectDevice.modelType = thisSelectDevice.deviceModelList[i].dataType;
        }
    }

});

// 添加一条规则表达式对象
const addTriggerItemEvent = ((triggerIndex)=>{
    // 如果是规则表达式列表没有的时候 就单独添加一条关系表达式就好了
    if(strategyInfoAndParsingList.value.triggerList==0){
        // 先加一条关系表达式
        strategyInfoAndParsingList.value.triggerList.splice(triggerIndex+2,0,{deviceSn:"",identifier:"",operator:"",cutOff:"",isLogicalOperator:false,});
        return;
    }
    // 如果添加的关系表达式下面没有逻辑表达式的时候 - 在关系表达式的最后一条的时候
    if(strategyInfoAndParsingList.value.triggerList[triggerIndex+1] == undefined){
        // 先加一条逻辑表达式
        strategyInfoAndParsingList.value.triggerList.splice(triggerIndex+2,0,{deviceSn:"",identifier:"",operator:"",cutOff:"",isLogicalOperator:true,});
        // 再加一条关系表达式
        strategyInfoAndParsingList.value.triggerList.splice(triggerIndex+3,0,{deviceSn:"",identifier:"",operator:"",cutOff:"",isLogicalOperator:false,});
        console.log("添加 逻辑表达式 关系表达式 ");
    }
    // 如果添加的关系表达式下面有逻辑表达式的时候  如果这一条是逻辑表达式的时候 
    else if(strategyInfoAndParsingList.value.triggerList[triggerIndex+1].isLogicalOperator == true){
        // 那就先加一条关系表达式
        strategyInfoAndParsingList.value.triggerList.splice(triggerIndex+2,0,{deviceSn:"",identifier:"",operator:"",cutOff:"",isLogicalOperator:false,});
        // 再加一条逻辑表达式
        strategyInfoAndParsingList.value.triggerList.splice(triggerIndex+3,0,{deviceSn:"",identifier:"",operator:"",cutOff:"",isLogicalOperator:true,});
        console.log("添加 关系表达式 逻辑表达式 ");
    }

    // console.log(strategyInfoAndParsingList.value.triggerList);
});

// 删除一条规则表达式对象
const delTriggerItemEvent = ((triggerIndex)=>{
    // 如果是删除最后一条的时候 
    if(strategyInfoAndParsingList.value.triggerList[triggerIndex+1] == undefined ){
        // 删除自己  关系表达式
        strategyInfoAndParsingList.value.triggerList.splice(triggerIndex,1);
        // 再删除上一条 逻辑表达式
        strategyInfoAndParsingList.value.triggerList.splice(triggerIndex-1,1);
        console.log("删除 当前 关系表达式 上一条逻辑表达式");
    }
    // 如果存在逻辑表达式
    else if(strategyInfoAndParsingList.value.triggerList[triggerIndex+1].isLogicalOperator==true){
        // 删除自己和下一条  就是关系表达式和逻辑表达式
        strategyInfoAndParsingList.value.triggerList.splice(triggerIndex,2);
        console.log("删除 自己和下一条 就是关系表达式和逻辑表达式");
    }
});

// 添加一条执行动作
const addActionItemEvent = ((actionIndex)=>{
    strategyInfoAndParsingList.value.actionList.splice(actionIndex+1,0,{deviceSn: '', deviceModelList: [], cmdData: '', delayTime: 0});
    console.log("---->",actionIndex);
});

// 删除一条执行动作
const delActionItemEvent = ((actionIndex)=>{
    strategyInfoAndParsingList.value.actionList.splice(actionIndex,1);
});

// 更新保存策略信息 到数据库
const updataStrategyinfoEvent = ( async()=>{
    // 规则表达式列表 转 规则表达式字符串
    strategyInfoAndParsingList.value.triggerStr = triggerObjListToStr(strategyInfoAndParsingList.value.triggerList);
    // 触发规则命令列表 转 触发规则命令字符串
    strategyInfoAndParsingList.value.actionStr = actionObjListToStr(strategyInfoAndParsingList.value.actionList);
    let {...strategyInfoCopy} = strategyInfoAndParsingList.value;
    if(strategyInfoCopy.actionStr.length > 0 && strategyInfoCopy.triggerStr.length > 0){
        try {
            let temp = await deviceStrategyService.update(strategyInfoCopy);
            console.log("更新保存策略信息-到数据库--->updataStrategyinfoEvent--->",strategyInfoCopy,temp);
            LmMessageSuccess("更新保存策略信息成功！");
        } catch (error) {
            console.log("device-strteagy-error--->",error);
        }
   
    }
    else{
        LmMessageError("更新保存策略信息失败！请检查是否存在空！");
    }
});

onActivated(async ()=>{
    selectDeviceSnNameList.value = await getDeviceSnNameData();
    console.log("页面加载完毕后onActivated--获取设备sn和设备名称用于select->",selectDeviceSnNameList.value);

    strategyInfoAndParsingList.value = await getDeviceStrategy(pathStrategyID.value)
    console.log("页面加载完毕后onActivated--策略信息->",strategyInfoAndParsingList.value);


    // 将从服务器获取到的 策略规则表达式字符串 解析成为 对象列表
    strategyInfoAndParsingList.value.triggerList = triggerStrToObjList(strategyInfoAndParsingList.value.triggerStr);
    // 遍历策略规则列表  获取设备拥有的物模型列表 和当前选中的标识符数据类型
    strategyInfoAndParsingList.value.triggerList.forEach(async(item)=>{
        if(item.isLogicalOperator == false){
            // 通过设备的sn码 和 当前选中的标识符 获取 设备的所选物模型数据 和当前选中标识符数据的数据类型
            let temp = await getDeviceModelAndParsing(item.deviceSn,item.identifier,item.deviceModelList);
            // 解析后的当前标识符的物模型数据类型
            item.modelType = temp.thisModelType;
            // 物模型数据列表
            item.deviceModelList = temp.deviceModelList;
        }
    });
    console.log("页面加载完毕后onActivated--解析完成策略规则表达式->",strategyInfoAndParsingList.value);

    // 将从服务器获取到的 执行命令表达式字符串 解析成为 对象列表
    strategyInfoAndParsingList.value.actionList = actionStrToObjList(strategyInfoAndParsingList.value.actionStr);
    // 遍历执行命令列表  获取设备拥有的物模型列表 和当前选中的标识符数据类型
    strategyInfoAndParsingList.value.actionList.forEach(async(item)=>{
        // 通过设备的sn码 和 当前选中的标识符 获取 设备的所选物模型数据 和当前选中标识符数据的数据类型
        let temp = await getDeviceModelAndParsing(item.deviceSn,item.identifier,item.deviceModelList);
        // 解析后的当前标识符的物模型数据类型
        item.ModelType = temp.thisModelType;
        // 物模型数据列表
        item.deviceModelList = temp.deviceModelList;
    });
    console.log("页面加载完毕后onActivated--解析完成执行命令表达式->",strategyInfoAndParsingList.value);

});


// 返回上一页
const looktgEvent = (()=>{
    router.go(-1)
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

    // 保存 删除sn_identifier后的策略表达式
    let delSI_triggerStr = "";
    var reg1 = new RegExp('{','g');
    var reg2 = new RegExp('}','g');
    var reg3 = new RegExp("'",'g');
    triggerStr = triggerStr.replace(reg1,"");
    triggerStr = triggerStr.replace(reg2,"");
    triggerStr = triggerStr.replace(reg3,"");
    delSI_triggerStr = triggerStr;
    for(let i = 0 ; i<sn_identifier_list.length;i++){
        // 取出 策略表达式中的sn_identifier 后 删除策略表达式中的{}
        sn_identifier_list[i] = sn_identifier_list[i].replace(reg1,"");
        sn_identifier_list[i] = sn_identifier_list[i].replace(reg2,"");
        // 然后将策略表达式中的sn_identifier 删除
        delSI_triggerStr = delSI_triggerStr.replace(sn_identifier_list[i],"");
    }
    // console.log("strategy_examine-triggerObjListToStr 去除{}、sn_identifier后的策略表达式的值->",sn_identifier_list,delSI_triggerStr);

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
            identifier:"",
            operator:"",
            cutOff:"",
            isLogicalOperator:false,
        };
        let tempTrigger2 = {
            deviceSn:"",
            identifier:"",
            operator:"",
            cutOff:"",
            isLogicalOperator:true,
        };
        // 处理sn_identifier
        let sn_or_identifier = sn_identifier_list[i].match(/([^_]+)/g);
        // 将sn传入
        tempTrigger1.deviceSn = sn_or_identifier[0];
        // 将identifier传入
        tempTrigger1.identifier = sn_or_identifier[1];
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

// 将 执行规则命令 的 表达式字符串 转换 为 对象列表 
const actionStrToObjList = ((actionStr)=>{
//    /[^&]+/g    拆解 执行命令
//   /([^_]+)/g                       提取sn_identifier的值 0是sn 1是identifier
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
            identifier:"",
            cmdData:"",
            delayTime:"0",
        };
        // 将sn码和设备标识符解析出来 
        let temp_sn_identifier = temp_sn_identifier_cmdCal_delayTime[0].match(/([^_]+)/g);
        tmepAction.deviceSn = temp_sn_identifier[0];
        tmepAction.identifier = temp_sn_identifier[1];
        tmepAction.cmdData = temp_sn_identifier_cmdCal_delayTime[1].match(/([^_]+)/g)[0];
        tmepAction.delayTime = temp_sn_identifier_cmdCal_delayTime[2].match(/([^_]+)/g)[0];
        actionList.push(tmepAction);
    }
    console.log("strategy_examine-actionStrToObjList 解析规则执行命令字符串后的对象列表-->",actionList);
    return actionList;
});

// 将对象列表 转换为 触发策略规则的表达式字符串
const triggerObjListToStr = ((triggerList)=>{
    let triggerStr = "";
    let notNullCount = 0 ;
    // 遍历列表中的策略表达式 判断是否为 空
    for (let i = 0; i < triggerList.length; i++) {

        let triggerItem = triggerList[i];
        // 先校验一次再说  
        if(triggerItem.isLogicalOperator == false){
            if(triggerItem.deviceSn.length > 0 && triggerItem.identifier.length > 0 && triggerItem.operator.length > 0  ){
                if(triggerItem.cutOff.length > 0){
                    // 字符串不为空
                    notNullCount++;
                }
                else{
                }
            }
            else{
            }
        }  
        if(triggerItem.isLogicalOperator == true){
            // 不是数字 判断字符串是否为空
            if(triggerItem.operator.length > 0){
                // 字符串不为空
                notNullCount++;
            }
            else{
            }
        }
    }
    // 如果表达式不为空的计数器 和 表达式列表的长度相等 全都不为空
    if(notNullCount != triggerList.length){
        return "";
    }
    // 然后在进行二次规则表达式列表遍历
    for (let i = 0; i < triggerList.length; i++) {
        let triggerItem = triggerList[i];
        console.log("objlist to str-->",triggerItem);    
        // // console.log("objlist to str-->",triggerItem.operator.length);        
        // 关系关系符
        if(triggerItem.isLogicalOperator == false){
            // 判断是否是数字
            var tempSun = Number(triggerItem.cutOff);
            if (!isNaN(tempSun))
            {
                triggerStr += `{${triggerItem.deviceSn}_${triggerItem.identifier}}${triggerItem.operator}${triggerItem.cutOff}`;
            }
            else{
                // 是字符串就给cutOff加上''
                triggerStr += `{${triggerItem.deviceSn}_${triggerItem.identifier}}${triggerItem.operator}'${triggerItem.cutOff}'`;
            }
        }
        // 逻辑关系符
        if(triggerItem.isLogicalOperator == true){
            if(triggerItem.operator.length > 0 ){
                triggerStr += triggerItem.operator;
            }
        }
    }
    return triggerStr;
});
// 将 执行规则命令 的 对象列表 转换为 表达式字符串
const actionObjListToStr = ((actionList)=>{
    let actionStr = "";
    let notNullCount = 0 ;
    for (let i = 0; i < actionList.length; i++) {
        let temp = actionList[i];
        if(
            temp != undefined && 
            temp.deviceSn.length>0&&
            temp.identifier.length>0&&
            temp.cmdData.length>0&&
            temp.delayTime>=0
        ){
            notNullCount++;
        }
    }
    if(notNullCount != actionList.length){
        return "";
    }
     for (let i = 0; i < actionList.length; i++) {
        let temp = actionList[i];
        var tempSum = Number(temp.cmdData);
        if (!isNaN(tempSum)){
            // 是数字的时候
            actionStr+= `${temp.deviceSn}_${temp.identifier}:${temp.cmdData}:${temp.delayTime}`;
        }
        else{
            // 是字符串是时候就加上 ''
            actionStr+= `${temp.deviceSn}_${temp.identifier}:'${temp.cmdData}':${temp.delayTime}`;
        }
        // 最后一个就不加拼接符啦
        if(i != actionList.length-1){
            actionStr+='&'
        }
    }
    console.log("-----------------",actionList);
    return actionStr;
});
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
    cursor:pointer;
}
.lm_main_header_up_left_icon_box:hover{
    color: #a162f7;
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