import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import { useEffect, useState } from "react";
import { Customer } from "../Customer";
import { Alert } from "react-bootstrap";
import axios from "axios";

//deletes a customer
const DeleteCustomer = () => {
  const [customerUsername, setCustomerUsername] = useState<string>();
  const [customer, setCustomer] = useState<Customer>();
  const [message, setMessage] = useState("");

  //updates the customer when the username changes
  useEffect(() => {
    if (customerUsername != undefined) {
      fetchCustomer(customerUsername);
    }
  }, [customerUsername]);

  //gets the customer
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
        console.log("Error fetching customer info:", err);
      }
    }
  };

  //deletes the customer
  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      console.log(customerUsername);
      console.log(customer);
      //show message if the username is invalid
      if (customerUsername == undefined || customer == undefined) {
        setMessage("No customer");
        return;
      }

      //deletes the customer
      const response = await axios.delete(
        `http://localhost:8080/api/accountSystem/customer/${customer.id}`
      );
      setMessage("Customer deleted successfully!" + response.data);
    } catch (error) {
      console.error("Error deleting customer:", error);
      setMessage("Error deleting customer");
    }
  };

  //the thml for the delete customer page
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
