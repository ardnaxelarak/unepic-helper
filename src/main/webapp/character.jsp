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
    <link type="text/css" rel="stylesheet" href="stylesheets/main.css" />
    <script src="webjars/jquery/2.1.4/jquery.min.js"> </script>
    <script src="webjars/jquery-mobile/1.4.5/jquery.mobile.min.js"> </script>
    <script src="character-build.js"> </script>
</head>

<body>
<div data-role="page" class="type-interior">
    <div data-role="header">
        <h1>Character Build</h1>
    </div>
    <div class="level_div" data-role="controlgroup" data-type="horizontal" data-mini="true">
        <a class="ui-btn ui-corner-all">Character Level: </a>
        <a onclick="levelMinusClicked();" class="ui-btn ui-corner-all ui-icon-minus ui-btn-icon-notext">-</a>
        <a class="level_value ui-btn ui-corner-all">5</a>
        <a onclick="levelPlusClicked();" class="ui-btn ui-corner-all ui-icon-plus ui-btn-icon-notext">+</a>
    </div>
    <div class="allocation_div">
        <table>
            <thead>
                <tr>
                    <th>Skill</th>
                    <th>Ranks</th>
                    <th class="padded">Benefits</th>
                </tr>
            </thead>
            <tbody class="allocation_body">
                <tr class="allocation_row template">
                    <th class="right"><span class="display_name"></span>: </th>
                    <td class="no-pad">
                        <div class="control_div no-pad" data-role="controlgroup" data-type="horizontal" data-mini="true">
                            <a class="minus_btn ui-btn ui-corner-all ui-icon-minus ui-btn-icon-notext">-</a>
                            <a class="skill_value ui-btn ui-corner-all">1</a>
                            <a class="plus_btn ui-btn ui-corner-all ui-icon-plus ui-btn-icon-notext">+</a>
                        </div>
                    </td>
                    <td class="padded">
                        <div class="benefits_div">
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <div data-role="controlgroup" data-type="horizontal" data-mini="true">
        <a class="ui-btn ui-corner-all">Remaining Points:</a>
        <a class="remaining_points ui-btn ui-corner-all">1</a>
    </div>
</div>
</body>
</html>
