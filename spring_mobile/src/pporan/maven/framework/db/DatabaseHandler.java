/**
 * @author jinbos
 * @version 00.00.01
 * @date 2012.11.09
 * @category kr.co.etribe.framework.db
 * @description DB 종류별 Connection을 할수 있도록 처리 하는 로직
 */
package pporan.maven.framework.db;

import java.util.List;
import java.util.Map;

import pporan.maven.framework.data.EData;



public class DatabaseHandler {

	public static int DB_ORACLE = 1;
	public static int DB_MYSQL = 2;
	public static int DB_MSSQL = 3;
	
	private CommonDao oracleCommonDao;
	private CommonDao mysqlCommonDao;
	private CommonDao mssqlCommonDao;

	
	public void setOracleCommonDao(CommonDao oracleCommonDao) {
		this.oracleCommonDao = oracleCommonDao;
	}

	public void setMysqlCommonDao(CommonDao mysqlCommonDao) {
		this.mysqlCommonDao = mysqlCommonDao;
	}
	
	public void setMssqlCommonDao(CommonDao mssqlCommonDao) {
		this.mssqlCommonDao = mssqlCommonDao;
	}
	
	public CommonDao getCommonDao(int dbtype) {
		if(dbtype == DB_ORACLE){
			return oracleCommonDao;
		}
		else if(dbtype == DB_MYSQL){
			return mysqlCommonDao;
		}
		else if(dbtype == DB_MSSQL){
			return mssqlCommonDao;
		}
		else{
			return oracleCommonDao;
		}
	}


	/**
	 * 데이터 조회 - 1row
	 * @param dbtype
	 * @param statementName
	 * @param conditions
	 * @return
	 */
	public EData selectItem(int dbtype, String statementName, EData conditions) {		
		CommonDao dao = getCommonDao(dbtype);
		return dao.selectItem(statementName, conditions);
	}
	
	/**
	 * 데이터 조회 
	 * @param dbtype
	 * @param statementName
	 * @param conditions
	 * @returngetCommonDao
	 */
	public List selectList(int dbtype, String statementName, EData conditions) {
		CommonDao dao = getCommonDao(dbtype);
		return dao.selectList(statementName, conditions);
	}
	
	/**
	 * 데이터 조회 (결과를 map으로 리턴)
	 * @param dbtype
	 * @param statementName mapper id
	 * @param mapKey 쿼리 결과 map의 key로 사용할 컬럼명
	 * @param conditions 조건절
	 * @return
	 */
	public Map<String, Object> selectMap(int dbtype, String statementName, String mapKey, EData conditions){
		CommonDao dao = getCommonDao(dbtype);
		return dao.selectMap(statementName, mapKey, conditions);
	}
	
	/**
	 * 데이터 입력
	 * @param dbtype
	 * @param statementName
	 * @param conditions
	 */
	public void insertItem(int dbtype, String statementName, EData conditions) {
		CommonDao dao = getCommonDao(dbtype);
		dao.insertItem(statementName, conditions);
	}
	public Object insertItem2(int dbtype, String statementName, Object conditions) {
		CommonDao dao = getCommonDao(dbtype);
		return dao.insertItem2(statementName, conditions);
	}
	
	/**
	 *  데이터 수정
	 * @param dbtype
	 * @param statementName
	 * @param conditions
	 */
	public int updateItem(int dbtype, String statementName, EData conditions) {
		CommonDao dao = getCommonDao(dbtype);
		return dao.updateItem(statementName, conditions);
	}
	
	/**
	 * 데이터 삭제
	 * @param dbtype
	 * @param statementName
	 * @param conditions
	 */
	public int deleteItem(int dbtype, String statementName, EData conditions) {
		CommonDao dao = getCommonDao(dbtype);
		return dao.deleteItem(statementName, conditions);
	}
}
