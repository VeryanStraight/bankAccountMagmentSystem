import { useState } from "react";
import { Employee } from "../Employee";
import axios, { AxiosError } from "axios";
import EmployeeDisplay from "./EmployeeDisplay";

function SearchEmployees() {
  const [search, setSearch] = useState("");
  const [searchData, setSearchData] = useState<Employee | null>(null); // Change to null for clarity
  const [message, setMessage] = useState("");

  const fetchData = async (search: string) => {
    try {
      const username = encodeURIComponent(search.trim());

      const res = await axios.get<Employee>(
        `http://localhost:8080/api/accountSystem/user/${username}/employee`
      );
      setSearchData(res.data);
      setMessage(""); // Clear any previous message
    } catch (err) {
      const error = err as AxiosError;
      if (error.response && error.response.status === 404) {
        setSearchData(null); // Set to null instead of undefined
        setMessage("No matching employee found");
      } else {
        console.log(err);
      }
    }
  };

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();

    if (search !== "") {
      fetchData(search);
    } else {
      setSearchData(null); // Set to null instead of undefined
      setMessage("Please enter a username to search.");
    }
  };

  return (
    <>
      <form className="d-flex mt-4" onSubmit={handleSearch}>
        <input
          onChange={(e) => setSearch(e.target.value)}
          value={search}
          className="form-control me-2"
          type="search"
          placeholder="username"
          aria-label="Search Employees"
        />
        <button className="btn btn-outline-success" type="submit">
          Search
        </button>
      </form>
      {searchData ? (
        <EmployeeDisplay employee={searchData} />
      ) : (
        <p>{message}</p>
      )}
    </>
  );
}

export default SearchEmployees;
