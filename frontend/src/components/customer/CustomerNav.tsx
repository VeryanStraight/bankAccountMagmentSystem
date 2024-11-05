import React from "react";
import { Link, Route, Routes } from "react-router-dom";
import Profile from "./Profile";
import MakeTransaction from "./MakeTransaction";

const CustomerNav = () => {
  const username: string = localStorage.getItem("username") ?? "";

  return (
    <>
      <nav>
        <ul>
          <li>
            <Link to="profile">Profile</Link>
          </li>
          <li>
            <Link to="maketransaction">Make Transaction</Link>
          </li>
        </ul>
      </nav>

      <div className="dashboard-content">
        <Routes>
          <Route path="profile" element={<Profile username={username} />} />
          <Route
            path="maketransaction"
            element={<MakeTransaction username={username} />}
          />
        </Routes>
      </div>
    </>
  );
};

export default CustomerNav;
