<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="ru.javawebinar.basejava.model.ContactType, java.net.URL" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
        <link rel="stylesheet" href="/css/main.css"/>
        <title>Resumes</title>
    </head>
    <body>
        <div class="wrapper">
        <jsp:include page="fragments/header.jsp"/>
        <main role="main" class="container main">
            <section>
                <div class="container">
                    <a href="resume?action=edit"><button type="button" class="btn btn-outline-success btn-sm">Создать</button></a>
                    <table class="table">
                        <thead class="thead-dark">
                            <tr>
                                <th scope="col">Имя</th>
                                <th scope="col">email</th>
                                <th>Действия</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${resumes}" var="resume">
                                <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume"/>
                                <tr>
                                    <td scope="row"><a href="/resume?uuid=${resume.getUuid()}&action=view">${resume.getFullName()}</a></td>
                                    <td>${resume.getContact(ContactType.EMAIL)}</td>
                                    <td>
                                        <a href="resume?action=edit&uuid=${resume.getUuid()}"><button class="btn btn-outline-primary btn-sm">Редактировать</button></a>
                                        <a href="resume?action=delete&uuid=${resume.getUuid()}"><button class="btn btn-outline-danger btn-sm">Удалить</button></a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
            </section>
        </main>
        <jsp:include page="fragments/footer.jsp"/>
        </div>
    </body>
</html>
