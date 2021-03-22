import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from "../model/User";
import { Login } from "../model/Login";



@Injectable()
export class RegisterServices{
    private pacijetUrl:string;

    constructor(private http:HttpClient){}
    
  
 

    public save(user:User){
        return this.http.post<User>("/api/admin/addAdmin",user);
    }

    public logIn(loginReq:Login){
        return this.http.post<Response>("/api/login", loginReq);
    }
 
    public whoIsLoggedIn():Observable<User>{
        return this.http.get<User>("/api/login/getLoggedUser");
        
    }

    public logout(request: Request) {
        return this.http.post("/api/login/logout", request);
    }


}