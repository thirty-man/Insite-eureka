import { ImageButton, TextButton } from "@components/common/button";
import { TextInput } from "@components/common/input";
import numberOrEmptyStringRegex from "@config/regex";
import { roomListState } from "@recoil/atom";
import { useState, useEffect } from "react";
import { useRecoilValue } from "recoil";

type PageButtonType = {
  id: number;
  alt: string;
  onClick: () => void;
  image: string;
};

function PageMove() {
  const one: string = "./src/assets/images/pagebutton1.png";
  const two: string = "./src/assets/images/pagebutton2.png";
  const [strNum, setStrNum] = useState("");
  const [num, setNum] = useState(0);
  const totalNum = Math.max(
    1,
    Math.ceil(useRecoilValue(roomListState).length / 5),
  );

  const goToFirst = () => {};
  const goToBefore = () => {};
  const goToNext = () => {};
  const goToLast = () => {};
  const goToTargetPage = () => {
    if (num === 0) {
      console.log("no null");
    } else {
      console.log(num);
    }
  };

  const handleNum = (e: React.ChangeEvent<HTMLInputElement>) => {
    const input = e.target.value;

    if (numberOrEmptyStringRegex.test(input) || input === "") {
      const parsedNum = parseInt(input, 10);

      if (parsedNum > totalNum) {
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
    { id: 1, alt: "처음", onClick: () => goToFirst, image: two },
    { id: 2, alt: "이전", onClick: () => goToBefore, image: one },
    { id: 3, alt: "중간", onClick: () => goToBefore, image: one },
    { id: 4, alt: "다음", onClick: () => goToNext, image: one },
    { id: 5, alt: "마지막", onClick: () => goToLast, image: two },
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
                className="w-[60%] bg-cg-2 h-[30px] sm:text-[15px] text-[10px] overflow-x-auto"
                onChange={handleNum}
                onKeyDown={(e) => e.key === "Enter" && goToTargetPage()}
              />
              <div className="w-[50%] sm:text-[15px] text-[10px]">
                / {totalNum}
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
