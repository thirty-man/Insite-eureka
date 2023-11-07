import React from "react";
import TextBox from "@components/common/TextBox";
import styled from "styled-components";
import { DefaultBox } from "@components/common";

const GuidePageStyle = styled.div`
  .parent{
    display:flex;
  }
  .child{
    margin: auto auto;
  }
  .textMargin{
    margin-bottom:2%
  }
  .text{
    overflow:scroll;
  }
  .box{
    width:100%;
    height:100%;
    margin:5%;
    
  }
  
`


function GuidePage() {
  return(
    <GuidePageStyle>
      
      <div className="parent">
        <div className="child">
          <DefaultBox  width="1000px" height="700px" >
            <h1 className="textMargin">인사이트 이용 가이드 ⯑</h1>
            <TextBox  width="80%" height="80%">
              <div className="box text">
              <br />
                <h2>주의사항:</h2>
                <ul>
                  <li>사항 1: 내용 1</li>
                  <li>사항 2: 내용 2</li>
                  <li>사항 3: 내용 3</li>
                </ul>
                <h2>가이드라인:</h2>
                <p>가이드라인 내용을 여기에 작성합니다.</p>
                  
        
        
              </div>
            </TextBox>
          </DefaultBox>
        </div>
      </div>
    </GuidePageStyle>
      );
}

export default GuidePage;
