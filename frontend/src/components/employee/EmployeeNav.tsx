import React from "react";
import { Link, Route, Routes } from "react-router-dom";
import DeleteCustomer from "./DeleteCustomer";
import AddCustomer from "./AddCustomer";

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
        </ul>
      </nav>

      <div className="dashboard-content">
        <Routes>
          <Route path="deletecustomer" element={<DeleteCustomer />} />
        </Routes>
        <Routes>
          <Route path="addcustomer" element={<AddCustomer />} />
        </Routes>
      </div>
    </>
  );
};

export default CustomerNav;
