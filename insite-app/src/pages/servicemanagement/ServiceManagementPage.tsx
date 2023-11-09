import TextBox from "@components/common/TextBox";
import styled from "styled-components";
import { DefaultBox } from "@components/common";
import {useState,useEffect} from "react"; 
import axios from "axios";
import { ButtonType } from "@customtypes/dataTypes";

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
  background-color: #4caf50; /* 배경색 */
  color: white;
  border: none;
  border-radius: 4px;
  transition: background-color 0.3s;
  text-align:center;
  &:hover {
    background-color: #45a049; /* 호버링 시 배경색 변경 */
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
  const [buttonList,setButtonList]=useState<ButtonType[]>([]);
  useEffect(()=>{
    const data={
      "applicationToken":"token"
    }
      axios.post("http://localhost:8082/api/v1/buttons",data)
      .then(response=>{
        console.log('API 호출 성공',response.data);
        setButtonList(response.data);
      }).catch(error=>{
        console.error('API 호출 실패', error);
      })
    
  })

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
              {buttonList.map((button:ButtonType)=>(
                <TextBox width="500px" height="50px">
                <p>{button.name}</p>
              </TextBox>
              ))}
              
            </div>
            <div>
              <div>
                <AddButton>버튼 추가하기</AddButton>
              </div>
            </div>
            </div>
          </DefaultBox>
        </div>
      </div>
    </ManagementStyle>
  );
}

export default ServiceManagementPage;
