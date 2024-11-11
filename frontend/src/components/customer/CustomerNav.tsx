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
