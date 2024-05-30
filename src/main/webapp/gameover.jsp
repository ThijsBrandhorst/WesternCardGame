<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="actions.GameAction"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Medieval Cards - Game Over</title>
<link href="css/gameover.css" rel="stylesheet">
<link href='https://fonts.googleapis.com/css?family=MedievalSharp'
	rel='stylesheet'>

</head>
<body>
	<div class="game-over">
		<h1>Game Over</h1>
		<p>
			Your Last Score:
			<%=GameAction.getGame().getScore()%></p>
		<s:a action="restart">Restart Game</s:a>
		<s:a action="mainMenu">Main Menu</s:a>
	</div>
</body>
</html>
