<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="ru.javawebinar.basejava.model.ContactType" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
        <title>Resumes</title>
    </head>
    <body>
        <section>
            <div class="container">
                <table class="table">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col">Имя</th>
                            <th scope="col">email</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${resumes}" var="resume">
                            <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume"/>
                            <tr>
                                <td scope="row"><a href="resume?uuid=${resume.getUuid()}">${resume.getFullName()}</a></td>
                                <td>${resume.getContact(ContactType.EMAIL)}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
        </section>
    </body>
</html>
