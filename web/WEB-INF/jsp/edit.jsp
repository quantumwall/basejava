<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="ru.javawebinar.basejava.model.ContactType,
         ru.javawebinar.basejava.model.SectionType,
         ru.javawebinar.basejava.model.TextSection,
         ru.javawebinar.basejava.model.ListSection"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
        <link rel="stylesheet" href="/css/main.css"/>
        <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
        <title>Резюме ${resume.fullName}</title>
    </head>
    <body>
        <jsp:include page="fragments/header.jsp"/>
        <main>
            <section>
                <form method="post" action="/resume" enctype="application/x-www-form-urlencoded">
                    <input type="hidden" name="uuid" value="${resume.uuid}">
                    <dl>
                        <dt>Имя:</dt>
                        <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
                    </dl>
                    <h3>Контакты:</h3>
                    <c:forEach var="type" items="${ContactType.values()}">
                        <dl>
                            <dt>${type.title}</dt>
                            <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
                        </dl>
                    </c:forEach>
                    <c:forEach var="type" items="${SectionType.values()}">
                        <c:if test="${type == PERSONAL || type == OBJECTIVE}">
                            <dl>
                                <dt>${type.title}</dt>
                                <dd><input type="text" name="${type.name()}" size=30 value="<%=((TextSection) resume.getSection(type)).getText()%>"></dd>
                            </dl>
                        </c:if>
                    </c:forEach>
                    <hr>
                    <button type="submit">Сохранить</button>
                    <button onclick="window.history.back()">Отменить</button>
                </form>
            </section>    
        </main>

        <jsp:include page="fragments/footer.jsp"/>
    </body>
</html>
