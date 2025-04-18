//개인정보수정페이지
import {updatePasswordDTO} from "@/type/updatepassword";

export default async function UpdatePasswordAction(employeeId: string, currentPassword: string, newPassword: string): Promise<updatePasswordDTO | null> {
  const url = `http://127.0.0.1:1006/employee/${employeeId}/updatepassword`;
  try {

    let token = "";
    if (typeof window !== "undefined") {
      token = localStorage.getItem("accessToken") || "";
    }

    const response = await fetch(url,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
          },
          body: JSON.stringify({
            currentPassword,
            newPassword,
          }),
        });

    if (!response.ok) {
      console.log("if response" + response)
      throw new Error(`Failed to fetch data fetch 오류 오류 오류 오류: ${response.status} ${response.statusText}`);
    }
    const data: updatePasswordDTO = await response.json();
    console.log("password update 성공함 ㅎ " + data);
    return data;
  } catch (err) {
    console.error("Error fetching EmployeePasswordUpdate 실패 FUCK:" + err);
    return null;
  }
}