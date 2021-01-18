/**
 * @filename:ItemsParamDao 2019年10月16日
 * @project   
 * Copyright(c) 2020 欧阳小广 Co. Ltd. 
 * All right reserved. 
 */
package com.lucky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.lucky.pojo.ItemsParam;

/**   
 * @Description:TODO(商品参数数据访问层)
 *
 * @version: 
 * @author: 欧阳小广
 * 
 */
@Mapper
public interface ItemsParamDao extends BaseMapper<ItemsParam> {
	
}
