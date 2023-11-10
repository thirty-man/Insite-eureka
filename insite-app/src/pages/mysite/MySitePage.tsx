import React from 'react';
import { Link } from 'react-router-dom';
import { BackgroundDiv, TextBox } from "@components/common";
import MainHeader from "@components/common/header/MainHeader";
import styled from "styled-components";
import { ItemType } from "@customtypes/dataTypes";
import SiteList from "@components/common/dropdown/SiteList";
import { pig, plus } from "@assets/images"; // 이미지를 불러옴
import { useState } from "react";
import axios from 'axios';
import { insitePanda } from '@assets/images';

const MainContainer = styled.div`
  width: 80%;
  height: 94%;
`;

const OutletContainer = styled.div`
  overflow-y: auto;
  width: 100%;
  height: 90%;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  grid-gap: 1%;
  color: white;
`;

const MySitePageStyle = styled.div`
  margin: 5%;
  min-height: 300px;
  padding: 20px;
  box-sizing: border-box;
`;

const Image = styled.img`
  width: 50%;
  height: 50%;
  object-fit: cover;
  top: 0;
  left: 0;
  opacity: 0.8; /* 이미지를 투명하게 만들어 백그라운드처럼 보이게 함 */
`;

const ModalBackground = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 80%; /* 모달의 가로 크기를 조절할 수 있습니다. */
  max-width: 600px; /* 모달의 최대 가로 크기를 설정할 수 있습니다. */
  height: auto; /* 내용에 맞게 높이를 조절합니다. */
  opacity:100%;
  display: flex;
  justify-content: center;
  align-items: center;
`;
const ModalContent = styled.div`
  background: #333744;
  padding: 40px; /* 모달 크기 조절 */
  border-radius: 8px;
`;

const InputField = styled.input`
  width: 93%;
  margin-bottom: 15px; /* 간격 조절 */
  padding: 15px; /* 텍스트 필드 크기 조절 */
  font-size: 16px; /* 폰트 크기 조절 */
`;

const ButtonContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;

const ConfirmButton = styled.button`
  flex: 1;
  margin-right: 10px; /* 간격 조절 */
  padding: 20px; /* 버튼 크기 조절 */
  font-size: 18px; /* 폰트 크기 조절 */
  cursor: pointer;
  background-color: #4caf50; /* 배경색 */
  color: white;
  border: none;
  border-radius: 4px;
  transition: background-color 0.3s;
  
  &:hover {
    background-color: #45a049; /* 호버링 시 배경색 변경 */
  }
`;

const CancelButton = styled.button`
  flex: 1;
  margin-left: 10px; /* 간격 조절 */
  padding: 20px; /* 버튼 크기 조절 */
  font-size: 18px; /* 폰트 크기 조절 */
  cursor: pointer;
  background-color: #f44336; /* 배경색 */
  color: white;
  border: none;
  border-radius: 4px;
  transition: background-color 0.3s;

  &:hover {
    background-color: #d32f2f; /* 호버링 시 배경색 변경 */
  }
`;


function MySitePage() {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };
  const [serviceName, setServiceName] = useState('');
  const [serviceUrl, setServiceUrl] = useState('');

  const handleServiceNameChange = (event:any) => {
    setServiceName(event.target.value);
  };

  const handleServiceUrlChange = (event:any) => {
    setServiceUrl(event.target.value);
  };

  const handleConfirm = () => {
    // 서버 API 호출 로직 추가
    const data = {
      name: serviceName,
      applicationUrl: serviceUrl,
      
    };
    // API 호출
  axios.post('http://localhost:8081/api/v1/application/regist', data)
  .then(response => {
    // API 호출이 성공하면 모달을 닫을 수 있도록 closeModal 호출
    console.log('API 호출 성공', response.data);
    
    closeModal();
    window.location.reload();
  })
  .catch(error => {
    // API 호출이 실패하면 에러를 처리할 수 있도록 추가 작업 필요
    console.error('API 호출 실패', error);
    // 에러 처리 로직 추가
  });
    
  setServiceName('');
  setServiceUrl('');

    // 모달 닫기
    closeModal();
  };

  const handleCancel = () => {
    // 모달 닫기
    closeModal();
    setServiceName('');
    setServiceUrl('');  
  };

  return (
    <BackgroundDiv>
      <MainContainer>
        <MainHeader />
        <OutletContainer>
          {SiteList.map((item: ItemType) => (
            <MySitePageStyle key={item.id}>
              <Link to={`/`}>
                <TextBox width="100%" height="100%">
                <img
                    src={insitePanda}
                    alt="Panda"
                    style={{ width: '30%', height: '30%', objectFit: 'cover', top: 0, left: 0, opacity: 0.8 }}
                  />
                  {item.name}
                </TextBox>
              </Link>
            </MySitePageStyle>
          ))}
          <MySitePageStyle>
            <div onClick={openModal} style={{ width: '100%', height: '100%',cursor: 'pointer'}}>
              <TextBox width="100%" height="100%">
              <Image src={plus} alt="Pig Image" />
              </TextBox>
            </div>
          </MySitePageStyle>
        </OutletContainer>
      </MainContainer>
      {isModalOpen && (
        <ModalBackground>
          <ModalContent>
          {/* 모달 내용 */}
          <h2 style={{color:"white"}}>서비스 추가하기</h2>
          <br/>
          <InputField
            type="text"
            placeholder="서비스명"
            value={serviceName}
            onChange={handleServiceNameChange}
          />
          <InputField
            type="text"
            placeholder="서비스URL"
            value={serviceUrl}
            onChange={handleServiceUrlChange}
          />
          <ButtonContainer>
            <ConfirmButton onClick={handleConfirm}  >확인</ConfirmButton>
            
            <CancelButton onClick={handleCancel}>취소</CancelButton>
          </ButtonContainer>
        </ModalContent>
        </ModalBackground>
      )}
    </BackgroundDiv>
  );
}

export default MySitePage;
