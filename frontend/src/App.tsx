// App.js
import React from "react";
import { Route, Routes } from "react-router-dom";
import SignIn from "./components/SignIn";
import EmployeeDashBoard from "./components/employee/EmployeeDashBoard";
import CustomerDashboard from "./components/customer/CustomerDashBoard";
import ProtectedRoute from "./components/ProtectedRoute";
import axios from "axios";

axios.defaults.withCredentials = true;

const App = () => {
  return (
    <Routes>
      <Route path="/" element={<SignIn />} />
      <Route
        path="/employee-dashboard/*"
        element={
          <ProtectedRoute role="EMPLOYEE">
            <EmployeeDashBoard />
          </ProtectedRoute>
        }
      />
      <Route
        path="/customer-dashboard/*"
        element={
          <ProtectedRoute role="CUSTOMER">
            <CustomerDashboard />
          </ProtectedRoute>
        }
      />
    </Routes>
  );
};

export default App;
