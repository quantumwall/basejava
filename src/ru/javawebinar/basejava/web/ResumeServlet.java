package ru.javawebinar.basejava.web;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Company;
import ru.javawebinar.basejava.model.CompanySection;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Link;
import ru.javawebinar.basejava.model.ListSection;
import ru.javawebinar.basejava.model.Period;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.model.TextSection;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.DateUtil;

@WebServlet("/resume")
public class ResumeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete" -> {
                try {
                    storage.delete(uuid);
                } catch (NotExistStorageException e) {
                    showException(e, request, response);
                } finally {
                    response.sendRedirect("/resume");
                    return;
                }
            }
            case "edit", "view" -> {
                resume = uuid != null ? storage.get(uuid) : new Resume();
            }
            default ->
                throw new IllegalArgumentException("Action " + action + " is not supported");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher("edit".equals(action) ? "/WEB-INF/jsp/edit.jsp" : "/WEB-INF/jsp/view.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        var resume = !uuid.isBlank() ? storage.get(uuid) : new Resume(fullName);
        resume.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && !value.isBlank()) {
                resume.addContact(type, value.trim());
            } else {
                resume.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && !value.isBlank()) {
                switch (type) {
                    case PERSONAL, OBJECTIVE -> {
                        resume.addSection(type, new TextSection(value.trim()));
                    }
                    case ACHIEVEMENTS, QUALIFICATIONS -> {
                        var items = new ArrayList<String>();
                        value.trim().lines().forEach(s -> {
                            var item = s.replaceAll("\n|\r\n", "");
                            if (!item.isBlank()) {
                                items.add(item);
                            }

                        });
                        resume.addSection(type, new ListSection(items));
                    }
                    default -> {
                        var companies = new ArrayList<Company>();
                        String[] requestCompanies = request.getParameterValues(type.name());
                        String[] requestUrl = request.getParameterValues(type.name() + "url");
                        for (int i = 0; i < requestCompanies.length; i++) {
                            var basename = type.name() + i;
                            if (!requestCompanies[i].trim().isBlank()) {
                                String[] requestEntryDate = request.getParameterValues(basename + "entryDate");
                                String[] requestExitDate = request.getParameterValues(basename + "exitDate");
                                String[] requestTitle = request.getParameterValues(basename + "title");
                                String[] requestDescription = request.getParameterValues(basename + "description");
                                var link = new Link(requestCompanies[i].trim(), !requestUrl[i].isBlank() ? requestUrl[i].trim() : null);
                                var periods = new ArrayList<Period>();
                                for (int j = 0; j < requestEntryDate.length; j++) {
                                    periods.add(new Period(requestTitle[j], requestDescription[j], DateUtil.parse(requestEntryDate[j]), DateUtil.parse(requestExitDate[j])));
                                }
                                companies.add(new Company(link, periods));
                            }
                        }
                        resume.addSection(type, new CompanySection(companies));
                    }
                }
            } else {
                resume.getSections().remove(type);
            }
        }
        if (uuid.isBlank()) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }
        response.sendRedirect("resume");
    }

    private void showException(Exception exception, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("message", exception.toString());
        request.getRequestDispatcher("/WEB-INF/jsp/exception.jsp").forward(request, response);
    }

}
