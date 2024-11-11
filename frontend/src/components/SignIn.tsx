//page for signin in to the backend
import axios, { AxiosError } from "axios";
import { useState } from "react";
import { Alert, Col, Container, Row } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

const SignIn = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  //logs in to the backend and directs you to the retrospective backend
  const handleLogin = async (role: string) => {
    try {
      //log in to backend
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

      //set state and redirect to backend
      console.log("Login successful");
      console.log(response.data);
      localStorage.setItem("userRole", role);
      localStorage.setItem("username", username);
      if (role === "EMPLOYEE") {
        navigate("/employee-dashboard/showaccounts");
      } else if (role === "CUSTOMER") {
        navigate("/customer-dashboard/profile");
      }
    } catch (error) {
      console.error("Error during login:", error);

      const axiosError: AxiosError = error as AxiosError;
      if (axiosError.response) {
        setMessage("Invalid username or password");
      } else {
        setMessage("Login failed. Please try again.");
      }
    }
  };

  //the login html
  return (
    <Container
      fluid
      className="min-vh-100 d-flex justify-content-center align-items-center"
    >
      <Row className="w-100">
        <Col xs={12} md={4} className="mx-auto">
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
            <button onClick={() => handleLogin("EMPLOYEE")}>
              Login as Employee
            </button>
            <button onClick={() => handleLogin("CUSTOMER")}>
              Login as Customer
            </button>

            {message && <Alert className="mt-3">{message}</Alert>}
          </div>
        </Col>
      </Row>
    </Container>
  );
};

export default SignIn;
