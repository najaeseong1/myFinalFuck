import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './AdoptionStatus.scss';
import Swal from 'sweetalert2';

const AdoptionStatus = () => {
  // 더미데이터
  const AdoptionStatuslist = {
    profile_img:
      'https://image.dongascience.com/Photo/2020/03/5bddba7b6574b95d37b6079c199d7101.jpg',
    name: '말티즈',
    gender: 'M',
    age: '2016(년생)',
    neuter_yn: 'Y',
  };

  // 결과 확인 버튼 클릭시 모달창 open!

  const handleShowModal = () => {
    Swal.fire({
      title: '승인되었습니다.',
      width: 600,
      padding: '3em',
      color: '#e89b93',
      backdrop: `
        rgba(0, 0, 0, 0.3)
        no-repeat
      `,
    });
  };

  return (
    <div className='mypage-fixed'>
      <div className='group-wrapper'>
        <div className='group'>
          <div className='overlap'>
            <button className='mypage-tap'>
              <Link to='/user/mypage'>마이페이지</Link>
            </button>
          </div>
          <button className='modify-tap'>
            <Link to='/user/modify'>개인정보변경</Link>
          </button>
          <button className='adoptionstatus'>
            <Link to='/user/adoptionstatus'>입양신청현황</Link>
          </button>
          <button className='like-list-tap'>
            <Link to='/user/likelist'>좋아요목록</Link>
          </button>
          <button className='order-history'>
            <Link to='/user/orderhistory'>주문 현황</Link>
          </button>
          <div className='mypage-title'>
            <span> 입양 신청 현황</span>
          </div>
          <div className='adoption-status'>
            <img
              src={AdoptionStatuslist.profile_img}
              alt='강아지 사진'
            />
            <p className='dog-profil'>이름: {AdoptionStatuslist.name} </p>
            <p className='dog-profil'>나이: {AdoptionStatuslist.age} </p>
            <p className='dog-profil'>성별: {AdoptionStatuslist.gender} </p>
            <p className='dog-profil'> 견종: {AdoptionStatuslist.neuter_yn} </p>
            <button onClick={handleShowModal}>결과확인</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdoptionStatus;
