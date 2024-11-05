import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import { useState } from "react";
import { Customer } from "../Customer";
import { Alert } from "react-bootstrap";
import axios from "axios";

const DeleteCustomer = () => {
  const [customerUsername, setCustomerUsername] = useState<string>();
  const [customer, setCustomer] = useState<Customer>();
  const [message, setMessage] = useState("");

  const fetchCustomer = async (username: string) => {
    console.log("fetch customer");
    if (username) {
      try {
        console.log(username);
        const res = await axios.get<Customer>(
          `http://localhost:8080/api/accountSystem/user/${username}/customer`
        );
        setCustomer(res.data);
        console.log(res.data);
      } catch (err) {
        console.error("Error fetching customer info:", err);
      }
    }
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      console.log(customerUsername);
      if (customerUsername == undefined) {
        setMessage("No customer");
        return;
      }
      fetchCustomer(customerUsername);
      console.log(customer);
      if (customer == undefined) {
        setMessage("No customer");
        return;
      }
      const response = await axios.delete(
        `http://localhost:8080/api/accountSystem/customer/${customer.id}`
      );
      setMessage("Customer deleted successfully!" + response.data);
    } catch (error) {
      console.error("Error deleting customer:", error);
      setMessage("Error deleting customer");
    }
  };

  return (
    <>
      <h2 className="mt-4">Delete Customer</h2>
      <Form onSubmit={handleSubmit}>
        <Form.Group controlId="toAccount">
          <Form.Label>Customer username:</Form.Label>
          <Form.Control
            type="text"
            value={customerUsername ? customerUsername : ""}
            onChange={(e) => setCustomerUsername(e.target.value)}
            required
          />
        </Form.Group>
        <Button variant="primary" type="submit">
          Delete Customer
        </Button>
      </Form>

      {message && <Alert className="mt-3">{message}</Alert>}
    </>
  );
};

export default DeleteCustomer;
