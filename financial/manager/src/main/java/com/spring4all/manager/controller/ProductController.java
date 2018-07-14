package com.spring4all.manager.controller;

import com.spring4all.entity.TpProduct;
import com.spring4all.manager.service.TpProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @program: financial
 * @description: 产品
 * @author: qiankeqin
 * @create: 2018-07-14 10:35
 **/
@RestController
@RequestMapping("/product")
public class ProductController {
    private static Logger LOG = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private TpProductService productService;

    @RequestMapping(value="",method = RequestMethod.POST)
    public TpProduct addProduct(@RequestBody TpProduct product){
        LOG.info("创建产品,参数:{}",product);

        TpProduct result = productService.addProduct(product);

        LOG.info("创建产品,结果:{}",result);
        return result;
    }

    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public TpProduct findOne(@PathVariable String id){
        LOG.info("查询单个产品，id={}",id);
        TpProduct product = productService.findOne(id);
        LOG.info("查询单个产品，result={}",product);
        return product;
    }

    @RequestMapping(value="",method = RequestMethod.GET)
    public Page<TpProduct> query(String ids,BigDecimal minRewardRate,BigDecimal maxRewardRate,String status,
                                 @RequestParam(defaultValue = "0") int pageNum,@RequestParam(defaultValue = "10") int pageSize){
        LOG.info("查询产品，ids={},minRewardRate={},maxRewardRate={},status={},pageNum={},pageSize={}",ids,minRewardRate,maxRewardRate,
                status,pageNum,pageSize);
        List<String> idList = null;
        List<String> statusList = null;
        if(!StringUtils.isEmpty(ids)){
            idList = Arrays.asList(ids.split(","));
        }
        if(!StringUtils.isEmpty(status)){
            statusList = Arrays.asList(status.split(","));
        }
        Pageable pageable = new PageRequest(pageNum,pageSize);
        Page<TpProduct> page = productService.query(idList, minRewardRate, maxRewardRate, statusList, pageable);
        LOG.info("查询产品，结果={}",page);
        return page;

    }
}
