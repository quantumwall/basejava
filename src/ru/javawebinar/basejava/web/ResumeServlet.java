package ru.javawebinar.basejava.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.AbstractSection;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.ListSection;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.model.TextSection;
import ru.javawebinar.basejava.storage.Storage;

@WebServlet(name = "ResumeServlet", urlPatterns = {"/ResumeServlet"})
public class ResumeServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ResumeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ResumeServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Storage storage = Config.get().getStorage();
        var sb = new StringBuilder();
        sb.append("""
                  <!DOCTYPE html>
                  <html>
                  <head>
                  <meta charset="UTF-8">
                  <title>Resumes</title>
                  </head>
                  <body>\n""");
        String uuid = request.getParameter("uuid");
        Resume resume = null;
        if (uuid != null) {
            try {
                resume = storage.get(uuid);
            } catch (NotExistStorageException e) {
                sb.append(String.format("<p>Resume %s does not exist</p>", uuid));
            }
        }
        if (resume != null) {
            sb.append("""
                      <h2>%s</h2>
                      <h2>%s</h2>\n""".formatted(uuid, resume.getFullName()));
            sb.append("<div>\n");
            for (Map.Entry<ContactType, String> pair : resume.getContacts().entrySet()) {
                sb.append(String.format("<p>%s: %s</p>\n", pair.getKey().getTitle(), pair.getValue()));
            }
            sb.append("</div>\n<div>\n");
            for (Map.Entry<SectionType, AbstractSection> pair : resume.getSections().entrySet()) {
                switch (pair.getKey()) {
                    case PERSONAL, OBJECTIVE ->
                        processTextSection(sb, pair);
                    case ACHIEVEMENTS, QUALIFICATIONS ->
                        processListSection(sb, pair);
                }
            }

        } else {
            var resumes = storage.getAllSorted();
            sb.append("""
                      <div>
                      <table>""");
            for(Resume res : resumes) {
                sb.append("""
                          <tr>
                          <td>%s</td>
                          <td>%s</td>
                          </tr>""".formatted(res.getUuid(), res.getFullName()));
            }
            sb.append("""
                      </table>
                      </div>""");
        }
        sb.append("""
                  </body>
                  </html>""");
        try(PrintWriter out = response.getWriter()) {
            out.println(sb.toString());
        }
    }

    private void processTextSection(StringBuilder sb, Map.Entry<SectionType, AbstractSection> pair) {
        var type = pair.getKey().getTitle();
        var text = ((TextSection) pair.getValue()).getText();
        sb.append("""
                  <h3>%s</h3>
                  <p>%s</p>\n""".formatted(type, text));
    }

    private void processListSection(StringBuilder sb, Map.Entry<SectionType, AbstractSection> pair) {
        var type = pair.getKey().getTitle();
        var items = ((ListSection) pair.getValue()).getItems();
        sb.append("""
                  <h3>%s</h3>
                  <ul>
                  """.formatted(type));
        for (String item : items) {
            sb.append(String.format("<li>%s</li>\n", item));
        }
        sb.append("</ul>\n");
    }

}
