import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage';
import BrandAdminPage from './pages/BrandAdminPage';
import './App.css';

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/admin/brands" element={<BrandAdminPage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
