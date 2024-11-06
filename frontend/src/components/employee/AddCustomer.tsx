import React, { useState } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import { Alert } from "react-bootstrap";
import { User } from "../User";
import { Customer } from "../Customer";
import axios from "axios";

//adds a customer
const AddCustomer = () => {
  const [user, setUser] = useState<User>({
    username: undefined,
    name: undefined,
    phone: undefined,
    email: undefined,
  });

  const [customer, setCustomer] = useState<Customer>({
    id: undefined,
    password: undefined,
    user: user,
    created_date: undefined,
    address: undefined,
  });

  const [message, setMessage] = useState("");
  const [success, setSuccess] = useState(false);

  //update the customer useState when the form is changed
  const handleChangeCustomer = (e: React.ChangeEvent<HTMLElement>) => {
    const target = e.target as HTMLSelectElement | HTMLInputElement;
    const { name, value } = target;
    console.log(name + " " + value);

    setCustomer((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  //update the user useState when the form is changed
  const handleChangeUser = (e: React.ChangeEvent<HTMLElement>) => {
    const target = e.target as HTMLSelectElement | HTMLInputElement;
    const { name, value } = target;
    console.log(name + " " + value);

    setUser((prevData) => ({
      ...prevData,
      [name]: value,
    }));

    //put the updated user in the customer
    setCustomer((pervData) => ({
      ...pervData,
      ["user"]: user,
    }));
  };

  //add the customer
  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      console.log(user);
      //add the user
      await axios.put(`http://localhost:8080/api/accountSystem/user`, user);

      console.log(customer);
      //add the customer
      const createdCustomer = await axios.put<Customer>(
        `http://localhost:8080/api/accountSystem/customer`,
        customer
      );
      setMessage(
        "Customer created successfully! Id: " + createdCustomer.data.id
      );
      setSuccess(true);
    } catch (error) {
      console.error("Error creating customer:", error);
      setMessage("Failed to create Customer. Please try again.");
      setSuccess(false);
    }
  };

  //the html for the add customer page
  return (
    <>
      <h2 className="mt-4">Create New Customer</h2>
      <Form onSubmit={handleSubmit}>
        <Form.Group>
          <Form.Label>username</Form.Label>
          <Form.Control
            type="text"
            name="username"
            placeholder="username"
            value={user ? user.username : ""}
            onChange={handleChangeUser}
          />
        </Form.Group>

        <Form.Group>
          <Form.Label>name</Form.Label>
          <Form.Control
            type="text"
            name="name"
            placeholder="name"
            value={user ? user.name : ""}
            onChange={handleChangeUser}
          />
        </Form.Group>

        <Form.Group>
          <Form.Label>phone</Form.Label>
          <Form.Control
            type="text"
            name="phone"
            placeholder="phone number"
            value={user ? user.phone : ""}
            onChange={handleChangeUser}
          />
        </Form.Group>

        <Form.Group>
          <Form.Label>email</Form.Label>
          <Form.Control
            type="text"
            name="email"
            placeholder="email"
            value={user ? user.email : ""}
            onChange={handleChangeUser}
          />
        </Form.Group>

        <Form.Group>
          <Form.Label>password</Form.Label>
          <Form.Control
            type="text"
            name="password"
            placeholder="password"
            value={customer ? customer.password : ""}
            onChange={handleChangeCustomer}
          />
        </Form.Group>

        <Form.Group>
          <Form.Label>adderss</Form.Label>
          <Form.Control
            type="text"
            name="address"
            placeholder="address"
            value={customer ? customer.address : ""}
            onChange={handleChangeCustomer}
          />
        </Form.Group>

        <Button variant="primary" type="submit">
          Create Customer
        </Button>
      </Form>

      {message && (
        <Alert variant={success ? "success" : "danger"} className="mt-3">
          {message}
        </Alert>
      )}
    </>
  );
};

export default AddCustomer;
