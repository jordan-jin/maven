/**
 * @author jinbos
 * @version 00.00.01
 * @date 2012.11.09
 * @category kr.co.etribe.framework.db
 * @description CommonDao 및 SqlSessionDaoSupport을 상속받아 Mybatis를 통해 Data를 처리 하는 로직
 */
package pporan.maven.framework.db;

import java.util.List;
import java.util.Map;


import org.mybatis.spring.support.SqlSessionDaoSupport;

import pporan.maven.framework.data.EData;


public class SqlMapCommonDao extends SqlSessionDaoSupport implements CommonDao{
	
	@Override
	public int deleteItem(String statementName, EData conditions) {
		return getSqlSession().delete(statementName, conditions);
	}
	
	@Override
	public void insertItem(String statementName, EData conditions) {
		getSqlSession().insert(statementName, conditions);

	}

	@Override
	public Object insertItem2(String statementName, Object conditions) {
		return getSqlSession().insert(statementName, conditions);
	}

	@Override
	public EData selectItem(String statementName, EData conditions) {
		return (EData)getSqlSession().selectOne(statementName, conditions);
	}

	@Override
	public List selectList(String statementName, EData conditions) {
		return getSqlSession().selectList(statementName, conditions);
	}
	
	@Override
	public Map<String, Object> selectMap(String statementName, String mapKey,
			EData conditions) {
		return getSqlSession().selectMap(statementName, conditions, mapKey);
	}

	@Override
	public int updateItem(String statementName, EData conditions) {
		return getSqlSession().update(statementName, conditions);
	}

}
