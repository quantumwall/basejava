package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.time.Month;

public class ResumeTestData {

    public static void main(String[] args) {
        var resume = new Resume("Григорий Кислин");
        resume.addContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "skype:grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.addContact(ContactType.GITHUB, "https://github.com/gkislin");
        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume.addContact(ContactType.HOMEPAGE, "http://gkislin.ru/");
        resume.addSection(SectionType.OBJECTIVE, new TextSection(SectionType.OBJECTIVE,
                "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.addSection(SectionType.PERSONAL, new TextSection(SectionType.PERSONAL,
                "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        {
            var achievementSection = new ListSection(SectionType.ACHIEVEMENTS);
            achievementSection.addItem("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
            achievementSection.addItem("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.");
            achievementSection.addItem("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
            achievementSection.addItem("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
            achievementSection.addItem("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
            achievementSection.addItem("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
            achievementSection.addItem("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
            resume.addSection(SectionType.ACHIEVEMENTS, achievementSection);
        }

        {
            var qualificationSection = new ListSection(SectionType.QUALIFICATIONS);
            qualificationSection.addItem("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
            qualificationSection.addItem("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
            qualificationSection.addItem("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
            qualificationSection.addItem("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
            qualificationSection.addItem("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
            qualificationSection.addItem("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
            qualificationSection.addItem("Python: Django.");
            qualificationSection.addItem("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
            qualificationSection.addItem("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
            qualificationSection.addItem("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
            qualificationSection.addItem("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
            qualificationSection.addItem("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer");
            qualificationSection.addItem("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
            qualificationSection.addItem("Родной русский, английский \"upper intermediate\"");
            resume.addSection(SectionType.QUALIFICATIONS, qualificationSection);
        }

        {
            var experienceSection = new CompanySection(SectionType.EXPERIENCE);
            var company1 = new Company("Java online projects");
            company1.setWebsite("http://javaops.ru/");
            var company1Period = new Period(LocalDate.of(2013, Month.OCTOBER, 1), null);
            company1Period.setTitle("Автор проекта");
            company1Period.setDescription("Создание, организация и проведение Java онлайн проектов и стажировок.");
            company1.addPeriod(company1Period);
            experienceSection.addCompany(company1);
            var company2 = new Company("Wrike");
            company2.setWebsite("https://www.wrike.com/");
            var company2Period = new Period(LocalDate.of(2014, Month.OCTOBER, 1), LocalDate.of(2016, Month.JANUARY, 1));
            company2Period.setTitle("Старший разработчик (backend)");
            company2Period.setDescription("Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
            company2.addPeriod(company2Period);
            experienceSection.addCompany(company2);
            var company3 = new Company("RIT Center");
            var company3Period = new Period(LocalDate.of(2012, Month.APRIL, 1), LocalDate.of(2014, Month.OCTOBER, 1));
            company3Period.setTitle("Java архитектор");
            company3Period.setDescription("Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
            company3.addPeriod(company3Period);
            experienceSection.addCompany(company3);
            var company4 = new Company("Luxoft (Deutsche Bank)");
            company4.setWebsite("http://www.luxoft.ru/");
            var company4Period = new Period(LocalDate.of(2010, Month.DECEMBER, 1), LocalDate.of(2012, Month.APRIL, 1));
            company4Period.setTitle("Ведущий программист");
            company4Period.setDescription("Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");
            company4.addPeriod(company4Period);
            experienceSection.addCompany(company4);
            var company5 = new Company("Yota");
            company5.setWebsite("https://www.yota.ru/");
            var company5Period = new Period(LocalDate.of(2008, Month.JUNE, 1), LocalDate.of(2010, Month.DECEMBER, 1));
            company5Period.setTitle("Ведущий специалист");
            company5Period.setDescription("Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)");
            company5.addPeriod(company5Period);
            experienceSection.addCompany(company5);
            var company6 = new Company("Enkata");
            company6.setWebsite("http://enkata.com/");
            var company6Period = new Period(LocalDate.of(2007, Month.MARCH, 1), LocalDate.of(2008, Month.JUNE, 1));
            company6Period.setTitle("Разработчик ПО");
            company6Period.setDescription("Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).");
            company6.addPeriod(company6Period);
            experienceSection.addCompany(company6);
            var company7 = new Company("Siemens AG");
            company7.setWebsite("https://www.siemens.com/ru/ru/home.html");
            var company7Period = new Period(LocalDate.of(2005, Month.JANUARY, 1), LocalDate.of(2007, Month.JULY, 1));
            company7Period.setTitle("Разработчик ПО");
            company7Period.setDescription("Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");
            company7.addPeriod(company7Period);
            experienceSection.addCompany(company7);
            var company8 = new Company("Alcatel");
            company8.setWebsite("http://www.alcatel.ru/");
            var company8Period = new Period(LocalDate.of(1997, Month.SEPTEMBER, 1), LocalDate.of(2005, Month.JANUARY, 1));
            company8Period.setTitle("Инженер по аппаратному и программному тестированию");
            company8Period.setDescription("Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");
            company8.addPeriod(company8Period);
            experienceSection.addCompany(company8);
            resume.addSection(SectionType.EXPERIENCE, experienceSection);
        }

        {
            var educationSection = new CompanySection(SectionType.EDUCATION);
            var company1 = new Company("Coursera");
            company1.setWebsite("https://www.coursera.org/course/progfun");
            var company1Period = new Period(LocalDate.of(2013, Month.MARCH, 1), LocalDate.of(2013, Month.MAY, 1));
            company1Period.setTitle("Functional Programming Principles in Scala' by Martin Odersky");
            company1.addPeriod(company1Period);
            educationSection.addCompany(company1);
            var company2 = new Company("Luxoft");
            company2.setWebsite("http://www.luxoft-training.ru/training/catalog/course.html?ID=22366");
            var company2Period = new Period(LocalDate.of(2011, Month.MARCH, 1), LocalDate.of(2011, Month.APRIL, 1));
            company2Period.setTitle("Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'");
            company2.addPeriod(company2Period);
            educationSection.addCompany(company2);
            var company3 = new Company("Siemens AG");
            company3.setWebsite("http://www.siemens.ru/");
            var company3Period = new Period(LocalDate.of(2005, Month.JANUARY, 1), LocalDate.of(2005, Month.MARCH, 1));
            company3Period.setTitle("3 месяца обучения мобильным IN сетям (Берлин)");
            company3.addPeriod(company3Period);
            educationSection.addCompany(company3);
            var company4 = new Company("Alcatel");
            company4.setWebsite("http://www.alcatel.ru/");
            var company4Period = new Period(LocalDate.of(1997, Month.SEPTEMBER, 1), LocalDate.of(1998, Month.MARCH, 1));
            company4Period.setTitle("6 месяцев обучения цифровым телефонным сетям (Москва)");
            company4.addPeriod(company4Period);
            educationSection.addCompany(company4);
            var company5 = new Company("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики");
            company5.setWebsite("http://www.ifmo.ru/");
            var company5Period1 = new Period(LocalDate.of(1993, Month.SEPTEMBER, 1), LocalDate.of(1996, Month.JULY, 1));
            company5Period1.setTitle("Аспирантура (программист С, С++)");
            company5.addPeriod(company5Period1);
            var company5Period2 = new Period(LocalDate.of(1987, Month.SEPTEMBER, 1), LocalDate.of(1993, Month.JULY, 1));
            company5Period2.setTitle("Инженер (программист Fortran, C)");
            company5.addPeriod(company5Period2);
            educationSection.addCompany(company5);
            var company6 = new Company("Заочная физико-техническая школа при МФТИ");
            company6.setWebsite("http://www.school.mipt.ru/");
            var company6Period = new Period(LocalDate.of(1984, Month.SEPTEMBER, 1), LocalDate.of(1987, Month.JULY, 1));
            company6Period.setTitle("Закончил с отличием");
            company6.addPeriod(company6Period);
            educationSection.addCompany(company6);
            resume.addSection(SectionType.EDUCATION, educationSection);
        }
        resume.display();
    }
}
