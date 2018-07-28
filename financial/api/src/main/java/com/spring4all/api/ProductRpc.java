package com.spring4all.api;

import com.googlecode.jsonrpc4j.JsonRpcService;
import com.spring4all.api.domain.ProductRpcReq;
import com.spring4all.entity.TpProduct;
import org.springframework.data.domain.Page;

/**
 * @program: financial
 * @description: 产品相关rpc服务
 * @author: qiankeqin
 * @create: 2018-07-28 10:39
 **/
@JsonRpcService("/products")
public interface ProductRpc {

    /**
     * 查询多个商品
     * @param req
     * @return
     */
    Page<TpProduct> query(ProductRpcReq req);

    /**
     * 查询单个产品
     * @param id
     * @return
     */
    TpProduct findOne(String id);
}
