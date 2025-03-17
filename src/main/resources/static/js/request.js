
const authData = window.Telegram.WebApp.initData;
const request= axios.create({
    baseURL:'https://263b-178-218-201-17.ngrok-free.app',
    headers:{
        "Authorization": `tma ${authData}`,
        "Content-Type": "application/json"
    }
})
