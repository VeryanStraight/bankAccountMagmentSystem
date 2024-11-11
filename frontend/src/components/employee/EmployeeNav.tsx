import { Link, Route, Routes } from "react-router-dom";
import DeleteCustomer from "./DeleteCustomer";
import AddCustomer from "./AddCustomer";
import AddAccount from "./AddAccount";
import DeleteAccount from "./DeleteAccount";
import ShowAccount from "./ShowAccount";
import { Navbar, Container, Nav, Col, Row } from "react-bootstrap";

//sets up the routes for customers and return the nav
const CustomerNav = () => {
  return (
    <>
      <Navbar expand="lg" className="bg-body-tertiary" data-bs-theme="dark">
        <Container>
          <Navbar.Brand as={Link} to="/">
            <img
              src="\resources\robin.png"
              alt="Bank Logo"
              width="60"
              height="40"
              className="d-inline-block align-top"
            />
          </Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="me-auto">
              <Nav.Link as={Link} to="deletecustomer">
                Delete Customer
              </Nav.Link>
              <Nav.Link as={Link} to="addcustomer">
                Add Customer
              </Nav.Link>
              <Nav.Link as={Link} to="addaccount">
                Add Account
              </Nav.Link>
              <Nav.Link as={Link} to="deleteaccount">
                Delete Account
              </Nav.Link>
              <Nav.Link as={Link} to="showaccounts">
                Show Account
              </Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>

      <Container
        fluid
        className="min-vh-100"
        style={{
          background:
            "linear-gradient(to right, rgb(157 57 57) 20%, white 20%, white 80%, rgb(157 57 57) 80%)",
        }}
      >
        <Row className="w-100">
          <Col xs={12} md={4} className="mx-auto pt-3">
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
          </Col>
        </Row>
      </Container>
    </>
  );
};

export default CustomerNav;
