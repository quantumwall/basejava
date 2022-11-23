<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="ru.javawebinar.basejava.model.*,
         java.io.IOException,
         java.time.format.DateTimeFormatter,
         java.time.LocalDate"
         %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%!
    private void showSection(SectionType type, AbstractSection section, JspWriter out) throws IOException {
        switch(type) {
            case PERSONAL:
            case OBJECTIVE:
                showTextSection(section, out);
                break;
            case ACHIEVEMENTS:
            case QUALIFICATIONS:
                showListSection(section, out);
                break;
            case EXPERIENCE:
            case EDUCATION:
                showCompanySection(section, out);
                break;
        }
    }
%>
<%!
    private void showTextSection(AbstractSection section, JspWriter out) throws IOException {
        out.println("<p>" + ((TextSection) section).getText() + "</p>\n");
    } 
%>
<%!
    private void showListSection(AbstractSection section, JspWriter out) throws IOException {
        var sb = new StringBuilder();
        sb.append("<ul>\n");
        for(String str : ((ListSection) section).getItems()) {
            sb.append("<li>").append(str).append("</li>\n");
        }
        sb.append("</ul>\n");
        out.println(sb.toString());
    }
%>
<%!
    private void showCompanySection(AbstractSection section, JspWriter out) throws IOException {
        var sb = new StringBuilder();
        var dateFormatter = DateTimeFormatter.ofPattern("MM/yyyy");
        for(Company company : ((CompanySection) section).getCompanies()) {
            String url = company.getLink().getUrl();
            String name = company.getLink().getName();
            sb.append("<div class=\"container\">\n")
              .append("<div class=\"row\">\n")
              .append("<div class=\"col-sm-3\">\n")
              .append("</div>\n")
              .append("<div class=\"col\">\n");
            if(url != null) {
                sb.append(String.format("<p><a href='%s'>%s</a></p>\n", url, name));
            } else {
                sb.append(String.format("<p>%s</p>\n", name));
            }               
            sb.append("</div>\n")
              .append("</div>\n");
            for(Period period : company.getPeriods()) {
                var description = period.getDescription();
                var exitDate = period.getExitDate();
                sb.append("<div class=\"row\">\n")
                  .append("<div class=\"col-sm-3\">\n")
                  .append(String.format("<p>%s - %s</p>\n", dateFormatter.format(period.getEntryDate()),
                                exitDate.isAfter(LocalDate.now()) ? "сегодня" : dateFormatter.format(exitDate)))
                  .append("</div>\n")
                  .append("<div class=\"col\">\n")
                  .append(String.format("<p>%s</p>\n<p>%s</p>\n", period.getTitle(), description != null ? description : ""))
                  .append("</div>\n")
                  .append("</div>\n");
            }
            sb.append("</div>\n")
              .append("</div>\n");
        }
        out.print(sb.toString());
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/css/main.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
        <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
        <title>Резюме ${resume.fullName}</title>
    </head>
    <body>
        <div class="wrapper">
        <jsp:include page="fragments/header.jsp"/>
        <main class="container main">
            <section class="row">
                <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
                <div class="col-sm">
                    <p>
                        <c:forEach var="contactEntry" items="${resume.contacts}">
                            <jsp:useBean id="contactEntry"
                                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
                            <c:if test="${contactEntry.getValue() != null}">
                                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
                            </c:if>    
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
        </main>
        <jsp:include page="fragments/footer.jsp"/>
        </div>
    </body>
</html>
