import ButtonBox from "@components/common/ButtonBox";
import TextBox from "@components/common/TextBox";
import styled from "styled-components";
import { DefaultBox } from "@components/common";
import { useState, useEffect } from "react";
import { ApplicationDtoType, ButtonType } from "@customtypes/dataTypes";
import { createButton, getButtonList, deleteApplication } from "@api/memberApi";


const ManagementStyle = styled.div`
  .parent {
    display: flex;
  }
  .child {
    margin: auto auto;
  }
  .infoContainer {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 20px;
    width: 700px; /* Set a fixed width for the infoContainer */
  }
  .infoText {
    margin: 0; /* Remove default margin */
  }
  .halfContainer {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 20px;
    margin-top: 40px;
  }
`;
// const AddButton = styled.button`
//   flex: 1;
//   justify-content: center;
//   text-align: center;
//   align-items: center;
//   margin-right: 10px; /* 간격 조절 */
//   padding: 20px; /* 버튼 크기 조절 */
//   font-size: 18px; /* 폰트 크기 조절 */
//   cursor: pointer;
//   background-color: #2ce8c7; /* 배경색 */
//   color: black;
//   border: none;
//   border-radius: 4px;
//   transition: background-color 0.3s;
//   text-align: center;
//   &:hover {
//     background-color: #00e6ff; /* 호버링 시 배경색 변경 */
//   }
//   width: 30rem;
//   height: auto;
// `;

const StyledButton = styled.button`
  background-image: linear-gradient(to right, #4776e6 0%, #8e54e9 51%, #4776e6);
  padding: 15px 30px;
  text-align: center;
  text-transform: uppercase;
  transition: 0.5s;
  background-size: 200% auto;
  color: white;
  box-shadow: 0 0 20px black;
  border-radius: 10px;
  display: block;
  width: 30rem;
  cursor: pointer;

  &:hover {
    background-color: white;
    color: white;
    text-decoration: none;
    transform: scale(1.1);
    transition: transform 0.3s ease;
  }

  &:active {
    transform: scale(0.96);
    transition: transform 0.1s;
  }

  &:focus {
    background-color: white;
  }
`;

const ModalBackground = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 80%; /* 모달의 가로 크기를 조절할 수 있습니다. */
  max-width: 600px; /* 모달의 최대 가로 크기를 설정할 수 있습니다. */
  height: auto; /* 내용에 맞게 높이를 조절합니다. */
  opacity: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`;
const ModalContent = styled.div`
  border: 1px solid black;
  background: #333744;
  padding: 40px; /* 모달 크기 조절 */
  border-radius: 8px;
  width: 20rem;
`;

const InputField = styled.input`
  width: 89%;
  margin-bottom: 15px; /* 간격 조절 */
  padding: 15px; /* 텍스트 필드 크기 조절 */
  font-size: 16px; /* 폰트 크기 조절 */
  background-color: #1e1f23;
  color: white;
  border: 1px solid black;
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
  background-color: #9051e4; /* 배경색 */
  color: white;
  border: none;
  border-radius: 4px;
  transition: background-color 0.3s;

  &:hover {
    background-color: #b990ec; /* 호버링 시 배경색 변경 */
  }
`;

const CancelButton = styled.button`
  flex: 1;
  margin-left: 10px; /* 간격 조절 */
  padding: 20px; /* 버튼 크기 조절 */
  font-size: 18px; /* 폰트 크기 조절 */
  cursor: pointer;
  background-color: #1e1f23; /* 배경색 */
  color: white;
  border: none;
  border-radius: 4px;
  transition: background-color 0.3s;

  &:hover {
    background-color: rgba(30, 31, 35, 0.6); /* 호버링 시 배경색 변경 */
  }
