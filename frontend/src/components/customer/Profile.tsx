import { useEffect, useState } from "react";
import axios from "axios";
import { Customer } from "../Customer";
import { Route, Routes, useLocation, useNavigate } from "react-router-dom";
import { Button } from "react-bootstrap";
import UpdateCustomer from "./UpdateCustomer";

interface Props {
  username: string;
}

//show the customers details
const Profile = ({ username }: Props) => {
  const [customer, setCustomer] = useState<Customer | null>(null);
  const navigate = useNavigate();
  const location = useLocation();

  console.log("profile " + username);
  //get the user each time the username is updated
  useEffect(() => {
    console.log("in use effect" + username);
    console.log(username);
    fetchCustomer();
  }, [username, location]);

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

  const onClick = () => {
    navigate("updateprofile");
  };

  //the html for the profile page
  return (
    <>
      <div>
        <h2>Profile</h2>
        {customer ? (
          <>
            <Button variant="primary" onClick={onClick}>
              Update
            </Button>
            <p>Username: {customer.user.username}</p>
            <p>Password: {customer.password}</p>
            <p>Name: {customer.user.name}</p>
            <p>Email: {customer.user.email}</p>
            <p>Phone: {customer.user.phone}</p>
            <p>Address: {customer.address}</p>
          </>
        ) : (
          <p>Loading...</p>
        )}
      </div>
      <div className="profile-content">
        <Routes>
          <Route
            path="updateprofile"
            element={<UpdateCustomer username={username} />}
          />
        </Routes>
      </div>
    </>
  );
};

export default Profile;
