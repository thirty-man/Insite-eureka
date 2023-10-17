function Login() {
  return (
    <>
      <div className="flex h-10 items-center justify-end px-5">
        도움말 들어갈 자리
      </div>
      <h1 className="h-1/6">
        <span className="text-cg-3">꿀</span>&apos;s Writing
      </h1>
      <div className="flex h-4/6 justify-center items-center">
        <div className="flex flex-col h-full items-center justify-center">
          <img src="../src/assets/images/푸꿀1.png" alt="mainpooh" />
          <button className="rounded border border-blue-700" type="button">
            <img
              src="../src/assets/images/kakao_login_medium_wide.png"
              alt="kakao Login Btn"
            />
          </button>
        </div>
      </div>
    </>
  );
}

export default Login;
