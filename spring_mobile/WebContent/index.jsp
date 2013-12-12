<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
response.setStatus(301);
response.setHeader( "Location", "main" );
response.setHeader( "Connection", "close" );
%> 

