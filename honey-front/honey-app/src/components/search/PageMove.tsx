import { ImageButton, TextButton } from "@components/common/button";
import { TextInput } from "@components/common/input";
import numberOrEmptyStringRegex from "@config/regex";
import {
  inputSearchState,
  roomListState,
  selectedPageState,
} from "@recoil/atom";
import { useState, useEffect } from "react";
import { useRecoilState } from "recoil";
import { pageButton1, pageButton2 } from "@assets/images";
import { PageType, RoomType } from "@customtype/dataTypes";
import axios from "axios";

type PageButtonType = {
  id: number;
  alt: string;
  onClick: () => void;
  image: string;
};

function PageMove() {
  const one = pageButton1;
  const two = pageButton2;
  const [strNum, setStrNum] = useState("");
  const [num, setNum] = useState(1);
  const [, setRoomList] = useRecoilState<RoomType[]>(roomListState);
  const [pageInfo, setPageInfo] = useRecoilState<PageType>(selectedPageState);
  const [inputSearch] = useRecoilState<string>(inputSearchState);
  const token = sessionStorage.getItem("Authorization");
  const config = {
    headers: {
      Authorization: token,
    },
  };

  const goToFirst = () => {
    if (pageInfo.currentPage === 0) {
      axios
        .get(
          `http://localhost:8080/api/v1/rooms?title=${inputSearch}&page=0`,
          config,
        )
        .then((response) => {
          const { data } = response;
          const getRoomList = data.roomSearchDtoList;

          // Recoil 상태 업데이트
          if (getRoomList.length > 0) {
            setRoomList(getRoomList);
          }
          const newPage: PageType = {
            currentPage: 0,
            hasNext: true,
            totalPages: data.totalPages,
          };

          setPageInfo(newPage);
        })
        .catch((error) => {
          console.error("PageMove : goToFirst Err:", error);
        });
    } else {
      alert("첫 페이지입니다.");
    }
  };

  const goToBefore = () => {
    if (pageInfo.currentPage > 0) {
      axios
        .get(
          `http://localhost:8080/api/v1/rooms?title=${inputSearch}&page=${
            pageInfo.currentPage - 1
          }`,
          config,
        )
        .then((response) => {
          const { data } = response;
          const getRoomList = data.roomSearchDtoList;
          console.log(data);

          // Recoil 상태 업데이트
          if (getRoomList.length > 0) {
            setRoomList(getRoomList);
          }

          const newPage: PageType = {
            currentPage: data.currentPage,
            hasNext: data.hasNext,
            totalPages: data.totalPages,
          };

          setPageInfo(newPage);
        })
        .catch((error) => {
          console.error("PageMove : goToBefore Err:", error);
        });
    } else {
      alert("첫 페이지입니다.");
    }
  };

  const goToNext = () => {
    if (pageInfo.hasNext) {
      axios
        .get(
          `http://localhost:8080/api/v1/rooms?title=${inputSearch}&page=${
            pageInfo.currentPage + 1
          }`,
          config,
        )
        .then((response) => {
          const { data } = response;
          const getRoomList = data.roomSearchDtoList;
          console.log(data);

          // Recoil 상태 업데이트
          if (getRoomList.length > 0) {
            setRoomList(getRoomList);
          }

          const newPage: PageType = {
            currentPage: data.currentPage,
            hasNext: data.hasNext,
            totalPages: data.totalPages,
          };

          setPageInfo(newPage);
        })
        .catch((error) => {
          console.error("PageMove : goToBefore Err:", error);
        });
    } else {
      alert("마지막 페이지입니다.");
    }
  };

  const goToLast = () => {
    if (pageInfo.currentPage < pageInfo.totalPages - 1) {
      console.log(pageInfo.totalPages);
      axios
        .get(
          `http://localhost:8080/api/v1/rooms?title=${inputSearch}&page=${
            pageInfo.totalPages - 1
          }`,
          config,
        )
        .then((response) => {
          const { data } = response;
          const getRoomList = data.roomSearchDtoList;

          // Recoil 상태 업데이트
          if (getRoomList.length > 0) {
            setRoomList(getRoomList);
          }
          const newPage: PageType = {
            currentPage: data.totalPages - 1,
            hasNext: false,
            totalPages: data.totalPages,
          };

          setPageInfo(newPage);
        })
        .catch((error) => {
          console.error("PageMove : goToLast Err:", error);
        });
    } else {
      alert("마지막 페이지입니다.");
    }
  };

  const goToTargetPage = () => {
    if (num === 0) {
      alert("0이상의 수를 입력해주세요");
    } else if (num === pageInfo.currentPage + 1) {
      console.log("현재 페이지입니다.");
    } else {
      axios
        .get(
          `http://localhost:8080/api/v1/rooms?title=${inputSearch}&page=${
            num - 1
          }`,
          config,
        )
        .then((response) => {
          const { data } = response;
          const getRoomList = data.roomSearchDtoList;

          // Recoil 상태 업데이트
          if (getRoomList.length > 0) {
            setRoomList(getRoomList);
          }
          const newPage: PageType = {
            currentPage: data.currentPage,
            hasNext: data.hasNext,
            totalPages: data.totalPages,
          };

          setPageInfo(newPage);
        })
        .catch((error) => {
          console.error("PageMove : goToTarget Err:", error);
        });
    }
  };

  const handleNum = (e: React.ChangeEvent<HTMLInputElement>) => {
    const input = e.target.value;

    if (numberOrEmptyStringRegex.test(input) || input === "") {
      const parsedNum = parseInt(input, 10);

      if (parsedNum > pageInfo.totalPages) {
        alert(`입력한 숫자는 최대 페이지 수를 초과합니다.`);
      } else {
        setStrNum(input);
        setNum(parsedNum);
      }
    } else {
      setStrNum("");
      setNum(0);
      alert("숫자를 입력하세요.");
    }
  };

  useEffect(() => {
    const parsedNum = parseInt(strNum, 10);
    if (!Number.isNaN(parsedNum)) {
      setNum(parsedNum);
    }
  }, [strNum, num]);

  const pageButtons: PageButtonType[] = [
    { id: 1, alt: "처음", onClick: () => goToFirst(), image: two },
    { id: 2, alt: "이전", onClick: () => goToBefore(), image: one },
    { id: 3, alt: "중간", onClick: () => goToBefore(), image: one },
    { id: 4, alt: "다음", onClick: () => goToNext(), image: one },
    { id: 5, alt: "마지막", onClick: () => goToLast(), image: two },
  ];

  return (
    <div className="flex justify-center">
      {pageButtons.map((button) => (
        <div key={button.id} className="flex w-full justify-around">
          {button.id === 3 ? (
            <div className="flex justify-center items-center">
              <TextInput
                value={strNum}
                holder=""
                readonly={false}
                className="w-[60%] bg-cg-2 h-[30px] sm:text-[15px] text-[10px] overflow-x-auto"
                onChange={handleNum}
                onKeyDown={(e) => e.key === "Enter" && goToTargetPage()}
              />
              <div className="w-[80%] sm:text-[15px] text-[10px]">
                {pageInfo.currentPage + 1} / {pageInfo.totalPages}
              </div>
              <TextButton
                text="이동"
                color="3"
                className="rounded-xl sm:text-[15px] text-[10px] w-[60%] h-[30px] m-1"
                onClick={() => goToTargetPage()}
              />
            </div>
          ) : (
            <ImageButton
              key={button.id}
              image={button.image}
              alt={button.alt}
              className={
                button.id === 4 || button.id === 5
                  ? "w-[30%] rotate-180"
                  : "w-[30%]"
              }
              onClick={button.onClick}
            />
          )}
        </div>
      ))}
    </div>
  );
}

export default PageMove;
