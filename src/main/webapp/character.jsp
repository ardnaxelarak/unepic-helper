<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<!--
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
-->
    <link type="text/css" rel="stylesheet" href="webjars/jquery-mobile/1.4.5/jquery.mobile.min.css" />
    <script src="webjars/jquery/2.1.4/jquery.min.js"> </script>
    <script src="webjars/jquery-mobile/1.4.5/jquery.mobile.min.js"> </script>
</head>

<body>
<div data-role="page" class="type-interior">
    <div data-role="header">
        <h1>Character Build</h1>
    </div>
    <div data-role="content">
        <div data-role="controlgroup" data-type="horizontal">
            <input id="button1" type="button" value="minus" data-icon="minus" data-iconpos="notext" data-theme="c" data-inline="true" onclick="alert('hi');" />
            <input id="button2" type="button" value="plus" data-icon="plus" data-iconpos="notext" data-theme="b" data-inline="true" onclick="alert('hi');" />
        </div>
        <div data-role="controlgroup"  data-type="horizontal">
            <a href="index.html" data-role="button" data-icon="arrow-l" data-iconpos="notext" data-theme="a" data-inline="true">My button</a>
            <a href="index.html" data-role="button" data-icon="arrow-r" data-iconpos="notext" data-theme="b" data-inline="true">My button</a>
            <a href="index.html" data-role="button" data-icon="arrow-u" data-iconpos="notext" data-theme="c" data-inline="true">My button</a>
            <a href="index.html" data-role="button" data-icon="arrow-d" data-iconpos="notext" data-theme="d" data-inline="true">My button</a>
            <a href="index.html" data-role="button" data-icon="delete" data-iconpos="notext" data-theme="e" data-inline="true">My button</a>
            <a href="index.html" data-role="button" data-icon="plus" data-iconpos="notext" data-theme="f" data-inline="true">My button</a>
            <a href="index.html" data-role="button" data-icon="minus" data-iconpos="notext" data-theme="g" data-inline="true">My button</a>
            <a href="index.html" data-role="button" data-icon="check" data-iconpos="notext" data-theme="h" data-inline="true">My button</a>
            <a href="index.html" data-role="button" data-icon="gear" data-iconpos="notext" data-theme="i" data-inline="true">My button</a>
            <a href="index.html" data-role="button" data-icon="refresh" data-iconpos="notext" data-theme="a" data-inline="true">My button</a>
            <a href="index.html" data-role="button" data-icon="forward" data-iconpos="notext" data-theme="a" data-inline="true">My button</a>
            <a href="index.html" data-role="button" data-icon="back" data-iconpos="notext" data-theme="a" data-inline="true">My button</a>
            <a href="index.html" data-role="button" data-icon="grid" data-iconpos="notext" data-theme="a" data-inline="true">My button</a>
            <a href="index.html" data-role="button" data-icon="star" data-iconpos="notext" data-theme="a" data-inline="true">My button</a>
            <a href="index.html" data-role="button" data-icon="alert" data-iconpos="notext" data-theme="a" data-inline="true">My button</a>
            <a href="index.html" data-role="button" data-icon="info" data-iconpos="notext" data-theme="a" data-inline="true">My button</a>
            <a href="index.html" data-role="button" data-icon="home" data-iconpos="notext" data-theme="a" data-inline="true">My button</a>
            <a href="index.html" data-role="button" data-icon="search" data-iconpos="notext" data-theme="a" data-inline="true">My button</a>
        </div>
    </div>
</div>
</body>
</html>
