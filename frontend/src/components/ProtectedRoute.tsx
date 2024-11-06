// ProtectedRoute.js
import React from "react";
import { Navigate } from "react-router-dom";

type Props = {
  children: React.ReactNode;
  role: string;
};

//redirects to sign in if user doesn't have the right role for the route
const ProtectedRoute = ({ children, role }: Props) => {
  const userRole = localStorage.getItem("userRole");

  if (userRole !== role) {
    return <Navigate to="/" replace />;
  }

  return children;
};

export default ProtectedRoute;
