<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="ru.javawebinar.basejava.model.ContactType,
         ru.javawebinar.basejava.model.SectionType,
         ru.javawebinar.basejava.model.TextSection,
         ru.javawebinar.basejava.model.ListSection,
         java.time.YearMonth,
         ru.javawebinar.basejava.util.DateUtil"%>
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
        <div class="wrapper">
            <jsp:include page="fragments/header.jsp"/>
            <main class="container main">
                <section>
                    <form method="post" action="/resume" enctype="application/x-www-form-urlencoded">
                        <input type="hidden" name="uuid" value="${resume.uuid}">

                        <article>
                            <h2>ФИО</h2>
                            <div class="form-group">
                                <input class="form-control" type="text" name="fullName" value="${resume.fullName}" required>
                            </div>
                        </article>

                        <article>
                            <h2>Контакты:</h2>
                            <div class="form-group">
                                <c:forEach var="type" items="${ContactType.values()}">
                                    <c:choose>
                                        <c:when test="${type == ContactType.EMAIL}">
                                            <div class="mb-3">
                                                <input class="form-control" type="email" name="${type.name()}" value="${resume.getContact(type)}" placeholder="${type.title}">
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="mb-3">
                                                <input class="form-control" type="text" name="${type.name()}" value="${resume.getContact(type)}" placeholder="${type.title}">
                                            </div>
                                        </c:otherwise>
                                    </c:choose>

                                </c:forEach>
                            </div>
                        </article>

                        <article>
                            <h2>Секции</h2>
                            <c:forEach var="type" items="${SectionType.values()}">
                                <h3>${type.title}</h3>
                                <c:choose>
                                    <c:when test="${type == SectionType.PERSONAL || type == SectionType.OBJECTIVE}">
                                        <div class="form-group">
                                            <div class="mb-3">
                                                <input class="form-control" type="text" name="${type.name()}" size="30" value="${resume.getSection(type)}"/>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:when test="${type == SectionType.ACHIEVEMENTS || type == SectionType.QUALIFICATIONS}">
                                        <div class="form-group">
                                            <div class="mb-3">
                                                <textarea class="form-control" name="${type.name()}" rows="10" cols="100"><c:forEach var="item" items="${resume.getSection(type).getItems()}"><c:out value="${item}&#10;" escapeXml="false"/></c:forEach></textarea>
                                                </div>
                                            </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="form-group">
                                            <c:forEach var="company" items="${resume.getSection(type).getCompanies()}" varStatus="count">
                                                <div class="mb-3">
                                                    <input class="form-control" type="text" name="${type}" value="${company.getLink().getName()}" placeholder="Название организации">
                                                </div>
                                                <div class="mb-3">
                                                    <input class="form-control" type="url" name="${type}url" value="${company.getLink().getUrl()}" placeholder="Сайт организации">
                                                </div>
                                                <c:forEach var="period" items="${company.getPeriods()}">
                                                    <div class="row">
                                                        <div class="col-1"></div>
                                                        <div class="col">
                                                            <div class="mb-3">
                                                                <div class="row">
                                                                    <div class="col-sm-2">
                                                                        <input class="form-control" type="text" name="${type}${count.index}entryDate" value="${DateUtil.format(period.getEntryDate())}" placeholder="Начало" onfocus="(this.type = 'month')" onblur="(this.type = 'text')">
                                                                    </div>
                                                                    <div class="col-sm-2">
                                                                        <input class="form-control" type="text" name="${type}${count.index}exitDate" value="${DateUtil.format(period.getExitDate())}" placeholder="Окончание" onfocus="(this.type = 'month')" onblur="(this.type = 'text')">
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="mb-3">
                                                                <input class="form-control" type="text" name="${type}${count.index}title" value="${period.getTitle()}" placeholder="Заголовок">
                                                            </div>
                                                            <div class="mb-3">
                                                                <textarea class="form-control" type="text" name="${type}${count.index}description" placeholder="Описание">${period.getDescription()}</textarea>
                                                            </div> 
                                                        </div>
                                                    </div>
                                                </c:forEach>
                                            </c:forEach>
                                        </div>
                                        <hr>
                                        <div id="${type}controlButtons" class="form-group">
                                            <button id="${type}addPeriod" class="btn btn-outline-warning btn-sm" type="button" onclick="addPeriod(this, '${type}');">Добавить период</button>
                                            <button id="${type}addCompany" class="btn btn-outline-info btn-sm" type="button" onclick="addCompany(this, '${type}');">Добавить организацию</button>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </article>
                        <hr>
                        <button class="btn btn-outline-success btn-sm" type="submit">Сохранить</button>
                        <a href="/resume"><button class="btn btn-outline-danger btn-sm" type="button">Отменить</button></a>
                    </form>
                </section>    
            </main>
            <jsp:include page="fragments/footer.jsp"/>
        </div>
        <script src="/js/insert.js"></script>
    </body>
</html>
