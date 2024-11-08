import { Link, Route, Routes } from "react-router-dom";
import Profile from "./Profile";
import MakeTransaction from "./MakeTransaction";
import ShowAccounts from "./ShowAccounts";
import Transactions from "./Transactions";
import { Col, Container, Nav, Navbar, Row } from "react-bootstrap";

//sets up the customer routes and return the nav
const CustomerNav = () => {
  const username: string = localStorage.getItem("username") ?? "";

  return (
    <>
      <Navbar expand="lg" className="bg-body-tertiary">
        <Container>
          <Navbar.Brand as={Link} to="/customer-dashboard">
            <img
              src="\resources\robin.jpg"
              alt="Bank Logo"
              width="50"
              height="40"
              className="d-inline-block align-top"
            />
          </Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="me-auto">
              <Nav.Link as={Link} to="profile">
                Profile
              </Nav.Link>
              <Nav.Link as={Link} to="maketransaction">
                Make Transaction
              </Nav.Link>
              <Nav.Link as={Link} to="accounts">
                Accounts
              </Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>

      <Container fluid className="min-vh-100">
        <Row className="w-100">
          <Col xs={12} md={4} className="mx-auto pt-3">
            <div className="dashboard-content">
              <Routes>
                <Route
                  path="profile/*"
                  element={<Profile username={username} />}
                />
                <Route
                  path="maketransaction"
                  element={<MakeTransaction username={username} />}
                />
                <Route
                  path="accounts"
                  element={<ShowAccounts username={username} />}
                />
                <Route
                  path="transactions/:accountId"
                  element={<Transactions />}
                />
              </Routes>
            </div>
          </Col>
        </Row>
      </Container>
    </>
  );
};

export default CustomerNav;
