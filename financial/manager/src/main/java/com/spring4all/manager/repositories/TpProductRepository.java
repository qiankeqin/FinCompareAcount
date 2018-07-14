package com.spring4all.manager.repositories;

import com.spring4all.entity.TpProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @program: financial
 * @description:产品管理
 * @author: qiankeqin
 * @create: 2018-07-14 10:15
 **/
public interface TpProductRepository extends JpaRepository<TpProduct,String>,JpaSpecificationExecutor<TpProduct>{

}
