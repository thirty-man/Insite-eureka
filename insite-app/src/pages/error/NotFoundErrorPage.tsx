/* eslint-disable react/jsx-boolean-value */
/* eslint-disable react/self-closing-comp */
/* eslint-disable react-hooks/rules-of-hooks */
/* eslint-disable import/order */
/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable import/no-extraneous-dependencies */
// /* eslint-disable @typescript-eslint/no-explicit-any */
// /* eslint-disable import/order */
// /* eslint-disable import/no-extraneous-dependencies */

import { useState, useEffect } from "react";
import { useSpring, animated } from "@react-spring/web";
import styled, { css } from "styled-components";
import backgroundImg from "../../assets/images/애니메이션배경.gif";
import backgroundImg2 from "../../assets/images/메인페이지_설명5.jpg";
import MainHeader2 from "@components/common/header/MainHeader2";
import { Element } from "react-scroll";

const StyledText1 = styled.div`
  text-align: center;
  transition: 0.5s;
  color: white;
  display: block;
  position: absolute;
  top: 28%;
  left: 10%;
  font-size: 40px;
  font-weight: bold;

  &:hover {
    color: white;
    transform: scale(1.06);
    transition: transform 0.1s ease;
  }
`;

const StyledText2 = styled.div`
  text-align: center;
  transition: 0.5s;
  color: white;
  display: block;
  position: absolute;
  top: 35%;
  left: 10%;
  font-size: 40px;
  font-weight: bold;

  &:hover {
    color: white;
    transform: scale(1.06);
    transition: transform 0.1s ease;
  }
`;

const StyledText3 = styled.div`
  text-align: center;
  transition: 0.5s;
  color: white;
  display: block;
  position: absolute;
  top: 45%;
  left: 10%;
  font-size: 24px;

  &:hover {
    color: white;
    transform: scale(1.06);
    transition: transform 0.1s ease;
  }
`;

const Section = styled(Element)`
  width: 100%;
  height: 100vh; // This makes the section take up the full viewport height
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Container = styled.div`
  width: 100%;
  height: 100vh; /* Making the container tall to enable scrolling */
  position: relative;
  background-color: #252531;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  overflow-y: scroll; /* Allow vertical scrolling */
  ${css`
    &::-webkit-scrollbar {
      display: none;
    }
  `}
`;

const DynamicBackground = styled(animated.div)`
  position: fixed;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  background-image: url(${backgroundImg2}),
    radial-gradient(circle at center, transparent 0%, #252531 80%),
    url(${backgroundImg});
  background-position:
    left,
    right center,
    right;
  background-repeat: no-repeat, no-repeat, no-repeat;
  background-size: 45%, 55%, 55%; /* Set this to the correct size of your images */
  z-index: 3;

  &:before {
    content: "";
    position: absolute;
    top: 0;
    left: 45%; /* Adjust this value so it overlays the left edge of the right image */
    height: 100%;
    width: 300px; /* Width of the blur effect */
    background: linear-gradient(to left, rgba(0, 0, 0, 0), #252531);
  }
`;

function NotFoundErrorPage() {
  const [vhInPixels, setVhInPixels] = useState(0);

  // Update vhInPixels whenever the window resizes
  useEffect(() => {
    const updateVhInPixels = () => {
      setVhInPixels(window.innerHeight);
    };

    window.addEventListener("resize", updateVhInPixels);

    // Call the handler immediately to set the initial value
    updateVhInPixels();

    // Remove the event listener when the component unmounts
    return () => window.removeEventListener("resize", updateVhInPixels);
  }, []);

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

  // Define the background and image properties to change with scroll
  const imageTransform = scrollY.to(
    (y) => `scale(${1 + y / (2 * vhInPixels)})`, // Image scale grows as you scroll down
  );

  const imageOpacity = scrollY.to((y) => `${1 - y / (0.5 * vhInPixels)}`);

  // Make sure to only render once we have the window height
  if (!vhInPixels) {
    return null;
  }

  // A function to render sections
  const renderSection = (key: number, content: JSX.Element) => (
    <Section name={`section-${key}`} key={key}>
      {content}
    </Section>
  );

  return (
    <Container>
      <MainHeader2 scrollY={scrollY} />
      {renderSection(
        1,
        <DynamicBackground
          style={{
            transform: imageTransform, // Apply the dynamic scale transformation
            opacity: imageOpacity, // Apply the dynamic opacity
          }}
        >
          <StyledText1>404</StyledText1>
          <StyledText2>페이지를 찾을 수 없습니다.</StyledText2>
          <StyledText3>
            요청하신 페이지가 사라졌거나, 잘못된 경로를 이용하셨습니다.
          </StyledText3>
        </DynamicBackground>,
      )}
    </Container>
  );
}

export default NotFoundErrorPage;
