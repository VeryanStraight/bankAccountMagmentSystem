import { Link, Route, Routes } from "react-router-dom";
import Profile from "./Profile";
import MakeTransaction from "./MakeTransaction";
import Accounts from "./ShowAccounts";
import Transactions from "./transactions";
import ShowAccounts from "./ShowAccounts";

//sets up the customer routes and return the nav
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
          <li>
            <Link to="accounts">Accounts</Link>
          </li>
        </ul>
      </nav>

      <div className="dashboard-content">
        <Routes>
          <Route path="profile/*" element={<Profile username={username} />} />
          <Route
            path="maketransaction"
            element={<MakeTransaction username={username} />}
          />
          <Route
            path="accounts"
            element={<ShowAccounts username={username} />}
          />
          <Route path="transactions/:accountId" element={<Transactions />} />
        </Routes>
      </div>
    </>
  );
};

export default CustomerNav;
