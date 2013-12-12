package com.pporan.project.util;

import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import pporan.maven.framework.data.EData;

public class XmlCreateUtil {
	
	final static String[] fwKeys = {"VendorID","HardwareVersionID","SoftwareVersionID","STBType","ProtocolID","DownloadCommand","HostIDSuffix","UnicastDownloadInfo","BroadcastDownloadInfo"};
	final static String[] swKeys = {"OrganizationID","ApplicationID","ApplicationVer","ApplicationName","STBType","ProtocolID","DownloadCommand","HostIDSuffix","UnicastDownloadInfo","BroadcastDownloadInfo"};

	
	public static String _xmlCreateDistinfo(List fwList, List swList, List fileList, EData eMap) throws Exception{
		StringBuffer root = new StringBuffer();
		root.append("<DistributeSignalResponse>");
		root.append("<SO_ID>");
		root.append(eMap.getString("so_id"));
		root.append("</SO_ID>");
		
		String[] itKey = null;
		if(fwList != null && fwList.size() > 0){
			root.append("<FWList>");
			
			for(int m=0, n=fwList.size(); m<n; m++){
				root.append("<FW>");
				EData dataMap = (EData) fwList.get(m);
				itKey = fwKeys;
				for(int i=0, j=itKey.length; i<j; i++){
					String key_name = itKey[i];
					if(key_name.equals("UnicastDownloadInfo") || key_name.equals("BroadcastDownloadInfo")){
						if(key_name.equals("UnicastDownloadInfo") && !"2".equals(dataMap.getString("ProtocolID"))){
							root.append("<UnicastDownloadInfo>");
							for(int k=0, l=fileList.size(); k<l; k++){
								EData fileMap = (EData)fileList.get(k);
								root.append("<DownloadURL>");
								root.append(fileMap.getString("FILESERVER_URL")+"/"+dataMap.getString("DownPath"));
								root.append("</DownloadURL>");
							}
							root.append("</UnicastDownloadInfo>");
						}
						if(key_name.equals("BroadcastDownloadInfo") && !"1".equals(dataMap.getString("ProtocolID"))){
							root.append("<BroadcastDownloadInfo>");
							root.append("<FrequencyVector>");
							root.append(dataMap.getString("FrequencyVector"));
							root.append("</FrequencyVector>");
							root.append("<TransportValue>");
							root.append(dataMap.getString("TransportValue"));
							root.append("</TransportValue>");
							root.append("<PID>");
							root.append(dataMap.getString("PID"));
							root.append("</PID>");
							root.append("<FileName>");
							root.append(dataMap.getString("FileName"));
							root.append("</FileName>");
							root.append("</BroadcastDownloadInfo>");
						}
					}
					else{
						root.append("<"+key_name+">");
						root.append(dataMap.getString(key_name));
						root.append("</"+key_name+">");
					}
				}
				root.append("</FW>");
			}
			root.append("</FWList>");
		}
		
		if(fwList != null && swList.size() > 0){
			root.append("<SWList>");
			for(int m=0, n=swList.size(); m<n; m++){
				root.append("<SW>");
				EData dataMap = (EData) swList.get(m);
				itKey = swKeys;
				for(int i=0, j=itKey.length; i<j; i++){
					String key_name = itKey[i];
					if(key_name.equals("UnicastDownloadInfo") || key_name.equals("BroadcastDownloadInfo")){
						if(key_name.equals("UnicastDownloadInfo") && !"2".equals(dataMap.getString("ProtocolID"))){
							root.append("<UnicastDownloadInfo>");
							for(int k=0, l=fileList.size(); k<l; k++){
								EData fileMap = (EData)fileList.get(k);
								root.append("<DownloadURL>");
								root.append(fileMap.getString("FILESERVER_URL")+"/"+dataMap.getString("DownPath"));
								root.append("</DownloadURL>");
							}
							root.append("</UnicastDownloadInfo>");
						}
						if(key_name.equals("BroadcastDownloadInfo") && !"1".equals(dataMap.getString("ProtocolID"))){
							root.append("<BroadcastDownloadInfo>");
							root.append("<FrequencyVector>");
							root.append(dataMap.getString("FrequencyVector"));
							root.append("</FrequencyVector>");
							root.append("<TransportValue>");
							root.append(dataMap.getString("TransportValue"));
							root.append("</TransportValue>");
							root.append("<PID>");
							root.append(dataMap.getString("PID"));
							root.append("</PID>");
							root.append("<FileName>");
							root.append(dataMap.getString("FileName"));
							root.append("</FileName>");
							root.append("</BroadcastDownloadInfo>");
						}
					}
					else{
						root.append("<"+key_name+">");
						root.append(dataMap.getString(key_name));
						root.append("</"+key_name+">");
					}
				}
				root.append("</SW>");
			}
			root.append("</SWList>");
		}
		
		
		root.append("</DistributeSignalResponse>");
		System.out.println(root.toString());
		return root.toString();
	}
	public static String _xmlCreateDistinfo2(List fwList, List swList, List fileList, EData eMap) throws Exception{
		Element root = new Element("DistributeSignalResponse");
		Element so_id = new Element("SO_ID");
		root.addContent(so_id);
		so_id.setText(eMap.getString("so_id"));
		
		System.out.println(root.toString());
		
		String[] itKey = null;
		if(fwList != null && fwList.size() > 0){
			Element fwlist = new Element("FWList");
			
			for(int m=0, n=fwList.size(); m<n; m++){
				Element fw = new Element("FW");
				EData dataMap = (EData)fwList.get(m);
				
				itKey = fwKeys; 
				for(int i=0, j=itKey.length; i<j; i++){
					String key_name = itKey[i];
					Element name = null;
					if(key_name.equals("UnicastDownloadInfo") && !dataMap.get("ProtocolID").equals("2")){
						name = new Element(key_name);
						Element downloadurl = new Element("DownloadURL");
						for(int k=0, l=fileList.size(); k<l; k++){
							EData fileMap = (EData)fileList.get(k);
							downloadurl.setText(fileMap.getString("server_url")+"/"+dataMap.getString("DownPath"));
							name.addContent(downloadurl);
						}
					}
					else if(key_name.equals("BroadcastDownloadInfo") && !dataMap.get("ProtocolID").equals("1")){
						name = new Element(key_name);
						Element frequencyvector = new Element("FrequencyVector");
						frequencyvector.setText(dataMap.getString("FrequencyVector"));
						
						Element transportvalue = new Element("TransportValue");
						transportvalue.setText(dataMap.getString("TransportValue"));
						
						Element pid = new Element("PID");
						pid.setText(dataMap.getString("PID"));
						
						Element filename = new Element("FileName");
						filename.setText(dataMap.getString("FileName"));
						
						name.addContent(frequencyvector);
						name.addContent(transportvalue);
						name.addContent(pid);
						name.addContent(filename);
						
					}
					else{
						name = new Element(key_name);
						System.out.println(dataMap.getString(key_name));
						name.setText(dataMap.getString(key_name));
					}
					fw.addContent(name);
				}
				fwlist.addContent(fw);
			}
			System.out.println(fwlist.toString());
			root.addContent(fwlist);
		}
		if(swList != null && swList.size() > 0){
			Element swlist = new Element("SWList");
			
			for(int m=0, n=fwList.size(); m<n; m++){
				Element fw = new Element("SW");
				EData dataMap = (EData)fwList.get(m);
				itKey = swKeys; 
				
				for(int i=0, j=itKey.length; i<j; i++){
					String key_name = itKey[i];
					Element name = null;
					if(key_name.equals("UnicastDownloadInfo") && !dataMap.get("ProtocolID").equals("2")){
						name = new Element(key_name);
						Element downloadurl = new Element("DownloadURL");
						for(int k=0, l=fileList.size(); k<l; k++){
							EData fileMap = (EData)fileList.get(k);
							downloadurl.setText(fileMap.getString("server_url")+"/"+dataMap.getString("DownPath"));
							name.addContent(downloadurl);
						}
					}
					else if(key_name.equals("BroadcastDownloadInfo") && !dataMap.get("ProtocolID").equals("1")){
						name = new Element(key_name);
						Element frequencyvector = new Element("FrequencyVector");
						frequencyvector.setText(dataMap.getString("FrequencyVector"));
						
						Element transportvalue = new Element("TransportValue");
						transportvalue.setText(dataMap.getString("TransportValue"));
						
						Element pid = new Element("PID");
						pid.setText(dataMap.getString("PID"));
						
						Element filename = new Element("FileName");
						filename.setText(dataMap.getString("FileName"));
						
						name.addContent(frequencyvector);
						name.addContent(transportvalue);
						name.addContent(pid);
						name.addContent(filename);
						
					}
					else{
						name = new Element(key_name);
						name.setText(dataMap.getString(key_name));
					}
					fw.addContent(name);
				}
				
				
				swlist.addContent(fw);
			}
			root.addContent(swlist);
		}
		System.out.println(root.toString());
		
		Document doc = new Document(root);
		System.out.println(doc.toString());
		return doc.toString();
	}


}
