/**
 * Copyright (C), 2015-2020
 * FileName: ExampleTest
 * Author:   Administrator
 * Date:     2020/2/8 12:25
 * Blog:     www.codedog.xyz
 */
package xyz.codedog.simple.model;

import example.dao.CountryMapper;
import example.model.Country;
import example.model.CountryExample;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ExampleTest extends BaseMapperTest {
    @Test
    public void testExample(){
        SqlSession sqlSession = getSqlSession();
        try {
            CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
            //创建Example对象
            CountryExample example = new CountryExample();
            //设置排序规则
            example.setOrderByClause("id desc,countryname asc");
            //设置是否distinct去重
            example.setDistinct(true);
            //创建条件
            CountryExample.Criteria criteria = example.createCriteria();
            //id >= 1
            criteria.andIdGreaterThanOrEqualTo(1);
            //id < 4
            criteria.andIdLessThan(4);
            //countrycode like '%U%'
            criteria.andCountrycodeLike("%U%");
            //or的情况
            CountryExample.Criteria or = example.or();
            //countryname=中国
            or.andCountrynameEqualTo("中国");
            //执行查询
            final List<Country> countryList = countryMapper.selectByExample(example);
            for (example.model.Country country : countryList) {
                System.out.println(country.getId() + "," + country.getCountryname() + "." + country.getCountrycode());
            }
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateByExampleSelective(){
        SqlSession sqlSession = getSqlSession();
        try {
            CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
            //创建Example对象
            CountryExample example = new CountryExample();
            //创建条件,只能有一个createCriteria
            CountryExample.Criteria criteria = example.createCriteria();
            //更新所有id > 2 的国家
            criteria.andIdGreaterThan(2);
            //创建一个要设置的对象
            Country country = new Country();
            //将国家名字设置为China
            country.setCountryname("China");
            //执行查询
            countryMapper.updateByExampleSelective(country,example);
            final List<Country> countries = countryMapper.selectByExample(example);
            for (Country country1 : countries) {
                System.out.println(country1.getId() + "," + country1.getCountryname() + "." + country1.getCountrycode());
            }
        }finally{
            sqlSession.close();
        }
    }

    @Test
    public void testDeleteByExample(){
        SqlSession sqlSession = getSqlSession();
        try {
            final CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
            //创建Example对象
            CountryExample example = new CountryExample();
            //创建条件,只能有一个createCriteria
            CountryExample.Criteria criteria = example.createCriteria();
            //删除所有id > 2 的国家
            criteria.andIdGreaterThan(2);
            //执行查询
            countryMapper.deleteByExample(example);
            //使用countByExample查询符合条件的数量,因为已经删除,所以这里应该是0
            Assert.assertEquals(0,countryMapper.countByExample(example));
        }finally {
            sqlSession.close();
        }
    }




}
