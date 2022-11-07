package ru.javawebinar.basejava.model;

import java.time.Month;
import java.util.ArrayList;
import ru.javawebinar.basejava.util.DateUtil;

public class ResumeTestData {

    public static void main(String[] args) {
        var resume = getResume("uuid1", "Григорий Кислин");
        System.out.println(resume);
        for (ContactType type : ContactType.values()) {
            System.out.printf("%s: %s\n", type.getTitle(), resume.getContact(type));
        }
        for (SectionType type : SectionType.values()) {
            System.out.printf("%s: %s\n", type.getTitle(), resume.getSection(type));
        }
    }

    public static Resume getResume(String uuid, String fullName) {
        var resume = new Resume(uuid, fullName);
        resume.addContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "skype:grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.addContact(ContactType.GITHUB, "https://github.com/gkislin");
        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume.addContact(ContactType.HOMEPAGE, "http://gkislin.ru/");
//        var objectiveSection = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
//        var personalSection = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
//        var achievementsList = new ArrayList<String>();
//        achievementsList.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
//        achievementsList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.");
//        achievementsList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
//        achievementsList.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
//        achievementsList.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
//        achievementsList.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
//        achievementsList.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
//        var achievementsSection = new ListSection(achievementsList);
//        var qualificationsList = new ArrayList<String>();
//        qualificationsList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
//        qualificationsList.add("");
//        qualificationsList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
//        qualificationsList.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
//        qualificationsList.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
//        qualificationsList.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
//        qualificationsList.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
//        qualificationsList.add("Python: Django.");
//        qualificationsList.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
//        qualificationsList.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
//        qualificationsList.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
//        qualificationsList.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
//        qualificationsList.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer");
//        qualificationsList.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
//        qualificationsList.add("Родной русский, английский \"upper intermediate\"");
//        var qualificationSection = new ListSection(qualificationsList);
//        var jobsList = new ArrayList<Company>();
//
//        {
//            var link = new Link("Java online projects", "http://javaops.ru/");
//            var periods = new ArrayList<Period>();
//            var companyPeriod1 = new Period("Автор проекта", "Создание, организация и проведение Java онлайн проектов и стажировок.", DateUtil.of(2013, Month.OCTOBER), null);
//            periods.add(companyPeriod1);
//            var company = new Company(link, periods);
//            jobsList.add(company);
//        }
//        {
//            var link = new Link("Wrike", "https://www.wrike.com/");
//            var periods = new ArrayList<Period>();
//            var companyPeriod1 = new Period("Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.", DateUtil.of(2014, Month.OCTOBER), DateUtil.of(2016, Month.JANUARY));
//            periods.add(companyPeriod1);
//            var company = new Company(link, periods);
//            jobsList.add(company);
//        }
//        {
//            var link = new Link("RIT Center", null);
//            var periods = new ArrayList<Period>();
//            var companyPeriod1 = new Period("Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python", DateUtil.of(2012, Month.APRIL), DateUtil.of(2014, Month.OCTOBER));
//            periods.add(companyPeriod1);
//            var company = new Company(link, periods);
//            jobsList.add(company);
//        }
//        {
//            var link = new Link("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/");
//            var periods = new ArrayList<Period>();
//            var companyPeriod1 = new Period("Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.", DateUtil.of(2010, Month.DECEMBER), DateUtil.of(2012, Month.APRIL));
//            periods.add(companyPeriod1);
//            var company = new Company(link, periods);
//            jobsList.add(company);
//        }
//        {
//            var link = new Link("Yota", "https://www.yota.ru/");
//            var periods = new ArrayList<Period>();
//            var companyPeriod1 = new Period("Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)", DateUtil.of(2008, Month.JUNE), DateUtil.of(2010, Month.DECEMBER));
//            periods.add(companyPeriod1);
//            var company = new Company(link, periods);
//            jobsList.add(company);
//        }
//        {
//            var link = new Link("Enkata", "http://enkata.com/");
//            var periods = new ArrayList<Period>();
//            var companyPeriod1 = new Period("Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).", DateUtil.of(2007, Month.MARCH), DateUtil.of(2008, Month.JUNE));
//            periods.add(companyPeriod1);
//            var company = new Company(link, periods);
//            jobsList.add(company);
//        }
//        {
//            var link = new Link("Siemens AG", "https://www.siemens.com/ru/ru/home.html");
//            var periods = new ArrayList<Period>();
//            var companyPeriod1 = new Period("Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).", DateUtil.of(2005, Month.JANUARY), DateUtil.of(2007, Month.JULY));
//            periods.add(companyPeriod1);
//            var company = new Company(link, periods);
//            jobsList.add(company);
//        }
//        {
//            var link = new Link("Alcatel", "http://www.alcatel.ru/");
//            var periods = new ArrayList<Period>();
//            var companyPeriod1 = new Period("Инженер по аппаратному и программному тестированию", "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).", DateUtil.of(1997, Month.SEPTEMBER), DateUtil.of(2005, Month.JANUARY));
//            periods.add(companyPeriod1);
//            var company = new Company(link, periods);
//            jobsList.add(company);
//        }
//        var experienceSection = new CompanySection(jobsList);
//        var educationList = new ArrayList<Company>();
//
//        {
//            var link = new Link("Coursera", "https://www.coursera.org/course/progfun");
//            var periods = new ArrayList<Period>();
//            var companyPeriod1 = new Period("Functional Programming Principles in Scala' by Martin Odersky", null, DateUtil.of(2013, Month.MARCH), DateUtil.of(2013, Month.MAY));
//            periods.add(companyPeriod1);
//            var company = new Company(link, periods);
//            educationList.add(company);
//        }
//        {
//            var link = new Link("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366");
//            var periods = new ArrayList<Period>();
//            var companyPeriod1 = new Period("Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'", null, DateUtil.of(2011, Month.MARCH), DateUtil.of(2011, Month.APRIL));
//            periods.add(companyPeriod1);
//            var company = new Company(link, periods);
//            educationList.add(company);
//        }
//        {
//            var link = new Link("Siemens AG", "http://www.siemens.ru/");
//            var periods = new ArrayList<Period>();
//            var companyPeriod1 = new Period("3 месяца обучения мобильным IN сетям (Берлин)", null, DateUtil.of(2005, Month.JANUARY), DateUtil.of(2005, Month.MARCH));
//            periods.add(companyPeriod1);
//            var company = new Company(link, periods);
//            educationList.add(company);
//        }
//        {
//            var link = new Link("Alcatel", "http://www.alcatel.ru/");
//            var periods = new ArrayList<Period>();
//            var companyPeriod1 = new Period("3 месяца обучения мобильным IN сетям (Берлин)", null, DateUtil.of(1997, Month.SEPTEMBER), DateUtil.of(1998, Month.MARCH));
//            periods.add(companyPeriod1);
//            var company = new Company(link, periods);
//            educationList.add(company);
//        }
//        {
//            var link = new Link("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/");
//            var periods = new ArrayList<Period>();
//            var companyPeriod1 = new Period("Аспирантура (программист С, С++)", null, DateUtil.of(1993, Month.SEPTEMBER), DateUtil.of(1996, Month.JULY));
//            var companyPeriod2 = new Period("Инженер (программист Fortran, C)", null, DateUtil.of(1987, Month.SEPTEMBER), DateUtil.of(1993, Month.JULY));
//            periods.add(companyPeriod1);
//            periods.add(companyPeriod2);
//            var company = new Company(link, periods);
//            educationList.add(company);
//        }
//        {
//            var link = new Link("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/");
//            var periods = new ArrayList<Period>();
//            var companyPeriod1 = new Period("Закончил с отличием", null, DateUtil.of(1984, Month.SEPTEMBER), DateUtil.of(1987, Month.JULY));
//            periods.add(companyPeriod1);
//            var company = new Company(link, periods);
//            educationList.add(company);
//        }
//        var educationSection = new CompanySection(educationList);
//        resume.addSection(SectionType.OBJECTIVE, objectiveSection);
//        resume.addSection(SectionType.PERSONAL, personalSection);
//        resume.addSection(SectionType.ACHIEVEMENTS, achievementsSection);
//        resume.addSection(SectionType.QUALIFICATIONS, qualificationSection);
//        resume.addSection(SectionType.EXPERIENCE, experienceSection);
//        resume.addSection(SectionType.EDUCATION, educationSection);
        return resume;
    }
}
