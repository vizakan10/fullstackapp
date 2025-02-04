import React from "react";
import { Link } from "react-router-dom";
import Event from "../components/Event";
import "./Homepage.css";

const Homepage = () => {
  return (
    <div className="container">
      {/* Title at the top */}
      <h1 className="heading">Zeus Ticketing System</h1>

      <Link
        to="/stimulation"
        state={{ name: "Naadhagama", price: "5000" }}
        className="link"
      >
        <Event
          picture="public\download.jpg"
          name="Naadhagama"
          price="Rs.5000"
        />
      </Link>

      <Link
        to="/stimulation"
        state={{ name: "New Year Party", price: "10000" }}
        className="link"
      >
        <Event
          picture="public\2download.jpg"
          name="New Year Party"
          price="Rs.10000"
        />
      </Link>
    </div>
  );
};

export default Homepage;
