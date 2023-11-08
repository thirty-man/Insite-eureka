import styled, { css } from "styled-components";
import React, { useEffect, useRef, useState } from "react";
import { setOpenDropdown } from "@reducer/HeaderModalStateInfo";
import { RootState } from "@reducer";
import { useDispatch, useSelector } from "react-redux";
import { setSelectedSite } from "@reducer/SelectedItemInfo";
import { dropdownArrow } from "@assets/icons";
import { ItemType } from "@customtypes/dataTypes";
import siteLogos from "../header/SiteLogo";

interface ComponentProps {
  width: string;
}

interface ButtonProps {
  height: string;
}

interface DropDownProps extends ComponentProps, ButtonProps {
  items: ItemType[];
  placeholder: string;
  initialValue: string;
  onChange: (selectedItem: ItemType) => void;
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
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  height: ${(props) => props.height};
  margin-top: 0.25rem;
  background-color: ${(props) => props.theme.colors.b3};
  border-radius: 0.6rem;
  border: 3px solid #2ce8c7;
  cursor: pointer;
`;

const Select = styled.div<{ $isThemeSelected: boolean }>`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 90%;
  height: 100%;
  outline: none;
  border: none;
  color: ${(props) => (props.$isThemeSelected ? "#f9fafb" : "gray")};
  font-size: 1rem;
`;

const DropDownStyle = styled.div`
  position: absolute;
  width: 100%;
  background-color: ${(props) => props.theme.colors.b3};
  border-radius: 0.6rem;
  border: 3px solid #2ce8c7;
  top: 3.5rem;
  height: auto;
  max-height: 10rem;
  z-index: 9999;
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
  $dropdown: boolean;
}

const Arrow = styled.div<ArrowProps>`
  width: 0.5rem;
  height: 0.5rem;
  background-image: url(${dropdownArrow});
  background-size: contain; // 이미지 크기 설정
  background-repeat: no-repeat; // 이미지 반복 설정
  background-color: transparent;
  margin-right: 0.5rem;

  ${(props) =>
    props.$dropdown
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
  padding-left: 0.7rem;
`;

/** 데이터, 너비, 높이(rem) */
function DropDown({
  items,
  width,
  height,
  placeholder,
  initialValue,
  onChange,
}: DropDownProps) {
  const dispatch = useDispatch();
  const openProfile = useSelector(
    (state: RootState) => state.HeaderModalStateInfo.openProfile,
  );
  const [isDropdown, setIsDropdown] = useState<boolean>(false);

  const [selectedItem, setSelectedItem] = useState<string>(initialValue);

  const selectedSiteLogo = siteLogos[selectedItem || ""];

  useEffect(() => {
    if (openProfile) {
      setIsDropdown(false);
      dispatch(setOpenDropdown(false));
    }
  }, [openProfile, dispatch, setIsDropdown]);

  const dropdownRef = useRef<HTMLDivElement | null>(null);
  useEffect(() => {
    const handleModal = (event: MouseEvent) => {
      if (
        dropdownRef.current &&
        !dropdownRef.current.contains(event.target as Node)
      ) {
        setIsDropdown(false);
        dispatch(setOpenDropdown(false));
      }
    };
    document.addEventListener("click", handleModal);
    return () => {
      document.removeEventListener("click", handleModal);
    };
  });

  const onClickOption = (e: React.MouseEvent) => {
    const themeValue = e.currentTarget.textContent;
    const selectedThemeObj = items.find((item) => item.name === themeValue);
    if (selectedThemeObj) {
      setSelectedItem(selectedThemeObj.name);
      setSelectedSite(selectedThemeObj.name);
      onChange(selectedThemeObj);
    }
    setIsDropdown(false);
    dispatch(setOpenDropdown(false));
  };

  const onClickSelect = (e: React.MouseEvent) => {
    e.stopPropagation();
    const newIsDropdown = !isDropdown;
    setIsDropdown(newIsDropdown);
    dispatch(setOpenDropdown(newIsDropdown));
  };

  return (
    <Component width={width} ref={dropdownRef} onClick={onClickSelect}>
      <SelectButton height={height} type="button">
        {selectedSiteLogo && (
          <SiteLogo src={selectedSiteLogo} alt="site logo" />
        )}
        <Select $isThemeSelected={selectedItem !== ""}>
          {selectedItem === null ? placeholder : selectedItem}
        </Select>
        <Arrow $dropdown={isDropdown} />
      </SelectButton>
      {isDropdown && (
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
