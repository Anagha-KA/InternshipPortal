# 🎓 Internship Application Portal

A **Java-based Web Application** that allows students to apply for internships and enables the admin to manage all applications efficiently.

---

## 🚀 Features

- 📝 **Student Application Form** — Submit internship applications with details (Name, College, Department, CGPA, Role, Resume Upload, etc.)
- 🔐 **Admin Login** — Secure access for admin to view all applications.
- 📊 **Dashboard View** — Admin can view, filter, and manage student applications.
- 🔍 **Filter by CGPA & Role** — Quickly search for candidates meeting criteria.
- ⬇️ **Download to CSV** — Export all applications for record-keeping.
- 🚪 **Logout Functionality** — Secure session handling.

---

## 🧩 Tech Stack

**Frontend:** HTML, CSS, JavaScript  
**Backend:** Java Servlets, JSP  
**Database:** MySQL  
**Server:** Apache Tomcat 9.0  
**Build Tool:** Maven *(optional if used)*

---

## ⚙️ Installation & Setup

1️⃣ Clone the Repository
```bash
git clone https://github.com/<your-username>/InternshipApplicationPortal.git
cd InternshipApplicationPortal
```
2️⃣ Setup Database
```bash
Open MySQL Workbench and run:

CREATE DATABASE internship_portal;
USE internship_portal;

CREATE TABLE applications (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  college VARCHAR(100),
  department VARCHAR(100),
  cgpa DECIMAL(3,2),
  role VARCHAR(100),
  fileName VARCHAR(255)
);
```
3️⃣ Configure Database Connection
```bash
Open:

src/main/java/com/portal/DBConnection.java

Update your database credentials:

String url = "jdbc:mysql://localhost:3306/internship_portal";
String user = "root";
String password = "your_password";
```
4️⃣ Deploy on Tomcat
```bash
Open Eclipse
Right-click the project → Run As → Run on Server
Select Apache Tomcat 9.0

Visit in browser:
http://localhost:8080/InternshipPortal/
```

## 👨‍💼 Admin Credentials

Username: admin  
Password: admin123

## 📁 Project Structure
```bash
InternshipPortal/
├── src/
│   └── main/java/com/portal/
│       ├── ApplicationServlet.java
│       ├── AdminLogin.java
│       ├── ViewApplicationsServlet.java
│       ├── LogoutServlet.java
│       ├── DownloadCSV.java
│       └── DBConnection.java
│
├── WebContent/
│   ├── forms/
│   ├── css/
│   ├── images/
│   └── success.html, error.html, etc.
│
├── web.xml
├── README.md
└── Dockerfile
```

## 🧠 Future Enhancements
```bash
✉️ Email notifications after successful application submission
👥 Role-based admin access
📄 Resume preview and verification system
☁️ Cloud database integration
```
## 💬 Author
👩‍💻 Anagha K A
