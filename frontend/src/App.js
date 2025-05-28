import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage';
import BrandAdminPage from './pages/BrandAdminPage';
import ProductManagementPage from './pages/ProductManagementPage';
import CategoryLowPricePage from './pages/CategoryLowPricePage';
import BrandLowPricePage from './pages/BrandLowPricePage';
import './App.css';

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/admin/brands" element={<BrandAdminPage />} />
          <Route path="/admin/products" element={<ProductManagementPage />} />
          <Route path="/category-low-price" element={<CategoryLowPricePage />} />
          <Route path="/brand-low-price" element={<BrandLowPricePage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
