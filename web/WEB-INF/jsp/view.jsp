<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%@ page import="ru.javawebinar.basejava.model.SectionType,
         ru.javawebinar.basejava.model.AbstractSection,
         ru.javawebinar.basejava.model.TextSection,
         ru.javawebinar.basejava.model.ListSection,
         java.io.IOException" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%!
    void showSection(SectionType type, AbstractSection section, JspWriter out) throws IOException {
        switch(type) {
            case PERSONAL:
            case OBJECTIVE:
                out.println("<p>" + ((TextSection) section).getText() + "</p>\n");
                break;
            case ACHIEVEMENTS:
            case QUALIFICATIONS:
                var sb = new StringBuilder();
                sb.append("<ul>\n");
                for(String str : ((ListSection) section).getItems()) {
                    sb.append("<li>").append(str).append("</li>\n");
                }
                sb.append("</ul>\n");
                out.println(sb.toString());
                break;
        }
    }
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/main.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
        <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
        <title>Резюме ${resume.fullName}</title>
    </head>
    <body>
        <jsp:include page="fragments/header.jsp"/>
        <section class="row">
            <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
            <div class="col-sm">
                <p>
                    <c:forEach var="contactEntry" items="${resume.contacts}">
                        <jsp:useBean id="contactEntry"
                                     type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
                        <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
                    </c:forEach>
                <p>
            </div>
        </section>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <section class="row">
                <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.AbstractSection>"/>
                <c:if test="${sectionEntry.getValue() != null}">
                    <h3>${sectionEntry.getKey().getTitle()}</h3>
                    <% showSection(sectionEntry.getKey(), sectionEntry.getValue(), out); %>
                </c:if>
            </section>
        </c:forEach>
        <jsp:include page="fragments/footer.jsp"/>
    </body>
</html>
