import { useEffect, useState } from "react";
import axios from "axios";
import { Route, Routes, useLocation, useNavigate } from "react-router-dom";
import { Alert, Button } from "react-bootstrap";
import UpdateAccount from "./UpdateAccount";
import { Account } from "../Account";
import Form from "react-bootstrap/Form";

//show the account details
const ShowAccount = () => {
  const [account, setAccount] = useState<Account | undefined>();
  const [accountId, setAccountId] = useState<number | undefined>();
  const [formData, setFormData] = useState<number | undefined>();
  const navigate = useNavigate();
  const location = useLocation();
  const [message, setMessage] = useState<String>();

  //get the user each time the username is updated
  useEffect(() => {
    if (accountId !== undefined) {
      fetchAccount();
    }
  }, [accountId, location]);

  //get the account
  const fetchAccount = async () => {
    try {
      const res = await axios.get<Account>(
        `http://localhost:8080/api/accountSystem/account/${accountId}`
      );
      console.log(res.data);
      setAccount(res.data);
      console.log("response " + res.data);
      setMessage(undefined);
    } catch (err) {
      setMessage("Could not find account");
      console.error("Error fetching account info:", err);
    }
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (formData !== undefined) {
      setAccountId(formData);
    }
  };

  const onClick = () => {
    navigate("updateaccount");
  };

  //the html for the account page
  return (
    <>
      <div>
        <Form onSubmit={handleSubmit}>
          <Form.Group>
            <Form.Label>Account Id:</Form.Label>
            <Form.Control
              type="number"
              value={formData || ""}
              onChange={(e) => setFormData(Number(e.target.value))}
              required
            />
          </Form.Group>
          <Button variant="primary" type="submit">
            Find Account
          </Button>
        </Form>
      </div>
      {message && <Alert className="mt-3">{message}</Alert>}
      {account ? (
        <>
          <h2>Account</h2>
          <Button variant="primary" onClick={onClick}>
            Update
          </Button>
          <p>Id: {account.id}</p>
          <p>Customer: {account.customer?.user.username}</p>
          <p>Name: {account.name}</p>
          <p>Blance: {account.balance}</p>
          <p>Status: {account.status?.status}</p>
          <p>Start Date: {String(account.start)}</p>
          <div className="account-content">
            <Routes>
              <Route
                path="updateaccount"
                element={<UpdateAccount account={account} />}
              />
            </Routes>
          </div>
        </>
      ) : (
        <></>
      )}
    </>
  );
};

export default ShowAccount;
