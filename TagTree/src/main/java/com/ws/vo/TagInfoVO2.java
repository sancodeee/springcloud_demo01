package com.ws.vo;

import com.ws.pojo.TagInfo;
import lombok.Data;

import java.util.List;

@Data
public class TagInfoVO2 extends TagInfo {

    List<TagInfo> childNodes;

}
