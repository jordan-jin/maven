/**
 * @author jinbos
 * @version 00.00.01
 * @date 2012.11.11
 * @category kr.co.etribe.framework.page
 * @description PagingUtil 에서 사용하는 Page 도메인 클래스 입니다.
 */
package pporan.maven.framework.page;

import java.io.Serializable;

public class PageDomain implements Serializable {

	private static final long serialVersionUID = 1L;
    
    private int pageRowSize;//페이지 리스트 개수
	private int pageListSize;//numbering개수
	private int pg;//현재 페이지 번호
	private int totalRow;//전체 리스트 개수
	private int totalPg;//전체 페이지 개수
	private int startPg;//시작 페이지
	private String delim;//페이지 구분 스트링
	private String pagingStr;// 페이징 String

	
	public int getPageRowSize() {
		return pageRowSize;
	}
	
	public void setPageRowSize(int pageRowSize) {
		this.pageRowSize = pageRowSize;
	}
	
	public int getPageListSize() {
		return pageListSize;
	}
	
	public void setPageListSize(int pageListSize) {
		this.pageListSize = pageListSize;
	}
	
	public int getPg() {
		return pg;
	}
	
	public void setPg(int pg) {
		this.pg = pg;
	}
	
	public int getTotalRow() {
		return totalRow;
	}
	
	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}
	
	public int getTotalPg() {
		return totalPg;
	}
	
	public void setTotalPg(int totalPg) {
		this.totalPg = totalPg;
	}
	
	public int getStartPg() {
		return startPg;
	}
	
	public void setStartPg(int startPg) {
		this.startPg = startPg;
	}
	
	public String getDelim() {
		return delim;
	}
	
	public void setDelim(String delim) {
		this.delim = delim;
	}

	public String getPagingStr() {
		return pagingStr;
	}

	public void setPagingStr(String pagingStr) {
		this.pagingStr = pagingStr;
	}

}
