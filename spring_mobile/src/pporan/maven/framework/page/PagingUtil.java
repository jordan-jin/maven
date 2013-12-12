/**
 * @author jinbos
 * @version 00.00.01
 * @date 2012.11.11
 * @category kr.co.etribe.framework.page
 * @description Pageing을 생성하기 위한 로직
 */
package pporan.maven.framework.page;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import pporan.maven.framework.data.EData;
import pporan.maven.framework.util.StringUtil;

/**
 * <p>
 * 페이지 네비게이션을 생성한다.
 * </p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: LGT</p>
 * @author LINUS, 2010. 05. 18
 * @version 1.0
 */

public class PagingUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final ResourceBundle rb = ResourceBundle.getBundle("properties.paging");
	private static final String paging_first_img = rb.getString("paging_first_img");
	private static final String paging_last_img = rb.getString("paging_last_img");
	private static final String paging_prev_img = rb.getString("paging_prev_img");
	private static final String paging_next_img = rb.getString("paging_next_img");
	
	
	private static final int DEFAULT_PAGE_ROW_SIZE = 10; //row size
    private static final int DEFAULT_PAGE_LIST_SIZE = 10; //list size
    public static final String DEFAULT_DELIM = "|";//page number delimeter    
    
    
    
    //페이징에 필요한 이미지들
    public static final String PAGING_FIRST_IMG	  	= "<img src=\""+paging_first_img+"\" alt=\"맨 처음으로\" />";
    public static final String PAGING_LAST_IMG 	 	= "<img src=\""+paging_last_img+"\" alt=\"끝\" />";

    public static final String PAGING_DIS_FIRST_IMG 	= "";
    public static final String PAGING_DIS_LAST_IMG  	= "";    

    public static final String PAGING_PREV_IMG 			= "<img class=\"prev\" src=\""+paging_first_img+"\" alt=\"이전\" />";
    public static final String PAGING_NEXT_IMG 			= "<img class=\"next\" src=\""+paging_first_img+"\" alt=\"다음\" />";

    
    public static final String PAGING_DIS_PREV_IMG  	= "<a href=\"#none\" class=\"direction\"><img class=\"prev\" src=\""+paging_prev_img+"\" alt=\"이전\" /></a>";
    public static final String PAGING_DIS_NEXT_IMG  	= "<a href=\"#none\" class=\"direction\"><img class=\"next\" src=\""+paging_next_img+"\" alt=\"다음\" /></a>";
    
	private PagingUtil() {}
	
	public static String getPangination(PageDomain page){
		setPageDomainInit( page );
		//return getScriptPageList();
		return getScriptPageList( page );
	}
	
	/**
	 * PageUtil 객체를 생성한다
	 * @param curPage
	 * @param totalCnt
	 * @param pageRowSize
	 * @return
	 */
	public static String getPangination(int curPage, int totalCnt, int pageRowSize){
		PageDomain page = new PageDomain();
		page.setPg(curPage );
		page.setTotalRow( totalCnt );
		page.setPageRowSize( pageRowSize );
		page.setPageListSize( DEFAULT_PAGE_LIST_SIZE );
		setPageDomainInit( page );
		
		return getScriptPageList( page );
	}


	/**
	 * PageUtil 객체를 생성한다
	 * @param curPage
	 * @param totalCnt
	 * @param pageRowSize
	 * @param pageListSize
	 * @return
	 */
	public static Map getPangination(EData eMap){
		PageDomain page = new PageDomain();
		
		int curPage = 1;
		int totalCnt = 0;
		int pageRowSize = DEFAULT_PAGE_ROW_SIZE;
		int pageListSize = DEFAULT_PAGE_LIST_SIZE;

		try{
			curPage = getObj2Int( eMap.get("curPage") );
		}catch(Exception e){}
		
		try{
			totalCnt = getObj2Int( eMap.get("totalCnt") );
		}catch(Exception e){e.printStackTrace();}
		
		try{
			pageRowSize = getObj2Int( eMap.get("pageRowSize") );
		}catch(Exception e){}

		try{
			pageListSize = getObj2Int( (Integer) eMap.get("pageListSize") );
		}catch(Exception e){}
		
		try{
			page.setPg( curPage );
			page.setTotalRow( totalCnt );
			page.setPageRowSize( pageRowSize );
			page.setPageListSize( pageListSize );
		}catch(NumberFormatException e){
			
		}

		String pagingStr = getPangination( page );
		page.setPagingStr(pagingStr);
		eMap.put("page", page);
		
		return eMap;
	}
	
	/**
	 * 페이징 리스트에서 필요한 값을 지정한다.
	 */
    private static void setPageDomainInit(PageDomain page) {
    	// 총페이지수
    	page.setTotalPg( page.getTotalRow() / page.getPageRowSize() );
		if ( page.getTotalRow() % page.getPageRowSize() != 0) page.setTotalPg( page.getTotalPg()+1 );
		
		// 현재 페이지
		if ( page.getPg() > page.getTotalPg() ) page.setPg(1);
		
		//페이지 리스트에서의 시작 페이지 번호
		page.setStartPg ( ( ( page.getPg() - 1) / page.getPageListSize() ) * page.getPageListSize() + 1 ) ;	
		
	}
    


	/**
	 * goPage(pageno) 함수를 이용한 페이지 이동
	 * @return 화면 출력용 페이지 목록을 리턴한다.
	 */
	public static String getScriptPageList( PageDomain page ) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(rb.getString("page_fir_css"));
		if ( page.getTotalRow() > 0 ) {
			//맨처음으로가기 버튼 생성
			if ( page.getTotalPg() > 1) {
				sb.append(rb.getString("page_fir_str_css"));
				sb.append(PAGING_FIRST_IMG);
				sb.append(rb.getString("page_fir_end_css"));
			} else {
				sb.append(PAGING_DIS_FIRST_IMG);		//링크를 걸지 않는다.
			}
			
			//이전페이지리스트로 가기 버튼 생성
			if ( page.getPg() > page.getPageListSize() ) {
				sb.append(rb.getString("page_prev_str_css"));
				sb.append(PAGING_PREV_IMG);
				sb.append(rb.getString("page_prev_end_css"));
			} else {
				sb.append(PAGING_DIS_PREV_IMG);
			}
			
//			sb.append("<ol>");
			//페이지리스트를 생성한다  ex) [1] [2] ..
			for (int i = 0, curPg = page.getStartPg() + i;
				i < page.getPageListSize() && curPg <= page.getTotalPg(); 
				i++, curPg = page.getStartPg() + i) {
				
				if ( page.getPg() == curPg) {
					sb.append(rb.getString("now_page_num_prev_css"));
					sb.append(curPg);
					sb.append(rb.getString("now_page_num_next_css"));
				} else {
					sb.append(rb.getString("page_num_prev_css"));
					sb.append(String.valueOf(curPg));
					sb.append(rb.getString("page_num_prev_css"));
				}
				sb.append("\n");

			}
//			sb.append("</ol>");
			
			//다음페이지 리스트로 이동하기 
			if ( page.getStartPg() + page.getPageListSize() <= page.getTotalPg()) {
				sb.append(rb.getString("page_next_str_css"));
				sb.append(PAGING_NEXT_IMG);
				sb.append(rb.getString("page_next_end_css"));
			} else {
				sb.append(PAGING_DIS_NEXT_IMG);
			}
			
			// 마지막페이지로 이동하기
			// 전체 페이지수가 2이상일경우 마지막 페이지로 이동버튼 생성
			if (page.getTotalPg() > 1) {
				sb.append(rb.getString("page_last_str_css"));
				sb.append(PAGING_LAST_IMG);
				sb.append(rb.getString("page_last_end_css"));
			} else {
				sb.append(PAGING_DIS_LAST_IMG);
			}
		}
		
		sb.append(rb.getString("page_end_css"));
		return sb.toString();
		
	}
	
    public static String getDefaultDelim() {
    	return DEFAULT_DELIM;
    }       
    
    public static int getObj2Int(Object obj){
    	int value = 1;
    	String className = obj.getClass().getName();
    	if("java.lang.String".equals( className )){
    		value = Integer.parseInt( (String) obj );
    	}else if("java.lang.Long".equals( className )){
    		value = ((Long) obj).intValue();
    	}else if("java.lang.Integer".equals( className )){
    		value = ((Integer) obj).intValue();
    	}else if("java.math.BigDecimal".equals( className )){
    		value = ((BigDecimal) obj).intValue();
    	}else if("java.lang.Double".equals( className )){
    		value = ((Double) obj).intValue();
    	}

    	return value;
    }
    
    /*
     * 스크립트에 폼네임 지정시 사용
     */
    public static String formIdUse(String pageScript, String form){
    	
    	pageScript = pageScript.replaceAll("goPage\\(\\p{Digit}*", "$0,'"+form+"'");
    	
//    	System.out.println(pageScript);
    	return pageScript;

    }
    
    public static void formIdUse(PageDomain page, String form){
		page.setPagingStr( formIdUse(page.getPagingStr(), form ) );
    }
    
    
    
    
    /**
     * 각 페이지별 리스트 가지고 오기(Oracle)
     */
    public static PageDomain _paging(List list, EData eMap){
    	
    	
    	String totalCnt = "";
		if(list.size()==0) { 
			totalCnt = "0";
		}
		else{
			HashMap countMap = (HashMap)list.get(0);
			totalCnt = StringUtil.nvl(countMap.get("TOTALCNT"),"0");
		}
		eMap.put("curPage", StringUtil.nvl(eMap.get("CURPAGE"),"1"));
		eMap.put("totalCnt", totalCnt);
    	
		getPangination(eMap);
		PageDomain page = (PageDomain) PagingUtil.getPangination(eMap).get("page");
    	
    	return page;
    }
    
}