package com.lm.admin.controller.device;

import com.lm.admin.controller.BaseController;
import com.lm.admin.entity.bo.device.DeviceDataBo;
import com.lm.admin.entity.pojo.devicemodel.DeviceModelData;
import com.lm.admin.mapper.mysql.DeviceModelDataMapper;
import com.lm.admin.mapper.tdengine.DeviceDataMapper;
import com.lm.admin.service.device.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 设备信息、数据 web Api
 * @author Lm
 * @since 2022-09-23
 */
@Controller
@Slf4j
public class DeviceController extends BaseController {

}
