📌 Internship Application Portal
An end-to-end Internship Application Management System built using Java (Servlets), HTML/CSS, and MySQL. This web-based system allows students to apply for internships and enables administrators to manage and filter applications through a secure admin panel.
🚀 Features
📝 Student Application Form with resume upload
🔒 Admin Login System with session handling
📊 Admin Dashboard to:
View submitted applications
Filter by CGPA and Role
Download all data as a CSV file
📂 Resume files are stored on the server
📅 Tracks application submission date
✅ Input validation for cleaner data and protection against SQL injection
🎨 Fully responsive and styled with custom CSS
💻 Tech Stack
Layer	Technology
Frontend	HTML, CSS
Backend	Java Servlets
Database	MySQL
Server	Apache Tomcat
Tools Used	Eclipse/VS Code, Git, GitHub
📁 Folder Structure
InternshipApplicationPortal/
│
├── backend/
│   ├── ApplicationServlet.java
│   ├── AdminLoginServlet.java
│   ├── ViewApplicationsServlet.java
│   ├── DownloadCSVServlet.java
│   └── DBConnection.java
│
├── forms/
│   ├── login.html
│   ├── studentForm.html
│   ├── viewApplications.jsp
│   ├── success.html
│   └── error.html
│
├── uploads/            # Uploaded resumes
├── css/                # Stylesheets
│   └── style.css
├── WEB-INF/
│   └── web.xml
└── README.md
🧪 How to Run Locally
Clone the repository
git clone https://github.com/your-username/InternshipApplicationPortal.git
cd InternshipApplicationPortal
Import into Eclipse/IDE
Open Eclipse → File → Import → Existing Projects
Select this folder.
Configure Database
Create a database in MySQL:
CREATE DATABASE internship_portal;
Create the applications table:

CREATE TABLE applications (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  college VARCHAR(100),
  department VARCHAR(100),
  cgpa DOUBLE,
  role VARCHAR(50),
  resume_path VARCHAR(255),
  applied_on DATETIME
);
Update DB credentials in DBConnection.java.
Deploy to Tomcat
Build a .war file or run on a local server via Eclipse.
Access the App
Visit: http://localhost:8080/InternshipApplicationPortal/forms/studentForm.html
🔐 Admin Credentials

Username: admin
Password: admin123

(Can be configured in AdminLoginServlet.java)

📌 Future Improvements

Email notifications on successful applications

File type validation for resume uploads

Admin role management (multi-level access)

Deploy online via Render or Railway

GitHub
