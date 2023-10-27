package com.thirty.ggulswriting.member.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonParser;

@Slf4j
@Service
public class KakaoLoginServiceImpl implements KakaoLoginService{
    @Value("${KAKAO_URL}")
    private String kakaoUrl;
//    private String tempUrl = "http://k9a701a.p.ssafy.io:3000/login";
    @Override
    public String getKakaoAccessToken(String code) {
        String access_Token ="";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";
        try{
            URL url = new URL(reqURL);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=367be5f2a1031bc9fb556dd456869c88"); // TODO REST_API_KEY 입력
            sb.append("&redirect_uri="+kakaoUrl);//TODO 인가코드 받은 redirect_uri 입력
            sb.append("&code=" +code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드 200=성공
            int responseCode = connection.getResponseCode();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line= "";
            String result="";

            while((line = br.readLine())!= null){
                result+=line;
            }

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            br.close();
            bw.close();

        }catch(IOException e){
            e.printStackTrace();;
        }
        return access_Token;
    }

    @Override
    public String createKakaoUser(String token) {
        String kakaoId ="";
        String reqURL = "https://kapi.kakao.com/v2/user/me";


        //access_token으로 사용자 조회
        try{
            URL url = new URL(reqURL);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Bearer "+token);// 전송할 Header 작성 및 access_token 전송

            //결과 코드 200 = 성공
            int responseCode = connection.getResponseCode();

            //받은 JSON타입 Response 메시지 읽기
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line="";
            String result="";

            while((line=br.readLine())!=null){
                result+=line;
            }

            //Gson 라이브러리로 JSON 파싱
            JsonParser parser= new JsonParser();
            JsonElement element= parser.parse(result);
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            kakaoId = element.getAsJsonObject().get("id").getAsString();
            kakaoId+=" ";
            kakaoId+= properties.get("nickname").getAsString();
            System.out.println("KakaoLogin Service kakaoId 변수 = "+ kakaoId);
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        return kakaoId;
    }
}
