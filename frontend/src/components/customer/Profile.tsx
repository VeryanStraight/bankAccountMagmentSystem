import { useEffect, useState } from "react";
import axios from "axios";
import { Customer } from "../Customer";

interface Props {
  username: string;
}

//show the customers details
const Profile = ({ username }: Props) => {
  const [customer, setCustomer] = useState<Customer | null>(null);

  console.log("profile " + username);
  //get the user each time the username is updated
  useEffect(() => {
    console.log("in use effect" + username);
    console.log(username);
    fetchCustomer();
  }, [username]);

  //get the customer
  const fetchCustomer = async () => {
    console.log("fetch customer");
    if (username) {
      try {
        const res = await axios.get<Customer>(
          `http://localhost:8080/api/accountSystem/user/${username}/customer`
        );
        console.log(res.data);
        setCustomer(res.data);
        console.log("response " + res.data);
      } catch (err) {
        console.error("Error fetching customer info:", err);
      }
    }
  };

  //the html for the profile page
  return (
    <div>
      <h2>Profile</h2>
      {customer ? (
        <>
          <p>Username: {customer.user.username}</p>
          <p>Name: {customer.user.name}</p>
          <p>Email: {customer.user.email}</p>
          <p>Phone: {customer.user.phone}</p>
        </>
      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
};

export default Profile;
