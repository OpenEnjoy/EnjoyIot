

package com.enjoyiot.module.eiot.api.device.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 设备拓扑图变化
 *
 * @author sjg
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DeviceTopoChangeDTO {


      private String oldParentId;

    //0-创建  1-删除 2-恢复禁用  8-禁用
       private Integer status ;

       private List<DeviceInfo> subList;



    @Data
    public static class DeviceInfo{
        private String dn;
        private String pk;

    }
}
