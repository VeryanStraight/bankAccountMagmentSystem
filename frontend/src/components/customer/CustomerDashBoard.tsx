import CustomerNav from "./CustomerNav";

const CustomerDashboard = () => {
  console.log("dashbord storage:", localStorage.getItem("username"));

  return (
    <div>
      <CustomerNav />
    </div>
  );
};

export default CustomerDashboard;
