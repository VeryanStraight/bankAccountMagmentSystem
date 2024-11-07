import { Link, Route, Routes } from "react-router-dom";
import DeleteCustomer from "./DeleteCustomer";
import AddCustomer from "./AddCustomer";
import AddAccount from "./AddAccount";
import DeleteAccount from "./DeleteAccount";
import ShowAccount from "./ShowAccount";

//sets up the routes for customers and return the nav
const CustomerNav = () => {
  return (
    <>
      <nav>
        <ul>
          <li>
            <Link to="deletecustomer">Delete Customer</Link>
          </li>
          <li>
            <Link to="addcustomer">Add Customer</Link>
          </li>
          <li>
            <Link to="addaccount">Add Account</Link>
          </li>
          <li>
            <Link to="deleteaccount">Delete Account</Link>
          </li>
          <li>
            <Link to="showaccounts">Show Account</Link>
          </li>
        </ul>
      </nav>

      <div className="dashboard-content">
        <Routes>
          <Route path="deletecustomer" element={<DeleteCustomer />} />
        </Routes>
        <Routes>
          <Route path="addcustomer" element={<AddCustomer />} />
        </Routes>
        <Routes>
          <Route path="addAccount" element={<AddAccount />} />
        </Routes>
        <Routes>
          <Route path="deleteAccount" element={<DeleteAccount />} />
        </Routes>
        <Routes>
          <Route path="showaccounts/*" element={<ShowAccount />} />
        </Routes>
      </div>
    </>
  );
};

export default CustomerNav;
