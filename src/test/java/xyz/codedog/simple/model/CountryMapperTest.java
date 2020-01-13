/**
 * Copyright (C), 2015-2020
 * FileName: CountryMapperTest
 * Author:   Administrator
 * Date:     2020/1/9 19:52
 * Blog:     www.codedog.xyz
 */
package xyz.codedog.simple.model;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class CountryMapperTest extends BaseMapperTest{
/*    抽取到 BaseMapperTest 中
    private static SqlSessionFactory sqlSessionFactory;

    @BeforeClass
    public static void init(){
        try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */

    @Test
    public void testSelectAll(){
        SqlSession sqlSession = getSqlSession();
        try {
            List<Country> countryList = sqlSession.selectList("xyz.codedog.simple.mapper.CountryMapper.selectAll");
            printCountryList(countryList);
        }finally {
            sqlSession.close();
        }
    }

    private void printCountryList(List<Country> countryList) {
        for (Country country : countryList) {
            System.out.printf("%-4d%4s%4s\n",country.getId(),country.getCountryname(), country.getCountrycode());
        }
    }
}
