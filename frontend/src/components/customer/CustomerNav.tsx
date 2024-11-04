import React from "react";
import { Link, Route, Routes } from "react-router-dom";
import Profile from "./Profile";

const CustomerNav = () => {
  const username: string = localStorage.getItem("username") ?? "";

  return (
    <>
      <nav>
        <ul>
          <li>
            <Link to="profile">Profile</Link>
          </li>
        </ul>
      </nav>

      <div className="dashboard-content">
        <Routes>
          <Route path="profile" element={<Profile username={username} />} />
        </Routes>
      </div>
    </>
  );
};

export default CustomerNav;
