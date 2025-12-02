
package com.enjoyiot.eiot.component.core.model.down;


import com.enjoyiot.eiot.component.core.model.AbstractAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DeviceTopoChange<T> extends AbstractAction {

    /**
     * 属性参数
     */
    private T params;


}
