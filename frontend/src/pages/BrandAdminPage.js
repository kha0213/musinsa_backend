import React, { useState, useEffect } from 'react';
import { brandService } from '../services/api';
import BrandForm from '../components/BrandForm';
import BrandList from '../components/BrandList';
import './BrandAdminPage.css';

const BrandAdminPage = () => {
  const [brands, setBrands] = useState([]);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  useEffect(() => {
    loadBrands();
  }, []);

  const loadBrands = async () => {
    try {
      setLoading(true);
      const response = await brandService.getAllBrands();
      setBrands(response.data);
    } catch (err) {
      setError('브랜드 목록을 불러오는 중 오류가 발생했습니다.');
      console.error('Error loading brands:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleBrandCreate = async (brandData) => {
    try {
      const response = await brandService.createBrand(brandData);
      setMessage(`브랜드 "${response.data.name}"가 성공적으로 등록되었습니다.`);
      setError('');
      await loadBrands(); // 목록 새로고침
      return true; // 성공 표시
    } catch (err) {
      setError(err.message || '브랜드 등록 중 오류가 발생했습니다.');
      setMessage('');
      return false; // 실패 표시
    }
  };

  const clearMessages = () => {
    setMessage('');
    setError('');
  };

  return (
    <div className="brand-admin-page">
      <h1>브랜드 등록</h1>
      
      {/* 메시지 영역 */}
      {message && (
        <div className="message success">
          {message}
          <button onClick={clearMessages} className="close-btn">×</button>
        </div>
      )}
      {error && (
        <div className="message error">
          {error}
          <button onClick={clearMessages} className="close-btn">×</button>
        </div>
      )}
      
      {/* 브랜드 등록 폼 */}
      <BrandForm onSubmit={handleBrandCreate} />
      
      <hr />
      
      {/* 브랜드 목록 */}
      <h2>등록된 브랜드 목록</h2>
      <BrandList brands={brands} loading={loading} />
      
      <div className="navigation">
        <a href="/" className="home-link">홈으로</a>
      </div>
    </div>
  );
};

export default BrandAdminPage;
