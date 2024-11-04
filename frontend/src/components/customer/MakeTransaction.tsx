import React, { useEffect, useState } from "react";
import axios from "axios";
import { Customer } from "../Customer"; // Adjust the import path as needed
import CustomerNav from "./CustomerNav";

interface Props {
  username: string;
}

const MakeTransaction = ({ username }: Props) => {
  const [customer, setCustomer] = useState<Customer | null>(null);

  console.log("profile " + username);
  useEffect(() => {
    fetchCustomer();
  }, [username]);

  const fetchCustomer = async () => {
    console.log("fetch customer");
    if (username) {
      try {
        const res = await axios.get<Customer>(
          `http://localhost:8080/api/accountSystem/user/${username}/customer`
        );
        setCustomer(res.data);
        console.log("response " + res.data);
      } catch (err) {
        console.error("Error fetching customer info:", err);
      }
    }
  };

  const fetchAccounts = async (customerId: number) => {
    console.log("fetch customer");
    if (username) {
      try {
        const res = await axios.get<Customer>(
          `http://localhost:8080/api/accountSystem/user/${customerId}/customer`
        );
        setCustomer(res.data);
        console.log("response " + res.data);
      } catch (err) {
        console.error("Error fetching customer info:", err);
      }
    }
  };

  return (
    <div>
      <CustomerNav />
      <h2>Make Transaction</h2>
    </div>
  );
};

export default MakeTransaction;
