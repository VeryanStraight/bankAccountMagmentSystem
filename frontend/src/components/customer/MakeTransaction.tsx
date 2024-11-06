import React, { useEffect, useState } from "react";
import axios from "axios";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import { Account } from "../Account";
import { TransactionType } from "../TransactionType";
import { Transaction } from "../Transactions";
import { Customer } from "../Customer";
import { Alert, Col, Row } from "react-bootstrap";

interface Props {
  username: string;
}

const CreateTransaction = ({ username }: Props) => {
  const [accounts, setAccounts] = useState<Account[]>([]);
  const [transactionTypes, setTransactionTypes] = useState<TransactionType[]>(
    []
  );
  const [transactionData, setTransactionData] = useState<Transaction>({
    id: undefined,
    toAccount: undefined,
    fromAccount: undefined,
    type: undefined,
    amount: undefined,
    description: undefined,
    datetime: undefined,
  });
  const [message, setMessage] = useState("");
  const [success, setSuccess] = useState(false);

  //on page open get the accounts and transaction types
  useEffect(() => {
    fetchAccounts();
    fetchTransactionTypes();
  }, []);

  //fetch accounts method
  const fetchAccounts = async () => {
    try {
      const customer: Customer = (
        await axios.get<Customer>(
          `http://localhost:8080/api/accountSystem/user/${username}/customer`
        )
      ).data;

      const response = await axios.get(
        `http://localhost:8080/api/accountSystem/customer/${customer.id}/account`
      );
      setAccounts(response.data);
    } catch (error) {
      console.error("Error fetching accounts:", error);
    }
  };

  //fetch transactions method
  const fetchTransactionTypes = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/accountSystem/transactiontypes`
      );
      setTransactionTypes(response.data);
    } catch (error) {
      console.error("Error fetching transaction types:", error);
    }
  };

  //whn the form changes update the data
  const handleChange = (e: React.ChangeEvent<HTMLElement>) => {
    const target = e.target as HTMLSelectElement | HTMLInputElement;
    const { name, value } = target;
    setTransactionData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  //when the account changes set the new account
  const handleChangeAccount = (e: React.ChangeEvent<HTMLElement>) => {
    const target = e.target as HTMLSelectElement | HTMLInputElement;
    const { name, value } = target;
    const account: Account = {
      id: Number(value),
      balance: undefined,
      customer: undefined,
      name: undefined,
      start: undefined,
      status: undefined,
    };
    setTransactionData((prevData) => ({
      ...prevData,
      [name]: account,
    }));
  };

  //when the transaction type changes set the new type
  const handleChangeType = (e: React.ChangeEvent<HTMLElement>) => {
    const target = e.target as HTMLSelectElement | HTMLInputElement;
    const { name, value } = target;
    const account: TransactionType = {
      id: Number(value),
      type: undefined,
    };
    setTransactionData((prevData) => ({
      ...prevData,
      [name]: account,
    }));
  };

  //make the new transaction
  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      console.log(transactionData);
      const response = await axios.put(
        `http://localhost:8080/api/accountSystem/transaction`,
        transactionData
      );
      setMessage("Transaction created successfully! id: " + response.data.id);
      setSuccess(true);
    } catch (error) {
      console.error("Error creating transaction:", error);
      setMessage("Failed to create transaction. Please try again.");
      setSuccess(false);
    }
  };

  //the html for the make transaction page
  return (
    <>
      <h2 className="mt-4">Create New Transaction</h2>
      <Form onSubmit={handleSubmit}>
        <Row className="mb-3">
          <Col>
            <Form.Group controlId="toAccount">
              <Form.Label>To Account:</Form.Label>
              <Form.Control
                type="number"
                name="toAccount"
                value={
                  transactionData.toAccount ? transactionData.toAccount.id : ""
                }
                onChange={handleChangeAccount}
                required
              />
            </Form.Group>
          </Col>

          <Col>
            <Form.Group controlId="fromAccount">
              <Form.Label>Customer Account:</Form.Label>
              <Form.Control
                as="select"
                name="fromAccount"
                value={
                  transactionData.fromAccount
                    ? transactionData.fromAccount.id
                    : ""
                }
                onChange={handleChangeAccount}
                required
              >
                <option value="">Select an account</option>
                {accounts.map((account) => (
                  <option key={account.id} value={account.id}>
                    {account.id}{" "}
                  </option>
                ))}
              </Form.Control>
            </Form.Group>
          </Col>
        </Row>

        <Row className="mb-3">
          <Col>
            <Form.Group controlId="type">
              <Form.Label>Transaction Type:</Form.Label>
              <Form.Control
                as="select"
                name="type"
                value={transactionData.type?.type}
                onChange={handleChangeType}
                required
              >
                <option value="">Select a type</option>
                {transactionTypes.map((type) => (
                  <option key={type.id} value={type.id}>
                    {type.type}{" "}
                  </option>
                ))}
              </Form.Control>
            </Form.Group>
          </Col>

          <Col>
            <Form.Group controlId="amount">
              <Form.Label>Amount:</Form.Label>
              <Form.Control
                type="number"
                name="amount"
                value={transactionData.amount ? transactionData.amount : ""}
                onChange={handleChange}
                required
              />
            </Form.Group>
          </Col>
        </Row>

        <Button variant="primary" type="submit">
          Create Transaction
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

export default CreateTransaction;
