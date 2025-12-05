# User Guide - Bangladesh Bank Simulation System

## Login Credentials

The system supports multiple user roles from different team members:

### Saida's Users (Commercial Bank Manager & Ministry of Finance)

1. **Admin**
   - Username: `admin`
   - Password: `admin123`
   - Access: User management dashboard

2. **Commercial Bank Manager**
   - Username: `bankmanager`
   - Password: `bank123`
   - Access: Commercial Bank Manager Dashboard
   - Features: SLR Compliance, Loan Approval, Liquidity Management, CAR Monitoring, AML Compliance, Branch Performance, etc.

3. **Ministry of Finance Representative**
   - Username: `financerep`
   - Password: `finance123`
   - Access: Ministry of Finance Dashboard
   - Features: Revenue Monitoring, Expenditure Control, Budget Management, Public Debt, Foreign Aid, etc.

### Shifat's Users (Governor & Director of Banking Regulation)

4. **Governor**
   - Username: `governor`
   - Password: `governor123`
   - Access: Governor Dashboard
   - Features: Manage Commercial Banks, Monetary Policies, Loan Approvals, Foreign Reserves, Staff Info, Circulars, Inflation Reports, Interest Rates

5. **Director of Banking Regulation**
   - Username: `dbr`
   - Password: `dbr123`
   - Access: Director of Banking Regulation Dashboard
   - Features: Supervise Commercial Banks, Bank Compliance Reports, Bank Audit Results, Loan Requests, Penalty Records, Inspection Schedules, Annual Performance Reports, Circulars for Banks

## How to Add More Team Members

To add more team members' users and dashboards:

1. **Add User Role to Login Combo Box**
   - Edit: `src/main/java/com/example/simulation_of_bangladesh_bank/saida/controller/LoginController.java`
   - Add the new role to the `userTypeComboBox` items in the `initialize()` method

2. **Add Navigation Logic**
   - Edit: `src/main/java/com/example/simulation_of_bangladesh_bank/saida/controller/LoginController.java`
   - Add a case in the `navigateToDashboard()` method's switch statement
   - Point to the FXML file path for the new dashboard

3. **Add Default User**
   - Edit: `src/main/java/com/example/simulation_of_bangladesh_bank/saida/util/DataManager.java`
   - Add a new `User` object in the `initializeDefaultUsers()` method
   - Include username, password, full name, role, email, phone, and department

4. **Update Module Info (if needed)**
   - Edit: `src/main/java/module-info.java`
   - Add `opens` and `exports` statements for the new team member's package

5. **Place FXML Files**
   - Place the team member's FXML files in: `src/main/resources/com/example/simulation_of_bangladesh_bank/[member_name]/`

## Example: Adding a New Team Member

```java
// In LoginController.java - initialize() method
userTypeComboBox.setItems(FXCollections.observableArrayList(
    "Admin",
    "Commercial Bank Manager",
    "Ministry of Finance Representative",
    "Governor",
    "Director of Banking Regulation",
    "New Role Name"  // Add here
));

// In LoginController.java - navigateToDashboard() method
case "New Role Name":
    fxmlPath = "/com/example/simulation_of_bangladesh_bank/newmember/Dashboard.fxml";
    break;

// In DataManager.java - initializeDefaultUsers() method
User newUser = new User(
    "USR006",
    "newuser",
    "newpass123",
    "Full Name",
    "New Role Name",
    "email@example.com",
    "+880-1234-567890",
    "Department Name"
);
defaultUsers.add(newUser);
```

## Notes

- All users share the same login page
- Each user sees their role-specific dashboard after login
- User data is stored in `data/users.bin`
- The Admin can manage all users (add, edit, delete, reset password)

