import Navbar from "./CustomerNav";
import Profile from "./Profile";

const CustomerDashboard = () => {
  const username: string = localStorage.getItem("username") ?? "";
  console.log("dashbord storage:", localStorage.getItem("username"));

  return (
    <div>
      {/* <Navbar /> */}
      <Profile username={username} />
    </div>
  );
};

export default CustomerDashboard;