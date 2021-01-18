/**
 * @filename:CarouselController 2019年10月16日
 * @project   
 * Copyright(c) 2020 欧阳小广 Co. Ltd. 
 * All right reserved. 
 */
package com.lucky.core;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**   
 * @Description:TODO(轮播图API接口层)
 * 
 * @version: 
 * @author:  欧阳小广
 * @time     2019年10月16日
 */
public class AbstractController<S extends IService<T>,T>{
	
	@Autowired
    protected S baseService;

	protected JsonResult<T> result = new JsonResult<T>();
	/**
	 * @explain 查询对象  <swagger GET请求>
	 * @param   对象参数：id
	 * @return  JsonResult
	 * @author  欧阳小广
	 * @time    2019年10月16日
	 */
	@GetMapping("/getById/{id}")
	@ApiOperation(value = "获取对象", notes = "作者：欧阳小广")
	@ApiImplicitParam(paramType="path", name = "id", value = "对象id", required = true, dataType = "Long")
	public JsonResult<T> getById(@PathVariable("id")Long id){
		T obj=baseService.getById(id);
		if (null!=obj ) {
			 result.success(obj);
		}else {
			 result.error("查询对象不存在！");
		}
		return result;
	}
	
	/**
	 * @explain 删除对象
	 * @param   对象参数：id
	 * @return  JsonResult
	 * @author  欧阳小广
	 * @time    2019年10月16日
	 */
	@PostMapping("/deleteById")
	@ApiOperation(value = "删除", notes = "作者：欧阳小广")
	@ApiImplicitParam(paramType="query", name = "id", value = "对象id", required = true, dataType = "Long")
	public JsonResult<T> deleteById(Long id){
		JsonResult<T> result=new JsonResult<T>();
		T obj=baseService.getById(id);
		if (null!=obj) {
		  boolean rsg = baseService.removeById(id);
		  if (rsg) {
			    result.success("删除成功");
		  }else {
			   result.error("删除失败！");
		  }
		}else {
			 result.error("删除的对象不存在！");
		}
		return result;
	}
	
	/**
	 * @explain 添加
	 * @param   对象参数：T
	 * @return  Boolean
	 * @author  欧阳小广
	 * @time    2019年10月16日
	 */
	@PostMapping("/insert")
	@ApiOperation(value = "添加", notes = "作者：欧阳小广")
	public JsonResult<T> insert(T entity){
		JsonResult<T> result=new JsonResult<T>();
		if (null!=entity) {
			boolean rsg = baseService.save(entity);
			if (rsg) {
				  result.success("添加成功");
			  }else {
				  result.error("添加失败！");
			  }
		}else {
			result.error("请传入正确参数！");
		}
		return result;
	}
	
	/**
	 * @explain 修改
	 * @param   对象参数：T
	 * @return  Boolean
	 * @author  欧阳小广
	 * @time    2019年10月16日
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "作者：欧阳小广")
	public JsonResult<T> update(T entity){
		JsonResult<T> result=new JsonResult<T>();
		if (null!=entity) {
			boolean rsg = baseService.updateById(entity);
			if (rsg) {
				  result.success("修改成功");
			  }else {
				  result.error("修改失败！");
			  }
		}else {
			result.error("请传入正确参数！");
		}
		return result;
	}
	
	/**
	 * @explain 分页条件查询用户   
	 * @param   对象参数：AppPage<Carousel>
	 * @return  PageInfo<Carousel>
	 * @author  欧阳小广
	 * @time    2019年10月16日
	 */
	@GetMapping("/getPage")
	@ApiOperation(value = "分页查询", notes = "分页查询返回[IPage<T>],作者：欧阳小广")
	public JsonResult<IPage<T>> getPages(PageParam<T> param){
		JsonResult<IPage<T>> returnPage=new JsonResult<IPage<T>>();
		Page<T> page=new Page<T>(param.getPageNum(),param.getPageSize());
		QueryWrapper<T> queryWrapper =new QueryWrapper<T>();
		queryWrapper.setEntity(param.getParam());
		//分页数据
		IPage<T> pageData=baseService.page(page, queryWrapper);
		returnPage.success(pageData);
		
		return returnPage;
	}
}
