import {SmallprofileDTO} from "@/type/smallprofile";


// @ts-ignore
export default async function smallProfile(employeeId: string):Promise<SmallprofileDTO>{
    const url = "http:/127.0.0.1:1006/employee/{employeeId}/smallprofile";

    try {
        const response = await fetch(url);
        if(!response.ok){
            throw new Error();
        }
        return await response.json();
    }
    catch (err){
        console.error(err)
    }
}