import React from 'react';
import './BrandList.css';

const BrandList = ({ brands, loading }) => {
  if (loading) {
    return <div className="loading">브랜드 목록을 불러오는 중...</div>;
  }

  return (
    <div className="brand-list">
      <table className="brand-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>브랜드명</th>
            <th>설명</th>
          </tr>
        </thead>
        <tbody>
          {brands.length === 0 ? (
            <tr>
              <td colSpan="3" className="no-data">등록된 브랜드가 없습니다.</td>
            </tr>
          ) : (
            brands.map((brand) => (
              <tr key={brand.id}>
                <td>{brand.id}</td>
                <td className="brand-name">{brand.name}</td>
                <td>{brand.description || ''}</td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
};

export default BrandList;
