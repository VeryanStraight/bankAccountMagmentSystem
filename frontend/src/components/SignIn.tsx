// SignIn.js
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const SignIn = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = (role: string) => {
    // Mock login logic: In a real app, you’d authenticate with the backend here
    console.log("Logging in as:", role);
    console.log("Logging in as:", username);
    localStorage.setItem("userRole", role);
    localStorage.setItem("username", username);
    console.log("storage:", localStorage.getItem("username"));
    console.log(
      "Navigating to:",
      role === "EMPLOYEE" ? "/employee-dashboard" : "/customer-dashboard"
    );

    if (role === "EMPLOYEE") {
      navigate("/employee-dashboard");
    } else if (role === "CUSTOMER") {
      navigate("/customer-dashboard");
    }
  };

  return (
    <div>
      <h2>Sign In</h2>
      <input
        type="text"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        placeholder="Username"
      />
      <input
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        placeholder="Password"
      />
      <button onClick={() => handleLogin("EMPLOYEE")}>Login as Employee</button>
      <button onClick={() => handleLogin("CUSTOMER")}>Login as Customer</button>
    </div>
  );
};

export default SignIn;
