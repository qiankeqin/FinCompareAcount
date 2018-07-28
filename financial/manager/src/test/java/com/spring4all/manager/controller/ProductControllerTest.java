package com.spring4all.manager.controller;

import com.spring4all.manager.*;
import com.spring4all.entity.TpProduct;
import com.spring4all.entity.enums.ProductStatus;
import com.spring4all.manager.repositories.TpProductRepository;
import com.spring4all.util.RestUtil;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: financial
 * @description: Product测试用例
 * @author: qiankeqin
 * @create: 2018-07-14 15:20
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = ManagerApp.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductControllerTest {

    private static RestTemplate rest = new RestTemplate();

    @Autowired
    private TpProductRepository productRepository;

    //根路径
    @Value("http://localhost:${local.server.port}/manager")
    private String baseUrl;

    //正常产品数据
    private static List<TpProduct> normals = new ArrayList<>();
    //异常产品数据
    private static List<TpProduct> errors = new ArrayList<>();

    @BeforeClass
    public static void init(){
        //正常数据
        TpProduct p1 = new TpProduct("T001","灵活宝1号", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10),BigDecimal.valueOf(1),BigDecimal.valueOf(3.42));
        TpProduct p2 = new TpProduct("T002","尊享2号",ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10),BigDecimal.valueOf(1),BigDecimal.valueOf(5.2));
        TpProduct p3 = new TpProduct("T003","安家盈1号",ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10),BigDecimal.valueOf(1),BigDecimal.valueOf(4.0));
        normals.add(p1);
        normals.add(p2);
        normals.add(p3);

        //异常数据
        TpProduct e1 = new TpProduct(null,"产品编号不可为空", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10),BigDecimal.valueOf(1),BigDecimal.valueOf(3.42));
        TpProduct e2 = new TpProduct("E002","收益率需在0～30%之间",ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10),BigDecimal.valueOf(1),BigDecimal.valueOf(35.2));
        TpProduct e3 = new TpProduct("E003","投资步长必须为整数",ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10),BigDecimal.valueOf(1.01),BigDecimal.valueOf(4.0));
        errors.add(e1);
        errors.add(e2);
        errors.add(e3);

        ResponseErrorHandler errorHandler = new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                //始终认为没有错误，然后自行进行异常处理
                return false;
            }

            /**
             * 自行异常处理
             * @param response
             * @throws IOException
             */
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {

            }
        };
        //设置rest异常处理
        rest.setErrorHandler(errorHandler);
    }

    //测试用例
    @Test
    public void create(){
        normals.forEach(product->{
            TpProduct result = RestUtil.postJSON(rest, baseUrl + "/product", product, TpProduct.class);
            Assert.notNull(result.getCreateAt(),"插入失败");//如果为空则表示插入失败
        });
    }

    @Test
    public void createException(){
        errors.forEach(product -> {
            Map<String,String> result = RestUtil.postJSON(rest, baseUrl + "/product", product, HashMap.class);
            //System.out.println(result);
            Assert.isTrue(result.get("message").equals(product.getName()),"插入成功");
            //Assert.notNull(result.getCreateAt(),"插入失败");
        });
    }

    @Test
    public void findOne(){
        normals.forEach(product->{
            TpProduct result = rest.getForObject(baseUrl + "/product/" + product.getId(), TpProduct.class);
            Assert.isTrue(result!=null && product.getId().equals(result.getId()),"查询失败");
        });

        errors.forEach(product->{
            TpProduct result = rest.getForObject(baseUrl + "/product/" + product.getId(), TpProduct.class);
            Assert.isNull(result,"查询失败");
        });
    }


    @Test
    @Transactional
    public void transaction(){
        normals.forEach(product -> {
            product.setLockTerm(0);
            productRepository.saveAndFlush(product);

        });
    }

    @Test
    public void zzzzClean(){
        productRepository.deleteAll();
    }

}
