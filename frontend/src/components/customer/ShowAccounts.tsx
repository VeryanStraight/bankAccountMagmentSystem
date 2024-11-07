import axios from "axios";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { Customer } from "../Customer";
import { Account } from "../Account";
import { Table, Button } from "react-bootstrap";

interface Props {
  username: string;
}

const ShowAccounts = ({ username }: Props) => {
  const [accounts, setAccounts] = useState<Account[]>([]);

  useEffect(() => {
    fetchAccounts();
  }, [username]);

  const fetchAccounts = async () => {
    console.log("fetch accounts");
    if (username) {
      try {
        const customer = await axios.get<Customer>(
          `http://localhost:8080/api/accountSystem/user/${username}/customer`
        );
        if (customer != undefined) {
          const res = await axios.get<Account[]>(
            `http://localhost:8080/api/accountSystem/customer/${customer.data.id}/account`
          );
          console.log(res.data);
          setAccounts(res.data);
          console.log("response " + res.data);
        } else {
          console.error("Error fetching customer");
        }
      } catch (err) {
        console.error("Error fetching accounts:", err);
      }
    }
  };

  return (
    <div>
      <h2>Accounts</h2>
      {accounts ? (
        <Table striped bordered hover responsive>
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Balance</th>
              <th>Transactions</th>
            </tr>
          </thead>
          <tbody>
            {accounts.map((account) => (
              <tr key={account.id}>
                <td>{account.id}</td>
                <td>{account.name}</td>
                <td>{account.balance}</td>
                <td>
                  <Link to={`/customer-dashboard/transactions/${account.id}`}>
                    <Button variant="link">View Transactions</Button>
                  </Link>
                </td>
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

export default ShowAccounts;
