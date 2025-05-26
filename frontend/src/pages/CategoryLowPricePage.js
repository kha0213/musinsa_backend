import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { productService } from '../services/api';
import './CategoryLowPricePage.css';

const CategoryLowPricePage = () => {
  const navigate = useNavigate();
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      setLoading(true);
      setError(null);
      
      const response = await productService.getCategoryLowPriceBrands();
      setData(response.data);
      
    } catch (err) {
      console.error('Error loading data:', err);
      setError(`데이터를 불러오는 중 오류가 발생했습니다: ${err.message}`);
    } finally {
      setLoading(false);
    }
  };

  const formatPrice = (price) => {
    return price?.toLocaleString() || '0';
  };

  if (loading) return <div className="loading">데이터를 불러오는 중...</div>;
  if (error) return <div className="error">{error}</div>;
  if (!data) {
    return (
      <div className="category-low-price-page">
        <div className="header">
          <h1>카테고리별 최저가격 브랜드와 상품가격</h1>
          <button 
            className="btn btn-secondary"
            onClick={() => navigate('/')}
          >
            홈으로
          </button>
        </div>
        <div className="error">데이터가 없습니다.</div>
      </div>
    );
  }

  return (
    <div className="category-low-price-page">
      <div className="header">
        <h1>카테고리별 최저가격 브랜드와 상품가격</h1>
        <button 
          className="btn btn-secondary"
          onClick={() => navigate('/')}
        >
          홈으로
        </button>
      </div>

      <div className="table-container">
        <table className="price-table">
          <thead>
            <tr>
              <th>카테고리</th>
              <th>브랜드</th>
              <th>가격</th>
            </tr>
          </thead>
          <tbody>
            {data.categoryLowPrices && data.categoryLowPrices.length > 0 ? (
              data.categoryLowPrices.map((item, index) => (
                <tr key={index}>
                  <td className="category-name">{item.categoryName || 'N/A'}</td>
                  <td className="brand-name">{item.brandName || 'N/A'}</td>
                  <td className="price">{formatPrice(item.price)}</td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="3" style={{textAlign: 'center', padding: '20px', color: '#666'}}>
                  데이터가 없습니다.
                </td>
              </tr>
            )}
            {data.totalPrice && (
              <tr className="total-row">
                <td colSpan="2" className="total-label">총액</td>
                <td className="total-price">{formatPrice(data.totalPrice)}</td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      <div className="summary">
        <p>각 카테고리별로 가장 저렴한 상품을 판매하는 브랜드와 가격을 보여줍니다.</p>
      </div>
    </div>
  );
};

export default CategoryLowPricePage;
