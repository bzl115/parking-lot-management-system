# Parking Lot Management System 🚗

The **Parking Lot Management System** is a Java-based project developed to streamline and automate parking management tasks.  
It allows administrators to manage parking slots, record vehicle entries and exits, and monitor parking activity efficiently.  
All data is stored in a connected MySQL database, ensuring accurate and reliable record-keeping and fee calculation.

## Course Project done by

BASIL PETER  
RAMKIRAN R  
RISHABH P.S.  
ROHAN ANOOP  

## 📋 Features

- 🔐 **User Login System** — Secure authentication for system users.  
- 🅿️ **Slot Management** — Add, remove, and update available parking slots.  
- 🚘 **Vehicle Tracking** — Record vehicle entries, exits, and calculate parking time.  
- 💾 **Database Integration** — All data stored and fetched from a MySQL database.  
- 🧩 **Modular Code Structure** — Separated logic for database, UI, and main operations.  
- ⚡ **Responsive UI** — Simple and fast interface using Java Swing (or JavaFX, if used).

## 🏗️ Project Structure

PLMS/  
├── .vscode/  
│ └── settings.json  
│    
├── lib/  
│ └── mysql-connector-j-9.4.0.jar    
│  
├── .gitignore  
├── config.properties  
├── Admin.java  
├── DatabaseConnection.java  
├── Main.java  
├── MainUI.java  
├── ParkingFunctions.java  
├── ParkingManager.java  
├── ParkingRecord.java  
├── ParkingSlot.java   
├── Vehicle.java  
└── Readme.md  

📦 Requirements

Java JDK 17+
MySQL 8.0+
MySQL Connector/J
(Optional) VS Code or IntelliJ IDEA

🪄 1. Clone the Repository
git clone <https://github.com/bzl115/paring-lot-management-system.git>
cd paring-lot-management-system

⚙️ 2. Install MySQL and Create the Database

Make sure MySQL is installed and running.

Open MySQL Command Line or MySQL Shell.

Log in as root:

mysql -u root -p

Create the database:  

CREATE DATABASE parkingdb;  
USE parkingdb;

Create a dedicated user for this project:  

CREATE USER 'parking_user'@'localhost' IDENTIFIED WITH mysql_native_password BY 'password';  
GRANT ALL PRIVILEGES ON parkingdb.* TO 'parking_user'@'localhost';  
FLUSH PRIVILEGES;

🧱 3. Create Required Table(s)  

Run this inside MySQL:  

CREATE TABLE parking_records (  
    record_id INT AUTO_INCREMENT PRIMARY KEY,    
    owner_name VARCHAR(100) NOT NULL,    
    vehicle_number VARCHAR(50) NOT NULL,  
    slot_number INT NOT NULL,  
    entry_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  
    exit_time TIMESTAMP NULL,  
    fee DECIMAL(10,2) DEFAULT 0.00  
);  

🔗 4. Add MySQL Connector (JDBC Driver)

Download the MySQL Connector/J .jar from the official Oracle site:
👉 <https://dev.mysql.com/downloads/connector/j/>

Then, in your project:

Place the file (e.g. mysql-connector-j-9.4.0.jar) inside your lib/ or root folder.

Add it to your classpath  
if using VS Code, ensure it’s added as a referenced library
open settings.json and modify

  "java.project.referencedLibraries": [
        "lib/**/*.jar"
    ]

⚙️ 5. Configure Database & Admin Credentials

modify the file config.properties

DB_URL=jdbc:mysql://localhost:3306/parkingdb
DB_USER=your_username_here
DB_PASS=your_password_here
ADMIN_ID=your_admin_here
ADMIN_PASS=your_admin_password_here

🧩 6. Run the Application

Now you can compile and run the project:

javac -cp ".;path_to_jar_file\mysql-connector-j-9.4.0.jar" *.java
java -cp ".;path_to_jar_file\mysql-connector-j-9.4.0.jar" Main.java

Or run directly from VS Code (ensure the .jar is linked in your classpath).
