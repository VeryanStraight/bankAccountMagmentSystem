import { Employee } from "./Employee";

interface Props {
  employee: Employee;
}

function EmployeeDisplay({ employee }: Props) {
  return (
    <main className="container mt-md-4">
      <div className="row justify-content-center">
        <header>
          <p>id: {employee.id.toString()}</p>
          <p>username: {employee.user.username}</p>
          <p>password: {employee.password}</p>
        </header>
      </div>
    </main>
  );
}

export default EmployeeDisplay;
