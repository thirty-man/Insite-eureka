import ClickCount from "@components/button";
import { DefaultBox, TextBox, TitleBox } from "@components/common";
import { ButtonType, ItemType } from "@customtypes/dataTypes";
import styled from "styled-components";
import { useState, useEffect } from "react";
import getButtonList from "@api/memberApi";
import DropDown from "@components/common/dropdown/DropDown";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "@reducer";
import { setSelectedButton } from "@reducer/SelectedItemInfo";

const FirstCol = styled.div`
  display: flex;
  width: 100%;
  top: 0;
  right: 0;
  background-color: ${(props) => props.theme.colors.b2};
`;

const ContentDiv = styled.div`
  display: flex;
  flex-direction: column;
  font-size: 20px;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 90%;
`;

const SecondCol = styled.div`
  display: flex;
  width: 100%;
  top: 0;
  right: 0;
  background-color: ${(props) => props.theme.colors.b2};
`;

function ButtonManagementPage() {
  const dispatch = useDispatch();
  const [buttonList, setButtonList] = useState<ButtonType[]>([]);
  const [isDropdown, setIsDropdown] = useState<boolean>(false);

  const selectedButton = useSelector(
    (state: RootState) => state.SelectedItemInfo.selectedButton,
  );

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await getButtonList();
        setButtonList(response.buttonDtoList);
      } catch (error) {
        // eslint-disable-next-line no-console
        console.error(error); // 에러 처리
      }
    };

    fetchData();
  }, []);

  const handleSelectedButton = (item: ItemType) => {
    dispatch(setSelectedButton(item.name));
  };

  return (
    <>
      <FirstCol>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox width="" height="10%">
            버튼 통계
          </TitleBox>
          <ContentDiv>{/* <ClickCount /> */}</ContentDiv>
        </DefaultBox>
        <DefaultBox width="62rem" height="25rem">
          <TitleBox width="" height="10%">
            버튼 클릭 로그
          </TitleBox>
          <TextBox width="90%" height="80%">
            표
          </TextBox>
        </DefaultBox>
      </FirstCol>
      <SecondCol>
        <DefaultBox width="102rem" height="25rem">
          <TitleBox width="" height="10%">
            버튼 누른 횟수
          </TitleBox>
          <DropDown
            items={buttonList}
            width="15rem"
            height="2rem"
            initialValue={selectedButton}
            onChange={handleSelectedButton}
            openDropdown={isDropdown}
            close={() => setIsDropdown(false)}
            toggle={() => setIsDropdown(!isDropdown)}
          />
          <ContentDiv>
            <ClickCount />
          </ContentDiv>
        </DefaultBox>
      </SecondCol>
    </>
  );
}

export default ButtonManagementPage;
