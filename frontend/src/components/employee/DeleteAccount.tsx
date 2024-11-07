import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import { useState } from "react";
import { Alert } from "react-bootstrap";
import axios from "axios";

//deletes an account
const DeleteAccount = () => {
  const [accountID, setAccountID] = useState<number>();
  const [message, setMessage] = useState("");

  //deletes the account
  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      //deletes the account
      const response = await axios.delete(
        `http://localhost:8080/api/accountSystem/account/${accountID}`
      );
      setMessage("Account deleted successfully!" + response.data);
    } catch (error) {
      console.error("Error deleting account:", error);
      setMessage("Error deleting account");
    }
  };

  //the thml for the delete account page
  return (
    <>
      <h2 className="mt-4">Delete Account</h2>
      <Form onSubmit={handleSubmit}>
        <Form.Group>
          <Form.Label>Account Id:</Form.Label>
          <Form.Control
            type="number"
            value={accountID ? accountID : ""}
            onChange={(e) => setAccountID(Number(e.target.value))}
            required
          />
        </Form.Group>
        <Button variant="primary" type="submit">
          Delete Account
        </Button>
      </Form>

      {message && <Alert className="mt-3">{message}</Alert>}
    </>
  );
};

export default DeleteAccount;
