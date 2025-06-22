# 🚌 Bus Reservation System

A comprehensive Java-console-based bus reservation management system that provides seamless booking, ticket management, and administrative controls for bus travel agencies.
<br>
## 🌟 Features

### 👤 **Admin Panel**
• **🔐 Secure Admin Authentication** - Login system with username/password verification<br>
• **🚍 Bus Management** - Add, view, update, and delete bus records<br>
• **💺 Seat Management** - Real-time seat availability tracking and updates<br>
• **🎫 Ticket Approval System** - Review and approve pending passenger tickets<br>
• **📊 Administrative Dashboard** - Complete oversight of bus operations<br>

### 🧳 **Passenger Panel**
• **🔍 Bus Search & View** - Browse available buses with detailed information<br>
• **📝 Easy Bus Booking** - Simple registration process with automatic passenger ID generation<br>
• **🎟️ Ticket Generation** - Create tickets with pending approval status<br>
• **👀 Ticket Tracking** - View personal ticket history and status updates<br>
• **📱 User-Friendly Interface** - Intuitive console-based navigation<br>

</br>

## 🏗️ System Architecture

### **📦 Package Structure**
```
├── main/
│   └── Main.java            # Application entry point & main method
├── system/
│   └── Driver.java          # Application controller & database connection
└── repository/
    ├── Bus.java             # Bus operations & admin functionalities
    └── Passenger.java       # Passenger booking & ticket management
```

### **🔄 Application Flow**
• `Main.java` - Contains the main method and creates Driver object to start the application<br>
• `Driver.java` - Handles user interface, menu navigation, and coordinates between Bus and Passenger classes<br>
• `Bus.java` - Manages all bus-related operations and admin functionalities<br>
• `Passenger.java` - Handles passenger registration, booking, and ticket management<br>

<br>

## 🗄️ Database Schema

### **🚌 Bus Table**
• `bus_id` (Primary Key) - Unique bus identifier<br>
• `source` - Departure location<br>
• `destination` - Arrival location<br>
• `arrival_time` - Scheduled arrival time<br>
• `total_seats` - Maximum bus capacity<br>
• `available_seats` - Current available seats<br>

### **👥 Passenger Table**
• `passenger_id` (Auto-generated) - Unique passenger identifier<br>
• `name` - Passenger full name<br>
• `phone_no` - Contact number<br>
• `age` - Passenger age<br>
• `bus_id` - Associated bus reference<br>
• `source` - Journey starting point<br>
• `destination` - Journey endpoint<br>

### **🎫 Ticket Table**
• `ticket_id` (Auto-generated) - Unique ticket identifier<br>
• `passenger_id` - Reference to passenger<br>
• `bus_id` - Reference to booked bus<br>
• `source` - Travel origin<br>
• `destination` - Travel destination<br>
• `status` - Approval status (Pending/Approved)<br>

### **🔑 Admin Table**
• `username` - Admin login credential<br>
• `password` - Admin authentication password<br>


## 🚀 Getting Started

### **📋 Prerequisites**
• ☕ Java Development Kit (JDK) 8 or higher<br>
• 🗄️ MySQL Database Server<br>
• 🔗 MySQL Connector/J JDBC Driver<br>
• 💻 IDE (Eclipse, IntelliJ IDEA, or VS Code)<br>


### **🔧 Configuration**
Update database connection details in `Driver.java`:
```java
private static final String url = "jdbc:mysql://localhost:3306/bus_travelling_agency";
private static final String username = "root";
private static final String password = "your_mysql_password";
```
<br>

## 🎮 How to Use

### **🏃‍♂️ Running the Application**
1. Compile all Java files</br>
2. Run the `Main.java` class (which creates Driver object)</br>
3. Choose between Admin Panel (1) or Passenger Panel (2)</br>

### **👨‍💼 Admin Workflow**
1. **🔑 Login** with admin credentials</br>
2. **➕ Add Buses** - Insert new bus routes and schedules<br>
3. **👀 Monitor** - View all registered buses and their status<br>
4. **✅ Approve Tickets** - Review and approve passenger bookings<br>
5. **🔄 Update** - Modify seat availability as needed<br>

### **🧳 Passenger Workflow**
1. **🔍 Browse** available buses</br>
2. **📝 Book** by providing personal details and route preferences<br>
3. **💾 Save** the generated Passenger ID (important!)<br>
4. **🎫 Generate** ticket using your Passenger ID<br>
5. **⏳ Wait** for admin approval<br>
6. **📋 Check** ticket status anytime<br>
<br>

## 🔥 Key Highlights

### **🛡️ Security Features**
• **🔐 Admin Authentication** - Secure login verification<br>
• **🛡️ SQL Injection Protection** - Prepared statements for database security<br>
• **✅ Data Validation** - Input verification and error handling<br>

### **📊 Smart Management**
• **🔄 Automatic Seat Updates** - Real-time seat availability tracking<br>
• **🎫 Ticket Approval System** - Controlled booking process<br>
• **🔗 Relational Data Integrity** - Foreign key constraints ensure data consistency<br>

### **👥 User Experience**
• **🎯 Intuitive Navigation** - Clear menu-driven interface<br>
• **📱 Responsive Design** - Clean console output formatting<br>
• **💡 Helpful Messages** - Informative success/error notifications<br>

<br>
## 🤝 Contributing

I welcome contributions to improve the Bus Reservation System!</br>

1. **🍴 Fork** the repository<br>
2. **🌟 Create** a feature branch<br>
3. **💻 Commit** your changes<br>
4. **📤 Push** to the branch<br>
5. **🔄 Open** a Pull Request<br>

---

### 🎉 **Ready to revolutionize bus travel management? Get started today!** 🚀

*Built with ❤️ using Java and MySQL*
