import styled from "styled-components";

const HeaderContainer = styled.div`
  position: fixed;
  width: 82%;
  height: 20%;
  top: 0;
  right: 0;
  background-color: ${(props) => props.theme.colors.b1};
`;

const HeaderWrapper = styled.div`
  width: 100%;
  height: 50%;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  font-weight: 900;
`;

function Header() {
  return (
    <HeaderContainer>
      <HeaderWrapper>
        <div>헤더</div>
      </HeaderWrapper>
    </HeaderContainer>
  );
}

export default Header;
