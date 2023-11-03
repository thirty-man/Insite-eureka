import styled, { css } from "styled-components";
import React, { useEffect, useRef, useState } from "react";
import { dropdownArrow } from "@assets/icons";
import { ItemTypes } from "@customtypes/dataTypes";
import siteLogos from "../header/SiteLogo";

interface ComponentProps {
  width: string;
}

interface ButtonProps {
  height: string;
}

interface DropDownProps extends ComponentProps, ButtonProps {
  // todo type 추후에 지정해주기
  items: ItemTypes[];
  placeholder: string;
  initialValue: string | null;
}

const Component = styled.div<ComponentProps>`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: ${(props) => props.width};
  position: relative;
`;

const SelectButton = styled.button<ButtonProps>`
  width: 100%;
  display: flex;
  align-items: center;
  height: ${(props) => props.height};
  margin-top: 0.25rem;
  background-color: ${(props) => props.theme.colors.b3};
  border-radius: 0.6rem;
  padding: 1.4rem 1.6rem;
  cursor: pointer;
`;

const Select = styled.div<{ isThemeSelected: boolean }>`
  width: 95%;
  outline: none;
  border: none;
  color: ${(props) => (props.isThemeSelected ? "#f9fafb" : "gray")};
  font-size: 1rem;
  text-align: left;
`;

const DropDownStyle = styled.div`
  position: absolute;
  width: 100%;
  background-color: ${(props) => props.theme.colors.b3};
  border-radius: 0.6rem;
  top: 3.5rem;
  height: auto;
  max-height: 10rem;
  overflow-y: auto;
  @keyframes dropdown {
    0% {
      transform: translateY(-15%);
      height: 0;
      overflow-y: hidden;
    }
    100% {
      transform: translateY(0);
      height: auto;
      overflow-y: auto;
    }
  }
  animation: dropdown 0.4s ease;
`;

const Option = styled.button`
  width: 100%;
  color: white;
  background-color: ${(props) => props.theme.colors.b3};
  font-size: 1rem;
  height: 2.5rem;
  &:hover {
    border-radius: 0.6rem;
    background-color: rgba(255, 255, 255, 0.1);
    cursor: pointer;
  }
`;

interface ArrowProps {
  dropdown: boolean;
}

const Arrow = styled.div<ArrowProps>`
  width: 1rem;
  height: 1rem;
  background-image: url(${dropdownArrow});
  background-size: contain; // 이미지 크기 설정
  background-repeat: no-repeat; // 이미지 반복 설정
  background-color: transparent;

  ${(props) =>
    props.dropdown
      ? css`
          transform: translate(0, -10%) rotate(180deg);
          transition: transform 0.5s ease;
        `
      : css`
          transform: translate(0, 10%) rotate(0deg);
          transition: transform 0.5s ease;
        `}
`;

const SiteLogo = styled.img`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 1.3rem;
  height: 1.3rem;
  margin-right: 20px;
`;

/** 데이터, 너비, 높이(rem) */
function DropDown({
  items,
  width,
  height,
  placeholder,
  initialValue,
  openDropdown,
  setOpenDropdown,
  onClickProfile,
}: DropDownProps & {
  openDropdown: boolean;
  setOpenDropdown: (openDropdown: boolean) => void;
  onClickProfile: (e: React.MouseEvent) => void;
}) {
  const [selectedItem, setSelectedItem] = useState(initialValue);
  // const reduxStateValue: string | null = "naver.com";
  const selectedSiteLogo = siteLogos[selectedItem || ""];

  const dropdownRef = useRef<HTMLDivElement | null>(null);
  useEffect(() => {
    const handleModal = (event: MouseEvent) => {
      if (
        dropdownRef.current &&
        !dropdownRef.current.contains(event.target as Node)
      ) {
        setOpenDropdown(false);
      }
    };
    document.addEventListener("click", handleModal);
    return () => {
      document.removeEventListener("click", handleModal);
    };
  });

  const onClickOption = (e: React.MouseEvent<HTMLButtonElement>) => {
    setOpenDropdown(false);
    const themeValue = e.currentTarget.textContent;
    const selectedThemeObj = items.find((item) => item.name === themeValue);
    if (selectedThemeObj) {
      setSelectedItem(selectedThemeObj.name);
    }
  };

  const onClickSelect = (e: React.MouseEvent) => {
    e.stopPropagation();
    setOpenDropdown(!openDropdown);
    onClickProfile(e);
  };

  return (
    <Component width={width} ref={dropdownRef} onClick={onClickSelect}>
      <SelectButton height={height} type="button">
        {selectedSiteLogo && (
          <SiteLogo src={selectedSiteLogo} alt="site logo" />
        )}
        <Select isThemeSelected={selectedItem !== ""}>
          {selectedItem === null ? placeholder : selectedItem}
        </Select>
        <Arrow dropdown={openDropdown} />
      </SelectButton>
      {openDropdown && (
        <DropDownStyle>
          {items.map((item) => (
            <Option value={item.id} key={item.id} onClick={onClickOption}>
              {item.name}
            </Option>
          ))}
        </DropDownStyle>
      )}
    </Component>
  );
}

export default DropDown;
