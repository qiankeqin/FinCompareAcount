package com.spring4all.manager.service;

import com.spring4all.entity.TpProduct;
import com.spring4all.entity.enums.ProductStatus;
import com.spring4all.manager.error.ErrorEnum;
import com.spring4all.manager.repositories.TpProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: financial
 * @description: 产品服务类
 * @author: qiankeqin
 * @create: 2018-07-14 10:20
 **/
@Service
public class TpProductService {
    private static Logger LOG = LoggerFactory.getLogger(TpProductService.class);

    @Autowired
    private TpProductRepository productRepository;

    public TpProduct addProduct(TpProduct product){
        LOG.debug("创建产品，参数:{}",product);
        //数据校验
        checkProduct(product);

        //设置默认值
        setDefault(product);

        TpProduct result = productRepository.save(product);
        LOG.debug("创建产品，结果:{}",result);
        return result;
    }

    /**
     * 查询单个产品
     * @param id 产品ID
     * @return
     */
    public TpProduct findOne(String id){
        Assert.notNull(id,"需要产品编号参数");
        LOG.debug("查询单个产品，参数={}",id);

        TpProduct product = productRepository.findOne(id);

        LOG.debug("查询单个产品，结果={}",product);

        return product;
    }

    /**
     * 分页查询产品
     * @param idList 产品编号列表
     * @param minRewardRate 回报率下限
     * @param maxRewardRate 回报率上限
     * @param statusList 状态列表
     * @param pageable 分页数据
     * @return
     */
    public Page<TpProduct> query(List<String> idList,
                                 BigDecimal minRewardRate, BigDecimal maxRewardRate,
                                 List<String> statusList, Pageable pageable){
        LOG.debug("查询产品，idList={},minRewardRate={},maxRewardRate={},statusList={},pageable={}",idList,minRewardRate,maxRewardRate,statusList,pageable);
        Specification<TpProduct> specification = new Specification<TpProduct>() {
            @Override
            public Predicate toPredicate(Root<TpProduct> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //设置需要作为查询的列
                Expression<String> idCol = root.get("id");
                Expression<BigDecimal> rewardRateCol = root.get("rewardRate");
                Expression<String> statusCol = root.get("status");
                List<Predicate> predicates = new ArrayList<>();
                //组合条件
                if(null!=idList && idList.size()>0){
                    predicates.add(idCol.in(idList));
                }
                if(null!=minRewardRate && BigDecimal.ZERO.compareTo(minRewardRate)<0){
                    predicates.add(cb.ge(rewardRateCol,minRewardRate));
                }
                if(null!=maxRewardRate && BigDecimal.ZERO.compareTo(maxRewardRate)<0){
                    predicates.add(cb.le(rewardRateCol,maxRewardRate));
                }
                if(null!=statusList && statusList.size()>0){
                    predicates.add(statusCol.in(statusList));
                }
                //查询
                query.where(predicates.toArray(new Predicate[0]));
                return null;
            }
        };

        Page<TpProduct> page = productRepository.findAll(specification, pageable);
        LOG.debug("查询产品，结果={}",page);
        return page;
    }

    /**
     * 设置默认值
     * 设置创建时间，更新时间
     * 设置投资步长，锁定期，状态
     * 注意，原始类型中不使用默认值
     * @param product
     */
    private void setDefault(TpProduct product) {
        if(product.getCreateAt() == null){
            product.setCreateAt(new Date());
        }
        if(product.getUpdateAt()==null){
            product.setUpdateAt(new Date());
        }
        if(product.getStepAmount()==null){
            product.setStepAmount(BigDecimal.ZERO);
        }
        if(product.getLockTerm()==null){
            product.setLockTerm(0);
        }
        if(product.getStatus()==null){
            product.setStatus(ProductStatus.AUDITING.name());
        }
    }

    /**
     * 产品数据校验（业务校验和逻辑校验）
     * 非空数据
     * 收益率在0～30以内
     * 投资步长需为整数
     * @param product
     */
    private void checkProduct(TpProduct product) {
        Assert.notNull(product.getId(), ErrorEnum.ID_NOT_NULL.getCode());
        //其他非空校验

        Assert.isTrue(BigDecimal.ZERO.compareTo(product.getRewardRate())<0 && BigDecimal.valueOf(30).compareTo(product.getRewardRate())>0,ErrorEnum.REWARDRATE_ERROR.getCode());

        //longValue()获取整数部分
        Assert.isTrue(BigDecimal.valueOf(product.getStepAmount().longValue()).compareTo(product.getStepAmount())==0,ErrorEnum.STEPAMOUNT.getCode());

    }

}
