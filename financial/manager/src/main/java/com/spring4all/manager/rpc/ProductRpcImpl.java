package com.spring4all.manager.rpc;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import com.spring4all.api.ProductRpc;
import com.spring4all.api.domain.ProductRpcReq;
import com.spring4all.entity.TpProduct;
import com.spring4all.manager.service.TpProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * @program: financial
 * @description:
 * @author: qiankeqin
 * @create: 2018-07-28 10:52
 **/
@AutoJsonRpcServiceImpl
@Service
public class ProductRpcImpl implements ProductRpc {
    private static Logger LOG = LoggerFactory.getLogger(ProductRpcImpl.class);

    @Autowired
    private TpProductService productService;

    @Override
    public Page<TpProduct> query(ProductRpcReq req) {
        LOG.info("查询多个产品，请求:{}",req);
        Page<TpProduct> productResult = productService.query(req.getIdList(), req.getMinRewardRate(), req.getMaxRewardRate(), req.getStatusList(), req.getPageable());
        LOG.info("查询多个产品，结果:{}",productResult);
        return productResult;
    }

    @Override
    public TpProduct findOne(String id) {
        LOG.info("查询单个产品，请求:{}",id);
        TpProduct result = productService.findOne(id);
        LOG.info("查询单个产品，结果:{}",result);
        return result;
    }
}
