/**
 * @filename:ItemsCommentsDao 2019年10月16日
 * @project   
 * Copyright(c) 2020 欧阳小广 Co. Ltd. 
 * All right reserved. 
 */
package com.lucky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lucky.vo.MyCommentVO;
import com.lucky.vo.SearchItemsVo;
import org.apache.ibatis.annotations.Mapper;
import com.lucky.pojo.ItemsComments;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**   
 * @Description:TODO(商品表数据访问层)
 *
 * @version: 
 * @author: 欧阳小广
 * 
 */
@Mapper
public interface ItemsCommentsMapper extends BaseMapper<ItemsComments> {

    void batchSaveComments(@Param("map") Map<String, Object> map);

    List<MyCommentVO> queryMyComments(String userId);

}
