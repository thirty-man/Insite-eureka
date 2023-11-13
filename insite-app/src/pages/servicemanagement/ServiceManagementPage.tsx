import ButtonBox from "@components/common/ButtonBox";
import TextBox from "@components/common/TextBox";
import styled from "styled-components";
import { DefaultBox } from "@components/common";
import {useState} from "react"; 
import axios from "axios";
import { ButtonType } from "@customtypes/dataTypes";
import ButtonList from "@components/ButtonList";

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
const AddButton = styled.div`
  flex: 1;
  margin-right: 10px; /* 간격 조절 */
  padding: 20px; /* 버튼 크기 조절 */
  font-size: 18px; /* 폰트 크기 조절 */
  cursor: pointer;
  background-color: #2CE8C7; /* 배경색 */
  color: black;
  border: none;
  border-radius: 4px;
  transition: background-color 0.3s;
  text-align:center;
  &:hover {
    background-color: #00E6FF; /* 호버링 시 배경색 변경 */
  }
  width:500px;
  height:auto;
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


function ServiceManagementPage() {
  const data = {
    applicationToken: 'your_application_token',
    applicationUrl: 'your_application_url',
    name: 'your_name',
    applicationId: 'your_application_id',
    createTime: 'your_create_time',
  };
  // const [buttonList,setButtonList]=useState<ButtonType[]>([]);
  // useEffect(()=>{
  //   const data={
  //     "applicationToken":"token"
  //   }
  //     axios.post("http://localhost:8082/api/v1/buttons",data)
  //     .then(response=>{
  //       console.log('API 호출 성공',response.data);
  //       setButtonList(response.data);
  //     }).catch(error=>{
  //       console.error('API 호출 실패', error);
  //     })
    
  // })
  //임시로 버튼 리스트를 만든 뒤 사용, 추후 서버와 연결할 때 사용할 것

  const [isModalOpen, setIsModalOpen] = useState(false);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };
  const [buttonName, setButtonName] = useState('');
  const [applicationToken, setApplicationToken] = useState('');

  const handleButtonNameChange = (event:any) => {
    setButtonName(event.target.value);
  };

  const handleApplicationTokenChange = (event:any) => {
    setApplicationToken(event.target.value);
  };

  const handleConfirm = () => {
    // 서버 API 호출 로직 추가
    const data = {
      name: buttonName,
      applicationToken: applicationToken,
      
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
    
  setButtonName('');
  setApplicationToken('');

    // 모달 닫기
    closeModal();
  };

  const handleCancel = () => {
    // 모달 닫기
    closeModal();
    setButtonName('');
    setApplicationToken('');  
  };



  return (
    <ManagementStyle>
      <div className="parent">
        <div className="child">
          <DefaultBox width="1000px" height="700px">
            <div >
            <br />
            <h1 >내 서비스 관리</h1>
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
                <p>{data.createTime}</p>
              </TextBox>
            </div>
            <div className="infoContainer">
              <p className="infoText">토큰 </p>
              <TextBox width="500px" height="50px">
                <p>{data.applicationToken}</p>
              </TextBox>
            </div>
            </div>
            
            <div style={{marginTop:'5%'}}>
            <div className="infoContainer">
              <p className="infoText">버튼 </p>
              <div style={{flexDirection:"column", overflow:"scroll",height:"200px", textAlign: "center"}} >
              {ButtonList&&ButtonList.length>0 ?(ButtonList.map((button: ButtonType)=>(
                <div  key={button.id}>
                <ButtonBox width="490px" height="50px" color="#00E6FF">
                <p>{button.name}</p>
              </ButtonBox>
              <br></br>
              
              </div>
              ))):(
              <ButtonBox width="490px" height="50px" color="#00E6FF">
              <p>버튼을 추가해 보세요.</p>
            </ButtonBox>)}
            </div>
            </div>
            </div>
              <div>
              <div onClick={openModal} style={{ width: '100%', height: '100%',cursor: 'pointer'}}>
              <AddButton>버튼 추가하기</AddButton>
            </div>
                
              </div>
          </DefaultBox>
        </div>
      </div>
      {isModalOpen && (
        <ModalBackground>
          <ModalContent>
          {/* 모달 내용 */}
          <h2 style={{color:"white"}}>버튼 추가하기</h2>
          <br/>
          <InputField
            type="text"
            placeholder="버튼명"
            value={buttonName}
            onChange={handleButtonNameChange}
          />
          <InputField
            type="text"
            placeholder="어플리케이션 토큰"
            value={applicationToken}
            onChange={handleApplicationTokenChange}
          />
          <ButtonContainer>
            <ConfirmButton onClick={handleConfirm}  >확인</ConfirmButton>
            
            <CancelButton onClick={handleCancel}>취소</CancelButton>
          </ButtonContainer>
        </ModalContent>
        </ModalBackground>
      )}
    </ManagementStyle>
  );
}

export default ServiceManagementPage;
