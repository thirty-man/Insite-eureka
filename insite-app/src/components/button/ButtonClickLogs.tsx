import { getButtonLogs } from "@api/accumulApi";
import getButtonList from "@api/memberApi";
import { activeuserclickavg, clickavgexit } from "@assets/icons";
import {
  Border,
  StyledTable,
  TableBody,
  TableCell,
  TableHeader,
  TableRow,
} from "@assets/styles/tableStyles";
import { ImageBox, TextBox } from "@components/common";
import DropDown from "@components/common/dropdown/DropDown";
import { ButtonLogDtoType, ButtonType, ItemType } from "@customtypes/dataTypes";
import { RootState } from "@reducer";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import styled from "styled-components";

const Outer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  margin: 0;
`;

const OutDiv = styled.div`
  display: flex;
  justify-content: space-evenly;
  width: 100%;
  height: 100%;
  margin-top: 1rem;
`;

const IconDiv = styled.div`
  display: flex;
  flex-direction: column;
  margin: 0;
  width: 20%;
  justify-content: start;
  align-items: center;
`;

const InnerIconBox = styled.div`
  display: flex;
  width: 100%;
  justify-content: center;
  align-items: center;
  margin-bottom: 1rem;
`;

const NumberDiv = styled.div`
  display: flex;
  width: 80%;
`;

function ButtonClickLogs() {
  const [buttonList, setButtonList] = useState<ButtonType[]>([]);
  const [selectedButton, setSelectedButton] =
    useState<string>("버튼을 선택해주세요");
  const [isDropdown, setIsDropdown] = useState<boolean>(false);
  const [data, setData] = useState<ButtonLogDtoType[]>([]);
  const [exitRate, setExitRate] = useState<number>(0);
  const [clickCountsPerActiveUsers, setClickCountsPerActiveUsers] =
    useState<number>(0);

  const startDateTime = useSelector(
    (state: RootState) => state.dateSelectionInfo.start,
  );

  const endDateTime = useSelector(
    (state: RootState) => state.dateSelectionInfo.end,
  );

  const handleSelectedButton = (item: ItemType) => {
    setSelectedButton(item.name);
  };

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

  useEffect(() => {
    const parseStartDateTime = new Date(startDateTime);
    const parseEndDateTime = new Date(endDateTime);

    const fetchData = async () => {
      try {
        const response = await getButtonLogs(
          parseStartDateTime,
          parseEndDateTime,
          selectedButton,
        );
        if (!response) {
          setData([]);
          setExitRate(0);
          setClickCountsPerActiveUsers(0);
        } else {
          setData(response.buttonLogDtoList);
          setExitRate(response.exitRate);
          setClickCountsPerActiveUsers(response.clickCountsPerActiveUsers);
        }
      } catch (error) {
        // eslint-disable-next-line no-console
        // console.error(error); // 에러 처리
      }
    };

    fetchData();
  }, [endDateTime, selectedButton, startDateTime]);

  return (
    <Outer>
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
      <OutDiv>
        <TextBox width="70%" height="90%">
          <Border>
            <StyledTable>
              <TableHeader>
                <tr>
                  <th>순위</th>
                  <th>URL</th>
                  <th>클릭 시간</th>
                  <th>쿠키 아이디</th>
                  <th>비정상 접근</th>
                </tr>
              </TableHeader>
              <TableBody>
                {data.map((item, index) => (
                  <TableRow key={item.id}>
                    <TableCell>{index + 1}</TableCell>
                    <TableCell>{item.currentUrl}</TableCell>
                    <TableCell>{item.clickDateTime}</TableCell>
                    <TableCell>{item.cookieId}</TableCell>
                    <TableCell>
                      <div style={{ color: item.isAbnormal ? "red" : "green" }}>
                        {item.isAbnormal ? "비정상" : "정상"}
                      </div>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </StyledTable>
          </Border>
        </TextBox>
        <IconDiv>
          <div>클릭 후 평균 이탈율</div>
          <InnerIconBox>
            <ImageBox
              width="4rem"
              height="4rem"
              src={clickavgexit}
              alt="클릭 후 평균 이탈율"
            />
            <NumberDiv>{exitRate}</NumberDiv>
          </InnerIconBox>
          <div>사용자당 클릭 수 평균</div>
          <InnerIconBox>
            <ImageBox
              width="4rem"
              height="4rem"
              src={activeuserclickavg}
              alt="사용자당 클릭 수 평균"
            />
            <NumberDiv>{clickCountsPerActiveUsers}</NumberDiv>
          </InnerIconBox>
        </IconDiv>
      </OutDiv>
    </Outer>
  );
}

export default ButtonClickLogs;
