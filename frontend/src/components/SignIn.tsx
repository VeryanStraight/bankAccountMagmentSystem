// SignIn.js
import axios, { AxiosError } from "axios";
import React, { useState } from "react";
import { Alert } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

const SignIn = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  // const handleLogin = (role: string) => {
  //   // Mock login logic: In a real app, you’d authenticate with the backend here
  //   console.log("Logging in as:", role);
  //   console.log("Logging in as:", username);
  //   localStorage.setItem("userRole", role);
  //   localStorage.setItem("username", username);
  //   console.log("storage:", localStorage.getItem("username"));
  //   console.log(
  //     "Navigating to:",
  //     role === "EMPLOYEE" ? "/employee-dashboard" : "/customer-dashboard"
  //   );

  //   if (role === "EMPLOYEE") {
  //     navigate("/employee-dashboard");
  //   } else if (role === "CUSTOMER") {
  //     navigate("/customer-dashboard");
  //   }
  // };

  const handleLogin = async (role: string) => {
    try {
      console.log(username);
      console.log(password);
      const response = await axios.post(
        "http://localhost:8080/login",
        {
          username,
          password,
        },
        {
          headers: { "Content-Type": "application/x-www-form-urlencoded" },
        }
      );

      // Handle successful login (e.g., redirect to dashboard)
      console.log("Login successful" + response.data);
      // Redirect or update state as needed
      localStorage.setItem("userRole", role);
      localStorage.setItem("username", username);

      if (role === "EMPLOYEE") {
        navigate("/employee-dashboard");
      } else if (role === "CUSTOMER") {
        navigate("/customer-dashboard");
      }
    } catch (error) {
      const axiosError: AxiosError = error as AxiosError;
      console.error("Error during login:", error);
      // Check if the error response exists to get a more specific message
      if (axiosError.response) {
        //error.response.data.message
        setMessage("Invalid username or password");
      } else {
        setMessage("Login failed. Please try again.");
      }
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

      {message && <Alert className="mt-3">{message}</Alert>}
    </div>
  );
};

export default SignIn;
