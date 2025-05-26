import React, { useState } from 'react';
import './BrandForm.css';

const BrandForm = ({ onSubmit }) => {
  const [formData, setFormData] = useState({
    name: '',
    description: ''
  });
  const [submitting, setSubmitting] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!formData.name.trim()) {
      alert('브랜드명을 입력해주세요.');
      return;
    }

    setSubmitting(true);
    const success = await onSubmit(formData);
    
    if (success) {
      setFormData({ name: '', description: '' }); // 폼 초기화
    }
    
    setSubmitting(false);
  };

  return (
    <form onSubmit={handleSubmit} className="brand-form">
      <table className="form-table">
        <tbody>
          <tr>
            <td className="label">브랜드명:</td>
            <td>
              <input
                type="text"
                name="name"
                value={formData.name}
                onChange={handleChange}
                required
                disabled={submitting}
                className="form-input"
              />
            </td>
          </tr>
          <tr>
            <td className="label">설명:</td>
            <td>
              <textarea
                name="description"
                value={formData.description}
                onChange={handleChange}
                disabled={submitting}
                className="form-textarea"
                rows="3"
              />
            </td>
          </tr>
          <tr>
            <td></td>
            <td>
              <button 
                type="submit" 
                disabled={submitting}
                className="submit-btn"
              >
                {submitting ? '등록 중...' : '등록'}
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </form>
  );
};

export default BrandForm;
