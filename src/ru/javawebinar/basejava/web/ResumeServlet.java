package ru.javawebinar.basejava.web;

import static ru.javawebinar.basejava.model.SectionType.OBJECTIVE;
import static ru.javawebinar.basejava.model.SectionType.QUALIFICATIONS;

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
                  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
                  <title>Resumes</title>
                  </head>
                  <body>\n""");
        String uuid = request.getParameter("uuid");

        if (uuid != null) {
            try {
                var resume = storage.get(uuid);
                sb.append("""
                          <div class="container">
                          <div class="row">
                          <h2>%s</h2>\n""".formatted(resume.getFullName()));
                for (Map.Entry<ContactType, String> pair : resume.getContacts().entrySet()) {
                    sb.append(String.format("<p>%s: %s</p>\n", pair.getKey().getTitle(), pair.getValue()));
                }
                sb.append("</div>\n");
                for (Map.Entry<SectionType, AbstractSection> pair : resume.getSections().entrySet()) {
                    switch (pair.getKey()) {
                        case PERSONAL, OBJECTIVE ->
                            processTextSection(sb, pair);
                        case ACHIEVEMENTS, QUALIFICATIONS ->
                            processListSection(sb, pair);
                    }
                }
            } catch (NotExistStorageException e) {
                sb.append(String.format("<p>Resume %s does not exist</p>", uuid));
            }
        } else {
            var resumes = storage.getAllSorted();
            sb.append("""
                      <div class="container">
                      <table class="table">
                      <thead class="thead-dark">
                      <tr>
                      <th scope="col">uuid</th>
                      <th scope="col">name</th>
                      </thead>
                      <tbody>\n""");
            for (Resume res : resumes) {
                sb.append("""
                          <tr>
                          <td scope="row">%s</td>
                          <td>%s</td>
                          </tr>\n""".formatted(res.getUuid(), res.getFullName()));
            }
            sb.append("""
                      </tbody>
                      </table>
                      </div>\n""");
        }
        sb.append("""
                  </body>
                  </html>\n""");
        try ( PrintWriter out = response.getWriter()) {
            out.println(sb.toString());
        }
    }

    private void processTextSection(StringBuilder sb, Map.Entry<SectionType, AbstractSection> pair) {
        var type = pair.getKey().getTitle();
        var text = ((TextSection) pair.getValue()).getText();
        sb.append("""
                  <div class="row">
                  <h3>%s</h3>
                  <p>%s</p>
                  </div>""".formatted(type, text));
    }

    private void processListSection(StringBuilder sb, Map.Entry<SectionType, AbstractSection> pair) {
        var type = pair.getKey().getTitle();
        var items = ((ListSection) pair.getValue()).getItems();
        sb.append("""
                  <div class="row">
                  <h3>%s</h3>
                  <ul>
                  """.formatted(type));
        for (String item : items) {
            sb.append(String.format("<li>%s</li>\n", item));
        }
        sb.append("</ul>\n</div>");
    }

}
