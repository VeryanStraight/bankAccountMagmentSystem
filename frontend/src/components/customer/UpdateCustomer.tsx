import axios from "axios";
import { useEffect, useState } from "react";
import { Button, Container, Form } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { Customer } from "../Customer";

interface Props {
  username: string;
}

const UpdateCustomer = ({ username }: Props) => {
  const [formData, setFormData] = useState({
    name: undefined,
    email: undefined,
    phone: undefined,
    address: undefined,
    password: undefined,
  });

  const [original, setoriginal] = useState({
    id: undefined,
    name: undefined,
    email: undefined,
    phone: undefined,
    address: undefined,
    password: undefined,
  });
  const navigate = useNavigate();

  useEffect(() => {
    fetchCustomer();
  }, [username]);

  //get the customer
  const fetchCustomer = async () => {
    console.log("Fetching customer...");
    if (username) {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/accountSystem/user/${username}/customer`
        );

        setoriginal({
          id: res.data.id || "",
          name: res.data.user.name || "",
          email: res.data.user.email || "",
          phone: res.data.user.phone || "",
          address: res.data.address || "",
          password: res.data.password || "",
        });
      } catch (err) {
        console.error("Error fetching customer info:", err);
      }
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLElement>) => {
    const target = e.target as HTMLSelectElement | HTMLInputElement;
    const { name, value } = target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (username) {
      try {
        const customer: Customer = {
          id: undefined,
          password: undefined,
          user: {
            username: undefined,
            name: undefined,
            phone: undefined,
            email: undefined,
          },
          created_date: undefined,
          address: undefined,
        };

        customer.user.name = formData.name;
        customer.user.email = formData.email;
        customer.address = formData.address;
        customer.user.phone = formData.phone;
        customer.password = formData.password;

        console.log("to update");
        console.log(customer);

        const updatedUser = await axios.patch(
          `http://localhost:8080/api/accountSystem/user/${username}`,
          customer.user
        );

        customer.user.username = username;
        const updatedCustomer = await axios.patch(
          `http://localhost:8080/api/accountSystem/customer/${original.id}`,
          customer
        );
      } catch (err) {
        console.error("Error updating info:", err);
      }
    }

    navigate("/customer-dashboard/profile");
  };

  const handleCancel = () => {
    navigate("/customer-dashboard/profile");
  };

  return (
    <Container className="mt-4">
      <h2>Update Profile</h2>
      <Form onSubmit={handleSubmit}>
        <Form.Group className="mb-3" controlId="formPassword">
          <Form.Label>Password</Form.Label>
          <Form.Control
            type="text"
            placeholder={original.password}
            name="password"
            value={formData.password}
            onChange={handleChange}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formName">
          <Form.Label>Name</Form.Label>
          <Form.Control
            type="text"
            placeholder={original.name}
            name="name"
            value={formData.name}
            onChange={handleChange}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formEmail">
          <Form.Label>Email</Form.Label>
          <Form.Control
            type="email"
            placeholder={original.email}
            name="email"
            value={formData.email}
            onChange={handleChange}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formPhone">
          <Form.Label>Phone</Form.Label>
          <Form.Control
            type="text"
            placeholder={original.phone}
            name="phone"
            value={formData.phone}
            onChange={handleChange}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formAddress">
          <Form.Label>Address</Form.Label>
          <Form.Control
            as="textarea"
            rows={3}
            placeholder={original.address}
            name="address"
            value={formData.address}
            onChange={handleChange}
          />
        </Form.Group>

        <div className="d-flex justify-content-between">
          <Button variant="primary" type="submit">
            Submit
          </Button>
          <Button variant="secondary" onClick={handleCancel}>
            Cancel
          </Button>
        </div>
      </Form>
    </Container>
  );
};

export default UpdateCustomer;
