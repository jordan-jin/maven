package com.pporan.project.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import pporan.maven.framework.util.StringUtil;

public class Paging {
	
	private static Logger logger = Logger.getLogger(Paging.class);

	static String preNum = "";
	static String nextNum = "";
	
	public static String getPageNavigation(List list,Map map){
		
		return getPageNavigation(list, map, null);
	}
	
	public static String getPageNavigation(List list,Map map, String pageParamName){
		return getPageNavigation(list, map, pageParamName, null);
	}
	
	//댓글 페이징
	public static String getReplyPageNavigation(List list,Map map){
		return getPageNavigation(list, map, "curPage_Rep", "curPage");
	}
	
	/**
	 * 페이징 tag 문자열 생성
	 * @param list 게시물 조회 목록
	 * @param map 파라미터map
	 * @param pageParamName 페이징 파라미터명
	 * @param otherPageParamName 다른 페이지 파라미터명
	 * @return
	 */
	public static String getPageNavigation(List list,Map map, String pageParamName, String otherPageParamName){
		StringBuffer sb = new StringBuffer();

		try{
			if(list != null && list.size() > 0){
				
				if(pageParamName == null || pageParamName.length() == 0){
					pageParamName = "curPage";
				}
				
				//########## 페이징할 리스트 전체 카운트를 가져온다.
				HashMap listMap = (HashMap)list.get(0);
				int totcnt = StringUtil.nvl(listMap.get("TOTCNT"), 0);
				
				//페이징할 페이지의 url
				String nowUrl 	= StringUtil.nvl(map.get("nowUrl")).toString()+"?"+StringUtil.nvl(map.get("params")).toString();
				//현재페이지
				int curPage 	= StringUtil.nvl(map.get(pageParamName),1);
				//페이지 row 수
				int pageRowSize = StringUtil.nvl(map.get("pageRowSize"),10);
				//block 사이즈
				int nBlockSize 	= StringUtil.nvl(map.get("pageBlockSize"),10);
				
				int nStartPage = 0;		//block상에서 시작페이지
				int nEndPage = 0;		//block상에서 마지막페이지
				
				int nBlockGrpSize =  (int)Math.ceil((float)totcnt / (float)(pageRowSize * nBlockSize))  ;	//블록 그룸 size
				int nCurrentGrp = (int)Math.ceil( (float)curPage/(float)nBlockSize ); 						//현재 페이지가 속한 블록그룸번호
				int nTotalPageSize = (int)Math.ceil( (float)totcnt / (float)pageRowSize );    				//전체 페이지수
				
				nStartPage = (nCurrentGrp * nBlockSize) - (nBlockSize - 1);
	
				//마지막을 전체 페이지 번호까지
				if( nCurrentGrp == nBlockGrpSize ){
		            nEndPage = nTotalPageSize;
		        }
		        //만약 현재 페이지가 속한 블록 그룹이 마지막블록그룹보다 작다면
		        else if(nCurrentGrp < nBlockGrpSize){
		            nEndPage = nCurrentGrp * nBlockSize;
		        }
				
				//nowUrl 문자열 값 끝에 파라미터 연결자 '?', '&' 있는지 체크 후 둘다 없으면 '&'를 추가한다. 
				nowUrl = nowUrl.trim();
				if(!nowUrl.endsWith("?") && !nowUrl.endsWith("&")){
					nowUrl += "&";	
				}
				
				//현재 대상 page파라미터명 외에 다른 page페이지 파라미터 명이 있으면 추가..
				if(otherPageParamName != null && otherPageParamName.length() > 0){
					
					if(!StringUtil.nvl(map.get(otherPageParamName)).equals("")){
						nowUrl += otherPageParamName + "=" + map.get(otherPageParamName)+"&";
					}
				}
				
				sb.append(" <span class='direction first'><a href=\""+nowUrl + pageParamName + "=1\"><em></em></a></span> ");
				 
				if(nCurrentGrp != 1 ){
			        int nBefPage = (nCurrentGrp-1)*nBlockSize;
			        sb.append(" <span class='direction prev'><a href=\""+nowUrl + pageParamName + "="+nBefPage+"\" ><em></em></a></span> ");
			    }else{
					sb.append(" <span class='direction prev'><a><em></em></a></span> ");
			    }
				for(int i=nStartPage; i<=nEndPage; i++){
			        //현재 페이지라면 
			        if(i == curPage){
			        	sb.append(" <strong>"+ i + "</strong> ");
			        }
			        //현재 페이지가 아니라면
			        else if(i != curPage){
			        	sb.append(" <a href=\""+nowUrl + pageParamName+"="+i+"\">"+i+"</a> ");
			        }
			    }
				 //현재 그룹페이지가 마지막페이지가 아니고 또 전체페이지가 0이 아닐때(즉데이터가 존재할때) [다음]출력
			    if(nCurrentGrp != nBlockGrpSize && totcnt != 0){
			        int nNextPage = nCurrentGrp*nBlockSize+1;
			        sb.append(" <span class='direction next'><a href=\""+nowUrl + pageParamName+"="+nNextPage+"\" ><em></em></a></span> ");
			    }else{
			    	sb.append(" <span class='direction next'><a><em></em></a></span> ");
			    }
			    sb.append(" <span class='direction last'><a href=\""+nowUrl + pageParamName+"=" + nTotalPageSize + "\"><em></em></a></span> ");
			}else{
				sb.append("  데이터가 없습니다.  ");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
	public static HashMap getPageNavigationMap(List list,Map map){
		HashMap returnMap = new HashMap();
		StringBuffer sb = new StringBuffer();
		
		try{
			if(list != null && list.size() > 0){
				
				//########## 페이징할 리스트 전체 카운트를 가져온다.
				HashMap listMap = (HashMap)list.get(0);
				int totcnt = StringUtil.nvl(listMap.get("TOTCNT"), 0);
				
				//페이징할 페이지의 url
				String nowUrl 	= map.get("nowUrl").toString()+"?"+map.get("params").toString();
				//현재페이지
				int curPage 	= StringUtil.nvl(map.get("curPage"),1);
				//페이지 row 수
				int pageRowSize = StringUtil.nvl(map.get("pageRowSize"),10);
				//block 사이즈
				int nBlockSize 	= StringUtil.nvl(map.get("pageBlockSize"),10);
				
				int nStartPage = 0;		//block상에서 시작페이지
				int nEndPage = 0;		//block상에서 마지막페이지
				
				int nBlockGrpSize =  (int)Math.ceil((float)totcnt / (float)(pageRowSize * nBlockSize))  ;	//블록 그룸 size
				int nCurrentGrp = (int)Math.ceil( (float)curPage/(float)nBlockSize ); 						//현재 페이지가 속한 블록그룸번호
				int nTotalPageSize = (int)Math.ceil( (float)totcnt / (float)pageRowSize );    				//전체 페이지수
				
				nStartPage = (nCurrentGrp * nBlockSize) - (nBlockSize - 1);
				
				//마지막을 전체 페이지 번호까지
				if( nCurrentGrp == nBlockGrpSize ){
					nEndPage = nTotalPageSize;
				}
				//만약 현재 페이지가 속한 블록 그룹이 마지막블록그룹보다 작다면
				else if(nCurrentGrp < nBlockGrpSize){
					nEndPage = nCurrentGrp * nBlockSize;
				}
				
				if(nCurrentGrp != 1 ){
					int nBefPage = (nCurrentGrp-1)*nBlockSize;
					sb.append("<a href=\""+nowUrl+"curPage="+nBefPage+"\" class=\"prev\">");
					sb.append("◀");
					sb.append("</a>\n");
				}else{
					sb.append("<a href=\"#;\" class=\"prev\">");
					sb.append("◀");
					sb.append("</a>\n");
				}
				for(int i=nStartPage; i<=nEndPage; i++){
					//현재 페이지라면 
					if(i == curPage){
						sb.append("<a href=\""+nowUrl+"curPage="+i+"\" class=\"on\"><b>"+i+"</b></a>\n");
					}
					//현재 페이지가 아니라면
					else if(i != curPage){
						sb.append("<a href=\""+nowUrl+"curPage="+i+"\">"+i+"</a>\n");
					}
				}
				//현재 그룹페이지가 마지막페이지가 아니고 또 전체페이지가 0이 아닐때(즉데이터가 존재할때) [다음]출력
				if(nCurrentGrp != nBlockGrpSize && totcnt != 0){
					int nNextPage = nCurrentGrp*nBlockSize+1;
					sb.append("<a href=\""+nowUrl+"curPage="+nNextPage+"\" class=\"next\">");
					sb.append("▶");
					sb.append("</a>\n");
				}else{
					sb.append("<a href=\"#;\" class=\"next\">");
					sb.append("▶");
					sb.append("</a>\n");
				}
				
				returnMap.put("totcnt", totcnt);
				returnMap.put("curPage", curPage);
				returnMap.put("pageRowSize", pageRowSize);
				returnMap.put("pageBlockSize", nBlockSize);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		returnMap.put("pageNavigation", sb.toString());
		return returnMap;
	}
}
