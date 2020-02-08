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
import org.junit.Test;

import java.util.List;

public class ExampleTest extends BaseMapperTest {
    @Test
    public void testExample(){
        SqlSession sqlSession = getSqlSession();
        try {
            CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
            CountryExample example = new CountryExample();
            example.setOrderByClause("id desc,countryname asc");
            example.setDistinct(true);
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
                System.out.println(country);
            }
        }finally {
            sqlSession.close();
        }
    }
}
