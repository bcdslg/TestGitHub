package com.hnii.scweb.util;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * mybatis公共通用部分
 * @author dengwei
 *
 */
public class BaseDao extends SqlSessionDaoSupport{	
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	
	//insert 部分 begin
    public int insert(String statement) {  
        return getSqlSession().insert(statement);  
    }  
    public int insert(String statement, Object parameter) {  
        return getSqlSession().insert(statement, parameter);  
    }  
    //insert 部分 end
    
    //update 部分 begin
    public int update(String statement) {  
        return getSqlSession().update(statement);  
    }  
  
    public int update(String statement, Object parameter) {  
        return getSqlSession().update(statement, parameter);  
    }  
    //update 部分 begin
    
    //delete 部分  begin
    public int delete(String statement) {  
        return getSqlSession().delete(statement);  
    }  
      
    public int delete(String statement, Object parameter) {  
        return getSqlSession().delete(statement, parameter);  
    }  
    //delete 部分  end
    
    
    /** 
     * 获取一个list集合 
     * @param statement 
     * @return 
     */  
    public <T> List<T> selectList(String statement,Class<T> cla) {  
        return getSqlSession().selectList(statement);  
    }  
    public <T> List<T> selectList(String statement, Object parameter,Class<T> cla) {  
        return getSqlSession().selectList(statement, parameter);  
    }  
      
    public Map<?, ?> selectMap(String statement, String mapKey) {  
        return getSqlSession().selectMap(statement, mapKey);  
    }  
  
    public Map<?,?> selectMap(String statement, Object parameter, String mapKey) {  
        return getSqlSession().selectMap(statement, parameter, mapKey);  
    }  
     
    public <T> T selectObject(String statement,Class<T> cla) {  
        return getSqlSession().selectOne(statement);  
    }  
    
    public <T> T selectObject(String statement,Object parameter,Class<T> cla) {  
    	return getSqlSession().selectOne(statement, parameter);
    }
   
    
    /** 
     * 获取connection, 以便执行较为复杂的用法 
     * @return 
     */  
    public Connection getConnection() {  
        return getSqlSession().getConnection();  
    }  
}
