import React, { useState } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import { Alert } from "react-bootstrap";
import axios from "axios";
import { Account } from "../Account";
import { Customer } from "../Customer";

const AddAccount = () => {
  const [formData, setFormData] = useState({
    username: undefined,
    name: undefined,
  });

  const [message, setMessage] = useState("");
  const [success, setSuccess] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLElement>) => {
    const target = e.target as HTMLSelectElement | HTMLInputElement;
    const { name, value } = target;
    console.log(name + " " + value);
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      console.log(formData);

      const customer = await axios.get<Customer>(
        `http://localhost:8080/api/accountSystem/user/${formData.username}/customer`
      );

      const account: Account = {
        name: formData.name,
        customer: customer.data,
        id: undefined,
        balance: undefined,
        start: undefined,
        status: undefined,
      };

      console.log(account);

      if (customer == undefined) {
        setMessage("Failed to find customer. Please try again.");
        return;
      }

      const createdAccount = await axios.put<Account>(
        `http://localhost:8080/api/accountSystem/account`,
        account
      );
      setMessage("Account created successfully! Id: " + createdAccount.data.id);
      setSuccess(true);
    } catch (error) {
      console.error("Error creating account:", error);
      setMessage("Failed to create account. Please try again.");
      setSuccess(false);
    }
  };

  //the html for the add customer page
  return (
    <>
      <h2 className="mt-4">Create New Account</h2>
      <Form onSubmit={handleSubmit}>
        <Form.Group>
          <Form.Label>username</Form.Label>
          <Form.Control
            type="text"
            name="username"
            placeholder="username"
            value={formData ? formData.username : ""}
            onChange={handleChange}
          />
        </Form.Group>

        <Form.Group>
          <Form.Label>name</Form.Label>
          <Form.Control
            type="text"
            name="name"
            placeholder="name"
            value={formData.name ? formData.name : ""}
            onChange={handleChange}
          />
        </Form.Group>

        <Button variant="primary" type="submit">
          Create Account
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

export default AddAccount;
