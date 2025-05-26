import React, { useState, useEffect } from 'react';
import { homeService } from '../services/api';
import './HomePage.css';

const HomePage = () => {
  const [brandCategories, setBrandCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    loadBrandCategories();
  }, []);

  const loadBrandCategories = async () => {
    try {
      setLoading(true);
      const response = await homeService.getBrandCategories();
      setBrandCategories(response.data);
    } catch (err) {
      setError('데이터를 불러오는 중 오류가 발생했습니다.');
      console.error('Error loading brand categories:', err);
    } finally {
      setLoading(false);
    }
  };

  const formatPrice = (price) => {
    return price?.toLocaleString() || '';
  };

  if (loading) return <div className="loading">데이터를 불러오는 중...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div className="home-page">
      <h1>무신사 스토어</h1>
      <p>준비된 데이터는 다음과 같습니다.</p>
      
      <div className="table-container">
        <table className="brand-table">
          <thead>
            <tr>
              <th>브랜드</th>
              {brandCategories[0]?.categoryProducts?.map((categoryProduct, index) => (
                <th key={index}>
                  {categoryProduct?.category?.name || ''}
                </th>
              ))}
            </tr>
          </thead>
          <tbody>
            {brandCategories.map((brandCategory, index) => (
              <tr key={index}>
                <td className="brand-name">{brandCategory.brandName}</td>
                {brandCategory.categoryProducts?.map((categoryProduct, idx) => (
                  <td key={idx} className="price-cell">
                    {categoryProduct?.product?.price 
                      ? formatPrice(categoryProduct.product.price)
                      : ''}
                  </td>
                ))}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      
      <div className="navigation">
        <a href="/admin/brands" className="admin-link">브랜드 관리</a>
      </div>
    </div>
  );
};

export default HomePage;
