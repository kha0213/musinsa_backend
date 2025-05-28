import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { productService } from '../services/api';
import './BrandLowPricePage.css';

const BrandLowPricePage = () => {
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
      
      const response = await productService.getLowestBrandTotal();
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
      <div className="brand-low-price-page">
        <div className="header">
          <h1>단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액</h1>
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
    <div className="brand-low-price-page">
      <div className="header">
        <h1>단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액</h1>
        <button 
          className="btn btn-secondary"
          onClick={() => navigate('/')}
        >
          홈으로
        </button>
      </div>

      <div className="result-container">
        <div className="brand-info">
          <h2>최저가 브랜드: {data.brand?.name || 'N/A'}</h2>
        </div>

        <div className="table-container">
          <table className="price-table">
            <thead>
              <tr>
                <th>카테고리</th>
                <th>가격</th>
              </tr>
            </thead>
            <tbody>
              {data.categoryProducts && data.categoryProducts.length > 0 ? (
                data.categoryProducts.map((item, index) => (
                  <tr key={index}>
                    <td className="category-name">{item.category?.name || 'N/A'}</td>
                    <td className="price">{formatPrice(item.product?.price)}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="2" style={{textAlign: 'center', padding: '20px', color: '#666'}}>
                    데이터가 없습니다.
                  </td>
                </tr>
              )}
              {data.sum && (
                <tr className="total-row">
                  <td className="total-label">총액</td>
                  <td className="total-price">{formatPrice(data.sum)}</td>
                </tr>
              )}
            </tbody>
          </table>
        </div>

        <div className="summary">
          <p>한 브랜드에서 모든 카테고리의 상품을 구매할 때 가장 저렴한 브랜드와 총액을 보여줍니다.</p>
        </div>
      </div>
    </div>
  );
};

export default BrandLowPricePage;
