import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import axios from "axios";
import MyBarChart from "../components/Table";

const Stimulation = () => {
  const location = useLocation();
  const { name = "Default Event", price = "0" } = location.state || {};
  const [refetchData, setRefetchData] = useState(false);
  const [fetchedData, setFetchedData] = useState({ data: "", price: "" });

  useEffect(() => {
    console.log("Location state:", location.state);

    // Function to fetch data
    const fetchData = async () => {
      try {
        const response = await axios.get("http://localhost:8082/zeus/getdataprice");
        setFetchedData(response.data);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    // Fetch data every 2 seconds
    const intervalId = setInterval(fetchData, 2000);

    // Cleanup interval on component unmount
    return () => clearInterval(intervalId);
  }, []);

  // Load input values from localStorage or set default values
  const savedInputs = JSON.parse(localStorage.getItem("inputValues"));
  const [inputValues, setInputValues] = useState(
    savedInputs || {
      totaltickets: "",
      maxcus: "",
      protime: "",
      contime: "",
      maxpro: "",
      buyercount: "",
      merchantcount: "",
    }
  );

  // State for handling errors and success messages
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  // Handle input changes and update the specific input field's value
  const handleChange = (field, value) => {
    setInputValues((prevState) => {
      const newValues = {
        ...prevState,
        [field]: value,
      };
      // Save to localStorage when the state updates
      localStorage.setItem("inputValues", JSON.stringify(newValues));
      return newValues;
    });
  };

  // Handle form submission (different for start and stop actions)
  const handleSubmit = async (actionType) => {
    // Validation for required fields for "start"
    if (
      actionType === "start" &&
      (!inputValues.totaltickets ||
        !inputValues.maxcus ||
        !inputValues.protime ||
        !inputValues.contime ||
        !inputValues.maxpro ||
        !inputValues.buyercount ||
        !inputValues.merchantcount)
    ) {
      setErrorMessage("Please fill in all fields before starting.");
      return;
    }

    // Clear previous messages
    setErrorMessage("");
    setSuccessMessage("");

    try {
      let response;

      if (actionType === "start") {
        // Prepare data for starting stimulation
        setRefetchData(false);
        const data = {
          totaltickets: inputValues.totaltickets,
          maxcus: inputValues.maxcus,
          protime: inputValues.protime,
          contime: inputValues.contime,
          eventname: name,
          ticketprice: price,
          maxpro: inputValues.maxpro,
          buyercount: inputValues.buyercount,
          merchantcount: inputValues.merchantcount,
        };

        // Send POST request to start stimulation
        response = await axios.post("http://localhost:8082/zeus/data", data);
      } else if (actionType === "stop") {
        // Send POST request to stop stimulation
        setRefetchData(true);
        response = await axios.post("http://localhost:8082/zeus/stop", {
          msg: "stop",
        });
      }

      // Handle success response
      if (response.status === 200) {
        setSuccessMessage(
          actionType === "start"
            ? "Stimulation started successfully!"
            : "Stimulation stopped successfully!"
        );
      } else {
        setErrorMessage(
          actionType === "start"
            ? "Failed to start stimulation."
            : "Failed to stop stimulation."
        );
      }
    } catch (error) {
      console.error("Error:", error);
      setErrorMessage(
        actionType === "start"
          ? "Failed to start stimulation. Please try again."
          : "Failed to stop stimulation. Please try again."
      );
    }
  };

  return (
    <div className="App">
      <h1>Zeus Ticketing System</h1>
      <h2>[All configurations should be filled]</h2>
      <form className="input-form">
        {/* Event Name and Ticket Price are static */}
        <div className="input-box">
          <label>Event Name</label>
          <input type="text" value={name} disabled placeholder="Event Name" />
        </div>

        <div className="input-box">
          <label>Ticket Price</label>
          <input
            type="text"
            value={price}
            disabled
            placeholder="Ticket Price"
          />
        </div>

        {/* Other input fields */}
        <div className="input-box">
          <label>Ticket Buffer Size </label>
          <input
            type="number"
            value={inputValues.totaltickets}
            onChange={(e) => handleChange("totaltickets", e.target.value)}
            placeholder="Enter total tickets"
          />
        </div>

        <div className="input-box">
          <label>Maximum tickets a customer can buy</label>
          <input
            type="number"
            value={inputValues.maxcus}
            onChange={(e) => handleChange("maxcus", e.target.value)}
            placeholder="Enter max tickets per customer"
          />
        </div>

        <div className="input-box">
          <label>One Ticket producing rate per second</label>
          <input
            type="number"
            value={inputValues.protime}
            onChange={(e) => handleChange("protime", e.target.value)}
            placeholder="Enter production rate"
          />
        </div>

        <div className="input-box">
          <label>One Ticket consuming rate per second</label>
          <input
            type="number"
            value={inputValues.contime}
            onChange={(e) => handleChange("contime", e.target.value)}
            placeholder="Enter consumption rate"
          />
        </div>

        <div className="input-box">
          <label>Maximum tickets a producer can produce</label>
          <input
            type="number"
            value={inputValues.maxpro}
            onChange={(e) => handleChange("maxpro", e.target.value)}
            placeholder="Enter max tickets produced"
          />
        </div>

        <div className="input-box">
          <label>How many buyers?</label>
          <input
            type="number"
            value={inputValues.buyercount}
            onChange={(e) => handleChange("buyercount", e.target.value)}
            placeholder="Enter number of buyers"
          />
        </div>

        <div className="input-box">
          <label>How many Merchants?</label>
          <input
            type="number"
            value={inputValues.merchantcount}
            onChange={(e) => handleChange("merchantcount", e.target.value)}
            placeholder="Enter number of merchants"
          />
        </div>

        {/* Error and Success Messages */}
        {errorMessage && <div className="error-message">{errorMessage}</div>}
        {successMessage && (
          <div className="success-message">{successMessage}</div>
        )}

        {/* Start Button */}
        <button type="button" onClick={() => handleSubmit("start")}>
          Start Stimulation
        </button>

        {/* Stop Button */}
        <button
          className="button2"
          type="button"
          onClick={() => handleSubmit("stop")}
        >
          Stop Stimulation
        </button>
      </form>

      <MyBarChart refetchData={refetchData} />

      {/* Display fetched data */}
      <div>
        <h2>Fetched Data</h2>
        <p>TOtal Tickets sold: {fetchedData.data}</p>
        <p>Revenue: {fetchedData.price}</p>
      </div>
    </div>
  );
};

export default Stimulation;