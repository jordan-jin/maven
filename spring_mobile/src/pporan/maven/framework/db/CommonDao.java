/**
 * @author jinbos
 * @version 00.00.01
 * @date 2012.11.09
 * @category kr.co.etribe.framework.db
 * @description Mybatis를 통한 DB 와의 Connectiond을 Statement/ResultSet 기능을 위한 Interface
 */
package pporan.maven.framework.db;

import java.util.List;
import java.util.Map;

import pporan.maven.framework.data.EData;


public interface CommonDao {
	
	/**
	 * 데이터 조회 - 1 row
	 * @param statmentName
	 * @param conditions
	 * @return
	 */
	public EData selectItem(String statementName, EData conditions);

	/**
	 * 데이터 조회
	 * @param statmentName
	 * @param conditions
	 * @return
	 */
	public List<Object> selectList(String statementName, EData conditions);
	
	/**
	 * 데이터 조회 (결과를 map으로 리턴)
	 * @param statementName mapper id
	 * @param mapKey 쿼리 결과 map의 key로 사용할 컬럼명
	 * @param conditions 조건절
	 * @return
	 */
	public Map<String, Object> selectMap(String statementName, String mapKey, EData conditions);
	
	/**
	 * 데이터 입력
	 * @param statementName
	 * @param conditons
	 */
	public void insertItem(String statementName, EData conditions);
	public Object insertItem2(String statementName, Object conditions);
	
	/**
	 * 데이터 수정
	 * @param statementName
	 * @param conditons
	 */
	public int updateItem(String statementName, EData conditions);
	
	/**
	 * 데이터 삭제
	 * @param statmentName
	 * @param conditions
	 */
	public int deleteItem(String statementName, EData conditions);
}
