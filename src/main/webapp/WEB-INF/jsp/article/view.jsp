<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<base href="${pageContext.request.contextPath }/" />
<title>게시판</title>
</head>
<body>
	<h2>글 보기</h2>
	<c:if test="${param.mode=='FAILURE' }">
		<p style="color: red;">권한이 없습니다.</p>
	</c:if>
	<p>
		<span><a href="./app/article/list">글 목록</a></span> | <span><a href="./app/article/updateForm?articleId=${article.articleId }">글 수정</a></span> | <span><a href="./app/article/delete?articleId=${article.articleId }">글 삭제</a></span>
	</p>
	<hr />
	<p>
		<span>${article.articleId }</span> | <span style="font-weight: bold;">${article.title }</span>
	</p>
	<p>
		<span>${article.cdate }</span> | <span>${article.name }</span>
	</p>
	<hr />
	<p>${article.contentHtml }</p>
	<hr />
</body>
</html>