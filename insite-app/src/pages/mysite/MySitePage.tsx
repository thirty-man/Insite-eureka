import { useEffect, useState } from "react";
import { BackgroundDiv, TextBox } from "@components/common";
import MainHeader2 from "@components/common/header/MainHeader2";
import styled from "styled-components";
import { plus, insitePanda } from "@assets/images"; // 이미지를 불러옴
import { ApplicationDtoType } from "@customtypes/dataTypes";
import { createStie, getSiteList } from "@api/memberApi";
import { useNavigate } from "react-router-dom";
import { useSpring } from "react-spring";
import { useDispatch } from "react-redux";
import { setPastDate, setStartDate } from "@reducer/DateSelectionInfo";

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
  width: 15rem;
  cursor: pointer;

  &:hover {
    background-color: white;
    color: white;
    text-decoration: none;
    transform: scale(1.05);
    transition: transform 0.3s ease;
  }

  &:active {
    transform: scale(0.99);
    transition: transform 0.1s;
  }

  &:focus {
    background-color: white;
  }
`;

const MainContainer = styled.div`
  width: 90%;
  height: 70%;
`;

const OverflowContainer = styled.div`
  overflow-y: auto; /* 스크롤이 필요한 경우에만 스크롤 표시 */
  width: 100%;
  height: 80%; /* 동적으로 계산되도록 설정 */
`;

const OutletContainer = styled.div`
  width: 100%;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  grid-gap: 1%;
  color: white;
`;

const MySitePageStyle = styled.div`
  margin: 5%;
  height: 20rem;
  width: 20rem;
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
  opacity: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`;
const ModalContent = styled.div`
  background: #333744;
  padding: 40px; /* 모달 크기 조절 */
  border-radius: 10px;
`;

const InputField = styled.input`
  width: 93%;
  margin-bottom: 15px; /* 간격 조절 */
  padding: 15px; /* 텍스트 필드 크기 조절 */
  font-size: 16px; /* 폰트 크기 조절 */
  background-color: #1e1f23;
  color: white;
  border: 1px solid black;
  border-radius: 10px;
`;

const ButtonContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;

const CancelButton = styled.button`
  flex: 1;
  margin-left: 10px; /* 간격 조절 */
  padding: 20px; /* 버튼 크기 조절 */
  cursor: pointer;
  background-color: #1e1f23; /* 배경색 */
  color: white;
  border: none;
  border-radius: 10px;
  transition: background-color 0.3s;

  &:hover {
    background-color: rgba(30, 31, 35, 0.6); /* 호버링 시 배경색 변경 */
  }
`;

function MySitePage() {
  const dispatch = useDispatch();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [serviceName, setServiceName] = useState("");
  const [serviceUrl, setServiceUrl] = useState("");
  const [siteList, setSiteList] = useState<ApplicationDtoType[]>([]);
  const navi = useNavigate();
  const [change, setChange] = useState<boolean>(false);

  // Spring values for the image animations based on scroll
  const [{ scrollY }, setScrollY] = useSpring(() => ({ scrollY: 0 }));

  // Update scrollY when the user scrolls
  useEffect(() => {
    const handleScroll = () => {
      setScrollY({ scrollY: window.scrollY });
    };
    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, [setScrollY]);

  // 등록된 사이트 리스트 가져오기
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await getSiteList();
        if (!response.applicationDtoList) setSiteList([]);
        else setSiteList(response.applicationDtoList);
        setChange(false);
      } catch (error) {
        // eslint-disable-next-line no-console
        // console.error(error); // 에러 처리
      }
    };

    fetchData();
  }, [change]);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const handleServiceNameChange = (
    event: React.ChangeEvent<HTMLInputElement>,
  ) => {
    setServiceName(event.target.value);
  };

  const handleServiceUrlChange = (
    event: React.ChangeEvent<HTMLInputElement>,
  ) => {
    setServiceUrl(event.target.value);
  };

  const handleConfirm = () => {
    // API 호출
    const fetchData = async () => {
      try {
        const response = await createStie(serviceName, serviceUrl);
        console.log("어플 등록 성공", response);
        setChange(true);
        closeModal();
        // window.location.reload();
      } catch (error) {
        // eslint-disable-next-line no-console
        console.error("등록에러: ", error); // 에러 처리
      }
    };

    fetchData();
    setServiceName("");
    setServiceUrl("");

    // 모달 닫기
    closeModal();
  };

  const handleCancel = () => {
    // 모달 닫기
    closeModal();
    setServiceName("");
    setServiceUrl("");
  };

  const selectSite = (item: ApplicationDtoType) => {
    const myApp = {
      applicationId: item.id,
      name: item.name,
      applicationUrl: item.applicationUrl,
      applicationToken: item.applicationToken,
      createTime: item.createTime,
    };
    sessionStorage.setItem("myApp", JSON.stringify(myApp));
    dispatch(setStartDate(item.createTime));
    dispatch(setPastDate(item.createTime));
    navi("/board");
    window.location.reload();
  };

  return (
    <BackgroundDiv>
      <MainContainer>
        <MainHeader2 scrollY={scrollY} />
        <OverflowContainer>
          <OutletContainer>
            {siteList.map((item: ApplicationDtoType) => (
              <MySitePageStyle key={item.id}>
                <button
                  type="button"
                  style={{
                    height: "100%",
                    width: "100%",
                    backgroundColor: "transparent",
                    cursor: "pointer",
                  }}
                  onClick={() => selectSite(item)}
                >
                  <TextBox width="100%" height="100%">
                    <img
                      src={insitePanda}
                      alt="Panda"
                      style={{
                        width: "30%",
                        height: "30%",
                        objectFit: "cover",
                        top: 0,
                        left: 0,
                        opacity: 0.8,
                      }}
                    />
                    {item.name}
                  </TextBox>
                </button>
              </MySitePageStyle>
            ))}
            <MySitePageStyle>
              <button
                type="button"
                onClick={openModal}
                style={{
                  width: "100%",
                  height: "100%",
                  cursor: "pointer",
                  backgroundColor: "transparent",
                }}
              >
                <TextBox width="100%" height="100%">
                  <Image src={plus} alt="Add Image" />
                </TextBox>
              </button>
            </MySitePageStyle>
          </OutletContainer>
        </OverflowContainer>
      </MainContainer>
      {isModalOpen && (
        <ModalBackground>
          <ModalContent>
            {/* 모달 내용 */}
            <h2 style={{ color: "white" }}>서비스 추가하기</h2>
            <br />
            <InputField
              type="text"
              placeholder="ex) 꿀스라이팅"
              value={serviceName}
              onChange={handleServiceNameChange}
            />
            <InputField
              type="text"
              placeholder="ex) https://rollinghoney.com"
              value={serviceUrl}
              onChange={handleServiceUrlChange}
            />
            <ButtonContainer>
              <StyledButton onClick={handleConfirm}>확인</StyledButton>
              <CancelButton onClick={handleCancel}>취소</CancelButton>
            </ButtonContainer>
          </ModalContent>
        </ModalBackground>
      )}
    </BackgroundDiv>
  );
}

export default MySitePage;
