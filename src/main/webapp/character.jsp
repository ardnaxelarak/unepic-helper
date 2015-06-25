<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    String[] categories = new String[] {"daggers", "swords", "maces", "axes", "polearms", "bows", "staves", "wands", "constitution", "armor", "robes", "fire", "frost", "healing", "alteration", "arcane", "mental", "protection", "light"};
%>

<html>
<head>
    <link type="text/css" rel="stylesheet" href="webjars/jquery-mobile/1.4.5/jquery.mobile.min.css" />
    <script src="webjars/jquery/2.1.4/jquery.min.js"> </script>
    <script src="webjars/jquery-mobile/1.4.5/jquery.mobile.min.js"> </script>
    <script src="character-build.js"> </script>
</head>

<body>
<div data-role="page" class="type-interior">
    <div data-role="header">
        <h1>Character Build</h1>
    </div>
    <div id="level-div" data-role="controlgroup" data-type="horizontal" data-mini="true">
        <a class="ui-btn ui-corner-all">Character Level: </a>
        <a onclick="levelMinusClicked();" class="ui-btn ui-corner-all ui-icon-minus ui-btn-icon-notext">-</a>
        <a id="level-value" class="ui-btn ui-corner-all">5</a>
        <a onclick="levelPlusClicked();" class="ui-btn ui-corner-all ui-icon-plus ui-btn-icon-notext">+</a>
    </div>
    <div id="allocation-div">
<%
for (String tag : categories) {
%>
    <div id="<%= tag %>-div" data-role="controlgroup" data-type="horizontal" data-mini="true">
        <a onclick="minusClicked('<%= tag %>');" class="ui-btn ui-corner-all ui-icon-minus ui-btn-icon-notext">-</a>
        <a id="<%= tag %>-value" class="ui-btn ui-corner-all">1</a>
        <a onclick="plusClicked('<%= tag %>');" class="ui-btn ui-corner-all ui-icon-plus ui-btn-icon-notext">+</a>
    </div>
<%
}
%>
    </div>
    <div id="remaining-div" data-role="controlgroup" data-type="horizontal" data-mini="true">
        <a class="ui-btn ui-corner-all">Remaining Points:</a>
        <a id="remaining-value" class="ui-btn ui-corner-all">1</a>
    </div>
</div>
</body>
</html>
