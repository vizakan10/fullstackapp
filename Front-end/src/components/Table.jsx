import React, { useState, useEffect } from "react";
import axios from "axios";
import {
  AreaChart as ReAreaChart,
  Area,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
} from "recharts";

const AreaChart = ({refetchData}) => {
  const [chartData, setChartData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const res = await axios.get("http://localhost:8082/zeus/getChart");
        if (res.data && res.data.length > 0) {
          const data = res.data.map((item) => ({
            eventName: item.eventName || "Unknown Event",
            totalTicket: item.totalTicket || 0,
            producingTime: item.producingTime || 0,
            consumingTime: item.consumingTime || 0,
          }));
          setChartData(data);
        } else {
          console.warn("Received empty data");
        }
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, [refetchData]);

  return (
    <ResponsiveContainer width="100%" height={400}>
      <ReAreaChart data={chartData}>
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="eventName" />
        <YAxis />
        <Tooltip />
        <Area
          type="monotone"
          dataKey="totalTicket"
          stroke="#8884d8"
          fill="#8884d8"
        />
        <Area
          type="monotone"
          dataKey="producingTime"
          stroke="#82ca9d"
          fill="#82ca9d"
        />
        <Area
          type="monotone"
          dataKey="consumingTime"
          stroke="#ffc658"
          fill="#ffc658"
        />
      </ReAreaChart>
    </ResponsiveContainer>
  );
};

export default AreaChart;
