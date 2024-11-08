import { useParams } from "react-router-dom";
import { Transaction } from "../Transactions";
import axios from "axios";
import { useState, useEffect } from "react";
import { Table } from "react-bootstrap";

const Transactions = () => {
  const { accountId } = useParams();
  const [transactions, setTransactions] = useState<Transaction[]>([]);

  useEffect(() => {
    fetchTransactions();
  }, [accountId]);

  const fetchTransactions = async () => {
    console.log("fetch transactions");
    try {
      const res = await axios.get<Transaction[]>(
        `http://localhost:8080/api/accountSystem/account/${accountId}/transaction`
      );
      if (res.data != undefined) {
        setTransactions(res.data);
      }
      console.log("response " + res.data);
    } catch (err) {
      console.error("Error fetching accounts:", err);
    }
  };

  return (
    <div>
      <h2>Transactions</h2>
      {transactions ? (
        <Table striped bordered hover responsive>
          <thead>
            <tr>
              <th>Transaction Id</th>
              <th>Transaction Type</th>
              <th>Amount</th>
              <th>To Account</th>
            </tr>
          </thead>
          <tbody>
            {transactions.map((transaction) => (
              <tr key={transaction.id}>
                <td>{transaction.id}</td>
                <td>{transaction.type?.type}</td>
                <td>{transaction.amount}</td>
                <td>{transaction.toAccount?.id}</td>
              </tr>
            ))}
          </tbody>
        </Table>
      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
};

export default Transactions;
