package cn.ruishan.iac.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Devs {

    private int id;
    private String type;
    private Map<String, Object> data;

}
