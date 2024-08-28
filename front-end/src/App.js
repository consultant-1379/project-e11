import './App.css';
import React, { useState } from 'react';
import axios from 'axios';

function App() {
  const [responseData, setResponseData] = useState([]);
  const [id, setId] = useState('');
  const [fromDate, setFromDate] = useState('');
  const [untilDate, setUntilDate] = useState('');

  const fetchDataWithDates = async (fromDate, untilDate) => {
    try {
      console.log(fromDate);
      console.log(untilDate);
      const response = await axios.get(`http://localhost:8081/gitData/withDates?from=${fromDate}&to=${untilDate}`);
      console.log(response);
      setResponseData(response.data.gitData);
    } catch (error) {
      console.error('Error fetching data with dates:', error);
    }
  };

  const fetchSpecificData = async (id) => {
    try {
      const response = await axios.get(`http://localhost:8081/gitData/${id}`);
      setResponseData([response.data]);
    } catch (error) {
      console.error('Error fetching specific data:', error);
    }
  };

  return (
    <div>
      <h1>Homepage</h1>

      {/* Date pickers and button to fetch data with dates */}
      <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
        <label htmlFor="fromDate">From:</label>
        <input
          type="date"
          id="fromDate"
          value={fromDate}
          onChange={(e) => setFromDate(e.target.value)}
        />
        <label htmlFor="untilDate">Until:</label>
        <input
          type="date"
          id="untilDate"
          value={untilDate}
          onChange={(e) => setUntilDate(e.target.value)}
        />
        <button id="fetchDataWithDatesButton" onClick={() => fetchDataWithDates(fromDate, untilDate)}>Fetch Data With Dates</button>
      </div>
      
      {/* Input and button to fetch specific data */}
      <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
        <input
          type="text"
          value={id}
          onChange={(e) => setId(e.target.value)}
          placeholder="Enter ID"
        />
        <button id="fetchSpecificDataButton" onClick={() => fetchSpecificData(id)}>Fetch Specific Data</button>
      </div>

      {/* Display fetched data */}
      {responseData.length > 0 && (
        <div>
          <h2>Response Data:</h2>
          {responseData.map((data, index) => (
            <div key={index}>
              {Object.entries(data).map(([key, value]) => {
                if (typeof value === 'object' && value !== null) {
                  // If the value is an object, iterate over its keys and render each value
                  return (
                    <div key={key}>
                      {key}:
                      <ul>
                        {Object.entries(value).map(([nestedKey, nestedValue]) => (
                          <li key={nestedKey}>
                            {nestedKey}: {nestedValue}
                          </li>
                        ))}
                      </ul>
                    </div>
                  );
                } else {
                  // If the value is not an object, render it directly
                  return (
                    <div key={key}>
                      {key}: {value}
                    </div>
                  );
                }
              })}
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default App;
