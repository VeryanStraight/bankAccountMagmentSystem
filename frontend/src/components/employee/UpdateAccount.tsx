import axios from "axios";
import { useEffect, useState } from "react";
import { Button, Container, Form } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { Account } from "../Account";
import { Status } from "../Status";

interface Props {
  account: Account;
}

interface FormData {
  name: string | undefined;
  customerId: number | undefined;
  status: Status | undefined;
}

const UpdateAccount = ({ account }: Props) => {
  const [formData, setFormData] = useState<FormData>({
    name: undefined,
    customerId: undefined,
    status: undefined,
  });
  const [statuses, setStatuses] = useState<Status[]>([]);

  const navigate = useNavigate();

  useEffect(() => {
    fetchStatuses();
  }, [account]);

  const fetchStatuses = async () => {
    console.log("in find statuses");
    try {
      const response = await axios.get(
        `http://localhost:8080/api/accountSystem/statuses`
      );
      console.log(response.data);
      setStatuses(response.data);
      console.log(statuses);
    } catch (error) {
      console.error("Error fetching statuses:", error);
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

  const handleChangeStatus = (e: React.ChangeEvent<HTMLElement>) => {
    const target = e.target as HTMLSelectElement | HTMLInputElement;
    const { name, value } = target;
    const status: Status = {
      id: Number(value),
      status: undefined,
    };

    setFormData((prevData) => ({
      ...prevData,
      [name]: status,
    }));
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      const newAccount: Account = {
        id: undefined,
        balance: undefined,
        customer: undefined,
        name: formData.name,
        start: undefined,
        status: formData.status,
      };

      if (formData.customerId != undefined) {
        const customer = await axios.get(
          `http://localhost:8080/api/accountSystem/customer/${formData.customerId}`
        );

        if (customer == undefined) {
          return;
        }
        newAccount.customer = customer.data;
      }

      newAccount.name = formData.name;
      newAccount.status = formData.status;
      console.log("new account:");
      console.log(newAccount);

      await axios.patch(
        `http://localhost:8080/api/accountSystem/account/${account.id}`,
        newAccount
      );
    } catch (err) {
      console.error("Error updating info:", err);
    }

    navigate("/employee-dashboard/showaccounts");
  };

  const handleCancel = () => {
    navigate("/employee-dashboard/showaccounts");
  };

  return (
    <Container className="mt-4">
      <h2>Update Account</h2>
      <Form onSubmit={handleSubmit}>
        <Form.Group className="mb-3" controlId="formName">
          <Form.Label>Name</Form.Label>
          <Form.Control
            type="text"
            placeholder={account.name}
            name="name"
            value={formData.name}
            onChange={handleChange}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formCustomer">
          <Form.Label>Customer ID</Form.Label>
          <Form.Control
            type="text"
            placeholder={String(account.customer?.id)}
            name="customerId"
            value={formData.customerId}
            onChange={handleChange}
          />
        </Form.Group>

        <Form.Group controlId="formStatus">
          <Form.Label>Status</Form.Label>
          <Form.Control
            as="select"
            name="status"
            value={formData.status?.status}
            onChange={handleChangeStatus}
          >
            <option value="">Select to update status</option>
            {statuses.map((status) => (
              <option key={status.id} value={status.id}>
                {status.status}
              </option>
            ))}
          </Form.Control>
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

export default UpdateAccount;