`;

const Title = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

function ServiceManagementPage() {
  const myApp =
    sessionStorage.getItem("myApp") ||
    `{"applicationId":0,"name":"사이트를 선택해주세요.","applicationUrl":"사이트를 선택해주세요", "applicationToken":"사이트를 선택해주세요"}`;

  const data: ApplicationDtoType = JSON.parse(myApp);
  const [buttonList, setButtonList] = useState<ButtonType[]>([]);
  const [buttonName, setButtonName] = useState("");
  const [isModalOpen, setIsModalOpen] = useState(false);

  // 버튼 리스트 가져오기
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await getButtonList();
        if (!response.buttonDtoList) setButtonList([]);
        else setButtonList(response.buttonDtoList);
      } catch (error) {
        // eslint-disable-next-line no-console
        // console.error(error); // 에러 처리
      }
    };

    fetchData();
  }, []);
  
  const deleteData = async() =>{
    
    try{
      const response = await deleteApplication(data.id);
      console.log("API 호출 성공",response);
      window.location.reload();
    }catch(error){
      console.error(error);
        }
  }

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const handleButtonNameChange = (
    event: React.ChangeEvent<HTMLInputElement>,
  ) => {
    setButtonName(event.target.value);
  };
  
  // 버튼 추가
  const handleConfirm = () => {
    const createData = async () => {
      try {
        const response = await createButton(buttonName);
        console.log("API 호출 성공", response);

        closeModal();
        window.location.reload();
      } catch (error) {
        // eslint-disable-next-line no-console
        console.error(error); // 에러 처리
      }
    };

    createData();
    setButtonName("");

    // 모달 닫기
    closeModal();
  };

  const handleCancel = () => {
    // 모달 닫기
    closeModal();
    setButtonName("");
  };

  return (
    <ManagementStyle>
      <div className="parent">
        <div className="child">
          <DefaultBox width="1000px" height="800px">
            <div>
              <br />
              <br />
              <Title>
                <h1>내 서비스 관리</h1>
              </Title>
              <ButtonContainer>
              <ConfirmButton onClick={deleteData}>삭제</ConfirmButton>
            </ButtonContainer>
              <br />
              <br />
              <div className="infoContainer">
                <p className="infoText">서비스 명 </p>
                <TextBox width="500px" height="50px">
                  <p>{data.name}</p>
                </TextBox>
              </div>
              <div className="infoContainer">
                <p className="infoText">URL </p>
                <TextBox width="500px" height="50px">
                  <p>{data.applicationUrl}</p>
                </TextBox>
              </div>
              <div className="infoContainer">
                <p className="infoText">등록일 </p>
                <TextBox width="500px" height="50px">
                  {/* <p>{data.createTime}</p> */}
                  <p>등록일 필요</p>
                </TextBox>
              </div>
              <div className="infoContainer">
                <p className="infoText">토큰 </p>
                <TextBox width="500px" height="50px">
                  <p>{data.applicationToken}</p>
                </TextBox>
              </div>
              
            </div>

            <div style={{ marginTop: "5%" }}>
              <div className="infoContainer">
                <p className="infoText">버튼 </p>
                <div
                  style={{
                    flexDirection: "column",
                    overflow: "scroll",
                    height: "200px",
                    textAlign: "center",
                  }}
                >
                  {buttonList && buttonList.length > 0 ? (
                    buttonList.map((button: ButtonType) => (
                      <div key={button.id}>
                        <ButtonBox width="490px" height="50px" color="#1e1f23">
                          <p>{button.name}</p>
                        </ButtonBox>
                        <br />
                      </div>
                    ))
                  ) : (
                    <ButtonBox width="490px" height="50px" color="#1e1f23">
                      <p>버튼을 추가해 보세요.</p>
                    </ButtonBox>
                  )}
                </div>
              </div>
            </div>
            <StyledButton type="button" onClick={openModal}>
              버튼 추가하기
            </StyledButton>
            
          </DefaultBox>
        </div>
      </div>
      {isModalOpen && (
        <ModalBackground>
          <ModalContent>
            {/* 모달 내용 */}
            <h2 style={{ color: "white" }}>버튼 추가하기</h2>
            <br />
            <InputField
              type="text"
              placeholder="버튼명"
              value={buttonName}
              onChange={handleButtonNameChange}
            />
            <ButtonContainer>
              <ConfirmButton onClick={handleConfirm}>확인</ConfirmButton>
              <CancelButton onClick={handleCancel}>취소</CancelButton>
            </ButtonContainer>
          </ModalContent>
        </ModalBackground>
      )}
    </ManagementStyle>
  );
}

export default ServiceManagementPage;
