import { Alert, Modal } from "@components/common/modal";
import { useState, useEffect } from "react";
import useRouter from "@hooks/useRouter";
import { Calendar, TimePicker } from "@components/common/calendar";
import moment from "moment";
import axios, { AxiosError } from "axios";
import { useRecoilState } from "recoil";
import successCreateRoomState from "@recoil/atom/successCreateRoomState";

type ValuePiece = Date | null;
type Value = ValuePiece | [ValuePiece, ValuePiece];
function CreateRoom() {
  const { routeTo } = useRouter();
  const { VITE_API_URL } = import.meta.env;
  const [roomName, setRoomName] = useState<string>("");
  const [today, setToday] = useState<Date>(new Date());
  const [releaseDate, setReleaseDate] = useState<string>("");
  const [time, setTime] = useState<string | null>("00:00");
  const [boxChecked, setBoxChecked] = useState<boolean>(false);
  const [openCalendar, setOpenCalendar] = useState<boolean>(false);
  const [roomNameFocused, setRoomNameFocused] = useState<boolean>(false);
  const [roomPassword, setRoomPassword] = useState<string>("");
  const [roomPasswordFocused, setRoomPasswordFocused] =
    useState<boolean>(false);

  const [alertModal, setAlertModal] = useState<boolean>(false);
  const [alertText, setAlertText] = useState<string>("");
  const [, setSuccessCreateRoom] = useRecoilState<boolean>(
    successCreateRoomState,
  );

  useEffect(() => {
    const now = new Date();
    setToday(now);
    setReleaseDate(moment(now).format("YYYY년 MM월 DD일")); // 초기 날짜 설정
  }, []);

  const handleInputFocus = () => {
    setRoomNameFocused(true);
  };

  const handleInputBlur = () => {
    if (!roomName.trim()) {
      setRoomNameFocused(false);
    }
  };

  const handleRoomPasswordFocus = () => {
    setRoomPasswordFocused(true);
  };

  const handleRoomPasswordBlur = () => {
    if (!roomPassword.trim()) {
      setRoomPasswordFocused(false);
    }
  };

  const handleRoomName = (e: React.ChangeEvent<HTMLInputElement>) => {
    setRoomName(e.target.value);
  };

  const handleRoomPassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    const password = e.target.value;
    const newPassword = password.replace(/\s/g, "");
    setRoomPassword(newPassword);
  };

  const handleCheckboxToggle = () => {
    setBoxChecked(!boxChecked);
    setRoomPasswordFocused(false);
  };

  function parseKoreanDateString(dateString: string, timeString: string): Date {
    const dateMatches = dateString.match(/(\d+)년 (\d+)월 (\d+)일/);
    const timeMatches = timeString.match(/(\d+):(\d+)/);

    if (!dateMatches || !timeMatches) {
      throw new Error("Invalid date or time string format.");
    }

    const year = parseInt(dateMatches[1], 10);
    const month = parseInt(dateMatches[2], 10) - 1;
    const day = parseInt(dateMatches[3], 10);
    const hour = parseInt(timeMatches[1], 10);
    const minute = parseInt(timeMatches[2], 10);

    return new Date(year, month, day, hour, minute);
  }

  const handleDateChange = (value: Value) => {
    if (value instanceof Date && value !== null) {
      const selectedDate = value;
      const formattedDate = moment(selectedDate).format("YYYY년 MM월 DD일");

      if (selectedDate < moment().startOf("day").toDate()) {
        setAlertText("오늘 이전의 날짜는 선택할 수 없습니다.");
        setAlertModal(true);
      } else {
        setReleaseDate(formattedDate); // 날짜 선택 시 releaseDate 업데이트
        setToday(selectedDate);
      }
    } else {
      setReleaseDate("날짜 설정");
    }
  };

  const handleTimeChange = (value: string | null) => {
    setTime(value);
  };

  const handleCalendar = () => {
    setOpenCalendar((p) => !p);
  };

  const handleSetting = () => {
    const datePart = releaseDate.split(" ").slice(0, 3).join(" "); // 날짜 부분만 추출
    const newReleaseDate = `${datePart} ${time}`; // 새로운 시간과 결합
    setReleaseDate(newReleaseDate);

    const selectedDateTime = parseKoreanDateString(
      releaseDate,
      time || "00:00",
    );

    // 오늘 날짜와 현재 시간 이후인지 검증
    if (
      moment(selectedDateTime).isSame(moment(), "day") &&
      selectedDateTime < new Date()
    ) {
      setAlertText("공개 시각은 현재 시각 이후로 설정할 수 있습니다.");
      setAlertModal(true);
      return;
    }
    setOpenCalendar(false);
  };

  const submitRoomData = async () => {
    try {
      const token = sessionStorage.getItem("Authorization");
      const dateObject = parseKoreanDateString(releaseDate, time || "00:00");
      const formattedDate = moment(dateObject).format("YYYY-MM-DDTHH:mm:ss");

      const postData = {
        roomTitle: roomName,
        showTime: formattedDate,
        password: roomPassword,
      };

      const config = {
        headers: {
          Authorization: token,
        },
      };

      const response = await axios.post(
        `${VITE_API_URL}/api/v1/rooms/create`,
        postData,
        config,
      );
      console.log(response.data);
      setSuccessCreateRoom(true);
      routeTo("/");
    } catch (error: unknown) {
      if ((error as AxiosError).response) {
        const axiosError = error as AxiosError;
        if (axiosError.response) {
          console.error("Server Response: ", axiosError.response.data);
          console.error("Status Code: ", axiosError.response.status);
          if (axiosError.response.headers) {
            console.error("Headers: ", axiosError.response.headers);
          }
        } else if (axiosError.request) {
          console.error("No Response from Server: ", axiosError.request);
        } else {
          console.error("Error", axiosError.message);
        }
        console.error("Error Config: ", axiosError.config);
      }
    }
  };

  const submitCreateRoom = () => {
    const newRoomName = roomName.replace(/^\s+|\s+$|\n/g, "");
    setRoomName(newRoomName);
    if (newRoomName.trim() === "") {
      setAlertText("방제목을 입력하세요.");
      setAlertModal(true);
      // alert("방제목을 입력하세요."); alert1
      return;
    }
    if (releaseDate === null || releaseDate === "날짜 설정") {
      setAlertText("날짜를 설정해주세요.");
      setAlertModal(true);
      // alert("날짜를 설정해주세요."); alert 2
    }
    if (newRoomName.trim().length >= 50) {
      setAlertText("방제목은 50자 이하로 작성되어야합니다.");
      setAlertModal(true);
      // alert("방제목은 50자 이하로 작성되어야합니다."); alert 3
      return;
    }

    if (boxChecked) {
      const password = roomPassword;
      const passwordPattern = /^[A-Za-z0-9@$!%*?&]{8,16}$/;
      if (passwordPattern.test(password)) {
        setRoomPassword(password);
      } else {
        if (password === null || password === "") {
          setAlertText("비밀번호를 설정해주세요.");
          setAlertModal(true);
          // alert("비밀번호를 설정해주세요."); alert 4
          return;
        }
        setAlertText(
          "8자 이상 16자 이하의 영문 대소문자, 숫자, 특수문자를 입력해야 합니다.",
        );
        setAlertModal(true);
        // alert(
        //   "8자 이상 16자 이하의 영문 대소문자, 숫자, 특수문자를 입력해야 합니다.",
        // ); alert 5
        return;
      }
    }

    const selectedDateTime = parseKoreanDateString(
      releaseDate,
      time || "00:00",
    );

    // 오늘 날짜와 현재 시간 이후인지 검증
    if (
      moment(selectedDateTime).isSame(moment(), "day") &&
      selectedDateTime < new Date()
    ) {
      setAlertText("공개 시각은 현재 시각 이후로 설정할 수 있습니다.");
      setAlertModal(true);
      return;
    }
    setOpenCalendar(false);
    submitRoomData();
  };

  return (
    <>
      <div className="text-[30px] text-white">방 만들기</div>
      <div className="w-full h-full px-[30px]">
        <div className="w-full h-full rounded-[30px] bg-cg-7 flex flex-col items-center justify-around pb-[50px]">
          <div className="pt-[20px] pb-[8px] text-[30px] text-white">
            방제목
          </div>
          <input
            value={roomName}
            placeholder={roomNameFocused ? "" : "방제목을 입력해주세요."}
            className="w-[90%] h-[13%] min-h-[50px] text-[24px] bg-cg-2 rounded-2xl"
            onChange={handleRoomName}
            onFocus={handleInputFocus}
            onBlur={handleInputBlur}
          />
          <div className="pt-[20px] pb-[8px] text-[30px] text-white">
            공개일
          </div>
          <div
            className="w-[100%] h-[13%]"
            onClick={handleCalendar}
            aria-hidden
          >
            <input
              value={releaseDate}
              className="w-[90%] h-[100%] min-h-[50px] text-[24px] text-white bg-cg-2 rounded-2xl"
              readOnly
            />
          </div>
          <div className="w-[100%] flex flex-row items-center justify-center">
            <div
              className="pt-[20px] pb-[8px] pr-[15px] text-[28px] text-white cursor-pointer"
              onClick={handleCheckboxToggle}
              aria-hidden
            >
              암호 사용
            </div>
            <div className="flex items-center pt-[20px] pb-[10px]">
              <label
                htmlFor="passwordCheckbox"
                className={`w-[35px] h-[35px] border-[5px] border-white relative rounded-sm cursor-pointer ${
                  boxChecked ? "bg-white" : "bg-cg-7"
                }`}
              >
                <input
                  type="checkbox"
                  checked={boxChecked}
                  onChange={handleCheckboxToggle}
                  className="w-0 h-0 opacity-0 absolute"
                  id="passwordCheckbox"
                />
                <span
                  className={`block w-[24px] h-[14px] border-t-[5px] border-r-[5px] transform rotate-[135deg] absolute top-[40%] left-[53%] -translate-x-[50%] -translate-y-[50%] ${
                    boxChecked ? "border-cg-7" : "border-transparent"
                  }`}
                />
              </label>
            </div>
          </div>
          <input
            type="password"
            value={roomPassword}
            placeholder={
              boxChecked && !roomPasswordFocused
                ? "비밀번호를 설정해주세요."
                : ""
            }
            className={
              boxChecked
                ? "w-[90%] h-[13%] min-h-[50px] text-[24px] bg-cg-2 rounded-2xl"
                : "w-[90%] h-[13%] min-h-[50px] text-[24px] bg-transparent text-transparent"
            }
            disabled={!boxChecked}
            onFocus={handleRoomPasswordFocus}
            onBlur={handleRoomPasswordBlur}
            onChange={handleRoomPassword}
          />
        </div>
      </div>
      <div className="w-full h-[15%] pt-[20px] flex flex-row items-center justify-evenly z-[20]">
        <button
          type="button"
          className="w-[30%] h-[45px] bg-cg-1 text-[24px] rounded-[60px] text-center"
          onClick={submitCreateRoom}
        >
          만들기
        </button>
        <button
          type="button"
          className="w-[30%] h-[45px] bg-cg-1 text-[24px] rounded-[60px]"
          onClick={() => routeTo("/")}
        >
          닫기
        </button>
      </div>
      {openCalendar && (
        <Modal
          className="fixed w-[320px] h-[320px] bottom-[50%] left-[50%] -translate-x-[160px] translate-y-[100px] z-[150]"
          overlay
          overz="z-[110]"
          openModal={openCalendar}
        >
          <Calendar onChange={handleDateChange} value={today} />
          <div className="mt-[10px]">
            <TimePicker onChange={handleTimeChange} value="00:00" />
          </div>
          <div className="fixed w-[300px] h-[40px] bottom-1/2 left-1/2 -translate-x-[150px] translate-y-[260px] z-[120] flex flex-row items-center justify-around">
            <button
              type="button"
              className="w-[100px] h-[35px] rounded-[60px] bg-cg-2 flex items-center justify-center"
              onClick={handleSetting}
            >
              설정
            </button>
            <button
              type="button"
              className="w-[100px] h-[35px] rounded-[60px] bg-cg-2 flex items-center justify-center"
              onClick={() => setOpenCalendar(false)}
            >
              닫기
            </button>
          </div>
        </Modal>
      )}
      {alertModal && (
        <Alert
          openModal={alertModal}
          closeButton="확인"
          overz="z-[200]"
          text={alertText}
          closeAlert={() => setAlertModal(false)}
        />
      )}
    </>
  );
}

export default CreateRoom;
