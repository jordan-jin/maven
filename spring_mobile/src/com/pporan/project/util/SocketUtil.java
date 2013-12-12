package com.pporan.project.util;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

import pporan.maven.framework.data.EData;

public class SocketUtil {

	public static boolean _socketPush(EData eMap) throws Exception{
		boolean result = false;
		Socket socket = null;
		try{
			socket = new Socket(eMap.getString("ip"),eMap.getInt("port"));
			
			OutputStream out = socket.getOutputStream();
			
			out.write(eMap.getString("msg").getBytes());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				socket.close();
			}
			catch(IOException ie){}
		}
		
		return result;
	}
	
	public static String _socketPushList(List list) throws Exception{
		String reStr = "";
		Socket socket = null;
		for(int i=0,j=list.size(); i<j; i++){
			EData eMap = (EData)list.get(i);
			String[] addrs = eMap.getString("ADDR").split(":");
			try{
					socket = new Socket(addrs[0],Integer.parseInt(addrs[1]));
					OutputStream out = socket.getOutputStream();
					out.write(eMap.getString("MSG").getBytes());
					socket.close();
			}
			catch(Exception e){
				e.printStackTrace();
				reStr += eMap.getString("NAME")+",";
			}
			finally{
				try{
					socket.close();
				}
				catch(IOException ie){}
			}
		}
		
		return reStr.length() > 0 ? reStr.substring(0,reStr.lastIndexOf(",")) : reStr;
	}
	

	/*
▷ TCP/IP 소켓 통신
 
 - 송출기에 접속 성공후 아래 메시지 형식으로 메시지를 전송
 
 - 소켓을 통한 수신대해서는 처리 하지 않아도 됨(또는 항시 일정한 값을 송출기에서 보냄)
 
 - 송출기 접속 실패시에 팝업으로(또는 그외 다른 방법) 알림
 

▷ 전송신호 메시지 형식 : 
 
 -> 시스템이름|날짜(YYYYMMDD)|명령어1|명령어2
 
 - 메시지 형식에는 시스템 이름, 날짜, 명령어1, 명령어2 를 포함하며, 각각을 파이프라인(|)으로 구분
 
 - 시스템이름은 : UMSWAS
 
 - 날짜는 년월일으로 8자리 숫자 : YYYYMMDD
 
 - 명령어1, 명령어2는 추후 사용, 현재는 0으로 사용
 
ex> UMSWAS|20130821|0|0

	▷ 전송 수기
	 
	 - 오늘날짜의 스케줄 변경(등록/수정/삭제)
	 
	 - 사용자가 집적 전송
	 */
	
}
